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

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import client.gui.Main;
import mail.CMailListener;
import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.Mail;
import mail.ServerMailBase;

public class ClientApp {
	public final static ExecutorService exeService = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	
	
	public static class CMailController extends UnicastRemoteObject implements CMailListener  {

		private static final long serialVersionUID = 1L;

		protected CMailController() throws RemoteException {
			super();
		}

		@Override
		public void nuovaMail(Mail mail) throws RemoteException {
			System.out.println("ATTENZIONE");
			System.out.println("Hai ricevuto una nuova mail.");
			System.out.println("Mittente: " + mail.mittente);	
			System.out.println("Oggetto: " + mail.argomento);
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
			JOptionPane.showMessageDialog(null, "Controllare che il server sia raggiungibile.",
					"Errore collegamento al server.", JOptionPane.ERROR_MESSAGE);
			server = null;
			System.exit(-1);
		}
		try {

			DefaultTableModel m = new DefaultTableModel();
			m.addColumn("ID");
			m.addColumn("Data");
			m.addColumn("Priorita");
			m.addColumn("Argomento");
			new Main(m).setVisible(true);
			app.caselleElettronica = server.loginMail("lorenzo@gmail.com");
			app.caselleElettronica.addCMailListener(new CMailController());
			CasellaElettronicaBase caselleElettronica2 = server.loginMail("lorenzo2@gmail.com");
			String[] destinatari = {"lorenzo@gmail.com"};
			caselleElettronica2.sendMail(destinatari, 0, "testMail", "testo");
			for(Mail m2 : app.caselleElettronica.getAllMail()) {
				Object[] row = {m2.id, m2.data, m2.priorita, m2.argomento};
				m.addRow(row);
			}
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
