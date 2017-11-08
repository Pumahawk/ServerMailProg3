package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CasellaElettronicaBase extends Remote {
	public void sendMail(String[] destinatari, Mail mail) throws RemoteException;
	public void sendMail(String destinatari, Mail mail) throws RemoteException;
	public Mail getMail(String id);
	public Mail[] getAllMail() throws RemoteException;
	public InfoCasellaElettronica getInfo() throws RemoteException;
}
