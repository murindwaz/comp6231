package ca.concordia.drms.util;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import ca.concordia.drms.model.Book;
import ca.concordia.drms.model.Message;

/**
 * This message parses instructions coming from Client, and returns a full
 * featured Message Object
 * 
 * @author murindwaz
 */
public class MessageParser {

	public static Message parse(String argument) {
		Message message = new Message();
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(argument));
		JsonObject json = gson.fromJson(reader, JsonObject.class);
		if (json.getAsJsonPrimitive("o").isString()) {
			message.setOperation(json.getAsJsonPrimitive("o").getAsString());
		}
		message.setBook(parseBook(argument));
		return message;
	}

	public static Book parseBook(String argument) {
		
		if( argument == null || argument.equals(null) || argument.equals("null")){
			return new Book();
		}
		
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(argument.trim()));
		reader.setLenient(true);//in case string is not well formatted as a
		Book book = new Book();
		try {
			JsonObject json = gson.fromJson(reader, JsonObject.class);
			if (json.getAsJsonPrimitive("title") != null && json.getAsJsonPrimitive("title").isString())
				book.setTitle(json.getAsJsonPrimitive("title").getAsString());
			if (json.getAsJsonPrimitive("author") != null && json.getAsJsonPrimitive("author").isString())
				book.setAuthor(json.getAsJsonPrimitive("author").getAsString());
			if (json.getAsJsonPrimitive("code") != null && json.getAsJsonPrimitive("code").isString())
				book.setCode(json.getAsJsonPrimitive("code").getAsString());
			if (json.getAsJsonPrimitive("d") != null && json.getAsJsonPrimitive("d").isString())
				book.setLibrary(json.getAsJsonPrimitive("d").getAsString());
			if (json.getAsJsonPrimitive("library") != null && json.getAsJsonPrimitive("library").isString())
				book.setLibrary(json.getAsJsonPrimitive("library").getAsString());
			if (json.getAsJsonPrimitive("reserved") != null && json.getAsJsonPrimitive("reserved").isBoolean())
				book.setReserved(json.getAsJsonPrimitive("reserved").getAsBoolean());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return book;
	}

	// encodes Book to JSON string
	public static String encode(Book book) {
		return (new Gson()).toJson(book);
	}

}