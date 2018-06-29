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

package org.dominokit.jacksonapt.ser.array;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jacksonapt.JsonSerializer} implementation for array of char.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveCharacterArrayJsonSerializer extends JsonSerializer<char[]> {

    private static final PrimitiveCharacterArrayJsonSerializer INSTANCE = new PrimitiveCharacterArrayJsonSerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.ser.array.PrimitiveCharacterArrayJsonSerializer}
     */
    public static PrimitiveCharacterArrayJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveCharacterArrayJsonSerializer() {
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isEmpty(char[] value) {
        return null == value || value.length == 0;
    }

    /** {@inheritDoc} */
    @Override
    public void doSerialize(JsonWriter writer, char[] values, JsonSerializationContext ctx, JsonSerializerParameters params) {
        if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
            writer.cancelName();
            return;
        }

        if (ctx.isWriteCharArraysAsJsonArrays() && !(ctx.isWriteSingleElemArraysUnwrapped() && values.length == 1)) {
            writer.beginArray();
            for (char value : values) {
                writer.value(Character.toString(value));
            }
            writer.endArray();
        } else {
            writer.value(new String(values));
        }
    }
}
