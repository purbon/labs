package com.purbon.db.Storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;

import com.purbon.db.Graph;
import com.purbon.db.Node;
import com.purbon.db.Impl.NodeImpl;

public class GraphStorage {

 	private static final String GRAPH_FILE = "graphdb.gnetty";
 	
	private MappedByteBuffer  mb;
	private RandomAccessFile  file;
	private FileChannel       ch;
	private FileLock 		  lock;
	private Long nodes;
	private Long edges;
	
	public GraphStorage() {
		
	}
	
	public void open(String dir) throws IOException, OverlappingFileLockException {
		String fileName = dir+File.separatorChar+GRAPH_FILE;
 		file = new RandomAccessFile(fileName,"rw");
 		ch = file.getChannel();
 		lock = ch.lock();
 		mb = ch.map( FileChannel.MapMode.READ_WRITE, 0L, 2048L );
 		//ch.tryLock();
 	}
	
 

	public void close() throws IOException {
		lock.release();
		ch.force(true);
		ch.close();
		file.close();
	}
	
	public Long nodes() {
		nodes =  mb.getLong();
		return nodes;
	}
	
	public Long edges() {
		edges =  mb.getLong();
		return edges;
	}
	
	public ArrayList<NodeImpl> getNodes() {
		ArrayList<NodeImpl> nodesList = new ArrayList<NodeImpl>();
		for(Long i=1L; i <= nodes; i++) {
			int bytesCount = mb.getInt();
			byte[] dst = new byte[bytesCount];
			mb.get(dst);
			String type = new String(dst);
			nodesList.add(new NodeImpl(type));
		}
		return nodesList;
	}
	
	public void flush() {
		mb.force();
 	}
	
	public void write(Graph graph) {
		mb.rewind();
 		mb.putLong(graph.nodes());
		mb.putLong(graph.edges());
 		for(Long i=1L; i <= graph.nodes(); i++) {
			Node node = graph.getNode(i);
 			mb.putInt(node.getType().getBytes().length);
			mb.put(node.getType().getBytes());
		}
		flush();
 	}
	

	
}
