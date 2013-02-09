import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.channels.OverlappingFileLockException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public class DBTest {
	
 	public static final String GRAPH_FILE  = "graphdb.gnetty";
 	public static final String GRAPH2_FILE = "graphdb2.gnetty";


	@BeforeClass
	public static void setUp() throws Exception {
		 createGraph();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		deleteGraphDB();
	}

	@Test
	public void testNodesCount() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		graph.addNode("person");
		graph.addNode("person");
 		assertEquals(Long.valueOf(4L), graph.nodes());
		netty.close();
	}
	
	@Test
	public void testOpenTwiceFail() throws IOException {
		GNetty netty1 = new GNetty();
		netty1.open(GRAPH_FILE);
		GNetty netty2 = new GNetty();
		try {
			netty2.open(GRAPH_FILE);
			assertTrue(false);
		} catch (OverlappingFileLockException ex) {
			assertTrue(true);
		} finally {
			netty1.close();
 		}
	}
	
	@Test
	public void testOpenTwiceOk() throws IOException {
		GNetty netty1 = new GNetty();
		netty1.open(GRAPH_FILE);
		GNetty netty2 = new GNetty();
  		try {
			netty2.open(GRAPH2_FILE);
			assertTrue(true);
		} catch (OverlappingFileLockException ex) {
			assertTrue(false);
		} finally {
			netty1.close();
			netty2.close();
			new File(GRAPH2_FILE).delete();
  		}
	}
	
	@Test
	public void testNodesFetch() throws IOException {
		GNetty netty = new GNetty();
		netty.open(GRAPH_FILE);
		Graph graph = netty.getGraph();
		for(Long i=1L; i <= graph.nodes(); i++) {
			Node node = graph.getNode(i);
	 		assertEquals("person", node.getType());
		}
		netty.close();
	}
	
	private static void deleteGraphDB() throws IOException {
		new File(GRAPH_FILE).delete();
	}

	private static void createGraph() throws IOException {
		GNetty netty = new GNetty();
		 
		netty.open(GRAPH_FILE);
		
		Graph graph = netty.getGraph();
		
		graph.addNode("person");
		graph.addNode("person");
		
		netty.flush();
		netty.close();
	}
}
