
package coaching.context;

import java.util.Properties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The Class AbstractContextTest.
 */
public class AbstractContextTest {

    /**
     * The Class TestContext.
     */
    public class TestContext extends AbstractContext {

        /**
         * The Constructor.
         */
        private TestContext() {
            super();
        }

        /**
         * The Constructor.
         *
         * @param properties the properties
         */
        private TestContext(final Properties properties) {
            super(properties);
        }
    }

    /**
     * Test abstract context.
     */
    @Test
    public void testAbstractContext() {
        final TestContext testContext = new TestContext();
        assertNotNull(testContext);
    }

    /**
     * Test abstract context properties.
     */
    @Test
    public void testAbstractContextProperties() {
        final TestContext testContext = new TestContext(new Properties());
        assertNotNull(testContext);
    }

    /**
     * Test set properties.
     */
    @Test
    public void testSetProperties() {
        final TestContext testContext = new TestContext();
        assertNotNull(testContext);
        testContext.setProperties(new Properties());
    }

    /**
     * Test set get property.
     */
    @Test
    public void testSetGetProperty() {
        final TestContext testContext = new TestContext();
        assertNotNull(testContext);
        final String key = "key";
        final String value = "value";
        testContext.setProperty(key, value);
        assertEquals(value, testContext.getProperty(key));
    }

}
