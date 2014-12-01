package ca.concordia.drms.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.*;
import java.util.Map.Entry;

import ca.concordia.drms.model.*;
import ca.concordia.drms.util.*;

/**
 * Report processor will : - wait for incoming report request from another
 * server - visit current borrowers list - stream response back. - to mark the
 * end of session, send done message. Message = destination :: days :: requestor
 * 
 * @author murindwaz
 */
public class ReportServer implements Runnable {

	private DatagramSocket socket;
	private LibraryServerImpl libraryServer;
	private Map<String, Reservation> reservations;

	public ReportServer(LibraryServerImpl libraryServerImpl, DatagramSocket datagramSocket) throws SocketException {
		socket = datagramSocket;
		libraryServer = libraryServerImpl;
<<<<<<< HEAD
		libraryServerImpl.log(String.format("ReportServer init :: listening on port " + socket.getPort()));
=======
		libraryServerImpl.getLogger().info(String.format("ReportServer init :: listening on port " + socket.getPort()));
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
	}

	//
	public void run() {
		byte[] buffer = new byte[Configuration.BUFFER_SIZE];
		DatagramPacket response;
		DatagramPacket request;
		byte[] done = new String("done").getBytes();
		while (true) {
			try {
				request = new DatagramPacket(buffer, buffer.length);
				socket.receive(request);
				String message = new String(request.getData());
				// prevent current instance from auto-pinging itself
				if (message.indexOf(libraryServer.getInstitution()) > -1) {
					long days = -1L;
					reservations = libraryServer.getReservations();
<<<<<<< HEAD
					libraryServer.log(String.format("ReportServer processing request --- [" + message + "] on ---"+ libraryServer.getInstitution()));
=======
					libraryServer.getLogger().info(String.format("ReportServer processing request --- [" + message + "] on ---"+ libraryServer.getInstitution()));
>>>>>>> d1de818cb5f8fbab973e55070f28cddda419eb05
					for (Entry<String, Reservation> entry : reservations.entrySet()) {
						long elapsed = (new Date().getTime() - entry.getValue().getDueDate().getTime()) / 86400000L;
						if (elapsed >= days && entry.getValue().getReturnDate() == null) {
							entry.getValue().setFees((long) Math.round(elapsed));
							byte[] _msg = entry.getValue().toString().getBytes();
							response = new DatagramPacket(_msg, _msg.length, request.getAddress(), request.getPort());
							socket.send(response);
						}
					}
				}
				response = new DatagramPacket(done, done.length, request.getAddress(),request.getPort());
				socket.send(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}