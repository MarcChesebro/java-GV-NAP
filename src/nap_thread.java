import java.net.*;
import java.io.*;
import java.util.*;

final class nap_thread implements Runnable {
    private static int threadCount;
    private int user;
    private Socket connection;
    private String statusOk = "200 OK\n";
    private String statusMissing = "550 File Not Found\n";
    
    // Constructor
    // pass the control connection socket in
    public nap_thread(Socket socket) throws Exception {
        this.connection = socket;
    }

    // this runs on start after the thread has been setup
    public void run() {
        try {
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

        	String clientCommand = tokens.nextToken();
		if (clientCommand.equals("search")) { 
                	String searchKey = tokens.nextToken();
			if (searchKey == null) searchKey = "";
			//FILTER FILES

                	outToClient.writeBytes(statusOk);
			String filename = "";
			//SEND Filtered XML FILE
                	BufferedReader fileOut = new BufferedReader(new FileReader("./media/" + filename));
                	String line = fileOut.readLine();

                	while(line != null){
                    		outToClient.writeBytes(line + "\n");
                    		line = fileOut.readLine();
                	}
			outToClient.flush();
			//outToClient.close();
			fileOut.close();
       		} else if (clientCommand.equals("register")) {
			Socket dataSocket = new Socket(connection.getInetAddress(), port);
                	DataOutputStream dataOutToClient = new DataOutputStream(dataSocket.getOutputStream());

                	outToClient.writeBytes(statusOk);

                	String filename = tokens.nextToken();

                	BufferedReader inData = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
                	String line = inData.readLine();
                	while(line != "END"){
			  //  CREATE XML OBJECT BASED ON INFO IN LINE
                    		line = inData.readLine();
                	}
			dataOutToClient.close();
			inData.close();
                	//toFile.close();
                	dataSocket.close();			    
			}


		}
    }
}
