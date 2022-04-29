package csueb.cs401.server;

import java.io.IOException;

import csueb.cs401.common.LogQuery;
import csueb.cs401.common.Message;
import csueb.cs401.common.Message.Status;
import csueb.cs401.persist.EventLog;

/**
 * @author michaelvu
 * This class will receive a {@code Message} expecting a payload of type
 * {@code LogQuery}.
 * 
 * It will invoke the {@code EventLog} for the logs and return a {@code Message}
 * whose "message" field will contain the logs.
 * 
 * It will only return a successful message if the user is an admin
 */
public class ServiceGetLogs implements Service {

	@Override
	public int run(Message message, ClientHandler ref) {
		if (message.getPayload() != null 
				&& message.getPayload() instanceof LogQuery
				&& ref.getUser().isAdmin()) {
			LogQuery req = (LogQuery) message.getPayload();
			
			Message res = new Message(Message.Type.LOGS, Status.SUCCESS);
			res.setMessage(Server.getInstance().getEventLog().findEvents(req.getStart(), req.getEnd()));
			
			try {
				ref.getObjOutStream().writeObject(res);
			} catch (IOException e) {
				return -1;
			}
			
		} else {
			return -1;
		}
		return 0;
	}

}
