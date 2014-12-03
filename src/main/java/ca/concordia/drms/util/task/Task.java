package ca.concordia.drms.util.task;

import ca.concordia.drms.orb.RemoteException;



/**
 * This interface represents the base task configuration.
 * Any request, will be delegated to the proper task executor
 * @author murindwaz
 */
public interface Task{
	public void execute( ) throws RemoteException;
}
