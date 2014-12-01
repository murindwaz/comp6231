package ca.concordia.drms.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import ca.concordia.drms.model.Book;
import ca.concordia.drms.util.Configuration;

public class RemoteLibraryNotifier implements Observer {

	private LibraryServerImpl libraryServer;

	public RemoteLibraryNotifier(ReservationBookShelf shelf, LibraryServerImpl libraryServerImpl) {
		libraryServer = libraryServerImpl;
		shelf.addObserver(this);
	}
	public void update(Observable o, Object arg) {
		try {
			DatagramSocket socket = new DatagramSocket();
			try {
				Book book = ((Book) arg);
				String destination = book.getLibrary();
				InetAddress server = InetAddress.getByName("localhost");
				String 	raw = "{\"d\":\""+destination+"\",\"o\":\""+Configuration.DO_RELEASE+"\",\"title\":\""+book.getTitle()+"\",\"author\":\""+book.getAuthor()+"\",\"library\":\""+book.getLibrary()+"\",\"code\":\""+book.getCode()+"\" ,\"reserved\":\""+book.isReserved()+"\" }";
				if( book.isReserved() ){
					raw = "{\"d\":\""+destination+"\", \"o\":\""+Configuration.DO_LOCK+"\",\"title\":\""+book.getTitle()+"\",\"author\":\""+book.getAuthor()+"\",\"library\":\""+book.getLibrary()+"\",\"code\":\""+book.getCode()+"\" ,\"reserved\":\""+book.isReserved()+"\" }";
				}
				byte[] message = raw.getBytes("UTF-8");
				DatagramPacket request = new DatagramPacket(message, message.length, server,Configuration.RESERVATION_PROCESSING_PORT);
				socket.send(request);
<<<<<<< HEAD
				libraryServer.log(destination + " -- RemoteLibraryNotifier::call -- sent notification -- ");
=======
				libraryServer.getLogger().info(destination + " -- RemoteLibraryNotifier::call -- sent notification -- ");
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
				socket.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("RemoteLibraryNotifier::updated ");
	}
}