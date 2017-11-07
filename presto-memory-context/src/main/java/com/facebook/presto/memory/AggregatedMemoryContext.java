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
import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.units.DataSize;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import static com.facebook.presto.memory.MemoryReservationHandler.NOT_BLOCKED;
import static com.google.common.base.MoreObjects.toStringHelper;
import static io.airlift.units.DataSize.Unit.MEGABYTE;

@ThreadSafe
public class AggregatedMemoryContext
{
    private static final long GUARANTEED_MEMORY = new DataSize(1, MEGABYTE).toBytes();

    @Nullable
    protected final AggregatedMemoryContext parentMemoryContext;
    @Nullable
    private final MemoryReservationHandler reservationHandler;
    @GuardedBy("this")
    protected long usedBytes;
    @GuardedBy("this")
    private boolean closed;

    public AggregatedMemoryContext()
    {
        this(null, null);
    }

    public AggregatedMemoryContext(MemoryReservationHandler reservationHandler)
    {
        this(null, reservationHandler);
    }

    private AggregatedMemoryContext(AggregatedMemoryContext parentMemoryContext, MemoryReservationHandler reservationHandler)
    {
        this.parentMemoryContext = parentMemoryContext;
        this.reservationHandler = reservationHandler;
    }

    public AggregatedMemoryContext newAggregatedMemoryContext()
    {
        return new AggregatedMemoryContext(this, null);
    }

    public LocalMemoryContext newLocalMemoryContext()
    {
        return new LocalMemoryContext(this);
    }

    public synchronized long getBytes()
    {
        return usedBytes;
    }

    synchronized ListenableFuture<?> updateBytes(long bytes)
    {
        if (bytes == 0) {
            return NOT_BLOCKED;
        }

        ListenableFuture<?> future = NOT_BLOCKED;
        // delegate to parent if exists
        if (parentMemoryContext != null) {
            // update the parent before updating usedBytes as it may throw a runtime exception (e.g., ExceededMemoryLimitException)
            future = parentMemoryContext.updateBytes(bytes);
            usedBytes += bytes;
            return future;
        }

        // reservationHandler is called only by the root AggregatedMemoryContext
        if (reservationHandler != null) {
            future = reservationHandler.reserveMemory(bytes);
        }

        usedBytes += bytes;

        // Never block queries using a trivial amount of memory
        if (usedBytes < GUARANTEED_MEMORY) {
            future = NOT_BLOCKED;
        }

        return future;
    }

    synchronized boolean tryReserveMemory(long bytes)
    {
        if (bytes == 0) {
            return true;
        }

        // delegate to parent if exists
        if (parentMemoryContext != null) {
            if (parentMemoryContext.tryReserveMemory(bytes)) {
                usedBytes += bytes;
                return true;
            }
            return false;
        }
        // reservationHandler is called only by the root AggregatedMemoryContext
        if (reservationHandler != null) {
            if (reservationHandler.tryReserveMemory(bytes)) {
                usedBytes += bytes;
                return true;
            }
            return false;
        }
        return true;
    }

    public synchronized void close()
    {
        if (closed) {
            return;
        }
        closed = true;
        // delegate to parent if exists
        if (parentMemoryContext != null) {
            parentMemoryContext.updateBytes(-usedBytes);
            usedBytes = 0;
            return;
        }
        // reservationHandler is called only by the root AggregatedMemoryContext
        if (reservationHandler != null) {
            reservationHandler.reserveMemory(-usedBytes);
        }
        usedBytes = 0;
    }

    @VisibleForTesting
    MemoryReservationHandler getReservationHandler()
    {
        return reservationHandler;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("usedBytes", usedBytes)
                .add("closed", closed)
                .toString();
    }
}
