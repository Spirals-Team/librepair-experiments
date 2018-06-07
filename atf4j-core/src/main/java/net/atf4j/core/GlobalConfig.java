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

/**
 * Globally accessible Configuration class, using static final instance.
 */
public final class GlobalConfig extends AbstractConfig {

    /** The Constant 			GLOBAL_CONFIG. */
    private static final GlobalConfig GLOBAL_CONFIG = new GlobalConfig();

    /**
     * Instantiates a new configuration. Private default constructor prevents
     * wild instantiation.
     */
    private GlobalConfig() {
        super();
    }

    /**
     * Gets the single INSTANCE of configuration.
     *
     * @return single INSTANCE of configuration.
     */
    public static GlobalConfig getInstance() {
        return GLOBAL_CONFIG;
    }

    /**
     * A string value for key.
     *
     * @param key the key.
     * @return the string value.
     */
    public static String valueFor(final String key) {
        return getInstance().get(key, "default");
    }

    /**
     * An int value for the key.
     *
     * @param key the key.
     * @return the value as an int if found, otherwise 0.
     */
    public static int intValueFor(final String key) {
        return getInstance().get(key, 0);
    }

    /**
     * A long value for the key.
     *
     * @param key the key.
     * @return the value as a long if found, otherwise 0L.
     */
    public static long longValueFor(final String key) {
        return getInstance().get(key, 0L);
    }

    /**
     * A boolean value for the key (default to false).
     *
     * @param key the key
     * @return the boolean value if found, otherwise false.
     */
    public static boolean booleanValueFor(final String key) {
        return getInstance().get(key, false);
    }

    /**
     * A boolean value for the key, with default.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the boolean value if found, otherwise the default.
     */
    public static boolean booleanValueFor(final String key, boolean defaultValue) {
        return getInstance().get(key, defaultValue);
    }

}
