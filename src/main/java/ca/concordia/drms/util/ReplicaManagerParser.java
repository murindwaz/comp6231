package ca.concordia.drms.util;

import java.io.StringReader;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import ca.concordia.drms.model.*;




public class ReplicaManagerParser {
	
	
	public static NetworkMessage parseNetworkMessage(String argument) throws Exception{
		Gson gson = new Gson();
		NetworkMessage networkMessage = new NetworkMessage();
		JsonReader reader = new JsonReader(new StringReader(argument.trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		reader.setLenient(true);
			networkMessage.setDestination(json.getAsJsonPrimitive("destination").getAsString() );
			networkMessage.setOperation(json.getAsJsonPrimitive("operation").getAsString() );
			networkMessage.setPayload(json.getAsJsonPrimitive("payload").getAsString() );
		return networkMessage;
	}
	
	public static Account parseAccount(String argument) throws Exception{
		NetworkMessage networkMessage = parseNetworkMessage(argument);
		JsonReader reader = new JsonReader(new StringReader(networkMessage.getPayload().trim()));
		Gson gson = new Gson();
		reader.setLenient(true);
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		Account account = parseAccountFromJson(json);
		return account;
	}
	/**
	 * This is a repetition but we dont have alternative for the moment
	 * @param argument
	 * @return
	 * @throws Exception
	 */
	public static Account parseAccountFromPayload(String argument) throws Exception{
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(argument.trim()));
		reader.setLenient(true);
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		Account account = parseAccountFromJson(json);
		return account;
	}
	
	
	/**
	 * @param String argument
	 * @throws Exception
	 * @return Book 
	 */
	public static Book parseBook(String argument) throws Exception{
		Gson gson = new Gson();
		NetworkMessage networkMessage = parseNetworkMessage(argument);
		JsonReader reader = new JsonReader(new StringReader(networkMessage.getPayload().trim()));
		reader.setLenient(true);
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		Book book = parseBookFromJson(json);
		return book;
	}
	/**
	 * Specialization to parseBook added to handle both cases:
	 * 	- NetworkMessage payload containing Book and Account
	 *  - NetworkMessage payload containing only Book
	 * @param json
	 * @return
	 */
	private static Book parseBookFromJson(JsonObject json){
		Book book = new Book();
		book.setAuthor(json.getAsJsonPrimitive("author").getAsString());
		book.setTitle(json.getAsJsonPrimitive("title").getAsString());
		//the minimum book configuration is an author and a title
		try{
			book.setCode(json.getAsJsonPrimitive("code").getAsString());
			book.setLibrary(json.getAsJsonPrimitive("library").getAsString());
			book.setReserved(json.getAsJsonPrimitive("reserved").getAsBoolean());
		}catch(Exception e){
			System.out.println( String.format("An error occurred :: ", e.getMessage()));
		}
		return book;
	}
	
	/**
	 * @param json
	 * @return
	 */
	private static Account parseAccountFromJson(JsonObject json){
		Account account = new Account();
		account.setPassword(json.getAsJsonPrimitive("password").getAsString());
		account.setUsername(json.getAsJsonPrimitive("username").getAsString());
		account.setAdmin(json.getAsJsonPrimitive("admin").getAsBoolean());
		try{
			account.setFirst(json.getAsJsonPrimitive("first").getAsString());
			account.setLast(json.getAsJsonPrimitive("last").getAsString());
			account.setEmail(json.getAsJsonPrimitive("email").getAsString());
			account.setTelephone(json.getAsJsonPrimitive("telephone").getAsString());
			//account.setInstitution(institution);
			//account.setReserved(reserved);
		}catch( Exception e ){
			System.out.println( String.format("An error occurred :: ", e.getMessage()));

		}
		return account;
	}
	
	
	/**
	 * @param argument
	 * @return
	 * @throws Exception
	 */
	public static Reservation parseReservation(String argument) throws Exception{
		NetworkMessage networkMessage = parseNetworkMessage(argument);
		Gson gson = new Gson();
		Reservation reservation = new Reservation();
		JsonReader reader = new JsonReader(new StringReader(networkMessage.getPayload().trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
			reader.setLenient(true);
		JsonObject jbook = json.getAsJsonObject("book");
		JsonObject jaccount = json.getAsJsonObject("account");
		reservation.setBook(parseBookFromJson(jbook));
		reservation.setAccount(parseAccountFromJson(jaccount));
		//@todo before enabling this section, please test re-run ReplicaManagerParserTest to check if all parsing runs smothly 
		//reservation.setStarting(new Date( json.getAsJsonPrimitive("starting").getAsString()));
		//reservation.setDueDate(new Date(json.getAsJsonPrimitive("dueDate").getAsString()));
		return reservation;
	}
	
	/**
	 * @param argument
	 * @return
	 * @throws Exception 
	 */
	public static Reservation parseOverdue(String argument) throws Exception {
		NetworkMessage networkMessage = parseNetworkMessage(argument);
		Reservation reservation = parseReservation(argument);
		return reservation;
	}

	
	/**
	 * For sake of simplicity, but we can do better if we had enough time to refactor this
	 * @param payload
	 * @return
	 */
	public static Reservation parseReservationFromPayload(String payload) {
		Gson gson = new Gson();
		Reservation reservation = new Reservation();
		JsonReader reader = new JsonReader(new StringReader(payload.trim()));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
			reader.setLenient(true);
		JsonObject jbook = json.getAsJsonObject("book");
		JsonObject jaccount = json.getAsJsonObject("account");
		reservation.setBook(parseBookFromJson(jbook));
		reservation.setAccount(parseAccountFromJson(jaccount));
		//@todo before enabling this section, please test re-run ReplicaManagerParserTest to check if all parsing runs smothly 
		//reservation.setStarting(new Date( json.getAsJsonPrimitive("starting").getAsString()));
		//reservation.setDueDate(new Date(json.getAsJsonPrimitive("dueDate").getAsString()));
		return reservation;
	}

	/**
	 * 
	 * @param payload
	 * @return
	 */
	public static Reservation parseOverdueFromPaload(String payload) {
		Reservation reservation = parseReservationFromPayload(payload);
		return reservation;
	}
}