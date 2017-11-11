package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;

import mail.Mail.IDMail;

public interface CasellaElettronicaBase extends Remote {
	public void sendMail(String[] destinatari, String oggetto, String testo) throws RemoteException, CasellaElettronicaException;
	public Mail getMail(IDMail id) throws RemoteException, CasellaElettronicaException;
	public Mail[] getAllMail() throws RemoteException;
	public InfoCasellaElettronica getInfo() throws RemoteException;
}
