package csueb.cs401.common;

import java.util.Date;

public class FileNode {
	
	private Date created;
	private String fileName;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
