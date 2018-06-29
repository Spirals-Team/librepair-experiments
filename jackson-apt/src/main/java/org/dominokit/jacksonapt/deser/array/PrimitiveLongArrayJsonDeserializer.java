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
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for array of long.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveLongArrayJsonDeserializer extends AbstractArrayJsonDeserializer<long[]> {

    private static final PrimitiveLongArrayJsonDeserializer INSTANCE = new PrimitiveLongArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.deser.array.PrimitiveLongArrayJsonDeserializer}
     */
    public static PrimitiveLongArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveLongArrayJsonDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public long[] doDeserializeArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        List<Long> list = deserializeIntoList(reader, ctx, BaseNumberJsonDeserializer.LongJsonDeserializer.getInstance(), params);

        long[] result = new long[list.size()];
        int i = 0;
        for (Long value : list) {
            if (null != value) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    protected long[] doDeserializeSingleArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return new long[]{BaseNumberJsonDeserializer.LongJsonDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}
