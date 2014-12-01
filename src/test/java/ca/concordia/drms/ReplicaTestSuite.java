package ca.concordia.drms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ReplicaManagerTest.class})
public class ReplicaTestSuite extends TestCase{

	// text test runner that tells if tests fails
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new JUnit4TestAdapter(ReplicaManagerParserTest.class));;
		junit.textui.TestRunner.run(new JUnit4TestAdapter(ReplicaManagerTaskFactoryTest.class));;
		junit.textui.TestRunner.run(new JUnit4TestAdapter(ReplicaManagerTest.class));;
	}
}