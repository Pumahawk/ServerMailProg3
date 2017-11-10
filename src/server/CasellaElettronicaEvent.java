package server;

import java.util.EventObject;

public class CasellaElettronicaEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public static enum Code {
		GET_ALL_MAIL_REQUEST,
		GET_INFO_REQUEST
	}

	public final Code code;
	
	public CasellaElettronicaEvent(Object arg0, Code code) {
		super(arg0);
		this.code = code;
	}

}
