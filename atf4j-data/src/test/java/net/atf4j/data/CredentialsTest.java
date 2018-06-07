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
 * The CredentialsTest Class.
 */
public final class CredentialsTest extends TestResultsReporting {

    /**
     * Test credentials.
     */
    @Test
    public void testCredentials() {
        final Credentials credentials = new Credentials();
        this.log.debug("new Credentials() = {}", credentials);
        verifyNotNull(credentials);
    }

    /**
     * Test credentials username password email.
     */
    @Test
    public void testCredentialsUsernamePasswordEmail() {
        final Credentials credentials = new Credentials("Username", "Password", "email@example.org");
        this.log.debug("new Credentials() = {}", credentials);
        verifyNotNull(credentials);
    }

    /**
     * Test set get username.
     */
    @Test
    public void testSetGetUsername() {
        final Credentials credentials = new Credentials();
        this.log.debug("new Credentials() = {}", credentials);
        verifyNotNull(credentials);

        credentials.setUsername("username");
        this.log.debug("credentials = {}", credentials);
        assertEquals("username", credentials.getUsername());
    }

    /**
     * Test set get password.
     */
    @Test
    public void testSetGetPassword() {
        final Credentials credentials = new Credentials();
        this.log.debug("new Credentials() = {}", credentials);
        verifyNotNull(credentials);

        credentials.setPassword("username");
        this.log.debug("credentials = {}", credentials);
        assertEquals("username", credentials.getPassword());
    }

    /**
     * Test set get email.
     */
    @Test
    public void testSetGetEmail() {
        final Credentials credentials = new Credentials();
        this.log.debug("new Credentials() = {}", credentials);
        verifyNotNull(credentials);

        credentials.setEmail("email@example.org");
        this.log.debug("credentials = {}", credentials);
        assertEquals("email@example.org", credentials.getEmail());
    }

}
