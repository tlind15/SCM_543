# SCM_543

##Branch

We are **Team LRH** and this project is a source code management system made from scratch. It is being done as a project for **CECS 543 Section 2** (Advanced Software Engineering) at CSU Long Beach. The current functionality allows for the user to create a new repository. The user must provide a source project tree and a repository destination directory. Whenever the user creates a repository, it will be logged to a manifest file located at the top level directory of the repository. We provide a GUI that prompts the user for these inputs and can display the manifest file entries.

**Authors**
   - Thomas Lindblom (tlindblomjr@gmail.com)
   - Jocelyn Ramirez(jsyramirez@gmail.com)
   - Yushen Huang(maple.yushen@gmail.com)

###Project Progress
   - Project Part 1: 'Create Repo' Completed 2-26-17

###External Requirements
   -JRE 8
   -Can be downloaded here: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

###Build, Installation, and Setup

####Using the Command Line (Mac OS, Linux, or Windows)

   1. Unzip folder to any location

   2. Open up terminal 

   3. Run commands

      cd PATH_TO_EXTRACTED_FOLDER/SCM_543-master/543-p1_LRH/src/com/LRH
      javac -classpath PATH_TO_EXTRACTED_FOLDER/SCM_543-master/commons-io-2.5.jar -d PATH_TO_EXTRACTED_FOLDER Main_GUI.java Main.java RepoFile.java
      java -cp .;PATH_TO_EXTRACTED_FOLDER/ com.LRH.Main
      
      Note: For PATH_TO_EXTRACTED_FOLDER (enter the extracted folder and use that path)
   
####Using an IDE

   1. Add Main_GUI.java, Repo.java, Main.java to your project
    
   2. Change the package name of the added files to be the same as your package 

   3. Add commons-io-2.5.jar to your porject libraries

   4. Run Main.java

 
###Usage

To create repo:

   1. Run GUI

   2. Enter the Username, the location of the files you would like to copy, and where the repo will be located.

   3. Then hit 'create'.

To view activity logs

   1. Run GUI

   2. Click View, go to Activity Logs and a display will appear.
   
   3. Note: Activity logs will not appear if a repository has not yet been created. 

###Extra Features
   User friendly GUI that prompts users for inputs, allows them to create the repo with a single button click.
   GUI also has a tab for showing the manifest entries

###Known Bugs
  -The user must supply a valid location to copy from.
  -The directory where the repository is to be created must be valid and already exist.
  -Assumes user gives all inputs to the GUI (does not leave any fields blank).

