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
				Message res = new Message(Message.Type.POST_FILE_REQUEST, Status.SUCCESS);
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
