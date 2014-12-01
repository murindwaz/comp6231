package ca.concordia.drms.model;

<<<<<<< HEAD
/**
 * This model reflects message passed between client and server
 * This model is used specifically between Resevation/ReportClient and
 * Reservation/ReportServer 
 * @author murindwaz
 */
public class Message {

	private Book book;
	private Account account;
	private String operation;

	public Message() {
		this.book = new Book();
		this.account = new Account();
		this.operation = "";
	}

=======


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



>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	public Book getBook() {
		return book;
	}

<<<<<<< HEAD
=======


>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	public void setBook(Book book) {
		this.book = book;
	}

<<<<<<< HEAD
=======


>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	public Account getAccount() {
		return account;
	}

<<<<<<< HEAD
=======


>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	public void setAccount(Account account) {
		this.account = account;
	}

<<<<<<< HEAD
=======


>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	public String getOperation() {
		return operation;
	}

<<<<<<< HEAD
	public void setOperation(String operation) {
		this.operation = operation;
	}

}
=======


	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
	
	
	
	

}
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
