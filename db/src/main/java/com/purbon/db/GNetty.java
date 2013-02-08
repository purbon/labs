package com.purbon.db;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.purbon.db.Impl.GraphImpl;
import com.purbon.db.Impl.GraphPersistantImpl;

public class GNetty {

	private Map<String, Graph>  tmpGraphs;
	private GraphPersistantImpl graph;
	
	public GNetty() {
		tmpGraphs = new HashMap<String, Graph>();
		graph     = new GraphPersistantImpl();
	}
	
	public void open(String dir) throws IOException {
		graph.open(dir);
	}
	
	public void flush() {
		graph.save();
	}
	
	public void close() throws IOException {
		graph.close();
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
