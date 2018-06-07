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
 * ConfigurationInterface Interface.
 */
public interface ConfigurationInterface {

    /**
     * Get the value of a long from configuration property by key.
     *
     * @param key the key of the value as String.
     * @param defaultValue the default value as int.
     * @return int value.
     */
    public int valueFor(final String key, final int defaultValue);

    /**
     * Get the value of a long from configuration property by key.
     *
     * @param key the key of the value as String.
     * @param defaultValue the default value as long.
     * @return long value.
     */
    public long valueFor(final String key, final long defaultValue);

    /**
     * Get the value of a long from configuration property by key.
     *
     * @param key the key of the value as String.
     * @param defaultValue the default value as boolean. \* @return true, if
     *            successful, otherwise false. otherwise false.
     * @return true, if successful, otherwise false.
     */
    public boolean valueFor(final String key, final boolean defaultValue);

    /**
     * Get the value of a long from configuration property by key.
     *
     * @param key the key of the value as String.
     * @param defaultValue the default value as a String.
     * @return the configuration property as a String.
     */
    public String valueFor(final String key, final String defaultValue);

}
