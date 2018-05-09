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

package org.apache.flink.runtime.rest.messages.job;

import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.rest.messages.RequestBody;
import org.apache.flink.util.Preconditions;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Request for submitting a job.
 *
 * <p>We currently require the job-jars to be uploaded through the blob-server.
 */
public final class JobSubmitRequestBody implements RequestBody {

	private static final String FIELD_NAME_SERIALIZED_JOB_GRAPH = "serializedJobGraph";
	private static final String FIELD_NAME_JAR_FILES = "jars";

	/**
	 * The serialized job graph.
	 */
	@JsonProperty(FIELD_NAME_SERIALIZED_JOB_GRAPH)
	public final byte[] serializedJobGraph;

	@JsonProperty(FIELD_NAME_JAR_FILES)
	public final List<JarEntry> jarFiles;

	public JobSubmitRequestBody(JobGraph jobGraph, List<JarEntry> jarFiles) throws IOException {
		this(serializeJobGraph(jobGraph), jarFiles);
	}

	@JsonCreator
	public JobSubmitRequestBody(
			@JsonProperty(FIELD_NAME_SERIALIZED_JOB_GRAPH) byte[] serializedJobGraph,
			@JsonProperty(FIELD_NAME_JAR_FILES) List<JarEntry> jarFiles) {
		this.serializedJobGraph = Preconditions.checkNotNull(serializedJobGraph);
		this.jarFiles = jarFiles;
	}

	@Override
	public int hashCode() {
		return 71 * Arrays.hashCode(this.serializedJobGraph);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof JobSubmitRequestBody) {
			JobSubmitRequestBody other = (JobSubmitRequestBody) object;
			return Arrays.equals(this.serializedJobGraph, other.serializedJobGraph);
		}
		return false;
	}

	private static byte[] serializeJobGraph(JobGraph jobGraph) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(64 * 1024)) {
			ObjectOutputStream out = new ObjectOutputStream(baos);

			out.writeObject(jobGraph);

			return baos.toByteArray();
		}
	}

	/**
	 * Represents all information about a single jar file.
	 */
	public static final class JarEntry {
		private static final String FIELD_NAME_JAR_ID = "jarid";
		private static final String FIELD_NAME_DELETE_AFTER_SUBMISSION = "deleteAfterSubmission";

		@JsonProperty(FIELD_NAME_JAR_ID)
		public final String jarId;

		@JsonProperty(FIELD_NAME_DELETE_AFTER_SUBMISSION)
		public final boolean deleteAfterSubmission;

		@JsonCreator
		public JarEntry(
				@JsonProperty(FIELD_NAME_JAR_ID) String jarId,
				@JsonProperty(FIELD_NAME_DELETE_AFTER_SUBMISSION) boolean deleteAfterSubmission) {
			this.jarId = jarId;
			this.deleteAfterSubmission = deleteAfterSubmission;
		}
	}
}
