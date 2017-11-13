package client;

import mail.Mail;

public class DefaultMailAppController implements MailAppController {

	ElencoMail listaMail;
	public DefaultMailAppController(ElencoMail elenco) {
		this.listaMail = elenco;
	}
	@Override
	public void creaMailAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancellaMailAction(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void innoltraMailAction(Mail mail) {
		// TODO Auto-generated method stub
		
	}

}
