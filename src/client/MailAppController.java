package client;

import client.gui.CreaMailFrame;
import mail.Mail;

public interface MailAppController {
	
	public void creaMailAction();
	public void cancellaMailAction(int id);
	public void innoltraMailAction(Mail mail, String [] destinatari);
	public void inviaMailAction(CreaMailFrame frame);
}
