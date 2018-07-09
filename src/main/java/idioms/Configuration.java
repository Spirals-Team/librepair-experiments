
package idioms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * propertyFilename.
 */
public class Configuration {
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private final Properties properties = new Properties();

	/**
	 * Instantiates a new configuration.
	 */
	public Configuration() {
		super();
		try {
			final String resourceName = resourceName();
			final InputStream inputStream = inputStream(resourceName);
			if (inputStream != null) {
				this.properties.load(inputStream);
			}
		} catch (final IOException e) {
			this.log.error("{}", e);
		}
	}

	/**
	 * Resource name.
	 *
	 * string
	 *
	 * @return the string
	 */
	private String resourceName() {
		final String simpleName = this.getClass().getSimpleName();
		final String filename = String.format("%s.properties", simpleName);
		return filename;
	}

	/**
	 * Input stream for resourceName.
	 *
	 * resource name
	 * input stream
	 *
	 * @param resourceName the resource name
	 * @return the input stream
	 */
	private InputStream inputStream(final String resourceName) {
		final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		final InputStream resourceAsStream = classloader.getResourceAsStream(resourceName);
		return resourceAsStream;
	}

	/**
	 * Value for.
	 *
	 * key
	 * string
	 *
	 * @param key the key
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
	 * Value for.
	 *
	 * key
	 * default value
	 * string
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the string
	 */
	public String valueFor(final String key, final String defaultValue) {
		final String property = System.getProperty(key);
		if (property == null) {
			return this.properties.getProperty(key, defaultValue);
		}
		return property;
	}

	/**
	 * To pretty string.
	 *
	 * string
	 *
	 * @return the string
	 */
	public String toPrettyString() {
		return String.format("Configuration [properties=%s]", format(this.properties.toString()));
	}

	/**
	 * Format.
	 *
	 * string
	 * object
	 *
	 * @param string the string
	 * @return the object
	 */
	private Object format(final String string) {
		return string.replace("{", "\n\t{\n\t").replace(", ", "\n\t").replace("}", "\n\t}\n");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Configuration [properties=%s]", this.properties);
	}
}
