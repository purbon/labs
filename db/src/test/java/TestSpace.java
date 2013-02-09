import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;


public class TestSpace {


 	public static final String GRAPH_FILE  = "graphdb.gnetty";
 	private GNetty netty;
 	
	@Before
	public void setUp() throws Exception {
		netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		for(int i=0; i < 100; i++) {
			Node node = graph.addNode("dummy");
			for(int j=0; j < 100; j++) {
				node.set("Key"+j, "Value"+j);
			}
			netty.flush();
		}
		netty.flush();
		netty.close();
	}

	@After
	public void tearDown() throws Exception {
		File file = new File(GRAPH_FILE);
		System.out.println(GRAPH_FILE+" size: "+file.length());
		new File(GRAPH_FILE).delete();
	}

	@Test
	public void testNodesCount() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		assertEquals(Long.valueOf(100), graph.nodes());
		netty.close();
	}

	@Test
	public void testNodeProperties() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		Node node = graph.getNode(1);
 		assertEquals("Value62", node.get("Key62"));
		netty.close();
	}
}
