package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.EventListenerList;

import mail.CMailListener;
import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.CasellaElettronicaException.Error;
import mail.InfoCasellaElettronica;
import mail.Mail;
import mail.MailEvent;
import mail.MailListener;
import server.CasellaElettronicaEvent;

public class CasellaElettronica extends UnicastRemoteObject implements CasellaElettronicaBase {
	

	public static interface MailEventLauncher {
		public void run(MailListener listener);
	}
	void fireMailListeners(MailEventLauncher run) {
		for(MailListener l : listeners.getListeners(MailListener.class)) {
			run.run(l);
		}
	}
	public static interface CasellaElettronicaEventLauncher {
		public void run(CasellaElettronicaListener listener);
	}
	void fireCasellaElettronicaListener(CasellaElettronicaEventLauncher run) {
		for(CasellaElettronicaListener l : listeners.getListeners(CasellaElettronicaListener.class)) {
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
	public void sendMail(String[] destinatari,int priorita, String argomento, String testo) throws RemoteException, CasellaElettronicaException{
		Mail mail = new Mail(this.mailCounter.get(), "dataTemp", this.indirizzo, destinatari, priorita, argomento, testo);
		Set<String> elencoMail = this.server.caselleList.keySet();
		synchronized(this.server.caselleList) {
			for(String m : destinatari) {
				if(!elencoMail.contains(m))
					throw new CasellaElettronicaException(Error.INVIO_DEST_NOT_FOUND, "Destinatario " + m + " non trovato.");
			}
			for(String m : destinatari) {
				this.server.caselleList.get(m).addMail(mail);
			}
		}
		fireMailInviata(new MailEvent(this, mail));
	}

	@Override
	public Mail getMail(int id) throws RemoteException, CasellaElettronicaException {
		//TODO TESTARE
		CasellaElettronicaException error = new CasellaElettronicaException(Error.ID_MAIL_NOT_EXIST, "ID mail errato");;
		
		synchronized (this.mail) {
			for(Mail t : this.mail)
				if(t.id == id) {
					fireRequestPerformed(new CasellaElettronicaEvent(this, CasellaElettronicaEvent.Code.GET_MAIL_REQUEST));
					return t;
				}
		}
		throw error;
	}

	@Override
	public Mail[] getAllMail() throws RemoteException {
		fireRequestPerformed(new CasellaElettronicaEvent(this, CasellaElettronicaEvent.Code.GET_ALL_MAIL_REQUEST));
		return this.mail.toArray(new Mail[0]);
	}

	@Override
	public InfoCasellaElettronica getInfo() throws RemoteException {
		fireRequestPerformed(new CasellaElettronicaEvent(this, CasellaElettronicaEvent.Code.GET_INFO_REQUEST));
		return new InfoCasellaElettronica(indirizzo);
	}

	public void addMail(Mail mail) {
		this.mail.add(mail);
		mailCounter.incrementAndGet();
		fireMailRicevuta(new MailEvent(this, mail));
		fireNuovaCMail(mail);
	}

	public void addMailListener(MailListener listener) {
		listeners.add(MailListener.class, listener);
	}
	public void addCasellaElettronicaListener(CasellaElettronicaListener listener) {
		listeners.add(CasellaElettronicaListener.class, listener);
	}

	void fireMailInviata(MailEvent e) {
		fireMailListeners(l -> l.mailInviata(e));
	}
	
	void fireMailRicevuta(MailEvent e) {
		fireMailListeners(l -> l.mailRicevuta(e));
	}
	
	void fireRequestPerformed(CasellaElettronicaEvent e) {
		fireCasellaElettronicaListener(l -> l.actionPerformed(e));
	}
	
	void fireNuovaCMail(Mail mail) {
		for(CMailListener l :listeners.getListeners(CMailListener.class)) {
			ServerApp.exeService.execute(() -> {
				try {
					l.nuovaMail(mail);
				} catch (RemoteException e) {
					this.listeners.remove(CMailListener.class, l);
				}
			});
		}
	}
	@Override
	public void addCMailListener(CMailListener l) throws RemoteException {
		listeners.add(CMailListener.class, l);
		
	}
	@Override
	public void deleteMail(int id) throws RemoteException, CasellaElettronicaException {
		for(Mail m : this.mail)
			if(m.id == id) {
				this.mail.remove(m);
				return;
			}
		throw new CasellaElettronicaException(Error.ID_MAIL_NOT_EXIST, "Impossibile cancellare la mail. Id non trovato");
	}
}
