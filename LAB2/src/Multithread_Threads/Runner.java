package Multithread_Threads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import Multithread_Threads.Downloader;

public class Runner extends Thread {
	Downloader downloader;

	public Runner(Downloader downloader) {
		this.downloader = downloader;
	}

	@Override
	public void run() {
		BufferedInputStream in;
		FileOutputStream out;

		while (!downloader.isEmpty()) {
			try {
				
				System.out.println("STARTS DOWNLOADING FILE! \n");
				
				URL url = downloader.getURL();
				String fileName = downloader.getFileName(url);

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
}
