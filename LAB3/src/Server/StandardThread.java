package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class StandardThread extends Thread {
	private Socket socket;

	public StandardThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try {
			System.out.println("Client address: " + socket.getInetAddress());
			System.out.println("Client port: " + socket.getPort());
			System.out.println("Client hostname: " + socket.getInetAddress().getHostName());

			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			while (true) {
				int bytesRead = 0;
				byte[] input = new byte[1024];

				while ((bytesRead = in.read()) > -1) {
					char c = new String(input).charAt(0);
					System.out.println(c);
					out.write(input, 0, bytesRead);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
