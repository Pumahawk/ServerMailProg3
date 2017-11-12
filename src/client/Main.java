package client;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

public class Main extends JFrame implements Observer{
	
	ListModel<String> elencoMail;
	public Main(ListModel<String> modelloMail) {
		elencoMail = modelloMail;
		
		
		this.setSize(200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		JList<String> l = new JList<>(elencoMail);
		//p.add(l);
		add(l, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
