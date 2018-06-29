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

package org.dominokit.jacksonapt.ser.array.dd;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jacksonapt.JsonSerializer} implementation for 2D array of char.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveCharacterArray2dJsonSerializer extends JsonSerializer<char[][]> {

    private static final PrimitiveCharacterArray2dJsonSerializer INSTANCE = new PrimitiveCharacterArray2dJsonSerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.ser.array.dd.PrimitiveCharacterArray2dJsonSerializer}
     */
    public static PrimitiveCharacterArray2dJsonSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveCharacterArray2dJsonSerializer() {
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isEmpty(char[][] value) {
        return null == value || value.length == 0;
    }

    /** {@inheritDoc} */
    @Override
    public void doSerialize(JsonWriter writer, char[][] values, JsonSerializationContext ctx, JsonSerializerParameters params) {
        if (!ctx.isWriteEmptyJsonArrays() && values.length == 0) {
            writer.cancelName();
            return;
        }

        writer.beginArray();
        for (char[] array : values) {
            if (ctx.isWriteCharArraysAsJsonArrays()) {
                writer.beginArray();
                for (char value : array) {
                    writer.value(Character.toString(value));
                }
                writer.endArray();
            } else {
                writer.value(new String(array));
            }
        }
        writer.endArray();

    }
}
