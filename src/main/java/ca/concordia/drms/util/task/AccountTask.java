package ca.concordia.drms.util.task;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.ReplicaManagerParser;

public class AccountTask implements Task {

	private Account account;
	private Map<String, LibraryServerImpl> libraries;
	private LibraryServerImpl libraryServer;
	private ReplicaManager replicaManager;
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private NetworkMessage networkMessage;
	public AccountTask(ReplicaManager replicaManager, Map<String, LibraryServerImpl> libraries, NetworkMessage networkMessage, DatagramSocket datagramSocket, DatagramPacket datagramPacket) throws Exception{
		this.libraries = libraries;
		this.datagramPacket  =  datagramPacket;
		this.datagramSocket = datagramSocket;
		this.replicaManager = replicaManager;
		this.networkMessage = networkMessage;
		account = ReplicaManagerParser.parseAccount(networkMessage.getPayload());
		libraryServer = libraries.get(networkMessage.getDestination());
		account.setInstitution(libraryServer.getInstitution());
	}
	
	public void execute() throws RemoteException {
		libraryServer.setAcknowledgmentTask(new AcknowledgmentTask(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket) );
        libraryServer.createAccount(account.getFirst(), account.getLast(), account.getEmail(), account.getTelephone(), account.getUsername(), account.getPassword(), libraryServer.getInstitution());
        libraryServer.log(String.format("AccountTask::execute d"));
	}
}