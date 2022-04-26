package csueb.cs401.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServiceSearchTest {

	@Test
	void ShouldSearch() {
		ClientHandler ch = new ClientHandler(null);
		ServiceSearch svc = new ServiceSearch();
		Assertions.assertEquals(-1, svc.run(null, ch));
	}

}
