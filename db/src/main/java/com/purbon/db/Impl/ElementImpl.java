package com.purbon.db.Impl;

import java.util.HashMap;
import java.util.Map;

import com.purbon.db.Element;

public class ElementImpl implements Element {

	private Map<String, Object> properties;
	private String type;
	
	public ElementImpl(String type) {
		this.properties = new HashMap<String, Object>();
		this.type       = type; 
	}

	public void set(String key, Object value) {
		properties.put(key, value);
	}

	public Object get(String key) {
 		return properties.get(key);
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
