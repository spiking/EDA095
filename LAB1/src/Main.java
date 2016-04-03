
public class Main {

	public static void main(String[] args) {

		String url = "http://cs.lth.se/eda095/foerelaesningar/?no_cache=1";
		Downloader goAndGetThemFiles = new Downloader(url);
		goAndGetThemFiles.download();

	}
}
