package csueb.cs401.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import csueb.cs401.common.LoginBody;

class ClientLoginBodyTest {

	@Test
	void setUsernameTest() {
		LoginBody req = new LoginBody();
		req.setUsername("Test User");
		req.setPassword("Test password");
		
		assertTrue(req.getUsername().equals("Test User"));
	}
	
	@Test
	void setPasswordTest() {
		LoginBody req = new LoginBody();
		req.setUsername("Test User");
		req.setPassword("Test password");
		
		assertTrue(req.getPassword().equals("Test password"));
	}

}
