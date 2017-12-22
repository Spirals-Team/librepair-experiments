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
package com.facebook.presto.operator;

import com.facebook.presto.spi.type.Type;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import javax.annotation.concurrent.GuardedBy;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static java.util.Objects.requireNonNull;

public class PagesSpatialIndexFactory
{
    private final List<Type> types;
    private final List<Type> outputTypes;

    @GuardedBy("this")
    private final List<SettableFuture<PagesSpatialIndex>> pagesSpatialIndexFutures = new ArrayList<>();

    @GuardedBy("this")
    private PagesSpatialIndex pagesSpatialIndex;

    public PagesSpatialIndexFactory(List<Type> types, List<Type> outputTypes)
    {
        this.types = ImmutableList.copyOf(types);
        this.outputTypes = outputTypes;
    }

    public List<Type> getTypes()
    {
        return types;
    }

    public List<Type> getOutputTypes()
    {
        return outputTypes;
    }

    public synchronized ListenableFuture<PagesSpatialIndex> createPagesSpatialIndex()
    {
        if (pagesSpatialIndex != null) {
            return immediateFuture(pagesSpatialIndex);
        }

        SettableFuture<PagesSpatialIndex> future = SettableFuture.create();
        pagesSpatialIndexFutures.add(future);
        return future;
    }

    public void lendPagesSpatialIndex(PagesSpatialIndex pagesSpatialIndex)
    {
        requireNonNull(pagesSpatialIndex, "pagesSpatialIndex is null");

        ImmutableList<SettableFuture<PagesSpatialIndex>> settableFutures;
        synchronized (this) {
            this.pagesSpatialIndex = pagesSpatialIndex;
            settableFutures = ImmutableList.copyOf(pagesSpatialIndexFutures);
        }

        for (SettableFuture<PagesSpatialIndex> settableFuture : settableFutures) {
            settableFuture.set(pagesSpatialIndex);
        }
    }

    public void destroy()
    {
        // TODO Implement
    }
}
