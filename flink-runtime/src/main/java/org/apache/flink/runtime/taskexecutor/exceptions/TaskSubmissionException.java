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

package org.apache.flink.runtime.taskexecutor.exceptions;

import org.apache.flink.runtime.taskmanager.Task;

/**
 * Exception indicating a problem with the {@link Task} submission at the {@link TaskException}.
 */
public class TaskSubmissionException extends TaskManagerException {

	private static final long serialVersionUID = 4589813591317690486L;

	public TaskSubmissionException(String message) {
		super(message);
	}

	public TaskSubmissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskSubmissionException(Throwable cause) {
		super(cause);
	}
}
