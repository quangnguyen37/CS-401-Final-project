package csueb.cs401.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import csueb.cs401.common.LoginBody;
import csueb.cs401.common.Message;
import csueb.cs401.common.User;

class ServiceLoginTest {

	@Test
	void ShouldLoginUser() {
		// test values
		String validUSR = "usr";
		String validPWD = "pwd";
		String id = validUSR + validPWD;
		User user = new User();
		user.setId(id);
		user.setLoginPassword(validUSR);
		user.setLoginPassword(validPWD);
		
		// prepopulate server with a registered user
		Server.getInstance().getRegisteredUsers().put(id, user);
		
		// init runner
		ServiceLogin svc = new ServiceLogin();
		ClientHandler hdr = new ClientHandler(null);
		Message message = new Message(Message.Type.LOGIN);
		LoginBody body = new LoginBody();
		message.setPayload(body);
		body.setUsername(validUSR);
		body.setPassword(validPWD);
		
		int size = Server.getInstance().getRegisteredUsers().size();
		
		// run service
		svc.run(message, hdr);

		Assertions.assertTrue(hdr.isAuthenticated());
		Assertions.assertNotNull(hdr.getUser());
		Assertions.assertEquals(size, Server.getInstance().getRegisteredUsers().size());
	}
	
	@Test
	void ShouldCreateUser() {
		// init runner and test values
		ServiceLogin svc = new ServiceLogin();
		ClientHandler hdr = new ClientHandler(null);
		Message message = new Message(Message.Type.LOGIN);
		String validUSR = "123";
		String validPWD = "456";
		LoginBody user = new LoginBody();
		message.setPayload(user);
		user.setUsername(validUSR);
		user.setPassword(validPWD);
		
		// check that registeredUser increased
		int regSize = Server.getInstance().getRegisteredUsers().size();
		
		// run service
		svc.run(message, hdr);
		
		Assertions.assertTrue(hdr.isAuthenticated());
		Assertions.assertNotNull(hdr.getUser());
		Assertions.assertEquals(regSize + 1, Server.getInstance().getRegisteredUsers().size());
	}
	
	@Test
	void ShouldBePartOfActiveClients() {
		// test values
		String validUSR = "abc";
		String validPWD = "xyz";
		ServiceLogin svc = new ServiceLogin();
		ClientHandler hdr = new ClientHandler(null);
		Message message = new Message(Message.Type.LOGIN);
		LoginBody body = new LoginBody();
		message.setPayload(body);
		body.setUsername(validUSR);
		body.setPassword(validPWD);
		
		int clientsSize = Server.getInstance().getActiveClients().size();

		// run service
		svc.run(message, hdr);
		
		Assertions.assertEquals(clientsSize + 1, Server.getInstance().getActiveClients().size());
		
	}

}
