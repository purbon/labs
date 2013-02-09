package com.purbon.db.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.purbon.db.Element;
import com.purbon.db.Graph;

public class ElementImpl implements Element {

	private Map<String, Object> properties;
	private String type;
 	protected Graph  graph;
	
	public ElementImpl(Graph graph, String type) {
		this.properties = new HashMap<String, Object>();
		this.type       = type; 
		this.graph      = graph;
	}

	public void set(String key, Object value) {
		properties.put(key, value);
	}

	public Object get(String key) {
 		return properties.get(key);
	}
	
	public Set<String> keys() {
		return properties.keySet();
	}
	
	public int size() {
		return properties.size();
	}

	public String getType() {
 		return type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : properties.keySet()) {
			sb.append(key);
			sb.append(" ");
			sb.append(properties.get(key));
			sb.append("\n");
		}
		sb.append("type ");
		sb.append(type+"\n");
		return sb.toString();
 	}
	
	
}
