package ca.concordia.drms.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Callable;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.Book;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.MessageParser;



/**
 * The reservation client will be executed to find books, and make reservation on remote ReservationServer.
 * Upon reception of the message, the client executes reservation task.
 * After completing the task, the client notifies remote ReservationServer, to release or lock the book. 
 * 
 * Operations : 
 * 	- reservation : t ( take )  
 *  - release: r ( command sent to release the book after booking failure )
 *  - lock: l ( command sent to confirm book lockup after booking success )
 * @author murindwaz
 */
public class ReservationClient implements Callable<Book>{

	private LibraryServerImpl libraryServer;
	private String destination; 
	private Book book;
	private Account account;
	
	public ReservationClient(LibraryServerImpl libraryServerImpl, String node, Book book, Account account){
		destination = node;
		this.book = book; 
		this.account = account;
		libraryServer = libraryServerImpl;
	}
	
	//encode the book { d: destination, o : t|r|l, title : title, author: author }
	public Book call() throws Exception {
		DatagramSocket socket  = new DatagramSocket();
		InetAddress server = InetAddress.getByName("localhost");
		Book rbook = null;
		try {
			String raw = "{ \"d\": \""+destination+"\", \"o\" : \""+Configuration.DO_RESERVATION+"\", \"title\" : \""+book.getTitle()+"\", \"author\": \""+book.getAuthor()+"\" }";
			byte[] message = raw.getBytes("UTF-8");
			DatagramPacket request = new DatagramPacket(message, message.length, server, Configuration.RESERVATION_PROCESSING_PORT);
			socket.send(request);
			libraryServer.getLogger().info(String.format( "%s --- ReservationClient::call -- sent request -- %s -- %d BYTE-- TO  %s PORT :: %d ", libraryServer.getInstitution(), new String(message), message.length, destination , Configuration.RESERVATION_PROCESSING_PORT));
			byte buffer[] = new byte[Configuration.BUFFER_SIZE];
			DatagramPacket response = new DatagramPacket(buffer, buffer.length);
			socket.receive(response);
			rbook = MessageParser.parseBook( new String(response.getData()) );
			libraryServer.getLogger().info( String.format( " %s --- ReservationClient::call -- received -- %s ", libraryServer.getInstitution(), new String(response.getData())) );
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			socket.close();
			socket.disconnect();
		}
		return rbook;
	}
}