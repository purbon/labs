package org.hokkaido;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("rawtypes")
public class Config {

	private static final String INPUTS = "inputs";
	
	private Yaml yaml;
	private Map document;
	
	public Config() {
		yaml = new Yaml();
	}
	
	public void load(String filepath) throws IOException {
		load(new FileInputStream(new File(filepath)));
	}
	
	public void load(InputStream inputStream) {
		document = (Map) yaml.load(inputStream);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getAsList(String key) {
		return (ArrayList<Map<String,Object>>)document.get(key);
	}
	
}
