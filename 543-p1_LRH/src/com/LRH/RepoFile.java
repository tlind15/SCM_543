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

    private String username;
    private String destPath;

    public RepoFile (String destPath, String username) {
        super(destPath); //is the default constructor for the java 'File' class
        this.destPath
        this.username = username;
    }

    private void copyFolder(String originalPath) throws IOException {
        //File file_dest = FileUtils.getFile(dest + "\\" + this.getName()); //creates file path for new destination from String
        File file_original = FileUtils.getFile(originalPath);
        FileUtils.copyDirectory(file_original, this);
        //return file_dest;
    }

    public static String newFileCodeName(File file) throws IOException {
        long fileSize = file.length();   //get the size of file
        String fileCurrentName = file.getName();   //get the original name of file to be renamed
        String extension = fileCurrentName.substring(fileCurrentName.lastIndexOf("."));   //get the file name extension
        InputStream fileIS = new FileInputStream(file);  //get file content
        int checkSum = checkSum(fileIS);  // Call function for checkSum
        fileIS.close();  // close the input stream
        return checkSum + "." + fileSize + extension;   //rename file
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

    private void renameLeafFileArtifact(File leafDirectoryFolder) throws IOException {
        List<File> artifactFiles = (List<File>) FileUtils.listFiles(leafDirectoryFolder, null, false);

        String newFileName;
        File codeNameFile;
        for (File artifact : artifactFiles) {
            newFileName = newFileCodeName(artifact);
            codeNameFile = FileUtils.getFile(leafDirectoryFolder.getAbsolutePath() + "\\" + newFileName);
            FileUtils.moveFile(artifact, codeNameFile);
        }
    }

    private void createLeafFolder (File file) throws IOException {
        final String TEMP_DIR = "scm_temp";
        File tempDir = FileUtils.getFile(file.getParent() + "\\" + TEMP_DIR);
        FileUtils.moveFileToDirectory(file, tempDir, true);
        File leafDir = FileUtils.getFile(tempDir.getParent() + "\\" + file.getName());
        FileUtils.moveDirectory(tempDir, leafDir);

        renameLeafFileArtifact(file); //give file artifact a code name

        //will eventually call write to manifest
    }

    private void createActivityDirectory(String repoDestPath) throws IOException { //also creates than manifest.txt file
        File activity = FileUtils.getFile(repoDestPath + "\\" + "activity");
        FileUtils.forceMkdir(activity);

        File manifest = FileUtils.getFile(activity.getAbsolutePath() + "\\" + "manifest.txt");
        manifest.createNewFile();
    }

    public void createRepo(String repoDestPath) throws IOException {
        File dest_file = this.copyFolder(repoDestPath);
        List<File> leafFiles =  (List<File>) FileUtils.listFiles(dest_file, null, true);
        for (File f : leafFiles)
            createLeafFolder(f);
        createActivityDirectory(repoDestPath);
    }

    //whenever a repo is created write to the activity logs 'who, what, when'
    public void writeToManifesto_RepoCreate(String repo_loc, String dir_cpy, String username, String manifestoFile)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(manifestoFile, true), StandardCharsets.UTF_8)))) {
            out.println("User: " + username + " Created Repo at: " + repo_loc + " - " + timestamp);
        } catch (IOException e) {System.out.println("Could not write to Activity Logs");}
    }
}
