package com.purbon.db;

import java.io.IOException;

public interface PGraph {
 
	public void open(String dir) throws IOException;
	
	public  boolean isOpen();
	public void close() throws IOException;
	public void save() throws IOException;

	public void create(String file)  throws IOException;
 
}
