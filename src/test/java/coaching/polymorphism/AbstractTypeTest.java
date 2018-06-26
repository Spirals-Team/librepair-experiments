
package coaching.polymorphism;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The AbstractTypeTest Class.
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
        LOG.info("testPolymorphism");
        this.abstractType = new SubTypeOne();
        assertNotNull("Value cannot be null", this.abstractType);
        LOG.info("{}", this.abstractType);
        assertNotNull("Value cannot be null", this.abstractType.operation());
        LOG.info("{}", this.abstractType);
    }

    /**
     * Unit Test to two.
     */
    @Test
    public void testSubTypeTwo() {
        LOG.info("testPolymorphism");
        LOG.info("{}", this.abstractType);
        this.abstractType = new SubTypeTwo();
        assertNotNull("Value cannot be null", this.abstractType);
        LOG.info("{}", this.abstractType);
        this.abstractType.operation();
        assertNotNull("Value cannot be null", this.abstractType.operation());
        LOG.info("{}", this.abstractType);
    }

}
