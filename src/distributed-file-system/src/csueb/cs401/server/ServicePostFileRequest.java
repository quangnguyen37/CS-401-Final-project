package csueb.cs401.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csueb.cs401.common.File;
import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;
import csueb.cs401.common.Message.Status;
import csueb.cs401.common.Message.Type;

/**
 * @author michaelvu
 * This class will receive a {@code Message} expecting a payload of type
 * {@code File}. 
 * 
 * This class will return a {@code Message} with either type of Message.Type.POST_FILE_REQUEST or Message.Type.POST_FILE_RESPONSE.
 * If the message type is Message.Type.POST_FILE_RESPONSE, this means that the server has finished processing
 * the request sent by the client. 
 * If the message type is Message.Type.POST_FILE_REQUEST, this means that the server is pushing 
 * a payload of {@code File} to the client expecting the client to
 * 		1. write to file system
 * 		2. notify server by writing a message of type Message.Type.POST_FILE_RESPONSE from client to server
 *
 * It will iterate through the active clients that are connected to the server
 * and push the {@code File} payload to the client until all iterations have run or the
 * target redundancy has been met.
 * 
 * !!!IMPORTANT!!!
 * The file node distribution table is updated asynchronously.
 * The class will return a success after all request to the clients are done.
 * Whether the files have been processed and persisted onto the client and
 * the client notifies the server of the success is when the table is updated.
 * 
 */
public class ServicePostFileRequest implements Service {
	
	@Override
	public int run(Message message, ClientHandler ref) {
		if (message.getPayload() != null && message.getPayload() instanceof File) {
			// ensure valid payload
			File payload = (File) message.getPayload();
			payload.setOwner(ref.getUser().getId());
			HashMap<String, List<FileNode>> fileDistribution = Server.getInstance().getFileDistribution();
			if (!fileDistribution.containsKey(payload.getFileName())) {
				// ensure unique file name
				
				// init array
				fileDistribution.put(payload.getFileName(), new ArrayList<>());
				
				// iterate over active clients connected to the server and push file to them
				Server.getInstance().getActiveClients().forEach((k,v) -> {
					ClientHandler client = (ClientHandler) v;
					Message req = new Message(Type.POST_FILE_REQUEST);
					req.setPayload(payload);
					try {
						// sent to client
						client.getObjOutStream().writeObject(req);
					} catch (IOException e) {
						return;
					}
				});
				Message res = new Message(Message.Type.POST_FILE_RESPONSE, Status.SUCCESS);
				res.setMessage("File [" + payload.getFileName() + "] has been pushed into the system.");
				try {
					ref.getObjOutStream().writeObject(res);
				} catch (IOException e) {
					return -1;
				}
			} else {
				Message res = new Message(Message.Type.ERROR, Status.FAILURE);
				res.setMessage("expecting type file for payload");
				try {
					ref.getObjOutStream().writeObject(res);
				} catch (Exception e) {}
				return -1;
			}
		} else {
			Message res = new Message(Message.Type.ERROR, Status.FAILURE);
			res.setMessage("expecting type file for payload");
			try {
				ref.getObjOutStream().writeObject(res);
			} catch (Exception e) {}
			return -1;
		}
		return 0;
	}

}
