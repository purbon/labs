package org.hokkaido.inputs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hokkaido.Event;
import org.hokkaido.Input;

public class StdinInput extends Input {

	public void run() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			while ((s = in.readLine()) != null && s.length() != 0) {
				Event event = new Event();
				event.set("EventID", s);
				enqueue(event);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
