
package automation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Abstract Data Class.
 */
public abstract class AbstractData implements DataInterface {
	private final Properties properties = new Properties();

	/**
	 * Sets the property value for a key.
	 *
	 * @param key the key
	 * @param value the value
	 * @return this for a fluent interface.
	 */
	public AbstractData setProperty(final String key, final String value) {
		this.properties.setProperty(key, value);
		return this;
	}

	/**
	 * Number of values in collection.
	 *
	 * @return size as int.
	 */
	public int size() {
		return this.properties.size();
	}

	/**
	 * Gets the property value for the key.
	 *
	 * @param key the key
	 * @return the property
	 */
	public String getProperty(final String key) {
		return this.properties.getProperty(key);
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the property
	 */
	public String getProperty(final String key, final String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

	/**
	 * Load the property values from input stream.
	 *
	 * @param inputStream the in stream
	 * @return the abstract data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public AbstractData load(final InputStream inputStream) throws IOException {
		if (inputStream != null) {
			this.properties.load(inputStream);
		}
		return this;
	}

	/**
	 * Load properties from XML.
	 *
	 * @param input stream.
	 * @return this instance for a fluent interface.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InvalidPropertiesFormatException the invalid properties format exception
	 */
	public AbstractData loadFromXML(final InputStream inputStream) throws Exception {
		if (inputStream != null) {
			this.properties.loadFromXML(inputStream);
		}
		return this;
	}

	/**
	 * Store.
	 *
	 * @param outStream the out
	 * @return this instance for a fluent interface.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public AbstractData store(final OutputStream outStream) throws IOException {
		this.store(outStream, this.getClass().getSimpleName());
		return this;
	}

	/**
	 * Store.
	 *
	 * @param out the out
	 * @param comments the comments
	 * @return this instance for a fluent interface.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public AbstractData store(final OutputStream out, final String comments) throws IOException {
		this.properties.store(out, comments);
		return this;
	}

}
