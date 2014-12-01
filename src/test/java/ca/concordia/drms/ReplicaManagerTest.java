package ca.concordia.drms;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class ReplicaManagerTest {
	
	private ReplicaManager replica;
	@Test 
	public void testCanManagerGetCreateAccountCommand(){
		//send account creation  message.
		//receive the account creation message 
		//send back to us a response 
		//assert( false );
	}
	
	@Before 
	public void setUp() throws Exception{
		ReplicaManager.main( new String[]{ "test" });
		replica = new ReplicaManager();
	}
	
	@Test 
	public void testCanInstanciateReplica(){
		boolean isReplica = replica instanceof Replica; 
		//assertTrue( isReplica );
	}
	
	@After public void tearDown(){
		//send a suicide message to the replica
		replica.kill();
		replica  = null;
	}
	
}