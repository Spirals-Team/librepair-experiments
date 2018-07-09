
package coaching.config;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * An abstract Configuration class.
 * Loads Configuration Properties from an .properties file.
 */
public class Configuration extends AbstractConfiguration {

    /** The filename. */
    protected String filename;

    /**
     * Instantiates a new abstract configuration.
     */
    public Configuration() {
        super();
        loadFrom(defaultFilename());
    }

    /**
     * Instantiates a new abstract configuration.
     *
     * @param configFilename the Configuration filename
     */
    public Configuration(final String configFilename) {
        super();
        this.filename = configFilename;
        loadFrom(configFilename);
    }

    /**
     * Default filename.
     *
     * @return the string
     */
    protected String defaultFilename() {
        this.filename = this.getClass().getSimpleName();
        return this.filename;
    }

    /**
     * configuration from filename.
     *
     * @param configFilename
     *            the Configuration filename
     */
    protected void loadFrom(final String configFilename) {
        final String propertyFilename = toPropertyFilename(configFilename);
        loadFrom(inputStream(propertyFilename));
        this.properties.setProperty("propertyFilename", propertyFilename);
    }

    /**
     * To property filename.
     *
     * @param configFilename
     *            the Configuration filename
     * @return the string
     */
    protected String toPropertyFilename(final String configFilename) {
        assertNotNull(configFilename);
        if (configFilename.endsWith(".properties")) {
            return configFilename;
        } else {
            return String.format("%s.properties", configFilename);
        }
    }

    /**
     * Input stream from a resource filename.
     *
     * @param resourceName
     *            the resource name
     * @return the input stream
     */
    protected InputStream inputStream(final String resourceName) {
        final Thread currentThread = Thread.currentThread();
        final ClassLoader classloader = currentThread.getContextClassLoader();
        return classloader.getResourceAsStream(resourceName);
    }

    /**
     * Load from property file.
     *
     * @param resourceAsStream
     *            the resource as stream
     */
    protected void loadFrom(final InputStream resourceAsStream) {
        if (resourceAsStream != null) {
            try {
                this.properties.load(resourceAsStream);
                this.loaded = true;
            } catch (final IOException e) {
                this.log.error(e.toString());
            }
        }
    }

    /**
     * Value for key, allows an environment value to override the property.
     *
     * @param key the key
     * @return the value as a String object.
     */
    public String valueFor(final String key) {
        final String value = System.getProperty(key);
        if (value == null) {
            return this.properties.getProperty(key);
        } else {
            this.log.warn("Using system property value {} for key {}", value, key);
        }
        return value;
    }

    /**
     * Value for key, allows an environment value to override the property.
     *
     * @param key the key
     * @param defaultValue the default value, if no property found.
     * @return the property value as a String object.
     */
    public String valueFor(final String key, final String defaultValue) {
        final String value = System.getProperty(key);
        if (value == null) {
            return this.properties.getProperty(key, defaultValue);
        } else {
            this.log.warn("Using system property value {} for key {}", value, key);
        }
        return value;
    }
}
