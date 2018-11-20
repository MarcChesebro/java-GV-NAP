
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class nap_client {

    private Socket FtpControlSocket;
    DataOutputStream FtpoutToServer;
    BufferedReader FtpinFromServer;
    int FtpcontrolPort;

    public static void main(String[] args) {
        //Spawn a thread that listens and creates threads with a connection.
        String sentence;
        String commands = "retr file.txt || quit";
    }

    public void write_to_ftp_window(String str){
        // FIXME print to gui when implemented
    }

    public void GoButton(String command) throws IOException{
        String sentence = command; // insert textfield input
        StringTokenizer tokens = new StringTokenizer(sentence);

        if(sentence.startsWith("connect")){
            tokens.nextToken();
            String serverName = tokens.nextToken();
            FtpcontrolPort = Integer.parseInt(tokens.nextToken());

            Socket ControlSocket = new Socket(serverName, FtpcontrolPort);

            FtpoutToServer = new DataOutputStream(FtpControlSocket.getOutputStream());
            FtpinFromServer = new BufferedReader(new InputStreamReader(FtpControlSocket.getInputStream()));
            write_to_ftp_window("Connected to " + serverName);

        }else if (sentence.startsWith("retrieve"))/*the connect is probable going to by the connect button*/ {

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
            write_to_ftp_window("Successfully downloaded " + filename);
        }else if(sentence.startsWith("quit")){
            write_to_ftp_window("Exiting.....");
            FtpControlSocket.close();

        }
    }
}
