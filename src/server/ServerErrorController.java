package server;

import javax.swing.JOptionPane;

public class ServerErrorController implements ServerErrorListener {

	@Override
	public void erroreCreazioneRegistroRMI(ServerErrorEvent e, Exception ex) {
		System.err.println("Errore creazione RMI.");
		System.err.println("Controllare di aver riavviato il server.");
		ex.printStackTrace();
		JOptionPane.showMessageDialog(null, "Impossibile avviare il registro RMI.\nRiavviare il server.",
				"Errore inizializzazione server.", JOptionPane.ERROR_MESSAGE);
		System.exit(-1);
	}

	@Override
	public void erroreRebindServer(ServerErrorEvent e, Exception ex) {
		System.err.println("Errore RebindServer .");
		System.err.println("Controllare di aver avviato il registro RMI.");
		ex.printStackTrace();
		System.exit(-1);
	}

}
