package com.purbon.db.Impl;

import java.io.IOException;

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
		Long i = 0L;
		try {
			for(NodeImpl node : storage.getNodes()) {
				node.setGraph(this);
				nodesMap.put(++i, node);
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
