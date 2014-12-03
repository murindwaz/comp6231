package ca.concordia.drms.util.task;

import java.net.DatagramPacket;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;



/**
 * This task will be called when the sequencer sends to us a message with byzantine operation.
 * This task will make the replica manager do following tasks:
 * 	- Kill servers running on this ReplicaManager instance
 * @author murindwaz
 */
public class ByzantineTask implements Task {

	
	private LibraryServerImpl libraryServer; 
	private NetworkMessage networkMessage;
	private DatagramPacket request;
	private ReplicaManager replicaManager;
	
	
	public ByzantineTask(LibraryServerImpl libraryServerImpl, NetworkMessage ntwkmessage) {
		// TODO Auto-generated constructor stub
		libraryServer = libraryServerImpl;
		networkMessage = ntwkmessage;
	}

	
	/**
	 * @todo implement this section, to send shutdown request.
	 * @todo we need the ReplicaManager to shutdown thought
	 */
	public void execute() throws RemoteException {
		try{
			replicaManager.reboot();
		}catch(Exception e){
			//@todo try to reboot() again? or just give up and shutdown the entire machine??? 
		}
	}
	
	/**
	 * @param datagramPacket
	 */
	public void setReplicaManager(ReplicaManager rm){
		replicaManager = rm;
	}

}