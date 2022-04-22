package csueb.cs401.server;

public class Start {
	public static void main(String[] args) {
		Server server = Server.getInstance();
		server.start();
	}
}
