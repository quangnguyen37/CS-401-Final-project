package csueb.cs401.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;

import csueb.cs401.common.Message;
import csueb.cs401.server.ClientHandler;
import csueb.cs401.server.Server;

public class Client {

	private static Client me;
	
	private Logger LOGGER;
	
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int port;
    
private Client() {}
	
	public static Client getInstance() {
		if (me == null) {
			me = new Client();
			me.init();
		}
		return me;
	}
	
	private void init() {
		
		// Testing
		System.out.println("PORT INITIALIZED");
		
		// init port
		if (System.getenv("port") != null) {
			port = Integer.valueOf(System.getenv("port"));
		} else {
			port = 8080;
		}
		// init logger
		System.setProperty("java.util.logging.SimpleFormatter.format",
	              "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		LOGGER = Logger.getLogger(Client.class.getName());
	}
	
	public void start() {
		
		// Testing
		System.out.println("START RUNNING");
		
		Message msg = new Message();
		try {
			socket = new Socket("localhost", port);
			//LOGGER.info("Server now accepting requests on port " + port);
			//socket.setReuseAddress(true);
			
			// infinite loop for getting client requests
			while (true) {
				
				sendObj(msg);

			}
		} catch (IOException e) {
			LOGGER.warning(e.getLocalizedMessage());
		}
	}
    
    public void sendObj(Message msg) throws IOException {

        oos = new ObjectOutputStream(socket.getOutputStream());

        // Send obj to server
        // Needs completion
        System.out.println("sending msg");
        oos.writeObject(msg);

    }

    public void receiveObj(Message msg) throws IOException {

        ois = new ObjectInputStream(socket.getInputStream());

        // Receive object from server
        // Needs completion
        try {
			msg = (Message) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
