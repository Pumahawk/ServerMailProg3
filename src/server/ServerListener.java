package server;

import java.util.EventListener;

public interface ServerListener extends EventListener {
	public void login(ServerEvent e, String mail);
	public void mailExist(ServerEvent e, String mail);
	public void creazioneMail(ServerEvent e, String mail);
}
