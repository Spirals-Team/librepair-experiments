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

package net.atf4j.data;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.data.factory.LoremIpsumFactory;

/**
 * A Unit test class for LoremIpsum objects.
 */
public final class LoremIpsumTest extends TestResultsReporting {

    /**
     * Unit Test for LoremIpsum text.
     */
    @Test
    public void testLoremIpsum() {
        final String text = LoremIpsumFactory.text();
        assertNotNull("unexpected null", text);
        this.log.info("LoremIpsum.text() = {}", LoremIpsumFactory.text());

        this.log.info("LoremIpsum.word() = {}", LoremIpsumFactory.word());
        this.log.info("LoremIpsum.words(1) = {}", LoremIpsumFactory.words(1));
        this.log.info("LoremIpsum.words(14) = {}", LoremIpsumFactory.words(4));
        this.log.info("LoremIpsum.words(10) = {}", LoremIpsumFactory.words(10));

        this.log.info("LoremIpsum.sentence() = {}", LoremIpsumFactory.sentence());
        this.log.info("LoremIpsum.sentence(1) = {}", LoremIpsumFactory.sentences(1));
        this.log.info("LoremIpsum.sentence(6) = {}", LoremIpsumFactory.sentences(6));

        this.log.info("LoremIpsum.paragraph() = {} ", LoremIpsumFactory.paragraph());
        this.log.info("LoremIpsum.paragraphs(1) = {} ", LoremIpsumFactory.paragraphs(1));
        this.log.info("LoremIpsum.paragraphs(4) = {} ", LoremIpsumFactory.paragraphs(4));
    }

}
