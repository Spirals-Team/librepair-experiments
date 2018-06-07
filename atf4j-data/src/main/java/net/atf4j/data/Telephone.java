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

import static org.junit.Assert.assertNotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.atf4j.core.TestResultsReporting;

/**
 * Telephone Number.
 */
public class Telephone extends TestResultsReporting {

    /** Regular Expression pattern used for verification. */
    private static final Pattern PATTERN = Pattern.compile("^(\\d){2}-(\\d){2}-(\\d){2}$");

    /** telephone number. */
    private String number = "";

    /** The type of phone. */
    private Type type = Type.Phone;
    // Country Code
    // Area Code
    // Number

    /**
     * The Type Enum.
     */
    public enum Type {

        /** Phone. */
        Phone("Phone"),

        /** Landline. */
        Landline("Landline"),

        /** Mobile. */
        Mobile("Mobile"),

        /** Fax. */
        Fax("Fax"),

        /** Telex. */
        Telex("Telex"),

        /** Other. */
        Other("Other"),

        /** Blank. */
        BLANK(""),

        /** Unknown. */
        Unknown("Unknown");

        /** The type name. */
        private final String typeName;

        /**
         * Instantiates a new type.
         *
         * @param name the name
         */
        private Type(final String name) {
            this.typeName = name;
        }

        /**
         * Initialise.
         *
         * @return the type
         */
        public static Type initialise() {
            return Type.Phone;
        }

        /**
         * For string.
         *
         * @param asText the as text
         * @return the type
         */
        public static Type forString(final String asText) {
            for (final Type candidate : values()) {
                if (candidate.typeName.equals(asText)) {
                    return candidate;
                }
            }
            return null;
        }
    }

    /**
     * Create new INSTANCE of create.
     *
     * @return the telephone
     */
    public static Telephone create() {
        return new Telephone();
    }

    /**
     * Create new INSTANCE of create.
     *
     * @param type the type
     * @return the telephone
     */
    public static Telephone create(final Type type) {
        return new Telephone().setType(type);
    }

    /**
     * Instantiates a new telephone.
     *
     * @param type the type
     * @param number the number
     */
    public Telephone(final Type type, final String number) {
        super();
        this.type = type;
        this.number = number;
    }

    /**
     * Verify.
     *
     * @return true, if successful, otherwise false.
     */
    public static boolean verify() {
        return false;
    }

    /**
     * Instantiates a new telephone number.
     */
    public Telephone() {
        super();
        setNumber("");
        setType(Type.Unknown);
    }

    /**
     * Instantiates a new telephone number.
     *
     * @param number the number
     */
    public Telephone(final String number) {
        super();
        setNumber(number);
        setType(Type.Phone);
    }

    /**
     * Instantiates a new telephone.
     *
     * @param type the type
     * @param number the number
     */
    public Telephone(final String type, final String number) {
        super();
        setType(type);
        setNumber(number);
    }

    /**
     * Sets the number.
     *
     * @param number the number to set
     * @return the telephone number
     */
    public Telephone setNumber(final String number) {
        this.number = number;
        return this;
    }

    /**
     * Sets the type.
     *
     * @param type the type
     * @return the telephone
     */
    public Telephone setType(final String type) {
        this.type = Type.forString(type);
        return this;
    }

    /**
     * Sets the type.
     *
     * @param type the type
     * @return the telephone
     */
    public Telephone setType(final Telephone.Type type) {
        this.type = type;
        return this;
    }

    /**
     * Gets the number.
     *
     * @return the number
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        if (this.type != null) {
            return this.type.toString();
        } else {
            return "Telephone";
        }
    }

    /**
     * Verify.
     *
     * @param number the number \* @return true, if successful, otherwise false.
     * @return true, if successful, otherwise false.
     */
    public static boolean verify(final String number) {
        assertNotNull("unexpected null", number);
        final Matcher matcher = PATTERN.matcher(number);
        return matcher.find();
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        return String.format("Telephone [number=%s, type=%s]", this.number, this.type);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s : %s", getType(), getNumber());
    }
}
