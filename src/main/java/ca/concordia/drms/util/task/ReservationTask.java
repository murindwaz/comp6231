package ca.concordia.drms.util.task;

import java.util.Map;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.util.*;
import ca.concordia.drms.util.parser.ConsoleParser;

public class ReservationTask implements Task {
	private LibraryServer libraryServerReference;
	private String institution;
	private Reservation reservation;
	private boolean interlib;
	
	/**
	 * @param libraryServer
	 * @param institution
	 * @param argument
	 */
	public ReservationTask(LibraryServer libraryServer, String institution, String argument) {
		this.institution = institution;
		libraryServerReference = libraryServer;
		reservation = ConsoleParser.parseReservation(argument);
		interlib = false;
	}
	
	/**
	 * @param libraryServer
	 * @param institution
	 * @param argument
	 * @param interlib
	 */
	public ReservationTask(LibraryServer libraryServer, String institution, String argument, boolean interlib) {
		this( libraryServer, institution, argument);
		this.interlib = interlib;
	}
	
	/**
	 * Implementation of Task's execute function
	 */
	public void execute() throws RemoteException {
        Map<String, String> help = Configuration.getCommandHelp();
		if( interlib == true ){
	        libraryServerReference.reserveInterLibrary(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}else{
	        libraryServerReference.reserveBook(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}
		System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.EXIT]), "\r\n >" );
	}

}