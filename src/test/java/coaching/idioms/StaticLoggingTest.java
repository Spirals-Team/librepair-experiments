
package coaching.idioms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * StaticLoggingTest class.
 */
public class StaticLoggingTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(StaticLoggingTest.class);

    /**
     * Unit Test to send to log.
     */
    @Test
    public void testSendToLog() {
        assertNotNull(LOG);
    }

}
