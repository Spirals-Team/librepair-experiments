
package coaching.associations;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * class CompositeClassArrayTest.
 */
public class CompositeClassArrayTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CompositeClassArrayTest.class);

    /**
     * Unit Test to composite class array.
     */
    @Test
    public void testCompositeClassArray() {
        final CompositeClassArray compositeClassArray = new CompositeClassArray();
        assertNotNull("Value cannot be null", compositeClassArray);
        LOG.info("{}", compositeClassArray.toString());
    }

}
