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

package net.atf4j.pog;

/**
 * AccessModifiers Enum.
 * <p>
 * Default, public, protected, and private
 */
public enum AccessModifier {

    /** Default. */
    DEFAULT(""),

    /** public. */
    PUBLIC("public"),

    /** protected. */
    PROTECTED("protected"),

    /** private. */
    PRIVATE("private");

    /** the value of the access modifier. */
    private final String value;

    /**
     * Instantiates a new field type.
     *
     * @param asText the as text
     */
    private AccessModifier(final String asText) {
        this.value = asText;
    }

    /**
     * Initialise.
     *
     * @return the field type
     */
    public static AccessModifier initialise() {
        return AccessModifier.DEFAULT;
    }

    /**
     * FieldType From string value.
     *
     * @param asText the as text
     * @return the field type
     */
    public static AccessModifier fromString(final String asText) {
        for (final AccessModifier candidate : values()) {
            if (candidate.value.equals(asText)) {
                return candidate;
            }
        }
        return AccessModifier.DEFAULT;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.value;
    }
}
