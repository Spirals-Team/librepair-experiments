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

import java.util.ArrayDeque;
import java.util.Collection;

import net.atf4j.core.TestResultsReporting;

/**
 * ProductCatalog.
 */
public class ProductCatalogue extends TestResultsReporting {

    /** The catalogue. */
    private final Collection<Product> catalogue = new ArrayDeque<Product>();

    /**
     * Create new INSTANCE of create.
     *
     * @return the product catalog
     */
    public static ProductCatalogue create() {
        return new ProductCatalogue();
    }

    /**
     * Random.
     *
     * @return the product catalog
     */
    public static ProductCatalogue random() {
        return null;
    }

    /**
     * Instantiates a new product Catalogue.
     */
    public ProductCatalogue() {
        super();
    }

    /**
     * Adds the.
     *
     * @param e the e
     * @return true, if successful, otherwise false.
     */
    public boolean add(final Product e) {
        return this.catalogue.add(e);
    }

    /**
     * Gets the product list.
     *
     * @return the productList
     */
    public Collection<Product> getProductList() {
        return this.catalogue;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        final String className = this.getClass().getSimpleName();
        return String.format("%s [catalog=%s]", className, this.catalogue);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return debugString();
    }
}
