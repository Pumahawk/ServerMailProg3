package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMailBase extends Remote {
	public CasellaElettronicaBase loginMail(String mail)  throws RemoteException;
	public boolean mailExist(String mail) throws RemoteException;
	public CasellaElettronicaBase createMail(String mail) throws RemoteException;
}
