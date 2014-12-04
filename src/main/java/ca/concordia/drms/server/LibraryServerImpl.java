package ca.concordia.drms.server;

import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ca.concordia.drms.ReplicaManager;
import ca.concordia.drms.model.*;
import ca.concordia.drms.orb.RemoteException;
import ca.concordia.drms.util.*;
import ca.concordia.drms.util.task.AccountActivity;
import ca.concordia.drms.util.task.AcknowledgmentTask;

/**
 * @todo rename LibraryServer to LibraryServerImpl, and LibraryServerImpl to LibraryServerImpl
 * @todo - Refactor this server -- move all library based tasks to
 *       LibraryService and inject it in here
 *       
 * @Link http://stackoverflow.com/questions/3153337/how-do-i-get-my-current-working-directory-in-java      
 * @link http://stackoverflow.com/questions/13011892/how-to-locate-the-path-of-the-current-project-in-java-eclipse      
 * @author murindwaz
 */
public class LibraryServerImpl extends ca.concordia.drms.orb.LibraryServerPOA {

	private String institution;
	private Map<String, Map<String, Account>> accounts;
	private Map<String, Book> books;
	private Map<String, Reservation> reservations;
	private final Logger logger;
	private String[] nodes;
	private AcknowledgmentTask acknowledgmentTask;
	
	

	/**
	 * Kept to be able to send request to other servers
	 * @return
	 */
	public String[] getNodes(){
		return this.nodes;
	}
	public void setNodes(String[] nodes) {
		this.nodes = nodes;
	}

	
	
	
	public LibraryServerImpl() {
		this(Thread.currentThread().getName());
	}
	
	public LibraryServerImpl(String institution) {
		//for the logger
		System.setProperty( "history.log", System.getProperty("user.dir") +"/server-"+institution+".log" );
		PropertyConfigurator.configure("log4j.properties"); 
		logger = Logger.getLogger(LibraryServerImpl.class);
		this.institution = institution;
		accounts = new HashMap<String, Map<String, Account>>();
		reservations = new HashMap<String, Reservation>();
		logger.info("New Instance created for " + institution );
	}
	
	
	/**
	 * @param institution
	 * @param books
	 * @param accounts
	 */
	public LibraryServerImpl(String institution, Map<String, Book> books, Map<String, Map<String, Account>> accounts) {
		this(institution);
		this.accounts = accounts;
		this.books = books;
		reservations = new HashMap<String, Reservation>();
	}

	/**
	 * Getters/Setters 
	 */
	public String getInstitution() {
		return institution;
	}

	public Map<String, Map<String, Account>> getAccounts() {
		return accounts;
	}

	public Map<String, Book> getBooks() {
		return books;
	};

	public Map<String, Reservation> getReservations() {
		return reservations;
	};

	
	
	
	public void log( String message ) {
		logger.info(message);
	};	

	/**
	 * Things that this function has to do: - Check library's HashMap of
	 * accounts - Append
	 * @throws RemoteException 
	 */
	public void createAccount(String first, String last, String email, String telephone, String username,
			String password, String institution) throws RemoteException {
		/**
		 * @warning Critical point 
		 */
		logger.debug( String.format("Creating account for %s %s - %s %s %s", first, last, email, telephone, institution) );
		String initial = username.substring(0, 1).toUpperCase();
		if ( accounts != null && !accounts.containsKey(initial)) {
			accounts.put(initial, new HashMap<String, Account>());
			logger.debug( String.format("Adding Account", first, last, email, telephone, institution) );
		}
		
		//
		if( username == null || username.trim().equals("") || findAccountByUsername(username) instanceof Account ){
			//@todo update the message before sending the message back to the client
			//acknowledgmentTask.getNetworkMessage().setPayload(payload);
			//acknowledgmentTask.execute();
			throw new RemoteException("Account'username has been used, please try with a different username");
		}
		/**
		 * @warning Critical point 
		 * @todo use a username instead
		 * @todo add a test for two guys having the same name
		 * @todo use username and password generator instead of receiving it
		 * @todo make sure the client gets a copy of username and password after
		 *       creating an account
		 */
		if (!accounts.get(initial).containsKey(first)) {
			Account account = new Account();
			account.setFirst(first);
			account.setLast(last);
			account.setEmail(email);
			account.setUsername(username);
			account.setTelephone(telephone);
			account.setPassword(password);
			account.setInstitution(institution);
			accounts.get(initial).put(username, account);
			//Added this code to allow the ReplicaManager to notify the Sequencer about the outcome of the operation
			acknowledgmentTask.getNetworkMessage().setPayload(StringTransformer.getString(account));
			acknowledgmentTask.execute();
			//log account activity 
			new AccountActivity(account).logActivity("Account Successfully created");
			logger.debug(account.toString());
		}
		
	}

	/**
	 * These two functions will be used to make it possible to get accounts from
	 * Library's Accounts table
	 * 
	 * @param username
	 * @param password
	 * @return Account
	 */
	public Account findAccount(String username, String password) {
		Account account = findAccountByUsername(username);
		if( account instanceof Account && !account.getPassword().equals(password)){
			account = null;
		}
		return account;
	}

	/**
	 * Will be used to lookup a user by username
	 * @param String username
	 * @return Account account
	 */
	private Account findAccountByUsername(String username) {
		String initial = username.substring(0, 1).toUpperCase();
		synchronized (this) {
			if (accounts != null && accounts.containsKey(initial)) {
				return accounts.get(initial).get(username);
			}
		}
		return null;
	}

	/**
	 * Methods used by ReservationServer to make a unique reservation are: doReservation, doLock, doRelease
	 *	//set to book.setIsReserved( true )
		//account.getReserved().put( book )
	 */
	public Book doReservation(Book book, Account account){
		Entry<String, Book> bookEntry = findBookEntry(book.getTitle(), book.getAuthor(), true);
		if( bookEntry != null ){ 
			bookEntry.getValue().setReserved(true);
			return bookEntry.getValue();		
		}
		//returns an empty book instance 
		return new Book();
	}

	//makes the book available in current library 
	//set to book.setIsReserved( false );
	public void doRelease(Book book, Account account){
		Entry<String, Book> bookEntry = findBookEntry(book, false);
		if( bookEntry != null ) 
			bookEntry.getValue().setReserved(false);
	}
	
	//makes the book locked to current reservation --- not needed anyways 
	//book.setIsReserved( true );
	public void doLock(Book book, Account account){
		Entry<String, Book> bookEntry = findBookEntry(book, false);
		if( bookEntry != null ) 
			bookEntry.getValue().setReserved(true);
	}
	
	
	/**
	 * @uses reserveInterLibrary
	 * @throws RemoteException 
	 */
	public void reserveBook(String username, String password, String title, String author)  throws RemoteException {
		Account account = findAccount(username, password);
		if( account == null || account.getUsername().length() <= 0 ){
			String message = "Username and password does not match any account.";
			throw new RemoteException( message );
		}
		
		Entry<String, Book> bookEntry = findBookEntry(title, author, true);
			if( bookEntry != null && bookEntry.getKey() != null ){
				account.getReserved().put(bookEntry.getKey(), bookEntry.getValue());
				Calendar calendar = new GregorianCalendar();
				calendar.add(Calendar.DATE, 14);
				Date now = new Date();
				Reservation reservation = new Reservation(bookEntry.getValue(), account, now, calendar.getTime(), null);
				reservations.put(bookEntry.getKey(), reservation);
				bookEntry.getValue().setReserved(true);
				//send acknowledgment for the reservation 
				acknowledgmentTask.getNetworkMessage().setPayload(StringTransformer.getString(reservation));
				acknowledgmentTask.execute();
				//making the acknowledgment 
				new AccountActivity(account).logActivity("Reserved a Book: " + bookEntry.getValue().toString() + " From "+ account.getInstitution() );
		}else{
			//Try other libraries if book not found at this library.
			reserveInterLibrary(username, password, title, author);
		}
	}
	
	
	/**
	 * @use ReservationClient to for booking 
	 * @todo implement me 
	 */
	public void reserveInterLibrary(String username, String password, String title, String author)  throws RemoteException {
		ExecutorService threadPool = Executors.newFixedThreadPool( nodes.length - 1 );
		CompletionService<Book> pool = new ExecutorCompletionService<Book>(threadPool);		
		boolean reservationDone = false; 
		Account account = findAccount(username, password);
		if( account == null || account.getUsername().length() <= 0 ){
			String message =  "The book " + title + " By " + author + " not found.";
			throw new RemoteException( message );
		}
		ReservationBookShelf shelf = new ReservationBookShelf(account, this);//observable
		new RemoteLibraryNotifier( shelf , this);//observer
			try {
				for( String node: nodes){
					if( !node.equals( this.getInstitution()) ){
						pool.submit(new ReservationClient(this, node , new Book( title, author), account));
					}
				}
				for( String node: nodes){
					if( !node.equals( this.getInstitution()) ){
						Book book = pool.take().get();//@todo put these books into an ObservableMap, finalize reservation, and notify remote servers to release their copies
						if(book instanceof Book && book.getCode() != null ) { 
							shelf.add( book );
							/**
							 * @todo re-test this section to make sure this task is in sync with ReservationShelf 	
							 */
							if( reservationDone == false && acknowledgmentTask != null){
								Reservation reservation = new Reservation(book, account, new Date(), null, null);
								acknowledgmentTask.getNetworkMessage().setPayload(StringTransformer.getString(reservation));
								acknowledgmentTask.execute();
								reservationDone = true;
							}
						}
						log( String.format(" %s reserveInterLibrary :: getting a book  ---- %s", this.getInstitution(), book != null ? book.toString() : " NOT FOUND " ) );
					}
				}
			} catch (Exception e) {
				throw new RemoteException( e.getMessage() );
			}
		//terminating all tasks at the end
		threadPool.shutdown();
	}

	/**
	 * @todo this approach is not optimal O(n) Utility function to find
	 * @param title
	 * @param author
	 * @return
	 */
	private Entry<String, Book> findBookEntry(String title, String author, boolean checkAvailability) {
		for (Entry<String, Book> entry : books.entrySet()) {
			Book book = entry.getValue();
			if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
				if( (checkAvailability && !book.isReserved()) || !checkAvailability ){ 
					return entry;
				}
			}
		}
		return null;
	}

	
	private Entry<String, Book> findBookEntry(Book book, boolean checkAvailability) {
		for (Entry<String, Book> entry : books.entrySet()) {
			Book ebook = entry.getValue();
			if (ebook.getCode().equals(book.getCode())){
				if( (checkAvailability && !ebook.isReserved()) || !checkAvailability ){ 
					return entry;
				}
			}
		}
		return null;
	}



	
	/**
	 * This method is added for testing purposes only. 
	 * To use it run LibraryServerTest::testCanGetNonReturners
	 */
	public void setDuration(String username, String title, int days)  throws RemoteException {
		Account account = findAccountByUsername(username);
		if( account == null || !(account instanceof Account) ){
			return;
		}
		for (Entry<String, Reservation> reservation : reservations.entrySet()) {
			if( reservation.getValue().getAccount().getUsername().equals(username) && reservation.getValue().getBook().getTitle().equals(title) ){
				long dueTime = new Date().getTime() - days*86400000L;
				long startTime = dueTime - (14 * 86400000L);
				reservation.getValue().setDueDate( new Date(dueTime) );
				reservation.getValue().setStarting(new Date(startTime));
			}
		}
		
	}
	

	/**
	 * runs the reservation server process
	 * This process, get reservation requests, processes them and return results to clients  
	 * Clients are servers that wishes to make book reservation on this server.
	 * @param reservationSocket
	 */
	public void startReservationServer(LibraryServerImpl libraryServer, DatagramSocket reservationSocket) {
		try {
			new Thread(new ReservationServer(libraryServer, reservationSocket)).start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.logger.info("startReservationServer for " + institution );
	}
	
	
	/**
	 * runs reporting server process
	 * This process will wait for reporting requests, processes them and return results to clients.
	 * Clients are server other servers that wishes to gather non-returners data
	 * @param reportingSocket
	 */
	public void startReportingServer(LibraryServerImpl libraryServer, DatagramSocket reportingSocket) {
		try {
			new Thread(new ReportServer(libraryServer, reportingSocket)).start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.logger.info("startReportingServer for " + institution );
	}

	
	/**
	 * This function collects reservation from servers and return to the caller
	 * @param username 
	 * @param password 
	 * @param institutions 
	 * @param days 
	 */
	public ca.concordia.drms.orb.Reservation[] getNonReturners(String username, String password, String institution, int days)  throws RemoteException {
		ExecutorService threadPool = Executors.newFixedThreadPool( nodes.length - 1 );
		CompletionService<Map<String, Reservation>> pool = new ExecutorCompletionService<Map<String, Reservation>>(threadPool);		
		Map<String, Reservation> _reservations = new HashMap<String, Reservation>();
		try {
			for( String node: nodes){
				if( !node.equals( this.getInstitution()) ){
					pool.submit( new ReportClient( this, node ) );
				}
			}
			//retrieve and merge results 
			for( String node: nodes){
				if( !node.equals( this.getInstitution()) ){
					_reservations.putAll( pool.take().get() );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for( Entry<String, Reservation> entry : reservations.entrySet()) {
			long elapsed = (new Date().getTime() - entry.getValue().getDueDate().getTime()) / 86400000L;
			if ( (elapsed >= (long)days && entry.getValue().getReturnDate() == null) || (long)days <= 0 ){
				entry.getValue().setFees((long) Math.round(elapsed));
				_reservations.put(entry.getKey(), entry.getValue());
			}
		}
		//close the threadpool at the end 
		if( acknowledgmentTask != null ){ 
			acknowledgmentTask.getNetworkMessage().setPayload(StringTransformer.getString(_reservations));
			acknowledgmentTask.execute();
		}
		threadPool.shutdown();
		return ReservationTransformer.transform( _reservations );
	}
	
	/**
	 * Function that sends notification of the outcome on a request outcome.
	 * @param acknowledgmentTask
	 */
	public void setAcknowledgmentTask(AcknowledgmentTask acknowledgmentTask) {
		this.acknowledgmentTask = acknowledgmentTask;
	}
	
	

}