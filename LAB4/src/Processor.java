import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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
					URLConnection urlConn = url.openConnection();
					Document document = Jsoup.connect(url.toString()).timeout(10 * 1000).userAgent(USER_AGENT).get();

					if (urlConn.getContentType().contains("text/html")) {
						
						System.out.println("\nReceived webpage at (url) " + url);
						Elements links = document.getElementsByTag("a");
						Elements frames = document.select("frame");
						System.out.println("Found " + links.size() + " links");
						System.out.println("Found " + frames.size() + " frame links");
						System.out.println("Total traversed " + crawler.getTraversedSize() + " links");

						links.forEach(link -> {
							String absHref = link.attr("abs:href"); 
							String relHref = link.attr("href"); 
//							System.out.println("absHref: " + absHref + "\n" + "relHref: " + relHref + "\n" + "text: \n");
							
							if(absHref.length() + relHref.length() > 1) {
								if (link.toString().contains("mailto")) {
									crawler.addToMail(link.absUrl("href"));
								} else {
									crawler.addURLToRemaining(link.absUrl("href")); 
									crawler.addToPrintURLs(link.absUrl("href"));
								}
							} else {
//								System.out.println("No valid link! \n");
							}
							
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			System.out.println(this.getName() + " is done \n");
		}

		System.out.println("Completly done! \n");
	}

	void parse(String stringUrl) throws IOException {
		URL url = new URL("http://cs.lth.se/eda095/");
		InputStream is = url.openStream();
		Document doc = Jsoup.parse(is, "UTF-8", "http://cs.lth.se/");
		Elements base = doc.getElementsByTag("base");
		System.out.println("Base : " + base);
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			String linkHref = link.attr("href");
			String linkAbsHref = link.attr("abs:href");
			String linkText = link.text();
			System.out.println("href: " + linkHref + "abshref: " + linkAbsHref + " text: " + linkText);
		}
		is.close();
	}
}
