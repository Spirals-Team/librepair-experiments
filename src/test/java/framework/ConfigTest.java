
package framework;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

import coaching.config.Configuration;

/**
 * ConfigTest Class.
 */
public class ConfigTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigTest.class);

    /**
     * configuration Class.
     */
    public final class Config extends Configuration {
    }

    /**
     * Test configuration.
     */
    @Test
    public void testConfig() {
        final Config config = new Config();
        assertNotNull(config);
        LOG.info(config.toString());
    }

}
