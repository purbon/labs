package com.purbon.db;

public interface Element {

	public void   set(String key, Object value);
 	public Object get(String key);
	public String getType();
}
