package mail;

import java.io.Serializable;

public final class CasellaElettronicaException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static enum Error {
		GENERAL,
		INVIO_DEST_NOT_FOUND
	}
	
	public final Error code;
	public final String message;
	
	public CasellaElettronicaException(Error code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
