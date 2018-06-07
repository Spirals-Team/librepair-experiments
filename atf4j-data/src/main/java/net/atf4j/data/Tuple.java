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

package net.atf4j.data;

/**
 * Two Tuple Class.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class Tuple<K, V> implements Keyed<K, V> {

    /** The key. */
    private K key;

    /** The value. */
    private V value;

    /**
     * Sets the key.
     *
     * @param key the key
     * @return the keyed
     */
    public Keyed<K, V> setKey(final K key) {
        this.key = key;
        return this;
    }

    /**
     * Sets the value.
     *
     * @param value the value
     * @return the keyed
     */
    public Keyed<K, V> setValue(final V value) {
        this.value = value;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.data.Keyed#key()
     */
    @Override
    public K key() {
        return this.key;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.data.Keyed#value()
     */
    @Override
    public V value() {
        return this.value;
    }

}
