package com.LRH;

import java.io.*;

public static String newFileCodeName(File file) throws IOException{
    long fileSize = file.length();   //get the size of file
    String fileCurrentName = file.getName();   //get the original name of file to be renamed
    String extension = fileCurrentName.substring(fileCurrentName.lastIndexOf("."));   //get the file name extension
    InputStream fileIS = new FileInputStream(file);  //get file content
    int checkSum = checkSum(fileIS);  // Call function for checkSum
    fileIS.close();  // close the input stream
    fileCurrentName = checkSum + "." + fileSize + extension;   //rename file
    return fileCurrentName;
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
