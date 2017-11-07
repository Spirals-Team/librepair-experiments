/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.memory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Closer;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.concurrent.ThreadSafe;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static com.facebook.presto.memory.MemoryReservationHandler.NOT_BLOCKED;
import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

/**
 * This class is used to track memory usage at all levels (operator, driver, pipeline, etc.).
 *
 * At every level we have three aggregate and three local memory contexts. The local memory contexts
 * track the allocations in the current level while the aggregate memory contexts aggregate the memory
 * allocated by the children levels and the current level.
 *
 * The reason we have local memory contexts at every level is that not all the
 * allocations are done by the children levels (e.g., at the pipeline level exchange clients
 * can do system allocations directly, see the ExchangeOperator, another example is the buffers
 * doing system allocations at the task context level, etc.).
 *
 * As another example, at the pipeline level there will be system allocations initiated by the operator context
 * and there will be system allocations initiated by the exchange clients (local allocations). All these system
 * allocations will be visible in the systemAggregateMemoryContext.
 *
 * To perform local allocations clients should call localUserMemoryContext()/localSystemMemoryContext()
 * and get a reference to the local memory contexts. The other child-originated allocations will go through
 * updateUserMemory()/updateRevocableMemory()/updateSystemMemory() methods.
 */
@ThreadSafe
public class MemoryTrackingContext
{
    private final AggregatedMemoryContext userAggregateMemoryContext;
    private final AggregatedMemoryContext revocableAggregateMemoryContext;
    private final AggregatedMemoryContext systemAggregateMemoryContext;

    private final LocalMemoryContext userLocalMemoryContext;
    private final LocalMemoryContext revocableLocalMemoryContext;
    private final LocalMemoryContext systemLocalMemoryContext;

    private final AtomicLong peakUserMemory = new AtomicLong();

    public MemoryTrackingContext(
            AggregatedMemoryContext userAggregateMemoryContext,
            AggregatedMemoryContext revocableAggregateMemoryContext,
            AggregatedMemoryContext systemAggregateMemoryContext)
    {
        this.userAggregateMemoryContext = requireNonNull(userAggregateMemoryContext, "userAggregateMemoryContext is null");
        this.revocableAggregateMemoryContext = requireNonNull(revocableAggregateMemoryContext, "revocableAggregateMemoryContext is null");
        this.systemAggregateMemoryContext = requireNonNull(systemAggregateMemoryContext, "systemAggregateMemoryContext is null");
        this.userLocalMemoryContext = userAggregateMemoryContext.newLocalMemoryContext();
        this.revocableLocalMemoryContext = revocableAggregateMemoryContext.newLocalMemoryContext();
        this.systemLocalMemoryContext = systemAggregateMemoryContext.newLocalMemoryContext();
    }

    public ListenableFuture<?> updateUserMemory(long delta)
    {
        if (delta >= 0) {
            ListenableFuture<?> future = userLocalMemoryContext.addBytes(delta);
            peakUserMemory.accumulateAndGet(userLocalMemoryContext.getBytes(), Math::max);
            return future;
        }
        userLocalMemoryContext.addBytes(delta);
        return NOT_BLOCKED;
    }

    public boolean tryReserveUserMemory(long delta)
    {
        if (userLocalMemoryContext.tryReserveMemory(delta)) {
            peakUserMemory.accumulateAndGet(userLocalMemoryContext.getBytes(), Math::max);
            return true;
        }
        return false;
    }

    public ListenableFuture<?> updateRevocableMemory(long delta)
    {
        if (delta >= 0) {
            return revocableLocalMemoryContext.addBytes(delta);
        }
        revocableLocalMemoryContext.addBytes(delta);
        return NOT_BLOCKED;
    }

    public void close()
    {
        try (Closer closer = Closer.create()) {
            closer.register(userAggregateMemoryContext::close);
            closer.register(revocableAggregateMemoryContext::close);
            closer.register(systemAggregateMemoryContext::close);
            userLocalMemoryContext.setBytes(0);
            revocableLocalMemoryContext.setBytes(0);
            systemLocalMemoryContext.setBytes(0);
        }
        catch (IOException e) {
            throw new RuntimeException("Exception closing memory tracking context", e);
        }
    }

    public LocalMemoryContext localUserMemoryContext()
    {
        return userLocalMemoryContext;
    }

    public LocalMemoryContext localSystemMemoryContext()
    {
        return systemLocalMemoryContext;
    }

    public LocalMemoryContext newSystemMemoryContext()
    {
        return systemAggregateMemoryContext.newLocalMemoryContext();
    }

    public AggregatedMemoryContext newAggregateSystemMemoryContext()
    {
        return systemAggregateMemoryContext.newAggregatedMemoryContext();
    }

    public long getUserMemory()
    {
        return userAggregateMemoryContext.getBytes();
    }

    public long getRevocableMemory()
    {
        return revocableAggregateMemoryContext.getBytes();
    }

    public long getSystemMemory()
    {
        return systemAggregateMemoryContext.getBytes();
    }

    public long getPeakUserMemory()
    {
        return peakUserMemory.get();
    }

    public MemoryTrackingContext newMemoryTrackingContext()
    {
        return new MemoryTrackingContext(
                userAggregateMemoryContext.newAggregatedMemoryContext(),
                revocableAggregateMemoryContext.newAggregatedMemoryContext(),
                systemAggregateMemoryContext.newAggregatedMemoryContext());
    }

    @VisibleForTesting
    long getLocalSystemMemory()
    {
        return systemLocalMemoryContext.getBytes();
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("userAggregateMemoryContext", userAggregateMemoryContext)
                .add("revocableAggregateMemoryContext", revocableAggregateMemoryContext)
                .add("systemAggregateMemoryContext", systemAggregateMemoryContext)
                .add("userLocalMemoryContext", userLocalMemoryContext)
                .add("revocableLocalMemoryContext", revocableLocalMemoryContext)
                .add("systemLocalMemoryContext", systemLocalMemoryContext)
                .toString();
    }
}
