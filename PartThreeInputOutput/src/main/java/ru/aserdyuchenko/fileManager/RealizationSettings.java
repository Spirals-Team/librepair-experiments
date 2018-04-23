package ru.aserdyuchenko.fileManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com).
 * Realization interface Settings.
 * This class work with app.properties.
 */
class RealizationSettings {
/**
 * @param prs.
 */
	private final Properties prs = new Properties();
/**
 * @param io - io.
 */
	public void load(InputStream io) {
		try {
			this.prs.load(io);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
/**
 * @param key - key.
 * @return this.prs.getProperty(key).
 */
	public String getValue(String key) {
		return this.prs.getProperty(key);
	}
}
