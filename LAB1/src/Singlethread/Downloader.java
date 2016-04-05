package Singlethread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
	private URL url;
	private ArrayList<String> urls;

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
		urls = new ArrayList<String>();
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
						urls.add(link);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Extract the PDF-file name and download the file	
	
		for (String link : urls) {
			int fileNameIndex = link.lastIndexOf("/") + 1;
			String fileName = link.substring(fileNameIndex);
			downloadFile(link, fileName);
		}
	}

	private boolean valid(String s) {
		if (s.matches("javascript:.*|mailto:.*") || !s.contains(".pdf")) {
			return false;
		}
		return true;
	}

	private void downloadFile(String link, String fileName) {

		BufferedInputStream in;
		FileOutputStream out;

		try {

			URL url = new URL(link);
			in = new BufferedInputStream(url.openStream());
			out = new FileOutputStream(new File(fileName));

			byte[] input = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = in.read(input)) > -1) {
				out.write(input, 0, bytesRead);
			}

			out.close();
			out.flush();
			in.close();
			System.out.println("DOWNLOADED : " + fileName + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
