
package coaching.pool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * BlockingPoolTest Class.
 */
public class BlockingPoolTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(BlockingPoolTest.class);

    /**
     * TestResourcePool Class.
     */
    public class TestResourcePool extends AbstractBlockingPool<Boolean> {
    }

    /**
     * Test blocking pool.
     */
    @Test
    public void testBlockingPool() {
        final TestResourcePool resourcePool = new TestResourcePool();
        assertNotNull("Value cannot be null", resourcePool);
        LOG.info(resourcePool.toString());
    }

    /**
     * Unit test typical usage..
     */
    @Test
    public void testTypicalUsage() {
        // Given a resource pool
        final TestResourcePool testResourcePool = new TestResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        testResourcePool.add(true);
        testResourcePool.add(false);
        assertEquals(2, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());

        // * pool
        while (testResourcePool.countFree() > 0) {
            final Boolean bool = testResourcePool.take();
            if (bool) {
                testResourcePool.remove(bool);
            }
        }

        // Then
        assertEquals(0, testResourcePool.countFree());
        assertEquals(1, testResourcePool.countUsed());
    }

}
