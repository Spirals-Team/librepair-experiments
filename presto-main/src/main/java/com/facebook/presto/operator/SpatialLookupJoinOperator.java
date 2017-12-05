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
import com.facebook.presto.operator.project.PageProcessor;
import com.facebook.presto.operator.project.PageProcessorOutput;
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.slice.Slice;

import javax.annotation.concurrent.ThreadSafe;

import java.util.List;
import java.util.Optional;

import static com.facebook.presto.geospatial.GeometryUtils.deserialize;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static io.airlift.concurrent.MoreFutures.getDone;

@ThreadSafe
public class SpatialLookupJoinOperator
        implements Operator
{
    public static final class SpatialLookupJoinOperatorFactory
            implements OperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId planNodeId;
        private final List<Type> probeTypes;
        private final List<Integer> probeOutputChannels;
        private final List<Type> probeOutputTypes;
        private final List<Type> buildOutputTypes;
        private final PageProcessor probeExpressionPageProcessor;
        private final PagesSpatialIndexFactory pagesSpatialIndexFactory;

        public SpatialLookupJoinOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                List<Type> probeTypes,
                List<Integer> probeOutputChannels,
                PageProcessor probeExpressionPageProcessor,
                PagesSpatialIndexFactory pagesSpatialIndexFactory)
        {
            this.operatorId = operatorId;
            this.planNodeId = planNodeId;
            this.probeTypes = ImmutableList.copyOf(probeTypes);
            this.probeOutputTypes = probeOutputChannels.stream()
                    .map(probeTypes::get)
                    .collect(toImmutableList());
            this.buildOutputTypes = pagesSpatialIndexFactory.getOutputTypes();
            this.probeOutputChannels = ImmutableList.copyOf(probeOutputChannels);
            this.probeExpressionPageProcessor = probeExpressionPageProcessor;
            this.pagesSpatialIndexFactory = pagesSpatialIndexFactory;
        }

        @Override
        public List<Type> getTypes()
        {
            return ImmutableList.<Type>builder()
                    .addAll(probeOutputTypes)
                    .addAll(buildOutputTypes)
                    .build();
        }

        @Override
        public Operator createOperator(DriverContext driverContext)
        {
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, planNodeId, HashBuilderOperator.class.getSimpleName());
            return new SpatialLookupJoinOperator(operatorContext, getTypes(), probeTypes, probeOutputChannels, probeExpressionPageProcessor, pagesSpatialIndexFactory.createPagesSpatialIndex());
        }

        @Override
        public void noMoreOperators()
        {
        }

        @Override
        public OperatorFactory duplicate()
        {
            return new SpatialLookupJoinOperatorFactory(operatorId, planNodeId, probeTypes, probeOutputChannels, probeExpressionPageProcessor, pagesSpatialIndexFactory);
        }
    }

    private final OperatorContext operatorContext;
    private final List<Type> probeTypes;
    private final List<Integer> probeOutputChannels;
    private final PageProcessor probeExpressionPageProcessor;
    private final ListenableFuture<PagesSpatialIndex> pagesSpatialIndexFuture;
    private final PageBuilder pageBuilder;
    private Page probe;
    private Block probeGeometry;
    private int probePosition;
    private List<Long> joinAddresses;
    private int joinAddressIndex;
    private Page outputPage;
    private boolean finishing;
    private boolean finished;

    public SpatialLookupJoinOperator(
            OperatorContext operatorContext,
            List<Type> outputTypes,
            List<Type> probeTypes,
            List<Integer> probeOutputChannels,
            PageProcessor probeExpressionPageProcessor,
            ListenableFuture<PagesSpatialIndex> pagesSpatialIndexFuture)
    {
        this.operatorContext = operatorContext;
        this.probeTypes = ImmutableList.copyOf(probeTypes);
        this.probeOutputChannels = ImmutableList.copyOf(probeOutputChannels);
        this.probeExpressionPageProcessor = probeExpressionPageProcessor;
        this.pagesSpatialIndexFuture = pagesSpatialIndexFuture;
        this.pageBuilder = new PageBuilder(outputTypes);
    }

    @Override
    public OperatorContext getOperatorContext()
    {
        return operatorContext;
    }

    @Override
    public List<Type> getTypes()
    {
        return probeTypes;
    }

    @Override
    public boolean needsInput()
    {
        return pagesSpatialIndexFuture.isDone() && outputPage == null && probe == null;
    }

    @Override
    public void addInput(Page page)
    {
        Verify.verify(probe == null);
        probe = page;
        probeGeometry = computeProbeGeometry(probe);
        probePosition = 0;

        joinAddresses = null;
        joinAddressIndex = 0;

        processProbe();
        tryBuildOutputPage();
    }

    private void tryBuildOutputPage()
    {
        if (pageBuilder.isFull()) {
            outputPage = pageBuilder.build();
            pageBuilder.reset();
        }
    }

    private void processProbe()
    {
        Verify.verify(probe != null);

        PagesSpatialIndex pagesSpatialIndex = getDone(pagesSpatialIndexFuture);
        for (int position = probePosition; position < probe.getPositionCount(); position++) {
            Slice slice = probeGeometry.getSlice(position, 0, probeGeometry.getSliceLength(position));
            OGCGeometry ogcGeometry = deserialize(slice);

            if (this.joinAddresses == null) {
                joinAddresses = pagesSpatialIndex.findJoinAddresses(ogcGeometry);
                joinAddressIndex = -1;
            }

            for (int joinAddressIndex = this.joinAddressIndex + 1; joinAddressIndex < joinAddresses.size(); joinAddressIndex++) {
                if (pageBuilder.isFull()) {
                    probePosition = position;
                    this.joinAddressIndex = joinAddressIndex;
                    return;
                }

                long joinAddress = joinAddresses.get(joinAddressIndex);

                pageBuilder.declarePosition();
                int outputChannelOffset = 0;
                for (int outputIndex : probeOutputChannels) {
                    Type type = probeTypes.get(outputIndex);
                    Block block = probe.getBlock(outputIndex);
                    type.appendTo(block, position, pageBuilder.getBlockBuilder(outputChannelOffset));
                    outputChannelOffset++;
                }
                pagesSpatialIndex.appendTo(joinAddress, pageBuilder, outputChannelOffset);
            }

            this.joinAddresses = null;
        }

        clearProbe();
    }

    private void clearProbe()
    {
        this.probe = null;
        this.probePosition = 0;
        this.probeGeometry = null;
    }

    private Block computeProbeGeometry(Page page)
    {
        PageProcessorOutput pageProcessorOutput = probeExpressionPageProcessor.process(operatorContext.getSession().toConnectorSession(), operatorContext.getDriverContext().getYieldSignal(), page);
        Verify.verify(pageProcessorOutput.hasNext());
        Optional<Page> pageOptional = pageProcessorOutput.next();
        Verify.verify(pageOptional.isPresent());
        Page geometryPage = pageOptional.get();
        Verify.verify(geometryPage.getBlocks().length == 1);
        return geometryPage.getBlock(0);
    }

    @Override
    public Page getOutput()
    {
        if (outputPage == null && probe != null) {
            processProbe();
        }

        if (outputPage != null) {
            Page page = outputPage;
            outputPage = null;
            return page;
        }

        if (finishing) {
            Page page = pageBuilder.build();
            pageBuilder.reset();
            finished = true;
            return page;
        }

        return null;
    }

    @Override
    public void finish()
    {
        finishing = true;
    }

    @Override
    public boolean isFinished()
    {
        return finished;
    }
}
