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
 * Consumer Data.
 */
public class Consumer extends Person {

    /** The credentials. */
    private Credentials credentials = new Credentials();

    /**
     * Instantiates a new consumer.
     */
    public Consumer() {
        super();
    }

    /**
     * Instantiates a new consumer.
     *
     * @param credentials the credentials
     */
    public Consumer(final Credentials credentials) {
        super();
        this.credentials = credentials;
    }

    /**
     * Instantiates a new consumer.
     *
     * @param forename the forename
     * @param middlename the middlename
     * @param surname the surname
     */
    public Consumer(final String forename, final String middlename, final String surname) {
        super(forename, middlename, surname);
    }

    /**
     * Sets the credentials.
     *
     * @param credentials the credentials to set
     * @return the consumer
     */
    public Consumer setCredentials(final Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     * @return the credentials
     */
    public Credentials setUsername(final String username) {
        return this.credentials.setUsername(username);
    }

    /**
     * Sets the password.
     *
     * @param password the password
     * @return the credentials
     */
    public Credentials setPassword(final String password) {
        return this.credentials.setPassword(password);
    }

    /**
     * Sets the email.
     *
     * @param email the email
     * @return the credentials
     */
    public Credentials setEmail(final String email) {
        return this.credentials.setEmail(email);
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.credentials.getUsername();
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.credentials.getPassword();
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return this.credentials.getEmail();
    }

    /**
     * Gets the credentials.
     *
     * @return the credentials
     */
    public Credentials getCredentials() {
        return this.credentials;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.data.Person#toString()
     */
    @Override
    public String toString() {
        return String.format("Consumer [credentials=%s]", this.credentials);
    }

}
