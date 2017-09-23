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
package com.facebook.presto.execution;

import com.facebook.presto.ScheduledSplit;
import com.facebook.presto.TaskSource;
import com.facebook.presto.event.query.QueryMonitor;
import com.facebook.presto.execution.StateMachine.StateChangeListener;
import com.facebook.presto.execution.buffer.BufferState;
import com.facebook.presto.execution.buffer.OutputBuffer;
import com.facebook.presto.execution.executor.TaskExecutor;
import com.facebook.presto.execution.executor.TaskHandle;
import com.facebook.presto.operator.Driver;
import com.facebook.presto.operator.DriverContext;
import com.facebook.presto.operator.DriverFactory;
import com.facebook.presto.operator.DriverStats;
import com.facebook.presto.operator.PipelineContext;
import com.facebook.presto.operator.PipelineExecutionFlow;
import com.facebook.presto.operator.TaskContext;
import com.facebook.presto.sql.planner.LocalExecutionPlanner;
import com.facebook.presto.sql.planner.LocalExecutionPlanner.LocalExecutionPlan;
import com.facebook.presto.sql.planner.PlanFragment;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.concurrent.SetThreadName;
import io.airlift.units.Duration;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.facebook.presto.SystemSessionProperties.getInitialSplitsPerNode;
import static com.facebook.presto.SystemSessionProperties.getSplitConcurrencyAdjustmentInterval;
import static com.facebook.presto.execution.SqlTaskExecution.SplitsState.FINISHED;
import static com.facebook.presto.execution.SqlTaskExecution.SplitsState.INITIALIZED;
import static com.facebook.presto.execution.SqlTaskExecution.SplitsState.NO_MORE_SPLITS;
import static com.facebook.presto.execution.SqlTaskExecution.SplitsState.SPLITS_ADDED;
import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Verify.verify;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class SqlTaskExecution
{
    private final TaskId taskId;
    private final TaskStateMachine taskStateMachine;
    private final TaskContext taskContext;
    private final OutputBuffer outputBuffer;

    private final TaskHandle taskHandle;
    private final TaskExecutor taskExecutor;

    private final Executor notificationExecutor;

    private final QueryMonitor queryMonitor;

    private final List<WeakReference<Driver>> drivers = new CopyOnWriteArrayList<>();

    private final Map<PlanNodeId, DriverSplitRunnerFactory> partitionedDriverRunnerFactories;
    private final List<DriverSplitRunnerFactory> unpartitionedGroupedDriverRunnerFactories;
    private final List<DriverSplitRunnerFactory> unpartitionedAllAtOnceDriverRunnerFactories;

    // guarded for update only
    @GuardedBy("this")
    private final ConcurrentMap<PlanNodeId, TaskSource> unpartitionedSources = new ConcurrentHashMap<>();

    @GuardedBy("this")
    private long maxAcknowledgedSplit = Long.MIN_VALUE;

    @GuardedBy("this")
    private final SchedulingDriverGroupManager schedulingDriverGroupManager;

    @GuardedBy("this")
    private final Map<PlanNodeId, SplitsForPlanNode> pendingSplitsMap;

    private final Status status;

    public static SqlTaskExecution createSqlTaskExecution(
            TaskStateMachine taskStateMachine,
            TaskContext taskContext,
            OutputBuffer outputBuffer,
            PlanFragment fragment,
            List<TaskSource> sources,
            LocalExecutionPlanner planner,
            TaskExecutor taskExecutor,
            Executor notificationExecutor,
            QueryMonitor queryMonitor)
    {
        LocalExecutionPlan localExecutionPlan;
        try (SetThreadName ignored = new SetThreadName("Task-%s", taskStateMachine.getTaskId())) {
            try {
                localExecutionPlan = planner.plan(
                        taskContext,
                        fragment.getRoot(),
                        fragment.getSymbols(),
                        fragment.getPartitioningScheme(),
                        fragment.getPipelineExecutionFlow() == PipelineExecutionFlow.GROUPED,
                        fragment.getPartitionedSources(),
                        outputBuffer);

                for (DriverFactory driverFactory : localExecutionPlan.getDriverFactories()) {
                    Optional<PlanNodeId> sourceId = driverFactory.getSourceId();
                    if (sourceId.isPresent() && fragment.isPartitionedSources(sourceId.get())) {
                        checkArgument(fragment.getPipelineExecutionFlow() == driverFactory.getPipelineExecutionFlow(),
                                "Partitioned pipelines are expected to have the same execution flow as the fragment");
                    }
                    else {
                        checkArgument(fragment.getPipelineExecutionFlow() != PipelineExecutionFlow.ALL_AT_ONCE || driverFactory.getPipelineExecutionFlow() == PipelineExecutionFlow.ALL_AT_ONCE,
                                "When fragment execution flow is all-at-once, all pipelines should have all-at-once execution flow");
                    }
                }
            }
            catch (Throwable e) {
                // planning failed
                taskStateMachine.failed(e);
                throw Throwables.propagate(e);
            }
        }
        return createSqlTaskExecution(
                taskStateMachine,
                taskContext,
                outputBuffer,
                sources,
                localExecutionPlan,
                taskExecutor,
                notificationExecutor,
                queryMonitor);
    }

    @VisibleForTesting
    static SqlTaskExecution createSqlTaskExecution(
            TaskStateMachine taskStateMachine,
            TaskContext taskContext,
            OutputBuffer outputBuffer,
            List<TaskSource> sources,
            LocalExecutionPlan localExecutionPlan,
            TaskExecutor taskExecutor,
            Executor notificationExecutor,
            QueryMonitor queryMonitor)
    {
        SqlTaskExecution task = new SqlTaskExecution(
                taskStateMachine,
                taskContext,
                outputBuffer,
                localExecutionPlan,
                taskExecutor,
                queryMonitor,
                notificationExecutor);
        try (SetThreadName ignored = new SetThreadName("Task-%s", task.getTaskId())) {
            task.scheduleUnpartitionedAllAtOnceDriver();
            task.addSources(sources);
            return task;
        }
    }

    private SqlTaskExecution(
            TaskStateMachine taskStateMachine,
            TaskContext taskContext,
            OutputBuffer outputBuffer,
            LocalExecutionPlan localExecutionPlan,
            TaskExecutor taskExecutor,
            QueryMonitor queryMonitor,
            Executor notificationExecutor)
    {
        this.taskStateMachine = requireNonNull(taskStateMachine, "taskStateMachine is null");
        this.taskId = taskStateMachine.getTaskId();
        this.taskContext = requireNonNull(taskContext, "taskContext is null");
        this.outputBuffer = requireNonNull(outputBuffer, "outputBuffer is null");

        this.taskExecutor = requireNonNull(taskExecutor, "driverExecutor is null");
        this.notificationExecutor = requireNonNull(notificationExecutor, "notificationExecutor is null");

        this.queryMonitor = requireNonNull(queryMonitor, "queryMonitor is null");

        try (SetThreadName ignored = new SetThreadName("Task-%s", taskId)) {
            // index driver factories
            ImmutableMap.Builder<PlanNodeId, DriverSplitRunnerFactory> partitionedDriverFactories = ImmutableMap.builder();
            ImmutableMap.Builder<PlanNodeId, SplitsForPlanNode> pendingSplitsMap = ImmutableMap.builder();
            ImmutableList.Builder<DriverSplitRunnerFactory> unpartitionedAllAtOnceDriverFactories = ImmutableList.builder();
            ImmutableList.Builder<DriverSplitRunnerFactory> unpartitionedGroupedDriverFactories = ImmutableList.builder();
            ImmutableMap.Builder<Integer, PipelineExecutionFlow> pipelineIdToExecutionFlow = ImmutableMap.builder();
            ImmutableSet<PlanNodeId> partitionedSources = ImmutableSet.copyOf(localExecutionPlan.getPartitionedSourceOrder());
            for (DriverFactory driverFactory : localExecutionPlan.getDriverFactories()) {
                Optional<PlanNodeId> sourceId = driverFactory.getSourceId();
                pipelineIdToExecutionFlow.put(driverFactory.getPipelineId(), driverFactory.getPipelineExecutionFlow());
                if (sourceId.isPresent() && partitionedSources.contains(sourceId.get())) {
                    partitionedDriverFactories.put(sourceId.get(), new DriverSplitRunnerFactory(driverFactory));
                    pendingSplitsMap.put(sourceId.get(), new SplitsForPlanNode());
                }
                else {
                    switch (driverFactory.getPipelineExecutionFlow()) {
                        case GROUPED:
                            unpartitionedGroupedDriverFactories.add(new DriverSplitRunnerFactory(driverFactory));
                            break;
                        case ALL_AT_ONCE:
                            unpartitionedAllAtOnceDriverFactories.add(new DriverSplitRunnerFactory(driverFactory));
                            break;
                        default:
                            throw new UnsupportedOperationException();
                    }
                }
            }
            this.partitionedDriverRunnerFactories = partitionedDriverFactories.build();
            this.unpartitionedGroupedDriverRunnerFactories = unpartitionedGroupedDriverFactories.build();
            this.unpartitionedAllAtOnceDriverRunnerFactories = unpartitionedAllAtOnceDriverFactories.build();
            this.pendingSplitsMap = pendingSplitsMap.build();
            this.status = new Status(pipelineIdToExecutionFlow.build());
            this.schedulingDriverGroupManager = new SchedulingDriverGroupManager(localExecutionPlan.getPartitionedSourceOrder(), this.status);

            checkArgument(this.partitionedDriverRunnerFactories.keySet().equals(partitionedSources),
                    "Fragment is partitioned, but not all partitioned drivers were found");

            // Pre-register DriverGroups for all-at-once partitioned drivers in case they end up get no splits.
            for (Entry<PlanNodeId, DriverSplitRunnerFactory> entry : this.partitionedDriverRunnerFactories.entrySet()) {
                PlanNodeId planNodeId = entry.getKey();
                DriverSplitRunnerFactory driverSplitRunnerFactory = entry.getValue();
                if (driverSplitRunnerFactory.getPipelineExecutionFlow() == PipelineExecutionFlow.ALL_AT_ONCE) {
                    schedulingDriverGroupManager.addDriverGroupIfAbsent(DriverGroupId.notGrouped());
                    this.pendingSplitsMap.get(planNodeId).getDriverGroup(DriverGroupId.notGrouped());
                }
            }

            // don't register the task if it is already completed (most likely failed during planning above)
            if (!taskStateMachine.getState().isDone()) {
                taskHandle = taskExecutor.addTask(taskId, outputBuffer::getUtilization, getInitialSplitsPerNode(taskContext.getSession()), getSplitConcurrencyAdjustmentInterval(taskContext.getSession()));
                taskStateMachine.addStateChangeListener(new RemoveTaskHandleWhenDone(taskExecutor, taskHandle));
                taskStateMachine.addStateChangeListener(state -> {
                    if (state.isDone()) {
                        for (DriverFactory factory : localExecutionPlan.getDriverFactories()) {
                            factory.noMoreDriver();
                        }
                    }
                });
            }
            else {
                taskHandle = null;
            }

            outputBuffer.addStateChangeListener(new CheckTaskCompletionOnBufferFinish(SqlTaskExecution.this));
        }
    }

    public TaskId getTaskId()
    {
        return taskId;
    }

    public TaskContext getTaskContext()
    {
        return taskContext;
    }

    public void addSources(List<TaskSource> sources)
    {
        requireNonNull(sources, "sources is null");
        checkState(!Thread.holdsLock(this), "Can not add sources while holding a lock on the %s", getClass().getSimpleName());

        try (SetThreadName ignored = new SetThreadName("Task-%s", taskId)) {
            // update our record of sources and schedule drivers for new partitioned splits
            Map<PlanNodeId, TaskSource> updatedUnpartitionedSources = updateSources(sources);

            // tell existing drivers about the new splits; it is safe to update drivers
            // multiple times and out of order because sources contain full record of
            // the unpartitioned splits
            for (WeakReference<Driver> driverReference : drivers) {
                Driver driver = driverReference.get();
                // the driver can be GCed due to a failure or a limit
                if (driver == null) {
                    // remove the weak reference from the list to avoid a memory leak
                    // NOTE: this is a concurrent safe operation on a CopyOnWriteArrayList
                    drivers.remove(driverReference);
                    continue;
                }
                Optional<PlanNodeId> sourceId = driver.getSourceId();
                if (!sourceId.isPresent()) {
                    continue;
                }
                TaskSource sourceUpdate = updatedUnpartitionedSources.get(sourceId.get());
                if (sourceUpdate == null) {
                    continue;
                }
                driver.updateSource(sourceUpdate);
            }

            // we may have transitioned to no more splits, so check for completion
            checkTaskCompletion();
        }
    }

    private synchronized Map<PlanNodeId, TaskSource> updateSources(List<TaskSource> sources)
    {
        Map<PlanNodeId, TaskSource> updatedUnpartitionedSources = new HashMap<>();

        // first remove any split that was already acknowledged
        long currentMaxAcknowledgedSplit = this.maxAcknowledgedSplit;
        sources = sources.stream()
                .map(source -> new TaskSource(
                        source.getPlanNodeId(),
                        source.getSplits().stream()
                                .filter(scheduledSplit -> scheduledSplit.getSequenceId() > currentMaxAcknowledgedSplit)
                                .collect(Collectors.toSet()),
                        // Like splits, noMoreSplitsForDriverGroup could be pruned so that only new items will be processed.
                        // This is not happening here because correctness won't be compromised due to duplicate events for noMoreSplitsForDriverGroup.
                        source.getNoMoreSplitsForDriverGroup(),
                        source.isNoMoreSplits()))
                .collect(toList());

        // update task with new sources
        for (TaskSource source : sources) {
            if (partitionedDriverRunnerFactories.containsKey(source.getPlanNodeId())) {
                schedulePartitionedSource(source);
            }
            else {
                scheduleUnpartitionedSource(source, updatedUnpartitionedSources);
            }
        }

        for (DriverSplitRunnerFactory driverSplitRunnerFactory :
                Iterables.concat(partitionedDriverRunnerFactories.values(), unpartitionedAllAtOnceDriverRunnerFactories, unpartitionedGroupedDriverRunnerFactories)) {
            driverSplitRunnerFactory.closeDriverFactoryIfFullyCreated();
        }

        // update maxAcknowledgedSplit
        maxAcknowledgedSplit = sources.stream()
                .flatMap(source -> source.getSplits().stream())
                .mapToLong(ScheduledSplit::getSequenceId)
                .max()
                .orElse(maxAcknowledgedSplit);
        return updatedUnpartitionedSources;
    }

    @GuardedBy("this")
    private void mergeIntoPendingSplits(PlanNodeId planNodeId, Set<ScheduledSplit> scheduledSplits, Set<DriverGroupId> noMoreSplitsForDriverGroup, boolean noMoreSplits)
    {
        checkHoldsLock();

        DriverSplitRunnerFactory partitionedDriverFactory = partitionedDriverRunnerFactories.get(planNodeId);
        SplitsForPlanNode pendingSplitsForPlanNode = pendingSplitsMap.get(planNodeId);

        for (ScheduledSplit scheduledSplit : scheduledSplits) {
            DriverGroupId driverGroupId = scheduledSplit.getSplit().getDriverGroupId();
            checkDriverGroupId(partitionedDriverFactory.getPipelineExecutionFlow(), driverGroupId);
            pendingSplitsForPlanNode.getDriverGroup(driverGroupId).addSplit(scheduledSplit);
            schedulingDriverGroupManager.addDriverGroupIfAbsent(driverGroupId);
        }
        for (DriverGroupId driverGroupWithNoMoreSplits : noMoreSplitsForDriverGroup) {
            checkDriverGroupId(partitionedDriverFactory.getPipelineExecutionFlow(), driverGroupWithNoMoreSplits);
            pendingSplitsForPlanNode.getDriverGroup(driverGroupWithNoMoreSplits).noMoreSplits();
            schedulingDriverGroupManager.addDriverGroupIfAbsent(driverGroupWithNoMoreSplits);
        }
        if (noMoreSplits) {
            pendingSplitsForPlanNode.setNoMoreSplits();
        }
    }

    // Splits for a particular plan node (all driver groups)
    class SplitsForPlanNode
    {
        private final Map<DriverGroupId, SplitsForDriverGroupInPlanNode> map = new HashMap<>();

        public SplitsForDriverGroupInPlanNode getDriverGroup(DriverGroupId driverGroupId)
        {
            return map.computeIfAbsent(driverGroupId, ignored -> new SplitsForDriverGroupInPlanNode());
        }

        public void setNoMoreSplits()
        {
            for (SplitsForDriverGroupInPlanNode splitsForDriverGroup : map.values()) {
                splitsForDriverGroup.noMoreSplits();
            }
        }
    }

    // Splits for a particular plan node and driver group combination
    class SplitsForDriverGroupInPlanNode
    {
        private Set<ScheduledSplit> splits = new HashSet<>();
        private SplitsState state = INITIALIZED;

        public SplitsState getState()
        {
            return state;
        }

        public void addSplit(ScheduledSplit scheduledSplit)
        {
            if (state == INITIALIZED) {
                state = SPLITS_ADDED;
            }
            splits.add(scheduledSplit);
        }

        public Set<ScheduledSplit> removeAllSplits()
        {
            Set<ScheduledSplit> result = splits;
            splits = new HashSet<>();
            return result;
        }

        public void noMoreSplits()
        {
            if (state == SPLITS_ADDED || state == INITIALIZED) {
                state = NO_MORE_SPLITS;
            }
        }

        public void markAsCleanedUp()
        {
            checkState(splits.isEmpty());
            checkState(state == NO_MORE_SPLITS);
            state = FINISHED;
        }
    }

    enum SplitsState
    {
        INITIALIZED,
        // At least one split has been added to pendingSplits set.
        SPLITS_ADDED,
        // All splits have been received from scheduler.
        // No more splits will be added to the pendingSplits set.
        NO_MORE_SPLITS,
        // All splits has been turned into DriverSplitRunner.
        FINISHED,
    }

    private static class SchedulingDriverGroupManager
    {
        // SchedulingDriverGroupManager only contains partitioned drivers.
        // All the partitioned drivers in a task is guaranteed to have the same pipelineExecutionFlow.

        private final List<PlanNodeId> sourceStartOrder;
        private final Status status;

        private final Map<DriverGroupId, SchedulingDriverGroup> driverGroups = new HashMap<>();
        // driver groups whose scheduling is done (all splits for all plan nodes)
        private final Set<DriverGroupId> completedDriverGroups = new HashSet<>();

        private final Set<PlanNodeId> noMoreSplits = new HashSet<>();

        public SchedulingDriverGroupManager(List<PlanNodeId> sourceStartOrder, Status status)
        {
            this.sourceStartOrder = ImmutableList.copyOf(sourceStartOrder);
            this.status = requireNonNull(status, "status is null");
        }

        public void noMoreSplits(PlanNodeId planNodeId)
        {
            if (noMoreSplits.contains(planNodeId)) {
                return;
            }
            noMoreSplits.add(planNodeId);
            if (noMoreSplits.size() < sourceStartOrder.size()) {
                return;
            }
            checkState(noMoreSplits.size() == sourceStartOrder.size());
            checkState(noMoreSplits.containsAll(sourceStartOrder));
            status.setNoMoreDriverGroups();
        }

        public void addDriverGroupIfAbsent(DriverGroupId driverGroupId)
        {
            if (completedDriverGroups.contains(driverGroupId) || driverGroups.containsKey(driverGroupId)) {
                return;
            }
            checkState(!status.isNoMoreDriverGroups());
            driverGroups.put(driverGroupId, new SchedulingDriverGroup(driverGroupId, sourceStartOrder));
        }

        public Iterable<SchedulingDriverGroup> getActiveDriverGroups()
        {
            // This function returns an iterator that iterates through active driver groups.
            // Before it advances to the next item, it checks whether the previous returned driver group is done scheduling.
            // If so, the completed SchedulingDriverGroup is removed so that it will not be returned again.
            Iterator<SchedulingDriverGroup> driverGroupsIterator = driverGroups.values().iterator();
            return () -> new AbstractIterator<SchedulingDriverGroup>()
            {
                SchedulingDriverGroup lastSchedulingDriverGroup = null;

                @Override
                protected SchedulingDriverGroup computeNext()
                {
                    if (lastSchedulingDriverGroup != null) {
                        if (lastSchedulingDriverGroup.isDone()) {
                            completedDriverGroups.add(lastSchedulingDriverGroup.getDriverGroupId());
                            driverGroupsIterator.remove();
                        }
                    }
                    if (!driverGroupsIterator.hasNext()) {
                        return endOfData();
                    }
                    lastSchedulingDriverGroup = driverGroupsIterator.next();
                    return lastSchedulingDriverGroup;
                }
            };
        }
    }

    private static class SchedulingDriverGroup
    {
        private final DriverGroupId driverGroupId;
        private final List<PlanNodeId> planNodeSchedulingOrder;
        private int schedulingPlanNodeOrdinal;
        private boolean unpartitionedDriversScheduled;

        public SchedulingDriverGroup(DriverGroupId driverGroupId, List<PlanNodeId> planNodeSchedulingOrder)
        {
            this.driverGroupId = requireNonNull(driverGroupId, "driverGroupId is null");
            this.planNodeSchedulingOrder = requireNonNull(planNodeSchedulingOrder, "planNodeSchedulingOrder is null");
            checkArgument(!planNodeSchedulingOrder.isEmpty(), "planNodeSchedulingOrder is empty");
        }

        public DriverGroupId getDriverGroupId()
        {
            return driverGroupId;
        }

        public PlanNodeId getSchedulingPlanNode()
        {
            checkState(!isDone());
            return planNodeSchedulingOrder.get(schedulingPlanNodeOrdinal);
        }

        public void nextPlanNode()
        {
            checkState(!isDone());
            schedulingPlanNodeOrdinal++;
        }

        public boolean isDone()
        {
            return schedulingPlanNodeOrdinal >= planNodeSchedulingOrder.size();
        }

        public boolean getAndSetUnpartitionedDriversScheduled()
        {
            if (unpartitionedDriversScheduled) {
                return true;
            }
            unpartitionedDriversScheduled = true;
            return false;
        }
    }

    private synchronized void schedulePartitionedSource(TaskSource sourceUpdate)
    {
        mergeIntoPendingSplits(sourceUpdate.getPlanNodeId(), sourceUpdate.getSplits(), sourceUpdate.getNoMoreSplitsForDriverGroup(), sourceUpdate.isNoMoreSplits());

        for (SchedulingDriverGroup schedulingDriverGroup : schedulingDriverGroupManager.getActiveDriverGroups()) {
            DriverGroupId driverGroupId = schedulingDriverGroup.getDriverGroupId();

            // Schedule the currently scheduling plan node for each driver group.
            // If the currently scheduling plan node does not match sourceUpdate.getPlanNodeId(),
            // the while-loop below will be a no-op.
            // However, it is possible that the currently scheduling plan node finishes due to the sourceUpdate.
            // In such cases, subsequent plan nodes may get scheduled with previously-provided pending splits.
            while (true) {
                PlanNodeId schedulingPlanNode = schedulingDriverGroup.getSchedulingPlanNode();
                DriverSplitRunnerFactory partitionedDriverRunnerFactory = partitionedDriverRunnerFactories.get(schedulingPlanNode);
                checkDriverGroupId(partitionedDriverRunnerFactory.getPipelineExecutionFlow(), driverGroupId);
                SplitsForPlanNode splitsForPlanNode = pendingSplitsMap.get(schedulingPlanNode);
                SplitsForDriverGroupInPlanNode splitsForDriverGroupInPlanNode = splitsForPlanNode.getDriverGroup(driverGroupId);

                if (!schedulingDriverGroup.getAndSetUnpartitionedDriversScheduled()) {
                    scheduleUnpartitionedGroupedDriver(driverGroupId);
                }
                ImmutableList.Builder<DriverSplitRunner> runners = ImmutableList.builder();
                for (ScheduledSplit scheduledSplit : splitsForDriverGroupInPlanNode.removeAllSplits()) {
                    // create a new driver for the split
                    runners.add(partitionedDriverRunnerFactory.createDriverRunner(scheduledSplit, true, driverGroupId));
                }
                enqueueDriverSplitRunner(false, runners.build());
                if (splitsForDriverGroupInPlanNode.getState() != NO_MORE_SPLITS) {
                    break;
                }
                partitionedDriverRunnerFactory.noMoreDriverRunner(ImmutableList.of(driverGroupId));
                splitsForDriverGroupInPlanNode.markAsCleanedUp();

                schedulingDriverGroup.nextPlanNode();
                if (schedulingDriverGroup.isDone()) {
                    break;
                }
            }
        }

        if (sourceUpdate.isNoMoreSplits()) {
            schedulingDriverGroupManager.noMoreSplits(sourceUpdate.getPlanNodeId());
        }
    }

    private synchronized void scheduleUnpartitionedSource(TaskSource sourceUpdate, Map<PlanNodeId, TaskSource> updatedUnpartitionedSources)
    {
        // create new source
        TaskSource newSource;
        TaskSource currentSource = unpartitionedSources.get(sourceUpdate.getPlanNodeId());
        if (currentSource == null) {
            newSource = sourceUpdate;
        }
        else {
            newSource = currentSource.update(sourceUpdate);
        }

        // only record new source if something changed
        if (newSource != currentSource) {
            unpartitionedSources.put(sourceUpdate.getPlanNodeId(), newSource);
            updatedUnpartitionedSources.put(sourceUpdate.getPlanNodeId(), newSource);
        }
    }

    // This method calls enqueueDriverSplitRunner, which registers a callback with access to this class.
    // The call back is accessed from another thread, so this code can not be placed in the constructor.
    private void scheduleUnpartitionedAllAtOnceDriver()
    {
        // start unpartitioned drivers
        List<DriverSplitRunner> runners = new ArrayList<>();
        for (DriverSplitRunnerFactory driverRunnerFactory : unpartitionedAllAtOnceDriverRunnerFactories) {
            for (int i = 0; i < driverRunnerFactory.getDriverInstances().orElse(1); i++) {
                runners.add(driverRunnerFactory.createDriverRunner(null, false, DriverGroupId.notGrouped()));
            }
        }
        enqueueDriverSplitRunner(true, runners);
        for (DriverSplitRunnerFactory driverRunnerFactory : unpartitionedAllAtOnceDriverRunnerFactories) {
            driverRunnerFactory.noMoreDriverRunner(ImmutableList.of(DriverGroupId.notGrouped()));
            verify(driverRunnerFactory.isNoMoreDriverRunner());
        }
    }

    private synchronized void scheduleUnpartitionedGroupedDriver(DriverGroupId driverGroupId)
    {
        if (!driverGroupId.isGrouped()) {
            // The assertion below can be justified from 2 different perspectives
            // 1. Only all-at-once pipelines can be instantiated with the special un-grouped driver group.
            //   Attempt to do so is illegal (unless the attempt would be a no-op).
            // 2. In each task, all pipelines with a partitioned source must have the same execution flow. i.e. either all of them are grouped, or all of them are all-at-once.
            //   Therefore, if a partitioned driver exists for the special un-grouped driver group, the stage must have all-at-once execution flow.
            //   As a result, it is guaranteed that grouped driver factory does not exist (whether partitioned or un-partitioned).
            checkArgument(unpartitionedGroupedDriverRunnerFactories.isEmpty(), "Instantiating grouped pipeline as ungrouped is not allowed");
            return;
        }

        List<DriverSplitRunner> runners = new ArrayList<>();
        for (DriverSplitRunnerFactory driverSplitRunnerFactory : unpartitionedGroupedDriverRunnerFactories) {
            for (int i = 0; i < driverSplitRunnerFactory.getDriverInstances().orElse(1); i++) {
                runners.add(driverSplitRunnerFactory.createDriverRunner(null, false, driverGroupId));
            }
        }
        enqueueDriverSplitRunner(true, runners);
        for (DriverSplitRunnerFactory driverRunnerFactory : unpartitionedGroupedDriverRunnerFactories) {
            driverRunnerFactory.noMoreDriverRunner(ImmutableList.of(driverGroupId));
        }
    }

    private synchronized void enqueueDriverSplitRunner(boolean forceRunSplit, List<DriverSplitRunner> runners)
    {
        // schedule driver to be executed
        List<ListenableFuture<?>> finishedFutures = taskExecutor.enqueueSplits(taskHandle, forceRunSplit, runners);
        checkState(finishedFutures.size() == runners.size(), "Expected %s futures but got %s", runners.size(), finishedFutures.size());

        // when driver completes, update state and fire events
        for (int i = 0; i < finishedFutures.size(); i++) {
            ListenableFuture<?> finishedFuture = finishedFutures.get(i);
            final DriverSplitRunner splitRunner = runners.get(i);

            // record new driver
            status.incrementRemainingDriver(splitRunner.getDriverGroupId());

            Futures.addCallback(finishedFuture, new FutureCallback<Object>()
            {
                @Override
                public void onSuccess(Object result)
                {
                    try (SetThreadName ignored = new SetThreadName("Task-%s", taskId)) {
                        // record driver is finished
                        status.decrementRemainingDriver(splitRunner.getDriverGroupId());

                        checkTaskCompletion();

                        queryMonitor.splitCompletedEvent(taskId, getDriverStats());
                    }
                }

                @Override
                public void onFailure(Throwable cause)
                {
                    try (SetThreadName ignored = new SetThreadName("Task-%s", taskId)) {
                        taskStateMachine.failed(cause);

                        // record driver is finished
                        status.decrementRemainingDriver(splitRunner.getDriverGroupId());

                        // fire failed event with cause
                        queryMonitor.splitFailedEvent(taskId, getDriverStats(), cause);
                    }
                }

                private DriverStats getDriverStats()
                {
                    DriverContext driverContext = splitRunner.getDriverContext();
                    DriverStats driverStats;
                    if (driverContext != null) {
                        driverStats = driverContext.getDriverStats();
                    }
                    else {
                        // split runner did not start successfully
                        driverStats = new DriverStats();
                    }

                    return driverStats;
                }
            }, notificationExecutor);
        }
    }

    public synchronized Set<PlanNodeId> getNoMoreSplits()
    {
        ImmutableSet.Builder<PlanNodeId> noMoreSplits = ImmutableSet.builder();
        for (Entry<PlanNodeId, DriverSplitRunnerFactory> entry : partitionedDriverRunnerFactories.entrySet()) {
            if (entry.getValue().isNoMoreDriverRunner()) {
                noMoreSplits.add(entry.getKey());
            }
        }
        for (TaskSource taskSource : unpartitionedSources.values()) {
            if (taskSource.isNoMoreSplits()) {
                noMoreSplits.add(taskSource.getPlanNodeId());
            }
        }
        return noMoreSplits.build();
    }

    public synchronized Set<DriverGroupId> getCompletedDriverGroups()
    {
        return status.getCompleteDriverGroups();
    }

    private synchronized void checkTaskCompletion()
    {
        if (taskStateMachine.getState().isDone()) {
            return;
        }

        // are there more partition splits expected?
        for (DriverSplitRunnerFactory driverSplitRunnerFactory : partitionedDriverRunnerFactories.values()) {
            if (!driverSplitRunnerFactory.isNoMoreDriverRunner()) {
                return;
            }
        }
        // do we still have running tasks?
        if (status.getRemainingDriver() != 0) {
            return;
        }

        // no more output will be created
        outputBuffer.setNoMorePages();

        // are there still pages in the output buffer
        if (!outputBuffer.isFinished()) {
            return;
        }

        // Cool! All done!
        taskStateMachine.finished();
    }

    public void cancel()
    {
        // todo this should finish all input sources and let the task finish naturally
        try (SetThreadName ignored = new SetThreadName("Task-%s", taskId)) {
            taskStateMachine.cancel();
        }
    }

    public void fail(Throwable cause)
    {
        try (SetThreadName ignored = new SetThreadName("Task-%s", taskId)) {
            taskStateMachine.failed(cause);
        }
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("taskId", taskId)
                .add("remainingDrivers", status.getRemainingDriver())
                .add("unpartitionedSources", unpartitionedSources)
                .toString();
    }

    private void checkDriverGroupId(PipelineExecutionFlow executionFlow, DriverGroupId driverGroupId)
    {
        if (executionFlow == PipelineExecutionFlow.GROUPED) {
            checkArgument(driverGroupId.isGrouped(), "Expect grouped DriverGroupId for grouped ExecutionFlow. Got un-grouped DriverGroupId.");
        }
        else {
            checkArgument(!driverGroupId.isGrouped(), "Expect un-grouped DriverGroupId for all-at-once ExecutionFlow. Got grouped DriverGroupId.");
        }
    }

    private class DriverSplitRunnerFactory
    {
        private final DriverFactory driverFactory;
        private final PipelineContext pipelineContext;
        private boolean closed;

        private DriverSplitRunnerFactory(DriverFactory driverFactory)
        {
            this.driverFactory = driverFactory;
            this.pipelineContext = taskContext.addPipelineContext(driverFactory.getPipelineId(), driverFactory.isInputDriver(), driverFactory.isOutputDriver());
        }

        public DriverSplitRunner createDriverRunner(@Nullable ScheduledSplit partitionedSplit, boolean partitioned, DriverGroupId driverGroupId)
        {
            checkDriverGroupId(driverFactory.getPipelineExecutionFlow(), driverGroupId);
            status.incrementPendingCreation(pipelineContext.getPipelineId(), driverGroupId);
            // create driver context immediately so the driver existence is recorded in the stats
            // the number of drivers is used to balance work across nodes
            DriverContext driverContext = pipelineContext.addDriverContext(partitioned, driverGroupId);
            return new DriverSplitRunner(this, driverContext, partitionedSplit, driverGroupId);
        }

        public Driver createDriver(DriverContext driverContext, @Nullable ScheduledSplit partitionedSplit)
        {
            Driver driver = driverFactory.createDriver(driverContext);

            // record driver so other threads add unpartitioned sources can see the driver
            // NOTE: this MUST be done before reading unpartitionedSources, so we see a consistent view of the unpartitioned sources
            drivers.add(new WeakReference<>(driver));

            if (partitionedSplit != null) {
                // TableScanOperator requires partitioned split to be added before the first call to process
                driver.updateSource(new TaskSource(partitionedSplit.getPlanNodeId(), ImmutableSet.of(partitionedSplit), true));
            }

            // add unpartitioned sources
            Optional<PlanNodeId> sourceId = driver.getSourceId();
            if (sourceId.isPresent()) {
                TaskSource taskSource = unpartitionedSources.get(sourceId.get());
                if (taskSource != null) {
                    driver.updateSource(taskSource);
                }
            }

            status.decrementPendingCreation(pipelineContext.getPipelineId(), driverContext.getDriverGroup());
            closeDriverFactoryIfFullyCreated();

            return driver;
        }

        public void noMoreDriverRunner(Iterable<DriverGroupId> driverGroups)
        {
            for (DriverGroupId driverGroupId : driverGroups) {
                status.setNoMoreDriverRunner(pipelineContext.getPipelineId(), driverGroupId);
            }
            closeDriverFactoryIfFullyCreated();
        }

        public boolean isNoMoreDriverRunner()
        {
            return status.isNoMoreDriverRunners(pipelineContext.getPipelineId());
        }

        public void closeDriverFactoryIfFullyCreated()
        {
            if (closed) {
                return;
            }
            for (DriverGroupId driverGroupId : status.getAndAcknowledgeDriverGroupsWithNoMoreDrivers(pipelineContext.getPipelineId())) {
                driverFactory.noMoreDriver(driverGroupId);
            }
            if (!isNoMoreDriverRunner() || status.getPendingCreation(pipelineContext.getPipelineId()) != 0) {
                return;
            }
            driverFactory.noMoreDriver();
            closed = true;
        }

        public PipelineExecutionFlow getPipelineExecutionFlow()
        {
            return driverFactory.getPipelineExecutionFlow();
        }

        public OptionalInt getDriverInstances()
        {
            return driverFactory.getDriverInstances();
        }
    }

    private static class DriverSplitRunner
            implements SplitRunner
    {
        private final DriverSplitRunnerFactory driverSplitRunnerFactory;
        private final DriverContext driverContext;
        private final DriverGroupId driverGroupId;

        @GuardedBy("this")
        private boolean closed;

        @Nullable
        private final ScheduledSplit partitionedSplit;

        @GuardedBy("this")
        private Driver driver;

        private DriverSplitRunner(DriverSplitRunnerFactory driverSplitRunnerFactory, DriverContext driverContext, @Nullable ScheduledSplit partitionedSplit, DriverGroupId driverGroupId)
        {
            this.driverSplitRunnerFactory = requireNonNull(driverSplitRunnerFactory, "driverFactory is null");
            this.driverContext = requireNonNull(driverContext, "driverContext is null");
            this.partitionedSplit = partitionedSplit;
            this.driverGroupId = requireNonNull(driverGroupId, "driverGroupId is null");
        }

        public synchronized DriverContext getDriverContext()
        {
            if (driver == null) {
                return null;
            }
            return driver.getDriverContext();
        }

        public DriverGroupId getDriverGroupId()
        {
            return driverGroupId;
        }

        @Override
        public synchronized boolean isFinished()
        {
            if (closed) {
                return true;
            }

            return driver != null && driver.isFinished();
        }

        @Override
        public ListenableFuture<?> processFor(Duration duration)
        {
            Driver driver;
            synchronized (this) {
                // if close() was called before we get here, there's not point in even creating the driver
                if (closed) {
                    return Futures.immediateFuture(null);
                }

                if (this.driver == null) {
                    this.driver = driverSplitRunnerFactory.createDriver(driverContext, partitionedSplit);
                }

                driver = this.driver;
            }

            return driver.processFor(duration);
        }

        @Override
        public String getInfo()
        {
            return (partitionedSplit == null) ? "" : partitionedSplit.getSplit().getInfo().toString();
        }

        @Override
        public void close()
        {
            Driver driver;
            synchronized (this) {
                closed = true;
                driver = this.driver;
            }

            if (driver != null) {
                driver.close();
            }
        }
    }

    private static final class RemoveTaskHandleWhenDone
            implements StateChangeListener<TaskState>
    {
        private final TaskExecutor taskExecutor;
        private final TaskHandle taskHandle;

        private RemoveTaskHandleWhenDone(TaskExecutor taskExecutor, TaskHandle taskHandle)
        {
            this.taskExecutor = requireNonNull(taskExecutor, "taskExecutor is null");
            this.taskHandle = requireNonNull(taskHandle, "taskHandle is null");
        }

        @Override
        public void stateChanged(TaskState newState)
        {
            if (newState.isDone()) {
                taskExecutor.removeTask(taskHandle);
            }
        }
    }

    private static final class CheckTaskCompletionOnBufferFinish
            implements StateChangeListener<BufferState>
    {
        private final WeakReference<SqlTaskExecution> sqlTaskExecutionReference;

        public CheckTaskCompletionOnBufferFinish(SqlTaskExecution sqlTaskExecution)
        {
            // we are only checking for completion of the task, so don't hold up GC if the task is dead
            this.sqlTaskExecutionReference = new WeakReference<>(sqlTaskExecution);
        }

        @Override
        public void stateChanged(BufferState newState)
        {
            if (newState == BufferState.FINISHED) {
                SqlTaskExecution sqlTaskExecution = sqlTaskExecutionReference.get();
                if (sqlTaskExecution != null) {
                    sqlTaskExecution.checkTaskCompletion();
                }
            }
        }
    }

    private void checkHoldsLock()
    {
        // This method serves a similar purpose at runtime as GuardedBy on method serves during static analysis.
        // This method should not have significant performance impact. If it does, it may be reasonably to remove this method.
        // This intentionally does not use checkState.
        if (!Thread.holdsLock(this)) {
            throw new IllegalStateException(format("Thread must hold a lock on the %s", getClass().getSimpleName()));
        }
    }

    private static class Status
    {
        // no more driver runner: true if no more DriverSplitRunners will be created.
        // pending creation: number of created DriverSplitRunners that haven't created underlying Driver.
        // remaining driver: number of created Drivers that haven't yet finished.

        private final int allAtOncePipelineCount;
        private final int groupedPipelineCount;

        // For these 3 perX fields, they are populated lazily. If enumeration operations on the
        // map can lead to side effects, no new entries can be created after such enumeration has
        // happened. Otherwise, the order of entry creation and the enumeration operation will
        // lead to different outcome.
        private final Map<Integer, Map<DriverGroupId, PerPipelineAndDriverGroupStatus>> perPipelineAndDriverGroup;
        private final Map<Integer, PerPipelineStatus> perPipeline;
        private final Map<DriverGroupId, PerDriverGroupStatus> perDriverGroup = new HashMap<>();

        private int overallRemainingDriver;

        private boolean noMoreDriverGroups;

        public Status(Map<Integer, PipelineExecutionFlow> pipelineToExecutionFlow)
        {
            int allAtOncePipelineCount = 0;
            int groupedPipelineCount = 0;
            ImmutableMap.Builder<Integer, Map<DriverGroupId, PerPipelineAndDriverGroupStatus>> perPipelineAndDriverGroup = ImmutableMap.builder();
            ImmutableMap.Builder<Integer, PerPipelineStatus> perPipeline = ImmutableMap.builder();
            for (Entry<Integer, PipelineExecutionFlow> entry : pipelineToExecutionFlow.entrySet()) {
                int pipelineId = entry.getKey();
                PipelineExecutionFlow executionFlow = entry.getValue();
                perPipelineAndDriverGroup.put(pipelineId, new HashMap<>());
                perPipeline.put(pipelineId, new PerPipelineStatus(executionFlow));
                switch (executionFlow) {
                    case ALL_AT_ONCE:
                        allAtOncePipelineCount++;
                        break;
                    case GROUPED:
                        groupedPipelineCount++;
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }
            }
            this.allAtOncePipelineCount = allAtOncePipelineCount;
            this.groupedPipelineCount = groupedPipelineCount;
            this.perPipelineAndDriverGroup = perPipelineAndDriverGroup.build();
            this.perPipeline = perPipeline.build();
        }

        public synchronized void setNoMoreDriverGroups()
        {
            if (noMoreDriverGroups) {
                return;
            }
            noMoreDriverGroups = true;
        }

        public synchronized void setNoMoreDriverRunner(int pipelineId, DriverGroupId driverGroupId)
        {
            if (per(pipelineId, driverGroupId).noMoreDriverRunner) {
                return;
            }
            per(pipelineId, driverGroupId).noMoreDriverRunner = true;
            if (per(pipelineId, driverGroupId).pendingCreation == 0) {
                per(pipelineId).unacknowledgedDriverGroupsWithNoMoreDrivers.add(driverGroupId);
            }
            per(pipelineId).driverGroupsWithNoMoreDriverRunners++;
            per(driverGroupId).pipelinesWithNoMoreDriverRunners++;
        }

        public synchronized void incrementPendingCreation(int pipelineId, DriverGroupId driverGroupId)
        {
            checkState(!per(pipelineId, driverGroupId).noMoreDriverRunner, "Cannot increment pendingCreation for Pipeline %s DriverGroup %s. NoMoreSplits is set.", pipelineId, driverGroupId);
            per(pipelineId, driverGroupId).pendingCreation++;
            per(pipelineId).pendingCreation++;
        }

        public synchronized void decrementPendingCreation(int pipelineId, DriverGroupId driverGroupId)
        {
            checkState(per(pipelineId, driverGroupId).pendingCreation > 0, "Cannot decrement pendingCreation for Pipeline %s DriverGroup %s. Value is 0.", pipelineId, driverGroupId);
            per(pipelineId, driverGroupId).pendingCreation--;
            if (per(pipelineId, driverGroupId).pendingCreation == 0 && per(pipelineId, driverGroupId).noMoreDriverRunner) {
                per(pipelineId).unacknowledgedDriverGroupsWithNoMoreDrivers.add(driverGroupId);
            }
            per(pipelineId).pendingCreation--;
        }

        public synchronized void incrementRemainingDriver(DriverGroupId driverGroupId)
        {
            checkState(!isNoMoreDriverRunners(driverGroupId), "Cannot increment remainingDriver for DriverGroup %s. NoMoreSplits is set.", driverGroupId);
            per(driverGroupId).remainingDriver++;
            overallRemainingDriver++;
        }

        public synchronized void decrementRemainingDriver(DriverGroupId driverGroupId)
        {
            checkState(per(driverGroupId).remainingDriver > 0, "Cannot decrement remainingDriver for DriverGroup %s. Value is 0.", driverGroupId);
            per(driverGroupId).remainingDriver--;
            overallRemainingDriver--;
        }

        public boolean isNoMoreDriverGroups()
        {
            return noMoreDriverGroups;
        }

        public synchronized int getPendingCreation(int pipelineId)
        {
            return per(pipelineId).pendingCreation;
        }

        public synchronized int getRemainingDriver(DriverGroupId driverGroupId)
        {
            return per(driverGroupId).remainingDriver;
        }

        public synchronized int getRemainingDriver()
        {
            return overallRemainingDriver;
        }

        public synchronized boolean isNoMoreDriverRunners(int pipelineId)
        {
            int driverGroupCount;
            switch (per(pipelineId).executionFlow) {
                case ALL_AT_ONCE:
                    // Even if noMoreDriverGroups is not set, ALL_AT_ONCE pipelines can only have 1 driver group by nature.
                    driverGroupCount = 1;
                    break;
                case GROUPED:
                    if (!noMoreDriverGroups) {
                        // There may still still be new driver groups, which means potentially new splits.
                        return false;
                    }
                    if (perDriverGroup.containsKey(DriverGroupId.notGrouped())) {
                        driverGroupCount = perDriverGroup.size() - 1;
                    }
                    else {
                        driverGroupCount = perDriverGroup.size();
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            return per(pipelineId).driverGroupsWithNoMoreDriverRunners == driverGroupCount;
        }

        public synchronized boolean isNoMoreDriverRunners(DriverGroupId driverGroupId)
        {
            if (driverGroupId.isGrouped()) {
                return per(driverGroupId).pipelinesWithNoMoreDriverRunners == groupedPipelineCount;
            }
            else {
                return per(driverGroupId).pipelinesWithNoMoreDriverRunners == allAtOncePipelineCount;
            }
        }

        /**
         * Return driver groups who recently became known to not need any new drivers.
         * Once it is determined that a driver group will not need any new driver groups,
         * the driver group will be returned in the next invocation of this method.
         * Once a driver group is returned, it is considered acknowledged, and will not be returned again.
         * In other words, each driver group will be returned by this method only once.
         */
        public synchronized List<DriverGroupId> getAndAcknowledgeDriverGroupsWithNoMoreDrivers(int pipelineId)
        {
            List<DriverGroupId> result = ImmutableList.copyOf(per(pipelineId).unacknowledgedDriverGroupsWithNoMoreDrivers);
            per(pipelineId).unacknowledgedDriverGroupsWithNoMoreDrivers.clear();
            return result;
        }

        /**
         * Returns the completed driver groups (excluding notGrouped).
         * A driver group is considered complete if all drivers associated with it
         * has completed, and no new drivers associated with it will be created.
         */
        public synchronized Set<DriverGroupId> getCompleteDriverGroups()
        {
            ImmutableSet.Builder<DriverGroupId> result = ImmutableSet.builder();
            for (DriverGroupId driverGroupId : perDriverGroup.keySet()) {
                if (driverGroupId.isGrouped() && isNoMoreDriverRunners(driverGroupId) && getRemainingDriver(driverGroupId) == 0) {
                    result.add(driverGroupId);
                }
            }
            return result.build();
        }

        private PerPipelineAndDriverGroupStatus per(int pipelineId, DriverGroupId driverGroupId)
        {
            return perPipelineAndDriverGroup.get(pipelineId).computeIfAbsent(driverGroupId, ignored -> new PerPipelineAndDriverGroupStatus());
        }

        private PerPipelineStatus per(int pipelineId)
        {
            return perPipeline.get(pipelineId);
        }

        private PerDriverGroupStatus per(DriverGroupId driverGroupId)
        {
            if (perDriverGroup.containsKey(driverGroupId)) {
                return perDriverGroup.get(driverGroupId);
            }
            PerDriverGroupStatus result = new PerDriverGroupStatus();
            perDriverGroup.put(driverGroupId, result);
            return result;
        }
    }

    private static class PerPipelineStatus
    {
        final PipelineExecutionFlow executionFlow;

        int pendingCreation;
        int driverGroupsWithNoMoreDriverRunners;
        List<DriverGroupId> unacknowledgedDriverGroupsWithNoMoreDrivers = new ArrayList<>();

        public PerPipelineStatus(PipelineExecutionFlow executionFlow)
        {
            this.executionFlow = requireNonNull(executionFlow, "executionFlow is null");
        }
    }

    private static class PerDriverGroupStatus
    {
        int remainingDriver;
        int pipelinesWithNoMoreDriverRunners;
    }

    private static class PerPipelineAndDriverGroupStatus
    {
        int pendingCreation;
        boolean noMoreDriverRunner;
    }
}
