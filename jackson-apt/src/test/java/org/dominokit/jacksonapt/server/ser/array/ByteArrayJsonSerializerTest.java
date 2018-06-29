/*
 * Copyright 2014 Nicolas Morel
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

package org.dominokit.jacksonapt.server.ser.array;

import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.ser.array.PrimitiveByteArrayJsonSerializer;
import org.dominokit.jacksonapt.server.ser.AbstractJsonSerializerTest;
import org.junit.Test;

/**
 * Test byte array serialization.
 */
public class ByteArrayJsonSerializerTest extends AbstractJsonSerializerTest<byte[]> {

    @Override
    protected JsonSerializer<byte[]> createSerializer() {
        return PrimitiveByteArrayJsonSerializer.getInstance();
    }

    @Test
	public void testSerializeValue() {
        assertSerialization("\"AAsWIQ==\"", new byte[]{0, 11, 22, 33});
        assertSerialization("\"APXq3w==\"", new byte[]{0, -11, -22, -33});
        assertSerialization("\"AGScAA==\"", new byte[]{0, 100, -100, 0});
        assertSerialization("\"\"", new byte[0]);
    }

}
