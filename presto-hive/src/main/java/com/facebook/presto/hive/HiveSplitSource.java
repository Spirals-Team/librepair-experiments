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
package com.facebook.presto.hive;

import com.facebook.presto.hive.util.AsyncQueue;
import com.facebook.presto.spi.ColumnHandle;
import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.ConnectorSplitSource;
import com.facebook.presto.spi.HostAddress;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.predicate.TupleDomain;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static com.facebook.presto.hive.HiveErrorCode.HIVE_FILE_NOT_FOUND;
import static com.facebook.presto.hive.HiveErrorCode.HIVE_UNKNOWN_ERROR;
import static com.facebook.presto.hive.HiveSplitSource.StateKind.CLOSED;
import static com.facebook.presto.hive.HiveSplitSource.StateKind.FAILED;
import static com.facebook.presto.hive.HiveSplitSource.StateKind.INITIAL;
import static com.facebook.presto.hive.HiveSplitSource.StateKind.NO_MORE_SPLITS;
import static com.facebook.presto.spi.StandardErrorCode.GENERIC_INTERNAL_ERROR;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Maps.transformValues;
import static io.airlift.concurrent.MoreFutures.failedFuture;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;

class HiveSplitSource
        implements ConnectorSplitSource
{
    private final String connectorId;
    private final String databaseName;
    private final String tableName;
    private final TupleDomain<? extends ColumnHandle> compactEffectivePredicate;
    private final PerBucket queues;
    private final HiveSplitLoader splitLoader;
    private final AtomicReference<State> stateReference;
    private final OptionalInt splitGroupCount;

    private final AtomicInteger estimatedSplitSizeInBytes = new AtomicInteger();

    // This special object makes sure that isFinished returns true only after getNextBatch
    // has indicated end of driver group by means of flag in ConnectorSplitBatch return value.
    // Specifically, this changes the outcome of getOutstandingSplitCount in isFinished.
    private static final ConnectorSplit INTERNAL_END_OF_QUEUE_POISON = new ConnectorSplit()
    {
        @Override
        public boolean isRemotelyAccessible()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<HostAddress> getAddresses()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getInfo()
        {
            throw new UnsupportedOperationException();
        }
    };

    private HiveSplitSource(
            String connectorId,
            String databaseName,
            String tableName,
            TupleDomain<? extends ColumnHandle> compactEffectivePredicate,
            PerBucket queues,
            HiveSplitLoader splitLoader,
            OptionalInt splitGroupCount,
            AtomicReference<State> stateReference)
    {
        this.connectorId = requireNonNull(connectorId, "connectorId is null");
        this.databaseName = requireNonNull(databaseName, "databaseName is null");
        this.tableName = requireNonNull(tableName, "tableName is null");
        this.compactEffectivePredicate = requireNonNull(compactEffectivePredicate, "compactEffectivePredicate is null");
        this.queues = requireNonNull(queues, "queues is null");
        this.splitLoader = requireNonNull(splitLoader, "splitLoader is null");
        this.splitGroupCount = requireNonNull(splitGroupCount, "splitGroupCount is null");
        this.stateReference = requireNonNull(stateReference, "stateReference is null");
    }

    public static HiveSplitSource allAtOnce(
            String connectorId,
            String databaseName,
            String tableName,
            TupleDomain<? extends ColumnHandle> compactEffectivePredicate,
            int maxOutstandingSplits,
            HiveSplitLoader splitLoader,
            Executor executor)
    {
        AtomicReference<State> stateReference = new AtomicReference<>(State.initial());
        return new HiveSplitSource(
                connectorId,
                databaseName,
                tableName,
                compactEffectivePredicate,
                new PerBucket()
                {
                    private final AsyncQueue<Optional<InternalHiveSplit>> queue = new AsyncQueue<>(maxOutstandingSplits, executor);

                    @Override
                    public CompletableFuture<?> offer(OptionalInt bucketNumber, Optional<InternalHiveSplit> connectorSplit)
                    {
                        // bucketNumber can be non-empty because BackgroundHiveSplitLoader does not have knowledge of execution plan
                        return queue.offer(connectorSplit);
                    }

                    @Override
                    public CompletableFuture<List<Optional<InternalHiveSplit>>> getBatchAsync(OptionalInt bucketNumber, int maxSize)
                    {
                        checkArgument(!bucketNumber.isPresent());
                        return queue.getBatchAsync(maxSize);
                    }

                    @Override
                    public void offerAndFinish(Optional<InternalHiveSplit> connectorSplit)
                    {
                        queue.offerAndFinish(connectorSplit);
                    }

                    @Override
                    public void finish()
                    {
                        queue.finish();
                    }

                    @Override
                    public int getOutstandingSplitCount()
                    {
                        return queue.size();
                    }
                },
                splitLoader,
                OptionalInt.empty(),
                stateReference);
    }

    public static HiveSplitSource bucketed(
            String connectorId,
            String databaseName,
            String tableName,
            TupleDomain<? extends ColumnHandle> compactEffectivePredicate,
            int maxOutstandingSplitsPerBucket,
            HiveSplitLoader splitLoader,
            int driverGroupCount,
            Executor executor)
    {
        AtomicReference<State> stateReference = new AtomicReference<>(State.initial());
        return new HiveSplitSource(
                connectorId,
                databaseName,
                tableName,
                compactEffectivePredicate,
                new PerBucket()
                {
                    private final Map<OptionalInt, AsyncQueue<Optional<InternalHiveSplit>>> queues = new ConcurrentHashMap<>();
                    private final AtomicInteger outstandingSplitCount = new AtomicInteger();

                    @Override
                    public CompletableFuture<?> offer(OptionalInt bucketNumber, Optional<InternalHiveSplit> connectorSplit)
                    {
                        checkArgument(bucketNumber.isPresent());
                        AsyncQueue<Optional<InternalHiveSplit>> queue = queueFor(bucketNumber);
                        outstandingSplitCount.incrementAndGet();
                        queue.offer(connectorSplit);
                        // Do not block "offer" when running split discovery in bucketed mode.
                        // A limit is enforced on estimatedSplitSizeInBytes.
                        return completedFuture(null);
                    }

                    @Override
                    public CompletableFuture<List<Optional<InternalHiveSplit>>> getBatchAsync(OptionalInt bucketNumber, int maxSize)
                    {
                        checkArgument(bucketNumber.isPresent());
                        AsyncQueue<Optional<InternalHiveSplit>> queue = queueFor(bucketNumber);
                        return queue.getBatchAsync(maxSize).thenApply(connectorSplits -> {
                            outstandingSplitCount.addAndGet(-connectorSplits.size());
                            return connectorSplits;
                        });
                    }

                    @Override
                    public void offerAndFinish(Optional<InternalHiveSplit> connectorSplit)
                    {
                        queues.values().forEach(queue -> {
                            outstandingSplitCount.incrementAndGet();
                            queue.offerAndFinish(connectorSplit);
                        });
                    }

                    @Override
                    public void finish()
                    {
                        queues.values().forEach(AsyncQueue::finish);
                    }

                    @Override
                    public int getOutstandingSplitCount()
                    {
                        return outstandingSplitCount.get();
                    }

                    public AsyncQueue<Optional<InternalHiveSplit>> queueFor(OptionalInt driverGroupId)
                    {
                        return queues.computeIfAbsent(driverGroupId, ignored -> {
                            State state = stateReference.get();
                            if (state.getKind() != INITIAL) {
                                throw new IllegalStateException();
                            }
                            return new AsyncQueue<>(maxOutstandingSplitsPerBucket, executor);
                        });
                    }
                },
                splitLoader,
                OptionalInt.of(driverGroupCount),
                stateReference);
    }

    @VisibleForTesting
    int getOutstandingSplitCount()
    {
        return queues.getOutstandingSplitCount();
    }

    CompletableFuture<?> addToQueue(Iterator<? extends InternalHiveSplit> splits)
    {
        CompletableFuture<?> lastResult = completedFuture(null);
        while (splits.hasNext()) {
            InternalHiveSplit split = splits.next();
            lastResult = addToQueue(split);
        }
        return lastResult;
    }

    CompletableFuture<?> addToQueue(InternalHiveSplit split)
    {
        if (stateReference.get().getKind() != INITIAL) {
            return completedFuture(null);
        }
        if (estimatedSplitSizeInBytes.addAndGet(split.getEstimatedSizeInBytes()) > 32 * 1024 * 1024) {
            // TODO: investigate alternative split discovery strategies when this error is hit.
            throw new PrestoException(GENERIC_INTERNAL_ERROR, format(
                    "Split buffering for %s.%s takes too much memory (32MB). %s splits are buffered. This is likely related to the experimental feature of bucket-by-bucket execution.",
                    databaseName, tableName, getOutstandingSplitCount()));
        }
        OptionalInt bucketNumber = split.getBucketNumber();
        return queues.offer(bucketNumber, Optional.of(split));
    }

    void noMoreSplits()
    {
        if (setIf(stateReference, State.noMoreSplits(), state -> state.getKind() == INITIAL)) {
            // add finish the queue
            queues.offerAndFinish(Optional.empty());

            // no need to process any more jobs
            splitLoader.stop();
        }
    }

    void fail(Throwable e)
    {
        // only record the first error message
        if (setIf(stateReference, State.failed(e), state -> state.getKind() == INITIAL)) {
            // add finish the queue
            queues.finish();

            // no need to process any more jobs
            splitLoader.stop();
        }
    }

    @Override
    public CompletableFuture<List<ConnectorSplit>> getNextBatch(int maxSize)
    {
        return getNextBatch(OptionalInt.empty(), maxSize).thenApply(ConnectorSplitBatch::getSplits);
    }

    @Override
    public CompletableFuture<ConnectorSplitBatch> getNextBatch(OptionalInt splitGroupId, int maxSize)
    {
        State state = stateReference.get();
        checkState(state.getKind() != CLOSED, "HiveSplitSource is already closed");
        if (state.getKind() == FAILED) {
            return failedFuture(state.getThrowable());
        }

        return queues.getBatchAsync(splitGroupId, maxSize).thenApply((List<Optional<InternalHiveSplit>> internalSplits) -> {
            // Use the `state` got at the beginning of the function (outside this lambda). Otherwise, it's not thread-safe.
            if (internalSplits.isEmpty()) {
                return new ConnectorSplitBatch(ImmutableList.of(), state.getKind() == StateKind.NO_MORE_SPLITS);
            }
            ImmutableList.Builder<ConnectorSplit> result = ImmutableList.builder();
            boolean noMoreSplits = false;
            int totalEstimatedSizeInBytes = 0;
            for (Optional<InternalHiveSplit> element : internalSplits) {
                if (!element.isPresent()) {
                    noMoreSplits = true;
                    continue;
                }
                InternalHiveSplit internalSplit = element.get();
                totalEstimatedSizeInBytes += internalSplit.getEstimatedSizeInBytes();
                result.add(new HiveSplit(
                        connectorId,
                        databaseName,
                        tableName,
                        internalSplit.getPartitionName(),
                        internalSplit.getPath(),
                        internalSplit.getStart(),
                        internalSplit.getLength(),
                        internalSplit.getFileSize(),
                        internalSplit.getSchema(),
                        internalSplit.getPartitionKeys(),
                        internalSplit.getAddresses(),
                        internalSplit.getBucketNumber(),
                        internalSplit.isForceLocalScheduling(),
                        (TupleDomain<HiveColumnHandle>) compactEffectivePredicate,
                        transformValues(internalSplit.getColumnCoercions(), HiveTypeName::toHiveType)));
            }
            estimatedSplitSizeInBytes.addAndGet(-totalEstimatedSizeInBytes);
            return new ConnectorSplitBatch(result.build(), noMoreSplits);
        });
    }

    @Override
    public OptionalInt getSplitGroupCount()
    {
        return splitGroupCount;
    }

    @Override
    public boolean isFinished()
    {
        State state = stateReference.get();

        if (state.getKind() == FAILED) {
            throw propagatePrestoException(state.getThrowable());
        }
        if (state.getKind() == INITIAL) {
            return false;
        }
        return queues.getOutstandingSplitCount() == 0;
    }

    @Override
    public void close()
    {
        if (setIf(stateReference, State.closed(), state -> state.getKind() == INITIAL || state.getKind() == NO_MORE_SPLITS)) {
            // add finish the queue
            queues.finish();

            // no need to process any more jobs
            splitLoader.stop();
        }
    }

    private static <T> boolean setIf(AtomicReference<T> atomicReference, T newValue, Predicate<T> predicate)
    {
        while (true) {
            T current = atomicReference.get();
            if (!predicate.test(current)) {
                return false;
            }
            if (atomicReference.compareAndSet(current, newValue)) {
                return true;
            }
        }
    }

    private static RuntimeException propagatePrestoException(Throwable throwable)
    {
        if (throwable instanceof PrestoException) {
            throw (PrestoException) throwable;
        }
        if (throwable instanceof FileNotFoundException) {
            throw new PrestoException(HIVE_FILE_NOT_FOUND, throwable);
        }
        throw new PrestoException(HIVE_UNKNOWN_ERROR, throwable);
    }

    interface PerBucket
    {
        CompletableFuture<?> offer(OptionalInt bucketNumber, Optional<InternalHiveSplit> split);

        CompletableFuture<List<Optional<InternalHiveSplit>>> getBatchAsync(OptionalInt bucketNumber, int maxSize);

        void offerAndFinish(Optional<InternalHiveSplit> split);

        void finish();

        int getOutstandingSplitCount();
    }

    static class State
    {
        private final StateKind kind;
        private final Throwable throwable;

        private State(StateKind kind, Throwable throwable)
        {
            this.kind = kind;
            this.throwable = throwable;
        }

        public StateKind getKind()
        {
            return kind;
        }

        public Throwable getThrowable()
        {
            checkState(throwable != null);
            return throwable;
        }

        public static State initial()
        {
            return new State(INITIAL, null);
        }

        public static State noMoreSplits()
        {
            return new State(NO_MORE_SPLITS, null);
        }

        public static State failed(Throwable throwable)
        {
            return new State(FAILED, throwable);
        }

        public static State closed()
        {
            return new State(CLOSED, null);
        }
    }

    enum StateKind {
        INITIAL,
        NO_MORE_SPLITS,
        FAILED,
        CLOSED,
    }
}
