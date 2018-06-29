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
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

import java.util.List;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for array of int.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveIntegerArrayJsonDeserializer extends AbstractArrayJsonDeserializer<int[]> {

    private static final PrimitiveIntegerArrayJsonDeserializer INSTANCE = new PrimitiveIntegerArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.deser.array.PrimitiveIntegerArrayJsonDeserializer}
     */
    public static PrimitiveIntegerArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveIntegerArrayJsonDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public int[] doDeserializeArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        List<Integer> list = deserializeIntoList(reader, ctx, BaseNumberJsonDeserializer.IntegerJsonDeserializer.getInstance(), params);

        int[] result = new int[list.size()];
        int i = 0;
        for (Integer value : list) {
            if (null != value) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    protected int[] doDeserializeSingleArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return new int[]{BaseNumberJsonDeserializer.IntegerJsonDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}
