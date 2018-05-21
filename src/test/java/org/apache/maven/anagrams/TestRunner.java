package org.apache.maven.anagrams;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test class for AnagramFinder. It launches all the implemented tests and prints the result 
 * 
 * @author valerio
 * @version 1.0
 */
public class TestRunner {
	
	/**
	 * The launcher of the program.
	 * 
	 * @param args no arguments are required
	 */
	public static void main(String[] args) {
		
		Logger testLogger = LogManager.getLogger("testLogger");
		Logger debuglogger = LogManager.getLogger("debugLogger");
		debuglogger.debug("--------------------- TEST SESSION STARTED ---------------------");
		testLogger.debug("--------------------- TEST SESSION STARTED ---------------------");
		
		//Tests execution
		Result result = JUnitCore.runClasses(AllTests.class);
		
		//We print the tests result. In case of failure we list the failed tests one-by-one
		if(result.wasSuccessful()) {
			testLogger.info("TestRunner - All tests executed successfully");
		} else {
			testLogger.error("TestRunner - The following tests have failed: ");
			for (Failure failure : result.getFailures()) {
				testLogger.error(failure.toString());
			}
		}
		
		debuglogger.debug("--------------------- TEST SESSION ENDED ---------------------");
		testLogger.debug("--------------------- TEST SESSION ENDED ---------------------");
		
	}
	
}  
