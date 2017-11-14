package client;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import mail.Mail;

public class ElencoMail extends Observable {
	private final List<Mail> elencoMail;

	public ElencoMail() {
		this.elencoMail = new LinkedList<>();
	}

	public synchronized void add(Mail mail) {
		this.elencoMail.add(mail);
		this.setChanged();
		this.notifyObservers(mail);
	}

	public synchronized void remove(int id) {
		for (Mail m : this.elencoMail) {
			if (m.id == id) {
				this.elencoMail.remove(m);
				this.setChanged();
				this.notifyObservers(m);
				break;
			}
		}
	}
	
	public synchronized Mail[] getAll() {
		return this.elencoMail.toArray(new Mail[0]);
	}

	public synchronized Mail getById(int id) {
		for(Mail m : elencoMail) {
			if(m.id == id) {
				return m;
			}
		}
		return null;
	}
}
