
package coaching.idioms;

import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test the AbstractLogging class.
 */
public class AbstractLoggingTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractLoggingTest.class);
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * MockLog Class.
     */
    public final class MockLog extends AbstractLogging {
        @Override
        public String toString() {
            return String.format("%s [bool=%s, num=%s, uuid=%s]",
                    this.getClass().getSimpleName(),
                    this.bool,
                    this.num,
                    this.uuid);
        }

        protected int num = Integer.MAX_VALUE;
        protected Boolean bool = true;
        protected UUID uuid = UUID.randomUUID();
    }

    /**
     * Unit Test Send to log.
     */
    @Test
    public void testSendToLog() {
        final MockLog mockLog = new MockLog();
        assertNotNull(mockLog);
        mockLog.toLog();
    }

    /**
     * Unit Test Send to log.
     */
    @Test
    public void testSendLogTo() {
        final MockLog mockLog = new MockLog();
        assertNotNull(mockLog);
        mockLog.logTo(LOG);
        mockLog.logTo(this.log);
    }

}
