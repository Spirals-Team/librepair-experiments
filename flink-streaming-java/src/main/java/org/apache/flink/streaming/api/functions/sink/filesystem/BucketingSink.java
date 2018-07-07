/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.api.functions.sink.filesystem;

import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.api.common.serialization.Writer;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.OperatorStateStore;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.common.typeutils.base.array.BytePrimitiveArraySerializer;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.ResumableWriter;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.runtime.state.CheckpointListener;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketers.Bucketer;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketers.DateTimeBucketer;
import org.apache.flink.streaming.api.functions.sink.filesystem.writers.StringWriter;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
import org.apache.flink.streaming.runtime.tasks.ProcessingTimeCallback;
import org.apache.flink.streaming.runtime.tasks.ProcessingTimeService;
import org.apache.flink.util.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Javadoc.
 */
public class BucketingSink<IN>
		extends RichSinkFunction<IN>
		implements CheckpointedFunction, CheckpointListener, ProcessingTimeCallback {

	private static final long serialVersionUID = 2544039385174378235L;

	private static final Logger LOG = LoggerFactory.getLogger(BucketingSink.class);

	private static final long DEFAULT_CHECK_INTERVAL = 60L * 1000L;

	private static final long DEFAULT_INACTIVITY_INTERVAL = 60L * 60L * 1000L;

	private static final long DEFAULT_ROLLOVER_INTERVAL = 60L * 1000L;

	private static final long DEFAULT_PART_SIZE = 1024L * 1024L * 384L;

	private final Path basePath;

	private transient ResumableWriter fsWriter;

	private transient Clock clock;

	private transient ProcessingTimeService processingTimeService;

	private Bucketer<IN> bucketer;

	private Writer<IN> writer;

	private long bucketCheckInterval = DEFAULT_CHECK_INTERVAL;

	private long partFileSize = DEFAULT_PART_SIZE;

	private long rolloverInterval = DEFAULT_ROLLOVER_INTERVAL;

	private long inactivityInterval = DEFAULT_INACTIVITY_INTERVAL;

	private transient Map<Path, Bucket<IN>> activeBuckets;

	private long initMaxPartCounter = 0L;

	private long maxPartCounterUsed = 0L;

	private final ListStateDescriptor<byte[]> bucketStateDesc =
			new ListStateDescriptor<>("bucket-states",
					BytePrimitiveArraySerializer.INSTANCE);

	private transient ListState<byte[]> restoredBucketStates;

	private final ListStateDescriptor<Long> maxPartCounterStateDesc =
			new ListStateDescriptor<>("max-part-counter",
					LongSerializer.INSTANCE);

	private transient ListState<Long> restoredMaxCounters;

	private final BucketFactory<IN> bucketFactory;

	/**
	 * Creates a new {@code BucketingSink} that writes files to the given base directory.
	 *
	 *
	 * <p>This uses a{@link DateTimeBucketer} as {@link Bucketer} and a {@link StringWriter} has writer.
	 * The maximum bucket size is set to 384 MB.
	 *
	 * @param basePath The directory to which to write the bucket files.
	 */
	public BucketingSink(Path basePath) {
		this(basePath, new DefaultBucketFactory<>());
	}

	@VisibleForTesting
	BucketingSink(Path basePath, BucketFactory<IN> bucketFactory) {
		this.basePath = Preconditions.checkNotNull(basePath);
		this.bucketer = new DateTimeBucketer<>();
		this.writer = new StringWriter<>();
		this.bucketFactory = Preconditions.checkNotNull(bucketFactory);
	}

	public BucketingSink<IN> setWriter(Writer<IN> writer) {
		this.writer = Preconditions.checkNotNull(writer);
		return this;
	}

	public BucketingSink<IN> setBucketer(Bucketer<IN> bucketer) {
		this.bucketer = Preconditions.checkNotNull(bucketer);
		return this;
	}

	public BucketingSink<IN> setPartFileSize(long partFileSize) {
		this.partFileSize = partFileSize;
		return this;
	}

	public BucketingSink<IN>  setBucketCheckInterval(long bucketCheckInterval) {
		this.bucketCheckInterval = bucketCheckInterval;
		return this;
	}

	public BucketingSink<IN> setRolloverInterval(long rolloverInterval) {
		this.rolloverInterval = rolloverInterval;
		return this;
	}

	public BucketingSink<IN> setInactivityInterval(long inactivityInterval) {
		this.inactivityInterval = inactivityInterval;
		return this;
	}

	@Override
	public void notifyCheckpointComplete(long checkpointId) throws Exception {
		final Iterator<Map.Entry<Path, Bucket<IN>>> activeBucketIt =
				activeBuckets.entrySet().iterator();

		while (activeBucketIt.hasNext()) {
			Bucket<IN> bucket = activeBucketIt.next().getValue();
			bucket.commitUpToCheckpoint(checkpointId);

			if (!bucket.isActive()) {
				// We've dealt with all the pending files and the writer for this bucket is not currently open.
				// Therefore this bucket is currently inactive and we can remove it from our state.
				activeBucketIt.remove();
			}
		}
	}

	@Override
	public void snapshotState(FunctionSnapshotContext context) throws Exception {
		Preconditions.checkNotNull(restoredBucketStates);
		Preconditions.checkNotNull(fsWriter);

		// TODO: 7/3/18 do not create it every time.
		final SimpleVersionedSerializer<Bucket.BucketState> serializer =
				Bucket.getBucketStateSerializer(fsWriter);

		restoredBucketStates.clear();
		for (Map.Entry<Path, Bucket<IN>> bucketStateEntry : activeBuckets.entrySet()) {
			final Bucket<IN> bucket = bucketStateEntry.getValue();
			final Bucket.BucketState bucketState = bucket.snapshot(
					context.getCheckpointId(),
					context.getCheckpointTimestamp());
			restoredBucketStates.add(serializer.serialize(bucketState));
		}

		restoredMaxCounters.clear();
		restoredMaxCounters.add(maxPartCounterUsed);
	}

	@Override
	public void initializeState(FunctionInitializationContext context) throws Exception {
		initFileSystem();

		this.activeBuckets = new HashMap<>();

		// Now when restoring, we start fresh. Everything gets committed and the state is empty.
		// If in the future we want to resume the in-progress files, we should make sure that in
		// case we receive two states for the same bucket, we merge them appropriately. This includes
		// keep only one in-progress file and commit the other, and commit the pending ones, as they
		// were pending for a previous to the last successful checkpoint.

		final OperatorStateStore stateStore = context.getOperatorStateStore();

		restoredBucketStates = stateStore.getListState(bucketStateDesc);
		restoredMaxCounters = stateStore.getUnionListState(maxPartCounterStateDesc);

		if (context.isRestored()) {
			final int subtaskIndex = getRuntimeContext().getIndexOfThisSubtask();

			LOG.info("Restoring state for the {} (taskIdx={}).", getClass().getSimpleName(), subtaskIndex);

			for (long partCounter: restoredMaxCounters.get()) {
				if (partCounter > initMaxPartCounter) {
					initMaxPartCounter = partCounter;
				}
			}

			final SimpleVersionedSerializer<Bucket.BucketState> deserializer =
					Bucket.getBucketStateSerializer(fsWriter);

			final int version = deserializer.getVersion();
			for (byte[] recoveredState : restoredBucketStates.get()) {
				final Bucket.BucketState bucketState = deserializer.deserialize(version, recoveredState);
				final Path bucketPath = bucketState.getBucketPath();

				final Bucket<IN> restoredBucket = bucketFactory.getRestoredBucket(
						fsWriter,
						subtaskIndex,
						bucketPath,
						initMaxPartCounter,
						partFileSize,
						rolloverInterval,
						inactivityInterval,
						writer,
						bucketState
				);

				final Bucket<IN> existingBucket = activeBuckets.get(bucketPath);
				if (existingBucket == null) {
					activeBuckets.put(bucketPath, restoredBucket);
				} else {
					existingBucket.merge(restoredBucket);
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("{} idx {} restored state for bucket {}", getClass().getSimpleName(), subtaskIndex, bucketPath);
				}
			}
		}
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);

		processingTimeService = ((StreamingRuntimeContext) getRuntimeContext()).getProcessingTimeService();
		this.clock = () -> processingTimeService.getCurrentProcessingTime();
		long currentProcessingTime = clock.currentTimeMillis();
		processingTimeService.registerTimer(currentProcessingTime + bucketCheckInterval, this);
	}

	@Override
	public void onProcessingTime(long timestamp) throws Exception {
		long currentProcessingTime = processingTimeService.getCurrentProcessingTime();
		for (Map.Entry<Path, Bucket<IN>> entry : activeBuckets.entrySet()) {
			entry.getValue().rollByTime(currentProcessingTime);
		}
		processingTimeService.registerTimer(currentProcessingTime + bucketCheckInterval, this);
	}

	@Override
	public void invoke(IN value, Context context) throws Exception {
		final Path bucketPath = bucketer.getBucketPath(clock, basePath, value);
		final long currentProcessingTime = clock.currentTimeMillis();
		final int subtaskIndex = getRuntimeContext().getIndexOfThisSubtask();

		Bucket<IN> bucket = activeBuckets.get(bucketPath);
		if (bucket == null) {
			bucket = bucketFactory.getNewBucket(
					fsWriter,
					subtaskIndex,
					bucketPath,
					initMaxPartCounter,
					partFileSize,
					rolloverInterval,
					inactivityInterval,
					writer);
			activeBuckets.put(bucketPath, bucket);
		}

		bucket.write(value, currentProcessingTime);

		// we update the counter here because as buckets become inactive and
		// get removed in the initializeState(), at the time we snapshot they
		// may not be there to take them into account during checkpointing.
		updateMaxPartCounter(bucket.getPartCounter());
	}

	@Override
	public void close() throws Exception {
		if (activeBuckets != null) {
			// here we cannot "commit" because this is also called in case of failures.
			for (Map.Entry<Path, Bucket<IN>> entry : activeBuckets.entrySet()) {
				entry.getValue().closeCurrentChunk();
			}
		}
	}

	private void initFileSystem() throws IOException {
		if (fsWriter == null) {
			fsWriter = FileSystem.get(basePath.toUri()).createRecoverableWriter();
		}
	}

	private void updateMaxPartCounter(long candidate) {
		if (candidate > maxPartCounterUsed) {
			this.maxPartCounterUsed = candidate;
		}
	}
}
