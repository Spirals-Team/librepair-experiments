
package coaching.associations;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * class PolymorphicListTest.
 */
public class PolymorphicListTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(PolymorphicListTest.class);

    /**
     * Unit Test to polymorphic list.
     */
    @Test
    public void testPolymorphicList() {
        LOG.info("testPolymorphicList");
        final PolymorphicList polymorphicList = new PolymorphicList();
        assertNotNull(polymorphicList);
        LOG.info(polymorphicList.toString());
    }

    /**
     * Unit Test to polymorphic list usage.
     */
    @Test
    public void testPolymorphicListUsage() {
        LOG.info("testPolymorphicList");
        final PolymorphicList polymorphicList = new PolymorphicList();
        assertNotNull(polymorphicList);
        polymorphicList.add(0);
        LOG.info(polymorphicList.toString());
    }

    /**
     * Unit Test to add.
     */
    @Test
    public void testAdd() {
        LOG.info("testAdd");
        final PolymorphicList polymorphicList = new PolymorphicList();
        assertNotNull(polymorphicList);
        assertNotNull(polymorphicList.add(1));
        LOG.info(polymorphicList.toString());
    }

}
