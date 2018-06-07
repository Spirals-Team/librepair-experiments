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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.atf4j.data.Bank;
import net.atf4j.data.Business;
import net.atf4j.data.Card;
import net.atf4j.data.Consumer;
import net.atf4j.data.Customer;
import net.atf4j.data.Person;
import net.atf4j.data.PostalAddress;
import net.atf4j.data.Postcode;

/**
 * Factory for random data.
 */
public final class DataFactory extends AbstractDataFactory {

    /** The Constant ISO_DATE_TIME. */
    public static final String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm'Z'";

    /** The Constant ISO_DATE. */
    public static final String ISO_DATE = "yyyy-MM-dd";

    /** The Constant ISO_TIME. */
    public static final String ISO_TIME = "HH:mm'Z'";

    /** The Constant UK_DATE. */
    public static final String UK_DATE = "dd:MM-yyyy";

    /**
     * Private constructor prevents wild instantiation.
     */
    private DataFactory() {
        super();
    }

    /**
     * Create a new Data instances.
     *
     * @return the postal address
     */
    public static PostalAddress createAddress() {
        return AddressDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the bank
     */
    public static Bank createBank() {
        return BankDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the business
     */
    public static Business createBusiness() {
        return BusinessDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the card
     */
    public static Card createCard() {
        return CardDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the consumer
     */
    public static Consumer createConsumer() {
        return ConsumerDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the customer
     */
    public static Customer createCustomer() {
        return CustomerDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the person
     */
    public static Person createPerson() {
        return PersonDataFactory.create();
    }

    /**
     * Create a new Data instances.
     *
     * @return the postcode
     */
    public static Postcode createPostcode() {
        return PostcodeDataFactory.create();
    }

    /**
     * Today date.
     *
     * @return today as Calendar
     */
    public static Calendar today() {
        return Calendar.getInstance();
    }

    /**
     * Date of birth.
     *
     * @return random date of birth as Calendar
     */
    public static Calendar dateOfBirth() {
        final Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.add(Calendar.YEAR, -1);
        dateOfBirth.add(Calendar.MONTH, -1);
        dateOfBirth.add(Calendar.DATE, -1);
        return dateOfBirth;
    }

    /**
     * DOB over 18.
     *
     * @return the calendar
     */
    public static Calendar dobOver18() {
        final Calendar dobOver18 = Calendar.getInstance();
        dobOver18.add(Calendar.YEAR, -18);
        dobOver18.add(Calendar.MONTH, 0);
        dobOver18.add(Calendar.DATE, 1);
        return dobOver18;
    }

    /**
     * DOB under 18.
     *
     * @return the calendar
     */
    public static Calendar dobUnder18() {
        final Calendar dobUnder18 = Calendar.getInstance();
        dobUnder18.add(Calendar.YEAR, -18);
        dobUnder18.add(Calendar.MONTH, 0);
        dobUnder18.add(Calendar.DATE, -1);
        return dobUnder18;
    }

    /**
     * Tomorrow date.
     *
     * @return the calendar
     */
    public static Calendar tomorrow() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    /**
     * Future date.
     *
     * @return the calendar
     */
    public static Calendar futureDate() {
        final Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        futureDate.add(Calendar.MONTH, 1);
        futureDate.add(Calendar.DATE, 1);
        return futureDate;
    }

    /**
     * Yesterday.
     *
     * @return the calendar
     */
    public static Calendar yesterday() {
        final Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.YEAR, 0);
        yesterday.add(Calendar.MONTH, 0);
        yesterday.add(Calendar.DATE, -1);
        return yesterday;
    }

    /**
     * Past date.
     *
     * @return the calendar
     */
    public static Calendar pastDate() {
        final Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.YEAR, -1);
        pastDate.add(Calendar.MONTH, -1);
        pastDate.add(Calendar.DATE, -1);
        return pastDate;
    }

    /**
     * Date between.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the calendar
     */
    public static Calendar dateBetween(final Date startDate, final Date endDate) {
        final long difference = endDate.getTime() - startDate.getTime();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(difference);
        return calendar;
    }

    /**
     * Date between.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the calendar
     */
    public static Calendar dateBetween(final Calendar startDate, final Calendar endDate) {
        return dateBetween(startDate.getTime(), endDate.getTime());
    }

    /**
     * This year.
     *
     * @return the int
     */
    public static int thisYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * This month.
     *
     * @return the int
     */
    public static int thisMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * To calendar.
     *
     * @param startDate the start date
     * @return the calendar
     */
    public static Calendar toCalendar(final Date startDate) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar;
    }

    /**
     * Format.
     *
     * @param calendar the calendar
     * @return the string
     */
    public static String format(final Calendar calendar) {
        return format(calendar.getTime());
    }

    /**
     * Format.
     *
     * @param date the date
     * @return the string
     */
    public static String format(final Date date) {
        return new SimpleDateFormat(ISO_DATE_TIME).format(date);
    }

}
