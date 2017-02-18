package com.LRH;

import java.io.File;


public class RepoFile extends File {

    private String path;

    public RepoFile (String path) {
        super(path); //is the default constructor for the java 'File' class
        this.path = path;
    }
}
