package ca.concordia.drms;



import static org.junit.Assert.*;

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
		
		NetworkMessage ntwkmessage = new NetworkMessage();
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT); 
		Task task = ReplicaManagerTaskFactory.create(ntwkmessage, libraryServer);
		assertTrue(task instanceof AccountTask);
		
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_RESERVATION); 
		task = ReplicaManagerTaskFactory.create(ntwkmessage, libraryServer);
		assertTrue(task instanceof ReservationTask);
		
		ntwkmessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_OVERDUE); 
		task = ReplicaManagerTaskFactory.create(ntwkmessage, libraryServer);
		assertTrue(task instanceof OverdueTask);
	}
	
	
}