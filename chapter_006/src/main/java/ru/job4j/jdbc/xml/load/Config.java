package ru.job4j.jdbc.xml.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The {@code Config} class load properties from
 * external file to the {@code Properties} class of java
 * and offer method for get of defined property by key
 *
 * @author Yury Matskevich
 */
public class Config {
	private static final Logger LOG = LoggerFactory
			.getLogger(Config.class);
	private Properties prop;

	public Config(String name) {
		loadParams(name);
	}

	/**
	 * Loads properties from an external file to the java
	 * class of {@link Properties}
	 * @param name a name of a configuration file from folder
	 * of resources
	 */
	private void loadParams(String name) {
		prop = new Properties();
		try (InputStream is = getClass()
				.getResourceAsStream(name)) {
			prop.load(is);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Looking for the property with the specified key.
	 * @param key the property key.
	 * @return value in these properties with the specified key value
	 * or null if the property is not found.
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
