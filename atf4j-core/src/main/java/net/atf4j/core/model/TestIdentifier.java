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

package net.atf4j.core.model;

import java.util.UUID;

import net.atf4j.core.TestResultsReporting;

/**
 * Unique TestIdentifier TODO : Make serializable?.
 */
public class TestIdentifier extends TestResultsReporting {

    private UUID uuid;

    /**
     * Instantiates a new test identifier.
     */
    public TestIdentifier() {
        super();
        uuid = UUID.randomUUID();
    }

    /**
     * create unique test identifier.
     *
     * @return the test identifier
     */
    public static TestIdentifier create() {
        return new TestIdentifier();
    }

    /**
     * Set the unique test identifier.
     *
     * @param uuidIn the new uuid
     * @return the test identifier
     */
    public TestIdentifier setUuid(final UUID uuidIn) {
        uuid = uuidIn;
        return this;
    }

    /**
     * Gets the UUID.
     *
     * @return the UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * return the UUID as a String.
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return uuid.toString();
    }

}
