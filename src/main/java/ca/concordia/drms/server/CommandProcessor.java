package ca.concordia.drms.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.ReplicaManagerParser;
import ca.concordia.drms.util.task.ReplicaManagerTaskFactory;
import ca.concordia.drms.util.task.Task;

/**
 * This class waits for request, reads the message, and delegate to the right caller 
 * @author murindwaz
 */
public class CommandProcessor implements Runnable {
	
	
	private ReplicaManager replica;
	private DatagramSocket socket;
	private LibraryServerImpl libraryServer;
	private Map<String, LibraryServerImpl> libraries;
	public CommandProcessor(ReplicaManager replicaManager, Map<String, LibraryServerImpl> libraryServers, DatagramSocket commandSocket) {
		replica = replicaManager;
		socket = commandSocket;
		libraries = libraryServers;
	}
	
	/**
	 * This function executes server side requests.
	 * NetworkMessage has operation, destination and payload 
	 */
	public void run() {
		byte[] buffer = new byte[ Configuration.BUFFER_SIZE];
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		while(true){
			try {
				socket.receive(request);
				NetworkMessage networkMessage = ReplicaManagerParser.parseNetworkMessage( new String(request.getData()) );
				Task rmtask = ReplicaManagerTaskFactory.create(replica, libraries, networkMessage, socket, request);
				rmtask.execute();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}