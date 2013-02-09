package com.purbon.db;

import java.util.Set;

public interface Element {

	public void   set(String key, Object value);
 	public Object get(String key);
	public String getType();
	
	public Set<String> keys();
	public int size();
}
