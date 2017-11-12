package mail;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

public interface CMailListener extends EventListener, Remote {
	public void nuovaMail(Mail mail) throws RemoteException;
}
