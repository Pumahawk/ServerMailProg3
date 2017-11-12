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
import javax.swing.table.TableModel;

public class Main extends JFrame implements Observer{
	
	TableModel elencoMail;
	public Main(TableModel modelloMail) {
		elencoMail = modelloMail;
		
		
		this.setSize(700,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JTable t = new JTable(elencoMail);
		t.setEnabled(false);
		JScrollPane sp = new JScrollPane(t);
		p.add(new JButton("Crea mail"));
		add(new JLabel("Elenco mail"), BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
