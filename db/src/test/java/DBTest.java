import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.purbon.db.GNetty;
import com.purbon.db.Graph;
import com.purbon.db.Node;

public class DBTest {
	
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
		netty.open("db/");
		Graph graph = netty.getGraph();
		graph.addNode("person");
		graph.addNode("person");
 		assertEquals(Long.valueOf(4L), graph.nodes());
		netty.close();
	}
	
	@Test
	public void testOpenTwice() throws IOException {
		GNetty netty1 = new GNetty();
		netty1.open("db/");
		GNetty netty2 = new GNetty();
		netty2.open("db/");
		netty1.close();
		netty2.close();

	}
	
	@Test
	public void testNodesFetch() throws IOException {
		GNetty netty = new GNetty();
		netty.open("db/");
		Graph graph = netty.getGraph();
		for(Long i=1L; i <= graph.nodes(); i++) {
			Node node = graph.getNode(i);
	 		assertEquals("person", node.getType());
		}
		netty.close();
	}
	
	private static void deleteGraphDB() throws IOException {
		new File("db/graphdb.gnetty").delete();
	}

	private static void createGraph() throws IOException {
		GNetty netty = new GNetty();
		 
		netty.open("db/");
		
		Graph graph = netty.getGraph();
		
		graph.addNode("person");
		graph.addNode("person");
		
		netty.flush();
		netty.close();
	}
}
