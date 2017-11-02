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

import com.facebook.presto.memory.LocalMemoryContext;
import com.facebook.presto.operator.aggregation.Accumulator;
import com.facebook.presto.operator.aggregation.AccumulatorFactory;
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.block.SortOrder;
import com.facebook.presto.spi.function.WindowIndex;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.planner.plan.AggregationNode.Step;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;

import static com.facebook.presto.sql.planner.plan.AggregationNode.Step.SINGLE;
import static com.facebook.presto.util.MoreLists.listOfListsCopy;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Objects.requireNonNull;

/**
 * Group input data and produce a single block for each sequence of identical values.
 */
public class AggregationOperator
        implements Operator
{
    private final boolean partial;

    public static class AggregationOperatorFactory
            implements OperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId planNodeId;
        private final Step step;
        private final List<AccumulatorFactory> accumulatorFactories;
        private final List<Type> sourceTypes;
        private final List<List<Integer>> orderByChannels;
        private final List<List<SortOrder>> orderings;
        private final List<Type> types;
        private final Optional<PagesIndex.Factory> pagesIndexFactory;
        private boolean closed;

        public AggregationOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                Step step,
                List<AccumulatorFactory> accumulatorFactories,
                List<Type> sourceTypes,
                List<List<Integer>> orderByChannels,
                List<List<SortOrder>> orderings,
                Optional<PagesIndex.Factory> pagesIndexFactory)
        {
            this.operatorId = operatorId;
            this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
            this.step = step;
            this.accumulatorFactories = ImmutableList.copyOf(accumulatorFactories);
            this.sourceTypes = requireNonNull(sourceTypes);
            this.orderByChannels = listOfListsCopy(requireNonNull(orderByChannels, "orderByChannels is null"));
            this.orderings = listOfListsCopy(requireNonNull(orderings, "orderings is null"));
            this.pagesIndexFactory = requireNonNull(pagesIndexFactory, "pagesIndexFactory is null");
            this.types = toTypes(step, accumulatorFactories);
        }

        @Override
        public List<Type> getTypes()
        {
            return types;
        }

        @Override
        public Operator createOperator(DriverContext driverContext)
        {
            checkState(!closed, "Factory is already closed");
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, planNodeId, AggregationOperator.class.getSimpleName());
            return new AggregationOperator(operatorContext, step, accumulatorFactories, sourceTypes, orderByChannels, orderings, pagesIndexFactory);
        }

        @Override
        public void noMoreOperators()
        {
            closed = true;
        }

        @Override
        public OperatorFactory duplicate()
        {
            return new AggregationOperatorFactory(operatorId, planNodeId, step, accumulatorFactories, sourceTypes, orderByChannels, orderings, pagesIndexFactory);
        }
    }

    private enum State
    {
        NEEDS_INPUT,
        HAS_OUTPUT,
        FINISHED
    }

    private final OperatorContext operatorContext;
    private final LocalMemoryContext systemMemoryContext;
    private final List<Type> types;
    private final List<Aggregator> aggregates;

    private State state = State.NEEDS_INPUT;

    public AggregationOperator(
            OperatorContext operatorContext,
            Step step,
            List<AccumulatorFactory> accumulatorFactories,
            List<Type> sourceTypes,
            List<List<Integer>> orderByChannels,
            List<List<SortOrder>> orderings,
            Optional<PagesIndex.Factory> pagesIndexFactory)
    {
        this.operatorContext = requireNonNull(operatorContext, "operatorContext is null");
        this.systemMemoryContext = operatorContext.getSystemMemoryContext().newLocalMemoryContext();

        requireNonNull(step, "step is null");
        this.partial = step.isOutputPartial();

        requireNonNull(accumulatorFactories, "accumulatorFactories is null");
        this.types = toTypes(step, accumulatorFactories);

        checkArgument(step == SINGLE || (orderByChannels.stream().allMatch(List::isEmpty) && orderings.stream().allMatch(List::isEmpty)), "ORDER BY exists in non-SINGLE aggregation");
        // wrapper each function with an aggregator
        ImmutableList.Builder<Aggregator> builder = ImmutableList.builder();
        for (int i = 0; i < accumulatorFactories.size(); i++) {
            AccumulatorFactory accumulatorFactory = accumulatorFactories.get(i);
            builder.add(new Aggregator(
                    accumulatorFactory,
                    step,
                    sourceTypes,
                    orderByChannels.get(i),
                    orderings.get(i),
                    pagesIndexFactory));
        }
        aggregates = builder.build();
    }

    @Override
    public OperatorContext getOperatorContext()
    {
        return operatorContext;
    }

    @Override
    public List<Type> getTypes()
    {
        return types;
    }

    @Override
    public void finish()
    {
        if (state == State.NEEDS_INPUT) {
            state = State.HAS_OUTPUT;
        }
    }

    @Override
    public boolean isFinished()
    {
        return state == State.FINISHED;
    }

    @Override
    public boolean needsInput()
    {
        return state == State.NEEDS_INPUT;
    }

    @Override
    public void addInput(Page page)
    {
        checkState(needsInput(), "Operator is already finishing");
        requireNonNull(page, "page is null");

        long memorySize = 0;
        for (Aggregator aggregate : aggregates) {
            aggregate.processPage(page);
            memorySize += aggregate.getEstimatedSize();
        }
        if (partial) {
            systemMemoryContext.setBytes(memorySize);
        }
        else {
            operatorContext.setMemoryReservation(memorySize);
        }
    }

    @Override
    public Page getOutput()
    {
        if (state != State.HAS_OUTPUT) {
            return null;
        }

        // project results into output blocks
        List<Type> types = aggregates.stream().map(Aggregator::getType).collect(toImmutableList());

        PageBuilder pageBuilder = new PageBuilder(types);

        pageBuilder.declarePosition();
        for (int i = 0; i < aggregates.size(); i++) {
            Aggregator aggregator = aggregates.get(i);
            BlockBuilder blockBuilder = pageBuilder.getBlockBuilder(i);
            aggregator.evaluate(blockBuilder);
        }

        state = State.FINISHED;
        return pageBuilder.build();
    }

    private static List<Type> toTypes(Step step, List<AccumulatorFactory> accumulatorFactories)
    {
        ImmutableList.Builder<Type> types = ImmutableList.builder();
        for (AccumulatorFactory accumulatorFactory : accumulatorFactories) {
            types.add(new Aggregator(accumulatorFactory, step).getType());
        }
        return types.build();
    }

    private static class Aggregator
    {
        private final Accumulator accumulator;
        private final Step step;
        private final int intermediateChannel;

        private Aggregator(AccumulatorFactory accumulatorFactory, Step step)
        {
            this(accumulatorFactory, step, ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), Optional.empty());
        }

        private Aggregator(
                AccumulatorFactory accumulatorFactory,
                Step step,
                List<Type> aggregationSourceTypes,
                List<Integer> orderByChannels,
                List<SortOrder> orderings,
                Optional<PagesIndex.Factory> pagesIndexFactory)
        {
            Accumulator aggregation;
            if (step.isInputRaw()) {
                intermediateChannel = -1;
                aggregation = accumulatorFactory.createAccumulator();
            }
            else {
                checkArgument(accumulatorFactory.getInputChannels().size() == 1, "expected 1 input channel for intermediate aggregation");
                intermediateChannel = accumulatorFactory.getInputChannels().get(0);
                aggregation = accumulatorFactory.createIntermediateAccumulator();
            }

            if (orderByChannels.isEmpty()) {
                checkArgument(orderings.isEmpty(), "orderByChannels is empty but ordering is not");
                this.accumulator = aggregation;
            }
            else {
                checkState(pagesIndexFactory.isPresent(), "No pagesIndexFactory to process ordering");
                this.accumulator = new OrderingAccumulator(aggregation, aggregationSourceTypes, orderByChannels, orderings, pagesIndexFactory.get());
            }

            this.step = step;
        }

        public Type getType()
        {
            if (step.isOutputPartial()) {
                return accumulator.getIntermediateType();
            }
            else {
                return accumulator.getFinalType();
            }
        }

        public void processPage(Page page)
        {
            if (step.isInputRaw()) {
                accumulator.addInput(page);
            }
            else {
                accumulator.addIntermediate(page.getBlock(intermediateChannel));
            }
        }

        public void evaluate(BlockBuilder blockBuilder)
        {
            if (step.isOutputPartial()) {
                accumulator.evaluateIntermediate(blockBuilder);
            }
            else {
                accumulator.evaluateFinal(blockBuilder);
            }
        }

        public long getEstimatedSize()
        {
            return accumulator.getEstimatedSize();
        }
    }

    private static class OrderingAccumulator
            implements Accumulator
    {
        private final Accumulator accumulator;
        private final List<Integer> orderByChannels;
        private final List<SortOrder> orderings;
        private final int[] orderByOutputChannels;
        private final PagesIndex pagesIndex;

        private OrderingAccumulator(
                Accumulator accumulator,
                List<Type> aggregationSourceTypes,
                List<Integer> orderByChannels,
                List<SortOrder> orderings,
                PagesIndex.Factory pagesIndexFactory)
        {
            this.accumulator = requireNonNull(accumulator, "accumulator is null");
            this.orderByOutputChannels = new int[aggregationSourceTypes.size()];
            Arrays.setAll(orderByOutputChannels, IntUnaryOperator.identity());
            this.orderByChannels = ImmutableList.copyOf(orderByChannels);
            this.orderings = orderings;
            this.pagesIndex = pagesIndexFactory.newPagesIndex(aggregationSourceTypes, 10_000);
        }

        public long getEstimatedSize()
        {
            return pagesIndex.getEstimatedSize().toBytes() + accumulator.getEstimatedSize();
        }

        public Type getFinalType()
        {
            return accumulator.getFinalType();
        }

        public Type getIntermediateType()
        {
            return accumulator.getIntermediateType();
        }

        public void addInput(Page page)
        {
            pagesIndex.addPage(page);
        }

        public void addInput(WindowIndex index, List<Integer> channels, int startPosition, int endPosition)
        {
            throw new UnsupportedOperationException();
        }

        public void addIntermediate(Block block)
        {
            throw new UnsupportedOperationException();
        }

        public void evaluateIntermediate(BlockBuilder blockBuilder)
        {
            throw new UnsupportedOperationException();
        }

        public void evaluateFinal(BlockBuilder blockBuilder)
        {
            pagesIndex.sort(orderByChannels, orderings);
            Iterator<Page> pagesIterator = pagesIndex.getPages(orderByOutputChannels);
            pagesIterator.forEachRemaining(accumulator::addInput);
            accumulator.evaluateFinal(blockBuilder);
        }
    }
}
