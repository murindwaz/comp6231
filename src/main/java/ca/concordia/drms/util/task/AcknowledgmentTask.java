package ca.concordia.drms.util.task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.StringTransformer;




/**
 * This task serves other tasks. 
 * 
 * @author murindwaz
 *
 */
public class AcknowledgmentTask implements Task{
	
	private ReplicaManager replicaManager;
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private NetworkMessage networkMessage;
	private Map<String, LibraryServerImpl> libraries;
	
	
	public AcknowledgmentTask(ReplicaManager replicaManager, Map<String, LibraryServerImpl> libraries, NetworkMessage networkMessage, DatagramSocket datagramSocket, DatagramPacket datagramPacket){
		this.libraries = libraries;
		this.datagramSocket = datagramSocket;
		this.datagramPacket = datagramPacket;
		this.replicaManager = replicaManager;
		this.networkMessage = networkMessage;
	}


	/**
	 * Get NetworkMessage will be used to add more info on current Message
	 */
	public NetworkMessage getNetworkMessage(){
		return networkMessage;
	}
	
	public void execute() throws RemoteException {
		System.out.println( "Called AcknowledgmentTask::execute " + networkMessage.toString() ); 
		if( datagramSocket != null && networkMessage.getPayload().length() > 0 ){
			String message = StringTransformer.getString(networkMessage);
			try {
				datagramSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
			} catch (IOException e) {
				//@todo what to do in this case????
				libraries.get(networkMessage.getDestination()).log( String.format(" --- An Error Happened at AcknowledgmentTask::execute ") );
				e.printStackTrace();
			}
		}
	}
	
}