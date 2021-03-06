package Lab3;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

public class MessageHandler {
	private ClientHandler clientHandler;

	public MessageHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
	}

	public synchronized void writeMessage(byte[] input, int length, Socket socket) throws UnsupportedEncodingException {

		String command = new String(input, "UTF-8").substring(0, 2);
		ArrayList<Socket> sockets = clientHandler.getSockets();

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
						clientHandler.removeConnection(s);
						System.out.println("Could not send message to a client, thus disconnected.");
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
				System.out.println("A client has disconnected (by valid command).");
				clientHandler.removeConnection(socket);
				socket.close();
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
