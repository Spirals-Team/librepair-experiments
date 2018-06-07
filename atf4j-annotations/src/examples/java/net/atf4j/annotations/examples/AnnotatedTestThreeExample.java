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

package net.atf4j.annotations.examples;

import static net.atf4j.annotations.AnnotationHelper.getTestDescription;
import static net.atf4j.annotations.AnnotationHelper.getTestId;
import static net.atf4j.annotations.AnnotationHelper.getTestName;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atf4j.annotations.Atf4j;

/**
 * Annotated Test Example Two, using static imports. Allows annotations to be
 * accessed directly with the functions :- <code>
 *  getTestId();
 *  getTestName();
 *  getTestDescription();
 *  </code>
 */
public class AnnotatedTestThreeExample {

    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Annotated Test example two.
     *
     * @throws Exception the exception
     */
    @Test
    @Atf4j.TestId("EXAMPLE-0003")
    @Atf4j.TestName("testExampleThree")
    @Atf4j.TestDescription("Example showing annotations with static import")
    public void testExampleThree() throws Exception {
        this.log.info("Test ID : = {} ", getTestId());
        this.log.info("Test Name : = {} ", getTestName());
        this.log.info("Test Description : = {} ", getTestDescription());
        assertTrue(true);
    }

}
