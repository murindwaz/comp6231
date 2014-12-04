package ca.concordia.drms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.Reservation;
import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.parser.ConsoleParser;

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