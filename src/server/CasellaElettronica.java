package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.EventListenerList;

import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.CasellaElettronicaException.Error;
import mail.InfoCasellaElettronica;
import mail.Mail;
import mail.MailEvent;
import mail.MailListener;

public class CasellaElettronica extends UnicastRemoteObject implements CasellaElettronicaBase {
	

	public static interface MailEventLauncher {
		public void run(MailListener listener);
	}
	void fireMailListeners(MailEventLauncher run) {
		for(MailListener l : listeners.getListeners(MailListener.class)) {
			run.run(l);
		}
	}
	
	private static final long serialVersionUID = -9167658156962602443L;
	final String indirizzo;
	final AtomicInteger mailCounter;
	final List<Mail> mail;
	private EventListenerList listeners = new EventListenerList();
	private final ServerMail server;
	
	public CasellaElettronica(String indirizzo, ServerMail server) throws RemoteException {
		super();
		this.indirizzo = indirizzo;
		mail = Collections.synchronizedList(new LinkedList<>());
		mailCounter = new AtomicInteger();
		this.server = server;
	}
	@Override
	public void sendMail(Mail mail) throws RemoteException, CasellaElettronicaException{
		Set<String> elencoMail = this.server.caselleList.keySet();
		synchronized(this.server.caselleList) {
			for(String m : mail.destinatari) {
				if(!elencoMail.contains(m))
					throw new CasellaElettronicaException(Error.INVIO_DEST_NOT_FOUND, "Destinatario " + m + " non trovato.");
			}
			for(String m : mail.destinatari) {
				this.server.caselleList.get(m).addMail(mail);
			}
		}
		fireMailInviata(new MailEvent(this, mail));
	}

	@Override
	public Mail getMail(String id) throws RemoteException, CasellaElettronicaException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mail[] getAllMail() throws RemoteException, CasellaElettronicaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfoCasellaElettronica getInfo() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addMail(Mail mail) {
		this.mail.add(mail);
		mailCounter.incrementAndGet();
		fireMailRicevuta(new MailEvent(this, mail));
	}
	
	public void addMailListener(MailListener listener) {
		listeners.add(MailListener.class, listener);
	}

	void fireMailInviata(MailEvent e) {
		fireMailListeners(l -> l.mailInviata(e));
	}
	
	void fireMailRicevuta(MailEvent e) {
		fireMailListeners(l -> l.mailRicevuta(e));
	}
	
	

}
