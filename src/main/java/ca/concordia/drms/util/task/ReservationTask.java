package ca.concordia.drms.util.task;

<<<<<<< HEAD
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.model.Reservation;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.ReplicaManagerParser;

public class ReservationTask implements Task {
	
	private LibraryServerImpl libraryServer;
=======
import java.util.Map;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.util.*;
import ca.concordia.drms.util.parser.ConsoleParser;

public class ReservationTask implements Task {
	private LibraryServer libraryServerReference;
	private String institution;
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	private Reservation reservation;
	private boolean interlib;
	
	/**
<<<<<<< HEAD
	 * @param libraryServerImpl
	 * @param message
	 * @throws Exception 
	 */
	public ReservationTask(LibraryServerImpl libraryServerImpl, NetworkMessage message) throws Exception {
		libraryServer = libraryServerImpl;
		reservation = ReplicaManagerParser.parseReservation(message.getPayload());
=======
	 * @param libraryServer
	 * @param institution
	 * @param argument
	 */
	public ReservationTask(LibraryServer libraryServer, String institution, String argument) {
		this.institution = institution;
		libraryServerReference = libraryServer;
		reservation = ConsoleParser.parseReservation(argument);
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
		interlib = false;
	}
	
	/**
	 * @param libraryServer
	 * @param institution
	 * @param argument
	 * @param interlib
<<<<<<< HEAD
	 * @throws Exception 
	 */
	public ReservationTask(LibraryServerImpl libraryServer, NetworkMessage message, boolean interlib) throws Exception {
		this( libraryServer, message);
=======
	 */
	public ReservationTask(LibraryServer libraryServer, String institution, String argument, boolean interlib) {
		this( libraryServer, institution, argument);
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
		this.interlib = interlib;
	}
	
	/**
	 * Implementation of Task's execute function
<<<<<<< HEAD
	 * @todo Send notification
	 */
	public void execute() throws RemoteException {
		//@todo notify the ReplicaManager about this task
		if( interlib == true ){
	        libraryServer.reserveInterLibrary(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}else{
	        libraryServer.reserveBook(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}
=======
	 */
	public void execute() throws RemoteException {
        Map<String, String> help = Configuration.getCommandHelp();
		if( interlib == true ){
	        libraryServerReference.reserveInterLibrary(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}else{
	        libraryServerReference.reserveBook(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}
		System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.EXIT]), "\r\n >" );
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	}

}