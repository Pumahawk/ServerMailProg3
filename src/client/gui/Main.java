package client.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import client.DefaultMailAppController;
import client.ElencoMail;
import mail.InfoCasellaElettronica;
import mail.Mail;

public class Main extends JFrame implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6553295146105580140L;
	final DefaultTableModel tabellaMail;
	final DefaultMailAppController controller;
	
	public Main(InfoCasellaElettronica casellaElettronica, DefaultMailAppController controller) {

		this.controller = controller;
		
		this.setTitle(casellaElettronica.indirizzo);
		this.setSize(700,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.tabellaMail = new DefaultTableModel() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = -594632327419624115L;

			public boolean isCellEditable(int row, int col)
		        { return false; }
		};

		this.tabellaMail.addColumn("ID");
		this.tabellaMail.addColumn("Mittente");
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
		this.tabellaMail.setRowCount(0);
		for(Mail m : ((ElencoMail) o).getAll())
			addMail(m);
	}

	public void addMail(Mail m) {
		Object[] row = { m.id, m.mittente, m.data, m.priorita, m.argomento };
		tabellaMail.insertRow(0, row);
	}
}
