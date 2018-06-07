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

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for ProductCatalog objects.
 */
public final class ProductCatalogTest extends TestResultsReporting {

    /**
     * Test method for ProductCatalog.
     */
    @Test
    public void testDefaultConstructor() {
        final ProductCatalogue catalog = new ProductCatalogue();
        this.log.debug("new ProductCatalogue() = {}", catalog);
        verifyNotNull(catalog);
    }

    /**
     * test ProductCatalog object.
     */
    @Test
    public void testProductCatalog() {
        final ProductCatalogue productCatalog = new ProductCatalogue();
        this.log.debug("new ProductCatalogue() = {}", productCatalog);
        verifyNotNull(productCatalog);
        final Product product = new Product();
        productCatalog.add(product);
        this.log.info("productCatalog = {}", productCatalog);
    }

    /**
     * Test method for ProductCatalog.
     */
    @Test
    public void testDebugString() {
        final ProductCatalogue productCatalog = new ProductCatalogue();
        this.log.debug("productCatalog = {}", productCatalog.debugString());
        verifyNotNull(productCatalog.debugString());
    }

}
