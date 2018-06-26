
package coaching.pool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * AbstractResourcePoolTest Class.
 */
public class AbstractResourcePoolTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractResourcePoolTest.class);

    /**
     * TestResourcePool Class.
     */
    public class MockResourcePool extends AbstractResourcePool<Boolean> {
    }

    /**
     * Test abstract resource pool constructor.
     */
    @Test
    public void testAbstractResourcePool() {
        LOG.debug("testAbstractResourcePool");
        assertNotNull("Value cannot be null", new MockResourcePool());
    }

    /**
     * Test abstract resource pool typical usage.
     */
    @Test
    public void testTypicalUsage() {
        LOG.debug("testTypicalUsage");

        // Given a resource pool
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
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

    /**
     * Test add element to abstract resource pool.
     */
    @Test
    public void testAdd() {
        LOG.debug("testAdd");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        assertNotNull("Value cannot be null", testResourcePool.add(true));
        assertNotNull("Value cannot be null", testResourcePool.add(false));
        assertEquals(2, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
    }

    /**
     * Test remove abstract resource pool.
     */
    @Test
    public void testRemove() {
        LOG.debug("testRemove");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        testResourcePool.add(true);
        testResourcePool.add(false);
        assertEquals(2, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
    }

    /**
     * Test offer abstract resource pool.
     */
    @Test
    public void testOffer() {
        LOG.debug("testOffer");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        assertTrue(testResourcePool.offer(true));
        assertTrue(testResourcePool.offer(false));
        assertEquals(2, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
    }

    /**
     * Test remove from abstract resource pool when null.
     */
    @Test
    public void testRemoveWhenNull() {
        LOG.debug("testRemoveWhenNull");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        assertNotNull("Value cannot be null", testResourcePool.remove(null));
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
    }

    /**
     * Test get from abstract resource pool when empty.
     */
    @Test
    public void testGetWhenEmpty() {
        LOG.debug("testGetWhenEmpty");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        assertNull(testResourcePool.take());
        assertNull(testResourcePool.take());
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
    }

    /**
     * Test get from abstract resource pool.
     */
    @Test
    public void testGet() {
        LOG.debug("testGet");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        testResourcePool.add(true);
        testResourcePool.add(false);
        assertNotNull("Value cannot be null", testResourcePool.take());
        assertNotNull("Value cannot be null", testResourcePool.take());
        assertNull(testResourcePool.take());
        assertEquals(0, testResourcePool.countFree());
        assertEquals(2, testResourcePool.countUsed());
    }

    /**
     * Test release from abstract resource pool.
     */
    @Test
    public void testRelease() {
        LOG.debug("testRelease");
        final MockResourcePool testResourcePool = new MockResourcePool();
        assertNotNull("Value cannot be null", testResourcePool);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(0, testResourcePool.countUsed());
        testResourcePool.add(true).add(false);
        assertTrue(testResourcePool.take() != null);
        assertTrue(testResourcePool.take() != null);
        assertTrue(testResourcePool.take() == null);
        assertEquals(0, testResourcePool.countFree());
        assertEquals(2, testResourcePool.countUsed());
    }

}
