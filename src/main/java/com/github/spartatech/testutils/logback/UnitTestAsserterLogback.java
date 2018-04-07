package com.github.spartatech.testutils.logback;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.ComparisonFailure;
import org.slf4j.LoggerFactory;

import com.github.spartatech.testutils.logback.constant.ExpectValue;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import junit.framework.AssertionFailedError;


/** 
 * 
 * Logback utility to spy logs calls
 * The way it works is:
 * - Instantiate a new {@link UnitTestAsserterLogback} giving the logger to be spied.
 * - Declare all your expectations using addExpectation
 * - call method to be tested
 * - call {@code UnitTestAsserterLogback.assertLogExpectations()}
 * 
 * @author Daniel Conde Diehl
 * 
 * History: 
 *    Dec 27, 2016 - Daniel Conde Diehl
 *  
 */
public class UnitTestAsserterLogback  {

    private LinkedList<LogEntryItem> expectations = new LinkedList<>();
    private LinkedList<ILoggingEvent> events = new LinkedList<>();
    
    private UnitTestAsserterLogbackAppender appender;
    
    
    /**
     * Constructor receiving the logger as a String.
     * @param logger name as a String
     */
    public UnitTestAsserterLogback(String logger) {
        appender = new UnitTestAsserterLogbackAppender(logger, events);
        attachAppenderToLogback();
    }
    
    /**
     * Constructor receiving the logger as a class.
     * @param clazz Class that will be used as a logger name
     */
    public UnitTestAsserterLogback(Class<?> clazz) {
        appender = new UnitTestAsserterLogbackAppender(clazz, events);
        attachAppenderToLogback();
    }
    
    /**
     * Adds a new expectation to the logger. 
     * 
     * @param level expected for the log entry
     * @param logMessage message expected for the log entry
     * @param params list of parameters for the log entry.
     */
    public void addExpectation(Level level, String logMessage, Object...params) {
        expectations.add(new LogEntryItem(level, logMessage, params));
    }
    
    
    
    /**
     * Replay expectations to check if all logs happened.
     * Analyzes in order and all logs supposed to be there 
     * 
     * @throws AssertionError Throws an assertion error when the asserts fail
     * @deprecated this method will be remove in future releases, please use {@code UnitTestAsserterLogback.assertLogExpectations(false)} instead
     */
    @Deprecated
    public void assertLogExpectations() throws AssertionError {
    	assertLogExpectations(false);
    }    
    
    /**
     * Replay expectations to check if all logs happened.
     * Analyzes in order and all logs supposed to be there 
     * 
     * @param ignoreExtraMessages false - if any message other than ones expected happens it fail, also check in order, 
     * 							  true - ensure that messages that were expected happens, allows extra messages and does not check 
     * @throws AssertionError Throws an assertion error when the asserts fail
     */
	public void assertLogExpectations(boolean ignoreExtraMessages) throws AssertionError {
    	if (!ignoreExtraMessages) {
            if (events.size() != expectations.size()) {
                throw new ComparisonFailure("Invalid number of messages", String.valueOf(expectations.size()), String.valueOf(events.size()));
            }
            
            for (ILoggingEvent event : events) {

                LogEntryItem entry = expectations.remove();

                if (!entry.getMessage().equals(event.getMessage())) {
                    throw new ComparisonFailure("Message mismatch", entry.getMessage(), event.getMessage());
                }
                
                compareEntries(event, entry);
            }
    	} else {
    		for (LogEntryItem entry : expectations) {
    			boolean foundMatch = false;
    			for(ILoggingEvent event : events) {
    				if (entry.getMessage().equals(event.getMessage())) {
    					try {
    						compareEntries(event, entry);
    						foundMatch = true;
    					} catch (ComparisonFailure e) {
    						//Error happened not a match
    					}
    				}
    			}
    			if (!foundMatch) {
    				throw new AssertionFailedError("Message ["+entry + "] not found");
    			}
            }
    	}
    }
    

    /**
     * Compares an expected entry with a logging event, checking if the level, and param match.
     * 
     * @param event Log event that happened
     * @param entry expected entry
     */
    private void compareEntries (ILoggingEvent event, LogEntryItem entry) {
        if (entry.getLevel() != event.getLevel()) {
            throw new ComparisonFailure("LogLevel mismatch", entry.getLevel().toString(), event.getLevel().toString());
        }
        
        int expectedSize = entry.getParams() == null ? 0 : entry.getParams().length;
        int actualSize = event.getArgumentArray() == null ? 0 : event.getArgumentArray().length;
        if (expectedSize != actualSize) {
            throw new ComparisonFailure("Incorrect number of params", 
                                        entry.getParams()== null ? "0" : String.valueOf(entry.getParams().length) , 
                                        event.getArgumentArray() == null ? "0" : String.valueOf(event.getArgumentArray().length));
        }

        for (int i = 0; i < entry.getParams().length; i++) {
            Object expectedParam = entry.getParams()[i];
            Object actualParam = event.getArgumentArray()[i];

            if (ExpectValue.ANY == expectedParam) {
                continue;
            }
            
            if (expectedParam == null && actualParam == null) {
                continue;
            } else if (expectedParam == null && actualParam != null) {
                throw new ComparisonFailure("Param [" + i + "] mismatch", "null", actualParam.toString());
            } else if (!expectedParam.equals(actualParam)) {
                throw new ComparisonFailure("Param [" + i + "] mismatch", expectedParam == null ? "NULL" : expectedParam.toString(), actualParam == null ? "NULL" : actualParam.toString());
            }
        }
    }
    
    /**
     * Attached the log to the logback. 
     */
    private void attachAppenderToLogback() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(appender);
    }
    
    /** 
     * 
     * Internal VO to carry log entries for asserting values.
     * 
     * @author Daniel Conde Diehl 
     * 
     * History: 
     *    Jan 15, 2017 - Daniel Conde Diehl
     *  
     */ 
    class LogEntryItem {
        private Level level;
        private String message;
        private Object[] params;
        
        /**
         * Constructor with all values.
         * 
         * @param level log loevel for the message
         * @param message text message
         * @param params params used in the log
         */
        public LogEntryItem(Level level, String message, Object[] params) {
            this.level = level;
            this.message = message;
            this.params = params;
        }
        
        /**
         * @return the level
         */
        public Level getLevel() {
            return level;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }
        /**
         * @return the params
         */
        public Object[] getParams() {
            return params;
        }

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[level=");
			builder.append(level);
			builder.append(", message=");
			builder.append(message);
			builder.append(", params=");
			builder.append(Arrays.toString(params));
			builder.append("]");
			return builder.toString();
		}
    }
};
