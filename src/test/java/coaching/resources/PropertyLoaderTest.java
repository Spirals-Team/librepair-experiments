
package coaching.resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

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
