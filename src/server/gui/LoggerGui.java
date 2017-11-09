package server.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

public class LoggerGui extends JFrame {
	
	private static final long serialVersionUID = -6840281351723960790L;
	
	public final Document document;

	public LoggerGui(Document doc) {
		this.document = doc;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 400);
		JTextArea textArea = new JTextArea(this.document);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);
	}
}
