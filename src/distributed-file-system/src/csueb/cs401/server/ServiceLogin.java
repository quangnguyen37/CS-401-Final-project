package csueb.cs401.server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import csueb.cs401.common.LoginBody;
import csueb.cs401.common.Message;
import csueb.cs401.common.User;

/**
 * @author michaelvu
 * This class will receive a {@code Message} expecting a payload of type
 * {@code LoginBody} which requires
 * 		1. username
 * 		2. password
 * 
 * This class will return a {@code Message} with no payload. The message will contain
 * the status, message, and timestamp for the request.
 *
 * It will create an id based on the concatenation of the username and password.
 * This id will be the key index.
 * 
 * It will register a new user with the server if the credentials are not found
 * to existing map. Otherwise, it will use the server to retrieve the user details.
 * After either, the client handler is updated with user details and that client handler
 * is added to the map of active authenticated connections maintained in the server.
 * 

 */
public class ServiceLogin implements Service{

	@Override
	public int run(Message message, ClientHandler ref) {
		
		LoginBody req = (LoginBody) message.getPayload();
		String id = req.getUsername().concat(req.getPassword());
		HashMap<String, User> registeredUsers = Server.getInstance().getRegisteredUsers();
		if (registeredUsers.containsKey(id)) {
			// found valid credentials
			// update client handler with user info
			ref.setAuthenticated(true);
			ref.setUser(registeredUsers.get(id));
		} else {
			// create a new user account since not found
			// update server with new user
			User user = new User();
			user.setId(id);
			user.setLoginUserName(req.getUsername());
			user.setLoginPassword(req.getPassword());
			
			// update server
			registeredUsers.put(id, user);
			
			// update client handler
			ref.setAuthenticated(true);
			ref.setUser(registeredUsers.get(id));
		}
		
		Server.getInstance().getActiveClients().put(id, ref);
		
		Message res = new Message(Message.Type.LOGIN);
		res.setDate(new Date().toString());
		res.setMessage("You are now logged in");
		res.setStatus(Message.Status.SUCCESS);
		try {
			ref.getObjOutStream().writeObject(res);
		} catch (IOException e) {
			return -1;
		}
		
		return 0;
	}
	
}
