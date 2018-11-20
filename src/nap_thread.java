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
    public ArrayList<nap_user> users;

    // Constructor
    // pass the control connection socket in
    public nap_thread(Socket socket) throws Exception {
        this.connection = socket;
        this.users = new ArrayList<nap_user>();
    }

    // this runs on start after the thread has been setup
    public void run() {
        try {
            read();
            processCommand();
        } catch (Exception e) {
        }
        System.out.println("Client has disconnected!");
    }

    // this will watch the control connection and execute the commands
    private void processCommand() throws Exception {
        // wrap input and output in buffered streams
        DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        user = threadCount;
        System.out.println("Client" + threadCount++ + " has connected!!!!1!");
        // read input from user
        while (true) {
	    //System.out.println("waiting");
            String fromClient = inFromClient.readLine();
            if (fromClient == null) {
                continue;
            }
	    //System.out.println("through");
            StringTokenizer tokens = new StringTokenizer(fromClient);
            //String frstln = tokens.nextToken();
            //int port = Integer.parseInt(frstln);
            //System.out.println(frstln);
            String clientCommand = tokens.nextToken();
            //System.out.println(clientCommand);
	    if (clientCommand.equals("search:")) {
                read();
                String searchKey = tokens.nextToken();
                if (searchKey == null) searchKey = "";
                ArrayList<String> output = findFiles(searchKey);
                for (int i = 0; i < output.size(); i++) {
                    outToClient.writeBytes(output.get(i) + "\n");
                }
		outToClient.writeBytes("done");
                outToClient.writeBytes(statusOk);
                outToClient.flush();
            } else if (clientCommand.equals("register:")) {
		
                String username = tokens.nextToken();
                String hostname = tokens.nextToken();
                String connectSpeed = tokens.nextToken();
		System.out.println(username + " " + hostname + " " + connectSpeed);
                nap_user newUser = new nap_user(username, hostname, connectSpeed);
                String filename;
                String description;
                while (tokens.hasMoreTokens()) {
                    filename = tokens.nextToken();
                    description = tokens.nextToken();
                    newUser.addFile(filename, description);
                }
		users.add(newUser);
                write();
                outToClient.writeBytes(statusOk);
            } else if (clientCommand.equals("quit")) {
                break;
            }
        }
        connection.close();
    }

    public void write() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data.ser", true);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(fileOut));
            for (int i = 0; i < users.size(); i++) {
                out.writeObject(users.get(i));
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("error in writing users");
        }
    }

    public void read() {
        users = new ArrayList<nap_user>();
        int count = 0;
        FileInputStream saveFile;
        try {
            saveFile = new FileInputStream("data.ser");
            try {
                ObjectInputStream save = new ObjectInputStream(new BufferedInputStream(saveFile));
                for (; ; ) {
                    users.add((nap_user) save.readObject());
                    count++;
                }
            } catch (Exception e) {

            } finally {
                saveFile.close();
            }
        } catch (EOFException e) {
            System.out.println("exception in write");
        } catch (Exception exc) {
            //System.out.println("exception in write");
        }
    }

    public ArrayList<String> findFiles(String text) {
        ArrayList<String> retList = new ArrayList<String>();
        for (int i = 0; i < users.size(); i++) {
            nap_user user = users.get(i);
            for (int j = 0; j < user.filesAndDesc.size(); j++) {
                if (user.filesAndDesc.get(j).contains(text)) {
                    retList.add(user.username + " " + user.hostname + " " + user.speed + " " + user.filesAndDesc.get(j));
                }
            }
        }
        return retList;
    }

    //THIS IS A TEST ONLY!!!!!
    public static void main(String[] args) {
        try {
		/*
            nap_thread n = new nap_thread(null);
            n.users.add(new nap_user("a", "b", "c"));
            n.users.get(0).addFile("asdf", "asdf");
            n.users.get(0).addFile("asd", "asd");
            n.users.get(0).addFile("as", "as");
            n.users.get(0).addFile("a", "a");
            n.users.add(new nap_user("d", "e", "f"));
            n.users.get(1).addFile("ghjk", "ghjk");
            n.users.get(1).addFile("ghj", "ghj");
            n.users.get(1).addFile("gh", "gh");
            n.users.get(1).addFile("g", "g");
            n.write();*/
            nap_thread q = new nap_thread(null);
            q.read();
            System.out.println(q.users.size());
            for (int i = 0; i < q.users.size(); i++) {
                nap_user u = q.users.get(i);
                System.out.println(u.username + " " + u.hostname + " " + u.speed);
                for (int j = 0; j < u.filesAndDesc.size(); j++) {
                    System.out.println(u.filesAndDesc.get(j));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
