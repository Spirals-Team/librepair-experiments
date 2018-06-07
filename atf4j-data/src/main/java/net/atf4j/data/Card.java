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
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.factory.DataFactory;

/**
 * Card data class.
 */
public class Card extends TestResultsReporting {

    /** The provider. */
    private String provider = "";

    /** The card number. */
    private String cardNumber = "";

    /** The card name. */
    private String cardName = "";

    /** The start date. */
    private Calendar startDate;

    /** The end date. */
    private Calendar endDate;

    /** The ccv. */
    private String ccv = "";

    /**
     * Instantiates a new card.
     */
    public Card() {
        super();
    }

    /**
     * Instantiates a new card.
     *
     * @param provider the provider
     * @param cardNumber the card number
     * @param cardName the card name
     * @param endDate the end date
     * @param startDate the start date
     */
    public Card(final String provider,
            final String cardNumber,
            final String cardName,
            final Calendar endDate,
            final Calendar startDate) {
        super();
        this.provider = provider;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    /**
     * Instantiates a new card.
     *
     * @param provider the provider
     * @param cardNumber the card number
     * @param cardName the card name
     * @param endDate the end date
     * @param startDate the start date
     */
    public Card(final String provider,
            final String cardNumber,
            final String cardName,
            final Date endDate,
            final Date startDate) {
        super();
        this.provider = provider;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.startDate = DataFactory.toCalendar(startDate);
        this.endDate = DataFactory.toCalendar(endDate);
    }

    /**
     * Factory method to create new INSTANCE of a Card.
     *
     * @return the card INSTANCE.
     */
    public static Card create() {
        final Card card = new Card();
        card.setCardName("Card Name");
        card.setCardNumber("1234 5678 9000 0000");
        card.setStartDate(DataFactory.pastDate());
        card.setEndDate(DataFactory.futureDate());
        return card;
    }

    /**
     * Gets the Card's provider.
     *
     * @return the provider
     */
    public String getProvider() {
        return this.provider;
    }

    /**
     * Sets the Card's provider.
     *
     * @param provider the provider
     * @return the card
     */
    public Card setProvider(final String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * Gets the Card's card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /**
     * Sets the Card's card number.
     *
     * @param cardNumber the card number
     * @return the card
     */
    public Card setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    /**
     * Gets the Card's card name.
     *
     * @return the card name
     */
    public String getCardName() {
        return this.cardName;
    }

    /**
     * Sets the Card's card name.
     *
     * @param cardName the card name
     * @return the card
     */
    public Card setCardName(final String cardName) {
        this.cardName = cardName;
        return this;
    }

    /**
     * Gets the Card's start date.
     *
     * @return the start date
     */
    public Calendar getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the Card's end date.
     *
     * @return the end date
     */
    public Calendar getEndDate() {
        return this.endDate;
    }

    /**
     * Gets the Card's ccv.
     *
     * @return the ccv
     */
    public String getCcv() {
        return this.ccv;
    }

    /**
     * Sets the card's start date.
     *
     * @param startDate the start date
     * @return the card
     */
    public Card setStartDate(final Date startDate) {
        return setStartDate(DataFactory.toCalendar(startDate));
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date
     * @return the card
     */
    public Card setStartDate(final Calendar startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the end date.
     *
     * @param startDate the start date
     * @return the card
     */
    public Card setEndDate(final Date startDate) {
        return setEndDate(DataFactory.toCalendar(startDate));
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date
     * @return the card
     */
    public Card setEndDate(final Calendar endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Sets the ccv.
     *
     * @param ccv the ccv
     * @return the card
     */
    public Card setCcv(final String ccv) {
        this.ccv = ccv;
        return this;
    }

    /**
     * Luhn check.
     *
     * @param cardNumber the card number
     * @return true, if successful, otherwise false.
     */
    public static boolean luhnCheck(final String cardNumber) {
        int sum = 0;
        boolean evenDigit = false;
        final String cleanCardNumber = cleanCardNumber(cardNumber);
        for (int i = cleanCardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cleanCardNumber.substring(i, i + 1));
            if (evenDigit) {
                n *= 2;
                if (n > 9) {
                    n = n % 10 + 1;
                }
            }
            sum += n;
            evenDigit = !evenDigit;
        }
        return sum % 10 == 0;
    }

    /**
     * Verify amex number.
     *
     * @param candidate the candidate
     * @return true, if successful, otherwise false.
     */
    public static boolean verifyAmex(final String candidate) {
        final Pattern pattern = Pattern.compile("^(?<amex>3[47][0-9]{13})$");
        final Matcher matcher = pattern.matcher(cleanCardNumber(candidate));
        return matcher.matches();
    }

    /**
     * Verify visa card number.
     *
     * @param candidate the candidate
     * @return true, if successful, otherwise false.
     */
    public static boolean verifyVisa(final String candidate) {
        final Pattern pattern = Pattern.compile("^(?<visa>4[0-9]{12}(?:[0-9]{3}))$");
        final Matcher matcher = pattern.matcher(cleanCardNumber(candidate));
        return matcher.matches();
    }

    /**
     * Verify master card number.
     *
     * @param candidate the candidate
     * @return true, if successful, otherwise false.
     */
    public static boolean verifyMasterCard(final String candidate) {
        final Pattern pattern = Pattern.compile("^(?<mastercard>5[1-5][0-9]{14})$");
        final Matcher matcher = pattern.matcher(cleanCardNumber(candidate));
        return matcher.matches();
    }

    /**
     * Clean card number of surplus characters.
     *
     * @param cardNumber the card number
     * @return the card as a String
     */
    private static String cleanCardNumber(final String cardNumber) {
        return cardNumber.replaceAll("[^0-9]+", "");
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Card [provider=%s, cardNumber=%s, cardName=%s, startDate=%s, endDate=%s, ccv=%s]",
                this.provider,
                this.cardNumber,
                this.cardName,
                this.startDate,
                this.endDate,
                this.ccv);
    }

}
