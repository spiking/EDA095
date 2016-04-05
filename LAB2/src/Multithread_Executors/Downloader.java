package Multithread_Executors;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Multithread_Executors.Runner;

public class Downloader {
	private URL url;
	private final static int MAX_NBR_THREADS = 7;
	Stack<String> urls;

	public Downloader(String link) {
		try {
			url = new URL(link);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download() {

		long startTime = System.nanoTime();

		System.out.println("DOWNLOADING! \n");
		BufferedReader br = null;
		urls = new Stack<String>();

		try {

			br = new BufferedReader(new InputStreamReader(new URL(url.toString()).openStream()));
			Pattern pattern = Pattern.compile("href=\"(.*?.pdf)"); // PDF-file
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
		ExecutorService executor = Executors.newFixedThreadPool(MAX_NBR_THREADS);
		for (String url : urls) {
			executor.submit(new Runner(url));
		}
		
		executor.shutdown();
	}

	public boolean isEmpty() {
		return urls.isEmpty();
	}

	public String getURL() {
		System.out.println("POP URL : " + urls.peek() + "\n");
		return urls.pop();
	}

	private boolean valid(String s) {
		if (s.matches("javascript:.*|mailto:.*") || !s.contains(".pdf")) {
			return false;
		}
		return true;
	}

}
