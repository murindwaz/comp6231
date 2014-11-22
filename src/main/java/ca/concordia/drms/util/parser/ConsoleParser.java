package ca.concordia.drms.util.parser;

import java.util.Arrays;
import ca.concordia.drms.model.*;


/** 
 * This class receives string from CLI and returns an object or rejects an exception
 * The parser may validate the obejct as well 
 * @author murindwaz
 */
public class ConsoleParser {
	
	private static int ACCOUNT = 1; 
	private static int BOOK = 2; 
	private static int LIBRARY = 3; 
	
	/**
	 * help.put("account", "**to register, type \"account -f [first name] -l [last name] -u username -p password -e [email] -t [telephone] \"**%s");
	 * @param String arg
	 * @return Account account
	 */
	public static Account parseAccount(String arg){
		String [] parsed = arg.split("(\\-[a-zA-Z])");
		Account account = new Account();
		account.setAdmin(false);
		account.setFirst( parsed[1].trim());
		account.setLast(parsed[2].trim());
		account.setUsername(parsed[3].trim());
		account.setPassword(parsed[4].trim());
		account.setEmail(parsed[5].trim());
		account.setTelephone(parsed[6].trim());
		return account;
	}

	
	
	
	/**
	 * Parses the string of type 
	 * reservation -u jane.dane -p enadenaj -b The Tragedy of Hamlet, Prince of Denmark -a William Shakespeare
	 * interlib -u jane.dane -p enadenaj -b The Tragedy of Hamlet, Prince of Denmark -a William Shakespeare
	 * and returns the object with Reservation.
	 * @param arg
	 * @return
	 */
	public static Reservation parseReservation(String arg){
		String[] parsed = arg.split("(\\-[a-zA-Z])");
   		Reservation reservation = new Reservation();
   		Account account = new Account(); 
   		account.setUsername(parsed[1].trim());
   		account.setPassword(parsed[2].trim());
   		Book book = new Book(); 
   		book.setTitle(parsed[3].trim());
   		book.setAuthor(parsed[4].trim());
   		reservation.setBook(book);
   		reservation.setAccount(account);
   		return reservation;
	}

	
	/**
	 * Takes cli input of type bonjour -i institution -r role ( student or admin )
	 * @param String arg
	 * @return String[]
	 */
	public static String [] parseBonjour( String arg){
		String[] parsed = arg.split("(\\-[a-zA-Z])");
		return new String[]{ parsed[1].trim(), parsed[2].trim() };
	}

	/**
	 * Will parse the command of type : overdue -u admin -p admin -d 10
	 * @param string
	 * @return
	 */
	public static Reservation parseOverdue(String arg) {
		String[] parsed = arg.split("(\\-[a-zA-Z])");
   		Reservation reservation = new Reservation();
   		Account account = new Account(); 
   		account.setUsername(parsed[1].trim());
   		account.setPassword(parsed[2].trim());
   		account.setAdmin(true);
   		reservation.setAccount(account);
   		reservation.setDays( Integer.decode( parsed[3].trim()) );
   		return reservation;
	}

	/**
	 * Gets the very first argument 
	 * @param arg
	 * @return
	 */
	public static String parseCommand(String arg) {
		String command = arg.split("(\\-[a-zA-Z])")[0].trim();
		if( Arrays.asList(new String[]{ "bonjour","overdue","account","reservation","interlib","exit"}).contains(command) ){
			return command;
		}
		return  null;
	}
	

}