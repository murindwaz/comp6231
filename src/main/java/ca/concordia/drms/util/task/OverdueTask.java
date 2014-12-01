package ca.concordia.drms.util.task;

import java.util.Map;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.RemoteException;
<<<<<<< HEAD
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.ReplicaManagerParser;
import ca.concordia.drms.util.Reporter;
import ca.concordia.drms.util.ReservationTransformer;
=======
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.Reporter;
import ca.concordia.drms.util.ReservationTransformer;
import ca.concordia.drms.util.parser.ConsoleParser;
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05

/**
 * The Overdue task executes the overdue command.
 * This command asks servers to report non-returners they have.
 * @author murindwaz
 */
public class OverdueTask implements Task{
	
<<<<<<< HEAD
	private LibraryServerImpl libraryServer;
	private Reservation overdue;
	
	
	public OverdueTask(LibraryServerImpl libraryServerReference, NetworkMessage message) throws Exception{
		overdue = ReplicaManagerParser.parseOverdue(message.getPayload());
		overdue.getAccount().setInstitution(libraryServerReference.getInstitution());
		libraryServer = libraryServerReference;
=======
	private String institution;
	private ca.concordia.drms.orb.LibraryServer libraryServerReference;
	private Reservation overdue;
	
	
	public OverdueTask(ca.concordia.drms.orb.LibraryServer libraryServerReference, String institution, String argument){
		overdue = ConsoleParser.parseOverdue(argument);
		this.institution = institution;
		overdue.getAccount().setInstitution(institution);
		this.libraryServerReference = libraryServerReference;
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	}
	
	
	/**
	 * @throws RemoteException 
	 * @todo check if it is more helpful to start/stop new Thread( new ReturnersReceiver( )).start(); here 
	 * @todo check if ca.concordia.drms.orb.LibraryServer  is not null, else throw an exception
	 */
	public void execute( ) throws RemoteException{
<<<<<<< HEAD
        ca.concordia.drms.orb.Reservation[] _reservations = libraryServer.getNonReturners(overdue.getAccount().getUsername(), overdue.getAccount().getPassword(), libraryServer.getInstitution(), overdue.getDays() );
	    new Reporter(overdue.getAccount()).report(ReservationTransformer.transform(_reservations));
		//@todo notify the ReplicaManager about this task
=======
        Map<String, String> help = Configuration.getCommandHelp();
        ca.concordia.drms.orb.Reservation[] _reservations = libraryServerReference.getNonReturners(overdue.getAccount().getUsername(), overdue.getAccount().getPassword(), institution, overdue.getDays() );
	    new Reporter(overdue.getAccount()).report(ReservationTransformer.transform(_reservations));
		System.out.printf(help.get(Configuration.ALLOWED_COMMANDS[ Configuration.EXIT]), "\r\n >");
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	}


}