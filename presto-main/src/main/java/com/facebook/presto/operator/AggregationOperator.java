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
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.block.SortOrder;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.planner.plan.AggregationNode.Step;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;

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
        private final Optional<PagesIndex.Factory> pageIndexFactory;
        private boolean closed;

        public AggregationOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                Step step,
                List<AccumulatorFactory> accumulatorFactories,
                List<Type> sourceTypes,
                List<List<Integer>> orderByChannels,
                List<List<SortOrder>> orderings,
                Optional<PagesIndex.Factory> pageIndexFactory)
        {
            this.operatorId = operatorId;
            this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
            this.step = step;
            this.accumulatorFactories = ImmutableList.copyOf(accumulatorFactories);
            this.sourceTypes = requireNonNull(sourceTypes);
            requireNonNull(orderByChannels, "orderByChannels is null");
            this.orderByChannels = ImmutableList.copyOf(orderByChannels.stream().map(ImmutableList::copyOf).collect(toImmutableList()));
            requireNonNull(orderings, "orderings is null");
            this.orderings = ImmutableList.copyOf(orderings.stream().map(ImmutableList::copyOf).collect(toImmutableList()));
            this.pageIndexFactory = requireNonNull(pageIndexFactory, "pageIndexFactory is null");
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
            return new AggregationOperator(operatorContext, step, accumulatorFactories, sourceTypes, orderByChannels, orderings, pageIndexFactory);
        }

        @Override
        public void close()
        {
            closed = true;
        }

        @Override
        public OperatorFactory duplicate()
        {
            return new AggregationOperatorFactory(operatorId, planNodeId, step, accumulatorFactories, sourceTypes, orderByChannels, orderings, pageIndexFactory);
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
            Optional<PagesIndex.Factory> pageIndexFactory)
    {
        this.operatorContext = requireNonNull(operatorContext, "operatorContext is null");
        this.systemMemoryContext = operatorContext.getSystemMemoryContext().newLocalMemoryContext();

        requireNonNull(step, "step is null");
        this.partial = step.isOutputPartial();

        requireNonNull(accumulatorFactories, "accumulatorFactories is null");
        this.types = toTypes(step, accumulatorFactories);

        // wrapper each function with an aggregator
        ImmutableList.Builder<Aggregator> builder = ImmutableList.builder();
        for (int i = 0; i < accumulatorFactories.size(); i++) {
            AccumulatorFactory accumulatorFactory = accumulatorFactories.get(i);
            builder.add(new Aggregator(accumulatorFactory, step, sourceTypes, orderByChannels.get(i), orderings.get(i), pageIndexFactory));
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
            aggregator.processOrderBy();
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
        private final Accumulator aggregation;
        private final Step step;
        private final int intermediateChannel;
        private final List<Integer> orderByChannels;
        private final List<SortOrder> orderings;
        private final int[] orderByOutputChannels;
        private final Optional<PagesIndex> pageIndex;
        private final PageBuilder pageBuilder;

        private Aggregator(AccumulatorFactory accumulatorFactory, Step step)
        {
            this(accumulatorFactory, step, ImmutableList.of(), ImmutableList.of(), ImmutableList.of(), Optional.empty());
        }

        private Aggregator(
                AccumulatorFactory accumulatorFactory,
                Step step,
                List<Type> sourceTypes,
                List<Integer> orderByChannels,
                List<SortOrder> orderings,
                Optional<PagesIndex.Factory> pageIndexFactory)
        {
            checkArgument(step != Step.INTERMEDIATE, "intermediate aggregation not supported");

            if (step.isInputRaw()) {
                intermediateChannel = -1;
                aggregation = accumulatorFactory.createAccumulator();
            }
            else {
                checkArgument(accumulatorFactory.getInputChannels().size() == 1, "expected 1 input channel for intermediate aggregation");
                intermediateChannel = accumulatorFactory.getInputChannels().get(0);
                aggregation = accumulatorFactory.createIntermediateAccumulator();
            }
            this.step = step;
            this.orderByOutputChannels = new int[sourceTypes.size()];
            for (int i = 0; i < sourceTypes.size(); i++) {
                orderByOutputChannels[i] = i;
            }
            this.orderByChannels = orderByChannels;
            this.orderings = orderings;
            this.pageIndex = pageIndexFactory.map(value -> value.newPagesIndex(sourceTypes, 10_000));
            this.pageBuilder = new PageBuilder(sourceTypes);
        }

        public Type getType()
        {
            if (step.isOutputPartial()) {
                return aggregation.getIntermediateType();
            }
            else {
                return aggregation.getFinalType();
            }
        }

        public void processPage(Page page)
        {
            if (!this.orderByChannels.isEmpty()) {
                checkArgument(step == Step.SINGLE && pageIndex.isPresent(), "Order by over distributed aggregation is not supported");
                pageIndex.ifPresent(value -> value.addPage(page));
            }
            else if (step.isInputRaw()) {
                aggregation.addInput(page);
            }
            else {
                aggregation.addIntermediate(page.getBlock(intermediateChannel));
            }
        }

        public void processOrderBy()
        {
            if (this.pageIndex.isPresent()) {
                PagesIndex pageIndex = this.pageIndex.get();
                pageIndex.sort(orderByChannels, orderings);
                int currentPosition = 0;
                while (true) {
                    pageBuilder.reset();
                    currentPosition = pageIndex.buildPage(currentPosition, orderByOutputChannels, pageBuilder);
                    if (pageBuilder.isEmpty()) {
                        break;
                    }
                    Page page = pageBuilder.build();
                    aggregation.addInput(page);
                }
            }
        }

        public void evaluate(BlockBuilder blockBuilder)
        {
            if (step.isOutputPartial()) {
                aggregation.evaluateIntermediate(blockBuilder);
            }
            else {
                aggregation.evaluateFinal(blockBuilder);
            }
        }

        public long getEstimatedSize()
        {
            return aggregation.getEstimatedSize();
        }
    }
}
