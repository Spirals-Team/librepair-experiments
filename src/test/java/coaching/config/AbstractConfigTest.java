
package coaching.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for Abstract configuration class.
 */
public class AbstractConfigTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractConfigTest.class);

    /**
     * Configuration class.
     */
    public class Configuration extends AbstractConfig {

        /**
         * Instantiates a new configuration.
         */
        public Configuration() {
            super();
        }

        /**
         * Instantiates a new configuration.
         *
         * @param configFilename
         *            the config filename
         */
        public Configuration(final String configFilename) {
            super(configFilename);
        }
    }

    /**
     * Mock a MissingConfiguration.
     * The configuration file is missing so throws MissingConfigException.
     */
    public class MissingConfiguration extends AbstractConfig {
    }

    /**
     * Mock a DefaultConfiguration.
     * When the configuration file is missing, provide default values.
     */
    public class DefaultConfiguration extends AbstractConfig {
    }

    @Test
    public void testTypicalUsage() {
        LOG.debug("testTypicalUsage");
        final Configuration config = new Configuration();
        assertNotNull("Value cannot be null", config);
        LOG.debug("{}", config.toString());
        assertEquals("Configuration.properties", config.valueFor("File"));
        assertEquals("Value.000", config.valueFor("000"));
        assertEquals("Value.001", config.valueFor("001"));
        assertEquals("Value.002", config.valueFor("002"));
    }

    /**
     * Unit Test with missing property file.
     */
    @Test
    public void testMissingConfig() {
        LOG.debug("testMissingConfig");
        final ConfigInterface configuration = new MissingConfiguration();
        assertNotNull("Value cannot be null", configuration);
    }

    /**
     * Unit Test with missing property file.
     */
    @Test
    public void testMissingConfigString() {
        LOG.debug("testMissingConfigString");
        final ConfigInterface configuration = new Configuration("Missing");
        assertNotNull("Value cannot be null", configuration);
    }

    /**
     * Unit Test to abstract configuration.
     */
    @Test
    public void testAbstractConfig() {
        LOG.debug("testAbstractConfig");
        final ConfigInterface configuration = new Configuration();
        assertNotNull("Value cannot be null", configuration);
        final String filename = configuration.getProperty("propertyFilename");
        assertEquals("Configuration.properties", filename);

        final String value = configuration.getProperty("key");
        assertNotNull("Value cannot be null", value);
        assertEquals("value", value);
    }

    /**
     * Unit Test to abstract Configuration string.
     */
    @Test
    public void testAbstractConfigString() {
        LOG.debug("testAbstractConfigString");
        final ConfigInterface configuration = new Configuration("Configuration");
        assertNotNull("Value cannot be null", configuration);
        final String filename = configuration.getProperty("propertyFilename");
        assertEquals("Configuration.properties", filename);

        final String value = configuration.getProperty("key");
        assertNotNull("Value cannot be null", value);
        assertEquals("value", value);
    }

    /**
     * Unit Test to get property string.
     */
    @Test
    public void testGetPropertyString() {
        LOG.debug("testGetPropertyString");
        final ConfigInterface configuration = new Configuration();
        final String filename = configuration.getProperty("propertyFilename");
        assertNotNull("Value cannot be null", configuration);
        assertEquals("Configuration.properties", filename);

        final String value = configuration.getProperty("key");
        assertNotNull("Value cannot be null", value);
        assertEquals("value", value);
    }

    /**
     * Unit Test to get property string with default.
     */
    @Test
    public void testGetPropertyStringDefault() {
        LOG.debug("testGetPropertyStringDefault");
        final ConfigInterface configuration = new Configuration();
        assertNotNull("Value cannot be null", configuration);
        final String filename = configuration.getProperty("propertyFilename");
        assertEquals("Configuration.properties", filename);

        final String value = configuration.getProperty("missing-key", "default");
        assertNotNull("Value cannot be null", value);
        assertEquals("default", value);
    }

    /**
     * Unit Test to get property string.
     */
    @Test
    public void testGetSystemProperty() {
        LOG.debug("testGetSystemProperty");
        final ConfigInterface configuration = new Configuration();
        assertNotNull("Value cannot be null", configuration);
        final String filename = configuration.getProperty("propertyFilename");
        assertEquals("Configuration.properties", filename);

        final String value = configuration.getProperty("missing-key", "default");
        assertNotNull("Value cannot be null", value);
        assertEquals("default", value);
    }

    /**
     * Unit Test to string.
     */
    @Test
    public void testToString() {
        LOG.debug("testToString");
        final ConfigInterface configuration = new Configuration();
        assertNotNull("Value cannot be null", configuration);
        final String string = configuration.toString();
        assertNotNull("Value cannot be null", string);
        LOG.debug("{}.{}", this.getClass().getSimpleName(), string);
        final String value = configuration.getProperty("key");
        assertNotNull("Value cannot be null", value);
        assertEquals("value", value);
    }

    /**
     * Unit Test to configuration.
     */
    @Test
    public void testConfiguration() {
        final Configuration config = new Configuration();
        assertNotNull("Value cannot be null", config);
        LOG.debug("{}", config.toString());
        assertEquals("Configuration.properties", config.valueFor("File"));
        assertEquals("Value.000", config.valueFor("000"));
        assertEquals("Value.001", config.valueFor("001"));
        assertEquals("Value.002", config.valueFor("002"));
    }

}
