/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with atf4j. If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.data.manager;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for SetUpData objects.
 */
public final class SetUpDataTest extends TestResultsReporting {

    /** The jdbc data. */
    private final AbstractSetUpData jdbcData = new MockJdbcData();

    /** The jms data. */
    private final AbstractSetUpData jmsData = new MockJmsData();

    /** The restful data. */
    private final AbstractSetUpData restfulData = new MockRestfulData();

    /**
     * Before.
     */
    @Before
    public void before() {
        assertTrue(this.jdbcData.setUp());
        assertTrue(this.jmsData.setUp());
        assertTrue(this.restfulData.setUp());
    }

    /**
     * After.
     */
    @Override
    @After
    public void after() {
        assertTrue(this.jdbcData.tearDown());
        assertTrue(this.jmsData.tearDown());
        assertTrue(this.restfulData.tearDown());
    }

    /**
     * Test.
     */
    @Test
    public void test() {
        assertTrue(this.jdbcData.reset());
        assertTrue(this.jmsData.reset());
        assertTrue(this.restfulData.reset());
    }

}
