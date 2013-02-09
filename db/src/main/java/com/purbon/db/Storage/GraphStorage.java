package com.purbon.db.Storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
	
	public ArrayList<NodeImpl> getNodes() throws ClassNotFoundException, IOException {
		ArrayList<NodeImpl> nodesList = new ArrayList<NodeImpl>();
		for(Long i=1L; i <= nodes; i++) {
 			String type = readString();
			NodeImpl node = new NodeImpl(type);
			int nProperties = mb.getInt();
			for(int j=0; j < nProperties; j++) {
				String key 	 = readString();
				Object value = readObject(); 
				node.set(key, value);
			}
			nodesList.add(node);
		}
		return nodesList;
	}
	
	public void flush() {
		mb.force();
 	}
	
	private String readString() {
		byte[] dst = new byte[mb.getInt()];
		mb.get(dst);
		return new String(dst);
	}
	
	private Object readObject() throws ClassNotFoundException, IOException {
		byte[] dst = new byte[mb.getInt()];
		mb.get(dst);
		return bytesToObject(dst);
	}
	
	public void write(Graph graph) throws IOException {
		mb.rewind();
 		mb.putLong(graph.nodes());
		mb.putLong(graph.edges());
 		for(Long i=1L; i <= graph.nodes(); i++) {
			Node node = graph.getNode(i);
			writeString(node.getType());
			mb.putInt(node.size());
			for(String key : node.keys()) {
				Object value = node.get(key);
				writeString(key);
				writeObject(value);
			}
		}
		flush();
 	}
	

	private void writeString(String value) {
		mb.putInt(value.getBytes().length);
		mb.put(value.getBytes());
	}
	
	private void writeObject(Object value) throws IOException {
		byte [] myBytes = objectToBytes(value);
		mb.putInt(myBytes.length);
		mb.put(myBytes);
	}
	
	private byte[] objectToBytes(Object value) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] yourBytes = null;

		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(value);
			yourBytes = bos.toByteArray();

		} finally {
			out.close();
			bos.close();
		}

		return yourBytes;
	}

	private Object bytesToObject(byte[] yourBytes) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
		ObjectInput in = null;
		Object o = null;
		try {
			in = new ObjectInputStream(bis);
			o = in.readObject();
		} finally {
			bis.close();
			in.close();
		}
		return o;
	}

}
