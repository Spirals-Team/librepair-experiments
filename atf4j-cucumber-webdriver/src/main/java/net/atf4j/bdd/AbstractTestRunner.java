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

package net.atf4j.bdd;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import net.atf4j.core.TestResultsReporting;

/**
 * Abstract Test Runner for Cucumber.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        strict = true,
        snippets = SnippetType.CAMELCASE,
        features = "classpath:features",
        tags = { "~@Ignore" })
public abstract class AbstractTestRunner extends TestResultsReporting {

    /**
     * Before class.
     */
    // @BeforeClass
    // public static void beforeClass() {
    // log.debug("beforeClass");
    // final String targetEnvironment = System.getProperty("targetEnvironment");
    // assertNotNull("Expected -DtargetEnvironment to be defined",
    // targetEnvironment);
    // }

    /**
     * Before test.
     */
    @Before
    public void beforeTest() {
        this.log.trace("beforeFeature");
    }

    /**
     * After test.
     */
    @After
    public void afterTest() {
        this.log.trace("afterTest");
    }

    /**
     * After class.
     */
    // @AfterClass
    // public static void afterClass() {
    // log.trace("afterClass");
    // }

}
