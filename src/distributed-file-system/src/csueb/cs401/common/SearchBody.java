package csueb.cs401.common;

import java.io.Serializable;
import java.util.List;

public class SearchBody implements Payload, Serializable {

	private List<FileNode> nodes;

	public List<FileNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<FileNode> nodes) {
		this.nodes = nodes;
	}
}
