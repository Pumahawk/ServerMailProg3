package mail;

import java.io.Serializable;

public class InfoCasellaElettronica implements Serializable{
	
	private static final long serialVersionUID = 3264792990637649256L;
	
	public final String indirizzo;
	
	public InfoCasellaElettronica(String indirizzo) {
		this.indirizzo = indirizzo;
	}
}
