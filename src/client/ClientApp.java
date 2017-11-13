package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import client.gui.Main;
import mail.CasellaElettronicaBase;
import mail.ServerMailBase;

public class ClientApp {
	public final static ExecutorService exeService = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	
	String mail;
	CasellaElettronicaBase caselleElettronica;
	
	
	public ClientApp(String mail, CasellaElettronicaBase c) {
		this.mail = mail;
		this.caselleElettronica = c;
	}
	
	public static void main(String[] args) {
		ServerMailBase server;
		ElencoMail modelloElencoMail = new ElencoMail();
		MailAppController controller = new DefaultMailAppController(modelloElencoMail);
		try {
			server = (ServerMailBase) Naming.lookup("//localhost/AppServer");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			JOptionPane.showMessageDialog(null, "Controllare che il server sia raggiungibile.",
					"Errore collegamento al server.", JOptionPane.ERROR_MESSAGE);
			server = null;
			System.exit(-1);
		}
		try {
			String mainMail = JOptionPane.showInputDialog("Inserire la mail principale.");
			ClientApp app = new ClientApp(mainMail, server.loginMail(mainMail));
			Main main = new Main(app.caselleElettronica.getInfo(), controller);
			modelloElencoMail.addObserver(main);
			main.setVisible(true);
		
		} catch (RemoteException e) {
			System.err.println("Errore login server.");
			e.printStackTrace();
			server = null;
			System.exit(-1);
		}
		
		
	}
}
