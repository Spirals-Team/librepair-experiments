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

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for Product objects.
 */
public final class ProductTest extends TestResultsReporting {

    /**
     * Test method for Product.
     */
    @Test
    public void testDefaultConstructor() {
        verifyNotNull(new Product());
    }

    /**
     * Test method for Product.
     */
    @Test
    public void testProduct() {
        verifyNotNull(new Product());
    }

    /**
     * Test method for Product.
     */
    @Test
    public void testProductStringStringString() {
        new Product("", "", "");
    }

    /**
     * Test method for Product.
     */
    @Test
    public void testSetGetId() {
        final Product product = new Product();
        final String expected = "id";
        final String actual = product.setId(expected).getId();
        assertEquals(expected, actual);
    }

    /**
     * Test method for Product.
     */
    @Test
    public void testSetGetName() {
        final Product product = new Product();
        final String expected = "name";
        final String actual = product.setName(expected).getName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for Product.
     */
    @Test
    public void testSetGetDetails() {
        final Product product = new Product();
        final String expected = "details";
        final String actual = product.setDetails(expected).getDetails();
        assertEquals(expected, actual);
    }

    /**
     * Test method for Product.
     */
    @Test
    public void testToString() {
        final String string = new Product().toString();
        verifyNotNull(string);
    }

}
