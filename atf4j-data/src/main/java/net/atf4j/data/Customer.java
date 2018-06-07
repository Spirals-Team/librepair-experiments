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

/**
 * A Customer Business. For a person as a customer see Consumer.
 */
public class Customer extends Business {

    /**
     * Instantiates a new customer.
     */
    public Customer() {
        super();
    }

    /**
     * Factory method to create a new instance of Customer.
     *
     * @return new instance of Customer
     */
    public static Customer create() {
        return new Customer();
    }

    /**
     * Random.
     *
     * @return the business
     */
    public static Customer random() {
        return new Customer();
    }

}
