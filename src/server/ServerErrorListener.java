package server;

import java.util.EventListener;

public interface ServerErrorListener extends EventListener {
	public void erroreCreazioneRegistroRMI(ServerErrorEvent e, Exception ex);
	public void erroreRebindServer(ServerErrorEvent e, Exception ex);
}
