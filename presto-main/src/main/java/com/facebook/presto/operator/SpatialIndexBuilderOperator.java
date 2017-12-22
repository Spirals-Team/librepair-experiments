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

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.gen.JoinFilterFunctionCompiler.JoinFilterFunctionFactory;
import com.facebook.presto.sql.planner.Symbol;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.collect.ImmutableList;

import javax.annotation.concurrent.ThreadSafe;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;

@ThreadSafe
public class SpatialIndexBuilderOperator
        implements Operator
{
    public static final class SpatialIndexBuilderOperatorFactory
            implements OperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId planNodeId;
        private final PagesSpatialIndexFactory pagesSpatialIndexFactory;
        private final List<Integer> outputChannels;
        private final int indexChannel;
        private final Optional<JoinFilterFunctionFactory> filterFunctionFactory;
        private final Optional<Integer> sortChannel;
        private final List<JoinFilterFunctionFactory> searchFunctionFactories;
        private final PagesIndex.Factory pagesIndexFactory;

        private final int expectedPositions;

        private int partitionIndex;
        private boolean closed;

        public SpatialIndexBuilderOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                List<Type> types,
                List<Integer> outputChannels,
                Map<Symbol, Integer> layout,
                int indexChannel,
                Optional<JoinFilterFunctionFactory> filterFunctionFactory,
                Optional<Integer> sortChannel,
                List<JoinFilterFunctionFactory> searchFunctionFactories,
                int expectedPositions,
                int partitionCount,
                PagesIndex.Factory pagesIndexFactory)
        {
            this.operatorId = operatorId;
            this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
            requireNonNull(sortChannel, "sortChannel can not be null");
            requireNonNull(searchFunctionFactories, "searchFunctionFactories is null");
            checkArgument(sortChannel.isPresent() != searchFunctionFactories.isEmpty(), "both or none sortChannel and searchFunctionFactories must be set");
            checkArgument(Integer.bitCount(partitionCount) == 1, "partitionCount must be a power of 2");

            this.outputChannels = ImmutableList.copyOf(requireNonNull(outputChannels, "outputChannels is null"));

            List<Type> outputTypes = outputChannels.stream()
                    .map(types::get)
                    .collect(toImmutableList());
            pagesSpatialIndexFactory = new PagesSpatialIndexFactory(types, outputTypes);

            this.indexChannel = indexChannel;
            this.filterFunctionFactory = requireNonNull(filterFunctionFactory, "filterFunctionFactory is null");
            this.sortChannel = sortChannel;
            this.searchFunctionFactories = ImmutableList.copyOf(searchFunctionFactories);
            this.pagesIndexFactory = pagesIndexFactory;
            this.expectedPositions = expectedPositions;
        }

        public PagesSpatialIndexFactory getPagesSpatialIndexFactory()
        {
            return pagesSpatialIndexFactory;
        }

        @Override
        public List<Type> getTypes()
        {
            return pagesSpatialIndexFactory.getTypes();
        }

        @Override
        public SpatialIndexBuilderOperator createOperator(DriverContext driverContext)
        {
            checkState(!closed, "Factory is already closed");
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, planNodeId, HashBuilderOperator.class.getSimpleName());
            SpatialIndexBuilderOperator operator = new SpatialIndexBuilderOperator(
                    operatorContext,
                    pagesSpatialIndexFactory,
                    partitionIndex,
                    outputChannels,
                    indexChannel,
                    filterFunctionFactory,
                    sortChannel,
                    searchFunctionFactories,
                    expectedPositions,
                    pagesIndexFactory);

            partitionIndex++;
            return operator;
        }

        @Override
        public void noMoreOperators()
        {
            closed = true;
        }

        @Override
        public OperatorFactory duplicate()
        {
            throw new UnsupportedOperationException("Spatial index build can not be duplicated");
        }
    }

    private final OperatorContext operatorContext;
    private final PagesSpatialIndexFactory pagesSpatialIndexFactory;
    private final int partitionIndex;

    private final List<Integer> outputChannels;
    private final int indexChannel;
    private final Optional<JoinFilterFunctionFactory> filterFunctionFactory;
    private final Optional<Integer> sortChannel;
    private final List<JoinFilterFunctionFactory> searchFunctionFactories;

    private final PagesIndex index;

    private boolean finishing;

    public SpatialIndexBuilderOperator(
            OperatorContext operatorContext,
            PagesSpatialIndexFactory pagesSpatialIndexFactory,
            int partitionIndex,
            List<Integer> outputChannels,
            int indexChannel,
            Optional<JoinFilterFunctionFactory> filterFunctionFactory,
            Optional<Integer> sortChannel,
            List<JoinFilterFunctionFactory> searchFunctionFactories,
            int expectedPositions,
            PagesIndex.Factory pagesIndexFactory)
    {
        this.operatorContext = operatorContext;
        this.partitionIndex = partitionIndex;
        this.filterFunctionFactory = filterFunctionFactory;
        this.sortChannel = sortChannel;
        this.searchFunctionFactories = searchFunctionFactories;

        this.index = pagesIndexFactory.newPagesIndex(pagesSpatialIndexFactory.getTypes(), expectedPositions);
        this.pagesSpatialIndexFactory = pagesSpatialIndexFactory;

        this.outputChannels = outputChannels;
        this.indexChannel = indexChannel;
    }

    @Override
    public OperatorContext getOperatorContext()
    {
        return operatorContext;
    }

    @Override
    public List<Type> getTypes()
    {
        return pagesSpatialIndexFactory.getTypes();
    }

    @Override
    public boolean needsInput()
    {
        return !finishing;
    }

    @Override
    public void addInput(Page page)
    {
        requireNonNull(page, "page is null");
        checkState(!isFinished(), "Operator is already finished");

        index.addPage(page);

        if (!operatorContext.trySetMemoryReservation(index.getEstimatedSize().toBytes())) {
            index.compact();
        }
        operatorContext.setMemoryReservation(index.getEstimatedSize().toBytes());
        operatorContext.recordGeneratedOutput(page.getSizeInBytes(), page.getPositionCount());
    }

    @Override
    public Page getOutput()
    {
        return null;
    }

    @Override
    public void finish()
    {
        if (finishing) {
            return;
        }
        finishing = true;

        PagesSpatialIndex spatialIndex = index.createPagesSpatialIndex(operatorContext.getSession(), indexChannel, filterFunctionFactory, outputChannels);
        pagesSpatialIndexFactory.lendPagesSpatialIndex(spatialIndex);

        // operatorContext.setMemoryReservation(partition.get().getInMemorySizeInBytes());
    }

    @Override
    public boolean isFinished()
    {
        return finishing /*&& pagesSpatialIndexFactory.isDestroyed().isDone()*/;
    }
}
