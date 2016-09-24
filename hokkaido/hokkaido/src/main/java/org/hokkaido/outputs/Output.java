package org.hokkaido.outputs;

import java.util.concurrent.LinkedTransferQueue;

import org.hokkaido.Event;

public abstract class Output implements Runnable {

	private LinkedTransferQueue<Event> queue;
	
	public abstract void run();
	
	public void setInputQueue(LinkedTransferQueue<Event> queue) {
		this.queue = queue;
	}
	
	protected synchronized Event poll() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
