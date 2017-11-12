package mail;

import java.io.Serializable;

public class Mail implements Serializable {
	
	private static final long serialVersionUID = 6350421221845772435L;
	
	public final int id;
	public final String data;
	public final String mittente;
	public final String[] destinatari;
	public final int priorita;
	public final String testo;
	public final String argomento;
	
	public Mail(int id, String data, String mittente, String[] destinatari, int priorita, String argomento, String testo) {
		this.id = id;
		this.mittente = mittente;
		this.destinatari = destinatari;
		this.argomento = argomento;
		this.testo = testo;
		this.priorita = priorita;
		this.data = data;
	}
}
