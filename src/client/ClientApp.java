package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import mail.CasellaElettronicaBase;
import mail.ServerMailBase;

public class ClientApp {
	
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
			app.caselleElettronica = server.loginMail(app.mail);
		} catch (RemoteException e) {
			System.err.println("Errore login server.");
			e.printStackTrace();
			server = null;
			System.exit(-1);
		}
		
		
	}
}
