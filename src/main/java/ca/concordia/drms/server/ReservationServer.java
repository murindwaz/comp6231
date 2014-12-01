package ca.concordia.drms.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import ca.concordia.drms.model.Book;
import ca.concordia.drms.model.Message;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.MessageParser;




/**
 * This class receives requests about to reserve a book
 * If the book is there, it gets rocked and sends a request to the client 
 * If the client already reserved that book from another server, it sends a release request
 * If the client reserves the book sent from this server, it sends back a "thanks" message
 * @author murindwaz
 */
public class ReservationServer implements Runnable {

	
	private DatagramSocket socket;
	private LibraryServerImpl libraryServer;
	public ReservationServer(LibraryServerImpl libraryServerImpl, DatagramSocket reservationSocket) throws SocketException{
		socket = reservationSocket;
		libraryServer = libraryServerImpl;
	}

	
	
	/**
	 * 
	 */
	public void run() {
		byte[] buffer = new byte[ Configuration.BUFFER_SIZE];
		byte[] done = new String("done").getBytes();
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		while(true){
			try {
				socket.receive(request);
<<<<<<< HEAD
				libraryServer.log( String.format( " %s -- ReservationServer::run received %s from PORT : %d ", libraryServer.getInstitution(), new String(request.getData()) , request.getPort()) );
=======
				libraryServer.getLogger().info( String.format( " %s -- ReservationServer::run received %s from PORT : %d ", libraryServer.getInstitution(), new String(request.getData()) , request.getPort()) );
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
				Message message = MessageParser.parse(new String(request.getData()));
				if( message.getOperation().equals(Configuration.DO_RESERVATION)){
					Book book = libraryServer.doReservation( message.getBook(), message.getAccount());
					buffer = MessageParser.encode(book).getBytes();
					socket.send( new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort()) );
				}else if( message.getOperation().equals(Configuration.DO_LOCK) ){
					libraryServer.doLock(message.getBook(), message.getAccount());
					socket.send( new DatagramPacket(done, done.length, request.getAddress(), request.getPort()) );
				}else if(message.getOperation().equals(Configuration.DO_RELEASE)){
					libraryServer.doRelease(message.getBook(), message.getAccount());
					socket.send( new DatagramPacket(done, done.length, request.getAddress(), request.getPort()) );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

}