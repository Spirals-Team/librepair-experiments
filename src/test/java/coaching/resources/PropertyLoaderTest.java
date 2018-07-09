
package coaching.resources;

import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PropertyLoaderTest {

    /**
     * ConfigurationLoader class.
     */
    public class Configuration extends PropertiesLoader {
        public Configuration() {
            super("./Configuration.properties");
        }
    }

    /**
     * Unit test to typical usage.
     */
    @Test
    public void testTypicalUsage() {
        // Given a resource file exists called ResourceLoader.properties

        // When
        final PropertiesLoader propertiesLoader = new PropertiesLoader();

        // Then
        assertNotNull(propertiesLoader);
        assertTrue(propertiesLoader.isLoaded());
    }

    /**
     * Unit test to typical usage specific properties.
     */
    @Test
    public void testTypicalUsageSpecificProperties() {
        // Given a resource file exists called SpecificResource.properties

        // When
        final Configuration configuration = new Configuration();

        // Then
        assertNotNull(configuration);
        assertTrue(configuration.isLoaded());
    }

    @Test
    public void testType() {
        assertThat(PropertiesLoader.class, notNullValue());
    }

}
