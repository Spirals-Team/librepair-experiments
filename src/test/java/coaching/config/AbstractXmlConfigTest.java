
package coaching.config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * A UnitTest for AbstractXmlConfig objects.
 */
public class AbstractXmlConfigTest {

    private static final String FILENAME_KEY = "Filename";

    private static final String CONFIGURATION_XML = "Configuration.xml";

    private static final String TEST_CONFIG_XML = "TestConfig.xml";

    /** provide logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractXmlConfigTest.class);

    /**
     * Test Configuration class.
     */
    public class TestConfig extends AbstractXmlConfig {

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
     * Missing Configuration.
     */
    public class MissingXmlConfig extends AbstractXmlConfig {
    }

    /**
     * Invalid Configuration.
     */
    public class InvalidXmlConfig extends AbstractXmlConfig {
    }

    /**
     * Unit Test for test abstract Configuration.
     */
    @Test
    public void testTypicalUsage() {
        LOG.debug("testTypicalUsage");
        final AbstractXmlConfig config = new TestConfig();
        assertNotNull(config);
        LOG.trace(config.toString());

        assertEquals(TEST_CONFIG_XML, config.get(FILENAME_KEY));
        verifyProperties(config);
    }

    /**
     * Unit test to missing configuration file.
     */
    @Test
    public void testMissingXmlConfig() {
        LOG.debug("testMissingXmlConfig");
        final AbstractXmlConfig missingConfig = new MissingXmlConfig();
        assertNotNull(missingConfig);
        LOG.debug(missingConfig.toString());
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
     * Unit test to invalid configuration file.
     */
    @Test
    public void testInvalidXmlConfig() {
        LOG.debug("testInvalidConfig");
        final AbstractXmlConfig invalidConfig = new InvalidXmlConfig();
        assertNotNull(invalidConfig);
        LOG.debug(invalidConfig.toString());
    }

    /**
     * Unit Test to get property string.
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
     * Unit Test to abstract Configuration string.
     */
    @Test
    public void testAbstractConfigString() {
        LOG.debug("testAbstractConfigString");
        final ConfigInterface config = new TestConfig("Configuration");
        assertNotNull(config);
        LOG.debug(config.toString());

        assertEquals(CONFIGURATION_XML, config.get(FILENAME_KEY));
        verifyProperties(config);
    }

    /**
     * Unit Test to abstract Configuration string.
     */
    @Test
    public void testAbstractConfigStringXml() {
        LOG.debug("testAbstractConfigString");
        final ConfigInterface config = new TestConfig(CONFIGURATION_XML);
        assertNotNull(config);
        LOG.debug(config.toString());

        assertEquals(CONFIGURATION_XML, config.get(FILENAME_KEY));
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
