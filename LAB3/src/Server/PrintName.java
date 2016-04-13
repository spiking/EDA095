package Server;

public class PrintName {
	
	public void createThreads() throws InterruptedException {
		for(int i = 0; i < 10; i++) {
			PrintNameThread runner = new PrintNameThread();
			runner.run();
		}
	}

}
