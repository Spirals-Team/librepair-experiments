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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.Postcode.InvalidPostcodeException;

/**
 * A Unit test class for Postcode objects.
 */
public final class PostcodeTest extends TestResultsReporting {

    /** The Constant BLANK_POSTCODE. */
    private static final String BLANK_POSTCODE = "";

    /** The Constant VALID_POSTCODE. */
    private static final String VALID_POSTCODE = "XX00 9XX";

    /** The Constant INVALID_POSTCODE. */
    private static final String INVALID_POSTCODE = "00ZZ Z00";

    /** The Constant EXPECTED_OUTWARD. */
    private static final String EXPECTED_OUTWARD = "XX00";

    /** The Constant EXPECTED_INWARD. */
    private static final String EXPECTED_INWARD = "9XX";

    /**
     * Test method for Postcode default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        final Postcode postcode = new Postcode();
        this.log.debug("new Postcode() = {}", postcode);
        verifyNotNull(postcode);
    }

    /**
     * Test method for void.
     */
    @Test
    public void testPostcodeCreate() {
        final Postcode postcode = Postcode.create();
        this.log.debug("Postcode.create() = {}", postcode);
        verifyNotNull(postcode);
    }

    /**
     * Test method for Postcode.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Ignore
    @Test(expected = InvalidPostcodeException.class)
    public void testPostcodeEmpty() throws InvalidPostcodeException {
        final Postcode invalidPostcode = new Postcode(BLANK_POSTCODE);
        this.log.debug("new Postcode(BLANK_POSTCODE) = {}", invalidPostcode);
        verifyNotNull(invalidPostcode);
    }

    /**
     * Test method for Postcode.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Test(expected = InvalidPostcodeException.class)
    public void testInvalidPostcode() throws InvalidPostcodeException {
        final Postcode invalidPostcode = new Postcode(INVALID_POSTCODE);
        this.log.debug("new Postcode({}) = {}", INVALID_POSTCODE, invalidPostcode);
        verifyNotNull(invalidPostcode);
    }

    /**
     * Test valid postcode.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Ignore("Fails")
    @Test(expected = InvalidPostcodeException.class)
    public void testValidPostcode() throws InvalidPostcodeException {
        final Postcode validPostcode = new Postcode(VALID_POSTCODE);
        this.log.debug("new Postcode({}) = {}", VALID_POSTCODE, validPostcode);
        verifyNotNull(validPostcode);
    }

    /**
     * Test set invalid postcode.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Test(expected = InvalidPostcodeException.class)
    public void testSetInvalidPostcode() throws InvalidPostcodeException {
        final Postcode postcode = new Postcode();
        this.log.debug("new Postcode() = {}", postcode);
        verifyNotNull(postcode);
        postcode.setPostCode(INVALID_POSTCODE);
        fail(EXPECTED_EXCEPTION);
    }

    /**
     * Test set get postcode.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Test
    public void testSetGetPostcode() throws InvalidPostcodeException {
        final Postcode postcode = new Postcode();
        this.log.debug("postcode = {}", postcode);
        assertEquals(postcode, postcode.setPostCode(VALID_POSTCODE));
        assertEquals(VALID_POSTCODE, postcode.getPostCode());
    }

    /**
     * Test postcode parts.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Test
    public void testOutwardPart() throws InvalidPostcodeException {
        final Postcode postcode = new Postcode(VALID_POSTCODE);
        this.log.debug("postcode = {}", postcode);
        verifyNotNull(postcode);
        final String outwardCode = postcode.getOutwardCode();
        verifyNotNull(outwardCode);
        assertEquals(EXPECTED_OUTWARD, outwardCode);
        this.log.debug("outwardCode = {}", outwardCode);
    }

    /**
     * Test postcode parts.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Test
    public void testInwardPart() throws InvalidPostcodeException {
        final Postcode postcode = new Postcode(VALID_POSTCODE);
        this.log.debug("postcode = {}", postcode);
        final String inwardCode = postcode.getInwardCode();
        verifyNotNull(inwardCode);
        assertEquals(EXPECTED_INWARD, inwardCode);
        this.log.debug("inwardCode = {}", inwardCode);
    }

    /**
     * Test method for Postcode.
     */
    @Test
    public void testVerify() {
        assertTrue(Postcode.verify(VALID_POSTCODE));
    }

    /**
     * Test method for Postcode.
     *
     * @throws InvalidPostcodeException the invalid postcode exception
     */
    @Test
    public void testToString() throws InvalidPostcodeException {
        final Postcode postcode = new Postcode(VALID_POSTCODE);
        this.log.debug("postcode = {}", postcode);
        verifyNotNull(postcode);
        final String string = postcode.toString();
        verifyNotNull(string);
        assertEquals(VALID_POSTCODE, string);
        this.log.debug("postcode = {}", postcode);
    }

}
