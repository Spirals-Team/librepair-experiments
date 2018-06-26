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

package org.apache.flink.streaming.connectors.fs.bucketing;

import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.OperatorStateStore;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.InputTypeConfigurable;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.fs.hdfs.HadoopFileSystem;
import org.apache.flink.runtime.state.CheckpointListener;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
import org.apache.flink.streaming.connectors.fs.Clock;
import org.apache.flink.streaming.connectors.fs.SequenceFileWriter;
import org.apache.flink.streaming.connectors.fs.StringWriter;
import org.apache.flink.streaming.connectors.fs.Writer;
import org.apache.flink.streaming.runtime.tasks.ProcessingTimeCallback;
import org.apache.flink.streaming.runtime.tasks.ProcessingTimeService;
import org.apache.flink.util.Preconditions;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Sink that emits its input elements to {@link FileSystem} files within
 * buckets. This is integrated with the checkpointing mechanism to provide exactly once semantics.
 *
 *
 * <p>When creating the sink a {@code basePath} must be specified. The base directory contains
 * one directory for every bucket. The bucket directories themselves contain several part files,
 * one for each parallel subtask of the sink. These part files contain the actual output data.
 *
 *
 * <p>The sink uses a {@link Bucketer} to determine in which bucket directory each element should
 * be written to inside the base directory. The {@code Bucketer} can, for example, use time or
 * a property of the element to determine the bucket directory. The default {@code Bucketer} is a
 * {@link DateTimeBucketer} which will create one new bucket every hour. You can specify
 * a custom {@code Bucketer} using {@link #setBucketer(Bucketer)}. For example, use the
 * {@link BasePathBucketer} if you don't want to have buckets but still want to write part-files
 * in a fault-tolerant way.
 *
 *
 * <p>The filenames of the part files contain the part prefix, the parallel subtask index of the sink
 * and a rolling counter. For example the file {@code "part-1-17"} contains the data from
 * {@code subtask 1} of the sink and is the {@code 17th} bucket created by that subtask. Per default
 * the part prefix is {@code "part"} but this can be configured using {@link #setPartPrefix(String)}.
 * When a part file becomes bigger than the user-specified batch size or when the part file becomes older
 * than the user-specified roll over interval the current part file is closed, the part counter is increased
 * and a new part file is created. The batch size defaults to {@code 384MB}, this can be configured
 * using {@link #setBatchSize(long)}. The roll over interval defaults to {@code Long.MAX_VALUE} and
 * this can be configured using {@link #setBatchRolloverInterval(long)}.
 *
 *
 * <p>In some scenarios, the open buckets are required to change based on time. In these cases, the sink
 * needs to determine when a bucket has become inactive, in order to flush and close the part file.
 * To support this there are two configurable settings:
 * <ol>
 *     <li>the frequency to check for inactive buckets, configured by {@link #setInactiveBucketCheckInterval(long)},
 *     and</li>
 *     <li>the minimum amount of time a bucket has to not receive any data before it is considered inactive,
 *     configured by {@link #setInactiveBucketThreshold(long)}</li>
 * </ol>
 * Both of these parameters default to {@code 60, 000 ms}, or {@code 1 min}.
 *
 *
 * <p>Part files can be in one of three states: {@code in-progress}, {@code pending} or {@code finished}.
 * The reason for this is how the sink works together with the checkpointing mechanism to provide exactly-once
 * semantics and fault-tolerance. The part file that is currently being written to is {@code in-progress}. Once
 * a part file is closed for writing it becomes {@code pending}. When a checkpoint is successful the currently
 * pending files will be moved to {@code finished}.
 *
 *
 * <p>If case of a failure, and in order to guarantee exactly-once semantics, the sink should roll back to the state it
 * had when that last successful checkpoint occurred. To this end, when restoring, the restored files in {@code pending}
 * state are transferred into the {@code finished} state while any {@code in-progress} files are rolled back, so that
 * they do not contain data that arrived after the checkpoint from which we restore. If the {@code FileSystem} supports
 * the {@code truncate()} method this will be used to reset the file back to its previous state. If not, a special
 * file with the same name as the part file and the suffix {@code ".valid-length"} will be created that contains the
 * length up to which the file contains valid data. When reading the file, it must be ensured that it is only read up
 * to that point. The prefixes and suffixes for the different file states and valid-length files can be configured
 * using the adequate setter method, e.g. {@link #setPendingSuffix(String)}.
 *
 *
 * <p><b>NOTE:</b>
 * <ol>
 *     <li>
 *         If checkpointing is not enabled the pending files will never be moved to the finished state. In that case,
 *         the pending suffix/prefix can be set to {@code ""} to make the sink work in a non-fault-tolerant way but
 *         still provide output without prefixes and suffixes.
 *     </li>
 *     <li>
 *         The part files are written using an instance of {@link Writer}. By default, a
 *         {@link StringWriter} is used, which writes the result of {@code toString()} for
 *         every element, separated by newlines. You can configure the writer using the
 *         {@link #setWriter(Writer)}. For example, {@link SequenceFileWriter}
 *         can be used to write Hadoop {@code SequenceFiles}.
 *     </li>
 *     <li>
 *       	{@link State#closePartFilesByTime(FileSystem, int, long)} closes buckets that have not been written to for
 *       	{@code inactiveBucketThreshold} or if they are older than {@code batchRolloverInterval}.
 *     </li>
 * </ol>
 *
 *
 * <p>Example:
 * <pre>{@code
 *     new BucketingSink<Tuple2<IntWritable, Text>>(outPath)
 *         .setWriter(new SequenceFileWriter<IntWritable, Text>())
 *         .setBucketer(new DateTimeBucketer("yyyy-MM-dd--HHmm")
 * }</pre>
 *
 * <p>This will create a sink that writes to {@code SequenceFiles} and rolls every minute.
 *
 * @see DateTimeBucketer
 * @see StringWriter
 * @see SequenceFileWriter
 *
 * @param <T> Type of the elements emitted by this sink
 */
public class BucketingSink<T>
		extends RichSinkFunction<T>
		implements InputTypeConfigurable, CheckpointedFunction, CheckpointListener, ProcessingTimeCallback {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(BucketingSink.class);

	// --------------------------------------------------------------------------------------------
	//  User configuration values
	// --------------------------------------------------------------------------------------------
	// These are initialized with some defaults but are meant to be changeable by the user

	/**
	 * The default maximum size of part files (currently {@code 384 MB}).
	 */
	private static final long DEFAULT_BATCH_SIZE = 1024L * 1024L * 384L;

	/**
	 * The default time between checks for inactive buckets. By default, {60 sec}.
	 */
	private static final long DEFAULT_INACTIVE_BUCKET_CHECK_INTERVAL_MS = 60L * 1000L;

	/**
	 * The default threshold (in {@code ms}) for marking a bucket as inactive and
	 * closing its part files. By default, {60 sec}.
	 */
	private static final long DEFAULT_INACTIVE_BUCKET_THRESHOLD_MS = 60L * 1000L;

	/**
	 * The suffix for {@code in-progress} part files. These are files we are
	 * currently writing to, but which were not yet confirmed by a checkpoint.
	 */
	private static final String DEFAULT_IN_PROGRESS_SUFFIX = ".in-progress";

	/**
	 * The prefix for {@code in-progress} part files. These are files we are
	 * currently writing to, but which were not yet confirmed by a checkpoint.
	 */
	private static final String DEFAULT_IN_PROGRESS_PREFIX = "_";

	/**
	 * The suffix for {@code pending} part files. These are closed files that we are
	 * not currently writing to (inactive or reached {@link #batchSize}), but which
	 * were not yet confirmed by a checkpoint.
	 */
	private static final String DEFAULT_PENDING_SUFFIX = ".pending";

	/**
	 * The prefix for {@code pending} part files. These are closed files that we are
	 * not currently writing to (inactive or reached {@link #batchSize}), but which
	 * were not yet confirmed by a checkpoint.
	 */
	private static final String DEFAULT_PENDING_PREFIX = "_";

	/**
	 * When {@code truncate()} is not supported by the used {@link FileSystem}, we create
	 * a file along the part file with this suffix that contains the length up to which
	 * the part file is valid.
	 */
	private static final String DEFAULT_VALID_SUFFIX = ".valid-length";

	/**
	 * When {@code truncate()} is not supported by the used {@link FileSystem}, we create
	 * a file along the part file with this preffix that contains the length up to which
	 * the part file is valid.
	 */
	private static final String DEFAULT_VALID_PREFIX = "_";

	/**
	 * The default prefix for part files.
	 */
	private static final String DEFAULT_PART_PREFIX = "part";

	/**
	 * The default suffix for part files.
	 */
	private static final String DEFAULT_PART_SUFFIX = null;

	/**
	 * The default timeout for asynchronous operations such as recoverLease and truncate (in {@code ms}).
	 */
	private static final long DEFAULT_ASYNC_TIMEOUT_MS = 60L * 1000L;

	/**
	 * The default time interval at which part files are written to the filesystem.
	 */
	private static final long DEFAULT_BATCH_ROLLOVER_INTERVAL = Long.MAX_VALUE;

	/**
	 * The base {@code Path} that stores all bucket directories.
	 */
	private final String basePath;

	/**
	 * The {@code Bucketer} that is used to determine the path of bucket directories.
	 */
	private Bucketer<T> bucketer;

	/**
	 * We have a template and call duplicate() for each parallel writer in open() to get the actual
	 * writer that is used for the part files.
	 */
	private Writer<T> writerTemplate;

	private long batchSize = DEFAULT_BATCH_SIZE;
	private long inactiveBucketCheckInterval = DEFAULT_INACTIVE_BUCKET_CHECK_INTERVAL_MS;
	private long inactiveBucketThreshold = DEFAULT_INACTIVE_BUCKET_THRESHOLD_MS;
	private long batchRolloverInterval = DEFAULT_BATCH_ROLLOVER_INTERVAL;

	// These are the actually configured prefixes/suffixes
	private String inProgressSuffix = DEFAULT_IN_PROGRESS_SUFFIX;
	private String inProgressPrefix = DEFAULT_IN_PROGRESS_PREFIX;

	private String pendingSuffix = DEFAULT_PENDING_SUFFIX;
	private String pendingPrefix = DEFAULT_PENDING_PREFIX;

	private String validLengthSuffix = DEFAULT_VALID_SUFFIX;
	private String validLengthPrefix = DEFAULT_VALID_PREFIX;

	private String partPrefix = DEFAULT_PART_PREFIX;
	private String partSuffix = DEFAULT_PART_SUFFIX;

	private boolean useTruncate = true;

	/**
	 * The timeout for asynchronous operations such as recoverLease and truncate (in {@code ms}).
	 */
	private long asyncTimeout = DEFAULT_ASYNC_TIMEOUT_MS;

	// --------------------------------------------------------------------------------------------
	//  Internal fields (not configurable by user)
	// -------------------------------------------§-------------------------------------------------

	/**
	 * We use reflection to get the .truncate() method, this is only available starting with Hadoop 2.7 .
	 */
	private transient Method refTruncate;

	/**
	 * The state object that is handled by Flink from snapshot/restore. This contains state for
	 * every open bucket: the current in-progress part file path, its valid length and the pending part files.
	 */
	private transient State<T> state;

	private transient ListState<State<T>> restoredBucketStates;

	/**
	 * User-defined FileSystem parameters.
	 */
	@Nullable
	private Configuration fsConfig;

	/**
	 * The FileSystem reference.
	 */
	private transient FileSystem fileSystem;

	private transient Clock clock;

	private transient ProcessingTimeService processingTimeService;

	/**
	 * Creates a new {@code BucketingSink} that writes files to the given base directory.
	 *
	 *
	 * <p>This uses a{@link DateTimeBucketer} as {@link Bucketer} and a {@link StringWriter} has writer.
	 * The maximum bucket size is set to 384 MB.
	 *
	 * @param basePath The directory to which to write the bucket files.
	 */
	public BucketingSink(String basePath) {
		this.basePath = basePath;
		this.bucketer = new DateTimeBucketer<>();
		this.writerTemplate = new StringWriter<>();
	}

	/**
	 * Specify a custom {@code Configuration} that will be used when creating
	 * the {@link FileSystem} for writing.
	 */
	public BucketingSink<T> setFSConfig(Configuration config) {
		this.fsConfig = new Configuration();
		fsConfig.addAll(config);
		return this;
	}

	/**
	 * Specify a custom {@code Configuration} that will be used when creating
	 * the {@link FileSystem} for writing.
	 */
	public BucketingSink<T> setFSConfig(org.apache.hadoop.conf.Configuration config) {
		this.fsConfig = new Configuration();
		for (Map.Entry<String, String> entry : config) {
			fsConfig.setString(entry.getKey(), entry.getValue());
		}
		return this;
	}

	@Override
	public void setInputType(TypeInformation<?> type, ExecutionConfig executionConfig) {
		if (writerTemplate instanceof InputTypeConfigurable) {
			((InputTypeConfigurable) writerTemplate).setInputType(type, executionConfig);
		}
	}

	@Override
	public void initializeState(FunctionInitializationContext context) throws Exception {
		Preconditions.checkArgument(restoredBucketStates == null, "The operator has already been initialized.");

		try {
			initFileSystem();
		} catch (IOException e) {
			LOG.error("Error while creating FileSystem when initializing the state of the BucketingSink.", e);
			throw new RuntimeException("Error while creating FileSystem when initializing the state of the BucketingSink.", e);
		}

		if (refTruncate == null && useTruncate) {
			this.refTruncate = reflectTruncate(fileSystem);
		}

		OperatorStateStore stateStore = context.getOperatorStateStore();
		restoredBucketStates = stateStore.getSerializableListState("bucket-states");

		int subtaskIndex = getRuntimeContext().getIndexOfThisSubtask();
		if (context.isRestored()) {
			LOG.info("Restoring state for the {} (taskIdx={}).", getClass().getSimpleName(), subtaskIndex);

			for (State<T> recoveredState : restoredBucketStates.get()) {
				recoveredState.handleRestoredBucketState(fileSystem, refTruncate, asyncTimeout);
				if (LOG.isDebugEnabled()) {
					LOG.debug("{} idx {} restored {}", getClass().getSimpleName(), subtaskIndex, recoveredState);
				}
			}
		}

		this.state = new State<>(
				inProgressSuffix,
				inProgressPrefix,
				pendingSuffix,
				pendingPrefix,
				validLengthSuffix,
				validLengthPrefix,
				partPrefix,
				partSuffix,
				batchSize,
				batchRolloverInterval,
				inactiveBucketThreshold);
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);

		processingTimeService =
				((StreamingRuntimeContext) getRuntimeContext()).getProcessingTimeService();

		long currentProcessingTime = processingTimeService.getCurrentProcessingTime();

		processingTimeService.registerTimer(currentProcessingTime + inactiveBucketCheckInterval, this);

		this.clock = () -> processingTimeService.getCurrentProcessingTime();
	}

	/**
	 * Create a file system with the user-defined {@code HDFS} configuration.
	 * @throws IOException If something goes wrong during filesystem initialization.
	 */
	private void initFileSystem() throws IOException {
		if (fileSystem == null) {
			Path path = new Path(basePath);
			fileSystem = createHadoopFileSystem(path, fsConfig);
		}
	}

	@Override
	public void close() throws Exception {
		if (state != null) {
			state.close(fileSystem);
		}
	}

	@Override
	public void invoke(T value) throws Exception {
		final Path bucketPath = bucketer.getBucketPath(clock, new Path(basePath), value);
		final long currentProcessingTime = processingTimeService.getCurrentProcessingTime();

		final BucketingSink.BucketState<T> bucketState = state.getOrCreateBucketState(
				getRuntimeContext().getIndexOfThisSubtask(),
				fileSystem,
				writerTemplate,
				bucketPath,
				currentProcessingTime);

		bucketState.write(value, currentProcessingTime);
	}

	@Override
	public void onProcessingTime(long timestamp) throws Exception {
		long currentProcessingTime = clock.currentTimeMillis();

		state.closePartFilesByTime(
				fileSystem,
				getRuntimeContext().getIndexOfThisSubtask(),
				currentProcessingTime);

		processingTimeService.registerTimer(currentProcessingTime + inactiveBucketCheckInterval, this);
	}

	@Override
	public void notifyCheckpointComplete(long checkpointId) throws Exception {
		Preconditions.checkState(fileSystem != null);
		state.commitPendingFilesForCheckpoint(checkpointId, fileSystem);
	}

	@Override
	public void snapshotState(FunctionSnapshotContext context) throws Exception {
		Preconditions.checkNotNull(restoredBucketStates, "The operator has not been properly initialized.");

		state.preCommitPendingFilesForCheckpoint(context.getCheckpointId());

		restoredBucketStates.clear();
		restoredBucketStates.add(state);

		final int subtaskIdx = getRuntimeContext().getIndexOfThisSubtask();
		if (LOG.isDebugEnabled()) {
			LOG.debug("{} idx {} checkpointed {}.", getClass().getSimpleName(), subtaskIdx, state);
		}
	}

	// --------------------------------------------------------------------------------------------
	//  Setters for User configuration values
	// --------------------------------------------------------------------------------------------

	/**
	 * Sets the maximum bucket size in bytes.
	 *
	 *
	 * <p>When a bucket part file becomes larger than this size a new bucket part file is started and
	 * the old one is closed. The name of the bucket files depends on the {@link Bucketer}.
	 *
	 * @param batchSize The bucket part file size in bytes.
	 */
	public BucketingSink<T> setBatchSize(long batchSize) {
		this.batchSize = batchSize;
		return this;
	}

	/**
	 * Sets the roll over interval in milliseconds.
	 *
	 *
	 * <p>When a bucket part file is older than the roll over interval, a new bucket part file is
	 * started and the old one is closed. The name of the bucket file depends on the {@link Bucketer}.
	 * Additionally, the old part file is also closed if the bucket is not written to for a minimum of
	 * {@code inactiveBucketThreshold} ms.
	 *
	 * @param batchRolloverInterval The roll over interval in milliseconds
	 */
	public BucketingSink<T> setBatchRolloverInterval(long batchRolloverInterval) {
		Preconditions.checkArgument(batchRolloverInterval > 0L);
		this.batchRolloverInterval = batchRolloverInterval;
		return this;
	}

	/**
	 * Sets the default time between checks for inactive buckets.
	 *
	 * @param interval The timeout, in milliseconds.
	 */
	public BucketingSink<T> setInactiveBucketCheckInterval(long interval) {
		Preconditions.checkArgument(interval > 0L);
		this.inactiveBucketCheckInterval = interval;
		return this;
	}

	/**
	 * Sets the default threshold for marking a bucket as inactive and closing its part files.
	 * Buckets which haven't been written to for at least this period of time become inactive.
	 * Additionally, part files for the bucket are also closed if the bucket is older than
	 * {@code batchRolloverInterval} ms.
	 *
	 * @param threshold The timeout, in milliseconds.
	 */
	public BucketingSink<T> setInactiveBucketThreshold(long threshold) {
		Preconditions.checkArgument(threshold > 0L);
		this.inactiveBucketThreshold = threshold;
		return this;
	}

	/**
	 * Sets the {@link Bucketer} to use for determining the bucket files to write to.
	 *
	 * @param bucketer The bucketer to use.
	 */
	public BucketingSink<T> setBucketer(Bucketer<T> bucketer) {
		this.bucketer = Preconditions.checkNotNull(bucketer);
		return this;
	}

	/**
	 * Sets the {@link Writer} to be used for writing the incoming elements to bucket files.
	 *
	 * @param writer The {@code Writer} to use.
	 */
	public BucketingSink<T> setWriter(Writer<T> writer) {
		this.writerTemplate = Preconditions.checkNotNull(writer);
		return this;
	}

	/**
	 * Sets the suffix of in-progress part files. The default is {@code "in-progress"}.
	 */
	public BucketingSink<T> setInProgressSuffix(String inProgressSuffix) {
		this.inProgressSuffix = inProgressSuffix;
		return this;
	}

	/**
	 * Sets the prefix of in-progress part files. The default is {@code "_"}.
	 */
	public BucketingSink<T> setInProgressPrefix(String inProgressPrefix) {
		this.inProgressPrefix = inProgressPrefix;
		return this;
	}

	/**
	 * Sets the suffix of pending part files. The default is {@code ".pending"}.
	 */
	public BucketingSink<T> setPendingSuffix(String pendingSuffix) {
		this.pendingSuffix = pendingSuffix;
		return this;
	}

	/**
	 * Sets the prefix of pending part files. The default is {@code "_"}.
	 */
	public BucketingSink<T> setPendingPrefix(String pendingPrefix) {
		this.pendingPrefix = pendingPrefix;
		return this;
	}

	/**
	 * Sets the suffix of valid-length files. The default is {@code ".valid-length"}.
	 */
	public BucketingSink<T> setValidLengthSuffix(String validLengthSuffix) {
		this.validLengthSuffix = validLengthSuffix;
		return this;
	}

	/**
	 * Sets the prefix of valid-length files. The default is {@code "_"}.
	 */
	public BucketingSink<T> setValidLengthPrefix(String validLengthPrefix) {
		this.validLengthPrefix = validLengthPrefix;
		return this;
	}

	/**
	 * Sets the prefix of part files.  The default is no suffix.
	 */
	public BucketingSink<T> setPartSuffix(String partSuffix) {
		this.partSuffix = partSuffix;
		return this;
	}

	/**
	 * Sets the prefix of part files.  The default is {@code "part"}.
	 */
	public BucketingSink<T> setPartPrefix(String partPrefix) {
		this.partPrefix = partPrefix;
		return this;
	}

	/**
	 * Sets whether to use {@code FileSystem.truncate()} to truncate written bucket files back to
	 * a consistent state in case of a restore from checkpoint. If {@code truncate()} is not used
	 * this sink will write valid-length files for corresponding bucket files that have to be used
	 * when reading from bucket files to make sure to not read too far.
	 */
	public BucketingSink<T> setUseTruncate(boolean useTruncate) {
		this.useTruncate = useTruncate;
		return this;
	}

	/**
	 * Disable cleanup of leftover in-progress/pending files when the sink is opened.
	 *
	 *
	 * <p>This should only be disabled if using the sink without checkpoints, to not remove
	 * the files already in the directory.
	 *
	 * @deprecated This option is deprecated and remains only for backwards compatibility.
	 * We do not clean up lingering files anymore.
	 */
	@Deprecated
	public BucketingSink<T> disableCleanupOnOpen() {
		return this;
	}

	/**
	 * Sets the default timeout for asynchronous operations such as recoverLease and truncate.
	 *
	 * @param timeout The timeout, in milliseconds.
	 */
	public BucketingSink<T> setAsyncTimeout(long timeout) {
		Preconditions.checkArgument(timeout > 0L);
		this.asyncTimeout = timeout;
		return this;
	}

	@VisibleForTesting
	State<T> getState() {
		return state;
	}

	// --------------------------------------------------------------------------------------------
	//  Internal Classes
	// --------------------------------------------------------------------------------------------

	/**
	 * This is used during snapshot/restore to keep track of in-progress buckets.
	 * For each bucket, we maintain a state.
	 */
	static final class State<T> implements Serializable {

		private static final long serialVersionUID = 1L;

		private final String inProgressSuffix;
		private final String inProgressPrefix;

		private final String pendingSuffix;
		private final String pendingPrefix;

		private final String validLengthSuffix;
		private final String validLengthPrefix;

		private final String partPrefix;
		private final String partSuffix;

		private final long batchSize;
		private final long batchRolloverInterval;
		private final long inactiveBucketThreshold;

		private long maxPartCounter = Long.MAX_VALUE;

		/**
		 * For every bucket directory (key), we maintain a bucket state (value).
		 */
		private final Map<String, BucketingSink.BucketState<T>> bucketStates = new HashMap<>();

		State(
				final String inProgressSuffix,
				final String inProgressPrefix,
				final String pendingSuffix,
				final String pendingPrefix,
				final String validLengthSuffix,
				final String validLengthPrefix,
				final String partPrefix,
				final String partSuffix,
				final long batchSize,
				final long batchRolloverInterval,
				final long inactiveBucketThreshold
		) {
			this.inProgressPrefix = inProgressPrefix;
			this.inProgressSuffix = inProgressSuffix;

			this.pendingPrefix = pendingPrefix;
			this.pendingSuffix = pendingSuffix;

			this.validLengthPrefix = validLengthPrefix;
			this.validLengthSuffix = validLengthSuffix;

			this.partPrefix = partPrefix;
			this.partSuffix = partSuffix;

			this.batchSize = batchSize;
			this.batchRolloverInterval = batchRolloverInterval;
			this.inactiveBucketThreshold = inactiveBucketThreshold;
		}

		BucketingSink.BucketState<T> getOrCreateBucketState(
				final int subtaskIndex,
				final FileSystem fileSystem,
				final Writer<T> writerTemplate,
				final Path bucketPath,
				final long currentTime) throws Exception {

			Preconditions.checkNotNull(fileSystem);
			Preconditions.checkNotNull(writerTemplate);
			Preconditions.checkNotNull(bucketPath);

			BucketingSink.BucketState<T> bucketState = bucketStates.get(bucketPath.toString());
			if (bucketState == null) {
				bucketState = new BucketingSink.BucketState<>(
						inProgressSuffix,
						inProgressPrefix,
						pendingSuffix,
						pendingPrefix,
						validLengthSuffix,
						validLengthPrefix,
						partPrefix,
						partSuffix,
						currentTime);
				bucketStates.put(bucketPath.toString(), bucketState);
			}

			if (bucketState.shouldRoll(subtaskIndex, currentTime, batchSize, batchRolloverInterval)) {
				bucketState.openPartFile(subtaskIndex, fileSystem, currentTime, bucketPath, writerTemplate);
			}
			return bucketState;
		}



		private void handleRestoredBucketState(final FileSystem fileSystem, final Method refTruncate, final long asyncTimeout) {
			Preconditions.checkNotNull(fileSystem);
			for (BucketingSink.BucketState<T> bucketState : bucketStates.values()) {
				bucketState.handleRestoredState(fileSystem, refTruncate, asyncTimeout);
			}
		}

		void preCommitPendingFilesForCheckpoint(final long checkpointId) throws IOException {
			for (BucketingSink.BucketState<T> bucketState : bucketStates.values()) {
				updateMaxPartCounter(bucketState.getPartCounter());
				bucketState.preCommitPendingFilesForCheckpoint(checkpointId);
			}
		}

		void commitPendingFilesForCheckpoint(final long checkpointId, final FileSystem fileSystem) throws IOException {
			Preconditions.checkNotNull(fileSystem);

			final Iterator<Map.Entry<String, BucketingSink.BucketState<T>>> bucketStatesIt =
					bucketStates.entrySet().iterator();

			while (bucketStatesIt.hasNext()) {
				BucketingSink.BucketState<T> bucketState = bucketStatesIt.next().getValue();

				bucketState.commitPendingFilesForCheckpoint(checkpointId, fileSystem);
				updateMaxPartCounter(bucketState.getPartCounter());

				if (!bucketState.isActive()) {
					bucketStatesIt.remove();
				}
			}
		}

		public long getMaxPartCounter() {
			return maxPartCounter;
		}

		private void updateMaxPartCounter(long partCounter) {
			this.maxPartCounter = maxPartCounter > partCounter ? maxPartCounter : partCounter;
		}

		/**
		 * Checks for inactive buckets, and closes them. Buckets are considered inactive if they have not been
		 * written to for a period greater than {@code inactiveBucketThreshold} ms. Buckets are also closed if they are
		 * older than {@code batchRolloverInterval} ms. This enables in-progress files to be moved to the pending state
		 * and be finalised on the next checkpoint.
		 */
		private void closePartFilesByTime(
				final FileSystem fileSystem,
				final int subtaskIdx,
				final long currentProcessingTime) throws Exception {
			Preconditions.checkNotNull(fileSystem);

			for (BucketingSink.BucketState<T> bucketState : bucketStates.values()) {
				if ((bucketState.getLastWrittenToTime() < currentProcessingTime - inactiveBucketThreshold)
						|| (bucketState.getCreationTime() < currentProcessingTime - batchRolloverInterval)) {
					LOG.debug("BucketingSink {} closing bucket due to inactivity of over {} ms.", subtaskIdx, inactiveBucketThreshold);
					bucketState.closePartFile(fileSystem);
				}
			}
		}

		@Override
		public String toString() {
			return bucketStates.toString();
		}

		public void close(final FileSystem fileSystem) throws IOException {
			Preconditions.checkNotNull(fileSystem);
			for (BucketingSink.BucketState<T> bucketState : bucketStates.values()) {
				bucketState.closePartFile(fileSystem);
			}
		}

		@VisibleForTesting
		BucketingSink.BucketState<T> getBucketState(Path bucketPath) {
			return bucketStates.get(bucketPath.toString());
		}

		@VisibleForTesting
		Map<String, BucketingSink.BucketState<T>> getBucketStates() {
			return bucketStates;
		}
	}

	/**
	 * This is used for keeping track of the current in-progress buckets and files that we mark
	 * for moving from pending to final location after we get a checkpoint-complete notification.
	 */
	static final class BucketState<T> implements Serializable {

		private static final long serialVersionUID = 1L;

		private final String inProgressSuffix;
		private final String inProgressPrefix;

		private final String pendingSuffix;
		private final String pendingPrefix;

		private final String validLengthSuffix;
		private final String validLengthPrefix;

		private final String partPrefix;
		private final String partSuffix;

		/**
		 * The file that was in-progress when the last checkpoint occurred.
		 */
		private String currentFile;

		/**
		 * The valid length of the in-progress file at the time of the last checkpoint.
		 */
		private long currentFileValidLength = -1L;

		/**
		 * The time this bucket was last written to.
		 */
		private long lastWrittenToTime;

		/**
		 * The time this bucket was created.
		 */
		private long creationTime;

		/**
		 * Pending files that accumulated since the last checkpoint.
		 */
		private List<String> pendingFiles = new ArrayList<>();

		/**
		 * When doing a checkpoint we move the pending files since the last checkpoint to this map
		 * with the id of the checkpoint. When we get the checkpoint-complete notification we move
		 * pending files of completed checkpoints to their final location.
		 */
		private final Map<Long, List<String>> pendingFilesPerCheckpoint = new HashMap<>();

		/**
		 * For counting the part files inside a bucket directory. Part files follow the pattern
		 * {@code "{part-prefix}-{subtask}-{count}"}. When creating new part files we increase the counter.
		 */
		private transient long partCounter;

		/**
		 * Tracks if the writer is currently opened or closed.
		 */
		private transient boolean isWriterOpen;

		/**
		 * The actual writer that we user for writing the part files.
		 */
		private transient Writer<T> writer;

		@Override
		public String toString() {
			return
				"In-progress=" + currentFile +
					" validLength=" + currentFileValidLength +
					" pendingForNextCheckpoint=" + pendingFiles +
					" pendingForPrevCheckpoints=" + pendingFilesPerCheckpoint +
					" lastModified@" + lastWrittenToTime;
		}

		BucketState(
				final String inProgressSuffix,
				final String inProgressPrefix,
				final String pendingSuffix,
				final String pendingPrefix,
				final String validLengthSuffix,
				final String validLengthPrefix,
				final String partPrefix,
				final String partSuffix,
				final long lastWrittenToTime) {

			this.inProgressPrefix = inProgressPrefix;
			this.inProgressSuffix = inProgressSuffix;

			this.pendingPrefix = pendingPrefix;
			this.pendingSuffix = pendingSuffix;

			this.validLengthPrefix = validLengthPrefix;
			this.validLengthSuffix = validLengthSuffix;

			this.partPrefix = partPrefix;
			this.partSuffix = partSuffix;

			this.lastWrittenToTime = lastWrittenToTime;
		}

		public long getLastWrittenToTime() {
			return lastWrittenToTime;
		}

		public long getCreationTime() {
			return creationTime;
		}

		public long getPartCounter() {
			return partCounter;
		}

		boolean isActive() {
			return isWriterOpen || !pendingFiles.isEmpty() || !pendingFilesPerCheckpoint.isEmpty();
		}

		/**
		 * Closes the current part file and opens a new one with a new bucket path, as returned by the
		 * {@link Bucketer}. If the bucket is not new, then this will create a new file with the same path
		 * as its predecessor, but with an increased rolling counter (see {@link BucketingSink}.
		 */
		void openPartFile(
				int subtaskIndex,
				FileSystem fileSystem,
				long currentProcessingTime,
				Path bucketPath,
				Writer<T> writerTemplate) throws Exception {

			closePartFile(fileSystem);

			// The following loop tries different partCounter values in ascending order until it reaches the minimum
			// that is not yet used. This works since there is only one parallel subtask that tries names with this
			// subtask id. Otherwise we would run into concurrency issues here. This is aligned with the way we now
			// clean the base directory in case of rescaling.

			Path partPath = new Path(bucketPath, partPrefix + "-" + subtaskIndex + "-" + partCounter);

			while (fileSystem.exists(partPath) ||
					fileSystem.exists(getPendingPathFor(partPath)) ||
					fileSystem.exists(getInProgressPathFor(partPath))) {
				partCounter++;
				partPath = new Path(bucketPath, partPrefix + "-" + subtaskIndex + "-" + partCounter);
			}

			// Record the creation time of the bucket
			this.creationTime = currentProcessingTime;

			if (partSuffix != null) {
				partPath = partPath.suffix(partSuffix);
			}

			// increase, so we don't have to check for this name next time
			partCounter++;

			LOG.debug("Next part path is {}", partPath.toString());
			this.currentFile = partPath.toString();

			Path inProgressPath = getInProgressPathFor(partPath);
			if (writer == null) {
				writer = writerTemplate.duplicate();
				if (writer == null) {
					throw new UnsupportedOperationException(
							"Could not duplicate writer. " +
									"Class '" + writerTemplate.getClass().getCanonicalName() + "' must implement the 'Writer.duplicate()' method."
					);
				}
			}
			writer.open(fileSystem, inProgressPath);
			isWriterOpen = true;
		}

		void write(T element, long currentTime) throws IOException {
			writer.write(element);
			lastWrittenToTime = currentTime;
		}

		/**
		 * Closes the current part file and moves it from the in-progress state to the pending state.
		 */
		void closePartFile(final FileSystem fileSystem) throws IOException {
			if (isWriterOpen) {
				writer.close();
				isWriterOpen = false;
			}

			if (currentFile != null) {
				Path currentPartPath = new Path(currentFile);
				Path inProgressPath = getInProgressPathFor(currentPartPath);
				Path pendingPath = getPendingPathFor(currentPartPath);

				// this is ok for s3 but inefficient
				fileSystem.rename(inProgressPath, pendingPath);
				LOG.debug("Moving in-progress bucket {} to pending file {}",
						inProgressPath,
						pendingPath);
				pendingFiles.add(currentPartPath.toString());
				currentFile = null;
			}
		}

		/**
		 * Returns {@code true} if the current {@code part-file} should be closed and a new should be created.
		 * This happens if:
		 * <ol>
		 *     <li>no file is created yet for the task to write to, or</li>
		 *     <li>the current file has reached the maximum bucket size.</li>
		 *     <li>the current file is older than roll over interval</li>
		 * </ol>
		 */
		boolean shouldRoll(int subtaskIndex, long currentProcessingTime, long batchSize, long batchRolloverInterval) throws IOException {
			if (!isWriterOpen) {
				LOG.debug("BucketingSink {} starting new bucket.", subtaskIndex);
				return true;
			}

			long writePosition = writer.getPos();
			if (writePosition > batchSize) {
				LOG.debug("BucketingSink {} starting new bucket because file position {} is above batch size {}.",
						subtaskIndex, writePosition, batchSize);
				return true;
			}

			if (currentProcessingTime - creationTime > batchRolloverInterval) {
				LOG.debug("BucketingSink {} starting new bucket because file is older than roll over interval {}.",
						subtaskIndex, batchRolloverInterval);
				return true;
			}
			return false;
		}

		void preCommitPendingFilesForCheckpoint(final long checkpointId) throws IOException {
			if (isWriterOpen) {
				currentFileValidLength = writer.flush();
			}
			pendingFilesPerCheckpoint.put(checkpointId, pendingFiles);
			pendingFiles = new ArrayList<>();
		}

		void commitPendingFilesForCheckpoint(final long checkpointId, final FileSystem fileSystem) throws IOException {
			final Iterator<Map.Entry<Long, List<String>>> pendingCheckpointsIt = pendingFilesPerCheckpoint.entrySet().iterator();

			while (pendingCheckpointsIt.hasNext()) {

				Map.Entry<Long, List<String>> entry = pendingCheckpointsIt.next();
				Long pastCheckpointId = entry.getKey();
				List<String> pendingPaths = entry.getValue();

				if (pastCheckpointId <= checkpointId) {
					LOG.debug("Moving pending files to final location for checkpoint {}", pastCheckpointId);

					for (String filename : pendingPaths) {
						Path finalPath = new Path(filename);
						Path pendingPath = getPendingPathFor(finalPath);

						// this is ok for s3 but inefficient
						fileSystem.rename(pendingPath, finalPath);
						LOG.debug(
								"Moving pending file {} to final location having completed checkpoint {}.",
								pendingPath,
								pastCheckpointId);
					}
					pendingCheckpointsIt.remove();
				}
			}
		}

		void handleRestoredState(final FileSystem fileSystem, final Method refTruncate, final long asyncTimeout) {

			pendingFiles.clear();

			handlePendingInProgressFile(fileSystem, refTruncate, asyncTimeout);

			// Now that we've restored the bucket to a valid state, reset the current file info
			currentFile = null;
			currentFileValidLength = -1;
			isWriterOpen = false;

			commitPendingFilesForPreviousCheckpoints(fileSystem);

			pendingFilesPerCheckpoint.clear();
		}

		private void handlePendingInProgressFile(
				final FileSystem fileSystem,
				final Method refTruncate,
				final long asyncTimeout) {

			if (currentFile != null) {

				// We were writing to a file when the last checkpoint occurred. This file can either
				// be still in-progress or became a pending file at some point after the checkpoint.
				// Either way, we have to truncate it back to a valid state (or write a .valid-length
				// file that specifies up to which length it is valid) and rename it to the final name
				// before starting a new bucket file.

				Path partPath = new Path(currentFile);
				try {
					Path partPendingPath = getPendingPathFor(partPath);
					Path partInProgressPath = getInProgressPathFor(partPath);

					if (fileSystem.exists(partPendingPath)) {
						LOG.debug("In-progress file {} has been moved to pending after checkpoint, moving to final location.", partPath);
						// has been moved to pending in the mean time, rename to final location
						fileSystem.rename(partPendingPath, partPath);
					} else if (fileSystem.exists(partInProgressPath)) {
						LOG.debug("In-progress file {} is still in-progress, moving to final location.", partPath);
						// it was still in progress, rename to final path
						fileSystem.rename(partInProgressPath, partPath);
					} else if (fileSystem.exists(partPath)) {
						LOG.debug("In-Progress file {} was already moved to final location {}.", currentFile, partPath);
					} else {
						LOG.debug("In-Progress file {} was neither moved to pending nor is still in progress. Possibly, " +
								"it was moved to final location by a previous snapshot restore", currentFile);
					}

					// truncate it or write a ".valid-length" file to specify up to which point it is valid
					if (refTruncate != null) {
						LOG.debug("Truncating {} to valid length {}", partPath, currentFileValidLength);
						// some-one else might still hold the lease from a previous try, we are
						// recovering, after all ...
						if (fileSystem instanceof DistributedFileSystem) {
							DistributedFileSystem dfs = (DistributedFileSystem) fileSystem;
							LOG.debug("Trying to recover file lease {}", partPath);
							dfs.recoverLease(partPath);
							boolean isclosed = dfs.isFileClosed(partPath);
							StopWatch sw = new StopWatch();
							sw.start();
							while (!isclosed) {
								if (sw.getTime() > asyncTimeout) {
									break;
								}
								try {
									Thread.sleep(500);
								} catch (InterruptedException e1) {
									// ignore it
								}
								isclosed = dfs.isFileClosed(partPath);
							}
						}
						Boolean truncated = (Boolean) refTruncate.invoke(fileSystem, partPath, currentFileValidLength);
						if (!truncated) {
							LOG.debug("Truncate did not immediately complete for {}, waiting...", partPath);

							// we must wait for the asynchronous truncate operation to complete
							StopWatch sw = new StopWatch();
							sw.start();
							long newLen = fileSystem.getFileStatus(partPath).getLen();
							while (newLen != currentFileValidLength) {
								if (sw.getTime() > asyncTimeout) {
									break;
								}
								try {
									Thread.sleep(500);
								} catch (InterruptedException e1) {
									// ignore it
								}
								newLen = fileSystem.getFileStatus(partPath).getLen();
							}
							if (newLen != currentFileValidLength) {
								throw new RuntimeException("Truncate did not truncate to right length. Should be " + currentFileValidLength + " is " + newLen + ".");
							}
						}
					} else {
						Path validLengthFilePath = getValidLengthPathFor(partPath);
						if (!fileSystem.exists(validLengthFilePath) && fileSystem.exists(partPath)) {
							LOG.debug("Writing valid-length file for {} to specify valid length {}", partPath, currentFileValidLength);
							FSDataOutputStream lengthFileOut = fileSystem.create(validLengthFilePath);
							lengthFileOut.writeUTF(Long.toString(currentFileValidLength));
							lengthFileOut.close();
						}
					}
				} catch (IOException e) {
					LOG.error("Error while restoring BucketingSink state.", e);
					throw new RuntimeException("Error while restoring BucketingSink state.", e);
				} catch (InvocationTargetException | IllegalAccessException e) {
					LOG.error("Could not invoke truncate.", e);
					throw new RuntimeException("Could not invoke truncate.", e);
				}
			}
		}

		private void commitPendingFilesForPreviousCheckpoints(final FileSystem fileSystem) {
			// Move files that are confirmed by a checkpoint but did not get moved to final location
			// because the checkpoint notification did not happen before a failure

			LOG.debug("Moving pending files to final location on restore.");

			Set<Long> pastCheckpointIds = pendingFilesPerCheckpoint.keySet();
			for (Long pastCheckpointId : pastCheckpointIds) {
				// All the pending files are buckets that have been completed but are waiting to be renamed
				// to their final name
				for (String filename : pendingFilesPerCheckpoint.get(pastCheckpointId)) {
					Path finalPath = new Path(filename);
					Path pendingPath = getPendingPathFor(finalPath);

					try {
						if (fileSystem.exists(pendingPath)) {
							LOG.debug("Restoring BucketingSink State: Moving pending file {} to final location after complete checkpoint {}.", pendingPath, pastCheckpointId);
							fileSystem.rename(pendingPath, finalPath);
						}
					} catch (IOException e) {
						LOG.error("Restoring BucketingSink State: Error while renaming pending file {} to final path {}: {}", pendingPath, finalPath, e);
						throw new RuntimeException("Error while renaming pending file " + pendingPath + " to final path " + finalPath, e);
					}
				}
			}
		}

		private Path getPendingPathFor(Path path) {
			return new Path(path.getParent(), pendingPrefix + path.getName()).suffix(pendingSuffix);
		}

		private Path getInProgressPathFor(Path path) {
			return new Path(path.getParent(), inProgressPrefix + path.getName()).suffix(inProgressSuffix);
		}

		private Path getValidLengthPathFor(Path path) {
			return new Path(path.getParent(), validLengthPrefix + path.getName()).suffix(validLengthSuffix);
		}

		@VisibleForTesting
		List<String> getPendingFiles() {
			return pendingFiles;
		}

		@VisibleForTesting
		Map<Long, List<String>> getPendingFilesPerCheckpoint() {
			return pendingFilesPerCheckpoint;
		}

		@VisibleForTesting
		String getCurrentFile() {
			return currentFile;
		}

		@VisibleForTesting
		long getCurrentFileValidLength() {
			return currentFileValidLength;
		}
	}

	// ------------------------------------------------------------------------
	//  Utilities
	// ------------------------------------------------------------------------

	public static FileSystem createHadoopFileSystem(
			Path path,
			@Nullable Configuration extraUserConf) throws IOException {

		// try to get the Hadoop File System via the Flink File Systems
		// that way we get the proper configuration

		final org.apache.flink.core.fs.FileSystem flinkFs =
				org.apache.flink.core.fs.FileSystem.getUnguardedFileSystem(path.toUri());
		final FileSystem hadoopFs = (flinkFs instanceof HadoopFileSystem) ?
				((HadoopFileSystem) flinkFs).getHadoopFileSystem() : null;

		// fast path: if the Flink file system wraps Hadoop anyways and we need no extra config,
		// then we use it directly
		if (extraUserConf == null && hadoopFs != null) {
			return hadoopFs;
		}
		else {
			// we need to re-instantiate the Hadoop file system, because we either have
			// a special config, or the Path gave us a Flink FS that is not backed by
			// Hadoop (like file://)

			final org.apache.hadoop.conf.Configuration hadoopConf;
			if (hadoopFs != null) {
				// have a Hadoop FS but need to apply extra config
				hadoopConf = hadoopFs.getConf();
			}
			else {
				// the Path gave us a Flink FS that is not backed by Hadoop (like file://)
				// we need to get access to the Hadoop file system first

				// we access the Hadoop FS in Flink, which carries the proper
				// Hadoop configuration. we should get rid of this once the bucketing sink is
				// properly implemented against Flink's FS abstraction

				URI genericHdfsUri = URI.create("hdfs://localhost:12345/");
				org.apache.flink.core.fs.FileSystem accessor =
						org.apache.flink.core.fs.FileSystem.getUnguardedFileSystem(genericHdfsUri);

				if (!(accessor instanceof HadoopFileSystem)) {
					throw new IOException(
							"Cannot instantiate a Hadoop file system to access the Hadoop configuration. " +
							"FS for hdfs:// is " + accessor.getClass().getName());
				}

				hadoopConf = ((HadoopFileSystem) accessor).getHadoopFileSystem().getConf();
			}

			// finalize the configuration

			final org.apache.hadoop.conf.Configuration finalConf;
			if (extraUserConf == null) {
				finalConf = hadoopConf;
			}
			else {
				finalConf = new org.apache.hadoop.conf.Configuration(hadoopConf);

				for (String key : extraUserConf.keySet()) {
					finalConf.set(key, extraUserConf.getString(key, null));
				}
			}

			// we explicitly re-instantiate the file system here in order to make sure
			// that the configuration is applied.

			URI fsUri = path.toUri();
			final String scheme = fsUri.getScheme();
			final String authority = fsUri.getAuthority();

			if (scheme == null && authority == null) {
				fsUri = FileSystem.getDefaultUri(finalConf);
			}
			else if (scheme != null && authority == null) {
				URI defaultUri = FileSystem.getDefaultUri(finalConf);
				if (scheme.equals(defaultUri.getScheme()) && defaultUri.getAuthority() != null) {
					fsUri = defaultUri;
				}
			}

			final Class<? extends FileSystem> fsClass = FileSystem.getFileSystemClass(fsUri.getScheme(), finalConf);
			final FileSystem fs;
			try {
				fs = fsClass.newInstance();
			}
			catch (Exception e) {
				throw new IOException("Cannot instantiate the Hadoop file system", e);
			}

			fs.initialize(fsUri, finalConf);

			// We don't perform checksums on Hadoop's local filesystem and use the raw filesystem.
			// Otherwise buffers are not flushed entirely during checkpointing which results in data loss.
			if (fs instanceof LocalFileSystem) {
				return ((LocalFileSystem) fs).getRaw();
			}
			return fs;
		}
	}

	/**
	 * Gets the truncate() call using reflection.
	 *
	 * <p><b>NOTE:</b> This code comes from Flume.
	 */
	public static Method reflectTruncate(final FileSystem fs) {
		Method m = null;
		if (fs != null) {
			Class<?> fsClass = fs.getClass();
			try {
				m = fsClass.getMethod("truncate", Path.class, long.class);
			} catch (NoSuchMethodException ex) {
				LOG.debug("Truncate not found. Will write a file to specify how many bytes in a bucket are valid.");
				return null;
			}

			// verify that truncate actually works
			FSDataOutputStream outputStream;
			Path testPath = new Path(UUID.randomUUID().toString());
			try {
				outputStream = fs.create(testPath);
				outputStream.writeUTF("hello");
				outputStream.close();
			} catch (IOException e) {
				LOG.error("Could not create file for checking if truncate works.", e);
				throw new RuntimeException("Could not create file for checking if truncate works. " +
						"You can disable support for truncate() completely via " +
						"BucketingSink.setUseTruncate(false).", e);
			}

			try {
				m.invoke(fs, testPath, 2);
			} catch (IllegalAccessException | InvocationTargetException e) {
				LOG.debug("Truncate is not supported.", e);
				m = null;
			}

			try {
				fs.delete(testPath, false);
			} catch (IOException e) {
				LOG.error("Could not delete truncate test file.", e);
				throw new RuntimeException("Could not delete truncate test file. " +
						"You can disable support for truncate() completely via " +
						"BucketingSink.setUseTruncate(false).", e);
			}
		}
		return m;
	}
}
