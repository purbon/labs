package org.hokkaido.outputs;

import org.hokkaido.Event;

import com.fasterxml.jackson.jr.ob.JSONObjectException;

public class StdoutOutput extends Output {

	@Override
	public void run() {
		while(true) {
			Event event = poll();
				try {
					System.out.println(event.toJSON());
				} catch (JSONObjectException e) {
					e.printStackTrace();
				}
		}
	}

}
