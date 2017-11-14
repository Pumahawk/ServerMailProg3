package client;

import client.gui.CreaMailFrame;
import mail.Mail;

public interface MailAppController {
	
	public void creaMailAction();
	public void rispondiMailAction(Mail m);
	public void cancellaMailAction(int id);
	public void innoltraMailAction(Mail mail, String [] destinatari);
	public void inviaMailAction(CreaMailFrame frame);
	public void apriMailAction(Mail m);
}
