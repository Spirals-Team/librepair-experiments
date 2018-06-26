
package patterns.bridge;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * BridgeTest Class.
 */
public class BridgeTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(BridgeTest.class);

    /**
     * Unit Test to operation.
     */
    @Test
    public void testOperation() {
        final RefinedAbstraction instance = new RefinedAbstraction(new Implementor());
        assertNotNull("Value cannot be null", instance);
        LOG.info("{}", instance.toString());
    }

}
