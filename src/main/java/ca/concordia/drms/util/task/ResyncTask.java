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

	public ResyncTask(LibraryServerImpl libraryServer, NetworkMessage ntwkmessage) {
		// TODO Auto-generated constructor stub
	}

	public void execute() throws RemoteException {
		
	}

	public void setRequest(DatagramPacket request) {
		// TODO Auto-generated method stub
		
	}

}
