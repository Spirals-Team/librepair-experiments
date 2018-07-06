package ru.job4j.last.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Yury Matskevich
 */
public class Config {
	private static final Logger LOG = Logger.getLogger(Config.class);
	private Properties prop;

	/**
	 * Creates object which contains settings for working a program
	 * and allow to get them
	 * @param name a name of file with settings for app.
	 * It's located in a resource folder
	 */
	public Config(String name) {
		loadParams(String.format("/%s", name));
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

	/**
	 * Loads parameters from file which is located in a resource
	 * folder to object of {@link Properties}
	 * @param name a name of .properties file which contains
	 * some parameters for working of app
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
}
