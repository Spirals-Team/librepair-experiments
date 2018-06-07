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

import net.atf4j.data.Credentials;

/**
 * PersonFactory, a data factory to create data.
 */
public final class CredentialsDataFactory extends AbstractDataFactory {

    /** The Constant CREDENTIALS_TXT. */
    private static final String CREDENTIALS_TXT = "credentials.txt";

    /** The credentials data factory. */
    private static CredentialsDataFactory credentialsDataFactory = null;

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    protected static CredentialsDataFactory getInstance() {
        if (CredentialsDataFactory.credentialsDataFactory == null) {
            CredentialsDataFactory.credentialsDataFactory = new CredentialsDataFactory();
        }
        return CredentialsDataFactory.credentialsDataFactory;
    }

    /**
     * Private constructor prevents wild instantiation.
     */
    private CredentialsDataFactory() {
        super();
        initialise();
    }

    /**
     * Initialise.
     */
    protected void initialise() {
        this.lines = load(CREDENTIALS_TXT);
    }

    /**
     * Factory method to create new instance of CredentialsDataFactory.
     *
     * @return new instance of CredentialsDataFactory
     */
    public static Credentials create() {
        return new Credentials();
    }

    /**
     * Random.
     *
     * @return the credentials
     */
    public static Credentials random() {
        final Credentials credentials = new Credentials();
        return credentials;
    }

}
