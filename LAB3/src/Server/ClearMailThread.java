package Server;

public class ClearMailThread extends Thread {
	private Mailbox mailbox;

	public ClearMailThread(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public void run() {
		while (true) {

			try {
				Thread.sleep((long) 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Read: " + mailbox.readMessage());
			System.out.println("Clear: " + mailbox.clearMessage());
		}
	}

}
