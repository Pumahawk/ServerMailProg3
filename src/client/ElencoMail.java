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

	public void add(Mail mail) {
		this.elencoMail.add(mail);
		this.setChanged();
		this.notifyObservers(mail);
	}

	public void remove(int id) {
		for (Mail m : this.elencoMail) {
			if (m.id == id) {
				this.elencoMail.remove(m);
				this.notifyObservers(m);
				break;
			}
		}
	}
	
	public Mail[] getAll() {
		return this.elencoMail.toArray(new Mail[0]);
	}
}
