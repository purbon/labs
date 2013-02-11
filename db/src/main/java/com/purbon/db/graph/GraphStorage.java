package com.purbon.db.graph;

import java.io.IOException;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Map;

import com.purbon.db.Edge;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public interface GraphStorage {
 	
 
	public void open(String fileName) throws IOException, OverlappingFileLockException;
	public void close() throws IOException;
	public Long nodes();
	public Long edges();
	public ArrayList<Node> getNodes() throws ClassNotFoundException, IOException;
	public ArrayList<Edge> getEdges(Map<Long, Node> nodesTable) throws ClassNotFoundException, IOException;

	public void flush();
	
	public void write(Graph graph) throws IOException;
	public void create(String file) throws IOException;
}
