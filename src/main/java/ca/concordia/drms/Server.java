package ca.concordia.drms;

import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;



/**
 * This link shows how to do that using : a single thread executor service
 * @link http://stackoverflow.com/questions/13939538/java-single-threaded-rmi-or-alternative
 * To enable threads on LibraryServerImpl revisit this section::
 * @link http://stackoverflow.com/questions/14839955/java-rmi-server-side-threading
 * @author murindwaz
 * 
 * running UDP thread 
 * http://gamedev.stackexchange.com/questions/23719/unable-to-connect-to-udp-server-using-ip-as-hostname
 * 
 * 
 * mere example 
 * http://www.programcreek.com/java-api-examples/index.php?api=java.net.SocketAddress
 *
 * 
 * 
 * 
 * WARNING: "IOP00410201: (COMM_FAILURE) Connection failure: socketType: IIOP_CLEAR_TEXT; hostname: 192.168.2.19; port: 900"
 * Followed by the exception, is a known problem in UNIX
 * @link http://stackoverflow.com/questions/13017782/issues-with-corba-communication
 * @link http://jeewesley.blogspot.ca/2008/12/glassfish-ejb3-remote-interface-on.html
 * @link http://stackoverflow.com/questions/13721845/corba-remote-access
 * Changing /etc/hosts would not be appreciated as there are other servers using this server name.
 * Alternative is to use IORs instead  
 *
 *
 * from this link doesn't seem to work @link https://axis.apache.org/axis2/java/core/docs/corba-deployer.html
 * NamingContextExt namingContextExt = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
 * namingContextExt.rebind(namingContextExt.to_name( institution ), reference );
 *
 */
public class Server {
	public static void main( String [] args ) throws Exception{
		System.out.println( "Server running");
		String [] names  = new String[]{Configuration.INSTITUTION_CONCORDIA, Configuration.INSTITUTION_DAWSON, Configuration.INSTITUTION_MCGILL}; 
		Properties props = new Properties();
		//props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		props.put("org.omg.CORBA.ORBInitialPort", Configuration.PORT_NUMBER);
		ORB orb = ORB.init(args, props);
		POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		PrintWriter pw = null;
		LibraryServerImpl servant = null;
		DatagramSocket reportingSocket = new DatagramSocket( Configuration.REPORT_PROCESSING_PORT );
		DatagramSocket reservationSocket = new DatagramSocket( Configuration.RESERVATION_PROCESSING_PORT );
		for( String institution : names ){
			servant = new LibraryServerImpl(institution, Configuration.initBooks(institution), Configuration.initAccounts(institution));
			servant.setNodes( names );//should I use whithout?
			org.omg.CORBA.Object reference = poa.id_to_reference( poa.activate_object(servant));
			servant.startReservationServer(servant, reservationSocket);
			servant.startReportingServer(servant, reportingSocket);
			pw = new PrintWriter(String.format("ior-%s.txt", institution));
			pw.println(orb.object_to_string(reference));
			pw.close();
			servant.log( "registering -- " + institution + " --- reporting port "+ reportingSocket.getPort()+ " " + reportingSocket.getLocalPort() + " --- reservation port " + reservationSocket.getPort() );
			poa.the_POAManager().activate();
		}
		orb.run();
	}
}