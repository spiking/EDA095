package Lab3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private static Socket socket;

	public static void main(String[] args) {
		OutputStream os = null;
		try {
			socket = new Socket("localHost", 30000);
			os = socket.getOutputStream();
		} catch (Exception e) {

			e.printStackTrace();
		}

		BufferedReader out = new BufferedReader(new InputStreamReader(System.in));
		ClientThread clientThread = new ClientThread(socket);
		clientThread.start();

		while (true) {
			try {
				byte[] input = out.readLine().getBytes();
				os.write(input);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
