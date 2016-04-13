package Server;

public class PrintNameThread extends Thread {

	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("PrintNameThread");

			try {
				Thread.sleep((int) (Math.random()*10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
