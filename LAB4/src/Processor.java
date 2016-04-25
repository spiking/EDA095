import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor extends Thread {
	private Crawler crawler;
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public Processor(Crawler crawler, URL url) {
		this.crawler = crawler;
	}

	public void run() {

		while (!crawler.isDone()) {
			String nextURL = crawler.getURLFromRemaining();
			if (nextURL != null) {
				try {
					URL url = new URL(nextURL);
					URLConnection urlc = url.openConnection();
					Document document = Jsoup.connect(url.toString()).timeout(10*1000).userAgent(USER_AGENT).get();

					if (urlc.getContentType().contains("text/html")) {

						System.out.println("\nReceived webpage at " + url);
						Elements linksOnPage = document.select("a[href]");
						Elements linksOnPageFrame = document.select("frame");
						System.out.println("Found " + linksOnPage.size() + " links");
						System.out.println("Found " + linksOnPageFrame.size() + " frame links");
						System.out.println("Traversed " + crawler.getTraversedSize() + " links");
						
						for (Element link : linksOnPage) { // not handling frames
							if (link.toString().contains("mailto")) {
								crawler.addToMail(link.absUrl("href"));
							} else {
								crawler.addURLToRemaining(link.absUrl("href"));
								crawler.addToPrintURLs(link.absUrl("href"));
							}
						}
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			System.out.println(this.getName() + " is done \n");
		}

		System.out.println("Completly done! \n");
	}
}
