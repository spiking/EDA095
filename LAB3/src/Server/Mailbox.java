package Server;

public class Mailbox {
	private String message;
	
	public Mailbox() {
		message = "";
	}
	
	public synchronized String readMessage() {
		return message;
	}
	
	public synchronized void writeMessage(String message) {
		this.message += message;
	}
	
	public synchronized String clearMessage() {
		return message = "";
	}
}
