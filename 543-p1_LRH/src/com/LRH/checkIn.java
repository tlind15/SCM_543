public static void checkIn(String srcpath, String destpath) throws Exception {

        for (String folderName : getAllNames(srcpath)) {   //get all folder names from srcpath
            File dest = new File(destpath + "\\" + folderName);

            if (!folderName.equals("activity")) {  //travese all folders except manifest folder if exists
                for (String individualFile : getAllNames(srcpath + "\\" + folderName)) {  //get all file names from srcpath and check individual
                    File src = new File(srcpath + "\\" + folderName + "\\" + individualFile);

                    for (String repoIndividualFile : getAllNames(destpath + "\\" + folderName)) {  //compare project file artifactID with the one in repo

                        if (individualFile != repoIndividualFile) {

                            if (src.isDirectory()) {
                                FileUtils.copyDirectoryToDirectory (src, dest);
                                //call function to write manifest for both adding file action and version number update
                            }
                            else {
                                FileUtils.copyFileToDirectory (src, dest);
                                //call function to write manifest for both adding file action and version number update
                            }

                        }
                    }
                }
            }


        }
    }

    public static ArrayList<String> getAllNames(String tgtDirectory) {
            File folder = new File(tgtDirectory);
            File[] listAllFiles = folder.listFiles();
            ArrayList<String> fileNames = new ArrayList<String>();
            for (File eachFile : listAllFiles) {
                fileNames.add(eachFile.getName());
            }
            return fileNames;
    }
