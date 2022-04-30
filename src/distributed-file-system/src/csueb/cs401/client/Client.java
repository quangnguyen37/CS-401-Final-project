package csueb.cs401.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;

import csueb.cs401.common.Message;
import csueb.cs401.server.ClientHandler;
import csueb.cs401.server.Server;
import csueb.cs401.common.File;
import csueb.cs401.common.FileNode;

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
		
		try {
			socket = new Socket("localhost", port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			//LOGGER.info("Server now accepting requests on port " + port);
			//socket.setReuseAddress(true);
			

			
		} catch (IOException e) {
			LOGGER.warning(e.getLocalizedMessage());
		}
	}
    
    public void sendObj(Message msg) throws IOException {

        

        // Send obj to server
        // Needs completion
        System.out.println("sending msg");
        oos.writeObject(msg);

    }

    public void receiveObj(Message msg) throws IOException {

        

        // Receive object from server
        // Needs completion
        try {
			msg = (Message) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public void addFile() {
    	Message msg = new Message(Message.Type.POST_FILE);
    	File file = new File();
    	file.setContent(null);
    	file.setFileName(null);
    	
    	try {
			oos.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    public void postFileRequest() {
    	try {
			Message msg = (Message) ois.readObject();
			if(msg.getType().equals(Message.Type.POST_FILE)) {
				File file = (File) msg.getPayload();
				// persisted and everything fine
				
				// Update to get type
				Message postMsg = new Message(Message.Type.POST_FILE_RESPONSE);
				
				FileNode fn = new FileNode();
				fn.setOwner(file.getOwner());
				fn.setFileName(file.getFileName());
				
				// update repo
				postMsg.setPayload();
				
				oos.writeObject(postMsg);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
