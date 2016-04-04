package Multithread_Threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
	private URL url;
	Stack<String> urls;

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
		urls = new Stack<String>();
		try {

			br = new BufferedReader(new InputStreamReader(new URL(url.toString()).openStream()));

			Pattern pattern = Pattern.compile("href=\"(.*?.pdf)"); // PDF-file
																	// links
			String line;
			while ((line = br.readLine()) != null) {
				Matcher match = pattern.matcher(line);
				while (match.find()) {
					String link = match.group(1);
					if (valid(link)) {
						// System.out.println(link);
						urls.push(link);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		startRunners();
	}

	private void startRunners() {
		System.out.println("Runners creat!");
		
		Runner r1 = new Runner(this);
		Runner r2 = new Runner(this);
		Runner r3 = new Runner(this);
		Runner r4 = new Runner(this);
		Runner r5 = new Runner(this);
		Runner r6 = new Runner(this);
		Runner r7 = new Runner(this);
		
		System.out.println("Runners start!");
		
		r1.start();
		r2.start();
		r3.start();
		r4.start();
		r5.start();
		r6.start();
		r7.start();
	}

	public boolean isEmpty() {
		return urls.isEmpty();
	}

	public synchronized String getURL() {
		return urls.pop();
	}

	private boolean valid(String s) {
		if (s.matches("javascript:.*|mailto:.*") || !s.contains(".pdf")) {
			return false;
		}
		return true;
	}
}
