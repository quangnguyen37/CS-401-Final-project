package csueb.cs401.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;
import csueb.cs401.common.SearchBody;

/**
 * @author michaelvu
 * This class will write a {@code Message} to the socket output stream
 * with the list of all files uploaded to the system.
 */
public class ServiceSearch implements Service {

	@Override
	public int run(Message message, ClientHandler ref) {
		Message res = new Message(Message.Type.SEARCH);
		SearchBody payload = new SearchBody();
		res.setPayload(payload);
		payload.setNodes(new ArrayList<>());
		for(List<FileNode> fileNodes: Server.getInstance().getFileDistribution().values()) {
			if (!fileNodes.isEmpty()) 
				payload.getNodes().add(fileNodes.get(0));
		}
		try {
			message.setStatus(Message.Status.SUCCESS);
			message.setDate(new Date().toString());
			message.setMessage("Successfully returning the list of files uploaded to the system.");
			ref.getObjOutStream().writeObject(res);
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

}
