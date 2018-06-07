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

package net.atf4j.pog;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * Unit Tests for Abstract Template Engine class.
 */
public final class TemplateEngineTest extends TestResultsReporting {

    /**
     * Mock the Abstract Template Engine.
     */
    public final class TemplateEngine extends AbstractTemplateEngine {
    }

    /**
     * Unit Test TemplateEngine.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testTemplateEngine() {
        final TemplateEngine templateEngine = new TemplateEngine();
        verifyNotNull(templateEngine);
        templateEngine.execute();
    }

    /**
     * Test execute.
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testMissingTemplate() {
        final TemplateEngine templateEngine = new TemplateEngine();
        verifyNotNull(templateEngine);
        templateEngine.setBasePath("./");
        templateEngine.setTemplateFilename("Missing.vm");
        templateEngine.execute();
    }

    /**
     * Test fluent execute.
     */
    @Ignore
    @Test
    public void testFluentExecute() {
        final TemplateEngine templateEngine = new TemplateEngine();
        verifyNotNull(templateEngine);
        templateEngine.setBasePath("./src/test/resources/");
        templateEngine.setTemplateFilename("templates/Class.vm");
        templateEngine.execute();
    }

}
