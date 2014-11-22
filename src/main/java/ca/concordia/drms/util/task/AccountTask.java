package ca.concordia.drms.util.task;

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
	}
}