package ca.concordia.drms.server;

import java.util.*;

import ca.concordia.drms.model.*;
import ca.concordia.drms.util.task.AccountActivity;



public class ReservationBookShelf extends Observable {

	
	private boolean done;
	private Account account;
	private Map<String,Book> books;
	private LibraryServerImpl libraryServer;
	public ReservationBookShelf(Account account, LibraryServerImpl libraryServerImpl) {
		this.account = account;
		done = false;
		books = new HashMap<String, Book>();
		libraryServer = libraryServerImpl;
	}

	public void add(Book book) {
		synchronized (this) {
			if( book instanceof Book && !books.containsKey(book.getCode()) ){
				//rejected books will have this flag set to false
				book.setReserved(false);
				if( done == false ){
					Date now = new Date();
					book.setReserved(true);
					books.put(book.getCode(), book);
					account.getReserved().put(book.getCode(), book);
					Calendar calendar = new GregorianCalendar();
					calendar.add(Calendar.DATE, 14);
					libraryServer.getReservations().put(book.getCode(), new Reservation(book, account, now, calendar.getTime(), null));
					new AccountActivity(account).logActivity("Reserved a Book: " + book.toString() + " From "+ book.getLibrary() );
					done = true;
				}
				setChanged();
				notifyObservers(book);
			}
		}
	}

}