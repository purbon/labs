package com.purbon.db;

import java.util.ArrayList;

public interface Node extends Element {

	public ArrayList<Node> neighbours();
	public ArrayList<Node> neighbours(EdgeDirection dir);
	public ArrayList<Edge> edges(EdgeDirection dir);
	
	public void addEdge(Edge edge, EdgeDirection dir);
	
 }
