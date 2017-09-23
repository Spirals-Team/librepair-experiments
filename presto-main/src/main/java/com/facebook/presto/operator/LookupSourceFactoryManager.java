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

package com.facebook.presto.operator;

import com.facebook.presto.execution.DriverGroupId;
import com.facebook.presto.spi.type.Type;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class LookupSourceFactoryManager
{
    private final PipelineExecutionFlow buildSideExecutionFlow;
    private final Function<DriverGroupId, LookupSourceFactory> lookupSourceFactoryProvider;
    private final List<Type> outputTypes;

    private final Map<DriverGroupId, LookupSourceFactory> map = new ConcurrentHashMap<>();

    public LookupSourceFactoryManager(PipelineExecutionFlow buildSideExecutionFlow, Function<DriverGroupId, LookupSourceFactory> lookupSourceFactoryProvider, List<Type> outputTypes)
    {
        this.buildSideExecutionFlow = requireNonNull(buildSideExecutionFlow, "buildSideExecutionFlow is null");
        this.lookupSourceFactoryProvider = requireNonNull(lookupSourceFactoryProvider, "lookupSourceFactoryProvider is null");
        this.outputTypes = requireNonNull(outputTypes, "outputTypes is null");
    }

    public static LookupSourceFactoryManager allAtOnce(PartitionedLookupSourceFactory factory)
    {
        return new LookupSourceFactoryManager(
                PipelineExecutionFlow.ALL_AT_ONCE,
                ignored -> factory,
                factory.getOutputTypes());
    }

    public List<Type> getBuildOutputTypes()
    {
        return outputTypes;
    }

    public LookupSourceFactory forDriverGroup(DriverGroupId driverGroupId)
    {
        if (buildSideExecutionFlow == PipelineExecutionFlow.ALL_AT_ONCE) {
            driverGroupId = DriverGroupId.notGrouped();
        }

        return map.computeIfAbsent(driverGroupId, lookupSourceFactoryProvider);
    }
}
