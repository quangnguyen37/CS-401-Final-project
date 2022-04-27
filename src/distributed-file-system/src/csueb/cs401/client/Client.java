import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int port;
    private String host;

    private void init() {
        port = 8080;
        host = "localhost";
    }

    public void start() {
        // Continue until 'logout' entered
        boolean cont = true;

        // initialze port and host variables
        init();

        // Continue until logout is entered
        while(cont) {

            try {
                socket = new Socket(host, port);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // Server is closed

    }

    public void sendObj() {

        oos = new ObjectOutputStream(socket.getOutputStream());

        // Send obj to server
        // Needs completion
        oos.writeObject(/*obj*/);

    }

    public void receiveObj() {

        ois = new ObjectInputStream(socket.getInputStream());

        // Receive object from server
        // Needs completion
        /*obj*/ = (Message) ois.readObject();

    }

}
