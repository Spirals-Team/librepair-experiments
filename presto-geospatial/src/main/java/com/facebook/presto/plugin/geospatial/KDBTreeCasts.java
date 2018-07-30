/*
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
package com.facebook.presto.plugin.geospatial;

import com.facebook.presto.geospatial.KDBTree;
import com.facebook.presto.geospatial.KDBTreeSpec;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.function.LiteralParameters;
import com.facebook.presto.spi.function.OperatorType;
import com.facebook.presto.spi.function.ScalarOperator;
import com.facebook.presto.spi.function.SqlType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.airlift.json.ObjectMapperProvider;
import io.airlift.slice.Slice;

import java.io.IOException;

import static com.facebook.presto.spi.StandardErrorCode.INVALID_FUNCTION_ARGUMENT;

public final class KDBTreeCasts
{
    private KDBTreeCasts()
    {
    }

    @LiteralParameters("x")
    @ScalarOperator(OperatorType.CAST)
    @SqlType(KDBTreeType.NAME)
    public static KDBTree castVarcharToKDBTree(@SqlType("varchar(x)") Slice json)
    {
        return getKdbTree(json);
    }

    private static KDBTree getKdbTree(Slice jsonSlice)
    {
        ObjectMapper objectMapper = new ObjectMapperProvider().get();
        try {
            return KDBTree.fromSpec(objectMapper.readValue(jsonSlice.toStringUtf8(), KDBTreeSpec.class));
        }
        catch (IOException e) {
            throw new PrestoException(INVALID_FUNCTION_ARGUMENT, "Cannot parse KDB tree JSON: " + e.getMessage());
        }
    }
}
