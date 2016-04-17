package Lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket socket;
	private InputStream in;

	public ClientThread(Socket socket) {
		this.socket = socket;
		try {
			in = socket.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			int character = in.read();
			while (character != -1) { // -1 returned at the end of the stream
				System.out.print((char) character);
				character = in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void run() {
	//
	// try {
	// BufferedReader in = new BufferedReader(new
	// InputStreamReader(socket.getInputStream()));
	// while (!socket.isClosed()) {
	// String line = in.readLine();
	// if (line == null)
	// System.exit(0);
	// else
	// System.out.println(line);
	// }
	// socket.close();
	// System.exit(0);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
}
