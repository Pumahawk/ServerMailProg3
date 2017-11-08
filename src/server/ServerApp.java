package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.EventListenerList;

import mail.CasellaElettronicaBase;
import mail.ServerMailBase;

public class ServerApp extends UnicastRemoteObject implements ServerMailBase{

	private static final long serialVersionUID = -7448759119983198734L;
	
	Map<String, CasellaElettronica> caselleList;

	public static interface ErrorEventLauncher {
		public void run(ServerErrorListener listener);
	}
	private EventListenerList listeners = new EventListenerList();
	
	public ServerApp(ServerErrorListener controller) throws RemoteException {
		super();
		this.caselleList = Collections.synchronizedMap(new HashMap<String, CasellaElettronica>());
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

	@Override
	public CasellaElettronicaBase loginMail(String mail) throws RemoteException {
		CasellaElettronica c = this.caselleList.get(mail);
		return c;
	}

	@Override
	public boolean mailExist(String mail) throws RemoteException {
		return (this.caselleList.get(mail) != null);
	}

	@Override
	public CasellaElettronicaBase createMail(String mail) throws RemoteException {
		if(this.caselleList.containsKey(mail))
			return null;
		else
			return this.caselleList.put(mail, new CasellaElettronica("mail"));
			
	}

}
