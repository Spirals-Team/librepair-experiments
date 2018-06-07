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

import net.atf4j.core.TestResultsReporting;

/**
 * Credentials Class.
 */
public class Credentials extends TestResultsReporting {

    /** The username. */
    private String username = "";

    /** The password. */
    private String password = "";

    /** The email. */
    private String email = "";

    /**
     * Instantiates a new credentials.
     */
    public Credentials() {
        super();
        this.username = "username";
        this.password = "password";
        this.email = "email@example.org";
    }

    /**
     * Instantiates a new credentials.
     *
     * @param username the username
     * @param password the password
     * @param email the email
     */
    public Credentials(final String username, final String password, final String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     * @return the credentials
     */
    public Credentials setUsername(final String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     * @return the credentials
     */
    public Credentials setPassword(final String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     * @return the credentials
     */
    public Credentials setEmail(final String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Credentials [username=%s, password=%s, email=%s]",
                this.username,
                this.password,
                this.email);
    }

}
