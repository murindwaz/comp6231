package ca.concordia.drms;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.util.*;
import ca.concordia.drms.util.parser.ConsoleParser;
import ca.concordia.drms.util.task.AccountTask;
import ca.concordia.drms.util.task.BonjourTask;
import ca.concordia.drms.util.task.ExitTask;
import ca.concordia.drms.util.task.OverdueTask;
import ca.concordia.drms.util.task.ReservationTask;
import ca.concordia.drms.util.task.Task;
import ca.concordia.drms.util.task.TaskFactory;

/**
 * EclEmma cannot does not cover expected=Exception, because of
 * underlying JaCoCo 
 * @link http://stackoverflow.com/questions/12757559/code-coverage-in-java-with-eclemma-not-scanning-expecting-exception-methods
 * @author murindwaz
 */
public class ClientTest {

	
	private static String BONJOUR_CONCORDIA_STUDENT  = "bonjour -i concordia -r student";
	private static String ACCOUNT_JANE_DANE = "account -f jane -l dane -u jane.dane -p pwd -e jane.dane@dane.com -t 517-789-7890";
	private static String JANE_DANE_RESERVES_HAMLET = "reservation -u jane.dane -p enadenaj -b The Tragedy of Hamlet, Prince of Denmark -a William Shakespeare";
	private static String ADMIN_OVERDUE = "overdue -u admin -p admin -d 10" ;
	
	
	private LibraryServer libraryServer;
	private String institution;
	
	
	@Before
	public void setUp() throws Exception {
		libraryServer = null;
		institution = null;
	}
	
	@After
	public void tearDown(){
		libraryServer = null;
		institution = null;
	}
	
	
	
	@Test 
	public void testCanExecuteBonjourTask() throws IOException{
		/**
		 * shows available commands 
		 * - sets institution 
		 * - sets the server to be used  --- not required 
		 */
		Task task = TaskFactory.create(libraryServer, institution, BONJOUR_CONCORDIA_STUDENT);
		assertTrue( task instanceof BonjourTask );
		assertNotNull( task );
	}
	
	
	@Test 
	public void testCanExecuteOverdueTask() throws IOException{
		Task task = TaskFactory.create(libraryServer, institution,  ADMIN_OVERDUE );
		assertTrue( task instanceof OverdueTask );
		assertNotNull( task );
		
	}
	@Test 
	public void testCanExecuteAccountTask() throws IOException{
		Task task = TaskFactory.create(libraryServer, institution,  ACCOUNT_JANE_DANE);
		assertTrue( task instanceof AccountTask );
		assertNotNull( task );
	}
	@Test 
	public void testCanExecuteReservationTask() throws IOException{
		Task task = TaskFactory.create(libraryServer, institution,  JANE_DANE_RESERVES_HAMLET);
		assertTrue( task instanceof ReservationTask );
		assertNotNull( task );
	}
	@Test 
	public void testCanExecuteExitTask() throws IOException{
		Task task = TaskFactory.create(libraryServer, institution,  "exit" );
		assertTrue( task instanceof ExitTask );
		assertNotNull( task );
	}
	
	@Test 
	public void testAvailableCommandsAndTitle(){
		Map<String,String> help = Configuration.getCommandHelp();
		assertNotNull( help.get("bonjour")); 
		assertNotNull( help.get("overdue")); 
		assertNotNull( help.get("account")); 
		assertNotNull( help.get("reservation")); 
		assertNotNull( help.get("exit")); 
		assertNotNull( Configuration.getApplicationTitle()); 
	}
	
	
	
	@Test 
	public void testCanParseBonjour(){
		 assertTrue(ConsoleParser.parseCommand( BONJOUR_CONCORDIA_STUDENT ).equals("bonjour"));
		 String [] bonjour = ConsoleParser.parseBonjour("bonjour -i concordia -r student");
		 assertTrue( bonjour[0].equals("concordia") ); 
		 assertTrue( bonjour[1].equals("student") == true );
	}
	
	
	@Test 
	public void testCanParseAccount(){
		 Account account = ConsoleParser.parseAccount( ACCOUNT_JANE_DANE);
		 assertTrue(ConsoleParser.parseCommand( ACCOUNT_JANE_DANE ).equals("account"));
		 assertTrue( account.getFirst().equals("jane")) ; 
		 assertTrue( account.getTelephone().equals("517-789-7890"));
		 assertTrue( account.getEmail().equals("jane.dane@dane.com"));
		 assertTrue( account.getTelephone().equals("517-789-7890"));
	}
	
	
	@Test
	public void testCanParseOverdue() {
		 Reservation overdue = ConsoleParser.parseOverdue( ADMIN_OVERDUE );
		 assertTrue( overdue.getAccount().getUsername().equals("admin"));
		 assertTrue( overdue.getAccount().getPassword().equals("admin"));
		 assertEquals(overdue.getDays(), 10);	 
	}	
	
	
	@Test 
	public void testCanParseReservation(){
		 Reservation reservation = ConsoleParser.parseReservation( JANE_DANE_RESERVES_HAMLET );
		 assertTrue(reservation.getBook().getAuthor().equals("William Shakespeare")); 
		 assertTrue(reservation.getBook().getAuthor().equals("William Shakespeare")); 
		 assertTrue(reservation.getBook().getTitle().equals("The Tragedy of Hamlet, Prince of Denmark")); 
		 assertTrue(reservation.getAccount().getUsername().equals("jane.dane"));
		 assertTrue(reservation.getAccount().getPassword().equals("enadenaj"));
	}
	
	
	@Test
	public void testCanParseExit() {
		 assertTrue(ConsoleParser.parseCommand( "exit" ).equals("exit"));
		 assertNull(ConsoleParser.parseCommand( "unexistant" ));
	}
}