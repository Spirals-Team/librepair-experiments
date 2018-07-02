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
import org.apache.flink.core.fs.ResumableFsDataOutputStream;
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

	private final FileSystem fileSystem;

	private final Path bucketPath;

	private int subtaskIndex;

	private long partCounter;

	private long creationTime;

	private long lastWrittenTime;

	private final long maxPathSize;

	private final long rolloverTime;

	private final long inactivityTime;

	private final Writer<IN> writer;

	private ResumableFsDataOutputStream<?> currentPartStream;

	private List<ResumableWriter.Resumable> pending = new ArrayList<>();

	private Map<Long, List<ResumableWriter.Resumable>> pendingPerCheckpoint = new HashMap<>();

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

		final ResumableWriter.Resumable resumable = bucketstate.getCurrent();
		final ResumableWriter fsWriter = fileSystem.createResumableWriter();

		this.currentPartStream = fsWriter.resume(resumable);
		this.pendingPerCheckpoint = bucketstate.getPendingPerCheckpoint();
	}
	
	public Bucket(
			FileSystem fileSystem,
			int subtaskIndex,
			Path bucketPath,
			long initialPartCounter,
			long batchSize,
			long rolloverTime,
			long inactivityTime,
			Writer<IN> writer) {

		this.fileSystem = Preconditions.checkNotNull(fileSystem);
		this.subtaskIndex = subtaskIndex;
		this.bucketPath = Preconditions.checkNotNull(bucketPath);
		this.partCounter = initialPartCounter;
		this.maxPathSize = batchSize;
		this.rolloverTime = rolloverTime;
		this.inactivityTime = inactivityTime;
		this.writer = Preconditions.checkNotNull(writer);
	}

	public long getPartCounter() {
		return partCounter;
	}

	public boolean isActive() {
		return isOpen() || !pending.isEmpty() || !pendingPerCheckpoint.isEmpty();
	}

	public boolean isOpen() {
		return currentPartStream != null;
	}

	public void write(IN element, long currentTime) throws IOException {
		if (shouldRoll(currentTime)) {
			startNewChunk(currentTime);
		}
		Preconditions.checkState(isOpen(), "Bucket is not open.");

		writer.write(element, currentPartStream);
		lastWrittenTime = currentTime;
	}

	private void startNewChunk(final long currentTime) throws IOException {
		if (currentPartStream != null) {
			pending.add(currentPartStream.persist());
			closeCurrentChunk();
		}
		ResumableWriter fsWriter = fileSystem.createResumableWriter();
		this.currentPartStream = fsWriter.open(getNewPartPath());
		this.creationTime = currentTime;
		this.partCounter++;
	}

	private boolean shouldRoll(long currentTime) throws IOException {
		if (currentPartStream == null) {
			return true;
		}

		long writePosition = currentPartStream.getPos();
		if (writePosition > maxPathSize) {
			return true;
		}

		return (currentTime - creationTime > rolloverTime);
	}

	public void closeCurrentChunk() throws IOException {
		if (currentPartStream != null) {
			pending.add(currentPartStream.persist());
			currentPartStream.close();
			currentPartStream = null;
		}
	}

	public void rollByTime(long currentTime) throws IOException {
		if (currentTime - creationTime > rolloverTime ||
				currentTime - lastWrittenTime > inactivityTime) {
			closeCurrentChunk();
		}
	}

	public void commitUpToCheckpoint(long checkpointId) throws IOException {
		Preconditions.checkNotNull(fileSystem);

		Iterator<Map.Entry<Long, List<ResumableWriter.Resumable>>> it =
				pendingPerCheckpoint.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<Long, List<ResumableWriter.Resumable>> entry = it.next();
			if (entry.getKey() <= checkpointId) {
				for (ResumableWriter.Resumable resumable : entry.getValue()) {
					final ResumableWriter fsWriter = fileSystem.createResumableWriter();
					fsWriter.resume(resumable).closeAndPublish();// TODO: 7/2/18 I may need to keep the actual stream, not the resumable
				}
				it.remove();
			}
		}
	}

	public BucketState snapshot(long checkpointId) throws IOException {
		ResumableWriter.Resumable resumable = null;
		if (currentPartStream != null) {
			resumable = currentPartStream.persist();
		}
		pendingPerCheckpoint.put(checkpointId, pending);
		pending = new ArrayList<>();
		return new BucketState(bucketPath, resumable, pendingPerCheckpoint);
	}

	private Path getNewPartPath() {
		return new Path(bucketPath, PART_PREFIX + "-" + subtaskIndex + "-" + partCounter);
	}

	/**
	 * Javadoc.
	 */
	static class BucketState {

		private final Path bucketPath;

		private final ResumableWriter.Resumable current;

		private final Map<Long, List<ResumableWriter.Resumable>> pendingPerCheckpoint;

		BucketState(
				final Path bucketPath,
				final ResumableWriter.Resumable current,
				final Map<Long, List<ResumableWriter.Resumable>> pendingPerCheckpoint
		) {
			this.bucketPath = Preconditions.checkNotNull(bucketPath);
			this.current = current;
			this.pendingPerCheckpoint = Preconditions.checkNotNull(pendingPerCheckpoint);
		}

		Path getBucketPath() {
			return bucketPath;
		}

		ResumableWriter.Resumable getCurrent() {
			return current;
		}

		Map<Long, List<ResumableWriter.Resumable>> getPendingPerCheckpoint() {
			return pendingPerCheckpoint;
		}
	}
}
