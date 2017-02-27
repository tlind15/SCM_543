# SCM_543

##(Project Name)

We are **Team LRH** and this project is a source code management system made from scratch. It is being done as a project for **CECS 543 Section 2** (Advanced Software Engineering) at CSU Long Beach. The current functionallity allows for the user to pass in a folder they would like to create a repo from and where they would like that repo to be located. Whenever the user creates a repo that will be logged to a manifest file located at the top level directory of their repo. We provide an easy to use GUI that prompts users for these inputs and displays manifest files to them.

**Authors**
   - Thomas Lindblom (tlindblomjr@gmail.com)
   - Jocelyn Ramirez(jsyramirez@gmail.com)
   - Yushen Huang(maple.yushen@gmail.com)

###Project Progress
   - Part 1: 'Create Repo' Completed 2-26-17

###External Requirements
   JRE 8

###Build, Installation, and Setup

##Using the Command Line (Mac OS, Linux, or Windows)

   1. Unzip folder to any location

   2. Open up terminal 

   3. Run commands

      cd PATH_TO_EXTRACTED_FOLDER/543-p1_LRH/src/com/LRH
      javac commons-io-2.5.jar Main_GUI.java Main.java RepoFile.java
      java Main_GUI
   
##Using an IDE

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

###Extra Features
   User friendly GUI that prompts users for inputs, allows them to create the repo with a single button click, and displays manifest        files.

###Known Bugs
  -The user must supply a valid location to copy from.
  -Assumes user gives all inputs to the GUI.

