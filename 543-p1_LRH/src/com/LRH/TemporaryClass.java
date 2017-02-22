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
	
	public void writeToManifesto_RepoCreate(String repo_loc, String dir_cpy, String username, String manifestoFile)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(manifestoFile, true), StandardCharsets.UTF_8)))) {
    		out.println("User: " + username + " Created Repo at: " + repo_loc + " - " + timestamp);
		} catch (IOException e) {}
	    //exception handling left as an exercise for the reader
	}
}
