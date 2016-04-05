package Singlethread;

public class Main {

	public static void main(String[] args) {

		String url = "http://cs229.stanford.edu/materials.html";
		Downloader goAndGetThemFiles = new Downloader(url);
		goAndGetThemFiles.download();

	}
}
