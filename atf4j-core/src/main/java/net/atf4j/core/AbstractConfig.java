/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with atf4j. If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Abstract Configuration class.
 */
public abstract class AbstractConfig extends TestResultsReporting implements ConfigurationInterface {

    /** The properties. */
    protected final Properties properties = new Properties();

    /**
     * Default Constructor for Configuration.
     */
    public AbstractConfig() {
        super();
        load();
    }

    /**
     * Instantiates a new configuration.
     *
     * @param propertyFilename as String
     * @throws ConfigurationNotLoadedException the missing exception
     */
    public AbstractConfig(final String propertyFilename) throws ConfigurationNotLoadedException {
        super();
        load(propertyFilename);
    }

    /**
     * Load configuration from property file named after class.
     *
     * @return the abstract configuration
     */
    protected ConfigurationInterface load() {
        try {
            load(propertiesFilename());
        } catch (final ConfigurationNotLoadedException e) {
            log.warn("{}; using default values.", e.getMessage());
        }
        return this;
    }

    /**
     * Properties filename.
     *
     * @return the string
     */
    protected String propertiesFilename() {
        final String simpleName = this.getClass().getSimpleName();
        return String.format("%s.properties", simpleName);
    }

    /**
     * Load properties file.
     *
     * @param propertyFilename the property filename
     * @return the abstract configuration
     * @throws ConfigurationNotLoadedException the missing exception
     */
    protected ConfigurationInterface load(final String propertyFilename) throws ConfigurationNotLoadedException {
        try {
            final InputStream resourceAsStream = resourceAsStream(propertyFilename);
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
                properties.setProperty("propertiesFilename", propertyFilename);
            } else {
                throw new ConfigurationNotLoadedException(propertyFilename);
            }
        } catch (final IOException e) {
            throw new ConfigurationNotLoadedException(e.getLocalizedMessage());
        }
        return this;
    }

    /**
     * Resource as stream.
     *
     * @param resourceFilename the resource filename
     * @return the input stream
     */
    protected InputStream resourceAsStream(final String resourceFilename) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        return classLoader.getResourceAsStream(resourceFilename);
    }

    /**
     * Gets the value from the properties.
     *
     * @param key the key
     * @return the string
     */
    protected String get(final String key) {
        return get(key, null);
    }

    /**
     * Get a property from System Property if available, otherwise from Property
     * File if available, otherwise default.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the string
     */
    protected String get(final String key, final String defaultValue) {
        String propertyValue = System.getProperty(key);
        if (propertyValue == null) {
            propertyValue = properties.getProperty(key, defaultValue);
            log.trace("Using property file for key {} = {}", key, propertyValue);
        } else {
            log.info("Using system property override for key {} = {}", key, propertyValue);
        }

        return propertyValue;
    }

    /**
     * Gets the value for the key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the int
     */
    protected int get(final String key, final int defaultValue) {
        return Integer.parseInt(this.get(key, Integer.toString(defaultValue)));
    }

    /**
     * Gets the value for the key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the long
     */
    protected long get(final String key, final long defaultValue) {
        return Long.parseLong(this.get(key, Long.toString(defaultValue)));
    }

    /**
     * Gets the value for the key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the boolean
     */
    protected boolean get(final String key, final boolean defaultValue) {
        return Boolean.parseBoolean(this.get(key, Boolean.toString(defaultValue)));
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.ConfigurationInterface#valueFor(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String valueFor(final String key, final String defaultValue) {
        return get(key, defaultValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.ConfigurationInterface#valueFor(java.lang.String,
     * int)
     */
    @Override
    public int valueFor(final String key, final int defaultValue) {
        return get(key, defaultValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.ConfigurationInterface#valueFor(java.lang.String,
     * long)
     */
    @Override
    public long valueFor(final String key, final long defaultValue) {
        return get(key, defaultValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.ConfigurationInterface#valueFor(java.lang.String,
     * boolean)
     */
    @Override
    public boolean valueFor(final String key, final boolean defaultValue) {
        return get(key, defaultValue);
    }

    /**
     * Pretty string.
     *
     * @return the string
     */
    public String prettyString() {
        final String className = this.getClass().getSimpleName();
        return String.format("%s [properties=%s]", className, prettyProperties(properties));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final String className = this.getClass().getSimpleName();
        return String.format("%s [properties=%s]", className, prettyProperties(properties));
    }

    /**
     * Pretty Format the properties.
     *
     * @param properties the properties as Properties object.
     * @return the properties as a formated String object.
     */
    protected String prettyProperties(final Properties properties) {
        if (properties == null) {
            return "null";
        } else if (properties.isEmpty()) {
            return "{empty}";
        } else {
            return prettyProperties(properties.toString());
        }
    }

    /**
     * Pretty Format the properties.
     *
     * @param properties the properties as String.
     * @return the properties as a formated String.
     */
    protected String prettyProperties(final String properties) {
        return properties
            .replace("[", "[\n\t")
            .replace("{", "{\n\t")
            .replace(", ", "\n\t")
            .replace("}", "\n\t}")
            .replace("]", "\n\t]}");
    }

    /**
     * No property was found for the key.
     */
    @SuppressWarnings("serial")
    public class PropertyNotFoundException extends VerificationError {
        /**
         * Instantiates a new property not found.
         *
         * @param propertyKey the property key
         */
        public PropertyNotFoundException(final String propertyKey) {
            super(String.format("Property value not found for key %s.", propertyKey));
        }
    }

    /**
     * Configuration Not Loaded.
     */
    @SuppressWarnings("serial")
    public class ConfigurationNotLoadedException extends VerificationError {
        /**
         * Instantiates a missing configuration exception.
         *
         * @param propertyFilename the property filename
         */
        public ConfigurationNotLoadedException(final String propertyFilename) {
            super(String.format("Property file '%s' not found.", propertyFilename));
        }
    }
}
