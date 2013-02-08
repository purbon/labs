package com.purbon.db;

public interface Graph {

	public String getName();
	
	public Node addNode(String type);
	public Node getNode(Long id);
	public Long nodes();
	
	public Edge addEdge(String type, Node source, Node target);
	public Edge getEdge(Long id);
	public Long edges();
}
