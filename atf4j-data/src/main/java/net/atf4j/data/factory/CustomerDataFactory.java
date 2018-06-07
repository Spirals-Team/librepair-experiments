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

import net.atf4j.data.Customer;

/**
 * CustomerDataFactory, a data factory to create data representing customers.
 */
public final class CustomerDataFactory extends AbstractDataFactory {

    /** The customer data factory. */
    private static CustomerDataFactory customerDataFactory = null;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static CustomerDataFactory getInstance() {
        if (CustomerDataFactory.customerDataFactory == null) {
            CustomerDataFactory.customerDataFactory = new CustomerDataFactory();
        }
        return CustomerDataFactory.customerDataFactory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private CustomerDataFactory() {
        super();
    }

    /**
     * Create new INSTANCE of create.
     *
     * @return the customer
     */
    public static Customer create() {
        return Customer.create();
    }

    /**
     * Random thing.
     *
     * @return the string
     */
    public static Customer random() {
        return Customer.random();
    }

}
