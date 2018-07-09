
package patterns.builder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The BuilderTest class.
 */
public class BuilderTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(BuilderTest.class);

    /**
     * Unit Test to build one.
     */
    @Test
    public void testBuilderOne() {
        final BuilderOne builder = new BuilderOne();
        assertNotNull(builder);
        final AbstractPart product = builder.build();
        LOG.info("product = {}", product);
    }

    /**
     * Unit Test to build two.
     */
    @Test
    public void testBuilderTwo() {
        final BuilderTwo builder = new BuilderTwo();
        assertNotNull(builder);
        final AbstractPart product = builder.build();
        LOG.info("product = {}", product);
    }

}
