package com.purbon.db.Impl;

import java.io.IOException;
import java.util.ArrayList;

import com.purbon.db.Storage.GraphStorage;

public class GraphPersistantImpl extends GraphImpl {
 
 	private GraphStorage storage;
	private static boolean isOpen;
	
	public GraphPersistantImpl() {
		super("main");
		storage = new GraphStorage();
		isOpen = false;
 	}
	
	public void open(String dir) throws IOException {
		storage.open(dir);
		this.nodes = storage.nodes();
 		this.edges = storage.edges();
		
		try {
			long i = 0L;
			ArrayList<NodeImpl> nodes = storage.getNodes();
			for(NodeImpl node : nodes) {
				node.setGraph(this);
				nodesMap.put(++i, node);
			}
			i = 0L;
			for(EdgeImpl edge : storage.getEdges(nodesMap)) {
				edge.setGraph(this);
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
