package ca.concordia.drms.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ca.concordia.drms.model.Reservation;



/**
 * @author murindwaz
 */
public class ReservationTransformer {
	
	
	
	public static ca.concordia.drms.orb.Reservation[] transform(Map<String, Reservation> _reservations){
		int counter = 0;	
		ca.concordia.drms.orb.Reservation[] reservations = new ca.concordia.drms.orb.Reservation[_reservations.size()]; 
		for (Entry<String, Reservation> entry : _reservations.entrySet()) {
			long elapsed = (new Date().getTime() - entry.getValue().getDueDate().getTime()) / 86400000L;
			ca.concordia.drms.orb.Account account = transformAccount(entry.getValue().getAccount());
			ca.concordia.drms.orb.Book book = transformBook(entry.getValue().getBook());
			int fees = Integer.parseInt( Long.toString(elapsed));
			reservations[counter++] = new ca.concordia.drms.orb.Reservation( account, book, fees);
		}
		return reservations;
	}
	
	/**
	 * @param reservations
	 * @return Map<String, Reservation>
	 */
	public static Map<String, Reservation> transform(ca.concordia.drms.orb.Reservation[]  reservations){
		Map<String, Reservation> _reservations = new HashMap<String, Reservation>();
			for( ca.concordia.drms.orb.Reservation reservation : reservations ){
				_reservations.put(reservation.book.code, transformReservation(reservation));
			}
		return _reservations;
	}
	
	
	
	

	private static ca.concordia.drms.orb.Book transformBook( ca.concordia.drms.model.Book book){
		return new ca.concordia.drms.orb.Book( book.getTitle(), book.getAuthor(), book.getCode() );
	}
	
	
	private static ca.concordia.drms.orb.Account transformAccount( ca.concordia.drms.model.Account account ){
		return new ca.concordia.drms.orb.Account( account.getFirst(), account.getLast(), account.getEmail(), account.getInstitution(), account.getTelephone());
	}
	
	
	private static ca.concordia.drms.model.Reservation transformReservation(ca.concordia.drms.orb.Reservation reservation ){
		ca.concordia.drms.model.Reservation _reservation = new ca.concordia.drms.model.Reservation();
		ca.concordia.drms.model.Account account = new ca.concordia.drms.model.Account();
			account.setAdmin(false);
			account.setEmail(reservation.account.email);
			account.setFirst(reservation.account.first);
			account.setLast(reservation.account.last);
			account.setInstitution(reservation.account.institution);
			account.setTelephone(reservation.account.telephone);
			_reservation.setAccount(account);
		ca.concordia.drms.model.Book book = new ca.concordia.drms.model.Book();
			book.setAuthor(reservation.book.author);
			book.setTitle(reservation.book.title);
			book.setCode(reservation.book.code);
			_reservation.setBook(book);
			_reservation.setFees(Long.decode( String.valueOf(reservation.fees)) );
			_reservation.setDays(reservation.fees);
		return _reservation;
	}
	
	
	
	
}