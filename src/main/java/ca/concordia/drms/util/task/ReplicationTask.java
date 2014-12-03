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

	public ReplicationTask(LibraryServerImpl libraryServer, NetworkMessage ntwkmessage) {
		// TODO Auto-generated constructor stub
	}

	public void execute() throws RemoteException {
		//@todo implement me ... 
	}

	public void setReplica(DatagramPacket request) {
		// TODO Auto-generated method stub
		
	}

}
