package ca.concordia.drms.util.task;

import java.util.Map;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.Reporter;
import ca.concordia.drms.util.ReservationTransformer;
import ca.concordia.drms.util.parser.ConsoleParser;

/**
 * The Overdue task executes the overdue command.
 * This command asks servers to report non-returners they have.
 * @author murindwaz
 */
public class OverdueTask implements Task{
	
	private String institution;
	private ca.concordia.drms.orb.LibraryServer libraryServerReference;
	private Reservation overdue;
	
	
	public OverdueTask(ca.concordia.drms.orb.LibraryServer libraryServerReference, String institution, String argument){
		overdue = ConsoleParser.parseOverdue(argument);
		this.institution = institution;
		overdue.getAccount().setInstitution(institution);
		this.libraryServerReference = libraryServerReference;
	}
	
	
	/**
	 * @throws RemoteException 
	 * @todo check if it is more helpful to start/stop new Thread( new ReturnersReceiver( )).start(); here 
	 * @todo check if ca.concordia.drms.orb.LibraryServer  is not null, else throw an exception
	 */
	public void execute( ) throws RemoteException{
        Map<String, String> help = Configuration.getCommandHelp();
        ca.concordia.drms.orb.Reservation[] _reservations = libraryServerReference.getNonReturners(overdue.getAccount().getUsername(), overdue.getAccount().getPassword(), institution, overdue.getDays() );
	    new Reporter(overdue.getAccount()).report(ReservationTransformer.transform(_reservations));
		System.out.printf(help.get(Configuration.ALLOWED_COMMANDS[ Configuration.EXIT]), "\r\n >");
	}


}