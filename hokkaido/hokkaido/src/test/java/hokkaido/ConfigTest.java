package hokkaido;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.hokkaido.Config;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

	private Config config;

	private InputStream fileStream = getClass().getResourceAsStream("/hokkaido.yaml");

	@Before
	public void setUp() throws Exception {
		config = new Config();
		config.load(fileStream);

	}

	@Test
	public void testNestedProperty() {
		List<Map<String,Object>> val = config.getAsList("inputs");
		assertEquals("generator", val.get(0).get("name"));
	}

}
