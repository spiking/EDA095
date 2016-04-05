package Singlethread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
	private URL url;
	private HashMap<URL, String> pdfs;

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

		try {

			br = new BufferedReader(new InputStreamReader(new URL(url.toString()).openStream()));
			Pattern pattern = Pattern.compile("(?i)(href=\")(.+?)([^\\/]*.pdf)(\")");
			String line;
			
			pdfs = new HashMap<URL, String>();

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

		for (Entry<URL, String> pair : pdfs.entrySet()) {
			downloadFile(pair.getKey(), pair.getValue());
		}
	}

	public void downloadFile(URL url, String fileName) {
		FileOutputStream fout;
		BufferedInputStream bis;

		try {
			
			System.out.println("STARTS DOWNLOADING FILE!");
			
			fout = new FileOutputStream(new File(fileName));
			bis = new BufferedInputStream(url.openStream());
			
			byte[] buffer = new byte[4096];
			int bytes;
			while ((bytes = bis.read(buffer)) > -1) {
				fout.write(buffer, 0, bytes);
			}

			bis.close();
			fout.flush();
			fout.close();

			System.out.println("DONE WITH " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static URL createURL(String link, URL url) {
		try {
			return new URL(url, link);
		} catch (Exception e) {
			return null;
		}
	}
}
