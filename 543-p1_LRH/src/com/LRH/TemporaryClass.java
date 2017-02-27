//Jocelyn Ramirez - jsyramirez@gmail.com
//Includes method to write to activity logs when a repo is made.

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.nio.charset.StandardCharsets;

public class TemporaryClass
{
	//whenever a repo is created write to the activity logs 'who, what, when'
	public void writeToManifesto_RepoCreate(String repo_loc, String dir_cpy, String username, String manifestoFile)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(manifestoFile, true), StandardCharsets.UTF_8)))) {
    		out.println("User: " + username + " Created Repo at: " + repo_loc + " Created Repo From: " + dir_cpy + " - " + timestamp);
		} catch (IOException e) {System.out.println("Could not write to Activity Logs");}
	}
	public void writeToManifesto_FileAdded(String artifactID, String file_name, String file_path, String manifestoFile)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(manifestoFile, true), StandardCharsets.UTF_8)))) {
    		out.println("Adding File...\nFile Name: " + file_name + " Artifact ID: " + artifactID + " File Location: " + file_path);
		} catch (IOException e) {System.out.println("Could not write added file");}
	    //exception handling left as an exercise for the reader
	}
}
