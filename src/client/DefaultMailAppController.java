package client;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.gui.CreaMailFrame;
import mail.CMailListener;
import mail.CasellaElettronicaBase;
import mail.CasellaElettronicaException;
import mail.Mail;
import mail.CasellaElettronicaException.Error;

public class DefaultMailAppController implements MailAppController {

	final ElencoMail listaMail;
	final CasellaElettronicaBase casellaElettronica;
	
	public class CasellaElettronicaListener extends UnicastRemoteObject implements CMailListener {

		protected CasellaElettronicaListener() throws RemoteException {
			super();
		}

		@Override
		public void nuovaMail(Mail mail) throws RemoteException {
			listaMail.add(mail);
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
		// TODO Auto-generated method stub
		
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
			JOptionPane.showMessageDialog(null, "Hai ricevuto un nuovo messaggio.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
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

}
