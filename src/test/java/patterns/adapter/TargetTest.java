
package patterns.adapter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The TargetTest Class.
 */
public class TargetTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(TargetTest.class);

    /**
     * Unit Test to request.
     */
    @Test
    public void testRequest() {
        final TargetAdapter targetAdapter = new TargetAdapter();
        assertNotNull("Value cannot be null", targetAdapter);
        LOG.info("{}", targetAdapter.toString());
    }

}
