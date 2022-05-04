package csueb.cs401.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import csueb.cs401.common.Message;

class ClientMessageTypeTest {

	@Test
	void messageTypeTest() {
		Message msg = new Message(Message.Type.LOGIN);
		
		assertTrue(msg.getType().equals(Message.Type.LOGIN));
	}

}
