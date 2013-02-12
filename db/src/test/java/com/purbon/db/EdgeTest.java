package com.purbon.db;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.EdgeDirection;
import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;


public class EdgeTest {

	private Graph graph;
	
	@Before
	public void setUp() throws Exception {
		GNetty netty  = new GNetty();
		graph         = netty.newGraph("graph");
		Node person01 = graph.addNode("person");
		     person01.set("name", "purbon");
		Node person02 = graph.addNode("person");
	     	 person02.set("name", "warper");

		graph.addEdge("friend", person01, person02);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetUp() {
		Node person01 = graph.getNode(1L);
		assertEquals("purbon", person01.get("name"));
 	}
	
	@Test
	public void testNavigation() {
		Node person01 = graph.getNode(1L);
		Node friend01 = person01.neighbours().get(0);
		assertEquals("warper", friend01.get("name"));
	}
	
	@Test
	public void testNavigationIn() {
		Node person01 = graph.getNode(2L);
		Node friend01 = person01.neighbours(EdgeDirection.IN).get(0);
		assertEquals("purbon", friend01.get("name"));
	}
	
	@Test
	public void testNavigationEdges() {
		Node person01 = graph.getNode(1L);
		Node friend01 = person01.edges(EdgeDirection.OUT).get(0).getTarget();
		assertEquals("warper", friend01.get("name"));
	}

	@Test
	public void testNavigationEdgesIn() {
		Node person01 = graph.getNode(2L);
		Node friend01 = person01.edges(EdgeDirection.IN).get(0).getSource();
		assertEquals("purbon", friend01.get("name"));
	}
	
	@Test
	public void testAddEdgeFromNode() {
		Node person01 = graph.getNode(1L);
		Node person03 = graph.addNode("person");
		     person03.set("name", "jonhy");
			 person01.addEdge("friend", person03);
		assertEquals(2, person01.neighbours().size());
		assertEquals("jonhy", person01.neighbours().get(1).get("name"));
	}
	
}
