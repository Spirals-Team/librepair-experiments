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

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.Consumer;

/**
 * Unit Test Suite for the ConsumerDataFactory.
 */
public final class ConsumerDataFactoryTest extends TestResultsReporting {

    /**
     * Test method for void.
     */
    @Test
    public void testCreate() {
        final Consumer consumer = ConsumerDataFactory.create();
        verifyNotNull(consumer);
        this.log.debug("ConsumerDataFactory.create() = {}", consumer);
    }

    /**
     * Test random.
     */
    @Test
    public void testRandom() {
        final Consumer consumer = ConsumerDataFactory.random();
        verifyNotNull(consumer);
        this.log.debug("ConsumerDataFactory.random() = {}", consumer);
    }

    /**
     * Test get instance.
     */
    @Test
    public void testGetInstance() {
        final ConsumerDataFactory customerDataFactory = ConsumerDataFactory.getInstance();
        verifyNotNull(customerDataFactory);
        this.log.debug("ConsumerDataFactory.getInstance() = {}", customerDataFactory);
    }

}
