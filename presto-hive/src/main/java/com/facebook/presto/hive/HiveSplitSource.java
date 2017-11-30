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

import com.facebook.presto.hive.InternalHiveSplit.InternalHiveBlock;
import com.facebook.presto.hive.util.AsyncQueue;
import com.facebook.presto.spi.ColumnHandle;
import com.facebook.presto.spi.ConnectorSession;
import com.facebook.presto.spi.ConnectorSplit;
import com.facebook.presto.spi.ConnectorSplitSource;
import com.facebook.presto.spi.DynamicFilterDescription;
import com.facebook.presto.spi.PrestoException;
import com.facebook.presto.spi.predicate.Domain;
import com.facebook.presto.spi.predicate.TupleDomain;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.TypeManager;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.concurrent.MoreFutures;
import io.airlift.log.Logger;
import io.airlift.stats.CounterStat;
import io.airlift.units.DataSize;
import org.joda.time.DateTimeZone;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static com.facebook.presto.hive.HiveErrorCode.HIVE_FILE_NOT_FOUND;
import static com.facebook.presto.hive.HiveErrorCode.HIVE_UNKNOWN_ERROR;
import static com.facebook.presto.hive.HiveSessionProperties.getMaxInitialSplitSize;
import static com.facebook.presto.hive.HiveSessionProperties.getMaxSplitSize;
import static com.facebook.presto.spi.StandardErrorCode.GENERIC_INTERNAL_ERROR;
import static com.facebook.presto.spi.StandardErrorCode.NOT_SUPPORTED;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.Maps.transformValues;
import static io.airlift.concurrent.MoreFutures.failedFuture;
import static io.airlift.concurrent.MoreFutures.toCompletableFuture;
import static io.airlift.units.DataSize.succinctBytes;
import static java.lang.Math.min;
import static java.lang.Math.toIntExact;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;

class HiveSplitSource
        implements ConnectorSplitSource
{
    private static final Logger log = Logger.get(HiveSplit.class);

    private final String queryId;
    private final String databaseName;
    private final String tableName;
    private final TupleDomain<? extends ColumnHandle> compactEffectivePredicate;
    private final AsyncQueue<InternalHiveSplit> queue;
    private final AtomicInteger bufferedInternalSplitCount = new AtomicInteger();
    private final int maxOutstandingSplitsBytes;

    private final DataSize maxSplitSize;
    private final DataSize maxInitialSplitSize;
    private final AtomicInteger remainingInitialSplits;
    //TODO: Make this configurable. May be a session property
    public static final int DF_TIMEOUT = 500; // Microseconds to wait for DF summary

    private final AtomicReference<Throwable> throwable = new AtomicReference<>();
    private final HiveSplitLoader splitLoader;
    private final List<Future<DynamicFilterDescription>> filters;
    private final DateTimeZone timeZone;
    private final TypeManager typeManager;
    private volatile boolean closed;

    private final AtomicLong estimatedSplitSizeInBytes = new AtomicLong();

    private final CounterStat highMemorySplitSourceCounter;
    private final AtomicBoolean loggedHighMemoryWarning = new AtomicBoolean();

    HiveSplitSource(
            ConnectorSession session,
            String databaseName,
            String tableName,
            TupleDomain<? extends ColumnHandle> compactEffectivePredicate,
            int maxInitialSplits,
            int maxOutstandingSplits,
            DataSize maxOutstandingSplitsSize,
            HiveSplitLoader splitLoader,
            Executor executor,
            CounterStat highMemorySplitSourceCounter,
            List<Future<DynamicFilterDescription>> dynamicFilters,
            DateTimeZone timeZone,
            TypeManager typeManager)
    {
        requireNonNull(session, "session is null");
        this.queryId = session.getQueryId();
        this.databaseName = requireNonNull(databaseName, "databaseName is null");
        this.tableName = requireNonNull(tableName, "tableName is null");
        this.compactEffectivePredicate = requireNonNull(compactEffectivePredicate, "compactEffectivePredicate is null");
        this.queue = new AsyncQueue<>(maxOutstandingSplits, executor);
        this.maxOutstandingSplitsBytes = toIntExact(maxOutstandingSplitsSize.toBytes());
        this.splitLoader = requireNonNull(splitLoader, "splitLoader is null");
        this.highMemorySplitSourceCounter = requireNonNull(highMemorySplitSourceCounter, "highMemorySplitSourceCounter is null");

        this.maxSplitSize = getMaxSplitSize(session);
        this.maxInitialSplitSize = getMaxInitialSplitSize(session);
        this.remainingInitialSplits = new AtomicInteger(maxInitialSplits);
        this.filters = ImmutableList.copyOf(dynamicFilters);
        this.timeZone = timeZone;
        this.typeManager = typeManager;
    }

    /**
     * The upper bound of outstanding split count.
     * It might be larger than the actual number when called concurrently with other methods.
     */
    @VisibleForTesting
    int getBufferedInternalSplitCount()
    {
        return bufferedInternalSplitCount.get();
    }

    CompletableFuture<?> addToQueue(List<? extends InternalHiveSplit> splits)
    {
        CompletableFuture<?> lastResult = completedFuture(null);
        for (InternalHiveSplit split : splits) {
            lastResult = addToQueue(split);
        }
        return lastResult;
    }

    CompletableFuture<?> addToQueue(InternalHiveSplit split)
    {
        if (throwable.get() == null) {
            if (estimatedSplitSizeInBytes.addAndGet(split.getEstimatedSizeInBytes()) > maxOutstandingSplitsBytes) {
                // This limit should never be hit given there is a limit of maxOutstandingSplits.
                // If it's hit, it means individual splits are huge.
                if (loggedHighMemoryWarning.compareAndSet(false, true)) {
                    highMemorySplitSourceCounter.update(1);
                    log.warn("Split buffering for %s.%s in query %s exceeded memory limit (%s). %s splits are buffered.",
                            databaseName, tableName, queryId, succinctBytes(maxOutstandingSplitsBytes), getBufferedInternalSplitCount());
                }
                throw new PrestoException(GENERIC_INTERNAL_ERROR, format(
                        "Split buffering for %s.%s exceeded memory limit (%s). %s splits are buffered.",
                        databaseName, tableName, succinctBytes(maxOutstandingSplitsBytes), getBufferedInternalSplitCount()));
            }
            bufferedInternalSplitCount.incrementAndGet();
            return toCompletableFuture(queue.offer(split));
        }
        return completedFuture(null);
    }

    void noMoreSplits()
    {
        if (throwable.get() == null) {
            // Stop the split loader before finishing the queue.
            // Once the queue is finished, it will always return a completed future to avoid blocking any caller.
            // This could lead to a short period of busy loop in splitLoader (although unlikely in general setup).
            splitLoader.stop();
            queue.finish();
        }
    }

    void fail(Throwable e)
    {
        // The error must be recorded before setting the finish marker to make sure
        // isFinished will observe failure instead of successful completion.
        // Only record the first error message.
        if (throwable.compareAndSet(null, e)) {
            // Stop the split loader before finishing the queue.
            // Once the queue is finished, it will always return a completed future to avoid blocking any caller.
            // This could lead to a short period of busy loop in splitLoader (although unlikely in general setup).
            splitLoader.stop();
            queue.finish();
        }
    }

    @Override
    public CompletableFuture<List<ConnectorSplit>> getNextBatch(int maxSize)
    {
        checkState(!closed, "Provider is already closed");

        ListenableFuture<List<ConnectorSplit>> future = queue.borrowBatchAsync(maxSize, internalSplits -> {
            ImmutableList.Builder<InternalHiveSplit> splitsToInsertBuilder = ImmutableList.builder();
            ImmutableList.Builder<ConnectorSplit> resultBuilder = ImmutableList.builder();
            int removedEstimatedSizeInBytes = 0;
            for (InternalHiveSplit internalSplit : internalSplits) {
                long maxSplitBytes = maxSplitSize.toBytes();
                if (remainingInitialSplits.get() > 0) {
                    if (remainingInitialSplits.getAndDecrement() > 0) {
                        maxSplitBytes = maxInitialSplitSize.toBytes();
                    }
                }
                InternalHiveBlock block = internalSplit.currentBlock();
                long splitBytes;
                if (internalSplit.isSplittable()) {
                    splitBytes = min(maxSplitBytes, block.getEnd() - internalSplit.getStart());
                }
                else {
                    splitBytes = internalSplit.getEnd() - internalSplit.getStart();
                }

                resultBuilder.add(new HiveSplit(
                        databaseName,
                        tableName,
                        internalSplit.getPartitionName(),
                        internalSplit.getPath(),
                        internalSplit.getStart(),
                        splitBytes,
                        internalSplit.getFileSize(),
                        internalSplit.getSchema(),
                        internalSplit.getPartitionKeys(),
                        block.getAddresses(),
                        internalSplit.getBucketNumber(),
                        internalSplit.isForceLocalScheduling(),
                        (TupleDomain<HiveColumnHandle>) compactEffectivePredicate,
                        transformValues(internalSplit.getColumnCoercions(), HiveTypeName::toHiveType)));
                internalSplit.increaseStart(splitBytes);

                if (internalSplit.isDone()) {
                    removedEstimatedSizeInBytes += internalSplit.getEstimatedSizeInBytes();
                }
                else {
                    splitsToInsertBuilder.add(internalSplit);
                }
            }
            estimatedSplitSizeInBytes.addAndGet(-removedEstimatedSizeInBytes);

            List<InternalHiveSplit> splitsToInsert = splitsToInsertBuilder.build();
            List<ConnectorSplit> result = resultBuilder.build();
            bufferedInternalSplitCount.addAndGet(splitsToInsert.size() - result.size());

            return new AsyncQueue.BorrowResult<>(splitsToInsert, result);
        });

        // Before returning, check if there is a registered failure.
        // If so, we want to throw the error, instead of returning because the scheduler can block
        // while scheduling splits and wait for work to finish before continuing.  In this case,
        // we want to end the query as soon as possible and abort the work
        if (throwable.get() != null) {
            return failedFuture(throwable.get());
        }

        return toCompletableFuture(future).thenApply(this::dynamicallyFilterSplits);
    }

    private List<ConnectorSplit> dynamicallyFilterSplits(List<ConnectorSplit> splits)
    {
        List<DynamicFilterDescription> dynamicFilterDescriptions = getAvailableDynamicFilterDescriptions();

        TupleDomain<HiveColumnHandle> runtimeTupleDomain = dynamicFilterDescriptions.stream()
                .map(DynamicFilterDescription::getTupleDomain)
                .map(domain -> domain.transform(HiveColumnHandle.class::cast))
                .reduce(TupleDomain.none(), TupleDomain::columnWiseUnion);

        Optional<Map<HiveColumnHandle, Domain>> domains = runtimeTupleDomain.getDomains();
        if (!domains.isPresent() || domains.get().size() == 0) {
            return splits;
        }

        DomainsCache domainsCache = new DomainsCache(domains.get());

        ImmutableList.Builder<ConnectorSplit> result = ImmutableList.builder();
        for (ConnectorSplit split : splits) {
            HiveSplit hiveSplit = (HiveSplit) split;
            List<HivePartitionKey> partitionKeys = hiveSplit.getPartitionKeys();
            boolean addOriginalSplit = true;
            for (HivePartitionKey partitionKey : partitionKeys) {
                String partitionKeyName = partitionKey.getName().toLowerCase(Locale.ENGLISH);
                Collection<Domain> relevantDomains = domainsCache.getDomains(partitionKeyName);
                Type type = partitionKey.getHiveType().getType(typeManager);
                Object objectToWrite;
                try {
                    objectToWrite = HiveUtil.parsePartitionValue(partitionKey.getName(), partitionKey.getValue(), type, timeZone).getValue();
                }
                catch (PrestoException e) {
                    // if the type is not supported, skip pruning for that partition
                    if (e.getErrorCode().equals(NOT_SUPPORTED)) {
                        log.warn("Type " + type + " is not supported for Dynamic Partition Pruning");
                        continue;
                    }

                    throw e;
                }
                for (Domain predicateDomain : relevantDomains) {
                    addOriginalSplit = false;
                    if (predicateDomain.overlaps(Domain.singleValue(type, objectToWrite))) {
                        result.add(new HiveSplit(
                                hiveSplit.getDatabase(),
                                hiveSplit.getTable(),
                                hiveSplit.getPartitionName(),
                                hiveSplit.getPath(),
                                hiveSplit.getStart(),
                                hiveSplit.getLength(),
                                hiveSplit.getFileSize(),
                                hiveSplit.getSchema(),
                                hiveSplit.getPartitionKeys(),
                                hiveSplit.getAddresses(),
                                hiveSplit.getBucketNumber(),
                                hiveSplit.isForceLocalScheduling(),
                                hiveSplit.getEffectivePredicate().intersect(runtimeTupleDomain),
                                hiveSplit.getColumnCoercions()));
                        break;
                    }
                    else {
                        log.info("Partition got pruned due to dynamic filters: "
                                + hiveSplit.getPath() + " for table " + hiveSplit.getDatabase() + "." + hiveSplit.getTable());
                    }
                }
            }
            if (addOriginalSplit) {
                result.add(split);
            }
        }
        return result.build();
    }

    class DomainsCache
    {
        private final Map<HiveColumnHandle, Domain> domains;
        private final Map<String, Collection<Domain>> domainsCache = new HashMap<>();

        public DomainsCache(Map<HiveColumnHandle, Domain> domains)
        {
            this.domains = ImmutableMap.copyOf(domains);
        }

        public Collection<Domain> getDomains(String partitionName)
        {
            String partitionNameLower = partitionName.toLowerCase(Locale.ENGLISH);
            Collection<Domain> relevantDomains = domainsCache.get(partitionNameLower);
            if (relevantDomains == null) {
                relevantDomains = domains.entrySet().stream()
                        .filter(entry -> entry.getKey().getName().equalsIgnoreCase(partitionNameLower))
                        .map(Map.Entry::getValue)
                        .collect(toImmutableList());
                domainsCache.put(partitionNameLower, relevantDomains);
            }

            return relevantDomains;
        }
    }

    private List<DynamicFilterDescription> getAvailableDynamicFilterDescriptions()
    {
        ImmutableList.Builder<DynamicFilterDescription> dynamicFilterDescriptionBuilder = ImmutableList.builder();
        for (Future<DynamicFilterDescription> descriptionFuture : filters) {
            Optional<DynamicFilterDescription> description = MoreFutures.tryGetFutureValue(descriptionFuture, DF_TIMEOUT, TimeUnit.MICROSECONDS);
            description.ifPresent(dynamicFilterDescriptionBuilder::add);
        }
        return dynamicFilterDescriptionBuilder.build();
    }

    @Override
    public boolean isFinished()
    {
        // the finished marker must be checked before checking the throwable
        // to avoid a race with the fail method
        boolean isFinished = queue.isFinished();
        if (throwable.get() != null) {
            throw propagatePrestoException(throwable.get());
        }
        return isFinished;
    }

    @Override
    public void close()
    {
        // Stop the split loader before finishing the queue.
        // Once the queue is finished, it will always return a completed future to avoid blocking any caller.
        // This could lead to a short period of busy loop in splitLoader (although unlikely in general setup).
        splitLoader.stop();
        queue.finish();

        closed = true;
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
}
