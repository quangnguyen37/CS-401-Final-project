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
 */
public class Server {

	private static Server me;
	
	private ServerSocket socket;
	private int port;
	
	private Logger LOGGER;
	
	private HashMap<String, ClientHandler> activeAuthClients = new HashMap<>(); // userid to user
	private HashMap<String, List<FileNode>> fileDistribution = new HashMap<>(); // file name to FileNode
	private Map<Message.Type, Service> services = Map.of(
			Message.Type.LOGIN, new ServiceLogin(),
			Message.Type.SEARCH, new ServiceSearch(),
			Message.Type.READ_FILE, new ServiceReadFile(),
			Message.Type.POST_FILE, new ServicePostFile()
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
		// init logger
		System.setProperty("java.util.logging.SimpleFormatter.format",
	              "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		LOGGER = Logger.getLogger(Server.class.getName());
	}
	
	public void start() {
		try {
			socket = new ServerSocket(port);
			LOGGER.info("Server now accepting requests on port " + port);
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
	
	public synchronized HashMap<String, ClientHandler> getActiveClients() {
		return activeAuthClients;
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
}
