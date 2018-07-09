
package patterns.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * AbstractPayload Class.
 */
public abstract class AbstractPayload implements PayloadInterface {

    /** The properties. */
    private final Properties properties = new Properties();

    /**
     * property.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the abstract payload
     */
    public AbstractPayload setProperty(final String key, final String value) {
        properties.setProperty(key, value);
        return this;
    }

    /**
     * property.
     *
     * @param key
     *            the key
     * @return the property
     */
    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    /**
     * property.
     *
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the property
     */
    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Load.
     *
     * @param inputStream
     *            the in stream
     * @return the abstract payload
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public AbstractPayload load(final InputStream inputStream) throws IOException {
        properties.load(inputStream);
        return this;
    }

    /**
     * Load from XML.
     *
     * @param inputStream
     *            the in
     * @return the abstract payload
     * @throws IOException
     *             Signals that an I/O exception has occurred. invalid
     *             properties
     *             format exception
     * @throws InvalidPropertiesFormatException
     *             the invalid properties format exception
     */
    public AbstractPayload loadFromXML(final InputStream inputStream)
            throws IOException, InvalidPropertiesFormatException {
        properties.loadFromXML(inputStream);
        return this;

    }

    /**
     * Store.
     *
     * @param outputStream
     *            the out
     * @return the abstract payload
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public AbstractPayload store(final OutputStream outputStream) throws IOException {
        return this.store(outputStream, "");
    }

    /**
     * Store.
     *
     * @param outputStream
     *            the out
     * @param comments
     *            the comments
     * @return the abstract payload
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public AbstractPayload store(final OutputStream outputStream, final String comments) throws IOException {
        properties.store(outputStream, comments);
        return this;
    }

}
