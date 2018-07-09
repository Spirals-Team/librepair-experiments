
package patterns.adapter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The TargetTest class.
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
        assertNotNull(targetAdapter);
        LOG.info(targetAdapter.toString());
    }

}
