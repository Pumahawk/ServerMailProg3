package server;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import server.gui.LoggerGui;

public class ServerApp {
	
	public final static ExecutorService exeService = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());;
	
	public static void main(String[] args) {
		ServerMail serverApp;
		
		Document logDoc = new PlainDocument();
		LoggerGui lg = new LoggerGui(logDoc);
		try {
			serverApp = new ServerMail();

			serverApp.addServerErrorListener(new ServerErrorController());
			serverApp.addServerListener(new ServerLogger(logDoc));
			
			serverApp.createMail("lorenzo@gmail.com");
			serverApp.createMail("lorenzo2@gmail.com");
			serverApp.createMail("lorenzo3@gmail.com");
			
		} catch (RemoteException e) {
			System.err.print("Impossibile creare l'oggetto server.");
			e.printStackTrace();
			serverApp = null;
			System.exit(-1);
		}
		serverApp.initServer();
		lg.setVisible(true);
	}


}
