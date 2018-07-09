
package coaching.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit test for the AbstractConfig object.
 */
public class AbstractConfigTest {

    private static final String FILENAME_KEY = "Filename";

    private static final String CONFIGURATION_PROPERTIES = "Configuration.properties";

    private static final String TEST_CONFIG_PROPERTIES = "TestConfig.properties";

    /** provide logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractConfigTest.class);

    /**
     * Test Configuration class.
     */
    public class TestConfig extends Configuration {

        /**
         * Instantiates a new configuration.
         */
        public TestConfig() {
            super();
        }

        /**
         * Instantiates a new configuration.
         *
         * @param configFilename
         *            the config filename
         */
        public TestConfig(final String configFilename) {
            super(configFilename);
        }
    }

    /**
     * Mock a MissingConfiguration.
     * The configuration file is missing so throws MissingConfigException.
     */
    public class MissingConfiguration extends Configuration {
    }

    /**
     * Mock a DefaultConfiguration.
     * When the configuration file is missing, provide default values.
     */
    public class InvalidConfig extends Configuration {
    }

    @Test
    public void testTypicalUsage() {
        LOG.debug("testTypicalUsage");
        final TestConfig config = new TestConfig();
        assertNotNull(config);
        LOG.trace(config.toString());

        assertEquals(TEST_CONFIG_PROPERTIES, config.valueFor(FILENAME_KEY));
        verifyProperties(config);
    }

    /**
     * Unit Test with missing property file.
     */
    @Test
    public void testMissingConfig() {
        LOG.debug("testMissingConfig");
        final ConfigInterface configuration = new MissingConfiguration();
        assertNotNull(configuration);
    }

    /**
     * Unit Test with missing property file.
     */
    @Test
    public void testMissingConfigString() {
        LOG.debug("testMissingConfigString");
        final ConfigInterface configuration = new TestConfig("Missing");
        assertNotNull(configuration);
    }

    /**
     * Unit Test to system property as override string.
     */
    @Test
    public void testGetSystemProperty() {
        LOG.debug("testGetSystemProperty");
        final String key = "systemPropertyKey";
        final String expectedValue = "systemPropertyValue";
        System.setProperty(key, expectedValue);
        final ConfigInterface configuration = new TestConfig();
        assertNotNull(configuration);
        assertNull(configuration.get(key));
    }

    /**
     * Unit Test to string.
     */
    @Test
    public void testToString() {
        LOG.debug("testToString");
        final ConfigInterface configuration = new TestConfig();
        assertNotNull(configuration);
        final String string = configuration.toString();
        assertNotNull(string);
        LOG.debug("{}.{}", this.getClass().getSimpleName(), string);
    }

    /**
     * Unit Test to configuration.
     */
    @Test
    public void testConfiguration() {
        LOG.debug("testConfiguration");
        final TestConfig config = new TestConfig("Configuration");
        assertNotNull(config);
        LOG.debug(config.toString());

        assertEquals(CONFIGURATION_PROPERTIES, config.get(FILENAME_KEY));
        verifyProperties(config);
    }

    /**
     * Unit Test to abstract Configuration string.
     */
    @Test
    public void testAbstractConfigStringXml() {
        LOG.debug("testAbstractConfigStringXml");
        final ConfigInterface config = new TestConfig(CONFIGURATION_PROPERTIES);
        assertNotNull(config);
        LOG.trace(config.toString());

        assertEquals(CONFIGURATION_PROPERTIES, config.get(FILENAME_KEY));
        verifyProperties(config);
    }

    private void verifyProperties(final ConfigInterface config) {
        assertNull(config.get("missing-key"));
        assertEquals("default", config.get("missing-key", "default"));
        assertEquals("value", config.get("key"));
        assertEquals("Value.000", config.get("000"));
        assertEquals("Value.001", config.get("001"));
        assertEquals("Value.002", config.get("002"));
        LOG.debug(config.toString());
    }

}
