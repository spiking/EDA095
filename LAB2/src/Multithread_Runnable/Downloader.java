package Multithread_Runnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Multithread_Runnable.Runner;

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
