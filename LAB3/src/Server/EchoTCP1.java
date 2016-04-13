package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP1 {

	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(30000); // create ServerSocket and
													// bind to port 30000
		} catch (IOException e) {
			e.printStackTrace();
		}

		Socket socket = null;

		try {
			socket = serverSocket.accept(); // accept client request and assign
											// to socket
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("Client address: " + socket.getInetAddress());
			System.out.println("Client port: " + socket.getPort());
			System.out.println("Client hostname: " + socket.getInetAddress().getHostName());

			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			while (true) {
				int bytesRead = 0;
				byte[] input = new byte[1024];

				while ((bytesRead = in.read(input)) > -1) {
					char c = new String(input).charAt(0);
					System.out.print(c);
					out.write(input, 0, bytesRead);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
