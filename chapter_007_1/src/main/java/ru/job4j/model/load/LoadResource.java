package ru.job4j.model.load;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Yury Matskevich
 */
public class LoadResource {
	private static final Logger LOG = Logger.getLogger(LoadResource.class);
	private Properties prop;

	public LoadResource(String name) {
		loadParams(name);
	}

	private void loadParams(String name) {
		prop = new Properties();
		try (InputStream is = getClass().getResourceAsStream(name)) {
			prop.load(is);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Looking for the property with the specified key.
	 *
	 * @param key the property key.
	 * @return value in these properties with the specified key value
	 * or null if the property is not found.
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
