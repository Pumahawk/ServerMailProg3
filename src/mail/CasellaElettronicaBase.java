package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CasellaElettronicaBase extends Remote {
	public void sendMail(Mail mail) throws RemoteException, CasellaElettronicaException;
	public Mail getMail(String id) throws RemoteException, CasellaElettronicaException;
	public Mail[] getAllMail() throws RemoteException, CasellaElettronicaException;
	public InfoCasellaElettronica getInfo() throws RemoteException;
}
