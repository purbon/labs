package com.purbon.db.Impl;

import java.util.HashMap;
import java.util.Map;

import com.purbon.db.Edge;
import com.purbon.db.EdgeDirection;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public class GraphImpl implements Graph {

	private String name;
	private Map<Long, Node> nodesMap;
	private Map<Long, Edge> edgesMap;
	private Long nodes;
	private Long edges;
	
	public GraphImpl(String name) {
		this.name     = name;
		this.nodesMap = new HashMap<Long, Node>();
		this.nodes    = 0L;
		this.edgesMap = new HashMap<Long, Edge>();
		this.edges    = 0L;
 	}
	
	public Node addNode(String type) {
 		Node node = new NodeImpl(type);
 		nodesMap.put(++nodes, node);
		return node;
	}

	public Node getNode(Long id) {
  		return nodesMap.get(id);
	}

	public String getName() {
		return name;
	}

	public Long nodes() {
 		return nodes;
	}

	public Edge addEdge(String type, Node source, Node target) {
		Edge edge = new EdgeImpl(type, source, target);
		edgesMap.put(++edges, edge);
		source.addEdge(edge, EdgeDirection.OUT);
		target.addEdge(edge, EdgeDirection.IN);
		return edge;
	}

	public Edge getEdge(Long id) {
 		return edgesMap.get(id);
	}

	public Long edges() {
 		return edges;
	}

	
}