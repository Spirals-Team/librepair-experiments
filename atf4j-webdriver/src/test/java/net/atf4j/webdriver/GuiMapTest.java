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

package net.atf4j.webdriver;

import static org.junit.Assert.assertNotNull;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for LogicalGuiMap objects.
 */
@Ignore
public final class GuiMapTest extends TestResultsReporting {

    /**
     * The LogicalGuiMap Class.
     */
    public class LogicalGuiMap extends AbstractGuiMap {
    }

    /**
     * The MissingGuiMap Class.
     */
    public class MissingGuiMap extends AbstractGuiMap {
    }

    /**
     * Test method for { net.atf4j.webdriver.AbstractGuiMap#getKeys()}.
     */
    @Test
    public void testGuiMapping() {
        final AbstractGuiMap instance = new LogicalGuiMap();
        final Enumeration<String> bundleKeys = instance.getKeys();
        while (bundleKeys.hasMoreElements()) {
            final String key = bundleKeys.nextElement();
            verifyNotNull(key);
            Assert.assertTrue(instance.containsKey(key));
            verifyNotNull(instance.getString(key));
            log.info("{}={}", key, instance.getString(key));
        }
    }

    /**
     * Test method for AbstractGuiMap.
     *
     * @throws Exception the exception
     */
    @Test
    public void testKeySet() throws Exception {
        final AbstractGuiMap abstractGuiMap = new LogicalGuiMap();
        verifyNotNull(abstractGuiMap);
        final Set<String> keySet = abstractGuiMap.keySet();
        for (final String key : keySet) {
            final String value = abstractGuiMap.getString(key);
            verifyNotNull(value);
        }
    }

    /**
     * test AbstractGuiMap object.
     */
    @Test(expected = MissingResourceException.class)
    public void testMissingGuiMapping() {
        assertNotNull(new MissingGuiMap());
    }

    /**
     * Test method for AbstractGuiMap.
     *
     * @throws Exception the exception
     */
    @Test
    public void testName() throws Exception {
        final AbstractGuiMap abstractGuiMap = new LogicalGuiMap();
        verifyNotNull(abstractGuiMap);
        abstractGuiMap.dumpTo(System.out);
    }

    /**
     * Test method for AbstractGuiMap.
     *
     * @throws Exception the exception
     */
    @Test
    public void testToString() throws Exception {
        final AbstractGuiMap abstractGuiMap = new LogicalGuiMap();
        verifyNotNull(abstractGuiMap);
        log.info(abstractGuiMap.toString());
        log.info(abstractGuiMap.getBaseBundleName());
        log.info(abstractGuiMap.getLocale().toString());
    }

}
