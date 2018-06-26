
package coaching.resources;

import coaching.resources.ResourceLoader.ConfigurationNotLoadedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

/**
 * ResourceLoader class tests.
 *
 * <code>
 *  Class.getResourceAsStream("resource.properties");
 *  Class.getResourceAsStream("/package/resource.properties");
 *  ClassLoader.getResourceAsStream("package/resource.properties");
 *  ResourceBundle.getBundle("package.resource");
 * </code>
 */
public class ResourceLoaderTest {

    /**
     * SpecificResource class.
     */
    public class SpecificResource extends ResourceLoader {
    }

    /**
     * Unit test to typical usage.
     */
    @Test
    public void testTypicalUsage() {
        // Given a resource file exists called SpecificResource.properties

        // When
        final ResourceLoader resourceLoader = new SpecificResource();

        // Then
        assertNotNull(resourceLoader);
        assertTrue(resourceLoader.isLoaded());
    }

    /**
     * Unit test to typical usage.
     */
    @Test
    public void testDefaultUsage() {
        // Given a resource file exists called ResourceLoader.properties

        // When
        final ResourceLoader resourceLoader = new SpecificResource();

        // Then
        assertNotNull(resourceLoader);
        assertTrue(resourceLoader.isLoaded());
    }

    /**
     * Unit test to reload usage.
     */
    @Test
    public void testReloadUsage() {
        // Given a resource loader
        final ResourceLoader resourceLoader = new ResourceLoader();
        assertNotNull(resourceLoader);

        // When we (re)load
        resourceLoader.load();

        // Then
        assertNotNull(resourceLoader);
        assertTrue(resourceLoader.isLoaded());
    }

    /**
     * Unit test for load resource by name.
     */
    @Test
    public void testResourceLoaderName() {
        // Given
        final String propertyFileName = "./ResourceLoader.properties";
        assumeNotNull(propertyFileName);

        // When
        final ResourceLoader resourceLoader = new ResourceLoader(propertyFileName);
        assertNotNull(resourceLoader);

        // Then
        assertTrue(resourceLoader.isLoaded());
    }

    /**
     * Unit test to load resource by name.
     */
    @Test
    public void testLoadResourceByName() {
        // Given
        final String propertyFileName = "./ResourceLoader.properties";
        assumeNotNull(propertyFileName);
        final ResourceLoader resourceLoader = new ResourceLoader();
        assertNotNull(resourceLoader);

        // When
        resourceLoader.load(propertyFileName);
        assertNotNull(resourceLoader);

        // Then
        assertTrue(resourceLoader.isLoaded());
    }

    /**
     * Unit test for resource loader null.
     */
    @Test(expected = ConfigurationNotLoadedException.class)
    public void testResourceLoaderNull() {
        // Given

        // When
        final ResourceLoader resourceLoader = new ResourceLoader(null);
        assertNotNull(resourceLoader);

        // Then
        assertTrue(resourceLoader.isLoaded());
    }

    /**
     * Unit test to load resource by null name.
     */
    @Test(expected = ConfigurationNotLoadedException.class)
    public void testLoadResourceNull() {
        // Given
        final ResourceLoader resourceLoader = new ResourceLoader();
        assertNotNull(resourceLoader);

        // When the property filename is ull
        final String propertyFileName = null;
        resourceLoader.load(propertyFileName);

        // Then ConfigurationNotLoadedException is expected.
    }

    /**
     * Unit test to resource loader missing.
     */
    @Test(expected = AssertionError.class)
    public void testResourceLoaderMissing() {
        // Given
        final String missingResource = "./MissingResource.properties";
        assumeNotNull(missingResource);

        // When
        final ResourceLoader resourceLoader = new ResourceLoader(missingResource);
        assertNotNull(resourceLoader);

        // Then
        assertTrue(resourceLoader.isLoaded());
    }

    @Test
    public void testType() {
        assertThat(ResourceLoader.class, notNullValue());
    }

    // @Test
    // public void testLoadResourceString() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final String propertyFileName = "./ResourceLoader.properties";
    // final ResourceLoader actual = resourceLoader.load(propertyFileName);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

    // @Test
    // public void testLoadResourceMissing() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final String propertyFileName = "MissingResource";
    // final ResourceLoader actual = resourceLoader.load(propertyFileName);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

    // @Test
    // public void testLoadResourceNull() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final ResourceLoader actual = resourceLoader.load(null);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

    // @Test
    // public void testloadFromString() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final String filename = "Resource";
    // final ResourceLoader actual = resourceLoader.load(filename);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

    // @Test
    // public void testloadFromNull() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final String filename = null;
    // final ResourceLoader actual = resourceLoader.load(filename);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

    // @Test
    // public void loadFromInputStream() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final InputStream streamForResource = null;
    // final ResourceLoader actual = resourceLoader.load(streamForResource);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

    // @Test
    // public void loadFromInputStreamNull() {
    // final ResourceLoader resourceLoader = new ResourceLoader();
    // assertNotNull(resourceLoader);
    // final InputStream streamForResource = null;
    // final ResourceLoader actual = resourceLoader.load(streamForResource);
    // final ResourceLoader expected = null;
    // assertThat(actual, is(equalTo(expected)));
    // }

}
