package csueb.cs401.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;
import csueb.cs401.common.User;

class ServicePostFileResponseTest {

	@Test
	void ShouldPostFileResponse() {
		String existingFile = "existing";
		HashMap<String, List<FileNode>> files = Server.getInstance().getFileDistribution();
		files.put(existingFile, new ArrayList<>());
		Assertions.assertEquals(0, files.get(existingFile).size());
		Message req = new Message();
		FileNode fn = new FileNode();
		req.setPayload(fn);
		fn.setFileName(existingFile);
		fn.setOwner("owner");
		ServicePostFileResponse svc = new ServicePostFileResponse();
		ClientHandler ch = new ClientHandler(null);
		User user = new User();
		ch.setUser(user);
		user.setId("someid");
		ch.setUser(user);
		Assertions.assertEquals(0, svc.run(req, ch));
		Assertions.assertEquals(1, files.get(existingFile).size());
	}

}
