package ca.concordia.drms.model;

import java.io.Serializable;

public class Book  implements Serializable{
	
	
	
	private static final long serialVersionUID = 7270952923454082619L;
	private String title;
	private String author;
	private String code; //will be used to identify unique books in libraries
	//introduces the notion of whose the book belongs to
	private String library; 
	private boolean reserved;
	
	public Book( String title, String author, String code , String library){
		this( title, author, code );
		this.setLibrary(library);
	}
	public Book( String title, String author, String code ){
		this.title  = title;
		this.author = author;
		this.code = code;
		this.reserved = false;
	}
	public Book( String title, String author){
		this(title, author, "");
	}
	
	
	/**
	 * This constructor is used for initialization purposes only 
	 */
	public Book() {
		this( null, null, null );
	}
	public  String getTitle() {
		return title;
	}
	public  void setTitle(String title) {
		this.title = title;
	}
	public  String getAuthor() {
		return author;
	}
	public  void setAuthor(String author) {
		this.author = author;
	}
	public  String getCode() {
		return code;
	}
	public  void setCode(String code) {
		this.code = code;
	}
	public boolean isReserved() {
		return reserved;
	}
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	

	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	
	/**
	 * toString 
	 */
	@Override
	public String toString(){
		if( library != null && library.length() > 0 ){
			return String.format( "%s by %s - %s - library: %s", this.getTitle(), this.getAuthor(), this.getCode(), this.getLibrary() );
		}
		return String.format( "%s by %s - %s", this.getTitle(), this.getAuthor(), this.getCode() );
	}

}