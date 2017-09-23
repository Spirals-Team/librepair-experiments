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

import com.facebook.presto.OutputBuffers.OutputBufferId;
import com.facebook.presto.ScheduledSplit;
import com.facebook.presto.TaskSource;
import com.facebook.presto.client.NodeVersion;
import com.facebook.presto.connector.ConnectorId;
import com.facebook.presto.event.query.QueryMonitor;
import com.facebook.presto.event.query.QueryMonitorConfig;
import com.facebook.presto.eventlistener.EventListenerManager;
import com.facebook.presto.execution.buffer.BufferResult;
import com.facebook.presto.execution.buffer.BufferState;
import com.facebook.presto.execution.buffer.OutputBuffer;
import com.facebook.presto.execution.buffer.PagesSerdeFactory;
import com.facebook.presto.execution.buffer.PartitionedOutputBuffer;
import com.facebook.presto.execution.buffer.SerializedPage;
import com.facebook.presto.execution.executor.TaskExecutor;
import com.facebook.presto.memory.MemoryPool;
import com.facebook.presto.memory.QueryContext;
import com.facebook.presto.metadata.Split;
import com.facebook.presto.operator.DriverContext;
import com.facebook.presto.operator.DriverFactory;
import com.facebook.presto.operator.Operator;
import com.facebook.presto.operator.OperatorContext;
import com.facebook.presto.operator.OperatorFactory;
import com.facebook.presto.operator.PipelineExecutionFlow;
import com.facebook.presto.operator.SourceOperator;
import com.facebook.presto.operator.SourceOperatorFactory;
import com.facebook.presto.operator.TaskContext;
import com.facebook.presto.operator.TaskOutputOperator.TaskOutputOperatorFactory;
import com.facebook.presto.operator.ValuesOperator.ValuesOperatorFactory;
import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.HostAddress;
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.QueryId;
import com.facebook.presto.spi.UpdatablePageSource;
import com.facebook.presto.spi.block.TestingBlockEncodingSerde;
import com.facebook.presto.spi.connector.ConnectorTransactionHandle;
import com.facebook.presto.spi.memory.MemoryPoolId;
import com.facebook.presto.spi.type.TestingTypeManager;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spiller.SpillSpaceTracker;
import com.facebook.presto.sql.planner.LocalExecutionPlanner.LocalExecutionPlan;
import com.facebook.presto.sql.planner.plan.PlanNodeId;
import com.facebook.presto.testing.TestingTransactionHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.airlift.json.ObjectMapperProvider;
import io.airlift.node.NodeInfo;
import io.airlift.units.DataSize;
import io.airlift.units.Duration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.facebook.presto.OutputBuffers.BufferType.PARTITIONED;
import static com.facebook.presto.OutputBuffers.createInitialEmptyOutputBuffers;
import static com.facebook.presto.SessionTestUtils.TEST_SESSION;
import static com.facebook.presto.block.BlockAssertions.createStringSequenceBlock;
import static com.facebook.presto.block.BlockAssertions.createStringsBlock;
import static com.facebook.presto.execution.TaskTestUtils.TABLE_SCAN_NODE_ID;
import static com.facebook.presto.execution.buffer.BufferState.OPEN;
import static com.facebook.presto.execution.buffer.BufferState.TERMINAL_BUFFER_STATES;
import static com.facebook.presto.spi.type.VarcharType.VARCHAR;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static io.airlift.concurrent.MoreFutures.getFutureValue;
import static io.airlift.concurrent.Threads.threadsNamed;
import static io.airlift.json.JsonCodec.jsonCodec;
import static io.airlift.units.DataSize.Unit.GIGABYTE;
import static io.airlift.units.DataSize.Unit.MEGABYTE;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test(singleThreaded = true)
public class TestSqlTaskExecution
{
    private static final OutputBufferId OUTPUT_BUFFER_ID = new OutputBufferId(0);
    private static final ConnectorId CONNECTOR_ID = new ConnectorId("test");
    private static final ConnectorTransactionHandle TRANSACTION_HANDLE = TestingTransactionHandle.create();
    private static final Duration ASSERT_WAIT_TIMEOUT = new Duration(1, HOURS);

    @DataProvider
    public static Object[][] executionFlows()
    {
        return new Object[][]{{PipelineExecutionFlow.ALL_AT_ONCE}, {PipelineExecutionFlow.GROUPED}};
    }

    @Test(dataProvider = "executionFlows")
    public void testSimple(PipelineExecutionFlow executionFlow)
            throws Exception
    {
        ScheduledExecutorService taskNotificationExecutor = newScheduledThreadPool(10, threadsNamed("task-notification-%s"));
        ScheduledExecutorService driverYieldExecutor = newScheduledThreadPool(2, threadsNamed("driver-yield-%s"));
        TaskExecutor taskExecutor = new TaskExecutor(5, 10);
        taskExecutor.start();

        try {
            TaskStateMachine taskStateMachine = new TaskStateMachine(TaskId.valueOf("task-id"), taskNotificationExecutor);
            PartitionedOutputBuffer outputBuffer = newTestingOutputBuffer(taskNotificationExecutor);
            OutputBufferConsumer outputBufferConsumer = new OutputBufferConsumer(outputBuffer, OUTPUT_BUFFER_ID);

            //
            // test initialization: 1 driver factory
            // * all-at-once/grouped: scan -> task output
            TestingScanOperatorFactory testingScanOperatorFactory = new TestingScanOperatorFactory(0, TABLE_SCAN_NODE_ID, ImmutableList.of(VARCHAR));
            TaskOutputOperatorFactory taskOutputOperatorFactory = new TaskOutputOperatorFactory(
                    1,
                    TABLE_SCAN_NODE_ID,
                    outputBuffer,
                    Function.identity(),
                    new PagesSerdeFactory(new TestingBlockEncodingSerde(new TestingTypeManager()), false));
            LocalExecutionPlan localExecutionPlan = new LocalExecutionPlan(
                    ImmutableList.of(new DriverFactory(
                            0,
                            true,
                            true,
                            ImmutableList.of(testingScanOperatorFactory, taskOutputOperatorFactory),
                            OptionalInt.empty(),
                            executionFlow)),
                    ImmutableList.of(TABLE_SCAN_NODE_ID));
            SqlTaskExecution sqlTaskExecution = SqlTaskExecution.createSqlTaskExecution(
                    taskStateMachine,
                    newTestingTaskContext(taskNotificationExecutor, driverYieldExecutor, taskStateMachine),
                    outputBuffer,
                    ImmutableList.of(),
                    localExecutionPlan,
                    taskExecutor,
                    taskNotificationExecutor,
                    new QueryMonitor(new ObjectMapperProvider().get(), jsonCodec(StageInfo.class), new EventListenerManager(), new NodeInfo("test"), new NodeVersion("testVersion"), new QueryMonitorConfig()));

            //
            // test body
            assertEquals(taskStateMachine.getState(), TaskState.RUNNING);

            switch (executionFlow) {
                case ALL_AT_ONCE:
                    // add source for pipeline
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            TABLE_SCAN_NODE_ID,
                            ImmutableSet.of(newScheduledSplit(0, TABLE_SCAN_NODE_ID, DriverGroupId.notGrouped(), 100000, 100123)),
                            false)));
                    // assert that partial task result is produced
                    outputBufferConsumer.consume(123, ASSERT_WAIT_TIMEOUT);

                    // pause operator execution to make sure that
                    // * operatorFactory will be closed even though operator can't execute
                    // * completedDriverGroups will NOT include the newly scheduled driver group while pause is in place
                    testingScanOperatorFactory.getPauser().pause();
                    // add source for pipeline, mark as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            TABLE_SCAN_NODE_ID,
                            ImmutableSet.of(
                                    newScheduledSplit(1, TABLE_SCAN_NODE_ID, DriverGroupId.notGrouped(), 200000, 200300),
                                    newScheduledSplit(2, TABLE_SCAN_NODE_ID, DriverGroupId.notGrouped(), 300000, 300200)),
                            true)));
                    // assert that pipeline will have no more drivers
                    waitUntilEquals(testingScanOperatorFactory::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    // assert that no DriverGroup is fully completed
                    assertEquals(sqlTaskExecution.getCompletedDriverGroups(), ImmutableSet.of());
                    // resume operator execution
                    testingScanOperatorFactory.getPauser().resume();
                    // assert that task result is produced
                    outputBufferConsumer.consume(500, ASSERT_WAIT_TIMEOUT);
                    outputBufferConsumer.assertBufferComplete(ASSERT_WAIT_TIMEOUT);

                    break;
                case GROUPED:
                    // add source for pipeline (driver group [1, 5]), mark driver group [1] as noMoreSplits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            TABLE_SCAN_NODE_ID,
                            ImmutableSet.of(
                                    newScheduledSplit(0, TABLE_SCAN_NODE_ID, DriverGroupId.of(1), 0, 1),
                                    newScheduledSplit(1, TABLE_SCAN_NODE_ID, DriverGroupId.of(5), 100000, 100010)),
                            ImmutableSet.of(DriverGroupId.of(1)),
                            false)));
                    // assert that pipeline will have no more drivers for driver group [1]
                    waitUntilEquals(testingScanOperatorFactory::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(1)), ASSERT_WAIT_TIMEOUT);
                    // assert that partial result is produced for both driver groups
                    outputBufferConsumer.consume(11, ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [1] is fully completed
                    waitUntilEquals(sqlTaskExecution::getCompletedDriverGroups, ImmutableSet.of(DriverGroupId.of(1)), ASSERT_WAIT_TIMEOUT);

                    // pause operator execution to make sure that
                    // * operatorFactory will be closed even though operator can't execute
                    // * completedDriverGroups will NOT include the newly scheduled driver group while pause is in place
                    testingScanOperatorFactory.getPauser().pause();
                    // add source for pipeline (driver group [5]), mark driver group [5] as noMoreSplits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            TABLE_SCAN_NODE_ID,
                            ImmutableSet.of(newScheduledSplit(2, TABLE_SCAN_NODE_ID, DriverGroupId.of(5), 200000, 200300)),
                            ImmutableSet.of(DriverGroupId.of(5)),
                            false)));
                    // assert that pipeline will have no more drivers for driver group [1, 5]
                    waitUntilEquals(testingScanOperatorFactory::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(1), DriverGroupId.of(5)), ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [5] is NOT YET fully completed
                    assertEquals(sqlTaskExecution.getCompletedDriverGroups(), ImmutableSet.of(DriverGroupId.of(1)));
                    // resume operator execution
                    testingScanOperatorFactory.getPauser().resume();
                    // assert that partial result is produced
                    outputBufferConsumer.consume(300, ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [1, 5] is fully completed
                    waitUntilEquals(sqlTaskExecution::getCompletedDriverGroups, ImmutableSet.of(DriverGroupId.of(1), DriverGroupId.of(5)), ASSERT_WAIT_TIMEOUT);

                    // pause operator execution to make sure that
                    testingScanOperatorFactory.getPauser().pause();
                    // add source for pipeline (driver group [7]), mark pipeline as noMoreSplits without explicitly marking driver group 7
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            TABLE_SCAN_NODE_ID,
                            ImmutableSet.of(
                                    newScheduledSplit(3, TABLE_SCAN_NODE_ID, DriverGroupId.of(7), 300000, 300045),
                                    newScheduledSplit(4, TABLE_SCAN_NODE_ID, DriverGroupId.of(7), 400000, 400054)),
                            ImmutableSet.of(),
                            true)));
                    // assert that pipeline will have no more drivers for driver group [1, 5, 7]
                    waitUntilEquals(testingScanOperatorFactory::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(1), DriverGroupId.of(5), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    // assert that pipeline will have no more drivers
                    waitUntilEquals(testingScanOperatorFactory::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [1, 5] is fully completed
                    assertEquals(sqlTaskExecution.getCompletedDriverGroups(), ImmutableSet.of(DriverGroupId.of(1), DriverGroupId.of(5)));
                    // resume operator execution
                    testingScanOperatorFactory.getPauser().resume();
                    // assert that result is produced
                    outputBufferConsumer.consume(99, ASSERT_WAIT_TIMEOUT);
                    outputBufferConsumer.assertBufferComplete(ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [1, 5, 7] is fully completed
                    waitUntilEquals(sqlTaskExecution::getCompletedDriverGroups, ImmutableSet.of(DriverGroupId.of(1), DriverGroupId.of(5), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);

                    break;
                default:
                    throw new UnsupportedOperationException();
            }

            outputBufferConsumer.abort(); // complete the task by calling abort on it
            TaskState taskState = taskStateMachine.getStateChange(TaskState.RUNNING).get(10, SECONDS);
            assertEquals(taskState, TaskState.FINISHED);
        }
        finally {
            taskExecutor.stop();
            taskNotificationExecutor.shutdownNow();
            driverYieldExecutor.shutdown();
        }
    }

    @Test(dataProvider = "executionFlows")
    public void testComplex(PipelineExecutionFlow executionFlow)
            throws Exception
    {
        ScheduledExecutorService taskNotificationExecutor = newScheduledThreadPool(10, threadsNamed("task-notification-%s"));
        ScheduledExecutorService driverYieldExecutor = newScheduledThreadPool(2, threadsNamed("driver-yield-%s"));
        TaskExecutor taskExecutor = new TaskExecutor(5, 10);
        taskExecutor.start();

        try {
            TaskStateMachine taskStateMachine = new TaskStateMachine(TaskId.valueOf("task-id"), taskNotificationExecutor);
            PartitionedOutputBuffer outputBuffer = newTestingOutputBuffer(taskNotificationExecutor);
            OutputBufferConsumer outputBufferConsumer = new OutputBufferConsumer(outputBuffer, OUTPUT_BUFFER_ID);

            // test initialization: 1 driver factory
            // 0. all-at-once/grouped: scan0 -> union-joinA -> union-joinC -> task output
            // 1. all-at-once/grouped: values1 -> union-joinB -> buildA (values1 is empty. This is effectively exchange-sinkB -> build1)
            // 2. all-at-once/grouped: scan2 -> buildB
            // 3. all-at-once/all-at-once: values3 -> buildC
            //
            // In the case of all-at-once test, this covers: 1) partitioned all-at-once 2) unpartitioned all-at-once.
            // This is all the combination a task can have if it has all-at-once execution flow.
            // In the case of grouped test, this covers: 1) partitioned grouped 2) unpartitioned grouped 3) unpartitioned ungrouped.
            // This is all the combination a task can have if it has grouped execution flow.
            // A single task can never have all 4 pipe.
            PlanNodeId scan0NodeId = new PlanNodeId("scan-0");
            PlanNodeId values1NodeId = new PlanNodeId("values-1");
            PlanNodeId scan2NodeId = new PlanNodeId("scan-2");
            PlanNodeId values3NodeId = new PlanNodeId("values-3");
            PlanNodeId joinANodeId = new PlanNodeId("join-a");
            PlanNodeId joinBNodeId = new PlanNodeId("join-b");
            PlanNodeId joinCNodeId = new PlanNodeId("join-c");
            BuildStates buildStatesA = new BuildStates(executionFlow);
            BuildStates buildStatesB = new BuildStates(executionFlow);
            BuildStates buildStatesC = new BuildStates(PipelineExecutionFlow.ALL_AT_ONCE);
            TestingScanOperatorFactory scanOperatorFactory0 = new TestingScanOperatorFactory(1, scan0NodeId, ImmutableList.of(VARCHAR));
            ValuesOperatorFactory valuesOperatorFactory1 = new ValuesOperatorFactory(
                    101,
                    values1NodeId,
                    ImmutableList.of(VARCHAR),
                    ImmutableList.of(new Page(createStringsBlock("multiplier1"))));
            TestingScanOperatorFactory scanOperatorFactory2 = new TestingScanOperatorFactory(201, scan2NodeId, ImmutableList.of(VARCHAR));
            ValuesOperatorFactory valuesOperatorFactory3 = new ValuesOperatorFactory(
                    301,
                    values3NodeId,
                    ImmutableList.of(VARCHAR),
                    ImmutableList.of(new Page(createStringsBlock("x", "y", "multiplier3"))));
            TaskOutputOperatorFactory taskOutputOperatorFactory = new TaskOutputOperatorFactory(
                    4,
                    joinCNodeId,
                    outputBuffer,
                    Function.identity(),
                    new PagesSerdeFactory(new TestingBlockEncodingSerde(new TestingTypeManager()), false));
            TestingUnionJoinOperatorFactory joinOperatorFactoryA = new TestingUnionJoinOperatorFactory(2, joinANodeId, buildStatesA);
            TestingUnionJoinOperatorFactory joinOperatorFactoryB = new TestingUnionJoinOperatorFactory(102, joinBNodeId, buildStatesB);
            TestingUnionJoinOperatorFactory joinOperatorFactoryC = new TestingUnionJoinOperatorFactory(3, joinCNodeId, buildStatesC);
            TestingBuildOperatorFactory buildOperatorFactoryA = new TestingBuildOperatorFactory(103, joinANodeId, buildStatesA);
            TestingBuildOperatorFactory buildOperatorFactoryB = new TestingBuildOperatorFactory(202, joinBNodeId, buildStatesB);
            TestingBuildOperatorFactory buildOperatorFactoryC = new TestingBuildOperatorFactory(302, joinCNodeId, buildStatesC);

            LocalExecutionPlan localExecutionPlan = new LocalExecutionPlan(
                    ImmutableList.of(
                            new DriverFactory(
                                    0,
                                    true,
                                    true,
                                    ImmutableList.of(scanOperatorFactory0, joinOperatorFactoryA, joinOperatorFactoryC, taskOutputOperatorFactory),
                                    OptionalInt.empty(),
                                    executionFlow),
                            new DriverFactory(
                                    1,
                                    false,
                                    false,
                                    ImmutableList.of(valuesOperatorFactory1, joinOperatorFactoryB, buildOperatorFactoryA),
                                    OptionalInt.empty(),
                                    executionFlow),
                            new DriverFactory(
                                    2,
                                    true,
                                    false,
                                    ImmutableList.of(scanOperatorFactory2, buildOperatorFactoryB),
                                    OptionalInt.empty(),
                                    executionFlow),
                            new DriverFactory(
                                    3,
                                    false,
                                    false,
                                    ImmutableList.of(valuesOperatorFactory3, buildOperatorFactoryC),
                                    OptionalInt.empty(),
                                    PipelineExecutionFlow.ALL_AT_ONCE)),
                    ImmutableList.of(scan2NodeId, scan0NodeId));
            SqlTaskExecution sqlTaskExecution = SqlTaskExecution.createSqlTaskExecution(
                    taskStateMachine,
                    newTestingTaskContext(taskNotificationExecutor, driverYieldExecutor, taskStateMachine),
                    outputBuffer,
                    ImmutableList.of(),
                    localExecutionPlan,
                    taskExecutor,
                    taskNotificationExecutor,
                    new QueryMonitor(new ObjectMapperProvider().get(), jsonCodec(StageInfo.class), new EventListenerManager(), new NodeInfo("test"), new NodeVersion("testVersion"), new QueryMonitorConfig()));

            //
            // test body
            assertEquals(taskStateMachine.getState(), TaskState.RUNNING);

            switch (executionFlow) {
                case ALL_AT_ONCE:
                    // assert that pipeline 1 and pipeline 3 will have no more drivers
                    // (Unpartitioned all-at-once pipelines can have all driver instance created up front.)
                    waitUntilEquals(joinOperatorFactoryB::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryA::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryC::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);

                    // add source for pipeline 2, and mark as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan2NodeId,
                            ImmutableSet.of(
                                    newScheduledSplit(0, scan2NodeId, DriverGroupId.notGrouped(), 100000, 100001),
                                    newScheduledSplit(1, scan2NodeId, DriverGroupId.notGrouped(), 300000, 300002)),
                            false)));
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan2NodeId,
                            ImmutableSet.of(newScheduledSplit(2, scan2NodeId, DriverGroupId.notGrouped(), 300000, 300002)),
                            true)));
                    // assert that pipeline 2 will have no more drivers
                    waitUntilEquals(scanOperatorFactory2::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryB::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);

                    // pause operator execution to make sure that
                    // * operatorFactory will be closed even though operator can't execute
                    // * completedDriverGroups will NOT include the newly scheduled driver group while pause is in place
                    scanOperatorFactory0.getPauser().pause();

                    // add source for pipeline 0, mark as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan0NodeId,
                            ImmutableSet.of(newScheduledSplit(3, scan0NodeId, DriverGroupId.notGrouped(), 400000, 400100)),
                            true)));
                    // assert that pipeline 0 will have no more drivers
                    waitUntilEquals(scanOperatorFactory0::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryA::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryC::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    // assert that no DriverGroup is fully completed
                    assertEquals(sqlTaskExecution.getCompletedDriverGroups(), ImmutableSet.of());
                    // resume operator execution
                    scanOperatorFactory0.getPauser().resume();
                    // assert that task result is produced
                    outputBufferConsumer.consume(100 * 5 * 3, ASSERT_WAIT_TIMEOUT);
                    outputBufferConsumer.assertBufferComplete(ASSERT_WAIT_TIMEOUT);

                    break;
                case GROUPED:
                    // assert that pipeline 3 will have no more drivers
                    // (Unpartitioned all-at-once pipelines can have all driver instances created up front.)
                    waitUntilEquals(buildOperatorFactoryC::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);

                    // add source for pipeline 2 driver group 3, and mark driver group 3 as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan2NodeId,
                            ImmutableSet.of(
                                    newScheduledSplit(0, scan2NodeId, DriverGroupId.of(3), 0, 1),
                                    newScheduledSplit(1, scan2NodeId, DriverGroupId.of(3), 100000, 100002)),
                            false)));
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan2NodeId,
                            ImmutableSet.of(newScheduledSplit(2, scan2NodeId, DriverGroupId.of(3), 200000, 200002)),
                            ImmutableSet.of(DriverGroupId.of(3)),
                            false)));
                    // assert that pipeline 2 driver group [3] will have no more drivers
                    waitUntilEquals(scanOperatorFactory2::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryB::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);

                    // pause operator execution to make sure that
                    // * completedDriverGroups will NOT include the newly scheduled driver group while pause is in place
                    scanOperatorFactory0.getPauser().pause();

                    // add source for pipeline 0 driver group 3, and mark driver group 3 as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan0NodeId,
                            ImmutableSet.of(newScheduledSplit(3, scan0NodeId, DriverGroupId.of(3), 300000, 300010)),
                            ImmutableSet.of(DriverGroupId.of(3)),
                            false)));
                    // assert that pipeline 0 driver group [3] will have no more drivers
                    waitUntilEquals(scanOperatorFactory0::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryA::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryC::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);
                    // assert that pipeline 1 driver group [3] will have no more drivers
                    waitUntilEquals(joinOperatorFactoryB::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryA::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);
                    // assert that no DriverGroup is fully completed
                    assertEquals(sqlTaskExecution.getCompletedDriverGroups(), ImmutableSet.of());
                    // resume operator execution
                    scanOperatorFactory0.getPauser().resume();
                    // assert that partial task result is produced
                    outputBufferConsumer.consume(10 * 5 * 3, ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [3] is fully completed
                    waitUntilEquals(sqlTaskExecution::getCompletedDriverGroups, ImmutableSet.of(DriverGroupId.of(3)), ASSERT_WAIT_TIMEOUT);

                    // add source for pipeline 2 driver group 7, and mark pipeline as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan2NodeId,
                            ImmutableSet.of(newScheduledSplit(4, scan2NodeId, DriverGroupId.of(7), 400000, 400002)),
                            ImmutableSet.of(DriverGroupId.of(7)),
                            true)));
                    // assert that pipeline 2 driver group [3, 7] will have no more drivers
                    waitUntilEquals(scanOperatorFactory2::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryB::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);

                    // pause operator execution to make sure that
                    // * operatorFactory will be closed even though operator can't execute
                    // * completedDriverGroups will NOT include the newly scheduled driver group while pause is in place
                    scanOperatorFactory0.getPauser().pause();

                    // add source for pipeline 0 driver group 7, mark pipeline as no more splits
                    sqlTaskExecution.addSources(ImmutableList.of(new TaskSource(
                            scan0NodeId,
                            ImmutableSet.of(newScheduledSplit(5, scan0NodeId, DriverGroupId.of(7), 500000, 501000)),
                            ImmutableSet.of(DriverGroupId.of(7)),
                            true)));
                    // assert that pipeline 0 driver group [3, 7] will have no more drivers
                    waitUntilEquals(scanOperatorFactory0::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryA::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryC::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    // assert that pipeline 0 will have no more drivers
                    waitUntilEquals(scanOperatorFactory0::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryA::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(joinOperatorFactoryC::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    // assert that pipeline 1 driver group [3, 7] will have no more drivers
                    waitUntilEquals(joinOperatorFactoryB::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryA::getDriverGroupsWithNoMoreOperators, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);
                    // assert that pipeline 1 will have no more drivers
                    // (Unpartitioned grouped pipelines will have no more driver instances when there can be no more driver groups.)
                    waitUntilEquals(joinOperatorFactoryB::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryA::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    // assert that pipeline 2 will have no more drivers
                    // note: One could argue that this should have happened as soon as pipeline 2 driver group 7 is marked as noMoreSplits.
                    //       This is not how SqlTaskExecution is currently implemented. And such a delay in closing DriverFactory does not matter much.
                    waitUntilEquals(scanOperatorFactory2::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    waitUntilEquals(buildOperatorFactoryB::isOverallNoMoreOperators, true, ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [3] (but not 7) is fully completed
                    assertEquals(sqlTaskExecution.getCompletedDriverGroups(), ImmutableSet.of(DriverGroupId.of(3)));
                    // resume operator execution
                    scanOperatorFactory0.getPauser().resume();
                    // assert that partial task result is produced
                    outputBufferConsumer.consume(1000 * 2 * 3, ASSERT_WAIT_TIMEOUT);
                    outputBufferConsumer.assertBufferComplete(ASSERT_WAIT_TIMEOUT);
                    // assert that driver group [3, 7] is fully completed
                    waitUntilEquals(sqlTaskExecution::getCompletedDriverGroups, ImmutableSet.of(DriverGroupId.of(3), DriverGroupId.of(7)), ASSERT_WAIT_TIMEOUT);

                    break;
                default:
                    throw new UnsupportedOperationException();
            }

            outputBufferConsumer.abort(); // complete the task by calling abort on it
            TaskState taskState = taskStateMachine.getStateChange(TaskState.RUNNING).get(10, SECONDS);
            assertEquals(taskState, TaskState.FINISHED);
        }
        finally {
            taskExecutor.stop();
            taskNotificationExecutor.shutdownNow();
            driverYieldExecutor.shutdown();
        }
    }

    private TaskContext newTestingTaskContext(ScheduledExecutorService taskNotificationExecutor, ScheduledExecutorService driverYieldExecutor, TaskStateMachine taskStateMachine)
    {
        QueryContext queryContext = new QueryContext(
                new QueryId("queryid"),
                new DataSize(1, MEGABYTE),
                new MemoryPool(new MemoryPoolId("test"), new DataSize(1, GIGABYTE)), new MemoryPool(new MemoryPoolId("testSystem"), new DataSize(1, GIGABYTE)),
                taskNotificationExecutor,
                driverYieldExecutor,
                new DataSize(1, MEGABYTE),
                new SpillSpaceTracker(new DataSize(1, GIGABYTE)));
        return queryContext.addTaskContext(taskStateMachine, TEST_SESSION, false, false);
    }

    private PartitionedOutputBuffer newTestingOutputBuffer(ScheduledExecutorService taskNotificationExecutor)
    {
        return new PartitionedOutputBuffer(
                "task-id",
                new StateMachine<>("bufferState", taskNotificationExecutor, OPEN, TERMINAL_BUFFER_STATES),
                createInitialEmptyOutputBuffers(PARTITIONED)
                        .withBuffer(OUTPUT_BUFFER_ID, 0)
                        .withNoMoreBufferIds(),
                new DataSize(1, MEGABYTE),
                ignored -> {},
                taskNotificationExecutor);
    }

    private <T> void waitUntilEquals(Supplier<T> actualSupplier, T expected, Duration timeout)
    {
        long nanoUntil = System.nanoTime() + timeout.toMillis() * 1_000_000;
        while (System.nanoTime() - nanoUntil < 0) {
            if (expected.equals(actualSupplier.get())) {
                return;
            }
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                // do nothing
            }
        }
        assertEquals(actualSupplier.get(), expected);
    }

    private static class OutputBufferConsumer
    {
        private final OutputBuffer outputBuffer;
        private final OutputBufferId outputBufferId;
        private int sequenceId;
        private int surplusPositions;
        private boolean bufferComplete;

        public OutputBufferConsumer(OutputBuffer outputBuffer, OutputBufferId outputBufferId)
        {
            this.outputBuffer = outputBuffer;
            this.outputBufferId = outputBufferId;
        }

        public void consume(int positions, Duration timeout)
                throws ExecutionException, InterruptedException, TimeoutException
        {
            long nanoUntil = System.nanoTime() + timeout.toMillis() * 1_000_000;
            surplusPositions -= positions;
            while (surplusPositions < 0) {
                assertFalse(bufferComplete, "bufferComplete is set before enough positions are consumed");
                BufferResult results = outputBuffer.get(outputBufferId, sequenceId, new DataSize(1, MEGABYTE)).get(nanoUntil - System.nanoTime(), TimeUnit.NANOSECONDS);
                bufferComplete = results.isBufferComplete();
                for (SerializedPage serializedPage : results.getSerializedPages()) {
                    surplusPositions += serializedPage.getPositionCount();
                }
                sequenceId += results.getSerializedPages().size();
            }
        }

        public void assertBufferComplete(Duration timeout)
                throws InterruptedException, ExecutionException, TimeoutException
        {
            assertEquals(surplusPositions, 0);
            long nanoUntil = System.nanoTime() + timeout.toMillis() * 1_000_000;
            while (!bufferComplete) {
                BufferResult results = outputBuffer.get(outputBufferId, sequenceId, new DataSize(1, MEGABYTE)).get(nanoUntil - System.nanoTime(), TimeUnit.NANOSECONDS);
                bufferComplete = results.isBufferComplete();
                for (SerializedPage serializedPage : results.getSerializedPages()) {
                    assertEquals(serializedPage.getPositionCount(), 0);
                }
                sequenceId += results.getSerializedPages().size();
            }
        }

        public void abort()
        {
            outputBuffer.abort(outputBufferId);
            assertEquals(outputBuffer.getInfo().getState(), BufferState.FINISHED);
        }
    }

    private ScheduledSplit newScheduledSplit(int sequenceId, PlanNodeId planNodeId, DriverGroupId driverGroupId, int begin, int end)
    {
        return new ScheduledSplit(sequenceId, planNodeId, new Split(CONNECTOR_ID, TRANSACTION_HANDLE, new TestingSplit(begin, end), driverGroupId));
    }

    public static class Pauser
    {
        private volatile SettableFuture<?> future = SettableFuture.create();

        {
            future.set(null);
        }

        public void pause()
        {
            if (!future.isDone()) {
                return;
            }
            future = SettableFuture.create();
        }

        public void resume()
        {
            if (future.isDone()) {
                return;
            }
            future.set(null);
        }

        public void await()
        {
            try {
                future.get();
            }
            catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class TestingScanOperatorFactory
            implements SourceOperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId sourceId;
        private final Pauser pauser = new Pauser();

        private final Set<DriverGroupId> driverGroupsWithNoMoreOperators = new HashSet<>();
        private boolean overallNoMoreOperators;

        public TestingScanOperatorFactory(
                int operatorId,
                PlanNodeId sourceId,
                List<Type> types)
        {
            this.operatorId = operatorId;
            this.sourceId = requireNonNull(sourceId, "sourceId is null");
        }

        @Override
        public PlanNodeId getSourceId()
        {
            return sourceId;
        }

        @Override
        public List<Type> getTypes()
        {
            return ImmutableList.of(VARCHAR);
        }

        @Override
        public SourceOperator createOperator(DriverContext driverContext)
        {
            checkState(!overallNoMoreOperators, "noMoreOperators() has been called");
            checkState(!driverGroupsWithNoMoreOperators.contains(driverContext.getDriverGroup()), "noMoreOperators(driverGroupId) has been called");
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, sourceId, TestingScanOperator.class.getSimpleName());
            return new TestingScanOperator(operatorContext, sourceId, driverContext.getDriverGroup());
        }

        @Override
        public synchronized void noMoreOperators(DriverGroupId driverGroupId)
        {
            checkArgument(!driverGroupsWithNoMoreOperators.contains(driverGroupId));
            driverGroupsWithNoMoreOperators.add(driverGroupId);
        }

        @Override
        public void noMoreOperators()
        {
            overallNoMoreOperators = true;
        }

        public synchronized Set<DriverGroupId> getDriverGroupsWithNoMoreOperators()
        {
            return ImmutableSet.copyOf(driverGroupsWithNoMoreOperators);
        }

        public boolean isOverallNoMoreOperators()
        {
            return overallNoMoreOperators;
        }

        public Pauser getPauser()
        {
            return pauser;
        }

        public class TestingScanOperator
                implements SourceOperator
        {
            private final OperatorContext operatorContext;
            private final PlanNodeId planNodeId;
            private final DriverGroupId driverGroupId;

            private final SettableFuture<?> blocked = SettableFuture.create();

            private TestingSplit split;

            private boolean finished;

            public TestingScanOperator(
                    OperatorContext operatorContext,
                    PlanNodeId planNodeId,
                    DriverGroupId driverGroupId)
            {
                this.operatorContext = requireNonNull(operatorContext, "operatorContext is null");
                this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
                this.driverGroupId = requireNonNull(driverGroupId, "driverGroupId is null");
            }

            @Override
            public OperatorContext getOperatorContext()
            {
                return operatorContext;
            }

            @Override
            public PlanNodeId getSourceId()
            {
                return planNodeId;
            }

            @Override
            public Supplier<Optional<UpdatablePageSource>> addSplit(Split split)
            {
                requireNonNull(split, "split is null");
                checkState(this.split == null, "Table scan split already set");

                if (finished) {
                    return Optional::empty;
                }

                this.split = (TestingSplit) split.getConnectorSplit();
                blocked.set(null);
                return Optional::empty;
            }

            @Override
            public void noMoreSplits()
            {
                if (split == null) {
                    finish();
                }
                blocked.set(null);
            }

            @Override
            public List<Type> getTypes()
            {
                return ImmutableList.of(VARCHAR);
            }

            @Override
            public void close()
            {
                finish();
            }

            @Override
            public void finish()
            {
                finished = true;
            }

            @Override
            public boolean isFinished()
            {
                return finished;
            }

            @Override
            public ListenableFuture<?> isBlocked()
            {
                return blocked;
            }

            @Override
            public boolean needsInput()
            {
                return false;
            }

            @Override
            public void addInput(Page page)
            {
                throw new UnsupportedOperationException(getClass().getName() + " can not take input");
            }

            @Override
            public Page getOutput()
            {
                if (split == null) {
                    return null;
                }

                pauser.await();
                Page result = new Page(createStringSequenceBlock(split.getBegin(), split.getEnd()));
                finish();
                return result;
            }
        }
    }

    public static class TestingBuildOperatorFactory
            implements OperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId planNodeId;
        private final Pauser pauser = new Pauser();

        private final Set<DriverGroupId> driverGroupsWithNoMoreOperators = new HashSet<>();
        private boolean overallNoMoreOperators;
        private final BuildStates buildStates;

        public TestingBuildOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                BuildStates buildStates)
        {
            this.operatorId = operatorId;
            this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
            this.buildStates = requireNonNull(buildStates, "buildStates is null");
        }

        @Override
        public List<Type> getTypes()
        {
            return ImmutableList.of(VARCHAR);
        }

        @Override
        public Operator createOperator(DriverContext driverContext)
        {
            checkState(!overallNoMoreOperators, "noMoreOperators() has been called");
            checkState(!driverGroupsWithNoMoreOperators.contains(driverContext.getDriverGroup()), "noMoreOperators(driverGroupId) has been called");
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, planNodeId, TestingBuildOperator.class.getSimpleName());
            buildStates.get(driverContext.getDriverGroup()).incrementPendingBuildCount();
            return new TestingBuildOperator(operatorContext, driverContext.getDriverGroup());
        }

        @Override
        public synchronized void noMoreOperators(DriverGroupId driverGroupId)
        {
            checkArgument(!driverGroupsWithNoMoreOperators.contains(driverGroupId));
            buildStates.get(driverGroupId).setNoNewBuilds();
            driverGroupsWithNoMoreOperators.add(driverGroupId);
        }

        @Override
        public void noMoreOperators()
        {
            overallNoMoreOperators = true;
        }

        @Override
        public OperatorFactory duplicate()
        {
            throw new UnsupportedOperationException();
        }

        public synchronized Set<DriverGroupId> getDriverGroupsWithNoMoreOperators()
        {
            return ImmutableSet.copyOf(driverGroupsWithNoMoreOperators);
        }

        public boolean isOverallNoMoreOperators()
        {
            return overallNoMoreOperators;
        }

        public Pauser getPauser()
        {
            return pauser;
        }

        public class TestingBuildOperator
                implements Operator
        {
            private final OperatorContext operatorContext;
            private final DriverGroupId driverGroupId;

            private final List<Page> pages = new ArrayList<>();

            private boolean finishing;

            public TestingBuildOperator(
                    OperatorContext operatorContext,
                    DriverGroupId driverGroupId)
            {
                this.operatorContext = requireNonNull(operatorContext, "operatorContext is null");
                this.driverGroupId = requireNonNull(driverGroupId, "driverGroupId is null");
            }

            @Override
            public OperatorContext getOperatorContext()
            {
                return operatorContext;
            }

            @Override
            public List<Type> getTypes()
            {
                return ImmutableList.of(VARCHAR);
            }

            @Override
            public void finish()
            {
                if (finishing) {
                    return;
                }
                finishing = true;
                buildStates.get(driverGroupId).addBuildResult(pages);
            }

            @Override
            public ListenableFuture<?> isBlocked()
            {
                if (!finishing) {
                    return NOT_BLOCKED;
                }
                return buildStates.get(driverGroupId).getLookupDoneFuture();
            }

            @Override
            public boolean isFinished()
            {
                return finishing && buildStates.get(driverGroupId).getLookupDoneFuture().isDone();
            }

            @Override
            public boolean needsInput()
            {
                return !finishing;
            }

            @Override
            public void addInput(Page page)
            {
                pages.add(page);
            }

            @Override
            public Page getOutput()
            {
                return null;
            }
        }
    }

    public static class TestingUnionJoinOperatorFactory
            implements OperatorFactory
    {
        private final int operatorId;
        private final PlanNodeId planNodeId;
        private final Pauser pauser = new Pauser();

        private final Set<DriverGroupId> driverGroupsWithNoMoreOperators = new HashSet<>();
        private boolean overallNoMoreOperators;
        private final BuildStates buildStates;

        public TestingUnionJoinOperatorFactory(
                int operatorId,
                PlanNodeId planNodeId,
                BuildStates buildStates)
        {
            this.operatorId = operatorId;
            this.planNodeId = requireNonNull(planNodeId, "planNodeId is null");
            this.buildStates = requireNonNull(buildStates, "buildStates is null");
        }

        @Override
        public List<Type> getTypes()
        {
            return ImmutableList.of(VARCHAR);
        }

        @Override
        public Operator createOperator(DriverContext driverContext)
        {
            checkState(!overallNoMoreOperators, "noMoreOperators() has been called");
            checkState(!driverGroupsWithNoMoreOperators.contains(driverContext.getDriverGroup()), "noMoreOperators(driverGroupId) has been called");
            OperatorContext operatorContext = driverContext.addOperatorContext(operatorId, planNodeId, TestingUnionJoinOperator.class.getSimpleName());
            buildStates.get(driverContext.getDriverGroup()).incrementPendingLookupCount();
            return new TestingUnionJoinOperator(operatorContext, driverContext.getDriverGroup());
        }

        @Override
        public synchronized void noMoreOperators(DriverGroupId driverGroupId)
        {
            checkArgument(!driverGroupsWithNoMoreOperators.contains(driverGroupId));
            buildStates.setNoNewLookups(driverGroupId);
            driverGroupsWithNoMoreOperators.add(driverGroupId);
        }

        @Override
        public void noMoreOperators()
        {
            buildStates.setNoNewLookups();
            overallNoMoreOperators = true;
        }

        @Override
        public OperatorFactory duplicate()
        {
            throw new UnsupportedOperationException();
        }

        public synchronized Set<DriverGroupId> getDriverGroupsWithNoMoreOperators()
        {
            return ImmutableSet.copyOf(driverGroupsWithNoMoreOperators);
        }

        public boolean isOverallNoMoreOperators()
        {
            return overallNoMoreOperators;
        }

        public Pauser getPauser()
        {
            return pauser;
        }

        public class TestingUnionJoinOperator
                implements Operator
        {
            private final OperatorContext operatorContext;
            private final DriverGroupId driverGroupId;

            private final ListenableFuture<Integer> multiplierFuture;
            private final Queue<Page> pages = new ArrayDeque<>();
            private boolean finishing;

            public TestingUnionJoinOperator(
                    OperatorContext operatorContext,
                    DriverGroupId driverGroupId)
            {
                this.operatorContext = requireNonNull(operatorContext, "operatorContext is null");
                this.driverGroupId = requireNonNull(driverGroupId, "driverGroupId is null");
                multiplierFuture = Futures.transform(buildStates.get(driverGroupId).getPagesFuture(), buildPages -> {
                    requireNonNull(buildPages, "buildPages is null");
                    return buildPages.stream()
                            .mapToInt(Page::getPositionCount)
                            .sum();
                });
            }

            @Override
            public OperatorContext getOperatorContext()
            {
                return operatorContext;
            }

            @Override
            public List<Type> getTypes()
            {
                return ImmutableList.of(VARCHAR);
            }

            @Override
            public void finish()
            {
                if (finishing) {
                    return;
                }
                finishing = true;
            }

            @Override
            public ListenableFuture<?> isBlocked()
            {
                return multiplierFuture;
            }

            @Override
            public boolean isFinished()
            {
                return finishing && pages.isEmpty();
            }

            @Override
            public boolean needsInput()
            {
                return !finishing && multiplierFuture.isDone();
            }

            @Override
            public void addInput(Page page)
            {
                int multiplier = getFutureValue(multiplierFuture);
                for (int i = 0; i < multiplier; i++) {
                    pages.add(page);
                }
            }

            @Override
            public Page getOutput()
            {
                Page result = pages.poll();
                if (isFinished() && pages.isEmpty()) {
                    buildStates.get(driverGroupId).decrementPendingLookupCount();
                }
                return result;
            }
        }
    }

    private static class BuildStates
    {
        private final HashMap<DriverGroupId, BuildState> buildStatesMap = new HashMap<>();
        private final boolean grouped;

        public BuildStates(PipelineExecutionFlow executionFlow)
        {
            this.grouped = executionFlow == PipelineExecutionFlow.GROUPED;
        }

        public synchronized BuildState get(DriverGroupId driverGroupId)
        {
            if (grouped) {
                return buildStatesMap.computeIfAbsent(driverGroupId, ignored -> new BuildState());
            }
            else {
                return buildStatesMap.computeIfAbsent(DriverGroupId.notGrouped(), ignored -> new BuildState());
            }
        }

        public void setNoNewLookups(DriverGroupId driverGroupId)
        {
            // If it's grouped execution, knowing no new probe operators will be created for a group
            // should be immediately propagated to build state, so that corresponding build
            // can move forward (e.g. clean up).
            // On the other hand, if there's a single build, knowing no new probe operators will be
            // created for a group has no immediate use.
            if (grouped) {
                get(driverGroupId).setNoNewLookups();
            }
        }

        public void setNoNewLookups()
        {
            if (!grouped) {
                get(DriverGroupId.notGrouped()).setNoNewLookups();
            }
        }

        private static class BuildState
        {
            private final SettableFuture<List<Page>> pagesFuture = SettableFuture.create();
            private final SettableFuture<?> lookupDoneFuture = SettableFuture.create();

            private final List<Page> pages = new ArrayList<>();
            private int pendingBuildCount;
            private boolean noNewBuilds;
            private int pendingLookupCount;
            private boolean noNewLookups;

            public synchronized void addBuildResult(List<Page> newPages)
            {
                checkState(!pagesFuture.isDone());
                pages.addAll(newPages);
                pendingBuildCount--;
                checkAllBuildsDone();
            }

            public synchronized void incrementPendingBuildCount()
            {
                checkState(!noNewBuilds);
                pendingBuildCount++;
            }

            public synchronized void setNoNewBuilds()
            {
                if (noNewBuilds) {
                    return;
                }
                checkState(!pagesFuture.isDone());
                noNewBuilds = true;
                checkAllBuildsDone();
            }

            public synchronized void checkAllBuildsDone()
            {
                if (pendingBuildCount == 0 && noNewBuilds) {
                    pagesFuture.set(pages);
                }
            }

            public ListenableFuture<List<Page>> getPagesFuture()
            {
                return pagesFuture;
            }

            public synchronized void decrementPendingLookupCount()
            {
                checkState(!lookupDoneFuture.isDone());
                pendingLookupCount--;
                checkAllLookupsDone();
            }

            public synchronized void incrementPendingLookupCount()
            {
                checkState(!noNewLookups);
                pendingLookupCount++;
            }

            synchronized void setNoNewLookups()
            {
                if (noNewLookups) {
                    return;
                }
                checkState(!lookupDoneFuture.isDone());
                noNewLookups = true;
                checkAllLookupsDone();
            }

            public synchronized void checkAllLookupsDone()
            {
                if (pendingLookupCount == 0 && noNewLookups) {
                    lookupDoneFuture.set(null);
                }
            }

            public ListenableFuture<?> getLookupDoneFuture()
            {
                return lookupDoneFuture;
            }
        }
    }

    public static class TestingSplit
            implements ConnectorSplit
    {
        private final int begin;
        private final int end;

        @JsonCreator
        public TestingSplit(@JsonProperty("begin") int begin, @JsonProperty("end") int end)
        {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public boolean isRemotelyAccessible()
        {
            return true;
        }

        @Override
        public List<HostAddress> getAddresses()
        {
            return ImmutableList.of();
        }

        @Override
        public Object getInfo()
        {
            return this;
        }

        public int getBegin()
        {
            return begin;
        }

        public int getEnd()
        {
            return end;
        }
    }
}
