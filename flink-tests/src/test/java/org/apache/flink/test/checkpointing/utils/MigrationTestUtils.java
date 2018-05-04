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

import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.InternalTimer;
import org.apache.flink.streaming.api.operators.InternalTimerService;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.operators.Triggerable;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.util.Collector;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A utility class containing common functions/classes used by multiple migration tests.
 */
public class MigrationTestUtils {

	public static class CheckpointingNonParallelSourceWithListState
			implements SourceFunction<Tuple2<Long, Long>>, CheckpointedFunction {

		static final ListStateDescriptor<String> STATE_DESCRIPTOR =
				new ListStateDescriptor<>("source-state", StringSerializer.INSTANCE);

		static final String CHECKPOINTED_STRING = "Here be dragons!";
		static final String CHECKPOINTED_STRING_1 = "Here be more dragons!";
		static final String CHECKPOINTED_STRING_2 = "Here be yet more dragons!";
		static final String CHECKPOINTED_STRING_3 = "Here be the mostest dragons!";

		private static final long serialVersionUID = 1L;

		private volatile boolean isRunning = true;

		private final int numElements;

		private transient ListState<String> unionListState;

		CheckpointingNonParallelSourceWithListState(int numElements) {
			this.numElements = numElements;
		}

		@Override
		public void snapshotState(FunctionSnapshotContext context) throws Exception {
			unionListState.clear();
			unionListState.add(CHECKPOINTED_STRING);
			unionListState.add(CHECKPOINTED_STRING_1);
			unionListState.add(CHECKPOINTED_STRING_2);
			unionListState.add(CHECKPOINTED_STRING_3);
		}

		@Override
		public void initializeState(FunctionInitializationContext context) throws Exception {
			unionListState = context.getOperatorStateStore().getListState(
					STATE_DESCRIPTOR);
		}

		@Override
		public void run(SourceContext<Tuple2<Long, Long>> ctx) throws Exception {

			ctx.emitWatermark(new Watermark(0));

			synchronized (ctx.getCheckpointLock()) {
				for (long i = 0; i < numElements; i++) {
					ctx.collect(new Tuple2<>(i, i));
				}
			}

			// don't emit a final watermark so that we don't trigger the registered event-time
			// timers
			while (isRunning) {
				Thread.sleep(20);
			}
		}

		@Override
		public void cancel() {
			isRunning = false;
		}
	}

	public static class CheckpointingParallelSourceWithUnionListState
			extends RichSourceFunction<Tuple2<Long, Long>> implements CheckpointedFunction {

		static final ListStateDescriptor<String> STATE_DESCRIPTOR =
				new ListStateDescriptor<>("source-state", StringSerializer.INSTANCE);

		static final String[] CHECKPOINTED_STRINGS = {
				"Here be dragons!",
				"Here be more dragons!",
				"Here be yet more dragons!",
				"Here be the mostest dragons!" };

		private static final long serialVersionUID = 1L;

		private volatile boolean isRunning = true;

		private final int numElements;

		private transient ListState<String> unionListState;

		CheckpointingParallelSourceWithUnionListState(int numElements) {
			this.numElements = numElements;
		}

		@Override
		public void snapshotState(FunctionSnapshotContext context) throws Exception {
			unionListState.clear();

			for (String s : CHECKPOINTED_STRINGS) {
				if (s.hashCode() % getRuntimeContext().getNumberOfParallelSubtasks() == getRuntimeContext().getIndexOfThisSubtask()) {
					unionListState.add(s);
				}
			}
		}

		@Override
		public void initializeState(FunctionInitializationContext context) throws Exception {
			unionListState = context.getOperatorStateStore().getUnionListState(
					STATE_DESCRIPTOR);
		}

		@Override
		public void run(SourceContext<Tuple2<Long, Long>> ctx) throws Exception {

			ctx.emitWatermark(new Watermark(0));

			synchronized (ctx.getCheckpointLock()) {
				for (long i = 0; i < numElements; i++) {
					if (i % getRuntimeContext().getNumberOfParallelSubtasks() == getRuntimeContext().getIndexOfThisSubtask()) {
						ctx.collect(new Tuple2<>(i, i));
					}
				}
			}

			// don't emit a final watermark so that we don't trigger the registered event-time
			// timers
			while (isRunning) {
				Thread.sleep(20);
			}
		}

		@Override
		public void cancel() {
			isRunning = false;
		}
	}

	public static class CheckpointingKeyedStateFlatMap extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> {

		private static final long serialVersionUID = 1L;

		private final ValueStateDescriptor<Long> stateDescriptor =
				new ValueStateDescriptor<>("state-name", LongSerializer.INSTANCE);

		@Override
		public void flatMap(Tuple2<Long, Long> value, Collector<Tuple2<Long, Long>> out) throws Exception {
			out.collect(value);

			getRuntimeContext().getState(stateDescriptor).update(value.f1);
		}
	}

	public static class CheckingKeyedStateFlatMap extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> {

		private static final long serialVersionUID = 1L;

		static final String SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR = CheckingKeyedStateFlatMap.class + "_RESTORE_CHECK";

		private final ValueStateDescriptor<Long> stateDescriptor =
				new ValueStateDescriptor<>("state-name", LongSerializer.INSTANCE);

		@Override
		public void open(Configuration parameters) throws Exception {
			super.open(parameters);

			getRuntimeContext().addAccumulator(SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR, new IntCounter());
		}

		@Override
		public void flatMap(Tuple2<Long, Long> value, Collector<Tuple2<Long, Long>> out) throws Exception {
			out.collect(value);

			ValueState<Long> state = getRuntimeContext().getState(stateDescriptor);
			if (state == null) {
				throw new RuntimeException("Missing key value state for " + value);
			}

			assertEquals(value.f1, state.value());
			getRuntimeContext().getAccumulator(SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR).add(1);
		}
	}

	public static class CheckpointingTimelyStatefulOperator
			extends AbstractStreamOperator<Tuple2<Long, Long>>
			implements OneInputStreamOperator<Tuple2<Long, Long>, Tuple2<Long, Long>>, Triggerable<Long, Long> {
		private static final long serialVersionUID = 1L;

		private final ValueStateDescriptor<Long> stateDescriptor =
				new ValueStateDescriptor<>("state-name", LongSerializer.INSTANCE);

		private transient InternalTimerService<Long> timerService;

		@Override
		public void open() throws Exception {
			super.open();

			timerService = getInternalTimerService(
					"timer",
					LongSerializer.INSTANCE,
					this);

		}

		@Override
		public void processElement(StreamRecord<Tuple2<Long, Long>> element) throws Exception {
			ValueState<Long> state = getKeyedStateBackend().getPartitionedState(
					element.getValue().f0,
					LongSerializer.INSTANCE,
					stateDescriptor);

			state.update(element.getValue().f1);

			timerService.registerEventTimeTimer(element.getValue().f0, timerService.currentWatermark() + 10);
			timerService.registerProcessingTimeTimer(element.getValue().f0, timerService.currentProcessingTime() + 30_000);

			output.collect(element);
		}

		@Override
		public void onEventTime(InternalTimer<Long, Long> timer) {

		}

		@Override
		public void onProcessingTime(InternalTimer<Long, Long> timer) {

		}

		@Override
		public void processWatermark(Watermark mark) {
			output.emitWatermark(mark);
		}
	}

	public static class CheckingNonParallelSourceWithListState
			extends RichSourceFunction<Tuple2<Long, Long>> implements CheckpointedFunction {

		private static final long serialVersionUID = 1L;

		static final String SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR = CheckingNonParallelSourceWithListState.class + "_RESTORE_CHECK";

		private volatile boolean isRunning = true;

		private final int numElements;

		CheckingNonParallelSourceWithListState(int numElements) {
			this.numElements = numElements;
		}

		@Override
		public void snapshotState(FunctionSnapshotContext context) throws Exception {

		}

		@Override
		public void initializeState(FunctionInitializationContext context) throws Exception {
			ListState<String> unionListState = context.getOperatorStateStore().getListState(
					CheckpointingNonParallelSourceWithListState.STATE_DESCRIPTOR);

			if (context.isRestored()) {
				assertThat(unionListState.get(),
						containsInAnyOrder(
								CheckpointingNonParallelSourceWithListState.CHECKPOINTED_STRING,
								CheckpointingNonParallelSourceWithListState.CHECKPOINTED_STRING_1,
								CheckpointingNonParallelSourceWithListState.CHECKPOINTED_STRING_2,
								CheckpointingNonParallelSourceWithListState.CHECKPOINTED_STRING_3));

				getRuntimeContext().addAccumulator(SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR, new IntCounter());
				getRuntimeContext().getAccumulator(SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR).add(1);
			} else {
				throw new RuntimeException(
						"This source should always be restored because it's only used when restoring from a savepoint.");
			}
		}

		@Override
		public void run(SourceContext<Tuple2<Long, Long>> ctx) throws Exception {

			// immediately trigger any set timers
			ctx.emitWatermark(new Watermark(1000));

			synchronized (ctx.getCheckpointLock()) {
				for (long i = 0; i < numElements; i++) {
					ctx.collect(new Tuple2<>(i, i));
				}
			}

			while (isRunning) {
				Thread.sleep(20);
			}
		}

		@Override
		public void cancel() {
			isRunning = false;
		}
	}

	public static class CheckingParallelSourceWithUnionListState
			extends RichParallelSourceFunction<Tuple2<Long, Long>> implements CheckpointedFunction {

		private static final long serialVersionUID = 1L;

		static final String SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR = CheckingParallelSourceWithUnionListState.class + "_RESTORE_CHECK";

		private volatile boolean isRunning = true;

		private final int numElements;

		CheckingParallelSourceWithUnionListState(int numElements) {
			this.numElements = numElements;
		}

		@Override
		public void snapshotState(FunctionSnapshotContext context) throws Exception {

		}

		@Override
		public void initializeState(FunctionInitializationContext context) throws Exception {
			ListState<String> unionListState = context.getOperatorStateStore().getUnionListState(
					CheckpointingNonParallelSourceWithListState.STATE_DESCRIPTOR);

			if (context.isRestored()) {
				assertThat(unionListState.get(),
						containsInAnyOrder(CheckpointingParallelSourceWithUnionListState.CHECKPOINTED_STRINGS));

				getRuntimeContext().addAccumulator(SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR, new IntCounter());
				getRuntimeContext().getAccumulator(SUCCESSFUL_RESTORE_CHECK_ACCUMULATOR).add(1);
			} else {
				throw new RuntimeException(
						"This source should always be restored because it's only used when restoring from a savepoint.");
			}
		}

		@Override
		public void run(SourceContext<Tuple2<Long, Long>> ctx) throws Exception {

			// immediately trigger any set timers
			ctx.emitWatermark(new Watermark(1000));

			synchronized (ctx.getCheckpointLock()) {
				for (long i = 0; i < numElements; i++) {
					if (i % getRuntimeContext().getNumberOfParallelSubtasks() == getRuntimeContext().getIndexOfThisSubtask()) {
						ctx.collect(new Tuple2<>(i, i));
					}
				}
			}

			while (isRunning) {
				Thread.sleep(20);
			}
		}

		@Override
		public void cancel() {
			isRunning = false;
		}
	}

	public static class CheckingTimelyStatefulOperator
			extends AbstractStreamOperator<Tuple2<Long, Long>>
			implements OneInputStreamOperator<Tuple2<Long, Long>, Tuple2<Long, Long>>, Triggerable<Long, Long> {
		private static final long serialVersionUID = 1L;

		static final String SUCCESSFUL_PROCESS_CHECK_ACCUMULATOR = CheckingTimelyStatefulOperator.class + "_PROCESS_CHECKS";
		static final String SUCCESSFUL_EVENT_TIME_CHECK_ACCUMULATOR = CheckingTimelyStatefulOperator.class + "_ET_CHECKS";
		static final String SUCCESSFUL_PROCESSING_TIME_CHECK_ACCUMULATOR = CheckingTimelyStatefulOperator.class + "_PT_CHECKS";

		private final ValueStateDescriptor<Long> stateDescriptor =
				new ValueStateDescriptor<>("state-name", LongSerializer.INSTANCE);

		@Override
		public void open() throws Exception {
			super.open();

			// have to re-register to ensure that our onEventTime() is called
			getInternalTimerService(
					"timer",
					LongSerializer.INSTANCE,
					this);

			getRuntimeContext().addAccumulator(SUCCESSFUL_PROCESS_CHECK_ACCUMULATOR, new IntCounter());
			getRuntimeContext().addAccumulator(SUCCESSFUL_EVENT_TIME_CHECK_ACCUMULATOR, new IntCounter());
			getRuntimeContext().addAccumulator(SUCCESSFUL_PROCESSING_TIME_CHECK_ACCUMULATOR, new IntCounter());
		}

		@Override
		public void processElement(StreamRecord<Tuple2<Long, Long>> element) throws Exception {
			ValueState<Long> state = getKeyedStateBackend().getPartitionedState(
					element.getValue().f0,
					LongSerializer.INSTANCE,
					stateDescriptor);

			assertEquals(state.value(), element.getValue().f1);
			getRuntimeContext().getAccumulator(SUCCESSFUL_PROCESS_CHECK_ACCUMULATOR).add(1);

			output.collect(element);
		}

		@Override
		public void onEventTime(InternalTimer<Long, Long> timer) throws Exception {
			ValueState<Long> state = getKeyedStateBackend().getPartitionedState(
					timer.getNamespace(),
					LongSerializer.INSTANCE,
					stateDescriptor);

			assertEquals(state.value(), timer.getNamespace());
			getRuntimeContext().getAccumulator(SUCCESSFUL_EVENT_TIME_CHECK_ACCUMULATOR).add(1);
		}

		@Override
		public void onProcessingTime(InternalTimer<Long, Long> timer) throws Exception {
			ValueState<Long> state = getKeyedStateBackend().getPartitionedState(
					timer.getNamespace(),
					LongSerializer.INSTANCE,
					stateDescriptor);

			assertEquals(state.value(), timer.getNamespace());
			getRuntimeContext().getAccumulator(SUCCESSFUL_PROCESSING_TIME_CHECK_ACCUMULATOR).add(1);
		}
	}

	public static class AccumulatorCountingSink<T> extends RichSinkFunction<T> {
		private static final long serialVersionUID = 1L;

		static final String NUM_ELEMENTS_ACCUMULATOR = AccumulatorCountingSink.class + "_NUM_ELEMENTS";

		int count = 0;

		@Override
		public void open(Configuration parameters) throws Exception {
			super.open(parameters);

			getRuntimeContext().addAccumulator(NUM_ELEMENTS_ACCUMULATOR, new IntCounter());
		}

		@Override
		public void invoke(T value, Context context) throws Exception {
			count++;
			getRuntimeContext().getAccumulator(NUM_ELEMENTS_ACCUMULATOR).add(1);
		}
	}
}
