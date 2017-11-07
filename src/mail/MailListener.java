package mail;

import java.util.EventListener;

public interface MailListener extends EventListener {
	
	public void mailInviata(MailEvent e);
	
	public void mailRicevuta(MailEvent e);
	
}
