package Multithread_Executors;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import Multithread_Executors.Downloader;

public class Runner implements Runnable {
	private URL url;
	private String fileName;

	public Runner(URL url, String fileName) {
		this.url = url;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		BufferedInputStream in;
		FileOutputStream out;

		try {

			System.out.println("STARTS DOWNLOADING FILE! \n");

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
