
package patterns.factory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * FactoryInterfaceTest class.
 */
public class FactoryInterfaceTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(FactoryInterfaceTest.class);

    /**
     * Unit Test to factory one.
     */
    @Test
    public void testFactoryOne() {
        LOG.info("testFactoryOne");
        final ConcreteFactoryOne factory = new ConcreteFactoryOne();
        assertNotNull(factory);
        final AbstractProductAlpha productA = factory.createProductA();
        assertNotNull(productA);
        final AbstractProductBeta productB = factory.createProductB();
        assertNotNull(productB);
    }

    /**
     * Unit Test to factory two.
     */
    @Test
    public void testFactoryTwo() {
        LOG.info("testFactoryOne");
        final ConcreteFactoryTwo factory = new ConcreteFactoryTwo();
        assertNotNull(factory);
        final AbstractProductAlpha productA = factory.createProductA();
        assertNotNull(productA);
        final AbstractProductBeta productB = factory.createProductB();
        assertNotNull(productB);
    }

}
