package ca.concordia.drms;

import static org.junit.Assert.assertTrue;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.drms.model.NetworkMessage;
import ca.concordia.drms.util.Configuration;
import ca.concordia.drms.util.ReplicaManagerParser;

public class CommandProcessorTest {

	
	private DatagramSocket commandSocket;
	private NetworkMessage networkMessage;
	
	@Before 
	public void setUp() throws SocketException{
		networkMessage = new NetworkMessage(); 
	};
	
	
	@After  
	public void tearDown(){
	
	}; 
	
	
	@Test 
	public void testCanExecuteCreateAccountTask() throws Exception{
			
			commandSocket = new DatagramSocket();
			networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
			networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT);
			networkMessage.setPayload("Hey There");
			
			final byte[] message = networkMessage.toString().getBytes("UTF-8");
			final DatagramPacket request = new DatagramPacket(message, message.length, InetAddress.getByName("localhost"), Configuration.RESERVATION_PROCESSING_PORT);
			commandSocket.send(request);
			final byte buffer[] = new byte[Configuration.BUFFER_SIZE];
			final DatagramPacket response = new DatagramPacket(buffer, buffer.length);
			commandSocket.receive(response);
			final NetworkMessage ntwkmessage = ReplicaManagerParser.parseNetworkMessage(new String(response.getData()) );
			System.out.println( ntwkmessage.toString() );
			assertTrue( false );
	}
	
	/**
	
	@Test 
	public void testCanExecuteMakeReservationTask(){
		networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT);
		networkMessage.setPayload("Hey There");
		assert( false );
	}

	
	@Test 
	public void testCanExecuteInterLibraryReservationTask(){
		networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT);
		networkMessage.setPayload("Hey There");
		assert( false );
	}
	
	
	@Test 
	public void testCanExecuteOverdueTask(){
		networkMessage.setDestination(Configuration.INSTITUTION_CONCORDIA);
		networkMessage.setOperation(Configuration.REPLICA_MANAGER_OPERATION_ACCOUNT);
		networkMessage.setPayload("Hey There");
		assert( false );
	}
	**/
	
}