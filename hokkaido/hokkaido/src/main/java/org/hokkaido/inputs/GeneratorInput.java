package org.hokkaido.inputs;

import java.util.HashMap;
import java.util.Map;

import org.hokkaido.Event;
import org.hokkaido.Input;

public class GeneratorInput extends Input {

	public void run() {
		int size 	   = getPropertyAsInteger("size");
		String message = getPropertyAsString("message");
		Map<String, Object> props = new HashMap<String, Object>();
		for(int eventId=0; eventId<size; eventId++) {
			props.put("message", buildMessage(eventId, message));
			Event event = encode(props);
			enqueue(event);			
		}
	}
	
	private String buildMessage(int eventId, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(message);
		sb.append(";");
		sb.append("Id;");
		sb.append(eventId);
		sb.append(";");
		return sb.toString();
	}
}
