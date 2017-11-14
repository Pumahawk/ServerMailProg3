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

import client.DefaultMailAppController.CasellaElettronicaListener;
import client.gui.Main;
import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.ServerMailBase;
import mail.CasellaElettronicaException.Error;
import mail.Mail;

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
		try {
			server = (ServerMailBase) Naming.lookup("//localhost/AppServer");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			JOptionPane.showMessageDialog(null, "Controllare che il server sia raggiungibile.",
					"Errore collegamento al server.", JOptionPane.ERROR_MESSAGE);
			server = null;
			System.exit(-1);
		}
		for(boolean open = false; !open;)
			try {
				String mainMail = JOptionPane.showInputDialog("Inserire la mail principale.");
				if(mainMail == null)
					break;
				ClientApp app = new ClientApp(mainMail, server.loginMail(mainMail));
				DefaultMailAppController controller = new DefaultMailAppController(app.caselleElettronica, modelloElencoMail);
				if(app.caselleElettronica == null)
					throw new CasellaElettronicaException(Error.CASELLA_NOT_FOUND, "Casella elettronica non trovata");
				open = true;
				Main main = new Main(app.caselleElettronica.getInfo(), controller);
				modelloElencoMail.addObserver(main);
				for(Mail m : app.caselleElettronica.getAllMail())
					modelloElencoMail.add(m);
				app.caselleElettronica.addCMailListener(controller.new CasellaElettronicaListener());
				main.setVisible(true);
			
			} catch (RemoteException e) {
				System.err.println("Errore login server.");
				e.printStackTrace();
				server = null;
				System.exit(-1);
			} catch (CasellaElettronicaException e) {
				if(e.code == Error.CASELLA_NOT_FOUND)
					JOptionPane.showMessageDialog(null, "Impossibile aprire la mail.",
							"Errore apertura mail.", JOptionPane.ERROR_MESSAGE);
			}
		
		
	}
}
