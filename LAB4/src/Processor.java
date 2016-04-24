import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor extends Thread {
	private Crawler crawler;
	private URL url;
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public Processor(Crawler crawler, URL url) {
		this.crawler = crawler;
		this.url = url;
	}

	public void run() {

		while (!crawler.isDone()) {
			String nextURL = crawler.getURLFromRemaining();
			if (nextURL != null) {
				try {
					url = new URL(nextURL);
					Connection connection = Jsoup.connect(url.toString()).userAgent(USER_AGENT);
					Document document = connection.ignoreContentType(true).get();
					// Document document =
					// connection.ignoreContentType(true).ignoreHttpErrors(true).get();

					if (connection.response().contentType().contains("text/html")) {

						System.out.println("\nReceived webpage at " + url);
						Elements linksOnPage = document.select("a[href]");
						Elements linksOnPageFrame = document.select("frame");
						System.out.println("Found " + linksOnPage.size() + " links");
						System.out.println("Found " + linksOnPageFrame.size() + " frame links");

						// System.out.println();
						// System.out.println("PRINT URLS SIZE : " +
						// crawler.getPrintUrlSize());
						// System.out.println("PRINT MAIL SIZE : " +
						// crawler.getPrintMailSize());

						for (Element link : linksOnPage) {
							if (link.toString().contains("mailto")) {
								crawler.addToPrintMails(link.absUrl("href"));
							} else {
								// crawler.addToPrintURLs(link.absUrl("href"));
								crawler.addURLToRemaining(link.absUrl("href"));
							}
						}
					} else {
						System.out.println("\n***************************\n\nNOT AN HTML FILE! \n" + url
								+ "\n\n***************************\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
