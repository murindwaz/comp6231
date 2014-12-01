package ca.concordia.drms.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.log4j.Logger;

import ca.concordia.drms.util.*;
import ca.concordia.drms.util.task.BonjourTask;
import ca.concordia.drms.util.task.ExitException;
import ca.concordia.drms.util.task.Task;
import ca.concordia.drms.util.task.TaskFactory;
import ca.concordia.drms.orb.LibraryServer;



/**
 * This is the client program.
 * It has the console to send commands to the server 
 * @author murindwaz
 */
public class Client {
	
	/**
	 * @param args
	 * @throws SocketException
	 */
	public static void main(String[] args) throws SocketException {
		Logger logger = Logger.getLogger(Client.class);
		logger.info("Starting application ");
		try {
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			LibraryServer libraryServer  = null;
			//session variable/objects	
			boolean session = true;
			String institution = null;
			String argument = "";
			//booting client application
			System.out.println( Configuration.getApplicationTitle() );
			//expires after a certain amount of time 
			while(session){
				logger.info("Starting client's session");
				System.out.printf( Configuration.getCommandHelp().get( Configuration.ALLOWED_COMMANDS[ Configuration.BONJOUR ] ), "\r\n >" );
				boolean valid = false;
				while( !valid ){
					argument = new String( keyboard.readLine() ).trim().replaceAll("\\s+", " ");
					try{
						Task task = TaskFactory.create(libraryServer, institution, argument);
						if( task instanceof BonjourTask ){
							libraryServer = ((BonjourTask)task).getLibraryServerRefence();
							institution  = ((BonjourTask)task).getInstitution();
						}
						if( task != null ){ 
							task.execute( );
						}else{
							System.out.println(" ------ YOU HAVE TO START OVER AGAIN. SKIP REGISTRATION IF YOU ALEADY HAVE AN ACCOUNT WITH US. ---- \r\n > " );
						}
					}catch(ExitException e ){
						valid = false; 
						session = false;
						System.out.println( e.getMessage() );
					}catch(Exception e){
						valid = false;
						System.out.printf( "Client Error :%s \r\n >",  e.getCause()!= null && !e.getCause().getMessage().equals(null)? e.getCause().getMessage() : e.getMessage() );
					}
				}
			}			
			logger.info( "Ending application " );
		}catch(Exception e) {
			logger.debug( "Application fails - Exception " + e.getMessage() );
			e.printStackTrace();
		}
	}
}