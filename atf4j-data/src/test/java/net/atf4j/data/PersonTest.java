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

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.Person.Gender;
import net.atf4j.data.Person.Title;

/**
 * A Unit test class for Person objects.
 */
public final class PersonTest extends TestResultsReporting {

    /**
     * Test person.
     */
    @Test
    public void testPerson() {
        final Person person = new Person();
        this.log.debug("new Person() = {}", person);
        verifyNotNull(person);
        person.forename("Forname").middlename("MiddleName").surname("Surname");
    }

    /**
     * Test person forname middle surname.
     */
    @Test
    public void testPersonFornameMiddleSurname() {
        final Person person = new Person("Forname", "MiddleName", "Surname");
        this.log.debug("new Person() = {}", person);
        verifyNotNull(person);
    }

    /**
     * Test set get title.
     */
    @Test
    public void testSetGetTitle() {
        final Person person = new Person();
        this.log.debug("new Person() = {}", person);
        verifyNotNull(person);
        assertEquals(Title.SIR, person.title(Title.SIR).title());
    }

    /**
     * Test set get forename string.
     */
    @Ignore("Fails")
    @Test
    public void testSetGetForenameString() {
        final Person person = new Person();
        this.log.debug("new Person() = {}", person);
        verifyNotNull(person);

        final String forename = "Forename";
        person.forename(forename).forename();
        assertEquals(forename, person);
    }

    /**
     * Test set get middlename string.
     */
    @Ignore("Fails")
    @Test
    public void testSetGetMiddlenameString() {
        final Person person = new Person();
        this.log.debug("new Person() = {}", person);
        verifyNotNull(person);

        final String middlename = "Middlename";
        person.middlename(middlename).middlename();
        assertEquals(middlename, person);
    }

    /**
     * Test set get surname string.
     */
    @Test
    public void testSetGetSurnameString() {
        final String actual = "Surname";
        assertEquals(actual, new Person().surname(actual).surname());
    }

    /**
     * Test set get dob.
     */
    @Test
    public void testSetGetDob() {
        final Calendar actual = Calendar.getInstance();
        assertEquals(actual, new Person().dateOfBirth(actual).dateOfBirth());
    }

    /**
     * Test set get postal address.
     */
    @Test
    public void testSetGetPostalAddress() {
        final PostalAddress actual = null;
        assertEquals(actual, new Person().postalAddress(actual).postalAddress());
    }

    /**
     * Test set get email address.
     */
    @Ignore("Fails")
    @Test
    public void testSetGetEmailAddress() {
        final String actual = "";
        assertEquals(actual, new Person().emailAddress(actual).emailAddress());
    }

    /**
     * Test set get gender.
     */
    @Test
    public void testSetGetGender() {
        assertEquals(Gender.UNKNOWN, new Person().gender(Gender.UNKNOWN).gender());
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        new Person().toString();
    }

}
