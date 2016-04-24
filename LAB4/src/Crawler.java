import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Crawler {
	private Set<String> traversedURLs; // gone through
	private Queue<String> remainingURLs; // will go through
	private Set<String> printURLs; // all URLs
	private Set<String> printMails; // all mails

	public Crawler() {
		traversedURLs = new TreeSet<String>();
		remainingURLs = new LinkedList<String>();
		printURLs = new TreeSet<String>();
		printMails = new TreeSet<String>();
	}

	public synchronized String getURLFromRemaining() {
		String url = remainingURLs.poll();
		if (!traversedURLs.contains(url)) {
			traversedURLs.add(url);
			return url;
		}
		return null;
	}

	public synchronized void addURLToRemaining(String URL) {
		remainingURLs.add(URL);
		printURLs.add(URL);
		notifyAll();
	}

	public void printAll() {
		for (String url : printURLs) {
			System.out.println(url);
		}

		System.out.println();

		for (String url : printMails) {
			System.out.println(url);
		}

		System.out.println("\nSites traversed: " + traversedURLs.size());
		System.out.println("Sites remaining: " + remainingURLs.size());
		System.out.println("Mail found: " + printMails.size());
		// System.out.println("URLs found: " + printURLs.size());
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
		notifyAll();
	}

	public synchronized void addToPrintMails(String mail) {
		printMails.add(mail);
		notifyAll();
	}

}
