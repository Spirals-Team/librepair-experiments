
package coaching.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Abstract Configuration Class.
 */
public abstract class AbstractConfig implements ConfigInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The properties. */
    protected final Properties properties = new Properties();

    /**
     * Instantiates a new abstract configuration.
     */
    public AbstractConfig() {
        final String propertyFilename = this.getClass().getSimpleName();
        loadFromFilename(propertyFilename);
    }

    /**
     * Instantiates a new abstract configuration.
     *
     * @param configFilename
     *            the Configuration filename
     */
    public AbstractConfig(final String configFilename) {
        loadFromFilename(configFilename);
    }

    /**
     * configuration from XML filename.
     *
     * @param configFilename
     *            the Configuration filename
     */
    protected void loadFromFilename(final String configFilename) {
        final String propertyFilename = toPropertyFilename(configFilename);
        loadFromResourceStream(inputStream(propertyFilename));
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
        return String.format("%s.properties", configFilename);
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
    protected void loadFromResourceStream(final InputStream resourceAsStream) {
        if (resourceAsStream != null) {
            try {
                this.properties.load(resourceAsStream);
            } catch (final IOException e) {
                this.log.error(e.toString());
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see framework.config.ConfigInterface#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(final String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = this.properties.getProperty(key);
        }
        this.log.trace("{}={}", key, value);
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see framework.config.ConfigInterface#getProperty(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String getProperty(final String key, final String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            value = this.properties.getProperty(key, defaultValue);
        }
        this.log.trace("{}={}", key, value);
        return value;
    }

    /**
     * Value for key.
     *
     * @param key
     *            the key
     * @return the string
     */
    public String valueFor(final String key) {
        final String property = System.getProperty(key);
        if (property == null) {
            return this.properties.getProperty(key);
        }
        return property;
    }

    /**
     * Value for key.
     *
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the string
     */
    public String valueFor(final String key, final String defaultValue) {
        final String property = System.getProperty(key);
        if (property == null) {
            return this.properties.getProperty(key, defaultValue);
        }
        return property;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final String prettyProperties = prettyProperties(this.properties);
        return String.format("properties = %s", prettyProperties);
    }

    /**
     * Pretty properties.
     *
     * @param properties
     *            the properties
     * @return the string
     */
    private String prettyProperties(final Properties properties) {
        return properties.toString().replace("{", "{\n\t").replace(", ", "\n\t").replace("}", "\n\t}");
    }

}
