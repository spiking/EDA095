package Lab3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket server;
	private ClientHandler clientHandler;
	private MessageHandler messageHandler;
	
	
	public Server(int port, ClientHandler inputHandler, MessageHandler messageHandler) throws IOException {
		server = new ServerSocket(port);
		this.clientHandler = inputHandler;
		this.messageHandler = messageHandler;
		init();
	}

	public void init() {
		Socket socket;
		System.out.println("Server started, port number 30000");
		
		try {
			while ((socket = server.accept()) != null) { // accepts connections to the server socket
				clientHandler.addConnection(socket);
				ServerThread serverThread = new ServerThread(messageHandler, socket);
				serverThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ClientHandler clientHandler = new ClientHandler();
		MessageHandler messageHandler = new MessageHandler(clientHandler);
		new Server(30000, clientHandler, messageHandler);
	}

}
