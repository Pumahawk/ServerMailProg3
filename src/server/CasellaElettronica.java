package server;

import java.rmi.RemoteException;

import mail.CasellaElettronicaBase;
import mail.InfoCasellaElettronica;
import mail.Mail;

public class CasellaElettronica implements CasellaElettronicaBase {

	@Override
	public void sendMail(String[] destinatari, Mail mail) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMail(String destinatari, Mail mail) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Mail getMail(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mail[] getAllMail() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfoCasellaElettronica getInfo() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
