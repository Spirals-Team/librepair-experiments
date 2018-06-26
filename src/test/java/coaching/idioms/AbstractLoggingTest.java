
package coaching.idioms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * AbstractLoggingTest Class.
 */
public class AbstractLoggingTest {

    /** provides logging. */
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
        final MockLog mockLog = new MockLog();
        assertNotNull("Value cannot be null", mockLog);
        mockLog.toLog();
    }

    /**
     * Unit Test Send to log.
     */
    @Test
    public void testSendLogTo() {
        final MockLog mockLog = new MockLog();
        assertNotNull("Value cannot be null", mockLog);
        mockLog.logTo(LOG);
    }

}
