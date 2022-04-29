package csueb.cs401.server;

import java.util.Date;

import csueb.cs401.common.FileNode;
import csueb.cs401.common.Message;

/**
 * @author michaelvu
 * This class will receive a {@code Message} expecting a payload of type
 * {@code FileNode}. 
 * 
 * It's purpose is to notify the server from the client (the node that persisted the actual file contents)
 * that the file is persisted and ready to be used by the system.
 */
public class ServicePostFileResponse implements Service {

	@Override
	public int run(Message message, ClientHandler ref) {
		if (message.getPayload() != null 
				&& message.getPayload() instanceof FileNode 
				&& ((FileNode) message.getPayload()).getFileName() != null
				&& ((FileNode) message.getPayload()).getOwner() != null) {
			FileNode fn = (FileNode) message.getPayload();
			fn.setCreated(new Date());
			fn.setNodeOwner(ref.getUser().getId());
			if (Server.getInstance().getFileDistribution().containsKey(fn.getFileName())) {
				Server.getInstance().getFileDistribution().get(fn.getFileName()).add(fn);
			}
		}
		return 0;
	}

}
