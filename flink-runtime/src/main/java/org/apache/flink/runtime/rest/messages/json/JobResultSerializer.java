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

package org.apache.flink.runtime.rest.messages.json;

import org.apache.flink.runtime.jobmaster.JobResult;
import org.apache.flink.util.OptionalFailure;
import org.apache.flink.util.SerializedThrowable;
import org.apache.flink.util.SerializedValue;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonGenerator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * JSON serializer for {@link JobResult}.
 *
 * @see JobResultDeserializer
 */
public class JobResultSerializer extends StdSerializer<JobResult> {

	private static final long serialVersionUID = 1L;

	static final String FIELD_NAME_JOB_ID = "id";

	static final String FIELD_NAME_NET_RUNTIME = "net-runtime";

	static final String FIELD_NAME_ACCUMULATOR_RESULTS = "accumulator-results";

	static final String FIELD_NAME_FAILURE_CAUSE = "failure-cause";

	private final JobIDSerializer jobIdSerializer = new JobIDSerializer();

	private final SerializedValueSerializer<OptionalFailure<Object>> serializedValueSerializer;

	private final SerializedThrowableSerializer serializedThrowableSerializer = new SerializedThrowableSerializer();

	public JobResultSerializer() {
		super(JobResult.class);
		serializedValueSerializer = new SerializedValueSerializer<>();
	}

	@Override
	public void serialize(
			final JobResult result,
			final JsonGenerator gen,
			final SerializerProvider provider) throws IOException {

		gen.writeStartObject();

		gen.writeFieldName(FIELD_NAME_JOB_ID);
		gen.writeString(jobIdSerializer.convert(result.getJobId()));

		gen.writeFieldName(FIELD_NAME_ACCUMULATOR_RESULTS);
		gen.writeStartObject();
		final Map<String, SerializedValue<OptionalFailure<Object>>> accumulatorResults = result.getAccumulatorResults();
		for (final Map.Entry<String, SerializedValue<OptionalFailure<Object>>> nameValue : accumulatorResults.entrySet()) {
			final String name = nameValue.getKey();
			final SerializedValue<OptionalFailure<Object>> value = nameValue.getValue();

			gen.writeFieldName(name);
			gen.writeBinary(serializedValueSerializer.convert(value));
		}
		gen.writeEndObject();

		gen.writeNumberField(FIELD_NAME_NET_RUNTIME, result.getNetRuntime());

		if (result.getSerializedThrowable().isPresent()) {
			gen.writeFieldName(FIELD_NAME_FAILURE_CAUSE);

			final SerializedThrowable serializedThrowable = result.getSerializedThrowable().get();
			serializedThrowableSerializer.serialize(serializedThrowable, gen, provider);
		}

		gen.writeEndObject();
	}
}
