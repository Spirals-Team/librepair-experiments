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
 * Name is an abstract datum.
 */
public abstract class Name extends AbstractDatum {

    /** The name. */
    private String name = "";

    /**
     * Instantiates a new name.
     */
    public Name() {
        super();
        setName("");
    }

    /**
     * Instantiates a new name.
     *
     * @param name the name
     */
    public Name(final String name) {
        super();
        setName(name);
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     * @return the name
     */
    public Name setName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Capitalise as Name.
     *
     * @param input as String.
     * @return string input as capitalised String.
     */
    public static String capitalise(final String input) {
        if (input != null) {
            if (input.length() > 0) {
                final String trim = input.trim();
                final String lowerCase = trim.toLowerCase();
                final StringBuffer buffer = new StringBuffer(lowerCase);
                buffer.setCharAt(0, Character.toUpperCase(input.charAt(0)));
                return buffer.toString();
            }
        }
        return "";
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.data.Datum#debugString()
     */
    @Override
    public String debugString() {
        return String.format("%s [name=%s]", this.getClass().getSimpleName(), this.name);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.data.Datum#toString()
     */
    @Override
    public String toString() {
        return capitalise(this.name);
    }

}
