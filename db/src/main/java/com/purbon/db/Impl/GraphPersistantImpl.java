package com.purbon.db.Impl;

import java.io.IOException;

import com.purbon.db.Storage.GraphStorage;

public class GraphPersistantImpl extends GraphImpl {
 
 	private GraphStorage storage;
	
	public GraphPersistantImpl() {
		super("main");
		storage = new GraphStorage();
 	}
	
	public void open(String dir) throws IOException {
		storage.open(dir);
		this.nodes = storage.nodes();
		this.edges = storage.edges();
	}
	
	public void close() throws IOException {
		storage.close();
	}
	
	public void save() {
		storage.write(this);
	}
	
 
}
