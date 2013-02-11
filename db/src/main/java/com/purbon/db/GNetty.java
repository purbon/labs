package com.purbon.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.purbon.db.graph.GraphFactory;

public class GNetty {

	private Map<String, Graph>  tmpGraphs;
	private PGraph graph;
	
	public GNetty() {
		tmpGraphs = new HashMap<String, Graph>();
		graph     = (PGraph)GraphFactory.get("pgraph");
	}
	
	public void create(String file) throws IOException {
		graph.create(file);
	}
	
	public void open(String dir) throws IOException {
		graph.open(dir);
	}
	
	public void flush() throws IOException {
		graph.save();
	}
	
	public void close() throws IOException {
		graph.close();
	}
	
	public Graph newGraph(String name) {
		Graph graph = GraphFactory.get("tgraph");
		tmpGraphs.put(name, graph);
		return graph;
	}
	

	public Graph getTempGraph(String name) {
		return tmpGraphs.get(name);
	}
	
	public Graph getGraph() {
		return (Graph) graph;
	}
}
