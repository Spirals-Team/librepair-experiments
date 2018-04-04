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

package org.apache.flink.runtime.rest.messages;

import org.apache.flink.api.common.JobID;
import org.apache.flink.runtime.rest.handler.job.JobConfigHandler;
import org.apache.flink.util.Preconditions;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * Response type of the {@link JobConfigHandler}.
 */
public class JobConfigInfo implements ResponseBody {

	public static final String FIELD_NAME_JOB_ID = "jid";
	public static final String FIELD_NAME_JOB_NAME = "name";
	public static final String FIELD_NAME_EXECUTION_CONFIG = "execution-config";

	@JsonProperty(FIELD_NAME_JOB_ID)
	private final JobID jobId;

	@JsonProperty(FIELD_NAME_JOB_NAME)
	private final String jobName;

	@JsonProperty(FIELD_NAME_EXECUTION_CONFIG)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final ExecutionConfigInfo executionConfigInfo;

	public JobConfigInfo(
			JobID jobId,
			String jobName,
			@Nullable ExecutionConfigInfo executionConfigInfo) {
		this.jobId = Preconditions.checkNotNull(jobId);
		this.jobName = Preconditions.checkNotNull(jobName);
		this.executionConfigInfo = executionConfigInfo;
	}

	@JsonIgnore
	public JobID getJobId() {
		return jobId;
	}

	@JsonIgnore
	public String getJobName() {
		return jobName;
	}

	@JsonIgnore
	@Nullable
	public ExecutionConfigInfo getExecutionConfigInfo() {
		return executionConfigInfo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		JobConfigInfo that = (JobConfigInfo) o;
		return Objects.equals(jobId, that.jobId) &&
			Objects.equals(jobName, that.jobName) &&
			Objects.equals(executionConfigInfo, that.executionConfigInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobId, jobName, executionConfigInfo);
	}

	//---------------------------------------------------------------------------------
	// Static helper classes
	//---------------------------------------------------------------------------------

	/**
	 * Nested class to encapsulate the execution configuration.
	 */
	public static final class ExecutionConfigInfo {

		public static final String FIELD_NAME_EXECUTION_MODE = "execution-mode";
		public static final String FIELD_NAME_RESTART_STRATEGY = "restart-strategy";
		public static final String FIELD_NAME_PARALLELISM = "job-parallelism";
		public static final String FIELD_NAME_OBJECT_REUSE_MODE = "object-reuse-mode";
		public static final String FIELD_NAME_GLOBAL_JOB_PARAMETERS = "user-config";

		@JsonProperty(FIELD_NAME_EXECUTION_MODE)
		private final String executionMode;

		@JsonProperty(FIELD_NAME_RESTART_STRATEGY)
		private final String restartStrategy;

		@JsonProperty(FIELD_NAME_PARALLELISM)
		private final int parallelism;

		@JsonProperty(FIELD_NAME_OBJECT_REUSE_MODE)
		private final boolean isObjectResuse;

		@JsonProperty(FIELD_NAME_GLOBAL_JOB_PARAMETERS)
		private final Map<String, String> globalJobParameters;

		@JsonCreator
		public ExecutionConfigInfo(
				@JsonProperty(FIELD_NAME_EXECUTION_MODE) String executionMode,
				@JsonProperty(FIELD_NAME_RESTART_STRATEGY) String restartStrategy,
				@JsonProperty(FIELD_NAME_PARALLELISM) int parallelism,
				@JsonProperty(FIELD_NAME_OBJECT_REUSE_MODE) boolean isObjectResuse,
				@JsonProperty(FIELD_NAME_GLOBAL_JOB_PARAMETERS) Map<String, String> globalJobParameters) {
			this.executionMode = Preconditions.checkNotNull(executionMode);
			this.restartStrategy = Preconditions.checkNotNull(restartStrategy);
			this.parallelism = parallelism;
			this.isObjectResuse = isObjectResuse;
			this.globalJobParameters = Preconditions.checkNotNull(globalJobParameters);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			ExecutionConfigInfo that = (ExecutionConfigInfo) o;
			return parallelism == that.parallelism &&
				isObjectResuse == that.isObjectResuse &&
				Objects.equals(executionMode, that.executionMode) &&
				Objects.equals(restartStrategy, that.restartStrategy) &&
				Objects.equals(globalJobParameters, that.globalJobParameters);
		}

		@Override
		public int hashCode() {
			return Objects.hash(executionMode, restartStrategy, parallelism, isObjectResuse, globalJobParameters);
		}
	}
}
