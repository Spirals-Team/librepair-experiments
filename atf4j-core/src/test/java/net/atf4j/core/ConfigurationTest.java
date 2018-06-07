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

package net.atf4j.core;

import org.junit.Test;

/**
 * Configuration Test class.
 */
public final class ConfigurationTest extends TestResultsReporting {

    /**
     * Test configuration.
     */
    @Test
    public void testConfiguration() {
        final Configuration configuration = new Configuration();
        verifyNotNull(configuration);
    }

    /**
     * Test my key.
     */
    @Test
    public void testMyKey() {
        final Configuration configuration = new Configuration();
        verifyNotNull(configuration);
        final String actual = configuration.myKey();
        Verify.verifyEqual("myValue", actual);
    }

    /**
     * Test my int value.
     */
    @Test
    public void testMyIntValue() {
        final Configuration configuration = new Configuration();
        verifyNotNull(configuration);
        final int actual = configuration.myIntValue();
        Verify.verifyEqual(0, actual);
    }

    /**
     * Test my long value.
     */
    @Test
    public void testMyLongValue() {
        final Configuration configuration = new Configuration();
        verifyNotNull(configuration);
        final long actual = configuration.myLongValue();
        Verify.verifyEqual(0L, actual);
    }

    /**
     * Test my bool value.
     */
    @Test
    public void testMyBoolValue() {
        final Configuration configuration = new Configuration();
        verifyNotNull(configuration);
        final boolean actual = configuration.myBooleanValue();
        Verify.verifyEqual(true, actual);
    }

}
