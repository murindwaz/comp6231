package ca.concordia.drms.util.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.omg.CORBA.ORB;

import ca.concordia.drms.orb.*;
import ca.concordia.drms.util.*;
import ca.concordia.drms.util.parser.ConsoleParser;


/**
 * Bonjour Task is used by the client to establish connection with a given remote server.
 * @author murindwaz
 */
public class BonjourTask implements Task {

	
	
	private LibraryServer libraryServerRefence;
	private String institution; 
	private String role;
	
	public BonjourTask(String argument) throws IOException{
		String[] bonjour = ConsoleParser.parseBonjour(argument);
		role = bonjour[1];
		institution = bonjour[0];
		ORB orb = ORB.init((String[])null, null);
		BufferedReader breader = new BufferedReader( new FileReader( String.format("ior-%s.txt", institution)) ) ;
		org.omg.CORBA.Object reference = orb.string_to_object( breader.readLine() );
		libraryServerRefence = LibraryServerHelper.narrow(reference);
		breader.close();
	}	
	
	public LibraryServer getLibraryServerRefence(){
		return libraryServerRefence;
	}
	
	public String getInstitution(){
		return institution;
	}
	
	public void execute() throws RemoteException{
        Map<String, String> help = Configuration.getCommandHelp();
		if( role.equals(Configuration.ROLE_ADMIN) ){
			System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.OVERDUE]), "\r" );
		}else if( role.equals(Configuration.ROLE_STUDENT) ){
			System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.ACCOUNT]), "\r" );
			System.out.printf( help.get(Configuration.ALLOWED_COMMANDS[ Configuration.RESERVATION]), "\r" );
		}
		System.out.printf(help.get(Configuration.ALLOWED_COMMANDS[ Configuration.EXIT]), "\r\n >");
	}

}