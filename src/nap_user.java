import java.io.*;
import java.util.*;
public class nap_user {
	public String username;
	public String hostname;
	public String speed;
	public ArrayList<String> filesAndDesc;
	public nap_user(String username, String hostname, String speed) {
		this.username = username;
		this.hostname = hostname;
		this.speed = speed;
		this.filesAndDesc = new ArrayList<String>();
	}
	public void addFile(String name, String description) {
		filesAndDesc.add((name+" "+description));
	}
}
