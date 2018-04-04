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

import org.apache.flink.util.AbstractID;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.util.StdConverter;

import javax.xml.bind.DatatypeConverter;

/**
 * Identifies a savepoint trigger request.
 */
@JsonSerialize(converter = TriggerId.TriggerIdSerializer.class)
@JsonDeserialize(converter = TriggerId.TriggerIdDeserializer.class)
public final class TriggerId extends AbstractID {

	private static final long serialVersionUID = 1L;

	public TriggerId() {
	}

	private TriggerId(final byte[] bytes) {
		super(bytes);
	}

	public static TriggerId fromHexString(String hexString) {
		return new TriggerId(DatatypeConverter.parseHexBinary(hexString));
	}

	/**
	 * JSON serializer for {@link TriggerId}.
	 */
	public static class TriggerIdSerializer extends StdConverter<TriggerId, String> {

		@Override
		public String convert(TriggerId triggerId) {
			return triggerId.toString();
		}
	}

	/**
	 * JSON deserializer for {@link TriggerId}.
	 */
	public static class TriggerIdDeserializer extends StdConverter<String, TriggerId> {

		@Override
		public TriggerId convert(String s) {
			return TriggerId.fromHexString(s);
		}
	}
}
