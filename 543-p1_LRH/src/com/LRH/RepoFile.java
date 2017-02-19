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
}
