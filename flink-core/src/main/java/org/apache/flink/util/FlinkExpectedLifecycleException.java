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

package org.apache.flink.util;

import org.apache.flink.annotation.Internal;

import java.io.PrintWriter;

/**
 * The exception that used in the normal life-cycle. we override the printStackTrace to
 * only contain the message, so that we can log this exception on the INFO/DEBUG level
 * without cause confusing.
 */
@Internal
public class FlinkExpectedLifecycleException extends FlinkRuntimeException {

	private static final long serialVersionUID = -4640364240001315705L;

	/**
	 * Creates a new Exception with the given message.
	 *
	 * @param message The exception message
	 */
	public FlinkExpectedLifecycleException(String message) {
		super(message);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		s.println(String.format("%s => %s", FlinkExpectedLifecycleException.class.getClass(), this.getLocalizedMessage()));
	}
}
