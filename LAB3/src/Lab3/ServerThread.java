package Lab3;

import java.io.InputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private InputHandler inputHandler;
	private Socket socket;

	public ServerThread(InputHandler inputHandler, Socket socket) {
		this.inputHandler = inputHandler;
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
					int length = in.read(input);
					inputHandler.writeMessage(input, length, socket);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println("CLIENT - UNKNOWN CONNETION FAILURE!");
					break;
				}
			} 
		}

	}

}
