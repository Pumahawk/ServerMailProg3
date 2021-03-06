package server;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import mail.MailEvent;
import mail.MailListener;

public class ServerLogger implements ServerListener, MailListener, CasellaElettronicaListener {
	
	Document lg;

	
	public ServerLogger(Document lg) {
		this.lg = lg;
	}

	void println(String s) {
		try {
			this.lg.insertString(lg.getEndPosition().getOffset()-1, s + '\n', null);
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	@Override
	public synchronized void login(ServerEvent e, String mail) {
		println("Richiesta login mail: " + mail);
	}

	@Override
	public synchronized void mailExist(ServerEvent e, String mail) {
		println("Richiesta verifica esistenza mail: " + mail);
		
	}

	@Override
	public synchronized void creazioneMail(ServerEvent e, String mail, CasellaElettronica casella) {
		println("Richiesta creazione mail: " + mail);
		if(casella != null) {
			casella.addMailListener(this);
			casella.addCasellaElettronicaListener(this);
		}
		
	}

	@Override
	public synchronized void mailInviata(MailEvent e) {
		println("Invio mail:");
		println("--> Mittente: " + e.mail.mittente);
		println("--> Destinatari:");
		for(String destinatario : e.mail.destinatari)
			println("------> " + destinatario);
	}

	@Override
	public synchronized void mailRicevuta(MailEvent e) {
		println("Ricezione mail:");
		println("--> CasellaPosta: " + ((CasellaElettronica)e.getSource()).indirizzo);
		println("--> Mittente: " + e.mail.mittente);
		
	}

	@Override
	public synchronized void actionPerformed(CasellaElettronicaEvent e) {
		switch (e.code) {
		case GET_ALL_MAIL_REQUEST:
			println("Richieste tutte le email da: " + ((CasellaElettronica)e.getSource()).indirizzo);
			break;
		case GET_INFO_REQUEST:
			println("Richieste informazioni casella elettronica da: " + ((CasellaElettronica)e.getSource()).indirizzo);
			break;
		case GET_MAIL_REQUEST:
			println("Richiesta mail da : " + ((CasellaElettronica)e.getSource()).indirizzo);
			break;
		case REMOVE_MAIL:
			println("Richiesta eliminazione mail da : " + ((CasellaElettronica)e.getSource()).indirizzo);
			break;
		}
		
	}

}
