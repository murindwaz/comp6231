package ca.concordia.drms.util.task;

import java.util.Map;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.ReplicaManagerParser;
import ca.concordia.drms.util.Reporter;
import ca.concordia.drms.util.ReservationTransformer;

/**
 * The Overdue task executes the overdue command.
 * This command asks servers to report non-returners they have.
 * @author murindwaz
 */
public class OverdueTask implements Task{
	
	private LibraryServerImpl libraryServer;
	private Reservation overdue;
	
	
	public OverdueTask(LibraryServerImpl libraryServerReference, NetworkMessage message) throws Exception{
		overdue = ReplicaManagerParser.parseOverdue(message.getPayload());
		overdue.getAccount().setInstitution(libraryServerReference.getInstitution());
		libraryServer = libraryServerReference;
	}
	
	
	/**
	 * @throws RemoteException 
	 * @todo check if it is more helpful to start/stop new Thread( new ReturnersReceiver( )).start(); here 
	 * @todo check if ca.concordia.drms.orb.LibraryServer  is not null, else throw an exception
	 */
	public void execute( ) throws RemoteException{
        ca.concordia.drms.orb.Reservation[] _reservations = libraryServer.getNonReturners(overdue.getAccount().getUsername(), overdue.getAccount().getPassword(), libraryServer.getInstitution(), overdue.getDays() );
	    new Reporter(overdue.getAccount()).report(ReservationTransformer.transform(_reservations));
		//@todo notify the ReplicaManager about this task
	}


}