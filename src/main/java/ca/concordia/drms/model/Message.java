package ca.concordia.drms.model;



/**
 * This model reflects message passed between client and server 
 * @author murindwaz
 */
public class Message {
	
	
	
	private Book book;
	private Account account;
	private String operation;
	
	
	
	public Message(){
		this.book = new Book(); 
		this.account = new Account(); 
		this.operation = "";
	}



	public Book getBook() {
		return book;
	}



	public void setBook(Book book) {
		this.book = book;
	}



	public Account getAccount() {
		return account;
	}



	public void setAccount(Account account) {
		this.account = account;
	}



	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
	
	
	
	

}
