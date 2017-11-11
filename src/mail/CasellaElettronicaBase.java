package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;

import mail.Mail.IDMail;

public interface CasellaElettronicaBase extends Remote {
	public void sendMail(Mail mail) throws RemoteException, CasellaElettronicaException;
	public Mail getMail(IDMail id) throws RemoteException, CasellaElettronicaException;
	public Mail[] getAllMail() throws RemoteException;
	public InfoCasellaElettronica getInfo() throws RemoteException;
}
