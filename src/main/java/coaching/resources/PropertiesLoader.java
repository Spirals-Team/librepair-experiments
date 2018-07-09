
package coaching.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Property Loader class.
 *
 * Loads a properties file by name from the classpath.
 *
 * The resource must be a file with .properties extension.
 *
 * The name is assumed to be absolute and can use either "/" or "." for package
 * segment separation with an
 * optional leading "/" and
 * The ".properties" suffix optional.
 * The following names refer to the same resource:
 * some.pkg.Resource
 * some.pkg.Resource.properties
 * some/pkg/Resource
 * some/pkg/Resource.properties
 * /some/pkg/Resource
 * /some/pkg/Resource.properties
 */
public class PropertiesLoader extends ResourceLoader {

    private Properties properties;

    /**
     * Default Constructor.
     */
    public PropertiesLoader() {
        super();
        log.info("{}.{}", this.getClass().getSimpleName(), properties.toString());
    }

    /**
     * Constructor taking property filename as String.
     *
     * @param propertyFileName the property file name
     */
    public PropertiesLoader(final String propertyFileName) {
        super(propertyFileName);
        log.info("{}.{}", this.getClass().getSimpleName(), properties.toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.resources.ResourceLoader#load(java.io.InputStream)
     */
    @Override
    public ResourceLoader load(final InputStream streamForResource) {
        if (properties == null) {
            properties = new Properties();
        }
        try {
            properties.load(streamForResource);
            loaded = true;
        } catch (final IOException e) {
            log.error(e.toString());
        }
        return this;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

}
