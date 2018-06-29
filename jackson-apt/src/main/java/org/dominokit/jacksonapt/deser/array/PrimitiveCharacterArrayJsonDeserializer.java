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

package org.dominokit.jacksonapt.deser.array;

import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.CharacterJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

import java.util.List;

/**
 * Default {@link JsonDeserializer} implementation for array of char.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveCharacterArrayJsonDeserializer extends AbstractArrayJsonDeserializer<char[]> {

    private static final PrimitiveCharacterArrayJsonDeserializer INSTANCE = new PrimitiveCharacterArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link PrimitiveCharacterArrayJsonDeserializer}
     */
    public static PrimitiveCharacterArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveCharacterArrayJsonDeserializer() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char[] doDeserializeArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        List<Character> list = deserializeIntoList(reader, ctx, CharacterJsonDeserializer.getInstance(), params);

        char[] result = new char[list.size()];
        int i = 0;
        for (Character value : list) {
            if (null != value) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected char[] doDeserializeNonArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        if (JsonToken.STRING == reader.peek()) {
            return reader.nextString().toCharArray();
        } else if (ctx.isAcceptSingleValueAsArray()) {
            return doDeserializeSingleArray(reader, ctx, params);
        } else {
            throw ctx.traceError("Cannot deserialize a char[] out of " + reader.peek() + " token", reader);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected char[] doDeserializeSingleArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return new char[]{CharacterJsonDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}
