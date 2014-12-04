package ca.concordia.drms.util.task;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Map;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;

/**
 * This class will be used to create tasks being used by CommandProcessor.
 * @author murindwaz
 */
public class ReplicaManagerTaskFactory {

	public static Task create(ReplicaManager replicaManager, Map<String, LibraryServerImpl> libraries, NetworkMessage networkMessage, DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws Exception {
		// @todo move these parameters to ReplicaManager-Task
		Task task = null;
		LibraryServerImpl libraryServer = libraries.get(networkMessage.getDestination());
		libraryServer.log(String.format(" %s -- CommandProcessor::run received %s from PORT : %d ",
				libraryServer.getInstitution(), new String(networkMessage.toString()), datagramSocket.getPort()));
		int ctask = Arrays.asList(Configuration.ALLOWED_COMMANDS).indexOf(networkMessage.getOperation());
		switch (ctask) {
			/**
			 * Basic Operations tasks
			 */
			case Configuration.ACCOUNT:
				task = new AccountTask(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
				break;
			case Configuration.OVERDUE:
				task = new OverdueTask(libraryServer, networkMessage);
				break;
			case Configuration.RESERVATION:
				task = new ReservationTask(libraryServer, networkMessage, false);
				break;
			/**
			 * Administrative tasks
			 */
			case Configuration.BYZANTINE:
				/**
				 * @todo run this task whenever there is a message 
				 */
				task = new ByzantineTask(libraryServer, networkMessage);
				((ByzantineTask) task).setReplicaManager(replicaManager);
				break;
			case Configuration.RESYNC:
				task = new ResyncTask(libraryServer, networkMessage);
				//((ResyncTask) task).setRequest(request);
				break;
			case Configuration.REPLICATION:
				/**
				 * This task should be running in background
				 * @todo if possible, move this task to the front-controller .... or run it at start time 
				 */
				task = new ReplicationTask(libraryServer, networkMessage);
				//((ReplicationTask) task).setReplica(request);
				break;
		}
		return task;
	}
}