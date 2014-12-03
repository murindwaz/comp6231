package ca.concordia.drms;



import static org.junit.Assert.*;

import java.net.DatagramPacket;
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
	
	@Before public void setUp(){
		libraryServer = new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA);
	}
	@After  public void tearDown(){}; 
	
	
	@Test 
	public void testCanCreateReplicaManagerTasks() throws Exception{
		
		assertNotNull( new ReplicaManagerTaskFactory() );
		//@todo retest this 
		DatagramPacket datagramPacket = new DatagramPacket(null, 0);
		Map<String, LibraryServerImpl> libraries = new HashMap<String,LibraryServerImpl>();
		NetworkMessage ntwkmessage = new NetworkMessage();
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT); 
		Task task = ReplicaManagerTaskFactory.create(datagramPacket, libraries);
		assertTrue(task instanceof AccountTask);
		
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_RESERVATION); 
		task = ReplicaManagerTaskFactory.create(datagramPacket, libraries);
		assertTrue(task instanceof ReservationTask);
		
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_OVERDUE); 
		task = ReplicaManagerTaskFactory.create(datagramPacket, libraries);
		assertTrue(task instanceof OverdueTask);
	}
	
	
}