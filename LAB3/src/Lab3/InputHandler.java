package Lab3;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

import org.omg.IOP.Encoding;

public class InputHandler {
	private ArrayList<Socket> sockets;

	public InputHandler() {
		sockets = new ArrayList<Socket>();

	}

	public synchronized void addConnection(Socket s) {
		if (!sockets.contains(s)) {
			sockets.add(s);
			System.out.println(s.getInetAddress().getHostName() + " has connected from port " + s.getPort());
		}
	}

	public synchronized void writeMessage(byte[] input, int length, Socket socket) throws UnsupportedEncodingException {

		String command = new String(input, "UTF-8").substring(0, 2);

		if (command.equals("M:")) {
			for (Socket s : sockets) {
				if (!s.equals(socket)) {
					try {

						OutputStream os = s.getOutputStream();
						String user = "\n" + socket.getInetAddress().getHostName() + " " + socket.getPort() + ": ";
						byte[] username = user.getBytes();
						os.write(username, 0, username.length);
						os.write(input, 2, length - 2);
						os.flush();

					} catch (Exception e) {
						sockets.remove(s);
						System.out.println("A client has disconnected.");
					}
				}

			}
		} else if (command.equals("E:")) {
			try {
				OutputStream os = socket.getOutputStream();
				String user = "\n" + socket.getInetAddress().getHostName() + " " + socket.getPort() + ": ";
				byte[] username = user.getBytes();
				os.write(username, 0, username.length);
				os.write(input, 2, length - 2);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("Q:")) {
			try {
				OutputStream os = socket.getOutputStream();
				String stringMsg = "You will now be disconnected!";
				byte[] msg = stringMsg.getBytes();
				os.write(msg, 0, msg.length);
				os.flush();
				socket.close();
				sockets.remove(socket);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {
				OutputStream os = socket.getOutputStream();
				String stringError = "\nUnknown command \n";
				byte[] error = stringError.getBytes();
				os.write(error, 0, error.length);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
