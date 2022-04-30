package csueb.cs401.client;

//import java.awt.Event;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import csueb.cs401.common.Message;
import csueb.cs401.persist.EventLog;
import csueb.cs401.common.Event;
import csueb.cs401.server.ClientHandler;
import csueb.cs401.server.Server;
import csueb.cs401.common.File;
import csueb.cs401.common.FileNode;
import csueb.cs401.common.LoginBody;

public class Client extends GUI {

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
			
		} catch (IOException e) {
			LOGGER.warning(e.getLocalizedMessage());
		}
	}

	public static class commands {
		// Post File Services

		public static void addFile() {
			Message msg = new Message(Message.Type.POST_FILE_REQUEST);
			File file = new File(null, null);
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
				if (msg.getType().equals(Message.Type.POST_FILE_RESPONSE)) {
					File file = (File) msg.getPayload();
					// persisted and everything fine

					// Update to get type
					Message postMsg = new Message(Message.Type.POST_FILE_RESPONSE);

					FileNode fn = new FileNode();
					fn.setOwner(file.getOwner());
					fn.setFileName(file.getFileName());

					// update repo
					postMsg.setPayload(fn);

					oos.writeObject(postMsg);
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}

		// Read File Services
		public static void readFile() {

			Message msg = new Message(Message.Type.READ_FILE_REQUEST);

			File file = new File(null, null);
			file.setContent(null);
			file.setFileName(null);

			try {
				oos.writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void readFileRequest() {
			try {

				Message msg = (Message) ois.readObject();
				if (msg.getType().equals(Message.Type.READ_FILE_RESPONSE)) {
					File file = (File) msg.getPayload();

					FileNode fn = new FileNode();
					fn.setOwner(file.getOwner());
					fn.setFileName(file.getFileName());

				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}

		public static void clientLogin() {
			try {
				Message msg = (Message) ois.readObject();
				if (msg.getType().equals(Message.Type.LOGIN)) {

					LoginBody req = (LoginBody) msg.getPayload();
					req.setUsername(txuser.getText());
					req.setPassword(pass.getText());
					
					msg.setPayload(req);
					
					oos.writeObject(msg);
					
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}

		public EventLog eventLog() {
		    EventLog eventLog = new EventLog();
			Message msg = new Message(Message.Type.LOGS);

			// Request logs
			try {
				msg.getObjOutStream().writeObject(msg);
			} catch (IOException e) {
				return -1;
			}

			// Receive response
			Message message = new Message();
			message.getObjInStream().readObject();
			if (message.getPayload() != null && message.Status == SUCCESS) {
				eventLog = message.getPayload().getEventLog();
			}

	        return eventLog;
		}

		public static void saveAndExit() {
			EventLog eLog = new EventLog();
			eLog.eventLog();
			eLog.Save();
			System.exit(0);
		}
	}
}

