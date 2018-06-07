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

package net.atf4j.data.factory;

import java.util.Arrays;

import net.atf4j.data.EmailAddress;
import net.atf4j.data.Person;

/**
 * PersonFactory, a data factory to create data.
 */
public final class PersonDataFactory extends AbstractDataFactory {

    /** The Constant FEMALE_FORENAMES_TXT. */
    private static final String FEMALE_FORENAMES_TXT = "femaleForenames.txt";

    /** The Constant MALE_FORENAMES_TXT. */
    private static final String MALE_FORENAMES_TXT = "maleForenames.txt";

    /** The Constant SURNAMES_TXT. */
    private static final String SURNAMES_TXT = "surnames.txt";

    /** The person data factory. */
    private static PersonDataFactory personDataFactory = null;

    /** The female forenames. */
    private static String[] femaleForenames;

    /** The male forenames. */
    private static String[] maleForenames;

    /** The surnames. */
    private static String[] surnames;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    protected static PersonDataFactory getInstance() {
        if (PersonDataFactory.personDataFactory == null) {
            PersonDataFactory.personDataFactory = new PersonDataFactory();
        }
        return PersonDataFactory.personDataFactory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private PersonDataFactory() {
        super();
        initialise();
    }

    /**
     * Initialise.
     */
    protected void initialise() {
        femaleForenames = load(FEMALE_FORENAMES_TXT);
        maleForenames = load(MALE_FORENAMES_TXT);
        surnames = load(SURNAMES_TXT);
    }

    /**
     * Creates the Person object.
     *
     * @return the postal address
     */
    public static Person create() {
        return new Person();
    }

    /**
     * Random.
     *
     * @return the person
     */
    public static Person random() {
        final Person person = new Person();
        // person.forename(randomForename());
        // person.surname(randomSurname());
        // person.dateOfBirth(DataFactory.dateOfBirth());
        // person.emailAddress(EmailAddress.create().toString());
        // person.title(Title.DR);
        return person;
    }

    /**
     * Create a Random forename.
     *
     * @return the string
     */
    protected static String randomForename() {
        if (random.nextBoolean()) {
            return randomMaleForename();
        } else {
            return randomFemaleForename();
        }
    }

    /**
     * Create Random male forename.
     *
     * @return the string
     */
    protected static String randomMaleForename() {
        return randomEntry(maleForenames);
    }

    /**
     * Create Random female forename.
     *
     * @return the string
     */
    protected static String randomFemaleForename() {
        return randomEntry(femaleForenames);
    }

    /**
     * Create Random surname.
     *
     * @return the string
     */
    protected static String randomSurname() {
        return randomEntry(surnames);
    }

    /**
     * Create Random fullname.
     *
     * @return the string
     */
    public static String randomFullname() {
        return String.format("%s %s %s", randomForename(), randomForename(), randomSurname());
    }

    /**
     * Create Random email.
     *
     * @return the string
     */
    public static String randomEmail() {
        return EmailAddress.create().toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("PersonDataFactory [maleForenames=%s, femaleForenames=%s, surnames=%s]",
                Arrays.toString(maleForenames),
                Arrays.toString(femaleForenames),
                Arrays.toString(surnames));
    }

}
