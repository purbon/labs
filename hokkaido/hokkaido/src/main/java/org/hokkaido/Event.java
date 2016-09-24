package org.hokkaido;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONObjectException;

public class Event {

	private Map<String, Object> map;

	public Event() {
		map = new HashMap<String, Object>();
		map.put("@timestamp", System.currentTimeMillis());
	}

	public Event parse(String data) throws JSONObjectException {
		try {
			map = JSON.std.mapFrom(data);
			return this;
		} catch (IOException e) {
			throw new JSONObjectException(e.getMessage());
		}
	}
	
	public String toJSON() throws JSONObjectException {
		try {
			return JSON.std.asString(map);
		} catch (IOException e) {
			throw new JSONObjectException(e.getMessage());
		}
	}

	public void set(String name, Object value) {
		map.put(name, value);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public Set<String> keys() {
		return map.keySet();
	}
	
 }
