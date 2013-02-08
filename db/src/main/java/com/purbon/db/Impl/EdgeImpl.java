package com.purbon.db.Impl;

import com.purbon.db.Edge;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public class EdgeImpl extends ElementImpl implements Edge {

	private Node source;
	private Node target;
	
	public EdgeImpl(Graph graph, String type, Node source, Node target) {
		super(graph, type);
		this.source = source;
		this.target = target;
 	}

	public Node getSource() {
 		return source;
	}

	public Node getTarget() {
 		return target;
	}

	@Override
	public String toString() {
		return "EdgeImpl [source=" + source + ", target=" + target + "]";
	}
	
	

}
