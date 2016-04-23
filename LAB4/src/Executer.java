import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Executer {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document document;

	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document document = connection.get();
			this.document = document;

			if (connection.response().statusCode() == 200) {
				System.out.println("\nVisiting received web page at " + url);
			}

			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("Failure");
				return false;
			}

			Elements linksOnPage = document.select("a[href]");
			System.out.println("Found " + linksOnPage.size() + " links");
			for (Element link : linksOnPage) {
				links.add(link.absUrl("href"));
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<String> getLinks() {
		return links;
	}

}