package server;

import mail.ServerEvent;
import mail.ServerListener;

public class ServerLogger implements ServerListener{

	@Override
	public void login(ServerEvent e, String mail) {
		System.out.println("<ServerController:login>");
		System.out.println("Richiesta login mail: " + mail);
	}

	@Override
	public void mailExist(ServerEvent e, String mail) {
		System.out.println("<ServerController:logimailExist>");
		System.out.println("Richiesta verifica esistenza mail: " + mail);
		
	}

	@Override
	public void creazioneMail(ServerEvent e, String mail) {
		System.out.println("<ServerController:creazioneMail>");
		System.out.println("Richiesta creazione mail: " + mail);
		
	}

}
