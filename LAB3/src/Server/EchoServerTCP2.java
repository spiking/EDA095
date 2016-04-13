package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerTCP2 {
	private ServerSocket serverSocket;
	private Socket socket;
	private ExecutorService executor;
	
	public EchoServerTCP2() throws IOException {
		serverSocket = new ServerSocket(30000);
		executor = Executors.newFixedThreadPool(10);
	}
	
	public void run() {
		
		while(true) {
			socket = null;
			
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			StandardThread runner = new StandardThread(socket);
			executor.submit(runner);
			System.out.println("NEW THREAD CREATED!");
		}
	}
 
}
