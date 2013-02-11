package com.purbon.db.graph.Impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.util.Map;

import com.purbon.db.Edge;
import com.purbon.db.EdgeDirection;
import com.purbon.db.Element;
import com.purbon.db.Graph;
import com.purbon.db.Node;
import com.purbon.db.graph.GraphStorage;

class GraphStorageImpl implements GraphStorage {
 	
	private MappedByteBuffer  mb;
	private RandomAccessFile  file;
	private FileChannel       ch;
	private FileLock 		  lock;
	private Long nodes;
	private Long edges;
	
	public GraphStorageImpl() {
		
	}
	
	public void open(String fileName) throws IOException, OverlappingFileLockException {
  		file = new RandomAccessFile(fileName,"rw");
  		ch = file.getChannel();
 		lock = ch.lock();
 		long size = ch.size();
 		if (size == 0) 
 			size = 81920L;
 		mb = ch.map( FileChannel.MapMode.READ_WRITE, 0L, size );
   	}
	
 

	public void close() throws IOException {
		try {
			lock.release();
			ch.force(true);
			ch.close();
			file.close();
		} catch (NullPointerException ex) {

		}
	}
	
	public Long nodes() {
		nodes =  mb.getLong();
		return nodes;
	}
	
	public Long edges() {
		edges =  mb.getLong();
		return edges;
	}
	
	public ArrayList<Node> getNodes() throws ClassNotFoundException, IOException {
		ArrayList<Node> nodesList = new ArrayList<Node>();
		for(Long i=1L; i <= nodes; i++) {
  			long   id   = mb.getLong();
			String type = readString();
   			NodeImpl node = new NodeImpl(type);
 			node.setId(id);
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
	
	public ArrayList<Edge> getEdges(Map<Long, Node> nodesTable) throws ClassNotFoundException, IOException {
		ArrayList<Edge> edgesList = new ArrayList<Edge>();
		for(Long i=1L; i <= edges; i++) {
 			long   id   = mb.getLong();
 			String type = readString();
 			long   srcId   = mb.getLong();
 			long   trgId   = mb.getLong();
  			NodeImpl srcNode = (NodeImpl)nodesTable.get(srcId);
 			NodeImpl trgNode = (NodeImpl)nodesTable.get(trgId);
  			EdgeImpl edge = new EdgeImpl(type, srcNode, trgNode);
			edge.setId(id);
			srcNode.addEdge(edge, EdgeDirection.OUT);
			trgNode.addEdge(edge, EdgeDirection.IN);
			int nProperties = mb.getInt();
			for(int j=0; j < nProperties; j++) {
				String key 	 = readString();
				Object value = readObject(); 
				edge.set(key, value);
			}
			edgesList.add(edge);
		}
		return edgesList;
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
		file.seek(0);
		file.writeLong(graph.nodes());
		file.writeLong(graph.edges());
 		for(Long i=1L; i <= graph.nodes(); i++) {
 			Node node = graph.getNode(i);
   			file.writeLong(node.getId());
			writeString(node.getType());
			writeProperties(node);
   		}
 		for(Long i=1L; i <= graph.edges(); i++) {
 			Edge edge = graph.getEdge(i);
 			file.writeLong(edge.getId());
			writeString(edge.getType());
			file.writeLong(edge.getSource().getId());
			file.writeLong(edge.getTarget().getId());
			writeProperties(edge);
   		}
		flush();
 	}
	
	private void writeProperties(Element element) throws IOException {
		file.writeInt(element.size());
		for(String key : element.keys()) {
			Object value = element.get(key);
			writeString(key);
			writeObject(value);
		}
	}
	private void writeString(String value) throws IOException {
		file.writeInt(value.getBytes().length);
		file.write(value.getBytes());
	}
	
	private void writeObject(Object value) throws IOException {
		byte [] myBytes = objectToBytes(value);
		file.writeInt(myBytes.length);
		file.write(myBytes);
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
