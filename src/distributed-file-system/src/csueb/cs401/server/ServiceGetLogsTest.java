package csueb.cs401.server;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import csueb.cs401.common.LogQuery;
import csueb.cs401.common.Message;
import csueb.cs401.common.User;

public class ServiceGetLogsTest {

	@Test
	public void ShouldGetLogs() {
		LogQuery req = new LogQuery();
		Message m = new Message();
		m.setPayload(req);
		ClientHandler ch = new ClientHandler(null);
		User user = new User();
		user.setAdmin(true);
		ch.setUser(user);
		ServiceGetLogs svc = new ServiceGetLogs();
		Assertions.assertEquals(-1, svc.run(m, ch));
	}

}
