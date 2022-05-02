package csueb.cs401.server;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import csueb.cs401.common.File;
import csueb.cs401.common.Message;

public class ServiceReadFileRequestTest {

	@Test
	public void test() {
		String fileName = "fileName";
		
		Server.getInstance().getFileDistribution().put(fileName, new ArrayList<>());
		
		Message m = new Message();
		File f = new File(fileName, null);
		f.setFileName(fileName);
		m.setPayload(f);
		
		ServiceReadFileRequest svc = new ServiceReadFileRequest();
		
		int tokens = Server.getInstance().getResolvedReadRequests().size();
		Assertions.assertEquals(0, svc.run(m, null));
		Assertions.assertEquals(tokens + 1, Server.getInstance().getResolvedReadRequests().size());
		
	}

}
