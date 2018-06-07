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

package net.atf4j.data.manager.jms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.manager.MockJmsData;

/**
 * A UnitTest for JmsData objects.
 */
public final class JmsDataTest extends TestResultsReporting {

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        final MockJmsData jmsData = new MockJmsData();
        this.log.debug("new JmsData() = {}", jmsData);
        verifyNotNull(jmsData);
    }

    /**
     * Test set up.
     */
    @Test
    public void testSetUp() {
        final MockJmsData jmsData = new MockJmsData();
        this.log.debug("new JmsData() = {}", jmsData);
        verifyNotNull(jmsData);
        assertTrue(jmsData.setUp());
    }

    /**
     * Test reset.
     */
    @Test
    public void testReset() {
        final MockJmsData jmsData = new MockJmsData();
        this.log.debug("new JmsData() = {}", jmsData);
        verifyNotNull(jmsData);
        assertTrue(jmsData.reset());
    }

    /**
     * Test tear down.
     */
    @Test
    public void testTearDown() {
        final MockJmsData jmsData = new MockJmsData();
        this.log.debug("new JmsData() = {}", jmsData);
        verifyNotNull(jmsData);
        assertTrue(jmsData.tearDown());
    }

}
