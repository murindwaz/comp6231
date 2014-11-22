package ca.concordia.drms.util.task;

import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.orb.RemoteException;

public class ExitTask implements Task {
	private LibraryServer libraryServerReference;
	public ExitTask(LibraryServer libraryServer, String institution, String argument) {
		libraryServerReference = libraryServer;
	}
	public void execute() throws ExitException, RemoteException {
		libraryServerReference._release();
			throw new ExitException("******* Thanks for trying out our service ******* ");
	}
}
