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

import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import static com.facebook.presto.memory.MemoryReservationHandler.NOT_BLOCKED;
import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

@ThreadSafe
public class LocalMemoryContext
{
    @Nonnull
    private final AggregatedMemoryContext parentMemoryContext;
    @GuardedBy("this")
    private long usedBytes;

    public LocalMemoryContext(AggregatedMemoryContext parentMemoryContext)
    {
        this.parentMemoryContext = requireNonNull(parentMemoryContext);
    }

    public synchronized long getBytes()
    {
        return usedBytes;
    }

    public synchronized ListenableFuture<?> setBytes(long bytes)
    {
        if (bytes == usedBytes) {
            return NOT_BLOCKED;
        }

        // update the parent first as it may throw a runtime exception (e.g., ExceededMemoryLimitException)
        ListenableFuture<?> future = parentMemoryContext.updateBytes(bytes - usedBytes);
        usedBytes = bytes;
        return future;
    }

    public synchronized ListenableFuture<?> addBytes(long bytes)
    {
        return setBytes(usedBytes + bytes);
    }

    public synchronized boolean tryReserveMemory(long bytes)
    {
        if (parentMemoryContext.tryReserveMemory(bytes)) {
            usedBytes += bytes;
            return true;
        }
        return false;
    }

    /**
     * This method transfers ownership of allocations from this memory context to the "to" memory context,
     * where parent of this is a descendant of to.parent (there can be multiple AggregatedMemoryContexts between them).
     *
     * During the transfer we don't call the reservation handlers to reflect any state changes outside of the contexts.
     */
    public synchronized void transferOwnership(LocalMemoryContext to)
    {
        AggregatedMemoryContext parent = parentMemoryContext;
        while (parent != null && parent != to.parentMemoryContext) {
            synchronized (parent) {
                parent.usedBytes -= usedBytes;
            }
            parent = parent.parentMemoryContext;
        }
        usedBytes = 0;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("usedBytes", usedBytes)
                .toString();
    }
}
