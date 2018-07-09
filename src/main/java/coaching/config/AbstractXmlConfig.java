
package coaching.config;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * An abstract Configuration class.
 * Loads the Configuration Properties from an XML file.
 */
public abstract class AbstractXmlConfig extends AbstractConfiguration {

    /** The filename. */
    protected String filename;

    /**
     * The Constructor.
     */
    public AbstractXmlConfig() {
        super();
        loadFrom(defaultFilename());
    }

    /**
     * The Constructor.
     *
     * @param configFilename the config filename
     */
    public AbstractXmlConfig(final String configFilename) {
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
        if (configFilename.endsWith(".xml")) {
            return configFilename;
        } else {
            return String.format("%s.xml", configFilename);
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
                this.properties.loadFromXML(resourceAsStream);
                this.loaded = true;
            } catch (final IOException e) {
                this.log.error(e.toString());
            }
        }
    }
}
