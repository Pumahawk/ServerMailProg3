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

public class ServerMail extends UnicastRemoteObject implements ServerMailBase {

	private static final long serialVersionUID = -7448759119983198734L;
	
	final Map<String, CasellaElettronica> caselleList;

	public static interface ErrorEventLauncher {
		public void run(ServerErrorListener listener);
	}
	public static interface ServerEventLauncher {
		public void run(ServerListener listener);
	}
	private EventListenerList listeners = new EventListenerList();
	
	public ServerMail() throws RemoteException {
		super();
		this.caselleList = Collections.synchronizedMap(new HashMap<String, CasellaElettronica>());
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
	void fireErrorListeners(ErrorEventLauncher errorRun) {
		for(ServerErrorListener l : listeners.getListeners(ServerErrorListener.class)) {
			errorRun.run(l);
		}
	}
	void fireServerListeners(ServerEventLauncher run) {
		for(ServerListener l : listeners.getListeners(ServerListener.class)) {
			run.run(l);
		}
	}
	
	
	void fireErroreCreazioneRegistroRMI(ServerErrorEvent e, Exception ex) {
		fireErrorListeners(l -> l.erroreCreazioneRegistroRMI(e, ex));
	}
	void fireErroreRebindServer(ServerErrorEvent e, Exception ex) {
		fireErrorListeners(l -> l.erroreRebindServer(e, ex));
	}
	void fireLogin(ServerEvent e, String mail) {
		fireServerListeners(run -> run.login(e, mail));
	}
	void fireMailExist(ServerEvent e, String mail) {
		fireServerListeners(run -> run.mailExist(e, mail));
	}
	void fireCreazioneMail(ServerEvent e, String mail) {
		this.fireCreazioneMail(e, mail, null);
	}
	void fireCreazioneMail(ServerEvent e, String mail, CasellaElettronica casella) {
		fireServerListeners(run -> run.creazioneMail(e, mail, casella));
	}

	public void addServerErrorListener(ServerErrorListener listener) {
		listeners.add(ServerErrorListener.class, listener);
	}
	public void addServerListener(ServerListener listener) {
		listeners.add(ServerListener.class, listener);
	}
	

	@Override
	public CasellaElettronicaBase loginMail(String mail) throws RemoteException {
		fireLogin(new ServerEvent(this), mail);
		CasellaElettronica c = this.caselleList.get(mail);
		return c;
	}

	@Override
	public boolean mailExist(String mail) throws RemoteException {
		fireMailExist(new ServerEvent(this), mail);
		return (this.caselleList.get(mail) != null);
	}

	@Override
	public CasellaElettronicaBase createMail(String mail) throws RemoteException {
		if(this.caselleList.containsKey(mail)) {
			fireCreazioneMail(new ServerEvent(this), mail);
			return null;
		}
		else {
			CasellaElettronica c = new CasellaElettronica(mail, this);
			fireCreazioneMail(new ServerEvent(this), mail, c);
			return this.caselleList.put(mail, c);
		}
			
	}
}
