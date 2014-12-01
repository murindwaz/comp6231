package ca.concordia.drms.util;

public class NetworkMessageEncoder<NetworkMessage> implements Encoder<NetworkMessage> {
	
	/**
	 * Takes a NetworkMessage and encodes it into a parsable message
	 */
	public String encode(NetworkMessage object) {
		/**
		 * @todo take any payload specific object and encode it 
		 */
		return object.toString();
	}
	
}