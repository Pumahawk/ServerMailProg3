package mail;

import java.io.Serializable;

public class Mail implements Serializable {
	
	public static class IDMail implements Serializable {
		private static final long serialVersionUID = 1L;
		public final int id;
		public final String mittente;
		public IDMail(int id, String mittente) {
			super();
			this.id = id;
			this.mittente = mittente;
		}
		public boolean equals(Object o) {
			//TODO TESTARE
			if(!o.getClass().isInstance(this))
				return false;
			else {
				IDMail i = (IDMail) o;
				return (i.id == this.id && i.mittente.equals(this.mittente));
			}
		}
	}
	
	private static final long serialVersionUID = 6350421221845772435L;
	
	public final IDMail id;
	public final String mittente;
	public final String[] destinatari;
	public final String oggetto;
	public final String testo;
	
	public Mail(IDMail id, String mittente, String[] destinatari, String oggetto, String testo) {
		this.id = id;
		this.mittente = mittente;
		this.destinatari = destinatari;
		this.oggetto = oggetto;
		this.testo = testo;
	}
	
	public Mail(String mittente, String[] destinatari, String oggetto, String testo) {
		this(null, mittente, destinatari, oggetto, testo);
	}
}
