import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.purbon.db.GNetty;
import com.purbon.db.Graph;


public class GraphPersistantTest {
	
	@Before
	public void setUp() throws Exception {
		GNetty netty = new GNetty();
 
		netty.open("db/");
		
		Graph graph = netty.getGraph();
		
		graph.addNode("person");
		graph.addNode("person");
		
		netty.flush();
		netty.close();
	}

	@After
	public void tearDown() throws Exception {
  	}

	@Test
	public void testNodes() throws IOException {
		GNetty netty = new GNetty();
		netty.open("db/");
		Graph graph = netty.getGraph();
 		assertEquals(Long.valueOf(2L), graph.nodes());
		netty.close();
	}

}
