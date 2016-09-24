package org.hokkaido;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


public abstract class Input implements Runnable {

	protected Queue<Event> queue;
	private Map<String, Object> props;
	 
	public void initialize() throws IOException {
		initialize(new HashMap<String, Object>());
	}
 
	public void initialize(Map<String, Object> props) throws IOException {
 		this.props = props;
	}
	
	protected void enqueue(Event e) {
		queue.add(e);
	}

	public void setOutputQueue(Queue<Event> outputQueue) {
		this.queue = outputQueue;
	}
	
	protected Event encode(Map<String, Object> props) {
		Event event = new Event();
		event.set("@timestamp", System.currentTimeMillis());
		for(String key : props.keySet()) {
			event.set(key, props.get(key));
		}
		return event;
	}
	
	protected Object getProperty(String key) {
		return props.get(key);
	}
	
	protected int getPropertyAsInteger(String key) {
		return (Integer)props.get(key);
	}
	
	protected String getPropertyAsString(String key) {
		return (String)props.get(key);
	}
}
