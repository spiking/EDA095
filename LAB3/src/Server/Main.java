package Server;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		// EchoTCP1 echoServer = new EchoTCP1();
		// echoServer.run();

		// EchoServerTCP2 echoServer2 = new EchoServerTCP2();
		// echoServer2.run();

		// PrintName printName = new PrintName();
		// printName.createThreads();

		Mailbox mailbox = new Mailbox();

		for (int i = 0; i < 10; i++) {
			WriteMailThread writeRunner = new WriteMailThread(mailbox);
			writeRunner.run();
			ClearMailThread clearRunner = new ClearMailThread(mailbox);
			clearRunner.start();
		}
	}

}
