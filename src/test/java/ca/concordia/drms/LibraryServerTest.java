package ca.concordia.drms;

import static org.junit.Assert.*;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.drms.model.*;
import ca.concordia.drms.util.*;
import ca.concordia.drms.server.*;

public class LibraryServerTest {

	Account jane;
	Account jack;
	Account admin;



	Book theBookOfNegros;
	Book hamlet;
	Book macbeth;
	Book lavale;

	LibraryServerImpl library;
	LibraryServerImpl dawsonLibrary;
	LibraryServerImpl mcgillLibrary;
	
	Reporter reporter;
	

	@Before
	public void setUp() {

		

		jane = new Account();
		jane.setFirst("Jane");
		jane.setLast("Doe");
		jane.setTelephone("514-567-8904");
		jane.setUsername("jane.dane");
		jane.setPassword(new StringBuffer("janedane").reverse().toString());
		jane.setUsername("jane.dane");

		jack = new Account();
		jack.setFirst("Jack");
		jack.setLast("Doe");
		jack.setTelephone("514-908-1234");
		jack.setUsername("jack.dane");
		jack.setPassword(new StringBuffer("jackdane").reverse().toString());

		admin = new Account();
		admin.setFirst("Admin");
		admin.setLast("admin");
		admin.setTelephone("514-809-1234");
		admin.setUsername("admin.admin");
		admin.setPassword(new StringBuffer("admin").reverse().toString());
		admin.setInstitution(Configuration.INSTITUTION_CONCORDIA);
		//will be used for reporting purposes
		reporter = new Reporter( admin );
		
		
		/** all initials + year of publication */
		theBookOfNegros = new Book("The Book of Negroes", "Lawrence Hill", "tbon-lh-2007");
		/** all title initials + year or publication */
		hamlet = new Book("The Tragedy of Hamlet, Prince of Denmark", "William Shakespeare", "ttohpod-ws-1599");
		macbeth = new Book("The Tragedy of Macbeth", "William Shakespeare", "ttom-ws-1606");
		/** Moliere */
		lavale = new Book("L'Avare", "Jean-Baptiste Poquelin", "1668");
		Map<String, Map<String, Account>> accounts = Configuration.initAccounts(Configuration.INSTITUTION_CONCORDIA);
		Map<String, Book> books = Configuration.initBooks(Configuration.INSTITUTION_CONCORDIA);
		library = new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA, books, accounts);
		
		accounts = Configuration.initAccounts(Configuration.INSTITUTION_MCGILL);
		books = Configuration.initBooks(Configuration.INSTITUTION_MCGILL);
		mcgillLibrary = new LibraryServerImpl(Configuration.INSTITUTION_MCGILL, books, accounts);
		
		accounts = Configuration.initAccounts(Configuration.INSTITUTION_DAWSON);
		books = Configuration.initBooks(Configuration.INSTITUTION_DAWSON);
		dawsonLibrary = new LibraryServerImpl(Configuration.INSTITUTION_DAWSON, books, accounts);
	}

	@After
	public void tearDown() {
		jane = null;
		jack = null;
		library = null;
		new File(reporter.getFilePath()).delete();
		reporter = null;
	}

	
	
	
	
	
	@Test
	public void testCanStartLibrary() {
		LibraryServerImpl testLibrary = new LibraryServerImpl();
		assertNotNull(testLibrary);
	}

	@Test
	public void testCanInitLibraryWithAccountsAndBooks() {
		Map<String, Book> books = new HashMap<String, Book>();
		Map<String, Map<String, Account>> accounts = new HashMap<String, Map<String, Account>>();
		LibraryServerImpl testLibrary = new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA, books, accounts);
		assertNotNull(testLibrary);
	}

	@Test
	public void testCanLibraryCreateAccount() throws RemoteException, ca.concordia.drms.orb.RemoteException {
		library.createAccount(jane.getFirst(), jane.getLast(), jane.getEmail(), jane.getTelephone(), jane.getUsername(), jane.getPassword(), Configuration.INSTITUTION_CONCORDIA);
		Account actual = library.findAccount(jane.getUsername(), jane.getPassword());
		assertNull(library.findAccount("dane.jane", "jane.dane"));
		assertNull(library.findAccount(jane.getUsername(), "jane.dane"));
		assertEquals(actual.getUsername(), jane.getUsername());
	}

	@Test
	public void testCanReserveBook() throws RemoteException, ca.concordia.drms.orb.RemoteException {
		HashMap<String, Book> books = new HashMap<String, Book>();
		books.put(theBookOfNegros.getCode(), theBookOfNegros);
		books.put(macbeth.getCode(), macbeth);
		books.put(hamlet.getCode(), hamlet);
		books.put(lavale.getCode(), lavale);
		Map<String, Map<String, Account>> accounts = new HashMap<String, Map<String, Account>>();
		LibraryServerImpl testLibrary = new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA, books, accounts);
		testLibrary.createAccount(jane.getFirst(), jane.getLast(), jane.getEmail(), jane.getTelephone(),
				jane.getUsername(), jane.getPassword(), Configuration.INSTITUTION_CONCORDIA);
		// changing Jack's institution
		jack.setInstitution(Configuration.INSTITUTION_CONCORDIA);
		testLibrary.createAccount(jack.getFirst(), jack.getLast(), jack.getEmail(), jack.getTelephone(),
				jack.getUsername(), jack.getPassword(), jack.getInstitution());
		testLibrary.reserveBook(jane.getUsername(), jane.getPassword(), macbeth.getTitle(), macbeth.getAuthor());
		testLibrary.reserveBook(jack.getUsername(), jack.getPassword(), hamlet.getTitle(), hamlet.getAuthor());
		//assertEquals(testLibrary.getNonReturners( admin.getUsername(), admin.getPassword(), Configuration.INSTITUTION_CONCORDIA, 0 ).size(), 2L);
	}

	
	
	
	
	@Test
	public void testCanGetNonReturners() throws RemoteException, ca.concordia.drms.orb.RemoteException {
		//book shelve
		HashMap<String, Book> books = new HashMap<String, Book>();
		books.put(theBookOfNegros.getCode(), theBookOfNegros);
		books.put(macbeth.getCode(), macbeth);
		books.put(hamlet.getCode(), hamlet);
		books.put(lavale.getCode(), lavale);
		//account directory 
		Map<String, Map<String, Account>> accounts = new HashMap<String,Map<String, Account>>();
		LibraryServerImpl testLibrary = new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA, books, accounts);
		//creating accounts
		testLibrary.createAccount(jane.getFirst(), jane.getLast(), jane.getEmail(), jane.getTelephone(), jane.getUsername(), jane.getPassword(), Configuration.INSTITUTION_CONCORDIA);
		jack.setInstitution(Configuration.INSTITUTION_CONCORDIA);
		testLibrary.createAccount(jack.getFirst(), jack.getLast(), jack.getEmail(), jack.getTelephone(), jack.getUsername(), jack.getPassword(), jack.getInstitution());
		//making reservation
		testLibrary.reserveBook(jane.getUsername(), jane.getPassword(), lavale.getTitle(), lavale.getAuthor());
		testLibrary.reserveBook(jane.getUsername(), jane.getPassword(), macbeth.getTitle(), macbeth.getAuthor());
		testLibrary.reserveBook(jack.getUsername(), jack.getPassword(), hamlet.getTitle(), hamlet.getAuthor());
		//assertEquals(testLibrary.getNonReturners( admin.getUsername(), admin.getPassword(), Configuration.INSTITUTION_CONCORDIA, 10 ).size(), 0L);
		//a couple of days later  --- changing the date 
		testLibrary.setDuration(jane.getUsername(), macbeth.getTitle(), 30);
		testLibrary.setDuration(jack.getUsername(), hamlet.getTitle(), 40);
		//assertEquals(testLibrary.getNonReturners( admin.getUsername(), admin.getPassword(), Configuration.INSTITUTION_CONCORDIA, 10 ).size(), 2L);
		//assertEquals(testLibrary.getNonReturners( admin.getUsername(), admin.getPassword(), Configuration.INSTITUTION_CONCORDIA, 50 ).size(), 0L);
		
		//testing the reporter
		assertNotNull( reporter ); 
		assertFalse( reporter.getFilePath().isEmpty() );
		assertFalse( new File(reporter.getFilePath()).exists() );
		//Map<String, Reservation> reservations = testLibrary.getNonReturners(admin.getUsername(), admin.getPassword(), Configuration.INSTITUTION_CONCORDIA, -1);
		//assertFalse( reservations.isEmpty() );
		//file creation
		//reporter.report( reservations );
		//assertTrue( new File(reporter.getFilePath()).exists() );
	}
	
	

	
}