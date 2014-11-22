package ca.concordia.drms.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import ca.concordia.drms.model.Reservation;
import ca.concordia.drms.util.*;


/**
 * Listens on the same socket as ReportRequestor uses.
 * It will keep listening till it receives the last "done" 
 * @author murindwaz
 */
public class ReportClient implements Callable<Map<String, Reservation>> {

	private LibraryServerImpl libraryServer;
	private String destination = null;
	public ReportClient(LibraryServerImpl libraryServerImpl, String node){
		destination = node;
		libraryServer = libraryServerImpl;
	}

	public Map<String, Reservation> call() throws Exception {
		Map<String, Reservation> _reservations = new HashMap<String, Reservation>();
		DatagramSocket socket = new DatagramSocket();
		InetAddress server = InetAddress.getByName("localhost");
		try {
			boolean finished = false;
			String message = destination; 
			DatagramPacket request = new DatagramPacket(message.getBytes(), message.getBytes().length, server, Configuration.REPORT_PROCESSING_PORT);
			socket.send(request);
			libraryServer.getLogger().info( destination + " -- ReportClient called --- " );
			while (!finished) {
				byte buffer[] = new byte[Configuration.BUFFER_SIZE];
				DatagramPacket response = new DatagramPacket(buffer, buffer.length);
				socket.receive(response);
				libraryServer.getLogger().info( destination + " -- ReportClient::call -- received -- " + new String(response.getData()));
				finished = new String(response.getData()).indexOf("done") > -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			socket.close();
			socket.disconnect();
		}
		return _reservations;
	}

}