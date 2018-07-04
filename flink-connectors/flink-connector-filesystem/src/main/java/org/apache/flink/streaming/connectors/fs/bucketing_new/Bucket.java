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

package org.apache.flink.streaming.connectors.fs.bucketing_new;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.RecoverableFsDataOutputStream;
import org.apache.flink.core.fs.ResumableWriter;
import org.apache.flink.streaming.connectors.fs.bucketing_new.writers.Writer;
import org.apache.flink.util.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Javadoc.
 */
public class Bucket<IN> {

	private static final String PART_PREFIX = "part";

	private final Path bucketPath;

	private int subtaskIndex;

	private long partCounter;

	private long creationTime;

	private long lastWrittenTime;

	private final long maxPathSize;

	private final long rolloverTime;

	private final long inactivityTime;

	private final Writer<IN> outputFormatWriter;

	private final ResumableWriter fsWriter;

	private RecoverableFsDataOutputStream currentOpenPartStream;

	private List<ResumableWriter.CommitRecoverable> pending = new ArrayList<>();

	private Map<Long, List<ResumableWriter.CommitRecoverable>> pendingPerCheckpoint = new HashMap<>();

	public Bucket(
			FileSystem fileSystem,
			int subtaskIndex,
			Path bucketPath,
			long initialPartCounter,
			long maxPartSize,
			long rolloverTime,
			long inactivityTime,
			Writer<IN> writer,
			BucketState bucketstate) throws IOException {

		this(fileSystem, subtaskIndex, bucketPath, initialPartCounter, maxPartSize, rolloverTime, inactivityTime, writer);

		// the constructor must have already initialized the filesystem writer
		Preconditions.checkState(fsWriter != null);

		// for now we commit the previous open file and we start fresh
		final ResumableWriter.ResumeRecoverable resumable = bucketstate.getCurrentInProgress();
		if (resumable != null) {
			fsWriter.recover(resumable).closeForCommit().commitAfterRecovery();
		}

		for (List<ResumableWriter.CommitRecoverable> commitables: bucketstate.getPendingPerCheckpoint().values()) {
			for (ResumableWriter.CommitRecoverable commitable: commitables) {
				fsWriter.recoverForCommit(commitable).commit();
			}
		}

		// we start fresh after recovery
		this.currentOpenPartStream = null;
		this.pending = new ArrayList<>();
		this.pendingPerCheckpoint = new HashMap<>();
	}
	
	public Bucket(
			FileSystem fileSystem,
			int subtaskIndex,
			Path bucketPath,
			long initialPartCounter,
			long batchSize,
			long rolloverTime,
			long inactivityTime,
			Writer<IN> writer) throws IOException {

		Preconditions.checkNotNull(fileSystem);
		this.fsWriter = fileSystem.createRecoverableWriter();

		this.subtaskIndex = subtaskIndex;
		this.bucketPath = Preconditions.checkNotNull(bucketPath);
		this.partCounter = initialPartCounter;
		this.maxPathSize = batchSize;
		this.rolloverTime = rolloverTime;
		this.inactivityTime = inactivityTime;
		this.outputFormatWriter = Preconditions.checkNotNull(writer);
	}

	public Path getBucketPath() {
		return bucketPath;
	}

	public long getPartCounter() {
		return partCounter;
	}

	public boolean isActive() {
		return isOpen() || !pending.isEmpty() || !pendingPerCheckpoint.isEmpty();
	}

	public boolean isOpen() {
		return currentOpenPartStream != null;
	}

	public void write(IN element, long currentTime) throws IOException {
		if (shouldRoll(currentTime)) {
			startNewChunk(currentTime);
		}
		Preconditions.checkState(isOpen(), "Bucket is not open.");

		outputFormatWriter.write(element, currentOpenPartStream);
		lastWrittenTime = currentTime;
	}

	private void startNewChunk(final long currentTime) throws IOException {
		closeCurrentChunk();

		this.currentOpenPartStream = fsWriter.open(getNewPartPath());
		this.creationTime = currentTime;
		this.partCounter++;
	}

	private boolean shouldRoll(long currentTime) throws IOException {
		if (currentOpenPartStream == null) {
			return true;
		}

		long writePosition = currentOpenPartStream.getPos();
		if (writePosition > maxPathSize) {
			return true;
		}

		return (currentTime - creationTime > rolloverTime);
	}

	public void closeCurrentChunk() throws IOException {
		if (currentOpenPartStream != null) {
			pending.add(currentOpenPartStream.closeForCommit().getRecoverable());
			currentOpenPartStream = null;
		}
	}

	public void rollByTime(long currentTime) throws IOException {
		if (currentTime - creationTime > rolloverTime ||
				currentTime - lastWrittenTime > inactivityTime) {
			closeCurrentChunk();
		}
	}

	public void commitUpToCheckpoint(long checkpointId) throws IOException {
		Preconditions.checkNotNull(fsWriter);

		Iterator<Map.Entry<Long, List<ResumableWriter.CommitRecoverable>>> it =
				pendingPerCheckpoint.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<Long, List<ResumableWriter.CommitRecoverable>> entry = it.next();
			if (entry.getKey() <= checkpointId) {
				for (ResumableWriter.CommitRecoverable commitable : entry.getValue()) {
					fsWriter.recoverForCommit(commitable).commit();
				}
				it.remove();
			}
		}
	}

	public BucketState snapshot(long checkpointId, long checkpointTimestamp) throws IOException {

		// we also check here so that we do not have to always
		// wait for the "next" element to arrive.

		if (shouldRoll(checkpointTimestamp)) {
			closeCurrentChunk();
		}

		ResumableWriter.ResumeRecoverable resumable = null;
		if (currentOpenPartStream != null) {
			resumable = currentOpenPartStream.persist();
		}

		if (!pending.isEmpty()) {
			pendingPerCheckpoint.put(checkpointId, pending);
			pending = new ArrayList<>();
		}

		return new BucketState(bucketPath, resumable, pendingPerCheckpoint);
	}

	public static BucketStateSerializer getBucketStateSerializer(FileSystem fileSystem) throws IOException {
		Preconditions.checkNotNull(fileSystem);

		ResumableWriter fsWriter = fileSystem.createRecoverableWriter();
		return new BucketStateSerializer(
				fsWriter.getResumeRecoverableSerializer(),
				fsWriter.getCommitRecoverableSerializer()
		);
	}

	private Path getNewPartPath() {
		return new Path(bucketPath, PART_PREFIX + "-" + subtaskIndex + "-" + partCounter);
	}

	/**
	 * Javadoc.
	 */
	public static class BucketState {

		private final Path bucketPath;

		private final ResumableWriter.ResumeRecoverable inProgress;

		private final Map<Long, List<ResumableWriter.CommitRecoverable>> pendingPerCheckpoint;

		public BucketState(
				final Path bucketPath,
				final ResumableWriter.ResumeRecoverable inProgress,
				final Map<Long, List<ResumableWriter.CommitRecoverable>> pendingPerCheckpoint
		) {
			this.bucketPath = Preconditions.checkNotNull(bucketPath);
			this.inProgress = inProgress;
			this.pendingPerCheckpoint = Preconditions.checkNotNull(pendingPerCheckpoint);
		}

		public Path getBucketPath() {
			return bucketPath;
		}

		public ResumableWriter.ResumeRecoverable getCurrentInProgress() {
			return inProgress;
		}

		public Map<Long, List<ResumableWriter.CommitRecoverable>> getPendingPerCheckpoint() {
			return pendingPerCheckpoint;
		}
	}
}
