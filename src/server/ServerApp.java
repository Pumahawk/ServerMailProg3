package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.event.EventListenerList;

public class ServerApp {

	private EventListenerList listeners = new EventListenerList();
	
	public static void initServer() {
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Error");
			System.exit(-1);
		}
	}
	
	public void addServerErrorListener(ServerErrorListener listener) {
		listeners.add(ServerErrorListener.class, listener);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
