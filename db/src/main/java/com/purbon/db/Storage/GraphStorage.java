package com.purbon.db.Storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import com.purbon.db.Graph;

public class GraphStorage {

 	private static final String GRAPH_FILE = "graphdb.gnetty";
 	
	private MappedByteBuffer mb;
	private RandomAccessFile  file;
	private FileChannel      ch;
	
	public GraphStorage() {
		
	}
	
	public void open(String dir) throws IOException {
		file = new RandomAccessFile(dir+File.separatorChar+GRAPH_FILE,"rw");
 		ch = file.getChannel();
		mb = ch.map( FileChannel.MapMode.READ_WRITE, 0L, 2048L );
	}
	
	public void close() throws IOException {
		ch.force(true);
		ch.close();
		file.close();
	}
	
	public Long nodes() {
		return mb.getLong();
	}
	
	public Long edges() {
		return mb.getLong();
	}
	
	public void flush() {
		mb.force();
 	}
	
	public void write(Graph graph) {
 		mb.putLong(graph.nodes());
		mb.putLong(graph.edges());
		flush();
 	}
	

	
}
