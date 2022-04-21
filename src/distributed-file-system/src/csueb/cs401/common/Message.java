package csueb.cs401.common;

import java.io.Serializable;

/**
 * @author michaelvu
 * This class will be the standard object for communication between 
 * the server and client.
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2849350904362862177L;
	public static enum Type {
			LOGIN,
			LOGOUT,
			SEARCH,
			ERROR,
			POST_FILE,
			READ_FILE,
	}
	
	private Type type;
	private Payload payload;
	private String date;
	private String message;
	private String user;
	
	public Message(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
