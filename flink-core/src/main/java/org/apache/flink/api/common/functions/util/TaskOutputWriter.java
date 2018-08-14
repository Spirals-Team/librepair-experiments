/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.api.common.functions.util;

import java.io.PrintStream;

/**
 * Task output writer for DataStream and DataSet print API.
 */
public class TaskOutputWriter<IN> {

	private static final long serialVersionUID = 1L;

	private static final boolean STD_OUT = false;
	private static final boolean STD_ERR = true;

	private final boolean target;
	private transient PrintStream stream;
	private final String sinkIdentifier;
	private transient String completedPrefix;

	public TaskOutputWriter() {
		this("", STD_OUT);
	}

	public TaskOutputWriter(final boolean stdErr) {
		this("", stdErr);
	}

	public TaskOutputWriter(final String sinkIdentifier, final boolean stdErr) {
		this.target = stdErr;
		this.sinkIdentifier = (sinkIdentifier == null ? "" : sinkIdentifier);
	}

	public void open(int taskNumber, int numTasks) {
		// get the target stream
		stream = target == STD_OUT ? System.out : System.err;

		completedPrefix = sinkIdentifier;

		if (numTasks > 1) {
			if (!completedPrefix.isEmpty()) {
				completedPrefix += ":";
			}
			completedPrefix += (taskNumber + 1);
		}

		if (!completedPrefix.isEmpty()) {
			completedPrefix += "> ";
		}
	}

	public void write(IN record) {
		stream.println(completedPrefix + record.toString());
	}

	public void close() {

	}

	@Override
	public String toString() {
		return "Print to " + (target == STD_OUT ? "System.out" : "System.err");
	}
}
