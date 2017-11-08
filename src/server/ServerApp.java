package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.event.EventListenerList;

import mail.ServerMailBase;

public class ServerApp extends UnicastRemoteObject implements ServerMailBase{

	private static final long serialVersionUID = -7448759119983198734L;

	public static interface ErrorEventLauncher {
		public void run(ServerErrorListener listener);
	}
	private EventListenerList listeners = new EventListenerList();
	
	public ServerApp(ServerErrorListener controller) throws RemoteException {
		super();
		addServerErrorListener(controller);
	}
	
	public void initServer(){
		try {
			LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			fireErroreCreazioneRegistroRMI(new ServerErrorEvent(this), e);
		}
		try {
			Naming.rebind("//localhost/AppServer", this);
		} catch (RemoteException | MalformedURLException e) {
			fireErroreRebindServer(new ServerErrorEvent(this), e);
		}
	}
	void fireListeners(ErrorEventLauncher errorRun) {
		for(ServerErrorListener l : listeners.getListeners(ServerErrorListener.class)) {
			errorRun.run(l);
		}
	}
	
	
	void fireErroreCreazioneRegistroRMI(ServerErrorEvent e, Exception ex) {
		fireListeners(l -> l.erroreCreazioneRegistroRMI(e, ex));
	}
	void fireErroreRebindServer(ServerErrorEvent e, Exception ex) {
		fireListeners(l -> l.erroreRebindServer(e, ex));
	}
	
	public void addServerErrorListener(ServerErrorListener listener) {
		listeners.add(ServerErrorListener.class, listener);
	}
	
	public static void main(String[] args) {
		ServerApp serverApp;
		try {
			serverApp = new ServerApp(new ServerErrorController());
		} catch (RemoteException e) {
			System.err.print("Impossibile creare l'oggetto.");
			e.printStackTrace();
			serverApp = null;
			System.exit(-1);
		}
		serverApp.initServer();
	}

}
