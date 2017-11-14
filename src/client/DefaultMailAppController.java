package client;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import client.gui.CreaMailFrame;
import client.gui.ViewMail;
import mail.CMailListener;
import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.Mail;
import mail.CasellaElettronicaException.Error;

public class DefaultMailAppController implements MailAppController {

	final ElencoMail listaMail;
	final CasellaElettronicaBase casellaElettronica;
	
	public class ClickMailController extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			JTable t = (JTable) e.getSource();
			int row = t.rowAtPoint(e.getPoint());
			int id = (Integer) t.getModel().getValueAt(row, 0);
			Mail m = listaMail.getById(id);
			apriMailAction(m);
		}
	}
	
	public class CasellaElettronicaListener extends UnicastRemoteObject implements CMailListener {

		protected CasellaElettronicaListener() throws RemoteException {
			super();
		}

		@Override
		public void nuovaMail(Mail mail) throws RemoteException {
			listaMail.add(mail);
			JOptionPane.showMessageDialog(null, "Hai ricevuto un nuovo messaggio.", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	
	public DefaultMailAppController(CasellaElettronicaBase casela, ElencoMail elenco) {
		this.listaMail = elenco;
		this.casellaElettronica = casela;
	}
	@Override
	public void creaMailAction() {
		JFrame f = new CreaMailFrame(this);
		f.setVisible(true);
	}

	@Override
	public void cancellaMailAction(int id) {
		try {
			this.casellaElettronica.deleteMail(id);
			this.listaMail.remove(id);
		} catch (RemoteException | CasellaElettronicaException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void innoltraMailAction(Mail mail, String[] destinatary) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void inviaMailAction(CreaMailFrame mailFrame) {
		String[] destinatari = mailFrame.destinatari.getText().split(";");
		String oggetto = mailFrame.oggetto.getText();
		Integer priorita = (Integer) mailFrame.priorita.getSelectedItem();
		String testo = mailFrame.testo.getText();
		try {
			
			casellaElettronica.sendMail(destinatari, priorita, oggetto, testo);
			mailFrame.setVisible(false);
			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "Controllare che il server sia raggiungibile.",
					"Errore collegamento al server.", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (CasellaElettronicaException e) {
			if(e.code == Error.INVIO_DEST_NOT_FOUND) {
				JOptionPane.showMessageDialog(null, "Destinatario non trovato.",
						"Errore invio mail.", JOptionPane.ERROR_MESSAGE);
				mailFrame.destinatari.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
		}
	}
	@Override
	public void apriMailAction(Mail m) {
		new ViewMail(m, this).setVisible(true);
	}
	@Override
	public void rispondiMailAction(Mail m) {
		
		String[] dest = {m.mittente};
		new CreaMailFrame(m, this, dest).setVisible(true);
	
	}
	@Override
	public void rispondiATuttiMailAction(Mail m) {
		
		ArrayList<String> dest = new ArrayList<>(m.destinatari.length);
		for(String d : m.destinatari) {
			try {
				if(!d.equals(this.casellaElettronica.getInfo().indirizzo))
					dest.add(d);
			} catch (RemoteException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		dest.add(m.mittente);
		new CreaMailFrame(m, this, dest.toArray(new String[0])).setVisible(true);
		
	}

}
