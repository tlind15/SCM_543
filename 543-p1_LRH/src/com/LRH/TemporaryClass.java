import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Arrays;
import java.lang.Integer;
import java.util.ArrayList;


public class TemporaryClass
{
	//Returns a list of all added files in the manifest. Call @ init + whenever repo created
	// or check in done!
	//Each row follows this indexing
	//0: file_name	
	//1: path_in_repo
	//2: artifact id	
	//3: File Location
	//4: Version
	//I would recommend storing the return value as a member variable
	public List<String []> parseManifestFile() {
		String file = getManifestFile().getAbsolutePath();
		//String file = "test.txt";
		List<String[]> filesList = new ArrayList<String[]>();

		try(FileReader fileReader = new FileReader(file))
		{
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.toLowerCase().contains("adding file...")) {
					System.out.println("Test");
 					String[] spt = line.split("\t");
 					filesList.add(new String[] {spt[2], spt[4], spt[6], spt[8], spt[10]});
				}
			}
			fileReader.close();
			for (String[] row : filesList) {
				System.out.println("Row = " + Arrays.toString(row));
			}
			//getLatestVersion(filesList);
			return filesList;
		}catch (IOException f) {System.out.println("Could not read manifest file");}
		return filesList;
	}
	//Pass in the list generated above to get the most up to date version.
	public int getLatestVersion(List<String []> filesList) {
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
	}

	public void writeToManifesto_RepoCreate(String repo_loc, String dir_cpy, String username, String manifestoFile)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new 
									OutputStreamWriter(new FileOutputStream(manifestoFile, true), StandardCharsets.UTF_8)))) {
			out.println("User:\t" + username + "\tCreated Repo at:\t" + repo_loc + "\t" + "Version:\t1");
		} catch (IOException e) {System.out.println("Could not write to Activity Logs");}
	   //exception handling left as an exercise for the reader
	}
	//check-out repofoldername emptyfolder version
	private void writeToManifesto_CheckOut(String repoFolderName, String checkOutFolder, String version)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
			out.println("Check-out from\tRepo Folder:\t" + repoFolderName + "\tto Folder:\t" + checkOutFolder
				+ "\tVersion:\t" + version);
		} catch (IOException e) {System.out.println("Could not write checked out file");}
		//exception handling left as an exercise for the reader
	}
	//check-in repofoldername checkoutfolder
	private void writeToManifesto_CheckIn(String repoFolderName, String checkOutFolder)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
			String version = getNewVersion();
			out.println("Check-In to\tRepo Folder:\t" + repoFolderName + "\tfrom Folder:\t" + checkOutFolder
				+ "\tVersion:\t" + version);
		} catch (IOException e) {System.out.println("Could not write checked in file");}
		//exception handling left as an exercise for the reader
	}
	//Function has been updated since project 1.
	private void writeToManifesto_FileAdded(File artifactFile, String original_file_name, String path_in_repo, String version)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getManifestFile().getAbsolutePath(), true), StandardCharsets.UTF_8)))) {
			out.println("Adding File...\tFile Name:\t" + original_file_name + "\tPath In Repo\t" + path_in_repo + "\tArtifact ID:\t" + 
			artifactFile.getName().substring(0, artifactFile.getName().lastIndexOf(".")) + "\tFile Location:\t" + artifactFile.getAbsolutePath()
			+ "\tVersion:\t" + version);
		}catch (IOException e) {System.out.println("Could not write added file");}
		//exception handling left as an exercise for the reader
	}
	public static void main(String args[])
	{
		TemporaryClass obj = new TemporaryClass();
		obj.parseManifestFile();
	}
}
