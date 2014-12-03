package ca.concordia.drms.util.task;

import java.net.DatagramPacket;

import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;


/**
 * This task will be used to update current ReplicaManager, after a ResyncTask sends a request to do so
 * @author murindwaz
 */
public class ReplicationTask implements Task {
	
	private DatagramPacket request;
	private LibraryServerImpl libraryServer;
	private NetworkMessage networkMessage;
	
	/**
	 * @todo change the NetworkMessage into a DatagramPacket, since it is much more complete to work with.
	 * 	     This packet should have a NetworkMessage in it though 
	 *  The DatagramRequest have the IP address of the sender.  
	 * @param libraryServerImpl
	 * @param ntwkmessage
	 */
	public ReplicationTask(LibraryServerImpl libraryServerImpl, NetworkMessage ntwkmessage) {
		networkMessage = ntwkmessage;
		libraryServer = libraryServerImpl;	
	}

	public void execute() throws RemoteException {
		//throw an exception if this thing cannot get the request/DatagramPacket thing 
	}

	public void setReplica(DatagramPacket datagramPacket) {
		request = datagramPacket;
	}

}
