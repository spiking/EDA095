import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Crawler {
	private Set<String> traversedURLs; // gone through
	private Queue<String> remainingURLs; // will go through
	private Set<String> printURLs; // all URLs
	private Set<String> printMails; // all mails

	public Crawler() {
		traversedURLs = new HashSet<String>();
		remainingURLs = new LinkedList<String>();
		printURLs = new HashSet<String>();
		printMails = new HashSet<String>();
	}

	public synchronized String getURLFromRemaining() {
		try {
			System.out.println("Waiting..");
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String url = remainingURLs.poll();
		if (!traversedURLs.contains(url)) {
			traversedURLs.add(url);
			return url;
		}
		return null;
	}

	public synchronized void addURLToRemaining(String URL) {
		remainingURLs.add(URL);
		notifyAll();
	}

	public void printAll() {
		for (String url : traversedURLs) {
			System.out.println(url);
		}

		System.out.println();

		for (String url : printMails) {
			System.out.println(url);
		}

		System.out.println("\nSites traversed: " + traversedURLs.size());
		System.out.println("Sites remaining: " + remainingURLs.size());
	}

	public boolean isDone() {
		return traversedURLs.size() >= 100 ? true : false;
	}

	public synchronized int getPrintMailSize() {
		return printMails.size();
	}

	public synchronized int getPrintUrlSize() {
		return printURLs.size();
	}

	public synchronized void addToPrintURLs(String URL) {
		printURLs.add(URL);
	}

	public synchronized void addToMail(String mail) {
		printMails.add(mail);
	}

	public synchronized int getTraversedSize() {
		return traversedURLs.size();
	}
}
