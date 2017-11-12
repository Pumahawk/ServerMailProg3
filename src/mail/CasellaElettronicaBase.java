package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CasellaElettronicaBase extends Remote {
	public void sendMail(String[] destinatari, int priorita, String argomento, String testo) throws RemoteException, CasellaElettronicaException;
	public Mail getMail(int id) throws RemoteException, CasellaElettronicaException;
	public Mail[] getAllMail() throws RemoteException;
	public InfoCasellaElettronica getInfo() throws RemoteException;
	public void addCMailListener(CMailListener l) throws RemoteException; 
}
