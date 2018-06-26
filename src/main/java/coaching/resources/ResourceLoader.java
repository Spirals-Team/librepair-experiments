
package coaching.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Resource Loader class.
 *
 * Load the resource file from the resources.
 * <code>
 * Class.getResourceAsStream ("resource.properties");
 * Class.getResourceAsStream ("/some/pkg/resource.properties");
 * ClassLoader.getResourceAsStream ("some/pkg/resource.properties");
 * ResourceBundle.getBundle ("some.pkg.resource");
 * </code>
 */
public class ResourceLoader {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** file suffix for the resource, default to .properties */
    private String suffix = ".properties";

    /** filename of the Resource, default to "./thisClassName.properties" */
    private String filename = String.format("./%s%s", this.getClass().getSimpleName(), this.suffix);

    /** resource has been loaded. */
    protected boolean loaded = false;

    /**
     * Construct a new configuration instance.
     *
     */
    public ResourceLoader() {
        assertNotNull(this.filename);
        load(this.filename);
    }

    /**
     * Construct a new configuration instance.
     *
     * @param propertyFileName
     *            the property file name
     */
    public ResourceLoader(final String propertyFileName) {
        setFilename(propertyFileName);
        load(this.filename);
    }

    /**
     * Sets the property file name.
     *
     * @param propertyFileName
     *            the property file name
     * @return this ResourceLoader for fluent interface.
     */
    public ResourceLoader setFilename(final String propertyFileName) {
        if (propertyFileName != null) {
            if (propertyFileName.endsWith(this.suffix)) {
                this.filename = propertyFileName;
            } else {
                this.filename = String.format("./%s%s", propertyFileName, this.suffix);
            }
        } else {
            throw new ConfigurationNotLoadedException(propertyFileName);
        }
        return this;
    }

    /**
     * Sets the suffix.
     *
     * @param suffix the suffix
     */
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    /**
     * Load the resource by className.
     *
     * @return this ResourceLoader for fluent interface.
     */
    public ResourceLoader load() {
        return load(this.filename);
    }

    /**
     * Load the resource by property filename.
     *
     * @param propertyFileName the property file name
     * @return the resource loader
     */
    public ResourceLoader load(final String propertyFileName) {
        if (propertyFileName != null) {
            final InputStream streamForResource = streamForResource(propertyFileName);
            if (streamForResource != null) {
                return load(streamForResource);
            }
        }
        throw new ConfigurationNotLoadedException(propertyFileName);
    }

    /**
     * Stream for resource.
     *
     * @param propertyFileName
     *            the property file name
     * @return the input stream
     *         <code>
     *  final ClassLoader classLoader = this.getClass().getClassLoader();
     * </code>
     */
    protected InputStream streamForResource(final String propertyFileName) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(propertyFileName);
    }

    /**
     * Load the configuration form the property file name.
     *
     * @param streamForResource
     *            the property InputStream
     * @return this for a fluent interface.
     */
    public ResourceLoader load(final InputStream streamForResource) {

        final InputStreamReader inputStreamReader = new InputStreamReader(streamForResource);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
            streamForResource.close();
            this.loaded = true;
        } catch (final IOException e) {
            this.log.error(e.toString());
        }
        return this;
    }

    /**
     * Checks if is loaded.
     *
     * @return true, if is loaded
     */
    public boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Gets the property file name.
     *
     * @return the property file name
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Gets the suffix.
     *
     * @return the suffix
     */
    public String getSuffix() {
        return this.suffix;
    }

    public static File getFile(final String resourceFilename) {
        return null;
    }

    public static File getFile(final URL resourceUrl) {
        return null;
    }

    public static File getFile(final URI resourceUri) {
        return null;
    }

    /**
     * No property was found for the key.
     */
    @SuppressWarnings("serial")
    public class PropertyNotFoundException extends AssertionError {
        /**
         * Instantiates a new property not found.
         *
         * @param propertyKey the property key
         */
        public PropertyNotFoundException(final String propertyKey) {
            super(String.format("Property value not found for key %s.", propertyKey));
        }
    }

    /**
     * Configuration Not Loaded.
     */
    @SuppressWarnings("serial")
    public class ConfigurationNotLoadedException extends AssertionError {
        /**
         * Instantiates a missing configuration exception.
         *
         * @param propertyFilename the property filename
         */
        public ConfigurationNotLoadedException(final String propertyFilename) {
            super(String.format("Property file '%s' not found.", propertyFilename));
        }
    }

}
