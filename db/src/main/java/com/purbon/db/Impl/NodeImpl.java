package com.purbon.db.Impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.purbon.db.Edge;
import com.purbon.db.EdgeDirection;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public class NodeImpl extends ElementImpl implements Node, Serializable {

 
	private static final long serialVersionUID = 3828298774769622620L;
	private Map<EdgeDirection, ArrayList<Edge>> edgesMap; 
	
	public NodeImpl(Graph graph, String type) {
		super(graph, type);
 		this.edgesMap = new HashMap<EdgeDirection, ArrayList<Edge>>();
		this.edgesMap.put(EdgeDirection.OUT, new ArrayList<Edge>());
		this.edgesMap.put(EdgeDirection.IN, new ArrayList<Edge>());
  	}
	
	public NodeImpl(String type) {
		this(null, type);
	}

	public ArrayList<Node> neighbours() {
		return neighbours(EdgeDirection.OUT);
	}
	

	public ArrayList<Node> neighbours(EdgeDirection dir) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		if (EdgeDirection.IN.equals(dir) || EdgeDirection.BOTH.equals(dir)) {
			for(Edge edge : edgesMap.get(dir)) {
				nodes.add(edge.getSource());
			}
		}
		if (EdgeDirection.OUT.equals(dir) || EdgeDirection.BOTH.equals(dir)) {
			for(Edge edge : edgesMap.get(dir)) {
				nodes.add(edge.getTarget());
			}
		}
		return nodes;
	}

	public ArrayList<Edge> edges(EdgeDirection dir) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
  		if (EdgeDirection.IN.equals(dir) || EdgeDirection.BOTH.equals(dir)) {
			edges.addAll(edgesMap.get(EdgeDirection.IN));
		} 
		if (EdgeDirection.OUT.equals(dir) || EdgeDirection.BOTH.equals(dir)) {
			edges.addAll(edgesMap.get(EdgeDirection.OUT));
		} 		
		return edges;
	}

	public void addEdge(String type, Node target) {
 		graph.addEdge(type, this, target);
	}	
	
	public void addEdge(Edge edge, EdgeDirection dir) {
  		edgesMap.get(dir).add(edge);
 	}

	@Override
	public String toString() {
		return super.toString();
	}


	
}
