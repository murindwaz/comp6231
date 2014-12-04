package ca.concordia.drms;



import static org.junit.Assert.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.server.LibraryServerImpl;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.StringTransformer;
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
		libraries.put(Configuration.INSTITUTION_CONCORDIA, new LibraryServerImpl(Configuration.INSTITUTION_CONCORDIA));
		datagramPacket = new DatagramPacket(new byte[]{}, 0);
		networkMessage = new NetworkMessage();
		networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
	}
	@After  public void tearDown(){}; 
	
	
	@Test 
	public void testCanCreateReplicaManagerTasks() throws Exception{
		
		assertNotNull( new ReplicaManagerTaskFactory() );
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT); 
		networkMessage.setPayload( StringTransformer.getString( new Account("p","m","m","514","pm","pm", Configuration.INSTITUTION_CONCORDIA)));
		Task task = ReplicaManagerTaskFactory.create(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
		assertTrue(task instanceof AccountTask);
		
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_RESERVATION); 
		task = ReplicaManagerTaskFactory.create(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
		assertTrue(task instanceof ReservationTask);
		
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_OVERDUE); 
		task = ReplicaManagerTaskFactory.create(replicaManager, libraries, networkMessage, datagramSocket, datagramPacket);
		assertTrue(task instanceof OverdueTask);
	}
	
	
}