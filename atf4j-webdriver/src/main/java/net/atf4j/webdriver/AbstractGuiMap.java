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

package net.atf4j.webdriver;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * A base abstraction to encapsulate GUI Mapping logical elements. Uses Resource
 * Bundles with basename_languageCode_countryCode example: PageObjectGuiMap uses
 * PageObjectGuiMap_en_GB.properties
 */
public abstract class AbstractGuiMap {

    /** The resource bundle. */
    private final ResourceBundle resourceBundle;

    /**
     * Instantiates a new abstract gui map.
     */
    public AbstractGuiMap() {
        final String name = this.getClass().getSimpleName();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    /**
     * Instantiates a new logical gui map.
     *
     * @param aClass the a class
     */
    public AbstractGuiMap(final Class<?> aClass) {
        this.resourceBundle = ResourceBundle.getBundle(aClass.getName());
        Class<?> currentClass = aClass;
        do {
            ResourceBundle.getBundle(currentClass.getName());
            final Class<?> superClass = currentClass.getSuperclass();
            currentClass = superClass;
        } while (currentClass != null);
    }

    /**
     * Instantiates a new logical gui map.
     *
     * @param useBundleName the use bundle name
     */
    public AbstractGuiMap(final String useBundleName) {
        this.resourceBundle = ResourceBundle.getBundle(useBundleName);
    }

    /**
     * Contains key.
     *
     * @param key the key \* @return true, if successful, otherwise false.
     * @return true, if successful, otherwise false.
     */
    public boolean containsKey(final String key) {
        return this.resourceBundle.containsKey(key);
    }

    /**
     * Dump the Resource Bundle data to a PrintStream.
     *
     * @param out the destination Print Stream.
     */
    public void dumpTo(final PrintStream out) {
        out.println(this.getClass().getSimpleName());
        final Enumeration<String> keys = this.resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            out.println(key + "=" + this.resourceBundle.getString(key)); //$NON-NLS-1$
        }
        out.flush();
    }

    /**
     * Gets the base bundle name.
     *
     * @return the base bundle name
     */
    public String getBaseBundleName() {
        return this.resourceBundle.getBaseBundleName();
    }

    /**
     * Gets the keys.
     *
     * @return the keys
     */
    public Enumeration<String> getKeys() {
        return this.resourceBundle.getKeys();
    }

    /**
     * Gets the locale.
     *
     * @return the locale
     * @see java.util.ResourceBundle#getLocale()
     */
    public Locale getLocale() {
        return this.resourceBundle.getLocale();
    }

    /**
     * Gets the string.
     *
     * @param key the key
     * @return the string
     */
    public final String getString(final String key) {
        return this.resourceBundle.getString(key);
    }

    /**
     * Key set.
     *
     * @return the sets the
     * @see java.util.ResourceBundle#keySet()
     */
    public Set<String> keySet() {
        return this.resourceBundle.keySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return this.resourceBundle.toString();
    }

}
