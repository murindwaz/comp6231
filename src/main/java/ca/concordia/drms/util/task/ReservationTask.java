package ca.concordia.drms.util.task;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.*;

public class ReservationTask implements Task {
	
	private Reservation reservation;
	private Map<String, LibraryServerImpl> libraries;
	private LibraryServerImpl libraryServer;
	private ReplicaManager replicaManager;
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private NetworkMessage networkMessage;
	
	/**
	 * @param replicaManager
	 * @param libraries
	 * @param networkMessage
	 * @param datagramSocket
	 * @param datagramPacket
	 * @throws Exception
	 */
	public ReservationTask(ReplicaManager replicaManager, Map<String, LibraryServerImpl> libraries, NetworkMessage networkMessage, DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws Exception {
		this.libraries = libraries;
		this.datagramPacket  =  datagramPacket;
		this.datagramSocket = datagramSocket;
		this.replicaManager = replicaManager;
		this.networkMessage = networkMessage;
		libraryServer = libraries.get(networkMessage.getDestination());
		reservation = ReplicaManagerParser.parseReservation(networkMessage.getPayload());
		//account.setInstitution(libraryServer.getInstitution());
	}
	

	

	/**
	 * Implementation of Task's execute function
	 * @todo Send notification
	 */
	public void execute() throws RemoteException {
		libraryServer.setAcknowledgmentTask(new AcknowledgmentTask(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket) );
		libraryServer.reserveBook(reservation.getAccount().getUsername(), reservation.getAccount().getPassword(), reservation.getBook().getTitle(), reservation.getBook().getAuthor());
        libraryServer.log(String.format("ReservationTask::execute d"));
	}

}