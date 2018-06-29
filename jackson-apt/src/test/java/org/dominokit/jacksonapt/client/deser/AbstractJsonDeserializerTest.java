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

package org.dominokit.jacksonapt.client.deser;

import org.dominokit.jacksonapt.DefaultJsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.client.JacksonTestCase;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJsonDeserializerTest<T> extends JacksonTestCase {

    protected abstract JsonDeserializer<T> createDeserializer();

    public void testDeserializeNullValue() {
        assertNull(deserialize("null"));
    }

    protected T deserialize(String value) {
        JsonDeserializationContext ctx = DefaultJsonDeserializationContext.builder().build();
        return deserialize(ctx, value);
    }

    protected T deserialize(JsonDeserializationContext ctx, String value) {
        JsonReader reader = ctx.newJsonReader(value);
        return createDeserializer().deserialize(reader, ctx);
    }

    protected void assertDeserialization(T expected, String value) {
        assertEquals(expected, deserialize(value));
    }

    protected void assertDeserialization(JsonDeserializationContext ctx, T expected, String value) {
        assertEquals(expected, deserialize(ctx, value));
    }

    public abstract void testDeserializeValue();
}
