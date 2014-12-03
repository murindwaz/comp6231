package ca.concordia.drms.util.task;

import java.net.DatagramPacket;

import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;



/**
 * This task is used to sync Current ReplicaManager servers with remote servers 
 * Received as soon as a request comes in, data in payload section will be channel-led to destination server.
 * 
 * @author murindwaz
 */
public class ResyncTask implements Task {

	
	private DatagramPacket request;
	private LibraryServerImpl libraryServer;
	private NetworkMessage networkMessage;
	
	
	
	/**
	 * @todo check if this
	 * @param libraryServerImpl
	 * @param ntwkmessage
	 */
	public ResyncTask(LibraryServerImpl libraryServerImpl, NetworkMessage ntwkmessage) {
		libraryServer = libraryServerImpl;
		networkMessage = ntwkmessage;
	}

	
	
	public void execute() throws RemoteException {
		//@todo complete this task. 
	}

	public void setRequest(DatagramPacket datagramPacket) {
		request = datagramPacket;
	}

}
