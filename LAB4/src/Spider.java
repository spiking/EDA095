
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

public class Spider {
	private TreeSet<String> visited;
	private Queue<String> toVisit;

	public Spider() {
		visited = new TreeSet<String>();
		toVisit = new LinkedList<String>();
	}

	public void scan(String url) {

		while (visited.size() < 20) {

			String currentUrl;
			Executer leg = new Executer();

			if (toVisit.isEmpty()) {
				currentUrl = url;
				visited.add(currentUrl);
			} else {
				currentUrl = toVisit.poll();
			}

			leg.crawl(currentUrl);
			toVisit.addAll(leg.getLinks());

			System.out.println("Visited size : " + visited.size());
			System.out.println("Visited url : " + currentUrl);

			visited.add(currentUrl);
		}
		System.out.println("\nDone! \n");
		printUrls();
	}

	public void printUrls() {
		for (String url : visited) {
			System.out.println(url);
		}
	}
}