package Lab3;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

import org.omg.IOP.Encoding;

public class ClientHandler {
	private ArrayList<Socket> sockets;

	public ClientHandler() {
		sockets = new ArrayList<Socket>();
	}

	public synchronized void addConnection(Socket s) {
		if (!sockets.contains(s)) {
			sockets.add(s);
			System.out.println(s.getInetAddress().getHostName() + " has connected from port " + s.getPort());
		}
	}
	
	public synchronized void removeConnection(Socket s) {
		if (sockets.contains(s)) {
			sockets.remove(s);
			System.out.println(s.getInetAddress().getHostName() + " at port " + s.getPort() + " has been removed!");
		}
	}
	
	public synchronized ArrayList<Socket> getSockets() {
		return sockets;
	}
}
