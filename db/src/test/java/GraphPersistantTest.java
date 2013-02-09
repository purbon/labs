import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;


public class GraphPersistantTest {
	
	@Before
	public void setUp() throws Exception {
		GNetty netty = new GNetty();
 
		netty.open("db/");
		
		Graph graph = netty.getGraph();
		
		Node node = graph.addNode("person");
		node.set("name", "purbon");
		graph.addNode("person");
		
		netty.flush();
		netty.close();
	}

	@After
	public void tearDown() throws Exception {
		new File("db/graphdb.gnetty").delete();
	}

	@Test
	public void testNodesCount() throws IOException {
		GNetty netty = new GNetty();
		netty.open("db/");
		Graph graph = netty.getGraph();
 		assertEquals(Long.valueOf(2L), graph.nodes());
		netty.close();
	}
	

	@Test
	public void testNodeProperty() throws IOException {
		GNetty netty = new GNetty();
		netty.open("db/");
		Graph graph = netty.getGraph();
		Node node   = graph.getNode(1);
		assertEquals("purbon", node.get("name"));
		netty.close();
	}
	
	@Test
	public void testNodes() throws IOException {
		GNetty netty = new GNetty();
		netty.open("db/");
		Graph graph = netty.getGraph();
		for(Long i=1L; i <= graph.nodes(); i++) {
			Node node = graph.getNode(i);
	 		assertEquals("person", node.getType());
		}
		netty.close();
	}

}
