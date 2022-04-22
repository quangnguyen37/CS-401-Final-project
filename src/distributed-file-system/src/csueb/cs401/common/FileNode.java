package csueb.cs401.common;

import java.util.Date;

public class FileNode {
	
	private Date created;
	private byte[] content;
	private String owner;
	private String nodeOwner;
	
	/*
	 * Getters and Setters
	 */
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getNodeOwner() {
		return nodeOwner;
	}
	public void setNodeOwner(String nodeOwner) {
		this.nodeOwner = nodeOwner;
	}
	
}
