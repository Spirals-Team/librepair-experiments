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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Global Configuration Test.
 */
public class GlobalConfigTest extends TestResultsReporting {

    /** KEY_FOR_INT. */
    private static final String KEY_FOR_INT = "keyForInt";
    
    /** KEY_FOR_STRING. */
    private static final String KEY_FOR_STRING = "keyForString";
    
    /** MISSING_KEY. */
    private static final String MISSING_KEY = "missingKey";

    /**
     * Test get instance.
     */
    @Test
    public void testGetInstance() {
        final GlobalConfig globalConfig = GlobalConfig.getInstance();
        log.debug("GlobalConfig.getInstance() = {}", globalConfig.toString());
        verifyNotNull(globalConfig);
    }

    /**
     * Test static value for missing key.
     */
    @Test
    public void testStaticValueForMissingKey() {
        final String value = GlobalConfig.valueFor(MISSING_KEY);
        log.debug("Config.valueFor(key='{}') = {}", MISSING_KEY, value);
        verifyNotNull(value);
        assertEquals("default", value);
    }

    /**
     * Unit Test for Static method valuefor(key).
     */
    @Test
    public void testStaticValueForKey() {
        final String value = GlobalConfig.valueFor(KEY_FOR_STRING);
        log.debug("Config.valueFor(key='{}') = {}", KEY_FOR_STRING, value);
        verifyNotNull(value);
        assertEquals("valueForString", value);
    }

    /**
     * Test static int value for missing key.
     */
    @Test
    public void testStaticIntValueForMissingKey() {
        final int value = GlobalConfig.intValueFor(MISSING_KEY);
        log.debug("Config.intValueFor(key='{}') = {}", MISSING_KEY, value);
        verifyNotNull(value);
        assertEquals(0, value);
    }

    /**
     * Unit Test for Static method valuefor(key).
     */
    @Test
    public void testStaticIntValueForKey() {
        final long intValue = GlobalConfig.intValueFor(KEY_FOR_INT);
        log.debug("Config.intValueFor(key='{}') = {}", KEY_FOR_INT, intValue);
        verifyNotNull(intValue);
        assertEquals(1, intValue);
    }

    /**
     * Test static long value for.
     */
    @Test
    public void testStaticLongValueFor() {
        final long value = GlobalConfig.longValueFor(MISSING_KEY);
        log.debug("Config.longValueFor(key='{}') = {}", MISSING_KEY, value);
        verifyNotNull(value);
        assertEquals(0L, value);
    }

    /**
     * Unit Test for Static method valuefor(key).
     */
    @Test
    public void testStaticLongValueForKey() {
        final String key = "keyForLong";
        final long longValue = GlobalConfig.longValueFor(key);
        log.debug("Config.longValueFor(key='{}') = {}", key, longValue);
        verifyNotNull(longValue);
        assertEquals(1L, longValue);
    }

    /**
     * Test static boolean value for missing key.
     */
    @Test
    public void testStaticBooleanValueForMissingKey() {
        final boolean value = GlobalConfig.booleanValueFor(MISSING_KEY);
        log.debug("Config.booleanValueFor(key='{}') = {}", MISSING_KEY, value);
        verifyNotNull(value);
        assertFalse(value);
    }

    /**
     * Unit Test for Static method valuefor(key).
     */
    @Test
    public void testStaticBooleanValueForKey() {
        final String key = "keyForBoolean";
        final boolean booleanValueFor = GlobalConfig.booleanValueFor(key);
        log.debug("Config.longValueFor(key='{}') = {}", key, booleanValueFor);
        verifyNotNull(booleanValueFor);
        assertEquals(false, booleanValueFor);
        assertTrue(GlobalConfig.booleanValueFor("keyForTrue"));
        assertFalse(GlobalConfig.booleanValueFor("keyForFalse"));
    }

}
