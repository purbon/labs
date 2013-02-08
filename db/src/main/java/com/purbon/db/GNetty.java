package com.purbon.db;

import java.util.HashMap;
import java.util.Map;

import com.purbon.db.Impl.GraphImpl;

public class GNetty {

	private Map<String, Graph> tmpGraphs;
	private Graph graph;
	
	public GNetty() {
		tmpGraphs = new HashMap<String, Graph>();
		graph     = new GraphImpl("main");
	}
	
	public Graph newGraph(String name) {
		Graph graph = new GraphImpl(name);
		tmpGraphs.put(name, graph);
		return graph;
	}
	

	public Graph getTempGraph(String name) {
		return tmpGraphs.get(name);
	}
	
	public Graph getGraph() {
		return graph;
	}
}
