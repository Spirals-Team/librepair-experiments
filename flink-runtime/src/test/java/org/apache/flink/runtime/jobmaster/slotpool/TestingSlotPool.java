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

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.runtime.clusterframework.types.AllocationID;
import org.apache.flink.runtime.instance.SlotSharingGroupId;
import org.apache.flink.runtime.jobmaster.SlotRequestId;
import org.apache.flink.runtime.messages.Acknowledge;
import org.apache.flink.runtime.rpc.RpcService;
import org.apache.flink.runtime.util.clock.Clock;
import org.apache.flink.util.Preconditions;

import javax.annotation.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Testing SlotPool which exposes internal state via some testing methods.
 */
final class TestingSlotPool extends SlotPool {

    private volatile Consumer<SlotRequestId> releaseSlotConsumer;

    private volatile Consumer<SlotRequestId> timeoutPendingSlotRequestConsumer;

    private final Time timeout;

    public TestingSlotPool(
            RpcService rpcService,
            JobID jobId,
            Clock clock,
            Time rpcTimeout,
            Time idleSlotTimeout) {
        super(
            rpcService,
            jobId,
            clock,
            rpcTimeout,
            idleSlotTimeout);

        releaseSlotConsumer = null;
        timeoutPendingSlotRequestConsumer = null;
        this.timeout = rpcTimeout;
    }

    public void setReleaseSlotConsumer(Consumer<SlotRequestId> releaseSlotConsumer) {
        this.releaseSlotConsumer = Preconditions.checkNotNull(releaseSlotConsumer);
    }

    public void setTimeoutPendingSlotRequestConsumer(Consumer<SlotRequestId> timeoutPendingSlotRequestConsumer) {
        this.timeoutPendingSlotRequestConsumer = Preconditions.checkNotNull(timeoutPendingSlotRequestConsumer);
    }

    @Override
    public CompletableFuture<Acknowledge> releaseSlot(
        SlotRequestId slotRequestId,
        @Nullable SlotSharingGroupId slotSharingGroupId,
        @Nullable Throwable cause) {
        final Consumer<SlotRequestId> currentReleaseSlotConsumer = releaseSlotConsumer;

        final CompletableFuture<Acknowledge> acknowledgeCompletableFuture = super.releaseSlot(slotRequestId, slotSharingGroupId, cause);

        if (currentReleaseSlotConsumer != null) {
            currentReleaseSlotConsumer.accept(slotRequestId);
        }

        return acknowledgeCompletableFuture;
    }

    @Override
    protected void timeoutPendingSlotRequest(SlotRequestId slotRequestId) {
        final Consumer<SlotRequestId> currentTimeoutPendingSlotRequestConsumer = timeoutPendingSlotRequestConsumer;

        if (currentTimeoutPendingSlotRequestConsumer != null) {
            currentTimeoutPendingSlotRequestConsumer.accept(slotRequestId);
        }

        super.timeoutPendingSlotRequest(slotRequestId);
    }

    CompletableFuture<Boolean> containsAllocatedSlot(AllocationID allocationId) {
        return callAsync(
            () -> getAllocatedSlots().contains(allocationId),
            timeout);
    }

    CompletableFuture<Boolean> containsAvailableSlot(AllocationID allocationId) {
        return callAsync(
            () -> getAvailableSlots().contains(allocationId),
            timeout);
    }

    CompletableFuture<Integer> getNumberOfPendingRequests() {
        return callAsync(
            () -> getPendingRequests().size(),
            timeout);
    }

    CompletableFuture<Integer> getNumberOfWaitingForResourceRequests() {
        return callAsync(
            () -> getWaitingForResourceManager().size(),
            timeout);
    }
}
