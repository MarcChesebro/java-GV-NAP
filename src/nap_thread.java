import java.net.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

final class nap_thread implements Runnable {
    private static int threadCount;
    private int user;
    private Socket connection;
    private String statusOk = "200 OK\n";
    private String statusMissing = "550 File Not Found\n";
    private ArrayList<nap_user> users;
    // Constructor
    // pass the control connection socket in
    public nap_thread(Socket socket) throws Exception {
        this.connection = socket;
	this.users = new ArrayList<nap_user>();
    }

    // this runs on start after the thread has been setup
    public void run() {
        try {
	    readXML();
            processCommand();
        } catch (Exception e) {}
        System.out.println("Client has disconnected!");
    }    
    
    // this will watch the control connection and execute the commands
    private void processCommand() throws Exception {
        // wrap input and output in buffered streams
        DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	user = threadCount;
	System.out.println("Client" + threadCount++ + " has connected!");
        // read input from user
	while (true) {
		String fromClient = inFromClient.readLine();
		if (fromClient == null) {
			break;
		}
        	StringTokenizer tokens = new StringTokenizer(fromClient);
       		String frstln = tokens.nextToken();
      	 	int port = Integer.parseInt(frstln);
		System.out.println("should be port number: " + port);
        	String clientCommand = tokens.nextToken();
		if (clientCommand.equals("search")) { 
                	String searchKey = tokens.nextToken();
			if (searchKey == null) searchKey = "";
	//		ArrayList<String> output = getDisplayedFiles(searchKey);
                	outToClient.writeBytes(statusOk);
                	//BufferedReader fileOut = new BufferedReader(new FileReader("./media/" + filename));
                	//String line = fileOut.readLine();
	//		for (Iterator<String> i = someIterable.iterator(); i.hasNext();) {
	//			outToClient.writeBytes(i + "\n");
	//		}
			outToClient.flush();
			//outToClient.close();
			fileOut.close();
       		} else if (clientCommand.equals("register:")) {
				
			String username = tokens.nextToken();
			String hostname = tokens.nextToken();
			String connectSpeed = tokens.nextToken();
			nap_user newUser = new nap_user(username, hostname, connectSpeed);
			String filename;
			String description;
			while (tokens.hasMoreTokens()) {
				filename = tokens.nextToken();
				description = tokens.nextToken();
				newUser.addFile(filename, description);
				//register this file as XML?
			}
			//saveXML();
                	outToClient.writeBytes(statusOk);
		}
	}
    }

    //This should be displayed in the GUI
    public void readXML() {
    	ArrayList<String> displayFiles = new ArrayList<String>();
	Document dom;
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	try {
		DocumentBuilder db = dbf.newDocumentBuilder();

		dom = db.parse("UserAndFiles.xml");
		NodeList nodeList = dom.getElementsByTagName("*");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (node.getNodeName() == "filename") {
					String filename = node.getNodeValue();
					String description = node.getFirstChild().getNodeValue();
					Node parent = node.getParentNode();
					String hostName = parent.getNodeValue();
					String speed = parent.getFirstChild().getNodeValue();
		//			if (filename.contains(filter) || description.contains(filter)) {
						displayFiles.append(hostName + '\t' + filename + '\t' + description + '\t' + speed);
	//				}
				}
			}
		}
	} catch (ParserConfigurationException pce) {
		System.out.println(pce.getMessage());
	} catch (SAXException se) {
		System.out.println(se.getMessage());
	} catch (IOException ioe) {
		System.err.println(ioe.getMessage());
	}
	//return displayFiles;
    }
}
