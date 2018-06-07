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

/**
 * Test Context.
 */
public final class TestContext extends TestResultsReporting {

    /**
     * Instantiates a new test context.
     */
    private TestContext() {
        super();
    }

    /**
     * Checks if running on a local machine.
     *
     * -DisLocal=true
     *
     * @return true, if local, otherwise false.
     */
    public static boolean isLocal() {
        return Boolean.getBoolean("isLocal");
    }

    /**
     * Checks if Selenium grid is available.
     *
     * -DisGrid=true
     *
     * @return true, if grid is available, otherwise false.
     */
    public static boolean isGrid() {
        return Boolean.getBoolean("isSelenium");
    }

    /**
     * Checks if jenkins is available.
     *
     * -DisJenkins=true
     *
     * @return true, if jenkins is available, otherwise false.
     */
    public static boolean isJenkins() {
        return Boolean.getBoolean("isJenkins");
    }

    /**
     * Checks if a local server is available.
     * <p>
     * Default to <code>http://127.0.0.1:8080/</code>.
     *
     * -DlocalServer=true
     *
     * @return true, if local server is available, otherwise false.
     */
    public static boolean localServer() {
        return Boolean.getBoolean("localServer");
    }

    /**
     * Checks if active MQ.
     *
     * @return true, if is active MQ
     */
    public static boolean isActiveMQ() {
        return Boolean.getBoolean("isActiveMQ");
    }

}
