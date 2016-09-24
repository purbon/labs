package org.hokkaido;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hokkaido.outputs.*;
import org.hokkaido.updater.*;

/**
 * Handles a data collection and distribution pipeline
 * 
 * @author purbon
 *
 */
@SuppressWarnings("unchecked")
public class Pipeline {

	public class InitializationException extends Exception {

		private static final long serialVersionUID = -4582676725187769120L;

		public InitializationException(Exception ex) {
			super(ex);
		}

	}

	private static final int OUTPUT_THREADS = 2;

	private Class<? extends Input> inputClass = null;
	private Class<? extends Updater> updaterClass = null;
	private Class<? extends Output> outputClass = null;

	private Thread inputWorker;
	private List<Thread> updateWorkers;
	private List<Thread> outputWorkers;

	protected LinkedTransferQueue<Event> outputQueue;
	protected LinkedTransferQueue<Event> updaterQueue;
	protected ConcurrentLinkedQueue<Event> replyQueue;

	private static Logger logger = Logger.getLogger("org.hokkaido.Pipeline");

	
	/**
	 * Initialize the pipeline
	 * @param config
	 * @throws InitializationException
	 */
	public void initialize(Config config) throws InitializationException {
		logger.log(Level.INFO, "Initializing the pipeline");
		
		replyQueue = new ConcurrentLinkedQueue<Event>();
		
		setupInputWorkers(config);
		setupUpdaterWorkers(config);
		setupOutputWorkers(config);
		
		logger.log(Level.INFO, "Pipeline initialized");
	}

	public void start() throws InitializationException {

		for (Thread outputWorker : outputWorkers) {
			outputWorker.start();
		}
		for (Thread updateWorker : updateWorkers) {
			updateWorker.start();
		}
		inputWorker.start();
	}

	public void stop() {
		inputWorker.interrupt();
		for (Thread updateWorker : updateWorkers) {
			updateWorker.interrupt();
		}
		for (Thread outputWorker : outputWorkers) {
			outputWorker.interrupt();
		}
	}
	 	
	private void setupInputWorkers(Config config) throws InitializationException {
		updaterQueue = new LinkedTransferQueue<Event>();
		outputQueue   = new LinkedTransferQueue<Event>();

		try {
			Map<String, Object> props = config.getAsList("inputs").get(0);
			String inputName = StringUtils.capitalize((String) (props.get("name")));
			inputClass   = (Class<? extends Input>) Class.forName("org.hokkaido.inputs." + inputName + "Input");
			
			Input input = inputClass.newInstance();
			input.initialize(props);
			if (updaterClass != null)
				input.setOutputQueue(updaterQueue);
			else
				input.setOutputQueue(outputQueue);

			inputWorker = new Thread(input);
		} catch (Exception ex) {
			throw new InitializationException(ex);
		}
	}
	
	private void setupUpdaterWorkers(Config config) throws InitializationException {
		updateWorkers = new ArrayList<Thread>();

		try {
			if (updaterClass != null) {
				Updater updater = updaterClass.newInstance();
				updater.setInputQueue(updaterQueue);
				updater.setOutputQueue(outputQueue);

				Thread updateWorker = new Thread(updater);
				updateWorkers.add(updateWorker);
			}
		} catch (Exception ex) {
			throw new InitializationException(ex);
		}
	}
	
	private void setupOutputWorkers(Config config) throws InitializationException {
		outputWorkers = new ArrayList<Thread>();
		try {
			String outputName = StringUtils.capitalize((String) (config.getAsList("outputs").get(0).get("name")));
			outputClass  = (Class<? extends Output>) Class.forName("org.hokkaido.outputs."+outputName+"Output");
			
			for (int i = 0; i < OUTPUT_THREADS; i++) {
				Output output = outputClass.newInstance();
				output.setInputQueue(outputQueue);
				Thread outputWorker = new Thread(output);
				outputWorkers.add(outputWorker);
			}
		} catch (Exception ex) {
			throw new InitializationException(ex);
		}
	}
}
