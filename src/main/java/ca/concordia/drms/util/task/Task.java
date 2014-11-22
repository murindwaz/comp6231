package ca.concordia.drms.util.task;

import ca.concordia.drms.orb.RemoteException;

public interface Task{
	public void execute( ) throws ExitException, RemoteException;
}
