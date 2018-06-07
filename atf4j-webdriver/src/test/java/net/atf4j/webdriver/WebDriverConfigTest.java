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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.AbstractConfig.ConfigurationNotLoadedException;
import net.atf4j.core.TestResultsReporting;

/**
 * WebDriverConfigTest Class.
 */
public final class WebDriverConfigTest extends TestResultsReporting {

    /**
     * Test method for {net.atf4j.webdriver.WebDriverConfig#WebDriverConfig()}.
     *
     * @throws ConfigurationNotLoadedException the missing property file
     *             exception
     */
    @Test
    public void testDefaultConstructor() throws ConfigurationNotLoadedException {
        final WebDriverConfig config = new WebDriverConfig();
        verifyNotNull(config);
    }

    /**
     * test WebDriverConfig object.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testHappyPath() throws ConfigurationNotLoadedException {
        final WebDriverConfig config = new WebDriverConfig();
        verifyNotNull(config);
        verifyNotNull(config.targetBrowser());
        verifyNotNull(config.targetUrl());
        verifyNotNull(config.seleniumUrl());
        verifyNotNull(config.chromeBinaryDriver());
        verifyNotNull(config.firefoxBinaryDriver());
        assertEquals(1000, config.pageLoadTimeout());
        assertEquals(1, config.implicitWait());
    }

}
