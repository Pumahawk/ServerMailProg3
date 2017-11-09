package server;

import java.rmi.RemoteException;

public class ServerApp {

	
	public static void main(String[] args) {
		ServerMail serverApp;
		try {
			serverApp = new ServerMail(new ServerErrorController());
			serverApp.addServerListener(new ServerController());
		} catch (RemoteException e) {
			System.err.print("Impossibile creare l'oggetto server.");
			e.printStackTrace();
			serverApp = null;
			System.exit(-1);
		}
		serverApp.initServer();
	}


}
