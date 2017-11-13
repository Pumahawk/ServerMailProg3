package client;

import mail.Mail;

public interface MailAppController {
	public void creaMailAction();
	public void cancellaMailAction(int id);
	public void innoltraMailAction(Mail mail);
}
