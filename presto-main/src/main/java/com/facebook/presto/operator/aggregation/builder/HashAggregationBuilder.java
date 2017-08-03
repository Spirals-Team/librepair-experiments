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
package com.facebook.presto.operator.aggregation.builder;

import com.facebook.presto.operator.HashCollisionsCounter;
import com.facebook.presto.spi.Page;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Iterator;

public interface HashAggregationBuilder
        extends AutoCloseable
{
    void processPage(Page page);

    Iterator<Page> buildResult();

    boolean isFull();

    ListenableFuture<?> isBlocked();

    void updateMemory();

    void recordHashCollisions(HashCollisionsCounter hashCollisionsCounter);

    @Override
    void close();
}
