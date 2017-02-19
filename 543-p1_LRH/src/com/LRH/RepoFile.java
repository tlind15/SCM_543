package com.LRH;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class RepoFile extends File {
    
    public RepoFile (String path) {
        super(path); //is the default constructor for the java 'File' class
    }

    public void copyFolder(String dest) throws IOException {
        File file_dest = FileUtils.getFile(dest + "\\" + this.getName()); //creates file path for new destination from String
        FileUtils.copyDirectory(this, file_dest);
    }
    
    public void createLeafFolder (File file) throws IOException {
        final String TEMP_DIR = "scm_temp";
        File tempDir = FileUtils.getFile(file.getParent() + "\\" + TEMP_DIR);
        FileUtils.moveFileToDirectory(file, tempDir, true);

        File leafDir = FileUtils.getFile(tempDir.getParent() + "\\" + file.getName());
        FileUtils.moveDirectory(tempDir, leafDir);
    }
    
public void createRepo(String dest) throws IOException {
        File dest_file = this.copyFolder(dest);
        List<File> leafFiles =  (List<File>) FileUtils.listFiles(dest_file, null, true);
        for (File f : leafFiles)
            createLeafFolder(f);

        File activity = FileUtils.getFile(dest + "\\" + "activity");
        FileUtils.forceMkdir(activity);

        File manifest = FileUtils.getFile(activity.getAbsolutePath() + "\\" + "manifest.txt");
        manifest.createNewFile();
    }
}
