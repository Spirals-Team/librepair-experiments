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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * The ConsumerTest Class.
 */
public final class ConsumerTest extends TestResultsReporting {

    /**
     * Test consumer.
     */
    @Test
    public void testConsumer() {
        verifyNotNull(new Consumer());
    }

    /**
     * Test consumer credentials.
     */
    @Test
    public void testConsumerCredentials() {
        final Credentials credentials = new Credentials();
        assertNotNull(credentials);
        final Consumer consumer = new Consumer(credentials);
        assertNotNull(consumer);
    }

    /**
     * Test consumer string string string.
     */
    @Test
    public void testConsumerStringStringString() {
        final Consumer consumer = new Consumer();
        assertNotNull(consumer);
    }

    /**
     * Test set get credentials.
     */
    @Test
    public void testSetGetCredentials() {
        final Consumer consumer = new Consumer();
        assertNotNull(consumer);
    }

    /**
     * Test set get username.
     */
    @Test
    public void testSetGetUsername() {
        final Consumer consumer = new Consumer();
        assertNotNull(consumer);
    }

    /**
     * Test set get password.
     */
    @Test
    public void testSetGetPassword() {
        final Consumer consumer = new Consumer();
        assertNotNull(consumer);
    }

    /**
     * Test set get email.
     */
    @Test
    public void testSetGetEmail() {
        final Consumer consumer = new Consumer();
        assertNotNull(consumer);
    }

}
