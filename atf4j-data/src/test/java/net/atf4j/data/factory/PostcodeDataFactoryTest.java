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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.Postcode;

/**
 * The PostcodeDataFactoryTest Class.
 */
public final class PostcodeDataFactoryTest extends TestResultsReporting {

    /**
     * Test postcode random.
     */
    @Test
    public void testPostcodeCreate() {
        final Postcode postcode = PostcodeDataFactory.create();
        verifyNotNull(postcode);
        final String string = postcode.toString();
        final boolean verify = Postcode.verify(string);
        assertTrue(verify);
    }

    /**
     * Test postcode random.
     */
    @Test
    public void testPostcodeRandom() {
        final Postcode postcode = PostcodeDataFactory.random();
        this.log.info("postcode = {}", postcode);
        verifyNotNull(postcode);

        final boolean valid = Postcode.verify(postcode.toString());
        this.log.info("Postcode.verify({}) = {}", postcode, valid);
        assertTrue(valid);
    }

    /**
     * Test postcode get instance.
     */
    @Test
    public void testPostcodeGetInstance() {
        final PostcodeDataFactory postcodeDataFactory = PostcodeDataFactory.getInstance();
        this.log.debug("PostcodeDataFactory.getInstance() = {}", postcodeDataFactory);
        verifyNotNull(postcodeDataFactory);

        final PostcodeDataFactory same = PostcodeDataFactory.getInstance();
        verifyNotNull(same);
        assertEquals(same, postcodeDataFactory);
    }

}
