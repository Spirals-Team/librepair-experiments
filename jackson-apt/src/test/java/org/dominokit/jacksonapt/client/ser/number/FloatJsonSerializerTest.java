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

package org.dominokit.jacksonapt.client.ser.number;

import org.dominokit.jacksonapt.client.ser.AbstractJsonSerializerTest;
import org.dominokit.jacksonapt.ser.BaseNumberJsonSerializer.FloatJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class FloatJsonSerializerTest extends AbstractJsonSerializerTest<Float> {

    @Override
    protected FloatJsonSerializer createSerializer() {
        return FloatJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization("34.10245", new Float("34.10245"));
        assertSerialization("-784.15454", new Float("-784.15454"));
        // the float emulation gives slightly different results => use BigDecimal for precision!
        if (System.getProperty("superdevmode").equals("on")) {
            assertSerialization("1.4E-45", Float.MIN_VALUE);
            assertSerialization("3.4028235E38", Float.MAX_VALUE);
        }
    }
}
