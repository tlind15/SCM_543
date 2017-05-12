    public void checkInS(String projectPath, String repoPath, String username) throws IOException {
        String originalProjectName = projectPath.substring(projectPath.lastIndexOf("\\")); //get original project name eg: D:\mypt return mypt
        createRepo(projectPath, username); //create a temp repo from users project file at projectPath

        ArrayList<String> temRepoFolderPath = getAllFolderPath(projectPath + "\\" + originalProjectName);   //eg: D:\mypt\mypt\src\main.fool\96969.fool return D:\mypt\mypt\src\main.fool
        ArrayList<String> tempRepoFileNames = getAllFilesNames(projectPath + "\\" + originalProjectName); //get all file names at repo folder D:\mypt\mypt\src\main.fool\96969.fool return 96969.fool
        boolean fileModified = false;
        boolean folerModified = false;

        for (String eachFolderPath : temRepoFolderPath) {    //get each folder path from repoFolderPath D:\mypt\mypt\src\main.fool
            if (fileModified && folerModified) {
                folerModified = false;
                fileModified = false;
                continue;
            }
            String folderName = eachFolderPath.substring(eachFolderPath.lastIndexOf("\\"));

            if (! folderName.equalsIgnoreCase("activity")) {   //exclude activity folder eg: D:\...\activity



                for (String tgtFileName :  tempRepoFileNames) {
                    if (folerModified || fileModified) {
                        folerModified = false;
                        fileModified = false;
                        break;
                    }

                    File tempFolder = new File(repoPath + "\\" + eachFolderPath.substring(eachFolderPath.lastIndexOf(originalProjectName)));

                    if (!tempFolder.exists()) {
                        FileUtils.copyDirectoryToDirectory(new File(eachFolderPath),new File(tempFolder.getParent()));
                        folerModified = true;
                        System.out.print("foldercopied");
                        continue;
                    }

                        //E:\repo  and D:\mypt\mypt\src\main.fool, return E:\repo\mypt\src\main.fool
                    for (String fileName : getAllFilesNames(repoPath + "\\" + eachFolderPath.substring(eachFolderPath.lastIndexOf(originalProjectName)))) {    //get each file name in current repo eg: 96969.fool
                        if (fileModified) {
                            fileModified = false;
                            continue;
                        }
                        System.out.print(fileName);
                        System.out.println();

                                if(!tgtFileName.equalsIgnoreCase("manifest")) {
                                    if (tgtFileName == fileName){
                                        break;
                                    }
                                if(!new File(eachFolderPath + "\\" + tgtFileName).exists()){
                                        break;
                                }
                                    File tempFile = new File(eachFolderPath + "\\" + tgtFileName);

                                    System.out.print(eachFolderPath + "\\" + tgtFileName);
                                    System.out.println();
                                    System.out.print(repoPath + "\\" + eachFolderPath.substring(eachFolderPath.lastIndexOf(originalProjectName)));
                                    System.out.println();

                                        if (tgtFileName != fileName && tempFile.exists() && tempFolder.isDirectory()) {
                                            FileUtils.moveToDirectory(tempFile, tempFolder, true);
                                            fileModified = true;
                                            System.out.print("file copied"+tempFile.getPath());
                                            System.out.println();
                                            //call manifest function
                                            break;
                                        }
                                    }
                                }
                }
            }

        }
        FileUtils.deleteDirectory(new File(projectPath + "\\activity"));
        FileUtils.deleteDirectory(new File(projectPath + "\\" + originalProjectName)); //delete temp repo folder from user's project path

    }

    public ArrayList<String> getAllFolderPath(String tgtDirectory) throws IOException{
        File repoDir = new File(tgtDirectory);
        List<File> allFiles =  (List<File>) FileUtils.listFiles(repoDir, null, true);
        ArrayList<String> filePath = new ArrayList<String>();
        for (File eachFile : allFiles) {
            filePath.add(eachFile.getParent());
        }
        return filePath;
    }

    public ArrayList<String> getAllFilesNames(String tgtDirectory) throws IOException{
        File repoDir = new File(tgtDirectory);
        List<File> allFiles =  (List<File>) FileUtils.listFiles(repoDir, null, true);
        ArrayList<String> fileNames = new ArrayList<String>();
        for (File eachFile : allFiles) {
            fileNames.add(eachFile.getName());
        }
        return fileNames;
    }
