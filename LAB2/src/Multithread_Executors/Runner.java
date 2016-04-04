package Multithread_Executors;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import Multithread_Executors.Downloader;

public class Runner implements Runnable {
	private String stringURL;

	public Runner(String stringURL) {
		this.stringURL = stringURL;
	}

	@Override
	public void run() {
		BufferedInputStream in;
		FileOutputStream out;

			try {

				String fileName = getFileName(stringURL);
				URL url = new URL(stringURL);

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

	private String getFileName(String url) {
		int fileNameIndex = url.lastIndexOf("/") + 1;
		return url.substring(fileNameIndex);
	}

}

