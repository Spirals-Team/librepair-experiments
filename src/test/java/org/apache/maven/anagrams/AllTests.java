package org.apache.maven.anagrams;


import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FileUtilsTest.class,
				AnagramsUtilsTest.class })

/**
 * Test Suite class that groups all test. It is used by the TestRunner
 *  
 * @author valerio
 * @version 1.0
 */
public class AllTests {

	/**
	 * Default constructor. It initialize the state of the instance
	 */
	public static Test suite() {
		return new TestSuite("Test suite for AnagramsUtils and FileUtils");
	}

}
