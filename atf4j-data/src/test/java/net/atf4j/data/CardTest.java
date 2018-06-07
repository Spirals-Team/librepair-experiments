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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for Card objects.
 */
public final class CardTest extends TestResultsReporting {

    /** PROVIDER constant. */
    private static final String PROVIDER = "Provider";
    
    /** CARD_NAME constant. */
    private static final String CARD_NAME = "Card Name";
    
    /** CARD_NUMBER constant. */
    private static final String CARD_NUMBER = "CardNumber";

    /** INVALID_NUMBER. */
    private static final String INVALID_NUMBER = "1111-1111-1111-1111";

    /** INVALID_AMEX. */
    private static final String INVALID_AMEX = "0000 1234 5678 9999";

    /** AMEX. */
    private static final String AMEX_1 = "0000 1234 5678 9999";

    /** AMEX. */
    private static final String AMEX_2 = "3714-4963-539-8431";

    /** INVALID MASTERCARD. */
    private static final String INVALID_MASTERCARD = "1111-1111-1111-1111";

    /** MASTERCARD_1. */
    private static final String MASTERCARD_1 = "5500-0055-5555-5559";

    /** MASTERCARD_2. */
    private static final String MASTERCARD_2 = "5555 5555 5555 4444";

    /** INVALID VISA CARD NUMBER. */
    private static final String INVALID_VISA = "1111-1111-1111-1111";

    /** VISA_CARD_1. */
    private static final String VISA_CARD_1 = "4444-4444-4444-4448";

    /** VISA_CARD_2. */
    private static final String VISA_CARD_2 = "4111 1111 1111 1111";

    /** VISA_CARD_3. */
    private static final String VISA_CARD_3 = "4012888888881881";

    /**
     * Test for Card Factory.
     */
    @Test
    public void testCreate() {
        verifyNotNull(Card.create());
    }

    /**
     * Test method for void.
     */
    @Test
    public void testConstructor() {
        final String provider = PROVIDER;
        final String cardNumber = CARD_NUMBER;
        final String cardName = CARD_NAME;
        final Date endDate = new Date();
        final Date startDate = new Date();
        final Card card = new Card(provider, cardNumber, cardName, endDate, startDate);
        verifyNotNull(card);
    }

    /**
     * Test method for void.
     */
    @Test
    public void testCardFluentInterface() {
        final String provider = PROVIDER;
        final String cardNumber = CARD_NUMBER;
        final String cardName = CARD_NAME;
        final Date endDate = new Date();
        final Date startDate = new Date();
        final Card card = new Card()
            .setProvider(provider)
            .setCardNumber(cardNumber)
            .setCardName(cardName)
            .setStartDate(startDate)
            .setEndDate(endDate);
        verifyNotNull(card);
    }

    /**
     * Test method for void.
     */
    @Ignore
    @Test
    public void testVerifyInvalid() {
        assertFalse(Card.luhnCheck(INVALID_NUMBER));
        assertFalse(Card.luhnCheck(INVALID_AMEX));
        assertFalse(Card.luhnCheck(INVALID_MASTERCARD));
        assertFalse(Card.luhnCheck(INVALID_VISA));
    }

    /**
     * Test verify amex.
     */
    @Ignore
    @Test
    public void testVerifyAmex() {
        assertFalse(Card.verifyAmex(INVALID_AMEX));
        assertTrue(Card.verifyAmex(AMEX_1));
        assertTrue(Card.verifyAmex(AMEX_2));
    }

    /**
     * Test method for void.
     */
    @Test
    public void testVerifyVisa() {
        assertFalse(Card.verifyVisa(INVALID_VISA));
        assertTrue(Card.verifyVisa(VISA_CARD_1));
        assertTrue(Card.verifyVisa(VISA_CARD_2));
        assertTrue(Card.verifyVisa(VISA_CARD_3));
    }

    /**
     * Test method for Card.
     */
    @Test
    public void testVerifyMasterCard() {
        assertFalse(Card.verifyMasterCard(INVALID_MASTERCARD));
        assertTrue(Card.verifyMasterCard(MASTERCARD_1));
        assertTrue(Card.verifyMasterCard(MASTERCARD_2));
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        final Card card = new Card();
        verifyNotNull(card.toString());
    }

}
