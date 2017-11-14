package client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.MailAppController;
import mail.Mail;

public class CreaMailFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3258612651824464621L;
	final MailAppController controller;
	public final JTextField oggetto;
	public final JTextField destinatari;
	public final JComboBox<Integer> priorita;
	public final JTextArea testo;
	
	public CreaMailFrame(MailAppController controller) {
		
		this.controller = controller;
		
		
		JButton inviaButton = new JButton("Invia");
		JButton annullaButton = new JButton("Annulla");

		JLabel destinatariLabel = new JLabel("Destinatari: ");
		this.destinatari = new JTextField(20);

		JLabel oggettoLabel = new JLabel("Oggetto: ");
		this.oggetto = new JTextField(20);
		
		JLabel prioritaLabel = new JLabel("Priorita: ");
		Integer[] pr = {1,2,3};
		this.priorita = new JComboBox<>(pr);
		
		JLabel testoLabel = new JLabel("Testo: ");
		this.testo = new JTextArea(20, 40);
		JScrollPane srb = new JScrollPane(this.testo);
		
		inviaButton.addActionListener((e) -> controller.inviaMailAction(this));
		annullaButton.addActionListener((e) -> setVisible(false));

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));

		JPanel centerRow0Base = new JPanel(new GridLayout(2,2));
		JPanel centerRow0 = new JPanel();
		JPanel centerRow1 = new JPanel();
		JPanel centerRow2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JPanel centerRow3 = new JPanel();

		centerRow0Base.add(oggettoLabel);
		centerRow0Base.add(this.oggetto);
		centerRow0Base.add(destinatariLabel);
		centerRow0Base.add(this.destinatari);
		centerRow0.add(centerRow0Base);
		centerRow0.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		centerRow1.add(prioritaLabel);
		centerRow1.add(this.priorita);

		centerRow2.add(testoLabel);
		centerRow2.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		centerRow3.add(srb);

		center.add(centerRow0);
		center.add(centerRow1);
		center.add(centerRow2);
		center.add(centerRow3);
		
		JPanel south = new JPanel();
		south.add(inviaButton);
		south.add(annullaButton);
		
		this.setTitle("Crea mail");
		this.setLayout(new BorderLayout());
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		this.setResizable(false);
		this.pack();
	}
	public CreaMailFrame(Mail m, MailAppController controller, String[] destinatari) {
		this(controller);
		String dest = destinatari[0];
		for(int i = 1; i < destinatari.length; i++) {
			dest += ";" + destinatari[i];
		}
		this.destinatari.setText(dest);
		this.oggetto.setText(m.argomento);
		this.priorita.setSelectedItem(m.priorita);
		this.testo.setText(m.testo);
	}
}
