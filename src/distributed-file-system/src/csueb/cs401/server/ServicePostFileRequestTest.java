package csueb.cs401.server;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import csueb.cs401.common.File;
import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;

class ServicePostFileRequestTest {

	@Test
	void ShouldPostFileRequest() {
		String existingFile = "existing";
		HashMap<String, List<FileNode>> fileDistribution = Server.getInstance().getFileDistribution();
		fileDistribution.put(existingFile, null);
		Message message = new Message();
		File f = new File("temp", "\u00e0\u004f\u0030\u009d".getBytes());
		f.setFileName(existingFile);
		message.setPayload(f);
		ServicePostFileRequest svc = new ServicePostFileRequest();
		ClientHandler ref = new ClientHandler(null);
		Assertions.assertEquals(-1, svc.run(message, null));
	}

}
