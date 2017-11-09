package server;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import mail.ServerEvent;
import mail.ServerListener;

public class ServerLogger implements ServerListener {
	
	Document lg;

	
	public ServerLogger(Document lg) {
		this.lg = lg;
	}

	void println(String s) {
		try {
			this.lg.insertString(lg.getEndPosition().getOffset()-1, s + '\n', null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	@Override
	public synchronized void login(ServerEvent e, String mail) {
		println("<ServerController:login>");
		println("Richiesta login mail: " + mail);
	}

	@Override
	public synchronized void mailExist(ServerEvent e, String mail) {
		println("<ServerController:logimailExist>");
		println("Richiesta verifica esistenza mail: " + mail);
		
	}

	@Override
	public synchronized void creazioneMail(ServerEvent e, String mail) {
		println("<ServerController:creazioneMail>");
		println("Richiesta creazione mail: " + mail);
		
	}

}
