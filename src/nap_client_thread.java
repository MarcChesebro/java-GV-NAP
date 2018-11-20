import java.net.ServerSocket;
import java.net.Socket;

public class nap_client_thread implements Runnable{


    public void run() {
        ServerSocket welcomeSocket;

        try {
            welcomeSocket = new ServerSocket(14000);
        }catch(Exception e){
            System.out.println("could not create ftp_server");
            return;
        }

        while (true) {
            //Wait for request at welcome socket
            try {
                Socket connectionSocket = welcomeSocket.accept();

                //create a new thread for the user

                ftp_thread userConnection = new ftp_thread(connectionSocket);
                Thread user_thread = new Thread(userConnection);

                // start the thread
                user_thread.start();
            }catch(Exception e){
                System.out.println("connection failed");
            }
        }
    }
}