
package coaching.tuples;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The BoxTest Class.
 */
public class BoxTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(BoxTest.class);

    /**
     * Unit Test to box.
     */
    @Test
    public void testBox() {
        final Box<String> box = new Box<>();
        assertNotNull("Value cannot be null", box);
    }

    /**
     * Test box object.
     */
    @Test
    public void testBoxObject() {
        final Box<Object> box = new Box<>();
        assertNotNull("Value cannot be null", box);
        LOG.info(box.toString());
    }

    /**
     * Test box string.
     */
    @Test
    public void testBoxString() {
        final Box<String> box = new Box<>();
        assertNotNull("Value cannot be null", box);
        LOG.info(box.toString());
    }

}
