package ca.concordia.drms.util;



/**
 * Encoder utility, will take any Object and encodes into a parsable string
 * @author murindwaz
 */
public interface Encoder <T> {
	public String encode(T object);
}