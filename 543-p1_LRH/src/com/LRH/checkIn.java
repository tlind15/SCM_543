    public ArrayList<String> getAllFilesNames(String tgtDirectory) throws IOException{
        File repoDir = new File(tgtDirectory);
        List<File> allFiles =  (List<File>) FileUtils.listFiles(repoDir, null, true);
        ArrayList<String> fileNames = new ArrayList<String>();
        for (File eachFile : allFiles) {
            fileNames.add(eachFile.getName());
        }
        return fileNames;
    }

    public void checkIn(String projectPath, String repoPath, String username) throws IOException{
        ArrayList<String> projectFileNames = getAllFilesNames(projectPath); //get all file names at users project folder which is folder names for repo
        String originalProjectName = projectPath.substring(projectPath.lastIndexOf("\\")); //get original project name from user

        createRepo(projectPath, username); //create a temp repo from users project file. projectPath

        for (String folderName : projectFileNames) {  // for all folder in repo
            if (!folderName.equals("activity")) {  //exclude activity folder
                for (String eachArtifact : getAllFilesNames(repoPath + "\\" + originalProjectName + "\\" + folderName)) {  //get file name from current repo
                    for (String tempCheckInCopy : getAllFilesNames(projectPath + "\\" + originalProjectName+ "\\" + folderName)) {   // get all file names from temp repo
                        if (eachArtifact != tempCheckInCopy) {
                            FileUtils.copyFileToDirectory(new File(projectPath + "\\" + originalProjectName + "\\" + folderName + "\\" + tempCheckInCopy), new File(repoPath + "\\" + originalProjectName + "\\" + folderName));
                        }
                    }
                }
            }
        }
        
        FileUtils.deleteDirectory(new File(projectPath + "\\activity"));
        FileUtils.deleteDirectory(new File(projectPath + "\\" + originalProjectName)); //delete temp repo folder from user's project path
    }
