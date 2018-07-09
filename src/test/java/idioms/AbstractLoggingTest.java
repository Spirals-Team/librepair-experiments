package idioms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractLoggingTest Class.
 */
public class AbstractLoggingTest {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractLoggingTest.class);

	/**
	 * MockLog Class.
	 */
	public final class MockLog extends AbstractLogging {
	}

	/**
	 * Unit Test Send to log.
	 */
	@Test
	public void testSendToLog() {
		new MockLog().toLog();
	}

	/**
	 * Unit Test Send to log.
	 */
	@Test
	public void testSendLogTo() {
		new MockLog().logTo(LOG);
	}

}
