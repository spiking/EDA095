package Server;

public class WriteMailThread extends Thread {
	private Mailbox mailbox;

	public WriteMailThread(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			mailbox.writeMessage("MailThread");
			System.out.println(mailbox.readMessage());

			try {
				Thread.sleep((long) 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
