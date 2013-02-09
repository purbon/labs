package com.purbon.db;

public interface Graph {

	public String getName();
	
	public Node addNode(String type);
	public Node getNode(long id);
	public Long nodes();
	
	public Edge addEdge(String type, Node source, Node target);
	public Edge getEdge(long id);
	public Long edges();
}
