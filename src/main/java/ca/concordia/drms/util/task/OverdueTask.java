package ca.concordia.drms.util.task;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import ca.concordia.drms.ReplicaManager;
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
	
	private Reservation overdue;
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
	public OverdueTask(ReplicaManager replicaManager, Map<String, LibraryServerImpl> libraries,
			NetworkMessage networkMessage, DatagramSocket datagramSocket, DatagramPacket datagramPacket)  throws Exception{
		this.libraries = libraries;
		this.datagramPacket  =  datagramPacket;
		this.datagramSocket = datagramSocket;
		this.replicaManager = replicaManager;
		this.networkMessage = networkMessage;
		libraryServer = libraries.get(networkMessage.getDestination());
		overdue = ReplicaManagerParser.parseOverdueFromPaload(networkMessage.getPayload());
		overdue.getAccount().setInstitution(libraryServer.getInstitution());
	}


	/**
	 * @throws RemoteException 
	 */
	public void execute( ) throws RemoteException{
		libraryServer.setAcknowledgmentTask(new AcknowledgmentTask(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket) );
        ca.concordia.drms.orb.Reservation[] _reservations = libraryServer.getNonReturners(overdue.getAccount().getUsername(), overdue.getAccount().getPassword(), libraryServer.getInstitution(), overdue.getDays() );
	    new Reporter(overdue.getAccount()).report(ReservationTransformer.transform(_reservations));
        libraryServer.log(String.format("OverdueTask::execute d"));
	}

}