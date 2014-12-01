package ca.concordia.drms.util.task;

import com.google.gson.Gson;

import ca.concordia.drms.model.*;




/**
 * This utility will take an object and transform it into a valid JSON String 
 * @author murindwaz
 */
public class StringTransformer {
	
	
	/**
	 * Helper to transform an object into a JSON string
	 * @param Book book
	 * @return String 
	 */
	public static String getString(Book book){
		return (new Gson()).toJson(book);
	}
	
	/**
	 * Helper that transforms an Account into a JSON string
	 * @param Account account
	 * @return String 
	 */
	public static String getString(Account account ){
		return (new Gson()).toJson(account);
	}
	
	/**
	 * Helper that transforms a Reservation Object into a JSON String
	 * @param reservation
	 * @return
	 */
	public static String getString(Reservation reservation){
		return (new Gson()).toJson(reservation);
	}
	
	/**
	 * Helper that transforms a NetworkMessage into a JSON String
	 * @param networkMessage
	 * @return
	 */
	public static String getString(NetworkMessage networkMessage){
		return (new Gson()).toJson(networkMessage);
	}
	
}