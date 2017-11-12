package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import mail.CMailListener;
import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.Mail;
import mail.ServerMailBase;

public class ClientApp {
	
	public static class CMailController extends UnicastRemoteObject implements CMailListener  {

		private static final long serialVersionUID = 1L;

		protected CMailController() throws RemoteException {
			super();
		}

		@Override
		public void nuovaMail(Mail mail) throws RemoteException {
			System.out.print("Mail ricevuta: " + mail.mittente);	
			
		}
		
	}
	
	String mail;
	CasellaElettronicaBase caselleElettronica;
	
	
	public ClientApp(String mail) {
		this.mail = mail;
	}
	
	public static void main(String[] args) {
		ServerMailBase server;
		ClientApp app = new ClientApp("lorenzo@gmail.com");
		try {
			server = (ServerMailBase) Naming.lookup("//localhost/AppServer");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			System.err.println("Errore collegamento al server.");
			System.err.println("Controllare che il server sia raggiungibile");
			e.printStackTrace();
			server = null;
			System.exit(-1);
		}
		try {
			server.createMail("lorenzo@gmail.com");
			server.createMail("lorenzo2@gmail.com");
			app.caselleElettronica = server.loginMail("lorenzo@gmail.com");
			CasellaElettronicaBase caselleElettronica2 = server.loginMail("lorenzo2@gmail.com");
			caselleElettronica2.addCMailListener(new CMailController());
			String[] destinatari = {"lorenzo2@gmail.com"};
			app.caselleElettronica.sendMail(destinatari, 0, "testMail", "testo");
			//app.caselleElettronica.getInfo();
			//app.caselleElettronica.getMail(0);
		} catch (RemoteException e) {
			System.err.println("Errore login server.");
			e.printStackTrace();
			server = null;
			System.exit(-1);
		} catch (CasellaElettronicaException e) {
			System.err.println("ERROR CODE: " + e.code);
			e.printStackTrace();
		}
		
		
	}
}
