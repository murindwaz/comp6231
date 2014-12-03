package ca.concordia.drms;

import java.net.SocketException;

/**
 * The interface for the replica
 * The replica has to implement following functionalities 
 * 	 - stop
 * 	 - start 
 *   - re/sync
 *   
 *   
 *   Should also implement 
 *   	- failure detection and 
 *   	- recovery 
 * @author murindwaz
 */
public interface Replica {
	
	/**
	 * The start function spins a new server instance
	 * @throws Exception 
	 */
	void up() throws Exception;
	
	/**
	 * The stop function will stop 
	 */
	void kill();
	
	/**
	 * Synchronizes one instance with another
	 */
	void resync();
	/**
	 * receive receives requests and call appropriate server side function
	 * @throws SocketException 
	 * 	
	 */
	void receive() throws SocketException;
	
	/**
	 * The replica send acknowledgment 
	 *  Required parameters 
	 *  	- String topic 
	 *  	- String message
	 */
	void acknowledge();
	
}