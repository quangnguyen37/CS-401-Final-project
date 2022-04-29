package csueb.cs401.server;

import java.io.IOException;

import csueb.cs401.common.File;
import csueb.cs401.common.Message;
import csueb.cs401.common.Message.Status;

/**
 * @author michaelvu
 * This class will receive a {@code Message} expecting a payload of type
 * {@code File} which requires
 * 		1. fileName
 * 		2. content
 * 		3. owner
 * 		4. token 
 * 
 * It's purpose is to handle a client message containing the requested file contents.
 * 
 * It will check if the original request was handled already. If so, it will return the file
 * to that client. Otherwise, the message is discarded.
 */
public class ServiceReadFileResponse implements Service {

	@Override
	public int run(Message message, ClientHandler ref) {
		if (message.getPayload() != null
				&& message.getPayload() instanceof File
				&& ((File) message.getPayload()).getFileName() != null
				&& ((File) message.getPayload()).getContent() != null
				&& ((File) message.getPayload()).getOwner() != null
				&& ((File) message.getPayload()).getToken() != null) {
			File f = (File) message.getPayload();
			String tok = f.getToken();
			boolean shouldResolve = Server.getInstance().getResolvedReadRequests().containsKey(tok) 
					&& !Server.getInstance().getResolvedReadRequests().get(tok).booleanValue();
			if (shouldResolve) {
				Message res = new Message(Message.Type.READ_FILE_RESPONSE, Status.SUCCESS);
				res.setPayload(f);
				try {
					ref.getObjOutStream().writeObject(res);
				} catch (IOException e) {
					return -1;
				}
				// update status of original request
				Server.getInstance().getResolvedReadRequests().put(tok, true);
			}
		} else {
			return -1;
		}

		return 0;
	}

}
