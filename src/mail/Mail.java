package mail;

import java.io.Serializable;

public class Mail implements Serializable{
	
	private static final long serialVersionUID = 6350421221845772435L;
	
	public final String oggetto;
	public final String testo;
	
	public Mail(String oggetto, String testo) {
		this.oggetto = oggetto;
		this.testo = testo;
	}
}
