package ca.concordia.drms.model;

import java.io.Serializable;
import java.util.*;//for Date, List, etc 

/**
 * Model representation of Account, will be used mainly for students who want to
 * borrow books, But covers also, other non-specific use cases
 * 
 * @author murindwaz
 */
public class Account  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5622422308569666504L;
	private String first;
	private String last;
	private String email;
	private String telephone;
	private String username;
	private String password;

	private boolean admin;

	// the only thing to be unique in this Hash is the date ...
	// @todo try to find a workaround of
	// this approach
	
	
	private HashMap<String, Book> reserved;
	private String institution;
	
	
	
	
	public Account(){
		this.admin = false;
		this.reserved = new HashMap<String, Book>();
	}
	
	
	
	
	/**
	 * 
	 * @param first
	 * @param last
	 * @param email
	 * @param telephone
	 * @param username
	 * @param password
	 * @param institution
	 */
	public Account(String first, String last, String email, String telephone, String username, String password,String institution) {
		this.admin = false;
		this.first = first;
		this.last = last;
		this.email = email;
		this.telephone = telephone;
		this.username = username;
		this.password = password;
		this.reserved = new HashMap<String, Book>();
	}





	/**
	 * Generated getters/setters
	 * 
	 * @return
	 */
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public  String getInstitution() {
		return institution;
	}

	public  void setInstitution(String institution) {
		this.institution = institution;
	}

	public HashMap<String, Book> getReserved() {
		return reserved;
	}

	public void setReserved(HashMap<String, Book> reserved) {
		this.reserved = reserved;
	}
	
	
	@Override
	public String toString(){
		return String.format("Full name : %s %s,  Username - Password : %s -  %s, E-mail %s, Tel: %s, School: %s", this.getFirst(), this.getLast(), this.getUsername(), this.getPassword(), this.getEmail(), this.getTelephone(), this.getInstitution() );
	}

}