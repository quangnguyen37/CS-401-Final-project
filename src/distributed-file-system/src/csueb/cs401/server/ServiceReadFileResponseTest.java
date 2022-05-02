package csueb.cs401.server;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import csueb.cs401.common.File;
import csueb.cs401.common.Message;

public class ServiceReadFileResponseTest {

	@Test
	public void ShouldReadFileResponse() {
		String tok = "aegsrhdtn";
		Server.getInstance().getResolvedReadRequests().put(tok, false);
		Message m = new Message();
		File f = new File("some name", null);
		f.setOwner("some name");
		f.setToken(tok);
		m.setPayload(f);
		ServiceReadFileResponse svc = new ServiceReadFileResponse();
		Assertions.assertEquals(-1, svc.run(m, null));
	}

}
