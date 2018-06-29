/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dominokit.jacksonapt.server.deser.number;

import org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer.BigDecimalJsonDeserializer;
import org.dominokit.jacksonapt.server.deser.AbstractJsonDeserializerTest;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author Nicolas Morel
 */
public class BigDecimalJsonDeserializerTest extends AbstractJsonDeserializerTest<BigDecimal> {

    @Override
    protected BigDecimalJsonDeserializer createDeserializer() {
        return BigDecimalJsonDeserializer.getInstance();
    }

    @Override
    @Test
	public void testDeserializeValue() {
        String value = "15487846511321245665435132032454.1545815468465578451323888744";
        BigDecimal expected = new BigDecimal(value);

        // test with a string
        assertDeserialization(expected, "\"" + value + "\"");

        // test with a number
        assertDeserialization(expected, value);
    }

}
