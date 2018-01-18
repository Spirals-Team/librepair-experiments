/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.executiongraph;

import org.apache.flink.api.common.ArchivedExecutionConfig;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.accumulators.Accumulator;
import org.apache.flink.api.common.accumulators.AccumulatorHelper;
import org.apache.flink.runtime.accumulators.StringifiedAccumulatorResult;
import org.apache.flink.runtime.checkpoint.CheckpointStatsSnapshot;
import org.apache.flink.runtime.jobgraph.JobStatus;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.jobgraph.tasks.CheckpointCoordinatorConfiguration;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.SerializedValue;

import javax.annotation.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * {@link SerializableExecutionGraph} implementation for testing purposes.
 */
public class DummyExecutionGraph implements SerializableExecutionGraph {

	private static final long serialVersionUID = -5709159493428811245L;

	private final JobID jobId;

	private final String jobName;

	private final JobStatus state;

	private final String jsonPlan;

	private final boolean isStoppable;

	private final boolean isArchived;

	private final Map<JobVertexID, ? extends AccessExecutionJobVertex> accessExecutionJobVertices;

	private final Map<String, SerializedValue<Object>> accumulatorsSerialized;

	private final StringifiedAccumulatorResult[] stringifiedAccumulatorResults;

	private final long[] timestamps;

	@Nullable
	private final ErrorInfo errorInfo;

	@Nullable
	private final CheckpointCoordinatorConfiguration checkpointCoordinatorConfiguration;

	@Nullable
	private final CheckpointStatsSnapshot checkpointStatsSnapshot;

	@Nullable
	private final ArchivedExecutionConfig archivedExecutionConfig;


	public DummyExecutionGraph(
			JobID jobId,
			String jobName,
			JobStatus state,
			String jsonPlan,
			boolean isStoppable,
			boolean isArchived,
			Map<JobVertexID, ? extends AccessExecutionJobVertex> accessExecutionJobVertices,
			Map<String, Accumulator<?, ?>> accumulators,
			long[] timestamps,
			@Nullable ErrorInfo errorInfo,
			@Nullable CheckpointCoordinatorConfiguration checkpointCoordinatorConfiguration,
			@Nullable CheckpointStatsSnapshot checkpointStatsSnapshot,
			@Nullable ArchivedExecutionConfig archivedExecutionConfig) throws IOException {
		this.jobId = Preconditions.checkNotNull(jobId);
		this.jobName = Preconditions.checkNotNull(jobName);
		this.state = Preconditions.checkNotNull(state);
		this.jsonPlan = Preconditions.checkNotNull(jsonPlan);
		this.isStoppable = Preconditions.checkNotNull(isStoppable);
		this.isArchived = Preconditions.checkNotNull(isArchived);
		this.accessExecutionJobVertices = Preconditions.checkNotNull(accessExecutionJobVertices);
		Preconditions.checkNotNull(accumulators);
		this.accumulatorsSerialized = AccumulatorHelper.serializeAccumulators(accumulators);
		this.stringifiedAccumulatorResults = StringifiedAccumulatorResult.stringifyAccumulatorResults(accumulators);
		Preconditions.checkArgument(timestamps.length == JobStatus.values().length, "Not enough timestamps provided.");
		this.timestamps = timestamps;
		this.errorInfo = errorInfo;
		this.checkpointCoordinatorConfiguration = checkpointCoordinatorConfiguration;
		this.checkpointStatsSnapshot = checkpointStatsSnapshot;
		this.archivedExecutionConfig = archivedExecutionConfig;
	}

	@Override
	public String getJsonPlan() {
		return jsonPlan;
	}

	@Override
	public JobID getJobID() {
		return jobId;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public JobStatus getState() {
		return state;
	}

	@Nullable
	@Override
	public ErrorInfo getFailureCause() {
		return errorInfo;
	}

	@Override
	public AccessExecutionJobVertex getJobVertex(JobVertexID id) {
		return accessExecutionJobVertices.get(id);
	}

	@Override
	public Map<JobVertexID, ? extends AccessExecutionJobVertex> getAllVertices() {
		return accessExecutionJobVertices;
	}

	@Override
	public Iterable<? extends AccessExecutionJobVertex> getVerticesTopologically() {
		return accessExecutionJobVertices.values();
	}

	@Override
	public Iterable<? extends AccessExecutionVertex> getAllExecutionVertices() {
		return accessExecutionJobVertices
			.values()
			.stream()
			.flatMap(vertex -> Arrays.stream(vertex.getTaskVertices()))
			.collect(Collectors.toList());
	}

	@Override
	public long getStatusTimestamp(JobStatus status) {
		return timestamps[status.ordinal()];
	}

	@Nullable
	@Override
	public CheckpointCoordinatorConfiguration getCheckpointCoordinatorConfiguration() {
		return checkpointCoordinatorConfiguration;
	}

	@Nullable
	@Override
	public CheckpointStatsSnapshot getCheckpointStatsSnapshot() {
		return checkpointStatsSnapshot;
	}

	@Nullable
	@Override
	public ArchivedExecutionConfig getArchivedExecutionConfig() {
		return archivedExecutionConfig;
	}

	@Override
	public boolean isStoppable() {
		return isStoppable;
	}

	@Override
	public StringifiedAccumulatorResult[] getAccumulatorResultsStringified() {
		return stringifiedAccumulatorResults;
	}

	@Override
	public Map<String, SerializedValue<Object>> getAccumulatorsSerialized() {
		return accumulatorsSerialized;
	}

	@Override
	public boolean isArchived() {
		return isArchived;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private JobID jobId = new JobID();
		private String jobName = UUID.randomUUID().toString();
		private JobStatus state = JobStatus.CREATED;
		private String jsonPlan = "";
		private boolean isStoppable = false;
		private boolean isArchived = false;
		private Map<JobVertexID, ? extends AccessExecutionJobVertex> accessExecutionJobVertices = Collections.emptyMap();
		private Map<String, Accumulator<?, ?>> accumulators = Collections.emptyMap();
		private long[] timestamps = new long[JobStatus.values().length];
		private ErrorInfo errorInfo;
		private CheckpointCoordinatorConfiguration checkpointCoordinatorConfiguration;
		private CheckpointStatsSnapshot checkpointStatsSnapshot;
		private ArchivedExecutionConfig archivedExecutionConfig;

		public Builder setJobId(JobID jobId) {
			this.jobId = jobId;
			return this;
		}

		public Builder setJobName(String jobName) {
			this.jobName = jobName;
			return this;
		}

		public Builder setState(JobStatus state) {
			this.state = state;
			return this;
		}

		public Builder setJsonPlan(String jsonPlan) {
			this.jsonPlan = jsonPlan;
			return this;
		}

		public Builder setIsStoppable(boolean isStoppable) {
			this.isStoppable = isStoppable;
			return this;
		}

		public Builder setIsArchived(boolean isArchived) {
			this.isArchived = isArchived;
			return this;
		}

		public Builder setAccessExecutionJobVertices(Map<JobVertexID, ? extends AccessExecutionJobVertex> accessExecutionJobVertices) {
			this.accessExecutionJobVertices = accessExecutionJobVertices;
			return this;
		}

		public Builder setAccumulators(Map<String, Accumulator<?, ?>> accumulators) {
			this.accumulators = accumulators;
			return this;
		}

		public Builder setTimestamps(long[] timestamps) {
			this.timestamps = timestamps;
			return this;
		}

		public Builder setErrorInfo(ErrorInfo errorInfo) {
			this.errorInfo = errorInfo;
			return this;
		}

		public Builder setCheckpointCoordinatorConfiguration(CheckpointCoordinatorConfiguration checkpointCoordinatorConfiguration) {
			this.checkpointCoordinatorConfiguration = checkpointCoordinatorConfiguration;
			return this;
		}

		public Builder setCheckpointStatsSnapshot(CheckpointStatsSnapshot checkpointStatsSnapshot) {
			this.checkpointStatsSnapshot = checkpointStatsSnapshot;
			return this;
		}

		public Builder setArchivedExecutionConfig(ArchivedExecutionConfig archivedExecutionConfig) {
			this.archivedExecutionConfig = archivedExecutionConfig;
			return this;
		}

		public DummyExecutionGraph build() throws IOException {
			return new DummyExecutionGraph(jobId, jobName, state, jsonPlan, isStoppable, isArchived, accessExecutionJobVertices, accumulators, timestamps, errorInfo, checkpointCoordinatorConfiguration, checkpointStatsSnapshot, archivedExecutionConfig);
		}
	}
}
