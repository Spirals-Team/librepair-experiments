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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * The JavaScriptExecutor Class.
 */
public final class JavaScriptExecutor {

    /**
     * Private to prevent wild construction.
     */
    private JavaScriptExecutor() {
        super();
    }

    /**
     * Inject JavaScript in to a page and execute.
     *
     * @param webDriver the driver
     * @param script the JavaScript source.
     * @return the execution object.
     */
    public static Object executeScript(final WebDriver webDriver, final String script) {
        return ((JavascriptExecutor) webDriver).executeScript(script);
    }

}
