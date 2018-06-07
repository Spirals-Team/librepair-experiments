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

/**
 * UnitTests for ConfigFromSystem.
 */
public final class ConfigFromSystemTests extends TestResultsReporting {

    /**
     * Mock Configuration from System settings.
     */
    private class ConfigFromSystem extends AbstractConfig {

        /**
         * Instantiates a new config from system.
         *
         * @throws ConfigurationNotLoadedException the configuration not loaded
         */
        public ConfigFromSystem() throws ConfigurationNotLoadedException {
            super();
        }

        /**
         * Gets the system property as boolean.
         *
         * @param key the key
         * @return the system property as boolean
         */
        public boolean getSystemPropertyAsBoolean(final String key) {
            return Boolean.getBoolean(key);
        }

        /**
         * Gets the system property as string.
         *
         * @param key the key
         * @return the system property as string
         */
        public String getSystemPropertyAsString(final String key) {
            return System.getProperty(key);
        }
    }

    /**
     * Test default constructor.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDefaultConstructor() throws Exception {
        final ConfigFromSystem configFromSystem = new ConfigFromSystem();
        log.debug("configFromSystem = {}", configFromSystem);
        verifyNotNull(configFromSystem);
    }

    /**
     * Test system string.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSystemString() throws Exception {
        final String systemPropertyKey = "stringFromSystem";
        final String systemPropertyValue = "present";
        System.setProperty(systemPropertyKey, systemPropertyValue);

        // When
        final ConfigFromSystem configFromSystem = new ConfigFromSystem();
        log.debug("configFromSystem = {}", configFromSystem);
        verifyNotNull(configFromSystem);
        final String systemPropertyAsString = configFromSystem.getSystemPropertyAsString(systemPropertyKey);
        assertEquals(systemPropertyValue, systemPropertyAsString);
    }

    /**
     * Test system boolean.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSystemBoolean() throws Exception {
        final String systemPropertyKey = "booleanFromSystem";
        final String systemPropertyValue = "true";
        System.setProperty(systemPropertyKey, systemPropertyValue);
        log.debug("System.getProperties() = {}", System.getProperties());

        // When
        final ConfigFromSystem configFromSystem = new ConfigFromSystem();
        log.debug("configFromSystem = {}", configFromSystem);
        verifyNotNull(configFromSystem);
        final boolean booleanFromSystem = configFromSystem.getSystemPropertyAsBoolean(systemPropertyKey);
        assertEquals(true, booleanFromSystem);
    }

}
