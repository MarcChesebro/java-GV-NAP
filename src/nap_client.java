import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class nap_client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("nap/sample.fxml"));
        primaryStage.setTitle("NAP client");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Spawn a thread that listens and creates threads with a connection.
        String sentence;
        String commands = "retr file.txt || quit";
//      while (true) {
//	sentence = "FIXME"; // insert textfield input
//	StringTokenizer tokens = new StringTokenizer(sentence);
//
//	if(sentence.startsWith("retrieve"))/*the connect is probable going to by the connect button*/{
//	   String serverName = tokens.nextToken();//pass the retrieve command
//	   serverName = tokens.nextToken(); //this would be the hostname textfield
//	   controlPort = Integer.parsseInt(tokens.nextToken());
//
//	   Socket ControlSocket = new Socket(serverName, controlPort);
//
//	   DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream());
//	   BufferedReader inFromServer = new BufferedReader(new InputStreamReader(ControlSocket.getInputStream()));
//	   System.out.println("Connected to " + serverName);
//
//	   int dataPort = controlPort + 2;
//
//	   outToServer.writeBytes(dataPort + " " + sentence + " " + '\n');
//
//	   ServerSocket welcomeData = new ServerSocket(dataPort);
//	   Socket dataSocket = welcomeData.accept();
//
//	   StringTokenizer user_tokens = new StringTokenizer(sentence);
//	   user_tokens.nextToken(); //skip retr command
//	   String filename = user_tokens.nextToken();
//
//	   String statusCode = inFromServer.readLine();
//	   if(!statusCode.startsWith("200")){
//	   	  //if it was not 200 return bad status code
//	     System.out.println(statusCode);
//	     welcomeData.close();
//	     dataSocket.close();
//	     sentence = inFromUser.readLine();
//	     continue;
//	   }
//	   BufferedReader inData = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
//	   BufferedWriter toFile = new BufferedWriter(new FileWriter(filename));
//	   String line = inData.readLine();
//	   while(line != null){
//	       toFile.write(line);
//	       toFile.newLine();
//	       line = inData.readLine();
//	   }
//	   toFile.close();
//	   welcomeData.close();
//	   dataSocket.close();
//	   System.out.println("Successfully downloaded " + filename );
//      	}
//      }
    }

    public void GoButton(String command) throws IOException{
        String sentence = command; // insert textfield input
        StringTokenizer tokens = new StringTokenizer(sentence);

        if (sentence.startsWith("retrieve"))/*the connect is probable going to by the connect button*/ {
            String serverName = tokens.nextToken();//pass the retrieve command
            serverName = tokens.nextToken(); //this would be the hostname textfield
            int controlPort = Integer.parseInt(tokens.nextToken());

            Socket ControlSocket = new Socket(serverName, controlPort);

            DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(ControlSocket.getInputStream()));
            System.out.println("Connected to " + serverName);

            int dataPort = controlPort + 2;

            outToServer.writeBytes(dataPort + " " + sentence + " " + '\n');

            ServerSocket welcomeData = new ServerSocket(dataPort);
            Socket dataSocket = welcomeData.accept();

            StringTokenizer user_tokens = new StringTokenizer(sentence);
            user_tokens.nextToken(); //skip retr command
            String filename = user_tokens.nextToken();

            String statusCode = inFromServer.readLine();
            if (!statusCode.startsWith("200")) {
                //if it was not 200 return bad status code
                System.out.println(statusCode);
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
            System.out.println("Successfully downloaded " + filename);
        }
    }

    /***************************************/
    //launch(args);
}
