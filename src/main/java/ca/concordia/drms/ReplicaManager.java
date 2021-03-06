package ca.concordia.drms;

import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ca.concordia.drms.server.CommandProcessor;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;

/**
 * Hello world!
 *--- wait for messages 
 *---- on message arrival, identify which process to execture and which process to let go 
 *--- possible message operations 
 *---- 1. create account operation is create 
 *---- 2. borrow book
 *---- 3. 
 */
public class ReplicaManager implements Replica{
	

	
	
	//to be used with main function
	private static ReplicaManager rm ;
	
	
	
	//Sockets to be used with listeners
	private DatagramSocket commandSocket = null;
	private DatagramSocket reportingSocket = null;
	private DatagramSocket reservationSocket = null;
	private Map<String, LibraryServerImpl> libraries = new HashMap<String, LibraryServerImpl>();
	/**
	 * Create server settings 	
	 * 	- setting up servants running on this replica.
	 *  - waiting commands from Configuration.PORT_NUMBER
	 *  - commandProcessor waits and processes commands at Configuration.PORT_NUMBER
	 * @throws SocketException 
	 * @throws InvalidName 
	 */
	public void up() throws Exception {
			System.out.println( "Server running" );
			String [] names  = new String[]{Configuration.INSTITUTION_CONCORDIA, Configuration.INSTITUTION_DAWSON, Configuration.INSTITUTION_MCGILL}; 
			
			Properties props = new Properties();
			props.put("org.omg.CORBA.ORBInitialHost", "localhost");
			props.put("org.omg.CORBA.ORBInitialPort", Configuration.PORT_NUMBER);
			ORB orb = ORB.init(new String[]{}, props);
			POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			PrintWriter pw = null;
			commandSocket = new DatagramSocket( Configuration.REPLICA_MANAGER_PORT);
			reportingSocket = new DatagramSocket( Configuration.REPORT_PROCESSING_PORT );
			reservationSocket = new DatagramSocket( Configuration.RESERVATION_PROCESSING_PORT );
			
			LibraryServerImpl servant = null;
			
			for( String institution : names ){
				servant = new LibraryServerImpl(institution, Configuration.initBooks(institution), Configuration.initAccounts(institution));
				servant.setNodes( names );
				servant.startReservationServer(servant, reservationSocket);
				servant.startReportingServer(servant, reportingSocket);
				servant.log( "registering -- " + institution + " --- reporting port "+ reportingSocket.getPort()+ " " + reportingSocket.getLocalPort() + " --- reservation port " + reservationSocket.getPort() );
				//to make our replication manager runs for-ever???
				org.omg.CORBA.Object reference = poa.id_to_reference(poa.activate_object(servant));
				pw = new PrintWriter(String.format("ior-%s.txt", institution));
				pw.println(orb.object_to_string(reference));
				pw.close();
				libraries.put(institution, servant);			
				poa.the_POAManager().activate();
			}
			new Thread(new CommandProcessor(rm, libraries, commandSocket)).start();
			orb.run();
	}

	
	/**
	 * @throws Exception 
	 * @todo use resync() to notify replicas in current grid to forward update messages here 
	 * @todo check if this approach can work or not.
	 * @todo in kill, check if current instance is not lost.
	 * @todo check if it is feasible to use rm.kill() and rm.up();
	 * @todo this is done when there is a Byzantine error
	 */
	public void reboot(ReplicaManager replicaManager) throws Exception {
		replicaManager.kill();
		replicaManager.up();
		replicaManager.resync();
	}
	/**
	 * @todo if whatever has been said above is correct, please move the code down here.	
	 */
	public void reboot() throws Exception {
		//
	}

	
	/**
	 * @throws SocketException 
	 * @warning This function has to run at last time.
	 * The receive method, listens for data sent to this replica manager.
	 * It parses the NetworkMessage
	 * 	 - operation, destination, extracts the payload
	 *   -  
	 */
	public void receive() throws SocketException {
		
			
	}
	
	/**
	 * Kills current instances of the server. 
	 * for wakeup, it will request data from other replica managers for data 
	 */
	public void kill() {
		//@todo stop the receiver thread
		//@todo stop the resynch thread
		//@todo stop the acknowledgment thread
	}

	public void resync() {
		//waits for incoming updates.
		//redirects update to a proper server 
	}
	public void acknowledge() {
		//this methods should not be here ... 
	}
	
	
	
	
	/**
	 * The main function will launch the rm. 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		rm = new ReplicaManager(); 
		rm.up();
	}


	
    
}