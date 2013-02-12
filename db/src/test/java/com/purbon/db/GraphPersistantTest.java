package com.purbon.db;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.Edge;
import com.purbon.db.EdgeDirection;
import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;


public class GraphPersistantTest {
	
 	public static final String GRAPH_FILE  = "graphdb.gnetty";
 	private Date since = null;
 	
	@Before
	public void setUp() throws Exception {
		GNetty netty = new GNetty();
 
		netty.open(GRAPH_FILE);
		
		Graph graph = netty.getGraph();
		
		Node person01 = graph.addNode("person");
		person01.set("name", "purbon");
		Node person02 = graph.addNode("person");
		person02.set("name", "sselles");
		Edge inLove = graph.addEdge("inlove", person01, person02);
		since = Calendar.getInstance().getTime();
			 inLove.set("since", since);
		netty.flush();
		netty.close();
	}

	@After
	public void tearDown() throws Exception {
		new File(GRAPH_FILE).delete();
	}
	
	@Test
 	public void testEdgesCount() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		Node purbon = graph.getNode(1);
		assertEquals(1, purbon.edges(EdgeDirection.OUT).size());
 		netty.close();
	}

	@Test
 	public void testEdgesContent() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		Node purbon = graph.getNode(1);
		Edge edge   = purbon.edges(EdgeDirection.OUT).get(0);
		assertEquals("inlove", edge.getType());
		netty.close();
	}
	
	@Test
 	public void testEdgesProperties() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		Node purbon = graph.getNode(1);
		Edge edge   = purbon.edges(EdgeDirection.OUT).get(0);
 		assertEquals(since, edge.get("since"));
		netty.close();
	}
	
	@Test
 	public void testEdgesCountIn() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		Node purbon = graph.getNode(1);
		assertEquals(0, purbon.edges(EdgeDirection.IN).size());
 		netty.close();
	}
	
	@Test
	public void testNodesCount() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
 		assertEquals(Long.valueOf(2L), graph.nodes());
		netty.close();
	}
	

	@Test
	public void testNodeProperty() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		Node node   = graph.getNode(1);
		assertEquals("purbon", node.get("name"));
		netty.close();
	}
	
	@Test
	public void testNodes() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		for(Long i=1L; i <= graph.nodes(); i++) {
			Node node = graph.getNode(i);
	 		assertEquals("person", node.getType());
		}
		netty.close();
	}

}
