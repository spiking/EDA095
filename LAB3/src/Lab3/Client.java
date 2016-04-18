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

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ClientThread clientThread = new ClientThread(socket);
		clientThread.start();

		while (true) {
			try {

				String stringInput = in.readLine();
				byte[] input = stringInput.getBytes();
				os.write(input);
				os.flush();

				if (stringInput.equals("Q:")) {
//					socket.close();
					System.exit(0);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
