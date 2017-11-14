package client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.MailAppController;
import mail.Mail;

public class ViewMail extends JFrame {
	final MailAppController controller;
	public final JTextField oggetto;
	public final JTextField mittente;
	public final JTextField destinatari;
	public final JTextField priorita;
	public final JTextArea testo;

	public ViewMail(Mail m, MailAppController controller) {

		this.controller = controller;
		

		JLabel mittenteLabel = new JLabel("Mittente: ");
		this.mittente = new JTextField(20);
		this.mittente.setText(m.mittente);
		this.mittente.setEditable(false);
		
		JLabel destinatariLabel = new JLabel("Destinatari: ");
		this.destinatari = new JTextField(20);
		String dest = m.destinatari[0];
		for(int i = 1; i < m.destinatari.length; i++) {
			dest += ";" + m.destinatari[i];
		}
		this.destinatari.setText(dest);
		this.destinatari.setEditable(false);

		JLabel oggettoLabel = new JLabel("Oggetto: ");
		this.oggetto = new JTextField(20);
		this.oggetto.setText(m.argomento);
		this.oggetto.setEditable(false);
		
		JLabel prioritaLabel = new JLabel("Priorita: ");
		this.priorita = new JTextField(1);
		this.priorita.setText(m.priorita + "");
		this.priorita.setEditable(false);
		
		JLabel testoLabel = new JLabel("Testo: ");
		this.testo = new JTextArea(25, 45);
		this.testo.setText(m.testo);
		this.testo.setEditable(false);
		JScrollPane srb = new JScrollPane(this.testo);

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));

		JPanel centerRow0Base = new JPanel(new GridLayout(3, 2));
		JPanel centerRow0 = new JPanel();
		JPanel centerRow1 = new JPanel();
		JPanel centerRow2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JPanel centerRow3 = new JPanel();

		centerRow0Base.add(mittenteLabel);
		centerRow0Base.add(this.mittente);
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
		JButton rispondiButton = new JButton("Rispondi");
		rispondiButton.addActionListener(e -> controller.rispondiMailAction(m));
		JButton rispondiATuttiButton = new JButton("Rispondi a tutti");
		rispondiATuttiButton.addActionListener(e -> controller.rispondiATuttiMailAction(m));
		JButton innoltraButton = new JButton("Innoltra");
		JButton cancellaButton = new JButton("Elimina");
		cancellaButton.addActionListener(e -> {
			controller.cancellaMailAction(m.id);
			setVisible(false);
		});
		JButton annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(e -> setVisible(false));
		JPanel south = new JPanel();
		south.add(rispondiButton);
		south.add(rispondiATuttiButton);
		south.add(innoltraButton);
		south.add(cancellaButton);
		south.add(annullaButton);
		
		this.setTitle("ViewMail");
		this.setLayout(new BorderLayout());
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		this.setResizable(false);
		this.pack();
	}
}
