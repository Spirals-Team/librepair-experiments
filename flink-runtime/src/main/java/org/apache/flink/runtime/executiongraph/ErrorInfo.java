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

import org.apache.flink.util.Preconditions;
import org.apache.flink.util.SerializedThrowable;

import javax.annotation.Nullable;

import java.io.Serializable;

/**
 * Simple container to hold an exception and the corresponding timestamp.
 */
public class ErrorInfo implements Serializable {

	private static final long serialVersionUID = -6138942031953594202L;

	private final transient Throwable exception;
	private final SerializedThrowable serializedThrowable;
	private final long timestamp;

	public ErrorInfo(Throwable exception, long timestamp) {
		Preconditions.checkNotNull(exception);
		Preconditions.checkArgument(timestamp > 0);

		this.exception = exception;
		this.serializedThrowable = new SerializedThrowable(exception);
		this.timestamp = timestamp;
	}

	/**
	 * Returns the contained exception.
	 *
	 * @return contained exception, or {@code "(null)"} if either no exception was set or this object has been deserialized
	 */
	@Nullable
	Throwable getException() {
		return exception;
	}

	/**
	 * Returns the serialized exception.
	 *
	 * @return serialized exception with which the error info has been initialized
	 */
	public SerializedThrowable getSerializedThrowable() {
		return serializedThrowable;
	}

	/**
	 * Returns the timestamp for the contained exception.
	 *
	 * @return timestamp of contained exception, or 0 if no exception was set
	 */
	public long getTimestamp() {
		return timestamp;
	}
}
