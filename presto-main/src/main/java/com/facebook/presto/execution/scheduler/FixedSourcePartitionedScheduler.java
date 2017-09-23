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
package com.facebook.presto.execution.scheduler;

import com.facebook.presto.execution.DriverGroupId;
import com.facebook.presto.execution.RemoteTask;
import com.facebook.presto.execution.SqlStageExecution;
import com.facebook.presto.execution.scheduler.ScheduleResult.BlockedReason;
import com.facebook.presto.metadata.Split;
import com.facebook.presto.operator.PipelineExecutionFlow;
import com.facebook.presto.spi.Node;
import com.facebook.presto.split.SplitSource;
import com.facebook.presto.sql.planner.NodePartitionMap;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.log.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static com.facebook.presto.execution.scheduler.SourcePartitionedScheduler.managedSourcePartitionedScheduler;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Verify.verify;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.MoreCollectors.onlyElement;
import static io.airlift.concurrent.MoreFutures.whenAnyComplete;
import static java.util.Objects.requireNonNull;

public class FixedSourcePartitionedScheduler
        implements StageScheduler
{
    private static final Logger log = Logger.get(FixedSourcePartitionedScheduler.class);

    private final SqlStageExecution stage;
    private final NodePartitionMap partitioning;
    private final List<SourcePartitionedScheduler> sourcePartitionedSchedulers;
    private boolean scheduledTasks;

    public FixedSourcePartitionedScheduler(
            SqlStageExecution stage,
            Map<PlanNodeId, SplitSource> splitSources,
            PipelineExecutionFlow pipelineExecutionFlow,
            List<PlanNodeId> schedulingOrder,
            NodePartitionMap partitioning,
            int splitBatchSize,
            int concurrentJoinGroups,
            NodeSelector nodeSelector)
    {
        requireNonNull(stage, "stage is null");
        requireNonNull(splitSources, "splitSources is null");
        requireNonNull(partitioning, "partitioning is null");

        this.stage = stage;
        this.partitioning = partitioning;

        checkArgument(splitSources.keySet().equals(ImmutableSet.copyOf(schedulingOrder)));

        FixedSplitPlacementPolicy splitPlacementPolicy = new FixedSplitPlacementPolicy(nodeSelector, partitioning, stage::getAllTasks);

        ArrayList<SourcePartitionedScheduler> sourcePartitionedSchedulers = new ArrayList<>();
        OptionalInt driverGroupCount = splitSources.values().stream()
                .map(SplitSource::getDriverGroupCount)
                .distinct()
                .collect(onlyElement());
        checkArgument(
                driverGroupCount.isPresent() == (pipelineExecutionFlow == PipelineExecutionFlow.GROUPED),
                "Driver group count should be present if and only if the execution flow is GROUPED");
        boolean firstDriverGroup = true;
        for (PlanNodeId planNodeId : schedulingOrder) {
            SplitSource splitSource = splitSources.get(planNodeId);
            SourcePartitionedScheduler sourcePartitionedScheduler = managedSourcePartitionedScheduler(
                    stage,
                    planNodeId,
                    splitSource,
                    splitPlacementPolicy,
                    Math.max(splitBatchSize / concurrentJoinGroups, 1));
            sourcePartitionedSchedulers.add(sourcePartitionedScheduler);

            if (firstDriverGroup) {
                if (driverGroupCount.isPresent()) {
                    if (concurrentJoinGroups == -1 || concurrentJoinGroups > driverGroupCount.getAsInt()) {
                        concurrentJoinGroups = driverGroupCount.getAsInt();
                    }
                    int totalDriverGroupCount = driverGroupCount.getAsInt();
                    AtomicInteger scheduledDriverGroupsCount = new AtomicInteger();
                    stage.addCompletedDriverGroupChangeListener(newlyCompletedDriverGroups -> {
                        for (DriverGroupId ignored : newlyCompletedDriverGroups) {
                            scheduleNextDriverGroup(sourcePartitionedScheduler, scheduledDriverGroupsCount, totalDriverGroupCount);
                        }
                    });
                    for (int i = 0; i < concurrentJoinGroups; i++) {
                        scheduleNextDriverGroup(sourcePartitionedScheduler, scheduledDriverGroupsCount, totalDriverGroupCount);
                    }
                }
                else {
                    sourcePartitionedScheduler.startDriverGroups(ImmutableList.of(DriverGroupId.notGrouped()));
                }
            }
            firstDriverGroup = false;
        }
        this.sourcePartitionedSchedulers = sourcePartitionedSchedulers;
    }

    private static void scheduleNextDriverGroup(SourcePartitionedScheduler scheduler, AtomicInteger scheduledDriverGroupCount, int totalDriverGroupCount)
    {
        int nextDriverGroupId = scheduledDriverGroupCount.getAndIncrement();
        if (nextDriverGroupId >= totalDriverGroupCount) {
            return;
        }
        scheduler.startDriverGroups(ImmutableList.of(DriverGroupId.of(nextDriverGroupId)));
    }

    @Override
    public ScheduleResult schedule()
    {
        // schedule a task on every node in the distribution
        List<RemoteTask> newTasks = ImmutableList.of();
        if (!scheduledTasks) {
            newTasks = partitioning.getPartitionToNode().entrySet().stream()
                    .map(entry -> stage.scheduleTask(entry.getValue(), entry.getKey()))
                    .collect(toImmutableList());
            scheduledTasks = true;
        }

        boolean allBlocked = true;
        List<ListenableFuture<?>> blocked = new ArrayList<>();
        BlockedReason blockedReason = BlockedReason.NO_ACTIVE_DRIVER_GROUP;
        int splitsScheduled = 0;

        Iterator<SourcePartitionedScheduler> schedulerIterator = sourcePartitionedSchedulers.iterator();
        List<DriverGroupId> driverGroupsToStart = ImmutableList.of();
        while (schedulerIterator.hasNext()) {
            SourcePartitionedScheduler sourcePartitionedScheduler = schedulerIterator.next();

            sourcePartitionedScheduler.startDriverGroups(driverGroupsToStart);

            ScheduleResult schedule = sourcePartitionedScheduler.schedule();
            splitsScheduled += schedule.getSplitsScheduled();
            if (schedule.getBlockedReason().isPresent()) {
                blocked.add(schedule.getBlocked());
                blockedReason = blockedReason.combineWith(schedule.getBlockedReason().get());
            }
            else {
                verify(schedule.getBlocked().isDone(), "blockedReason not provided when scheduler is blocked");
                allBlocked = false;
            }

            driverGroupsToStart = sourcePartitionedScheduler.drainCompletedDriverGroups();

            if (schedule.isFinished()) {
                schedulerIterator.remove();
                sourcePartitionedScheduler.close();
            }
        }

        if (allBlocked) {
            return new ScheduleResult(sourcePartitionedSchedulers.isEmpty(), newTasks, whenAnyComplete(blocked), blockedReason, splitsScheduled);
        }
        else {
            return new ScheduleResult(sourcePartitionedSchedulers.isEmpty(), newTasks, splitsScheduled);
        }
    }

    @Override
    public void close()
    {
        for (SourcePartitionedScheduler sourcePartitionedScheduler : sourcePartitionedSchedulers) {
            try {
                sourcePartitionedScheduler.close();
            }
            catch (Throwable t) {
                log.warn(t, "Error closing split source");
            }
        }
        sourcePartitionedSchedulers.clear();
    }

    public static class FixedSplitPlacementPolicy
            implements SplitPlacementPolicy
    {
        private final NodeSelector nodeSelector;
        private final NodePartitionMap partitioning;
        private final Supplier<? extends List<RemoteTask>> remoteTasks;

        public FixedSplitPlacementPolicy(NodeSelector nodeSelector,
                NodePartitionMap partitioning,
                Supplier<? extends List<RemoteTask>> remoteTasks)
        {
            this.nodeSelector = nodeSelector;
            this.partitioning = partitioning;
            this.remoteTasks = remoteTasks;
        }

        @Override
        public SplitPlacementResult computeAssignments(Set<Split> splits)
        {
            return nodeSelector.computeAssignments(splits, remoteTasks.get(), partitioning);
        }

        @Override
        public void lockDownNodes()
        {
        }

        @Override
        public List<Node> allNodes()
        {
            return ImmutableList.copyOf(partitioning.getPartitionToNode().values());
        }

        public Node getNodeForBucket(int bucketId)
        {
            return partitioning.getPartitionToNode().get(partitioning.getBucketToPartition()[bucketId]);
        }
    }
}
