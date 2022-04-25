import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        boolean cont = true;

        String messageChoice;

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the port number to connect to: <7777> ");
        int port = sc.nextInt();

        System.out.print("Enter the host address to connect to: <localhost> ");
        String host = sc.next();

        // Continue sending Messages until logout msg is sent
        while(cont) {

            Socket socket;
            ObjectOutputStream oos;
            ObjectInputStream ois;

            System.out.print("\nWhat kind of message would you like to send: ");
            messageChoice = sc.next();

            sc.nextLine();

            Message msg = new Message();

            if(messageChoice.equalsIgnoreCase("login")) {

                 msg = new Message("login","","none");

            } else if(messageChoice.equalsIgnoreCase("text")) {

                 msg = new Message("text","","");

                 System.out.print("Please enter text to send: ");

                 String text = sc.nextLine();

                 msg.setText(text);

            } else if (messageChoice.equalsIgnoreCase("logout")) {

                 msg = new Message("logout","","none");

                 cont = false;

            }

            socket = new Socket(host, port);

            oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("\nSending " + msg.getType() + " message to Socket Server...");

            // Send msg to server
            oos.writeObject(msg);

            ois = new ObjectInputStream(socket.getInputStream());
            
            // Receive msg from server
            msg = (Message) ois.readObject();
            System.out.println("Message received from Server!\n" + printMessage(msg));

            ois.close();
            oos.close();

        }

        System.out.println("\nServer closed connection. Thank you!");

    }

    // Function to print Message attributes
    private static String printMessage(Message msg) {
        return "Type: " + msg.getType() + "\nStatus: " + msg.getStatus() + "\nText: " + msg.getText();
    }

}
