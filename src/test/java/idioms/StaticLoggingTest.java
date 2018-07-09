package idioms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StaticLoggingTest Class.
 */
public class StaticLoggingTest {

	private static final Logger LOG = LoggerFactory.getLogger(StaticLoggingTest.class);

	/**
	 * Unit Test to send to log.
	 */
	@Test
	public void testSendToLog() {
		new StaticLogging().toLog();
	}

	/**
	 * Unit Test to send log to.
	 */
	@Test
	public void testSendLogTo() {
		new StaticLogging().logTo(LOG);
	}

}
