package org.hokkaido;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.hokkaido.Pipeline.InitializationException;

public class Main {

	private static final String DEBUG = "debug";
	private static final String HELP = "help";
	private static final String FILE = "file";
	
	private static Logger logger = Logger.getLogger("org.hokkaido.Main");

	@SuppressWarnings("static-access")
	public static Options buildOptions() {
		Options options = new Options();

		Option file = OptionBuilder.withArgName(FILE).isRequired().hasArg()
				.withDescription("Config file to use with hokkaido")
				.create(FILE);
		options.addOption(file);

		options.addOption(DEBUG, false, "Show debug information");
		options.addOption(HELP, false, "Print this help.");

		return options;
	}

	public static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("hokkaido", options);
	}

	private static final Pipeline pipeline = new Pipeline();

	private static void start(String configFile) throws InitializationException, IOException {
		logger.log(Level.INFO, "Starting the Hokkaido pipeline");

		Config config = new Config();
		config.load(configFile);
	 
		pipeline.initialize(config);
		pipeline.start();
		
		logger.log(Level.INFO, "Hokkaido is started");
	}

	public static void main(String[] args) throws Exception {
		addShutdownHook();
		
		CommandLineParser parser = new BasicParser();
		Options options = buildOptions();

		CommandLine cmd;
		try {
			 cmd = parser.parse(options, args);
			if (cmd.hasOption(HELP)) {
				printHelp(options);
				return;
			}
		} catch (MissingOptionException moe) {
			printHelp(options);
			return;
		}
 
		String configFile = cmd.getOptionValue(FILE);
		start(configFile);

	}
	
	private static void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Hokkaido is being shutdown");
				pipeline.stop();
			}
		});
	}
}
