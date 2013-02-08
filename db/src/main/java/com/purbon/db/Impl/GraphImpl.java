package com.purbon.db.Impl;

import java.util.HashMap;
import java.util.Map;

import com.purbon.db.Edge;
import com.purbon.db.EdgeDirection;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public class GraphImpl implements Graph {

	protected String name;
	protected Map<Long, Node> nodesMap;
	protected Map<Long, Edge> edgesMap;
	protected Long nodes;
	protected Long edges;
	
 	
	public GraphImpl(String name) {
		this.name     = name;
		this.nodesMap = new HashMap<Long, Node>();
		this.nodes    = 0L;
		this.edgesMap = new HashMap<Long, Edge>();
		this.edges    = 0L;
 	}
	
	public Node addNode(String type) {
 		Node node = new NodeImpl(this, type);
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
		Edge edge = new EdgeImpl(this, type, source, target);
		edgesMap.put(++edges, edge);
		((NodeImpl)source).addEdge(edge, EdgeDirection.OUT);
		((NodeImpl)target).addEdge(edge, EdgeDirection.IN);
		return edge;
	}

	public Edge getEdge(Long id) {
 		return edgesMap.get(id);
	}

	public Long edges() {
 		return edges;
	}
}
