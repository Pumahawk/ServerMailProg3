package client.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import client.DefaultMailAppController;
import client.ElencoMail;
import client.MailAppController;
import mail.CasellaElettronicaBase;
import mail.InfoCasellaElettronica;
import mail.Mail;

public class Main extends JFrame implements Observer{
	
	final DefaultTableModel tabellaMail;
	final DefaultMailAppController controller;
	
	public Main(InfoCasellaElettronica casellaElettronica, DefaultMailAppController controller) {

		this.controller = controller;
		
		this.setTitle(casellaElettronica.indirizzo);
		this.setSize(700,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.tabellaMail = new DefaultTableModel() {
		    public boolean isCellEditable(int row, int col)
		        { return false; }
		};

		this.tabellaMail.addColumn("ID");
		this.tabellaMail.addColumn("Data");
		this.tabellaMail.addColumn("Priorita");
		this.tabellaMail.addColumn("Argomento");
		
		JPanel p = new JPanel();
		JTable t = new JTable(this.tabellaMail);
		t.addMouseListener(controller.new ClickMailController());
		JScrollPane sp = new JScrollPane(t);
		
		JButton creaMailButton = new JButton("Crea mail");
		creaMailButton.addActionListener((e) -> controller.creaMailAction());
		
		p.add(creaMailButton);
		
		add(new JLabel("Elenco mail"), BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		addMail((Mail) arg);
	}

	public void addMail(Mail m) {
		Object[] row = { m.id, m.data, m.priorita, m.argomento };
		tabellaMail.insertRow(0, row);
	}
}
