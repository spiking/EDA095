package Lab3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	private static Socket socket;

	public static void main(String[] args) {
		OutputStream os = null;
		try {
			socket = new Socket("localhost", 30000);
			os = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedReader out = new BufferedReader(new InputStreamReader(System.in));
		ClientThread clientThread = new ClientThread(socket);
		clientThread.start();

		while (true) {
			try {
				byte[] output = out.readLine().getBytes();
				os.write(output);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
