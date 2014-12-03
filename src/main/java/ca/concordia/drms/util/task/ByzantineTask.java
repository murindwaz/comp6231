package ca.concordia.drms.util.task;

import java.net.DatagramPacket;

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

	public ByzantineTask(LibraryServerImpl libraryServer, NetworkMessage ntwkmessage) {
		// TODO Auto-generated constructor stub
	}

	public void execute() throws RemoteException {
		
	}

	public void setRequest(DatagramPacket request) {
		// TODO Auto-generated method stub
		
	}

}