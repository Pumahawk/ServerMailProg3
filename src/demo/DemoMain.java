package demo;

import client.ClientApp;
import server.ServerApp;

public class DemoMain {
	Thread server = new Thread(() -> ServerApp.main(null));
	Thread client = new Thread(() -> ClientApp.main(null));

	public static void main(String[] args) throws InterruptedException {
		DemoMain demo = new DemoMain();
		
		demo.server.start();
		
		Thread.sleep(1000);
		
		demo.client.start();
	}
}
