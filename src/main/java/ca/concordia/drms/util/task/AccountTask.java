package ca.concordia.drms.util.task;

<<<<<<< HEAD
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
=======
import java.util.Map;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.parser.ConsoleParser;

public class AccountTask implements Task {

	private LibraryServer libraryServerReference;
	private String institution;
	private Account account;
	public AccountTask(LibraryServer libraryServer, String institution,String argument) {
		account = ConsoleParser.parseAccount(argument); 
		account.setInstitution(institution);
		libraryServerReference = libraryServer;
		this.institution = institution;
	}
	public void execute() throws RemoteException {
        Map<String, String> help = Configuration.getCommandHelp();
		libraryServerReference.createAccount(account.getFirst(), account.getLast(), account.getEmail(), account.getTelephone(), account.getUsername(), account.getPassword(), institution);
		System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.INTERLIB]), "\r\n" );
		System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.RESERVATION]), "\r\n >" );
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	}
}