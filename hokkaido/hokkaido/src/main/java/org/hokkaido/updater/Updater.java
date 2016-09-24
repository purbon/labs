package org.hokkaido.updater;

import java.util.Queue;

import org.hokkaido.Event;

public abstract class Updater implements Runnable {

	private Queue<Event> inputQueue;
	private Queue<Event> outputQueue;
	
	public abstract void run();

	public void setInputQueue(Queue<Event> inputQueue) {
		this.inputQueue = inputQueue;
	}

	public void setOutputQueue(Queue<Event> outputQueue) {
		this.outputQueue = outputQueue;
	}
	
	public Event fetchEvent() {
		return inputQueue.poll();
	}
	
	public void append(Event event) {
		outputQueue.add(event);
	}
	
}
