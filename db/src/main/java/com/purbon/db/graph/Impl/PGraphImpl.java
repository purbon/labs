package com.purbon.db.graph.Impl;

import java.io.IOException;
import java.util.ArrayList;

import com.purbon.db.Edge;
import com.purbon.db.Node;
import com.purbon.db.PGraph;
import com.purbon.db.graph.GraphStorage;

public class PGraphImpl extends GraphImpl implements PGraph {
 
 	private GraphStorage storage;
	private static boolean isOpen;
	
	public PGraphImpl() {
		super("main");
		storage = new GraphStorageImpl();
		isOpen = false;
 	}
	
	public void create(String file) throws IOException {
		storage.create(file);
		setOpen(true);
	}
	
	public void open(String dir) throws IOException {
		storage.open(dir);
		this.nodes = storage.nodes();
 		this.edges = storage.edges();
		
		try {
			long i = 0L;
			ArrayList<Node> nodes = storage.getNodes();
			for(Node node : nodes) {
				((NodeImpl)node).setGraph(this);
				nodesMap.put(++i, node);
			}
			i = 0L;
			for(Edge edge : storage.getEdges(nodesMap)) {
				((EdgeImpl)edge).setGraph(this);
				edgesMap.put(++i, edge);
			}
			
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
		setOpen(true);
	}
	
	public synchronized boolean isOpen() {
		return isOpen;
	}
	
	private synchronized void setOpen(boolean status) {
		isOpen = status;
	}
	
	public void close() throws IOException {
		storage.close();
		setOpen(false);
	}
	
	public void save() throws IOException {
		storage.write(this);
	}
	
 
}
