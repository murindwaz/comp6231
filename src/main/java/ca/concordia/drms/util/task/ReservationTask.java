package ca.concordia.drms.util.task;

import java.util.Map;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.*;

public class ReservationTask implements Task {
	
	private LibraryServerImpl libraryServer;
	private Reservation reservation;
	private boolean interlib;
	
	/**
	 * @param libraryServerImpl
	 * @param message
	 * @throws Exception 
	 */
	public ReservationTask(LibraryServerImpl libraryServerImpl, NetworkMessage message) throws Exception {
		libraryServer = libraryServerImpl;
		reservation = ReplicaManagerParser.parseReservation(message.getPayload());
		interlib = false;
	}
	
	/**
	 * @param libraryServer
	 * @param institution
	 * @param argument
	 * @param interlib
	 * @throws Exception 
	 */
	public ReservationTask(LibraryServerImpl libraryServer, NetworkMessage message, boolean interlib) throws Exception {
		this( libraryServer, message);
		this.interlib = interlib;
	}
	
	/**
	 * Implementation of Task's execute function
	 * @todo Send notification
	 */
	public void execute() throws RemoteException {
		//@todo notify the ReplicaManager about this task
		if( interlib == true ){
	        libraryServer.reserveInterLibrary(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}else{
	        libraryServer.reserveBook(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
		}
	}

}