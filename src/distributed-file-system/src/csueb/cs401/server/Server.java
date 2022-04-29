package csueb.cs401.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;
import csueb.cs401.common.User;
import csueb.cs401.persist.EventLog;

/**
 * @author michaelvu
 * This class implements a server socket. It will check environment variables for
 * a port number. 
 * 
 * On start, the class will infinite loop, accepting client requests,
 * creating a {@code ClientHandler} and new thread to process the client messages.
 * 
 * The class holds a real time listing of active authenticated clients.
 * 
 * The class holds a mapped distribution of file names with the associated nodes that
 * hold the files.
 * 
 * The class holds a map of generated read file request tokens to the resolve state 
 * of its response.
 */
public class Server {

	private static Server me;
	
	private ServerSocket socket;
	private int port;
	private int redundancyTarget; // target # of nodes that server will try to push file to
	
	private Logger LOGGER;
	private EventLog eventLog;
	
	private HashMap<String, User> registeredUsers = new HashMap<>();
	private HashMap<String, ClientHandler> activeAuthClients = new HashMap<>(); // userid to user
	private HashMap<String, List<FileNode>> fileDistribution = new HashMap<>(); // file name to FileNode
	private HashMap<String, Boolean> resolvedReadRequests = new HashMap<>(); // tokens to resolve state
	private Map<Message.Type, Service> services = Map.of(
			Message.Type.LOGIN, new ServiceLogin(),
			Message.Type.SEARCH, new ServiceSearch(),
			Message.Type.READ_FILE_REQUEST, new ServiceReadFileRequest(),
			Message.Type.READ_FILE_RESPONSE, new ServiceReadFileResponse(),
			Message.Type.POST_FILE_REQUEST, new ServicePostFileRequest(),
			Message.Type.POST_FILE_RESPONSE, new ServicePostFileResponse(),
			Message.Type.LOGS, new ServiceGetLogs()
		);
	
	private Server() {}
	
	public static Server getInstance() {
		if (me == null) {
			me = new Server();
			me.init();
		}
		return me;
	}
	
	private void init() {
		// init port
		if (System.getenv("port") != null) {
			port = Integer.valueOf(System.getenv("port"));
		} else {
			port = 8080;
		}
		// init redundancies
		if (System.getenv("redundancies") != null) {
			redundancyTarget = Integer.valueOf(System.getenv("redundancies"));
		} else {
			redundancyTarget = 3;
		}
		// init event log
		eventLog = new EventLog();
		// init logger
		System.setProperty("java.util.logging.SimpleFormatter.format",
	              "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		LOGGER = Logger.getLogger(Server.class.getName());
	}
	
	public void start() {
		try {
			socket = new ServerSocket(port);
			LOGGER.info("Server now accepting requests on port " + port);
			LOGGER.info("Redundancy target: " + redundancyTarget);
			socket.setReuseAddress(true);
			
			// infinite loop for getting client requests
			while (true) {
				Socket client = socket.accept();
				
				LOGGER.info("Received client on " + client.getInetAddress().getHostAddress());
				
				ClientHandler handler = new ClientHandler(client);
				
				new Thread(handler);
			}
		} catch (IOException e) {
			LOGGER.warning(e.getLocalizedMessage());
		}
	}
	
	public synchronized HashMap<String, User> getRegisteredUsers() {
		return registeredUsers;
	}
	public synchronized HashMap<String, ClientHandler> getActiveClients() {
		return activeAuthClients;
	}
	public synchronized HashMap<String, Boolean> getResolvedReadRequests() {
		return resolvedReadRequests;
	}
	public synchronized HashMap<String, List<FileNode>> getFileDistribution() {
		return fileDistribution;
	}
	public Logger getLogger() {
		return LOGGER;
	}
	public Service getService(Message.Type type) {
		return services.get(type);
	}
	public int getRedundancyTarget() {
		return redundancyTarget;
	}

	public EventLog getEventLog() {
		return eventLog;
	}
}
