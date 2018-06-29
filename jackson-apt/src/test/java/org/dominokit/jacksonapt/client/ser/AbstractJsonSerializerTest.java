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

package org.dominokit.jacksonapt.client.ser;

import org.dominokit.jacksonapt.DefaultJsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.client.JacksonTestCase;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJsonSerializerTest<T> extends JacksonTestCase {

    protected abstract JsonSerializer<T> createSerializer();

    public void testSerializeNullValue() {
        assertSerialization("null", null);
    }

    protected String serialize(T value) {
        JsonSerializationContext ctx = DefaultJsonSerializationContext.builder().build();
        JsonWriter writer = ctx.newJsonWriter();
        createSerializer().serialize(writer, value, ctx);
        return writer.getOutput();
    }

    protected void assertSerialization(String expected, T value) {
        assertEquals(expected, serialize(value));
    }

    public abstract void testSerializeValue();
}
