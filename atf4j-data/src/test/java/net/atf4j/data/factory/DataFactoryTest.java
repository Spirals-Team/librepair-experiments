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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.Text;
import net.atf4j.data.Bank;
import net.atf4j.data.Business;
import net.atf4j.data.Card;
import net.atf4j.data.Consumer;
import net.atf4j.data.Customer;
import net.atf4j.data.Person;
import net.atf4j.data.PostalAddress;
import net.atf4j.data.Postcode;

/**
 * A UnitTest for DataFactory objects.
 */
public final class DataFactoryTest extends TestResultsReporting {

    /**
     * Test create address.
     */
    @Test
    public void testCreateAddress() {
        final PostalAddress address = DataFactory.createAddress();
        this.log.debug("DataFactory.createAddress() = {}", address);
        verifyNotNull(address);
    }

    /**
     * Test create bank.
     */
    @Test
    public void testCreateBank() {
        final Bank bank = DataFactory.createBank();
        this.log.debug("DataFactory.createBank() = {}", bank);
        verifyNotNull(bank);
    }

    /**
     * Test create business.
     */
    @Test
    public void testCreateBusiness() {
        final Business business = DataFactory.createBusiness();
        this.log.debug("DataFactory.createBusiness() = {}", business);
        verifyNotNull(business);
    }

    /**
     * Test create card.
     */
    @Test
    public void testCreateCard() {
        final Card card = DataFactory.createCard();
        this.log.debug("DataFactory.createCard() = {}", card);
        verifyNotNull(card);
    }

    /**
     * Test create consumer.
     */
    @Test
    public void testCreateConsumer() {
        final Consumer consumer = DataFactory.createConsumer();
        this.log.debug("DataFactory.createConsumer() = {}", consumer);
        verifyNotNull(consumer);
    }

    /**
     * Test create customer.
     */
    @Test
    public void testCreateCustomer() {
        final Customer customer = DataFactory.createCustomer();
        this.log.debug("DataFactory.createCustomer() = {}", customer);
        verifyNotNull(customer);
    }

    /**
     * Unit Test for test create person.
     */
    @Test
    public void testCreatePerson() {
        final Person person = DataFactory.createPerson();
        verifyNotNull(person);
        this.log.debug("{}", person.toString());
    }

    /**
     * Unit Test for test create person.
     */
    @Test
    public void testCreatePostcode() {
        final Postcode postcode = DataFactory.createPostcode();
        this.log.debug("{}", postcode.toString());
        verifyNotNull(postcode);
    }

    /**
     * Test method for expected usage.
     */
    @Test
    public void testExpectedUsage() {
        final Calendar today = DataFactory.today();
        this.log.debug("DataFactory.today() = {}", DataFactory.format(today));
        verifyNotNull(today);

        final Calendar dateOfBirth = DataFactory.dateOfBirth();
        verifyNotNull(dateOfBirth);
        this.log.debug("DataFactory.dateOfBirth() = {}", DataFactory.format(dateOfBirth));

        final Calendar dobOver18 = DataFactory.dobOver18();
        verifyNotNull(dobOver18);
        this.log.debug("DataFactory.dobOver18() = {}", DataFactory.format(dobOver18));

        final Calendar dobUnder18 = DataFactory.dobUnder18();
        verifyNotNull(dobUnder18);
        this.log.debug("DataFactory.dobUnder18() = {}", DataFactory.format(dobUnder18));

        final Calendar futureDate = DataFactory.futureDate();
        verifyNotNull(futureDate);
        this.log.debug("DataFactory.futureDate() = {}", DataFactory.format(futureDate));

        final Calendar pastDate = DataFactory.pastDate();
        verifyNotNull(pastDate);
        this.log.debug("DataFactory.pastDate() = {}", DataFactory.format(pastDate));
    }

    /**
     * Test random string creation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testString() throws Exception {
        final char randomChar = Text.randomChar();
        verifyNotNull(randomChar);
        this.log.debug("DataFactory.randomChar() = {}", randomChar);

        final String randomString = Text.randomString(10);
        verifyNotNull(randomString);
        this.log.debug("DataFactory.randomString(10) = {}", randomString);
    }

    /**
     * Test random numeric string creation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDigits() throws Exception {
        final char randomDigit = Text.randomDigit();
        verifyNotNull(randomDigit);
        this.log.debug("DataFactory.randomDigit() = {}", randomDigit);

        final String randomDigits = Text.randomDigits(10);
        verifyNotNull(randomDigits);
        this.log.debug("DataFactory.randomDigits(10) = {}", randomDigits);
    }

    /**
     * test DataFactory object.
     *
     * @throws Exception the exception
     */
    @Test
    public void testToday() throws Exception {
        final Calendar today = DataFactory.today();
        verifyNotNull(today);
        verifyDateData(today);
    }

    /**
     * Test method for DataFactory.
     *
     * @throws Exception the exception
     */
    @Test
    public void testThisMonth() throws Exception {
        final int thisMonth = DataFactory.thisMonth();
        assertTrue(thisMonth >= 0 && thisMonth <= 11);
    }

    /**
     * Test method for DataFactory.
     *
     * @throws Exception the exception
     */
    @Test
    public void testThisYear() throws Exception {
        final int thisYear = DataFactory.thisYear();
        assertNotEquals(0, thisYear);
    }

    /**
     * test DataFactory object.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPastDate() throws Exception {
        final Calendar pastDate = DataFactory.pastDate();
        verifyDateData(pastDate);
    }

    /**
     * test DataFactory object.
     *
     * @throws Exception the exception
     */
    @Test
    public void testFutureDate() throws Exception {
        final Calendar futureDate = DataFactory.futureDate();
        verifyDateData(futureDate);
    }

    /**
     * Test method for void.
     *
     * @throws Exception the exception
     */
    public void testDateBetween() throws Exception {
        final Calendar futureDate = DataFactory.dateBetween(DataFactory.pastDate(), DataFactory.today());
        verifyDateData(futureDate);
    }

    /**
     * test DataFactory object.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDateOfBirth() throws Exception {
        final Calendar dateOfBirth = DataFactory.dateOfBirth();
        verifyDateData(dateOfBirth);
    }

    /**
     * test DataFactory object.
     *
     * @throws Exception the exception
     */
    @Test
    public void testOver18() throws Exception {
        final Calendar dobOver18 = DataFactory.dobOver18();
        verifyDateData(dobOver18);
    }

    /**
     * test DataFactory object.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUnder18() throws Exception {
        final Calendar dobUnder18 = DataFactory.dobUnder18();
        verifyDateData(dobUnder18);
    }

    /**
     * Verify DataFactory object.
     *
     * @param dateData the date data
     */
    private void verifyDateData(final Calendar dateData) {
        verifyNotNull(dateData);
        this.log.debug("verifyDateData({})", dateData.toString());

        final String fromCalendar = DataFactory.format(dateData);
        verifyNotNull(fromCalendar);

        final Date time = dateData.getTime();
        verifyNotNull(time);

        final String fromDate = DataFactory.format(time);
        verifyNotNull(fromDate);
        assertEquals(fromCalendar, fromDate);
    }
}
