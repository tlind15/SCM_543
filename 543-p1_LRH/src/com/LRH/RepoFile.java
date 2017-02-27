package com.LRH;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class RepoFile extends File {

    //*** Thomas Lindblom - tlindblomjr@gmail.com***
    
    /*The RepoFile class simply extends the java File class and adds some funcitonality. The RepoFile represents the directory
    in which the user will create their new Repository. See individual functions for more specific descriptions.*/
    private String username;
    private String destPath;
    
    public RepoFile (String destPath) {
        super(destPath); //is the default constructor for the java 'File' class
        this.destPath = destPath;
    }

    //this simply copies the project tree at 'originalPath' to the 'destPath' of the RepoFile
    private File copyFolder(String originalPath) throws IOException {
        File file_original = FileUtils.getFile(originalPath); //gets file at 'originalPath'
        File repo_directory = FileUtils.getFile(this + "\\" + file_original.getName()); //this maintains the original project tree parent folder
        FileUtils.copyDirectory(file_original, repo_directory);

        return repo_directory;
    }
    
    //creates the leaf file folders in repository
    private void createLeafFolder (File file) throws IOException {
        final String TEMP_DIR = "scm_temp"; 
        File tempDir = FileUtils.getFile(file.getParent() + "\\" + TEMP_DIR); //makes a temporary directory because the leafDirectory cannot have the same name as the file at this point
        FileUtils.moveFileToDirectory(file, tempDir, true); //move leaf file into new directory
        File leafDir = FileUtils.getFile(tempDir.getParent() + "\\" + file.getName());
        FileUtils.moveDirectory(tempDir, leafDir); //rename temp directory to old file name

        renameLeafFileArtifact(file); //give file artifact a code name

        List<File> artifactFiles = (List<File>) FileUtils.listFiles(leafDir, null, false);
        for (File artifact : artifactFiles) //make a manifest entry for the newly created leafDirectory
            writeToManifesto_FileAdded(artifact, leafDir.getName());

    }

    //give leafFile artifact an AID name
    private void renameLeafFileArtifact(File leafDirectoryFolder) throws IOException { 
        List<File> artifactFiles = (List<File>) FileUtils.listFiles(leafDirectoryFolder, null, false);
        String newFileName;
        File codeNameFile;
        for (File artifact : artifactFiles) {
            newFileName = newFileCodeName(artifact);
            codeNameFile = FileUtils.getFile(leafDirectoryFolder.getAbsolutePath() + "\\" + newFileName);
            FileUtils.moveFile(artifact, codeNameFile); //actually rename the artifact file
        }
    }

    //creates the manifest.txt file
    private void createActivityDirectory() throws IOException { 
        File activity = FileUtils.getFile(this.destPath + "\\" + "activity");
        FileUtils.forceMkdir(activity);

        File manifest = FileUtils.getFile(activity.getAbsolutePath() + "\\" + "manifest.txt");
        manifest.createNewFile();
    }
    
    //provides simple way to get this directory path
    public File getManifestFile () {
        return new File(this.getAbsolutePath() + "\\" + "activity" + "\\" + "manifest.txt");
    }

    //creates the repository with project tree from 'originalPath' to 'destPath' supplied in constructor
    public void createRepo(String originalPath, String username) throws IOException {
        this.username = username; //set the user doing the command
        File repoDir = copyFolder(originalPath); //copy files
        createActivityDirectory();
        writeToManifest_RepoCreate(originalPath);
        List<File> leafFiles =  (List<File>) FileUtils.listFiles(repoDir, null, true);
        for (File f : leafFiles) //create leafDirectories
            createLeafFolder(f);
    }
    //***end Thomas***

    //***Yushen Huang - maple.yushen@gmail.com 
    //This newFileCodeName function implement the Artifact code names as well as its original extension.
    //It also calls the checkSum to finish computing the Artifact ID per***
    
    public static String newFileCodeName(File file) throws IOException {
        long fileSize = file.length();   //get the size of file
        String fileCurrentName = file.getName();   //get the original name of file to be renamed
        String extension = fileCurrentName.substring(fileCurrentName.lastIndexOf("."));   //get the file name extension
        InputStream fileIS = new FileInputStream(file);  //get file content
        int checkSum = checkSum(fileIS);  // Call function for checkSum
        fileIS.close();  // close the input stream
        return checkSum + "." + fileSize + extension;   //file code name
    }

    public static int checkSum(InputStream fileIS) throws IOException{
        int checkSum = 0, i = 0, tempASCII;   //set a starting point for computing checksum
        while ( (tempASCII = fileIS.read()) != -1)   //compute the checksum for the whole file
        {
            int index = i % 4;
            switch (index) {
                case 1:
                    tempASCII *= 3;
                    break;
                case 2:
                    tempASCII *= 11;
                    break;
                case 3:
                    tempASCII *= 17;
                    break;
            }
            checkSum += tempASCII;
            i++;
        }
        return checkSum;
    }
    //***end Yushen***


    //***Jocelyn Ramirez - jsyramirez@gmail.com***
    //whenever a repo is created write to the activity logs 'who, what, when'
    private void writeToManifest_RepoCreate(String originalPath)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath() , true), StandardCharsets.UTF_8)))) {
            out.println("Project Code Name: BRC-1 " + "User: " + username + " Created Repo at: " + this.getAbsolutePath() + " Created Repo From: " + originalPath + " - " + timestamp);
        } catch (IOException e) {System.out.println("Could not write to Activity Logs");}
    }

    private void writeToManifesto_FileAdded(File artifactFile, String original_file_name)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
            out.println("Adding File...\nFile Name: " + original_file_name + " Artifact ID: " + artifactFile.getName().substring(0, artifactFile.getName().lastIndexOf(".")) + " File Location: " + artifactFile.getAbsolutePath());
        } catch (IOException e) {System.out.println("Could not write added file");}
        //exception handling left as an exercise for the reader
    }
    //***end Jocelyn***
}
