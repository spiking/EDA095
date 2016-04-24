import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws MalformedURLException {

		Crawler crawler = new Crawler();
		URL url = new URL("http://cs.lth.se/eda095/");
		ExecutorService executor = Executors.newFixedThreadPool(20);

		for (int i = 0; i < 20; i++) {
			Processor process = new Processor(crawler, url);
			executor.submit(process);
			System.out.println("Thread started!");
		}

		crawler.addURLToRemaining(url.toString());
		executor.shutdown();
	}
}
