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

package org.dominokit.jacksonapt.server.ser.number;

import org.dominokit.jacksonapt.ser.BaseNumberJsonSerializer;
import org.dominokit.jacksonapt.server.ser.AbstractJsonSerializerTest;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class FloatJsonSerializerTest extends AbstractJsonSerializerTest<Float> {

    @Override
    protected BaseNumberJsonSerializer.FloatJsonSerializer createSerializer() {
        return BaseNumberJsonSerializer.FloatJsonSerializer.getInstance();
    }

    @Test
	public void testSerializeValue() {
        assertSerialization("34.10245", new Float("34.10245"));
        assertSerialization("-784.15454", new Float("-784.15454"));
    }
}
