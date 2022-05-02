package csueb.cs401.server;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ServiceSearchTest {

	@Test
	public void ShouldSearch() {
		ClientHandler ch = new ClientHandler(null);
		ServiceSearch svc = new ServiceSearch();
		Assertions.assertEquals(-1, svc.run(null, ch));
	}

}
