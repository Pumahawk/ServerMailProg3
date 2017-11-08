package server;

public class ServerErrorController implements ServerErrorListener {

	@Override
	public void erroreCreazioneRegistroRMI(ServerErrorEvent e, Exception ex) {
		System.err.println("Errore creazione RMI.");
		System.err.println("Controllare di aver riavviato il server.");
		ex.printStackTrace();
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
