import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JFrame;

public class nap_client {

    // FTP instance variables
    private Socket FtpControlSocket;
    private DataOutputStream FtpoutToServer;
    private BufferedReader FtpinFromServer;
    private int FtpcontrolPort;

    // Server Connection variables
    private Socket serverSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    public static void main(String[] args) {
        //Spawn a thread that listens and creates threads with a connection.
        String sentence;
        String commands = "retr file.txt || quit";

        nap_client client = new nap_client();


        nap_client_thread ftp_server = new nap_client_thread();
        Thread client_ftp_server = new Thread(ftp_server);

        // start the thread
        client_ftp_server.start();
//	MainGUI gui = new MainGUI(this);
        //client.connect("Marc", "localhost", "fast");

    }

    public void write_to_ftp_window(String str) {
        // FIXME print to gui when implemented
        System.out.println(str);
    }

    public void connect(String username, String hostname, String connectionspeed) {

        try {
            if (serverSocket != null) {
                serverSocket.close();
            }

            serverSocket = new Socket(hostname, 12000);

            outToServer = new DataOutputStream(serverSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            String connectionString = "register: " + username + " " + serverSocket.getLocalAddress().toString() + " " + connectionspeed;

            //get files
            File folder = new File("./media");
            String[] files = folder.list();
            if (files != null) {
                for (String file : files) {
                    connectionString += " " + file + " " + file;
                }
            }

            //System.out.println(connectionString);
            connectionString += "\n";
            outToServer.writeBytes(connectionString);

        } catch (Exception e) {
            System.out.println("connection to server failed!" + e.toString());
        }
    }

    public ArrayList<String> searchBtn(String filter) throws IOException {
        System.out.println(filter);
	String sentence = "search: " + filter + "\n";
        outToServer.writeBytes(sentence);
        ArrayList<String> files = new ArrayList<String>();
        while (true) {
            String fromClient = inFromServer.readLine();
            if (fromClient == null) {
                continue;
            }
            while (!fromClient.equals("done")) {
                files.add(fromClient);
                fromClient = inFromServer.readLine();
                if (fromClient == null) {
                    continue;
                }
            }
            break;
        }
        return files;
    }

    public String ftpButton(String command) throws IOException{
        String sentence = command;
        StringTokenizer tokens = new StringTokenizer(sentence);
	System.out.println(command);
        if (sentence.startsWith("connect")) {
            tokens.nextToken();
            String serverName = tokens.nextToken();
            FtpcontrolPort = Integer.parseInt(tokens.nextToken());

            this.FtpControlSocket = new Socket(serverName, FtpcontrolPort);

            FtpoutToServer = new DataOutputStream(FtpControlSocket.getOutputStream());
            FtpinFromServer = new BufferedReader(new InputStreamReader(FtpControlSocket.getInputStream()));
            return ("Connected to " + serverName + "\n");
        }else if (sentence.startsWith("retr")){
	    
            int dataPort = FtpcontrolPort + 2;

            FtpoutToServer.writeBytes(dataPort + " " + sentence + " " + '\n');

            ServerSocket welcomeData = new ServerSocket(dataPort);
            Socket dataSocket = welcomeData.accept();

            StringTokenizer user_tokens = new StringTokenizer(sentence);
            user_tokens.nextToken(); //skip retr command
            String filename = user_tokens.nextToken();

            String statusCode = FtpinFromServer.readLine();
            if (!statusCode.startsWith("200")) {
                //if it was not 200 return bad status code
                write_to_ftp_window(statusCode);
                welcomeData.close();
                dataSocket.close();
            }
            BufferedReader inData = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            BufferedWriter toFile = new BufferedWriter(new FileWriter(filename));
            String line = inData.readLine();
            while (line != null) {
                toFile.write(line);
                toFile.newLine();
                line = inData.readLine();
            }
            toFile.close();
            welcomeData.close();
            dataSocket.close();
            return("Successfully downloaded " + filename + "\n");
        }else if(sentence.startsWith("quit")){
            FtpControlSocket.close();
            return("Exiting.....\n");

        }
	return "error in the command";
    }

    public void quit() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }

        } catch (Exception e) {
            System.out.println("connection to server failed!" + e.toString());
        }
    }
}
