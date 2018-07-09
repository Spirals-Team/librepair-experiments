package coaching.net;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler {
	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
	private String[] args = null;
	private java.util.Properties properties = null;

	public Scheduler() {
		super();
		loadConfiguration();
	}

	private void loadConfiguration() {
		final String filename = this.getClass().getSimpleName() + ".properties";
		try {
			final InputStream resourceAsStream = getClass().getResourceAsStream(filename);
			this.properties.load(resourceAsStream);
		} catch (final Exception e) {
			Scheduler.log.error(e.toString());
		}
	}

	public Scheduler(final String[] args) {
		this.args = args;
		loadConfiguration(args);
	}

	private void loadConfiguration(final String[] args) {
		final Properties properties = new Properties();
		try {
			for (final String key : this.args) {
				properties.setProperty(key, key);
			}
		} catch (final IndexOutOfBoundsException indexOutOfBoundsException) {
			Scheduler.log.info("Error: reading args[]");
		}
		loadConfiguration(properties);
	}

	public Scheduler(final Properties properties) {
		loadConfiguration(properties);
	}

	private void loadConfiguration(final Properties properties) {
		this.properties = properties;
	}

	public Scheduler setProperties(final Properties properties) {
		this.properties = properties;
		return this;
	}

	public Properties execute() {
		return execute(this.properties);
	}

	public Properties execute(final Properties properties) {
		final Enumeration<?> keys = properties.propertyNames();
		while (keys.hasMoreElements()) {
			final String key = (String) keys.nextElement();
			final String value = properties.getProperty(key);
			try {
				final Thread thread = (Thread) Class.forName(value).newInstance();
				thread.start();
			} catch (final Exception exception) {
				Scheduler.log.error(exception.toString());
			}
		}
		return properties;
	}

	/**
	 * main method.
	 *
	 * arguments
	 */
	public static void main(final String[] args) {
		Scheduler.log.trace(System.getProperties().toString());
		Scheduler.log.debug("args[]={}", Arrays.toString(args));

		final Scheduler instance = new Scheduler(args);
		final Properties response = instance.execute();
		Scheduler.log.info(response.toString());
	}
}
