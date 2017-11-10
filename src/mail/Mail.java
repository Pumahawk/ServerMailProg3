package mail;

import java.io.Serializable;

public class Mail implements Serializable{
	
	private static final long serialVersionUID = 6350421221845772435L;
	
	public final String mittente;
	public final String[] destinatari;
	public final String oggetto;
	public final String testo;
	
	public Mail(String mittente, String[] destinatari, String oggetto, String testo) {
		this.mittente = mittente;
		this.destinatari = destinatari;
		this.oggetto = oggetto;
		this.testo = testo;
	}
}
