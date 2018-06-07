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

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * The StrategyTest Class.
 */
public class StrategyTest extends TestResultsReporting {

    /**
     * The Strategy Class.
     */
    public class Strategy extends AbstractStrategy {

        /**
         * Instantiates a new strategy.
         */
        public Strategy() {
            super();
        }
    }

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        final Strategy strategy = new Strategy();
        this.log.debug("strategy = {}", strategy);
        verifyNotNull(strategy);
    }

}
