package ca.concordia.drms.util.task;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.ReplicaManagerParser;

public class AccountTask implements Task {

	private Account account;
	private LibraryServerImpl libraryServer;
	public AccountTask(LibraryServerImpl libraryServerReference, NetworkMessage message) throws Exception{
		account = ReplicaManagerParser.parseAccount(message.getPayload());
		libraryServer = libraryServerReference;
		account.setInstitution(libraryServer.getInstitution());
	}
	//@todo send notifications 
	public void execute() throws RemoteException {
        libraryServer.createAccount(account.getFirst(), account.getLast(), account.getEmail(), account.getTelephone(), account.getUsername(), account.getPassword(), libraryServer.getInstitution());
		//@todo notify the ReplicaManager about this task
	}
}