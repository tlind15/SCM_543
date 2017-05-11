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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class RepoFile extends File {

    //*** Thomas Lindblom - tlindblomjr@gmail.com***
    private String username;
    private String destPath;
    private int version;

    public RepoFile(String destPath) {
        super(destPath); //is the default constructor for the java 'File' class
        this.destPath = destPath;
    }

    private File copyFolder(String originalPath) throws IOException {
        //File file_dest = FileUtils.getFile(dest + "\\" + this.getName()); //creates file path for new destination from String
        File file_original = FileUtils.getFile(originalPath);
        File repo_directory = FileUtils.getFile(this + "\\" + file_original.getName());
        FileUtils.copyDirectory(file_original, repo_directory);

        return repo_directory;
    }


    private void renameLeafFileArtifact(File leafDirectoryFolder) throws IOException {
        List<File> artifactFiles = (List<File>) FileUtils.listFiles(leafDirectoryFolder, null, true);

        String newFileName;
        File codeNameFile;
        for (File artifact : artifactFiles) {
            newFileName = newFileCodeName(artifact);
            codeNameFile = FileUtils.getFile(leafDirectoryFolder.getAbsolutePath() + "\\" + newFileName);
            FileUtils.moveFile(artifact, codeNameFile);
        }
    }

    private void createLeafFolder(File file, String project_path) throws IOException {
        final String TEMP_DIR = "scm_temp";
        File tempDir = FileUtils.getFile(file.getParent() + "\\" + TEMP_DIR);
        FileUtils.moveFileToDirectory(file, tempDir, true);
        File leafDir = FileUtils.getFile(tempDir.getParent() + "\\" + file.getName());
        FileUtils.moveDirectory(tempDir, leafDir);

        renameLeafFileArtifact(file); //give file artifact a code name
        String artifact_id = "";
        List<File> artifactFiles = (List<File>) FileUtils.listFiles(leafDir, null, false);
        for (File artifact : artifactFiles) {
            //artifact_id = newFileCodeName(artifact);
            String[] path_split = artifact.getAbsolutePath().split("\\\\");
            for (String s : path_split)
                System.out.println(s);
            writeToManifesto_FileAdded(artifact, "original path here", artifact.getAbsolutePath(), String.valueOf(this.version));
        }
    }

    public String[] findProjectFileEntryByID(String artifact_id) {
        final int ARTIFACT_ID_IDX = 2;
        final int VERSION_IDX = 4;
        List<String[]> manifest_data = parseManifestFile();

        for (String[] file_entry : manifest_data) {
            if (file_entry[ARTIFACT_ID_IDX].equals(artifact_id) && Integer.valueOf(file_entry[VERSION_IDX]) == this.version)
                return file_entry;
        }
        return null;
    }

    public String findProjectFilePathByID(String artifact_id) {
        String[] file_entry = findProjectFileEntryByID(artifact_id);
        final int FILE_PATH_IDX = 3;
        return file_entry[FILE_PATH_IDX];
    }


    private void createActivityDirectory() throws IOException { //also creates than manifest.txt file
        File activity = FileUtils.getFile(this.destPath + "\\" + "activity");
        FileUtils.forceMkdir(activity);

        File manifest = FileUtils.getFile(activity.getAbsolutePath() + "\\" + "manifest.txt");
        manifest.createNewFile();
    }

    public File getManifestFile() {
        return new File(this.getAbsolutePath() + "\\" + "activity" + "\\" + "manifest.txt");
    }

    public void createRepo(String originalPath, String username) throws IOException {
        this.username = username;
        File repoDir = copyFolder(originalPath);
        createActivityDirectory();
        writeToManifest_RepoCreate(destPath, originalPath, username);
        List<File> leafFiles = (List<File>) FileUtils.listFiles(repoDir, null, true);
        for (File f : leafFiles)
            createLeafFolder(f, originalPath);
    }

    public void checkout(int repo_version) {
        //public List<String> getRepoArtifactsByVersion(int version) {} //returns
        //public List<String> getProjFilePathsByVersion(int version) {}
        // for (int i=0; i < repo_paths.size(), i++) {
        //      FileUtils.moveFileToDirectory(repo_paths[i], project_paths[i].getParent(), true);
        //rename file in project to not have artifact ID
        // }
    }
    //***end Thomas***

    //***Yushen Huang - maple.yushen@gmail.com***
    public static String newFileCodeName(File file) throws IOException {
        long fileSize = file.length();   //get the size of file
        String fileCurrentName = file.getName();   //get the original name of file to be renamed
        String extension = fileCurrentName.substring(fileCurrentName.lastIndexOf("."));   //get the file name extension
        InputStream fileIS = new FileInputStream(file);  //get file content
        int checkSum = checkSum(fileIS);  // Call function for checkSum
        fileIS.close();  // close the input stream
        return checkSum + "." + fileSize + extension;   //rename file
    }

    public static int checkSum(InputStream fileIS) throws IOException {
        int checkSum = 0, i = 0, tempASCII;   //set a starting point for computing checksum
        while ((tempASCII = fileIS.read()) != -1)   //compute the checksum for the whole file
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
    //Returns a list of all added files in the manifest
    //Each row follows this indexing
    //0: file_name
    //1: path_in_repo
    //2: artifact id
    //3: File Location
    //4: Version
    public List<String[]> parseManifestFile() {
        String file = getManifestFile().getAbsolutePath();
        List<String[]> filesList = new ArrayList<String[]>();

        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.toLowerCase().contains("adding file...")) {
                    System.out.println("Test");
                    String[] spt = line.split("\t");
                    filesList.add(new String[]{spt[2], spt[4], spt[6], spt[8], spt[10]});
                }
            }
            fileReader.close();
            for (String[] row : filesList) {
                System.out.println("Row = " + Arrays.toString(row));
            }
            return filesList;
        } catch (IOException f) {
            System.out.println("Could not read manifest file");
        }
        return filesList;
    }

    public void writeToManifest_RepoCreate(String repo_loc, String dir_cpy, String username) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new
                OutputStreamWriter(new FileOutputStream(repo_loc + "\\" + "activity" + "\\" + "manifest.txt", true), StandardCharsets.UTF_8)))) {
            out.println("User:\t" + username + "\tCreated Repo at:\t" + repo_loc + "\t" + "Version:\t1");
        } catch (IOException e) {
            System.out.println("Could not write to Activity Logs");
        }
        //exception handling left as an exercise for the reader
    }

    //check-out repofoldername emptyfolder version
    private void writeToManifesto_CheckOut(String repoFolderName, String checkOutFolder, String version) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
            out.println("Check-out from\tRepo Folder:\t" + repoFolderName + "\tto Folder:\t" + checkOutFolder
                    + "\tVersion:\t" + version);
        } catch (IOException e) {
            System.out.println("Could not write checked out file");
        }
        //exception handling left as an exercise for the reader
    }

    //check-in repofoldername checkoutfolder
    private void writeToManifesto_CheckIn(String repoFolderName, String checkOutFolder) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
            String version = String.valueOf(this.version);
            out.println("Check-In to\tRepo Folder:\t" + repoFolderName + "\tfrom Folder:\t" + checkOutFolder
                    + "\tVersion:\t" + version);
        } catch (IOException e) {
            System.out.println("Could not write checked out file");
        }
        //exception handling left as an exercise for the reader
    }

    private void writeToManifesto_FileAdded(File artifactFile, String original_file_name, String path_in_repo, String version) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
            out.println("Adding File...\tFile Name:\t" + original_file_name + "\tPath In Repo\t" + path_in_repo + "\tArtifact ID:\t" +
                    artifactFile.getName().substring(0, artifactFile.getName().lastIndexOf(".")) + "\tFile Location:\t" + artifactFile.getAbsolutePath()
                    + "\tVersion:\t" + version);
        } catch (IOException e) {
            System.out.println("Could not write added file");
        }
        //exception handling left as an exercise for the reader
    }

    public int getLatestVersion(List<String[]> filesList) {
        int latest_version = 1;
        int temp;
        for (String[] row : filesList) {
            temp = Integer.parseInt(row[4]);
            if (temp > latest_version) {
                latest_version = temp;
            }
        }
        System.out.println(latest_version);
        return latest_version;
        //***end Jocelyn***

    }
}
