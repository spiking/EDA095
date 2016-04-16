package Lab3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket server;
	private InputHandler inputHandler;
	
	
	public Server(int port, InputHandler inputHandler) throws IOException {
		server = new ServerSocket(port);
		this.inputHandler = inputHandler;
		init();
	}

	public void init() {
		Socket socket;
		System.out.println("SERVER STARTED!");
		try {
			while ((socket = server.accept()) != null) {
				inputHandler.addConnection(socket);
				new ServerThread(inputHandler, socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		InputHandler inputHandler = new InputHandler();
		new Server(30000, inputHandler);
	}

}
