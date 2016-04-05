package Multithread_Runnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
	private URL url;
	private HashMap<URL, String> pdfs;
	private Stack<URL> pdfURLS;

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

		pdfURLS = new Stack<URL>();
		for (Entry<URL, String> pair : pdfs.entrySet()) {
			pdfURLS.push(pair.getKey()); // pushes URLS to Stack
		}

		startRunners();
	}

	private void startRunners() {

		Runner r1 = new Runner(this);
		Runner r2 = new Runner(this);
		Runner r3 = new Runner(this);
		Runner r4 = new Runner(this);
		Runner r5 = new Runner(this);
		Runner r6 = new Runner(this);
		Runner r7 = new Runner(this);

		System.out.println("RUNNERS CREATED! \n");

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		Thread t4 = new Thread(r4);
		Thread t5 = new Thread(r5);
		Thread t6 = new Thread(r6);
		Thread t7 = new Thread(r7);

		System.out.println("THREADS CREATED! \n");

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();

		System.out.println("THREADS STARTED! \n");

	}

	public synchronized boolean isEmpty() {
		return pdfURLS.isEmpty();
	}

	public synchronized URL getURL() {
		return pdfURLS.pop();
	}

	public synchronized String getFileName(URL url) {
		return pdfs.get(url);
	}

	private URL createURL(String link, URL url) {
		try {
			return new URL(url, link);
		} catch (Exception e) {
			return null;
		}
	}
}
