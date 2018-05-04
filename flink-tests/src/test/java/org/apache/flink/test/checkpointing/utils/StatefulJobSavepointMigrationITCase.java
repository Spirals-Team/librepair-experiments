/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.test.checkpointing.utils;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.StateBackendLoader;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.util.migration.MigrationVersion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Migration ITCases for a stateful job. The tests are parameterized to cover
 * migrating for multiple previous Flink versions, as well as for different state backends.
 */
@RunWith(Parameterized.class)
public class StatefulJobSavepointMigrationITCase extends SavepointMigrationTestBase {

	private static final int NUM_SOURCE_ELEMENTS = 4;

	/**
	 * This test runs in either of two modes: 1) we want to generate the binary savepoint, i.e.
	 * we have to run the checkpointing functions 2) we want to verify restoring, so we have to run
	 * the checking functions.
	 */
	public enum ExecutionMode {
		PERFORM_SAVEPOINT,
		VERIFY_SAVEPOINT
	}

	// TODO change this to PERFORM_SAVEPOINT to regenerate binary savepoints
	private final ExecutionMode executionMode = ExecutionMode.VERIFY_SAVEPOINT;

	@Parameterized.Parameters(name = "Migrate Savepoint / Backend: {0}")
	public static Collection<Tuple2<MigrationVersion, String>> parameters () {
		return Arrays.asList(
			Tuple2.of(MigrationVersion.v1_4, StateBackendLoader.MEMORY_STATE_BACKEND_NAME),
			Tuple2.of(MigrationVersion.v1_4, StateBackendLoader.ROCKSDB_STATE_BACKEND_NAME));
	}

	private final MigrationVersion testMigrateVersion;
	private final String testStateBackend;

	public StatefulJobSavepointMigrationITCase(Tuple2<MigrationVersion, String> testMigrateVersionAndBackend) throws Exception {
		this.testMigrateVersion = testMigrateVersionAndBackend.f0;
		this.testStateBackend = testMigrateVersionAndBackend.f1;
	}

	@Test
	public void testSavepoint() throws Exception {

		final int parallelism = 4;

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setRestartStrategy(RestartStrategies.noRestart());
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		switch (testStateBackend) {
			case StateBackendLoader.ROCKSDB_STATE_BACKEND_NAME:
				env.setStateBackend(new RocksDBStateBackend(new MemoryStateBackend()));
				break;
			case StateBackendLoader.MEMORY_STATE_BACKEND_NAME:
				env.setStateBackend(new MemoryStateBackend());
				break;
			default:
				throw new UnsupportedOperationException();
		}

		env.enableCheckpointing(500);
		env.setParallelism(parallelism);
		env.setMaxParallelism(parallelism);

		SourceFunction<Tuple2<Long, Long>> nonParallelSource;
		SourceFunction<Tuple2<Long, Long>> parallelSource;
		RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> flatMap;
		OneInputStreamOperator<Tuple2<Long, Long>, Tuple2<Long, Long>> timelyOperator;

		if (executionMode == ExecutionMode.PERFORM_SAVEPOINT) {
			nonParallelSource = new MigrationTestUtils.CheckpointingNonParallelSourceWithListState(NUM_SOURCE_ELEMENTS);
			parallelSource = new MigrationTestUtils.CheckpointingParallelSourceWithUnionListState(NUM_SOURCE_ELEMENTS);
			flatMap = new MigrationTestUtils.CheckpointingKeyedStateFlatMap();
			timelyOperator = new MigrationTestUtils.CheckpointingTimelyStatefulOperator();
		} else if (executionMode == ExecutionMode.VERIFY_SAVEPOINT) {
			nonParallelSource = new MigrationTestUtils.CheckingNonParallelSourceWithListState(NUM_SOURCE_ELEMENTS);
			parallelSource = new MigrationTestUtils.CheckingParallelSourceWithUnionListState(NUM_SOURCE_ELEMENTS);
			flatMap = new MigrationTestUtils.CheckingKeyedStateFlatMap();
			timelyOperator = new MigrationTestUtils.CheckingTimelyStatefulOperator();
		} else {
			throw new IllegalStateException("Unknown ExecutionMode " + executionMode);
		}

		env
			.addSource(nonParallelSource).uid("CheckpointingSource1")
			.keyBy(0)
			.flatMap(flatMap).startNewChain().uid("CheckpointingKeyedStateFlatMap1")
			.keyBy(0)
			.transform(
				"timely_stateful_operator",
				new TypeHint<Tuple2<Long, Long>>() {}.getTypeInfo(),
				timelyOperator).uid("CheckpointingTimelyStatefulOperator1")
			.addSink(new MigrationTestUtils.AccumulatorCountingSink<>());

		env
			.addSource(parallelSource).uid("CheckpointingSource2")
			.keyBy(0)
			.flatMap(flatMap).startNewChain().uid("CheckpointingKeyedStateFlatMap2")
			.keyBy(0)
			.transform(
				"timely_stateful_operator",
				new TypeHint<Tuple2<Long, Long>>() {}.getTypeInfo(),
				timelyOperator).uid("CheckpointingTimelyStatefulOperator2")
			.addSink(new MigrationTestUtils.AccumulatorCountingSink<>());

		if (executionMode == ExecutionMode.PERFORM_SAVEPOINT) {
			executeAndSavepoint(
				env,
				"src/test/resources/" + getSavepointPath(testMigrateVersion, testStateBackend),
				new Tuple2<>(MigrationTestUtils.AccumulatorCountingSink.NUM_ELEMENTS_ACCUMULATOR, NUM_SOURCE_ELEMENTS * 2));
		} else {
			restoreAndExecute(
				env,
				getResourceFilename(getSavepointPath(testMigrateVersion, testStateBackend)),
				new Tuple2<>(MigrationTestUtils.CheckingNonParallelSourceWithListState.SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR, 1),
				new Tuple2<>(MigrationTestUtils.CheckingParallelSourceWithUnionListState.SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR, parallelism),
				new Tuple2<>(MigrationTestUtils.CheckingKeyedStateFlatMap.SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR, NUM_SOURCE_ELEMENTS * 2),
				new Tuple2<>(MigrationTestUtils.CheckingTimelyStatefulOperator.SUCCESSFUL_PROCESS_CHECK_ACCUMULATOR, NUM_SOURCE_ELEMENTS * 2),
				new Tuple2<>(MigrationTestUtils.CheckingTimelyStatefulOperator.SUCCESSFUL_EVENT_TIME_CHECK_ACCUMULATOR, NUM_SOURCE_ELEMENTS * 2),
				new Tuple2<>(MigrationTestUtils.CheckingTimelyStatefulOperator.SUCCESSFUL_PROCESSING_TIME_CHECK_ACCUMULATOR, NUM_SOURCE_ELEMENTS * 2),
				new Tuple2<>(MigrationTestUtils.AccumulatorCountingSink.NUM_ELEMENTS_ACCUMULATOR, NUM_SOURCE_ELEMENTS * 2));
		}
	}

	private String getSavepointPath(MigrationVersion savepointVersion, String backendType) {
		switch (backendType) {
			case StateBackendLoader.ROCKSDB_STATE_BACKEND_NAME:
				return "new-stateful-udf-migration-itcase-flink" + savepointVersion + "-rocksdb-savepoint";
			case StateBackendLoader.MEMORY_STATE_BACKEND_NAME:
				return "new-stateful-udf-migration-itcase-flink" + savepointVersion + "-savepoint";
			default:
				throw new UnsupportedOperationException();
		}
	}
}
