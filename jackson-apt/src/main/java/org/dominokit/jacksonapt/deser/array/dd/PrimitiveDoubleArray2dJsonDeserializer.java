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

package org.dominokit.jacksonapt.deser.array.dd;

import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.BaseNumberJsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

import java.util.List;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for 2D array of double.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveDoubleArray2dJsonDeserializer extends AbstractArray2dJsonDeserializer<double[][]> {

    private static final PrimitiveDoubleArray2dJsonDeserializer INSTANCE = new PrimitiveDoubleArray2dJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.deser.array.dd.PrimitiveDoubleArray2dJsonDeserializer}
     */
    public static PrimitiveDoubleArray2dJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveDoubleArray2dJsonDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public double[][] doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        List<List<Double>> list = deserializeIntoList(reader, ctx, BaseNumberJsonDeserializer.DoubleJsonDeserializer.getInstance(), params);

        if (list.isEmpty()) {
            return new double[0][0];
        }

        List<Double> firstList = list.get(0);
        if (firstList.isEmpty()) {
            return new double[list.size()][0];
        }

        double[][] array = new double[list.size()][firstList.size()];

        int i = 0;
        int j;
        for (List<Double> innerList : list) {
            j = 0;
            for (Double value : innerList) {
                if (null != value) {
                    array[i][j] = value;
                }
                j++;
            }
            i++;
        }
        return array;
    }
}
