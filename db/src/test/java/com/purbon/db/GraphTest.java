package com.purbon.db;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;


public class GraphTest {

	private Graph graph;
	
	@Before
	public void setUp() throws Exception {
		GNetty netty = new GNetty();
		graph        = netty.newGraph("graph");
		graph.addNode("person");
	}

	@After
	public void tearDown() throws Exception {
 	}

	@Test
	public void testNodes() {
		assertEquals(Long.valueOf(1L), (Long)graph.nodes());
	}
	
	@Test 
	public void testGetNodes() {
 		Node node = graph.getNode(1L);
		assertEquals("person", node.getType());
	}

}
