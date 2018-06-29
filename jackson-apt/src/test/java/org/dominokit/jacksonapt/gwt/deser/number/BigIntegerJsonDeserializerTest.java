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

package org.dominokit.jacksonapt.gwt.deser.number;

import org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer.BigIntegerJsonDeserializer;
import org.dominokit.jacksonapt.gwt.deser.AbstractJsonDeserializerTest;

import java.math.BigInteger;

/**
 * @author Nicolas Morel
 */
public class BigIntegerJsonDeserializerTest extends AbstractJsonDeserializerTest<BigInteger> {

    @Override
    protected BigIntegerJsonDeserializer createDeserializer() {
        return BigIntegerJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        String value = "1548784651132124566543513203245448715154542123114001571970";
        BigInteger expected = new BigInteger(value);

        // test with a string
        assertDeserialization(expected, "\"" + value + "\"");

        // test with a number
        assertDeserialization(expected, value);
    }

}
