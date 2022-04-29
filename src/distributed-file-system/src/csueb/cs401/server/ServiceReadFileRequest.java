package csueb.cs401.server;

import java.io.IOException;
import java.util.UUID;

import csueb.cs401.common.File;
import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;
import csueb.cs401.common.Message.Type;

/**
 * @author michaelvu
 * This class will receive a {@code Message} expecting a payload of type
 * {@code File} which only needs the file name field.
 * 
 * It will create an asynchronous request to all the nodes registered with the requested file.
 * 
 * It will generate a token that is recorded onto the server and passed to all requests to the node
 * to ensure that the original requester of the file only receives one response.
 */
public class ServiceReadFileRequest implements Service {

	@Override
	public int run(Message message, ClientHandler ref) {
		if (message.getPayload() != null
				&& message.getPayload() instanceof File
				&& ((File) message.getPayload()).getFileName() != null) {
			String fileName = ((File) message.getPayload()).getFileName();
			if (Server.getInstance().getFileDistribution().containsKey(fileName) ) {
				// update server with token requests
				String token = UUID.randomUUID().toString();
				Server.getInstance().getResolvedReadRequests().put(token, false);
				
				// iterate over nodes that are 
				// 1. currently connected to the server
				// 2. registered as a node that persisted the requested file
				for (FileNode fn: Server.getInstance().getFileDistribution().get(fileName)) {
					if (Server.getInstance().getActiveClients().containsKey(fn.getNodeOwner())) {
						
						ClientHandler ch = Server.getInstance().getActiveClients().get(fn.getNodeOwner());
						Message req = new Message(Type.READ_FILE_REQUEST);
						
						File f = new File(fileName, null);
						f.setToken(token);
						f.setOwner(fn.getOwner());
						
						req.setPayload(f);
						
						try {
							ch.getObjOutStream().writeObject(req);
						} catch (IOException e) {
							return -1;
						}
					}
				}
			} else {
				return -1;
			}
		} else {
			return -1;
		}
		return 0;
	}

}
