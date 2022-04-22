package csueb.cs401.server;

import csueb.cs401.common.Message;

public interface Service {

	
	/**
	 * @return 0 if successful, any other number indicated failure
	 */
	public int run(Message message, ClientHandler ref);
}
