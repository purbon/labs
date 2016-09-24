package org.hokkaido.inputs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.hokkaido.Event;
import org.hokkaido.Input;

public class TCPInput extends Input {

	private static final int MAX_CONNECTIONS = 100;

	private int port;
	private String hostname;
	private ServerSocket serverSocket;

	public TCPInput() {
	}

	public void initialize() throws IOException {
		serverSocket = new ServerSocket(port, MAX_CONNECTIONS, InetAddress.getByName(hostname));
	}

	public void run() {

		while (true) {
			BufferedReader reader = null;
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String json = reader.readLine();
				enqueue(new Event().parse(json));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	public void initialize(Map<String, Object> props) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
