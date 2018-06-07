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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for JsonToPojo objects.
 */
public final class JsonToPojoTest extends TestResultsReporting {

    /**
     * Test method for void.
     */
    @Test
    public void testPrettyPrint() {
        final String compactJson = "{\"id\":0123456789,\"name\":\"Test\",\"itemList\":[{\"itemId\":1,\"name\":\"Bread\",\"qty\":1,\"price\":1.00},{\"itemId\":2,\"name\":\"Eggs\",\"qty\":6,\"price\":2.00},{\"itemId\":3,\"name\":\"Meat\",\"qty\":1,\"price\":10}]}";
        final JsonToPojo jsonToPojo = new JsonToPojo();
        final String prettyJson = jsonToPojo.toPrettyJson(compactJson);
        log.info("compactJson = {}\n", compactJson);
        log.info("prettyJson = {}\n", prettyJson);
        assertNotNull(compactJson);
        assertNotNull(prettyJson);
    }

}
