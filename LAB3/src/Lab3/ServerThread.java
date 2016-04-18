package Lab3;

import java.io.InputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private MessageHandler messageHandler;
	private Socket socket;

	public ServerThread(MessageHandler messageHandler, Socket socket) {
		this.messageHandler = messageHandler;
		this.socket = socket;

	}

	public void run() {
		InputStream in = null;
		try {
			in = socket.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] input = new byte[1024];
		while (true) {
			if (!socket.isClosed()) {
				try {
					int length = in.read(input); // number of read bytes
					messageHandler.writeMessage(input, length, socket);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println("A client has disconnected (not by valid command).");
					break;
				}
			} 
		}

	}

}
