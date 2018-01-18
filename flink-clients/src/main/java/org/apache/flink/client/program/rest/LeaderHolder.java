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

package org.apache.flink.client.program.rest;

import javax.annotation.Nullable;

import static org.apache.flink.util.Preconditions.checkArgument;

class LeaderHolder<T> {

	private final String leaderType;

	private final long awaitLeaderTimeoutMs;

	private volatile T leaderAddress;

	LeaderHolder(final String leaderType, final long awaitLeaderTimeoutMs) {
		checkArgument(awaitLeaderTimeoutMs >= 0, "awaitLeaderTimeoutMs must be greater than or equal to 0");
		this.leaderType = leaderType;
		this.awaitLeaderTimeoutMs = awaitLeaderTimeoutMs;
	}

	public T getLeaderAddress() throws LeaderNotAvailableException {
		final long startTime = System.nanoTime();
		T result;
		synchronized (this) {
			long sleptTimeMs = 0;
			while (leaderAddress == null && sleptTimeMs < awaitLeaderTimeoutMs) {
				try {
					wait(awaitLeaderTimeoutMs - sleptTimeMs);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				sleptTimeMs = (System.nanoTime() - startTime) / 1000000;
			}
			result = leaderAddress;
		}

		if (result == null) {
			throw new LeaderNotAvailableException(
				String.format("Could not retrieve %s address for %d ms",
					leaderType,
					awaitLeaderTimeoutMs));
		}

		return result;
	}

	public void setLeaderAddress(@Nullable final T leaderAddress) {
		synchronized (this) {
			this.leaderAddress = leaderAddress;
			if (leaderAddress != null) {
				notifyAll();
			}
		}
	}

}
