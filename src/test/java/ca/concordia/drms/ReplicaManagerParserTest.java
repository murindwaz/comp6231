package ca.concordia.drms;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.Book;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.model.Reservation;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.ReplicaManagerParser;
import ca.concordia.drms.util.task.StringTransformer;




public class ReplicaManagerParserTest {

	
	@Test 
	public void  testCanParseAccount() throws Exception{
		Account account = new Account( "Pascal", "Maniraho", "pmnr@email", "514-571","pmnr", "pmnr",  Configuration.INSTITUTION_CONCORDIA);
		String accountstr = StringTransformer.getString( account );
		NetworkMessage networkMessage = new NetworkMessage();
			networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
			networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT);
			networkMessage.setPayload(accountstr);
		String argument = StringTransformer.getString(networkMessage);
		Account paccount = ReplicaManagerParser.parseAccount(argument);
		assertTrue( paccount.getFirst().equals(account.getFirst()) );
		assertTrue( new ReplicaManagerParser() != null );//checking if exists
	}
	
	
	@Test 
	public void  testCanParseBook() throws Exception{
		Book book = new Book("The Book of Negroes", "Lawrence Hill", "co-tbon-lh-2007-1", Configuration.INSTITUTION_CONCORDIA);
		String booktstr = StringTransformer.getString( book );
		NetworkMessage networkMessage = new NetworkMessage();
			networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
			networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_RESERVATION);
			networkMessage.setPayload(booktstr);
		String argument = StringTransformer.getString(networkMessage);
		Book pbook = ReplicaManagerParser.parseBook(argument);
		assertTrue( pbook.getTitle().equals(book.getTitle()) );
	}
	

	
	@Test
	public void testCanParseReservation() throws Exception{
		Date now = new Date();
		Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DATE, 14);
		Account account = new Account( "Pascal", "Maniraho", "pmnr@email", "514-571","pmnr", "pmnr",  Configuration.INSTITUTION_CONCORDIA);
		Book book = new Book("The Book of Negroes", "Lawrence Hill", "co-tbon-lh-2007-1", Configuration.INSTITUTION_CONCORDIA);
		Reservation reservation = new Reservation(); 
			reservation.setAccount(account);
			reservation.setBook(book);
			reservation.setStarting(now);
			reservation.setReturnDate( calendar.getTime() );
		NetworkMessage networkMessage = new NetworkMessage();
		networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_RESERVATION);
		String payload = StringTransformer.getString(reservation);
		networkMessage.setPayload( payload );
		String argument = StringTransformer.getString(networkMessage);
		//testing returned data back-and-forth
		Reservation preservation = ReplicaManagerParser.parseReservation(argument);
		assertTrue( preservation.getAccount().getFirst().equals(reservation.getAccount().getFirst()) );
		assertTrue( preservation.getBook().getAuthor().equals( reservation.getBook().getAuthor()) );
		//Parsing Overdue message
		Reservation overdue = ReplicaManagerParser.parseOverdue(argument);
		assertTrue( overdue.getAccount().getFirst().equals(reservation.getAccount().getFirst()) );
		assertTrue( overdue.getBook().getAuthor().equals( reservation.getBook().getAuthor()) );
		
	}
	
	
}