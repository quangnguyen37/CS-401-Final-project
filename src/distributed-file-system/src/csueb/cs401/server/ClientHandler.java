package csueb.cs401.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import csueb.cs401.common.Message;
import csueb.cs401.common.User;
import csueb.cs401.persist.EventLog;

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
					
					// ensure that client is authenticated before making requests
					if (!req.getType().equals(Message.Type.LOGIN) && !isAuthenticated) {
						Message noAuth = new Message(Message.Type.ERROR);
						noAuth.setDate(new Date().toString());
						noAuth.setMessage("User need to be authenticated before making requests.");
						noAuth.setStatus(Message.Status.FAILURE);
						objOutStream.writeObject(noAuth);
						
					}
					
					Service service = Server.getInstance().getService(req.getType());
					if (service != null) {
						try {
							int result = service.run(req, this);
							if (result != 0) {
								Message res = new Message(Message.Type.ERROR);
								res.setMessage("something went wrong");
								res.setDate(new Date().toString());
								res.setStatus(Message.Status.FAILURE);
								objOutStream.writeObject(res);
								Server.getInstance().getLogger().warning("Failure with service " + service.getClass().getSimpleName());
							}
							// log event
							EventLog el = Server.getInstance().getEventLog();
							el.addEvent(result == 0 ? "Request of type [" + req.getType().toString() + "] successfully handled" : "Request of type [\" + req.getType().toString() + \"] failed", user.getId());
						} catch (Exception e) {
							Server.getInstance().getLogger().warning(e.getLocalizedMessage());
							Message res = new Message(Message.Type.ERROR);
							res.setMessage("something went wrong");
							res.setDate(new Date().toString());
							res.setStatus(Message.Status.FAILURE);
							objOutStream.writeObject(res);
						}
					} else {
						Message res = new Message(Message.Type.ERROR);
						res.setMessage("invalid service");
						res.setDate(new Date().toString());
						res.setStatus(Message.Status.FAILURE);
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
				// clean up
				Server.getInstance().getLogger().info("Terminating connection with " + client.getInetAddress().getHostAddress());
				if(objInStream != null) objInStream.close();
				if (objOutStream != null) objOutStream.close();
				client.close();
				if (isAuthenticated) {
					Server.getInstance().getActiveClients().remove(user.getLoginUserName().concat(user.getLoginPassword()));
				}
			} catch (IOException e) {
				Server.getInstance().getLogger().severe(e.getLocalizedMessage());
			}
		}
		
	}
	
	public boolean isOpen() {
		return !client.isClosed();
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

	public synchronized ObjectOutputStream getObjOutStream() {
		return objOutStream;
	}

}
