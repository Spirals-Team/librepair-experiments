
package coaching.polymorphism;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The AbstractTypeTest class.
 */
public class AbstractTypeTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractTypeTest.class);

    /** The abstract type. */
    private TypeInterface abstractType;

    /**
     * Unit Test to one.
     */
    @Test
    public void testSubTypeOne() {
        LOG.info("testSubTypeOnePolymorphism");
        this.abstractType = new SubTypeOne();
        assertNotNull(this.abstractType);
        LOG.info(this.abstractType.toString());
        assertNotNull(this.abstractType.operation());
        LOG.info(this.abstractType.toString());
    }

    /**
     * Unit Test to two.
     */
    @Test
    public void testSubTypeTwo() {
        LOG.info("testSubTypeOnePolymorphism");
        this.abstractType = new SubTypeTwo();
        assertNotNull(this.abstractType);
        LOG.info(this.abstractType.toString());
        this.abstractType.operation();
        assertNotNull(this.abstractType.operation());
        LOG.info(this.abstractType.toString());
    }

}
