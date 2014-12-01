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
		book.setCode(json.getAsJsonPrimitive("code").getAsString());
		book.setLibrary(json.getAsJsonPrimitive("library").getAsString());
		book.setReserved(json.getAsJsonPrimitive("reserved").getAsBoolean());
		book.setTitle(json.getAsJsonPrimitive("title").getAsString());
		return book;
	}
	
	/**
	 * @param json
	 * @return
	 */
	private static Account parseAccountFromJson(JsonObject json){
		Account account = new Account();
		account.setFirst(json.getAsJsonPrimitive("first").getAsString());
		account.setLast(json.getAsJsonPrimitive("last").getAsString());
		account.setEmail(json.getAsJsonPrimitive("email").getAsString());
		account.setPassword(json.getAsJsonPrimitive("password").getAsString());
		account.setTelephone(json.getAsJsonPrimitive("telephone").getAsString());
		account.setUsername(json.getAsJsonPrimitive("username").getAsString());
		account.setAdmin(json.getAsJsonPrimitive("admin").getAsBoolean());
		//account.setInstitution(institution);
		//account.setReserved(reserved);
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
}