package csueb.cs401.server;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import csueb.cs401.common.Message;
import csueb.cs401.common.User;

public class ClientHandler implements Runnable{

	private Socket client;
	private boolean isAuthenticated;
	private User user;
	
	private ObjectInputStream objInStream = null;
	private ObjectOutputStream objOutStream = null;
	
	public ClientHandler(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			// Create input stream for message object
			objInStream = new ObjectInputStream(client.getInputStream());
			// Create output stream to talk to client
			objOutStream = new ObjectOutputStream(client.getOutputStream());
			
			// infinitely loop to process client requests
			while(true) {
				
				try {
					Message req = (Message) objInStream.readObject();
					Server.getInstance().getLogger().info("Received message from client " + client.getInetAddress().getHostAddress());
					
					// handle log out request
					if (req.getType().equals(Message.Type.LOGOUT)) {
						if (isAuthenticated) {
							Server.getInstance().getActiveClients().remove(user.getId());
						}
						break;
					}
					
					Service service = Server.getInstance().getService(req.getType());
					if (service != null) {
						try {
							int result = service.run(req, this);
							if (result != 0) {
								Server.getInstance().getLogger().warning("Failure with service " + service.getClass().getSimpleName());
							}
						} catch (Exception e) {
							Server.getInstance().getLogger().warning(e.getLocalizedMessage());
						}
					} else {
						Message res = new Message(Message.Type.ERROR);
						res.setMessage("invalid service");
						objOutStream.writeObject(res);
					}
					
				} catch (ClassNotFoundException e) {
					Server.getInstance().getLogger().severe(e.getLocalizedMessage());
				}
			}
			
		} catch (IOException e) {
			Server.getInstance().getLogger().severe(e.getLocalizedMessage());
		} finally {
			try {
				Server.getInstance().getLogger().info("Terminating connection with " + client.getInetAddress().getHostAddress());
				if(objInStream != null) objInStream.close();
				if (objOutStream != null) objOutStream.close();
				client.close();
			} catch (IOException e) {
				Server.getInstance().getLogger().severe(e.getLocalizedMessage());
			}
		}
		
	}
	
	/*
	 * Getters and Setters
	 */
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ObjectInputStream getObjInStream() {
		return objInStream;
	}

	public ObjectOutputStream getObjOutStream() {
		return objOutStream;
	}

}
