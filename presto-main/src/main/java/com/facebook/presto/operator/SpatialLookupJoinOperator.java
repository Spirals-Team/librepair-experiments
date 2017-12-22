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
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.concurrent.ThreadSafe;

import java.util.List;
import java.util.function.BiPredicate;

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
        private final int probeGeometryChannel;
        private final PagesSpatialIndexFactory pagesSpatialIndexFactory;
        private final BiPredicate<OGCGeometry, OGCGeometry> spatialRelationshipTest;

        public SpatialLookupJoinOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                List<Type> probeTypes,
                List<Integer> probeOutputChannels,
                int probeGeometryChannel,
                PagesSpatialIndexFactory pagesSpatialIndexFactory,
                BiPredicate<OGCGeometry, OGCGeometry> spatialRelationshipTest)
        {
            this.operatorId = operatorId;
            this.planNodeId = planNodeId;
            this.probeTypes = ImmutableList.copyOf(probeTypes);
            this.probeOutputTypes = probeOutputChannels.stream()
                    .map(probeTypes::get)
                    .collect(toImmutableList());
            this.buildOutputTypes = pagesSpatialIndexFactory.getOutputTypes();
            this.probeOutputChannels = ImmutableList.copyOf(probeOutputChannels);
            this.probeGeometryChannel = probeGeometryChannel;
            this.pagesSpatialIndexFactory = pagesSpatialIndexFactory;
            this.spatialRelationshipTest = spatialRelationshipTest;
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
            return new SpatialLookupJoinOperator(operatorContext, getTypes(), probeTypes, probeOutputChannels, probeGeometryChannel, pagesSpatialIndexFactory.createPagesSpatialIndex(), spatialRelationshipTest);
        }

        @Override
        public void noMoreOperators()
        {
        }

        @Override
        public OperatorFactory duplicate()
        {
            return new SpatialLookupJoinOperatorFactory(operatorId, planNodeId, probeTypes, probeOutputChannels, probeGeometryChannel, pagesSpatialIndexFactory, spatialRelationshipTest);
        }
    }

    private final OperatorContext operatorContext;
    private final List<Type> probeTypes;
    private final List<Integer> probeOutputChannels;
    private final int probeGeometryChannel;
    private final ListenableFuture<PagesSpatialIndex> pagesSpatialIndexFuture;
    private final BiPredicate<OGCGeometry, OGCGeometry> spatialRelationshipTest;
    private final PageBuilder pageBuilder;
    private Page probe;
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
            int probeGeometryChannel,
            ListenableFuture<PagesSpatialIndex> pagesSpatialIndexFuture,
            BiPredicate<OGCGeometry, OGCGeometry> spatialRelationshipTest)
    {
        this.operatorContext = operatorContext;
        this.probeTypes = ImmutableList.copyOf(probeTypes);
        this.probeOutputChannels = ImmutableList.copyOf(probeOutputChannels);
        this.probeGeometryChannel = probeGeometryChannel;
        this.pagesSpatialIndexFuture = pagesSpatialIndexFuture;
        this.pageBuilder = new PageBuilder(outputTypes);
        this.spatialRelationshipTest = spatialRelationshipTest;
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
            if (this.joinAddresses == null) {
                joinAddresses = pagesSpatialIndex.findJoinAddresses(position, probe, probeGeometryChannel, spatialRelationshipTest);
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
