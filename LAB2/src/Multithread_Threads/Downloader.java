package Multithread_Threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Multithread_Runnable.*;

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

		System.out.println("DOWNLOADING! \n");

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

		r1.start();
		r2.start();
		r3.start();
		r4.start();
		r5.start();
		r6.start();
		r7.start();

		System.out.println("RUNNERS STARTED! \n");

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

	public static URL createURL(String link, URL url) {
		try {
			return new URL(url, link);
		} catch (Exception e) {
			return null;
		}
	}
}
