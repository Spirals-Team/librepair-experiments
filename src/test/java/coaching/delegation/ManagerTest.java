
package coaching.delegation;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Manager Test.
 */
public class ManagerTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(ManagerTest.class);

    /**
     * Test delegation.
     */
    @Test
    public void testDelegation() {
        LOG.info("testDelegation");
        final Manager manager = new Manager();
        assertNotNull("Value cannot be null", manager);

        final Worker worker = new Worker();
        assertNotNull("Value cannot be null", manager);

        assertNotNull("Value cannot be null", manager.setWorker(worker));
        assertNotNull("Value cannot be null", manager.doProcess());
    }

}
