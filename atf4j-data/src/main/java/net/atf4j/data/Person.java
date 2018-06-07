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

import java.util.Calendar;

import net.atf4j.core.TestResultsReporting;

/**
 * Person.
 */
public class Person extends TestResultsReporting {

    /** The title of the person. */
    private Title title = Title.BLANK;

    /** The forename of the person. */
    private String forename = "";

    /** The middlename of the person. */
    private String middlename = "";

    /** The surname of the person. */
    private String surname = "";

    /** The date of birth for person. */
    private Calendar dob;

    /** The postal address. */
    private PostalAddress postalAddress;

    /** The email address. */
    private EmailAddress emailAddress;

    /** The gender. */
    private Gender gender;

    /**
     * Gender Enum.
     */
    public enum Gender {

        /** male. */
        MALE("Male"),

        /** female. */
        FEMALE("Male"),

        /** unknown. */
        UNKNOWN("Unknown"),

        /** Blank. */
        BLANK("");

        /** The gender string. */
        private final String value;

        /**
         * Instantiates a new gender.
         *
         * @param asText the as text
         */
        private Gender(final String asText) {
            this.value = asText;
        }

        /**
         * From string.
         *
         * @param string the string
         * @return the gender
         */
        public static Gender fromString(final String string) {
            for (final Gender candidate : values()) {
                if (candidate.value.equals(string)) {
                    return candidate;
                }
            }
            return Gender.UNKNOWN;
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

    };

    /**
     * The Title Enum.
     */
    public enum Title {

        /** Mr. */
        MR("Mr"),

        /** Mrs. */
        MRS("Mrs"),

        /** Ms. */
        MS("Ms"),

        /** Miss. */
        MISS("Miss"),

        /** Sir. */
        SIR("Sir"),

        /** Lady. */
        LADY("Lady"),

        /** Lord. */
        LORD("Lord"),

        /** DR. */
        DR("Dr"),

        /** Blank. */
        BLANK("");

        /** The value. */
        private final String value;

        /**
         * Instantiates a new title.
         *
         * @param asText the as text
         */
        private Title(final String asText) {
            this.value = asText;
        }

        /**
         * From string.
         *
         * @param string the string
         * @return the title
         */
        public static Title fromString(final String string) {
            for (final Title candidate : values()) {
                if (candidate.value.equals(string)) {
                    return candidate;
                }
            }
            return Title.BLANK;
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

    /**
     * Instantiates a new person.
     */
    public Person() {
        super();
    }

    /**
     * Person.
     *
     * @param forename the forename
     * @param middlename the middlename
     * @param surname the surname
     */
    public Person(final String forename, final String middlename, final String surname) {
        super();
        forename(forename);
        middlename(middlename);
        surname(surname);
    }

    /**
     * Title.
     *
     * @param title the title
     * @return the person
     */
    public Person title(final String title) {
        this.title = Title.fromString(title);
        return this;
    }

    /**
     * Title.
     *
     * @param title the title
     * @return the person
     */
    public Person title(final Title title) {
        this.title = title;
        return this;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public Title title() {
        return this.title;
    }

    /**
     * Forename.
     *
     * @param forename the forename
     * @return the person
     */
    public Person forename(final String forename) {
        this.forename = forename;
        return this;
    }

    /**
     * Forename.
     *
     * @return the string
     */
    public String forename() {
        return this.forename;
    }

    /**
     * Middlename.
     *
     * @param middlename the middlename
     * @return the person
     */
    public Person middlename(final String middlename) {
        this.middlename = middlename;
        return this;
    }

    /**
     * Middlename.
     *
     * @return the string
     */
    public String middlename() {
        return this.middlename;
    }

    /**
     * Surname.
     *
     * @param surname the surname
     * @return the person
     */
    public Person surname(final String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * Surname.
     *
     * @return the string
     */
    public String surname() {
        return this.surname;
    }

    /**
     * Date of birth.
     *
     * @param dob the dob
     * @return the person
     */
    public Person dateOfBirth(final Calendar dob) {
        this.dob = dob;
        return this;
    }

    /**
     * Date of birth.
     *
     * @return the calendar
     */
    public Calendar dateOfBirth() {
        return this.dob;
    }

    /**
     * Age.
     *
     * @return the int
     */
    public int age() {
        final Calendar now = Calendar.getInstance();
        final int currentmonth = now.get(Calendar.MONTH);
        final int currentyear = now.get(Calendar.YEAR);

        final int month = this.dob.get(Calendar.MONTH);
        final int year = this.dob.get(Calendar.YEAR);

        return (currentyear * 12 + currentmonth - (year * 12 + month)) / 12;
    }

    /**
     * Gender.
     *
     * @param gender the gender
     * @return the person
     */
    public Person gender(final String gender) {
        this.gender = Gender.fromString(gender);
        return this;
    }

    /**
     * Gender.
     *
     * @param gender the gender
     * @return the person
     */
    public Person gender(final Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Gender.
     *
     * @return the string
     */
    public Gender gender() {
        return this.gender;
    }

    /**
     * Sets the postal address.
     *
     * @param postalAddress the postal address
     * @return the person
     */
    public Person postalAddress(final PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    /**
     * Gets the postal address.
     *
     * @return the postal address
     */
    public PostalAddress postalAddress() {
        return this.postalAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress the new email address
     * @return the person
     */
    public Person emailAddress(final String emailAddress) {
        this.emailAddress = new EmailAddress(emailAddress);
        return this;
    }

    /**
     * Email address.
     *
     * @param emailAddress the email address
     * @return the person
     */
    public Person emailAddress(final EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String emailAddress() {
        return this.emailAddress.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "Person [title=%s, forename=%s, middlename=%s, surname=%s, dob=%s, postalAddress=%s, emailAddress=%s]",
                this.title,
                this.forename,
                this.middlename,
                this.surname,
                this.dob,
                this.postalAddress,
                this.emailAddress);
    }

}
