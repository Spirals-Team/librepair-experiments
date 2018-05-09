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

package org.apache.flink.runtime.jobmaster.slotpool;

import org.apache.flink.runtime.clusterframework.types.AllocationID;
import org.apache.flink.runtime.clusterframework.types.ResourceProfile;
import org.apache.flink.runtime.executiongraph.utils.SimpleAckingTaskManagerGateway;
import org.apache.flink.runtime.jobmanager.scheduler.Locality;
import org.apache.flink.runtime.jobmaster.LogicalSlot;
import org.apache.flink.runtime.jobmaster.SlotContext;
import org.apache.flink.runtime.jobmaster.SlotOwner;
import org.apache.flink.runtime.jobmaster.SlotRequestId;
import org.apache.flink.runtime.taskmanager.LocalTaskManagerLocation;
import org.apache.flink.util.FlinkException;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.TestLogger;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SingleLogicalSlotTest extends TestLogger {

	/**
	 * Tests that the slot release is only signaled after the owner has
	 * taken it back.
	 */
	@Test
	public void testSlotRelease() throws ExecutionException, InterruptedException {
		final CompletableFuture<Boolean> returnFuture = new CompletableFuture<>();
		final WaitingSlotOwner waitingSlotOwner = new WaitingSlotOwner(returnFuture);
		final SlotContext slotContext = new AllocatedSlot(
			new AllocationID(),
			new LocalTaskManagerLocation(),
			0,
			ResourceProfile.UNKNOWN,
			new SimpleAckingTaskManagerGateway());

		final SingleLogicalSlot singleLogicalSlot = new SingleLogicalSlot(
			new SlotRequestId(),
			slotContext,
			null,
			Locality.LOCAL,
			waitingSlotOwner);

		final CompletableFuture<?> releaseFuture = singleLogicalSlot.releaseSlot(new FlinkException("Test exception"));

		assertThat(releaseFuture.isDone(), is(false));

		returnFuture.complete(true);

		releaseFuture.get();
	}

	private static final class WaitingSlotOwner implements SlotOwner {

		private final CompletableFuture<Boolean> returnFuture;

		private WaitingSlotOwner(CompletableFuture<Boolean> returnFuture) {
			this.returnFuture = Preconditions.checkNotNull(returnFuture);
		}

		@Override
		public CompletableFuture<Boolean> returnAllocatedSlot(LogicalSlot logicalSlot) {
			return returnFuture;
		}
	}
}
