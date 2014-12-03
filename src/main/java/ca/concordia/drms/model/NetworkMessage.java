package ca.concordia.drms.model;


/**
 * For the lack of a better name, the NetworkMessage were intended to be simply named Message. 
 * @author murindwaz
 */
public class NetworkMessage {
	
	/**
	 * Operation to be done by the ReplicaManager. 
	 * Type:
	 * 	- Configuration.OPERATION_ACCOUNT
	 *  - Configuration.OPERATION_OVERDUE
	 *  - Configuration.OPERATION_INTERLIB
	 *  - Configuration.OPERATION_RESERVATION
	 *  
	 *  @todo add following Operations
	 *  - Configuration.REPLICA_OPERATION_RESYNC
	 *  - Configuration.REPLICA_OPERATION_ELECTION [Not covered in this implementation]
	 */
	private String operation; 
	/**
	 * 
	 */
	private String destination; 
	/**
	 * 
	 */
	private String payload;
	
	
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
	
	public NetworkMessage(){}
	public String toString(){
		return "{operation:\""+operation+"\",destination:\""+destination+"\", payload:\""+payload+"\"}";
	}
}