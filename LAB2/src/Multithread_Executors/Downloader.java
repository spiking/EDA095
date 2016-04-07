package Multithread_Executors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
	private URL url;
	private HashMap<URL, String> pdfs;
	private static final int MAX_NBR_THREADS = 10;

	public Downloader(String link) {
		try {
			url = new URL(link);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download() {

		BufferedReader br = null;
		pdfs = new HashMap<URL, String>();

		try {

			br = new BufferedReader(new InputStreamReader(new URL(url.toString()).openStream()));
			Pattern pattern = Pattern.compile("(?i)(href=\")(.+?)([^\\/]*.pdf)(\")");
			String line;

			while ((line = br.readLine()) != null) {
				Matcher match = pattern.matcher(line);
				while (match.find()) {
					URL tempURL = createURL(match.group(2) + match.group(3), url);
					if (tempURL != null) {
						pdfs.put(tempURL, match.group(3));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		startRunners();
	}

	private void startRunners() {
		ExecutorService executor = Executors.newFixedThreadPool(MAX_NBR_THREADS);

		for (Entry<URL, String> pair : pdfs.entrySet()) {
			executor.submit(new Runner(pair.getKey(), pair.getValue()));
		}
		
		executor.shutdown();
	}

	private URL createURL(String link, URL url) {
		try {
			return new URL(url, link);
		} catch (Exception e) {
			return null;
		}
	}

}
