package ca.concordia.drms.util.task;

import java.util.Arrays;

import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;

public class ReplicaManagerTaskFactory{
	
	public static Task create(NetworkMessage message, LibraryServerImpl libraryServer) throws Exception{
		Task task = null;
		int ctask = Arrays.asList(Configuration.ALLOWED_COMMANDS).indexOf(message.getOperation());
		switch (ctask) {
			case Configuration.ACCOUNT: 
				task = new AccountTask(libraryServer, message);
			break;
			case Configuration.OVERDUE: 
				task  = new OverdueTask(libraryServer, message);
			break;
			case Configuration.RESERVATION: 
				task  = new ReservationTask(libraryServer, message, false);
			break;
		}
		return task;
	}

}