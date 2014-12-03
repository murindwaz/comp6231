package ca.concordia.drms.util.task;

import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.Map;

import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.ReplicaManagerParser;

public class ReplicaManagerTaskFactory {

	public static Task create(DatagramPacket request, Map<String, LibraryServerImpl> libraries) throws Exception {
		// @todo move these parameters to ReplicaManager-Task
		Task task = null;
		NetworkMessage ntwkmessage = ReplicaManagerParser.parseNetworkMessage(new String(request.getData()));
		LibraryServerImpl libraryServer = libraries.get(ntwkmessage.getDestination());
		libraryServer.log(String.format(" %s -- CommandProcessor::run received %s from PORT : %d ",
				libraryServer.getInstitution(), new String(request.getData()), request.getPort()));
		int ctask = Arrays.asList(Configuration.ALLOWED_COMMANDS).indexOf(ntwkmessage.getOperation());
		switch (ctask) {
		/**
		 * Basic Operations tasks
		 */
		case Configuration.ACCOUNT:
			task = new AccountTask(libraryServer, ntwkmessage);
			break;
		case Configuration.OVERDUE:
			task = new OverdueTask(libraryServer, ntwkmessage);
			break;
		case Configuration.RESERVATION:
			task = new ReservationTask(libraryServer, ntwkmessage, false);
			break;
		/**
		 * Administrative tasks
		 */
		case Configuration.BYZANTINE:
			/**
			 * @todo run this task whenever there is a message 
			 */
			task = new ByzantineTask(libraryServer, ntwkmessage);
			((ByzantineTask) task).setRequest(request);
			break;
		case Configuration.RESYNC:
			task = new ResyncTask(libraryServer, ntwkmessage);
			((ResyncTask) task).setRequest(request);
			break;
		case Configuration.REPLICATION:
			/**
			 * This task should be running in background
			 * @todo if possible, move this task to the front-controller .... or run it at start time 
			 */
			task = new ReplicationTask(libraryServer, ntwkmessage);
			((ReplicationTask) task).setReplica(request);
			break;
		}
		return task;
	}

}