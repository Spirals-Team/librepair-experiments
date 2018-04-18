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

import com.esri.core.geometry.ogc.OGCGeometry;
import com.facebook.presto.memory.context.LocalMemoryContext;
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.gen.JoinFilterFunctionCompiler.JoinFilterFunctionFactory;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;

public class SpatialIndexBuilderOperator
        implements Operator
{
    @FunctionalInterface
    public interface SpatialPredicate
    {
        boolean apply(OGCGeometry probe, OGCGeometry build, OptionalDouble radius);
    }

    public static final class SpatialIndexBuilderOperatorFactory
            implements OperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId planNodeId;
        private final PagesSpatialIndexFactory pagesSpatialIndexFactory;
        private final List<Integer> outputChannels;
        private final int indexChannel;
        private final Optional<Integer> radiusChannel;
        private final SpatialPredicate spatialRelationshipTest;
        private final Optional<JoinFilterFunctionFactory> filterFunctionFactory;
        private final PagesIndex.Factory pagesIndexFactory;

        private final int expectedPositions;

        private boolean closed;

        public SpatialIndexBuilderOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                List<Type> types,
                List<Integer> outputChannels,
                int indexChannel,
                Optional<Integer> radiusChannel,
                SpatialPredicate spatialRelationshipTest,
                Optional<JoinFilterFunctionFactory> filterFunctionFactory,
                int expectedPositions,
                PagesIndex.Factory pagesIndexFactory)
        {
            this.operatorId = operatorId;
            this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
            this.outputChannels = ImmutableList.copyOf(requireNonNull(outputChannels, "outputChannels is null"));

            List<Type> outputTypes = outputChannels.stream()
                    .map(types::get)
                    .collect(toImmutableList());
            pagesSpatialIndexFactory = new PagesSpatialIndexFactory(types, outputTypes);

            this.indexChannel = indexChannel;
            this.radiusChannel = radiusChannel;
            this.spatialRelationshipTest = spatialRelationshipTest;
            this.filterFunctionFactory = requireNonNull(filterFunctionFactory, "filterFunctionFactory is null");
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
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, planNodeId, SpatialIndexBuilderOperator.class.getSimpleName());
            return new SpatialIndexBuilderOperator(
                    operatorContext,
                    pagesSpatialIndexFactory,
                    outputChannels,
                    indexChannel,
                    radiusChannel,
                    spatialRelationshipTest,
                    filterFunctionFactory,
                    expectedPositions,
                    pagesIndexFactory);
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
    private final LocalMemoryContext localUserMemoryContext;
    private final PagesSpatialIndexFactory pagesSpatialIndexFactory;

    private final List<Integer> outputChannels;
    private final int indexChannel;
    private final Optional<Integer> radiusChannel;
    private final SpatialPredicate spatialRelationshipTest;
    private final Optional<JoinFilterFunctionFactory> filterFunctionFactory;

    private final PagesIndex index;
    private ListenableFuture<?> indexNotNeeded;

    private boolean finishing;
    private boolean finished;

    private SpatialIndexBuilderOperator(
            OperatorContext operatorContext,
            PagesSpatialIndexFactory pagesSpatialIndexFactory,
            List<Integer> outputChannels,
            int indexChannel,
            Optional<Integer> radiusChannel,
            SpatialPredicate spatialRelationshipTest,
            Optional<JoinFilterFunctionFactory> filterFunctionFactory,
            int expectedPositions,
            PagesIndex.Factory pagesIndexFactory)
    {
        this.operatorContext = requireNonNull(operatorContext, "operatorContext is null");
        this.localUserMemoryContext = operatorContext.localUserMemoryContext();
        this.spatialRelationshipTest = requireNonNull(spatialRelationshipTest, "spatialRelationshipTest is null");
        this.filterFunctionFactory = filterFunctionFactory;

        this.pagesSpatialIndexFactory = requireNonNull(pagesSpatialIndexFactory, "pagesSpatialIndexFactory is null");
        this.index = pagesIndexFactory.newPagesIndex(pagesSpatialIndexFactory.getTypes(), expectedPositions);

        this.outputChannels = requireNonNull(outputChannels, "outputChannels is null");
        this.indexChannel = indexChannel;
        this.radiusChannel = radiusChannel;
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
        return !finished;
    }

    @Override
    public void addInput(Page page)
    {
        requireNonNull(page, "page is null");
        checkState(!isFinished(), "Operator is already finished");

        index.addPage(page);

        if (!localUserMemoryContext.trySetBytes((index.getEstimatedSize().toBytes()))) {
            index.compact();
            localUserMemoryContext.setBytes(index.getEstimatedSize().toBytes());
        }

        operatorContext.recordGeneratedOutput(page.getSizeInBytes(), page.getPositionCount());
    }

    @Override
    public Page getOutput()
    {
        return null;
    }

    @Override
    public ListenableFuture<?> isBlocked()
    {
        if (indexNotNeeded != null && !indexNotNeeded.isDone()) {
            return indexNotNeeded;
        }
        return NOT_BLOCKED;
    }

    @Override
    public void finish()
    {
        if (finishing) {
            return;
        }

        finishing = true;
        PagesSpatialIndexSupplier spatialIndex = index.createPagesSpatialIndex(operatorContext.getSession(), indexChannel, radiusChannel, spatialRelationshipTest, filterFunctionFactory, outputChannels);
        localUserMemoryContext.setBytes(index.getEstimatedSize().toBytes() + spatialIndex.getEstimatedSize().toBytes());
        indexNotNeeded = pagesSpatialIndexFactory.lendPagesSpatialIndex(spatialIndex);
    }

    @Override
    public boolean isFinished()
    {
        if (finished) {
            return true;
        }

        if (finishing && indexNotNeeded.isDone()) {
            index.clear();
            localUserMemoryContext.setBytes(index.getEstimatedSize().toBytes());
            finished = true;
        }

        return finished;
    }

    @Override
    public void close()
    {
        index.clear();
        localUserMemoryContext.setBytes(index.getEstimatedSize().toBytes());
    }
}
