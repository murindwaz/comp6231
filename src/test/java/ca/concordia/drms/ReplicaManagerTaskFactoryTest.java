package ca.concordia.drms;



import static org.junit.Assert.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.task.*;


public class ReplicaManagerTaskFactoryTest{
	
	
	private LibraryServerImpl libraryServer; 
	private ReplicaManager replicaManager;
	private Map<String, LibraryServerImpl> libraries;
	private DatagramPacket datagramPacket; 
	private DatagramSocket datagramSocket;
	private NetworkMessage networkMessage;
	
	@Before public void setUp(){
		libraryServer = new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA);
		replicaManager = new ReplicaManager();
		libraries = new HashMap<String,LibraryServerImpl>();
	}
	@After  public void tearDown(){}; 
	
	
	@Test 
	public void testCanCreateReplicaManagerTasks() throws Exception{
		
		assertNotNull( new ReplicaManagerTaskFactory() );
		//@todo retest this 
		DatagramPacket datagramPacket = new DatagramPacket(null, 0);
		NetworkMessage ntwkmessage = new NetworkMessage();
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT); 
		Task task = ReplicaManagerTaskFactory.create(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
		assertTrue(task instanceof AccountTask);
		
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_RESERVATION); 
		task = ReplicaManagerTaskFactory.create(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
		assertTrue(task instanceof ReservationTask);
		
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_OVERDUE); 
		task = ReplicaManagerTaskFactory.create(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
		assertTrue(task instanceof OverdueTask);
	}
	
	
}