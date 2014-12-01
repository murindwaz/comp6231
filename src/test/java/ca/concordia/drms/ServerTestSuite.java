package ca.concordia.drms;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Reservation story
 * 	- Student should be able to 
 * 		- connect to server via client ( bonjour command ) 
 * 		- create an account ( account command ) 
 * 		- make a reservation ( reservation ) 
 * 		- 
 * 	- Admin should be able to 
 * @author murindwaz
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ LibraryServerTest.class, ClientTest.class})
public class ServerTestSuite {

	// helpful when running using JUnit 3 Test runners or Ant
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ServerTestSuite.class);
	}

	// text test runner that tells if tests fails
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}