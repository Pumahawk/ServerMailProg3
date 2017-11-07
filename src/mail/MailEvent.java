package mail;

import java.util.EventObject;

public class MailEvent extends EventObject {

	private static final long serialVersionUID = -653054177189779975L;
	
	public final Mail mail;

	public MailEvent(Object arg0, Mail mail) {
		super(arg0);
		this.mail = mail;
	}

}
