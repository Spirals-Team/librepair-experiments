/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.atf4j.core.AbstractConfig.ConfigurationNotLoadedException;

/**
 * ConfigLoadingTests Class.
 */
public final class ConfigLoadingTests extends TestResultsReporting {

    /**
     * Missing Properties class.
     */
    private class MissingProperties extends AbstractConfig {

        /**
         * Instantiates a new missing properties.
         *
         * @throws ConfigurationNotLoadedException the configuration not loaded
         */
        public MissingProperties() throws ConfigurationNotLoadedException {
            super("Missing.properties");
        }
    }

    /**
     * A Mock Test Configuration from file.
     */
    private class ConfigFromFile extends AbstractConfig {
        /**
         * Instantiates a new Configuration from file.
         *
         * @throws ConfigurationNotLoadedException the configuration not loaded
         */
        public ConfigFromFile() throws ConfigurationNotLoadedException {
            super("ConfigFromFile.properties");
        }

        /**
         * Gets the property filename.
         *
         * @return the property filename
         */
        public String getPropertyFilename() {
            return get("filename");
        }
    }

    /**
     * Test method for MissingProperties.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded exception
     */
    @Test(expected = ConfigurationNotLoadedException.class)
    public void testMissingConfig() throws ConfigurationNotLoadedException {
        new MissingProperties();
    }

    /**
     * Test method for value from file.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded exception
     */
    @Test
    public void testConfigFromFile() throws ConfigurationNotLoadedException {
        final ConfigFromFile config = new ConfigFromFile();
        log.debug("config = {}", config.toString());
        verifyNotNull(config);
        final String propertyFilename = config.getPropertyFilename();
        assertEquals("ConfigFromFile.properties", propertyFilename);
    }

    /**
     * Test method for System overriding.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded exception
     */
    @Test
    public void testSystemOveridesConfig() throws ConfigurationNotLoadedException {
        final ConfigFromFile config = new ConfigFromFile();
        log.debug("config = {}", config.toString());
        verifyNotNull(config);
        final String key = "property";
        final String value = "FromSystem";
        System.setProperty(key, value);
        assertEquals(value, config.get(key));
    }

}
