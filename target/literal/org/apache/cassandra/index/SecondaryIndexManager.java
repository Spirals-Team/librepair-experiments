package org.apache.cassandra.index;


public class SecondaryIndexManager implements org.apache.cassandra.index.IndexRegistry {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.apache.cassandra.index.SecondaryIndexManager.class);

    private java.util.Map<String, org.apache.cassandra.index.Index> indexes = com.google.common.collect.Maps.newConcurrentMap();

    private static final java.util.concurrent.ExecutorService asyncExecutor = new org.apache.cassandra.concurrent.JMXEnabledThreadPoolExecutor(1, org.apache.cassandra.concurrent.StageManager.KEEPALIVE, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue(), new org.apache.cassandra.concurrent.NamedThreadFactory("SecondaryIndexManagement"), "internal");

    private static final java.util.concurrent.ExecutorService blockingExecutor = com.google.common.util.concurrent.MoreExecutors.newDirectExecutorService();

    public final org.apache.cassandra.index.ColumnFamilyStore baseCfs;

    public SecondaryIndexManager(org.apache.cassandra.index.ColumnFamilyStore baseCfs) {
        this.baseCfs = baseCfs;
    }

    public void reload() {
        org.apache.cassandra.schema.Indexes tableIndexes = baseCfs.metadata.getIndexes();
        indexes.keySet().stream().filter(( indexName) -> !(tableIndexes.has(indexName))).forEach(this::removeIndex);
        for (org.apache.cassandra.schema.IndexMetadata tableIndex : tableIndexes)
            addIndex(tableIndex);

    }

    private java.util.concurrent.Future<?> reloadIndex(org.apache.cassandra.schema.IndexMetadata indexDef) {
        org.apache.cassandra.index.Index index = indexes.get(indexDef.name);
        java.util.concurrent.Callable<?> reloadTask = index.getMetadataReloadTask(indexDef);
        return reloadTask == null ? com.google.common.util.concurrent.Futures.immediateFuture(null) : org.apache.cassandra.index.SecondaryIndexManager.blockingExecutor.submit(index.getMetadataReloadTask(indexDef));
    }

    private java.util.concurrent.Future<?> createIndex(org.apache.cassandra.schema.IndexMetadata indexDef) {
        org.apache.cassandra.index.Index index = createInstance(indexDef);
        index.register(this);
        final java.util.concurrent.Callable<?> initialBuildTask = (indexes.containsKey(indexDef.name)) ? index.getInitializationTask() : null;
        return initialBuildTask == null ? com.google.common.util.concurrent.Futures.immediateFuture(null) : org.apache.cassandra.index.SecondaryIndexManager.asyncExecutor.submit(initialBuildTask);
    }

    public synchronized java.util.concurrent.Future<?> addIndex(org.apache.cassandra.schema.IndexMetadata indexDef) {
        if (indexes.containsKey(indexDef.name))
            return reloadIndex(indexDef);
        else
            return createIndex(indexDef);

    }

    public synchronized void removeIndex(String indexName) {
        org.apache.cassandra.index.Index index = indexes.remove(indexName);
        if (null != index) {
            markIndexRemoved(indexName);
            org.apache.cassandra.index.SecondaryIndexManager.executeBlocking(index.getInvalidateTask());
        }
    }

    public java.util.Set<org.apache.cassandra.schema.IndexMetadata> getDependentIndexes(org.apache.cassandra.config.ColumnDefinition column) {
        if (indexes.isEmpty())
            return java.util.Collections.emptySet();

        java.util.Set<org.apache.cassandra.schema.IndexMetadata> dependentIndexes = new java.util.HashSet<>();
        for (org.apache.cassandra.index.Index index : indexes.values())
            if (index.dependsOn(column))
                dependentIndexes.add(index.getIndexMetadata());


        return dependentIndexes;
    }

    public void markAllIndexesRemoved() {
        getBuiltIndexNames().forEach(this::markIndexRemoved);
    }

    public void rebuildIndexesBlocking(java.util.Collection<org.apache.cassandra.io.sstable.format.SSTableReader> sstables, java.util.Set<String> indexNames) {
        java.util.Set<org.apache.cassandra.index.Index> toRebuild = indexes.values().stream().filter(( index) -> indexNames.contains(index.getIndexMetadata().name)).filter(Index::shouldBuildBlocking).collect(java.util.stream.Collectors.toSet());
        if (toRebuild.isEmpty()) {
            org.apache.cassandra.index.SecondaryIndexManager.logger.info("No defined indexes with the supplied names: {}", com.google.common.base.Joiner.on(',').join(indexNames));
            return;
        }
        toRebuild.forEach(( indexer) -> markIndexRemoved(indexer.getIndexMetadata().name));
        buildIndexesBlocking(sstables, toRebuild);
        toRebuild.forEach(( indexer) -> markIndexBuilt(indexer.getIndexMetadata().name));
    }

    public void buildAllIndexesBlocking(java.util.Collection<org.apache.cassandra.io.sstable.format.SSTableReader> sstables) {
        buildIndexesBlocking(sstables, indexes.values().stream().filter(Index::shouldBuildBlocking).collect(java.util.stream.Collectors.toSet()));
    }

    public void buildIndexBlocking(org.apache.cassandra.index.Index index) {
        if (index.shouldBuildBlocking()) {
            try (org.apache.cassandra.index.ColumnFamilyStore.RefViewFragment viewFragment = baseCfs.selectAndReference(org.apache.cassandra.db.lifecycle.View.select(SSTableSet.CANONICAL));org.apache.cassandra.utils.concurrent.Refs<org.apache.cassandra.io.sstable.format.SSTableReader> sstables = viewFragment.refs) {
                buildIndexesBlocking(sstables, java.util.Collections.singleton(index));
                markIndexBuilt(index.getIndexMetadata().name);
            }
        }
    }

    public static boolean isIndexColumnFamilyStore(org.apache.cassandra.index.ColumnFamilyStore cfs) {
        return org.apache.cassandra.index.SecondaryIndexManager.isIndexColumnFamily(cfs.name);
    }

    public static boolean isIndexColumnFamily(String cfName) {
        return cfName.contains(Directories.SECONDARY_INDEX_NAME_SEPARATOR);
    }

    public static org.apache.cassandra.index.ColumnFamilyStore getParentCfs(org.apache.cassandra.index.ColumnFamilyStore cfs) {
        String parentCfs = org.apache.cassandra.index.SecondaryIndexManager.getParentCfsName(cfs.name);
        return cfs.keyspace.getColumnFamilyStore(parentCfs);
    }

    public static String getParentCfsName(String cfName) {
        assert org.apache.cassandra.index.SecondaryIndexManager.isIndexColumnFamily(cfName);
        return org.apache.commons.lang3.StringUtils.substringBefore(cfName, Directories.SECONDARY_INDEX_NAME_SEPARATOR);
    }

    public static String getIndexName(org.apache.cassandra.index.ColumnFamilyStore cfs) {
        return org.apache.cassandra.index.SecondaryIndexManager.getIndexName(cfs.name);
    }

    public static String getIndexName(String cfName) {
        assert org.apache.cassandra.index.SecondaryIndexManager.isIndexColumnFamily(cfName);
        return org.apache.commons.lang3.StringUtils.substringAfter(cfName, Directories.SECONDARY_INDEX_NAME_SEPARATOR);
    }

    private void buildIndexesBlocking(java.util.Collection<org.apache.cassandra.io.sstable.format.SSTableReader> sstables, java.util.Set<org.apache.cassandra.index.Index> indexes) {
        if (indexes.isEmpty())
            return;

        org.apache.cassandra.index.SecondaryIndexManager.logger.info("Submitting index build of {} for data in {}", indexes.stream().map(( i) -> i.getIndexMetadata().name).collect(java.util.stream.Collectors.joining(",")), sstables.stream().map(SSTableReader::toString).collect(java.util.stream.Collectors.joining(",")));
        org.apache.cassandra.index.SecondaryIndexBuilder builder = new org.apache.cassandra.index.SecondaryIndexBuilder(baseCfs, indexes, new org.apache.cassandra.io.sstable.ReducingKeyIterator(sstables));
        java.util.concurrent.Future<?> future = CompactionManager.instance.submitIndexBuild(builder);
        org.apache.cassandra.utils.FBUtilities.waitOnFuture(future);
        flushIndexesBlocking(indexes);
        org.apache.cassandra.index.SecondaryIndexManager.logger.info("Index build of {} complete", indexes.stream().map(( i) -> i.getIndexMetadata().name).collect(java.util.stream.Collectors.joining(",")));
    }

    public void markIndexBuilt(String indexName) {
        org.apache.cassandra.index.SystemKeyspace.setIndexBuilt(baseCfs.keyspace.getName(), indexName);
    }

    public void markIndexRemoved(String indexName) {
        org.apache.cassandra.index.SystemKeyspace.setIndexRemoved(baseCfs.keyspace.getName(), indexName);
    }

    public org.apache.cassandra.index.Index getIndexByName(String indexName) {
        return indexes.get(indexName);
    }

    private org.apache.cassandra.index.Index createInstance(org.apache.cassandra.schema.IndexMetadata indexDef) {
        org.apache.cassandra.index.Index newIndex;
        if (indexDef.isCustom()) {
            assert (indexDef.options) != null;
            String className = indexDef.options.get(IndexTarget.CUSTOM_INDEX_OPTION_NAME);
            assert !(com.google.common.base.Strings.isNullOrEmpty(className));
            try {
                java.lang.Class<? extends org.apache.cassandra.index.Index> indexClass = org.apache.cassandra.utils.FBUtilities.classForName(className, "Index");
                java.lang.reflect.Constructor ctor = indexClass.getConstructor(org.apache.cassandra.index.ColumnFamilyStore.class, org.apache.cassandra.schema.IndexMetadata.class);
                newIndex = ((org.apache.cassandra.index.Index) (ctor.newInstance(baseCfs, indexDef)));
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
            }
        }else {
            newIndex = org.apache.cassandra.index.internal.CassandraIndex.newIndex(baseCfs, indexDef);
        }
        return newIndex;
    }

    public void truncateAllIndexesBlocking(final long truncatedAt) {
        org.apache.cassandra.index.SecondaryIndexManager.executeAllBlocking(indexes.values().stream(), ( index) -> index.getTruncateTask(truncatedAt));
    }

    public void invalidateAllIndexesBlocking() {
        markAllIndexesRemoved();
        org.apache.cassandra.index.SecondaryIndexManager.executeAllBlocking(indexes.values().stream(), Index::getInvalidateTask);
    }

    public void flushAllIndexesBlocking() {
        flushIndexesBlocking(com.google.common.collect.ImmutableSet.copyOf(indexes.values()));
    }

    public void flushIndexesBlocking(java.util.Set<org.apache.cassandra.index.Index> indexes) {
        if (indexes.isEmpty())
            return;

        java.util.List<java.util.concurrent.Future<?>> wait = new java.util.ArrayList<>();
        java.util.List<org.apache.cassandra.index.Index> nonCfsIndexes = new java.util.ArrayList<>();
        synchronized(baseCfs.getTracker()) {
            indexes.forEach(( index) -> index.getBackingTable().map(( cfs) -> wait.add(cfs.forceFlush())).orElseGet(() -> nonCfsIndexes.add(index)));
        }
        org.apache.cassandra.index.SecondaryIndexManager.executeAllBlocking(nonCfsIndexes.stream(), Index::getBlockingFlushTask);
        org.apache.cassandra.utils.FBUtilities.waitOnFutures(wait);
    }

    public void flushAllNonCFSBackedIndexesBlocking() {
        java.util.Set<org.apache.cassandra.index.Index> customIndexers = indexes.values().stream().filter(( index) -> !(index.getBackingTable().isPresent())).collect(java.util.stream.Collectors.toSet());
        flushIndexesBlocking(customIndexers);
    }

    public java.util.List<String> getBuiltIndexNames() {
        java.util.Set<String> allIndexNames = new java.util.HashSet<>();
        indexes.values().stream().map(( i) -> i.getIndexMetadata().name).forEach(allIndexNames::add);
        return org.apache.cassandra.index.SystemKeyspace.getBuiltIndexes(baseCfs.keyspace.getName(), allIndexNames);
    }

    public java.util.Set<org.apache.cassandra.index.ColumnFamilyStore> getAllIndexColumnFamilyStores() {
        java.util.Set<org.apache.cassandra.index.ColumnFamilyStore> backingTables = new java.util.HashSet<>();
        indexes.values().forEach(( index) -> index.getBackingTable().ifPresent(backingTables::add));
        return backingTables;
    }

    public boolean hasIndexes() {
        return !(indexes.isEmpty());
    }

    public void indexPartition(org.apache.cassandra.index.UnfilteredRowIterator partition, org.apache.cassandra.utils.concurrent.OpOrder.Group opGroup, java.util.Set<org.apache.cassandra.index.Index> indexes, int nowInSec) {
        if (!(indexes.isEmpty())) {
            org.apache.cassandra.index.DecoratedKey key = partition.partitionKey();
            java.util.Set<org.apache.cassandra.index.Index.Indexer> indexers = indexes.stream().map(( index) -> index.indexerFor(key, nowInSec, opGroup, IndexTransaction.Type.UPDATE)).collect(java.util.stream.Collectors.toSet());
            indexers.forEach(Index.Indexer::begin);
            try (org.apache.cassandra.index.RowIterator filtered = org.apache.cassandra.index.UnfilteredRowIterators.filter(partition, nowInSec)) {
                if (!(filtered.staticRow().isEmpty()))
                    indexers.forEach(( indexer) -> indexer.insertRow(filtered.staticRow()));

                while (filtered.hasNext()) {
                    org.apache.cassandra.index.Row row = filtered.next();
                    indexers.forEach(( indexer) -> indexer.insertRow(row));
                } 
            }
            indexers.forEach(Index.Indexer::finish);
        }
    }

    public void deletePartition(org.apache.cassandra.index.UnfilteredRowIterator partition, int nowInSec) {
        org.apache.cassandra.index.CleanupTransaction indexTransaction = newCleanupTransaction(partition.partitionKey(), partition.columns(), nowInSec);
        indexTransaction.start();
        indexTransaction.onPartitionDeletion(new org.apache.cassandra.index.DeletionTime(org.apache.cassandra.utils.FBUtilities.timestampMicros(), nowInSec));
        indexTransaction.commit();
        while (partition.hasNext()) {
            org.apache.cassandra.index.Unfiltered unfiltered = partition.next();
            if ((unfiltered.kind()) != (Unfiltered.Kind.ROW))
                continue;

            indexTransaction = newCleanupTransaction(partition.partitionKey(), partition.columns(), nowInSec);
            indexTransaction.start();
            indexTransaction.onRowDelete(((org.apache.cassandra.index.Row) (unfiltered)));
            indexTransaction.commit();
        } 
    }

    public org.apache.cassandra.index.Index getBestIndexFor(org.apache.cassandra.index.ReadCommand command) {
        if ((indexes.isEmpty()) || (command.rowFilter().isEmpty()))
            return null;

        java.util.Set<org.apache.cassandra.index.Index> searchableIndexes = new java.util.HashSet<>();
        for (org.apache.cassandra.db.filter.RowFilter.Expression expression : command.rowFilter()) {
            if (expression.isCustom()) {
                org.apache.cassandra.db.filter.RowFilter.CustomExpression customExpression = ((org.apache.cassandra.db.filter.RowFilter.CustomExpression) (expression));
                org.apache.cassandra.index.SecondaryIndexManager.logger.trace("Command contains a custom index expression, using target index {}", customExpression.getTargetIndex().name);
                org.apache.cassandra.tracing.Tracing.trace("Command contains a custom index expression, using target index {}", customExpression.getTargetIndex().name);
                return indexes.get(customExpression.getTargetIndex().name);
            }else {
                indexes.values().stream().filter(( index) -> index.supportsExpression(expression.column(), expression.operator())).forEach(searchableIndexes::add);
            }
        }
        if (searchableIndexes.isEmpty()) {
            org.apache.cassandra.index.SecondaryIndexManager.logger.trace("No applicable indexes found");
            org.apache.cassandra.tracing.Tracing.trace("No applicable indexes found");
            return null;
        }
        org.apache.cassandra.index.Index selected = ((searchableIndexes.size()) == 1) ? com.google.common.collect.Iterables.getOnlyElement(searchableIndexes) : searchableIndexes.stream().min(( a, b) -> com.google.common.primitives.Longs.compare(a.getEstimatedResultRows(), b.getEstimatedResultRows())).orElseThrow(() -> new java.lang.AssertionError("Could not select most selective index"));
        if (org.apache.cassandra.tracing.Tracing.isTracing()) {
            org.apache.cassandra.tracing.Tracing.trace("Index mean cardinalities are {}. Scanning with {}.", searchableIndexes.stream().map(( i) -> ((i.getIndexMetadata().name) + ':') + (i.getEstimatedResultRows())).collect(java.util.stream.Collectors.joining(",")), selected.getIndexMetadata().name);
        }
        return selected;
    }

    public void validate(org.apache.cassandra.db.partitions.PartitionUpdate update) throws org.apache.cassandra.exceptions.InvalidRequestException {
        indexes.values().stream().filter(( i) -> i.indexes(update.columns())).forEach(( i) -> i.validate(update));
    }

    public void registerIndex(org.apache.cassandra.index.Index index) {
        indexes.put(index.getIndexMetadata().name, index);
        org.apache.cassandra.index.SecondaryIndexManager.logger.trace("Registered index {}", index.getIndexMetadata().name);
    }

    public void unregisterIndex(org.apache.cassandra.index.Index index) {
        org.apache.cassandra.index.Index removed = indexes.remove(index.getIndexMetadata().name);
        org.apache.cassandra.index.SecondaryIndexManager.logger.trace((removed == null ? "Index {} was not registered" : "Removed index {} from registry"), index.getIndexMetadata().name);
    }

    public org.apache.cassandra.index.Index getIndex(org.apache.cassandra.schema.IndexMetadata metadata) {
        return indexes.get(metadata.name);
    }

    public java.util.Collection<org.apache.cassandra.index.Index> listIndexes() {
        return com.google.common.collect.ImmutableSet.copyOf(indexes.values());
    }

    public org.apache.cassandra.index.UpdateTransaction newUpdateTransaction(org.apache.cassandra.db.partitions.PartitionUpdate update, org.apache.cassandra.utils.concurrent.OpOrder.Group opGroup, int nowInSec) {
        if (!(hasIndexes()))
            return UpdateTransaction.NO_OP;

        org.apache.cassandra.index.Index[] indexers = indexes.values().stream().filter(( i) -> i.indexes(update.columns())).map(( i) -> i.indexerFor(update.partitionKey(), nowInSec, opGroup, IndexTransaction.Type.UPDATE)).toArray(org.apache.cassandra.index.Index[]::new);
        return (indexers.length) == 0 ? UpdateTransaction.NO_OP : new org.apache.cassandra.index.SecondaryIndexManager.WriteTimeTransaction(indexers);
    }

    public org.apache.cassandra.index.CompactionTransaction newCompactionTransaction(org.apache.cassandra.index.DecoratedKey key, org.apache.cassandra.index.PartitionColumns partitionColumns, int versions, int nowInSec) {
        org.apache.cassandra.index.Index[] interestedIndexes = indexes.values().stream().filter(( i) -> i.indexes(partitionColumns)).toArray(org.apache.cassandra.index.Index[]::new);
        return (interestedIndexes.length) == 0 ? CompactionTransaction.NO_OP : new org.apache.cassandra.index.SecondaryIndexManager.IndexGCTransaction(key, versions, nowInSec, interestedIndexes);
    }

    public org.apache.cassandra.index.CleanupTransaction newCleanupTransaction(org.apache.cassandra.index.DecoratedKey key, org.apache.cassandra.index.PartitionColumns partitionColumns, int nowInSec) {
        if (!(hasIndexes()))
            return CleanupTransaction.NO_OP;

        org.apache.cassandra.index.Index[] interestedIndexes = indexes.values().stream().filter(( i) -> i.indexes(partitionColumns)).toArray(org.apache.cassandra.index.Index[]::new);
        return (interestedIndexes.length) == 0 ? CleanupTransaction.NO_OP : new org.apache.cassandra.index.SecondaryIndexManager.CleanupGCTransaction(key, nowInSec, interestedIndexes);
    }

    private static final class WriteTimeTransaction implements org.apache.cassandra.index.UpdateTransaction {
        private final Index.Indexer[] indexers;

        private WriteTimeTransaction(org.apache.cassandra.index.Index... indexers) {
            for (org.apache.cassandra.index.Index.Indexer indexer : indexers)
                assert indexer != null;

            this.indexers = indexers;
        }

        public void start() {
            for (org.apache.cassandra.index.Index.Indexer indexer : indexers)
                indexer.begin();

        }

        public void onPartitionDeletion(org.apache.cassandra.index.DeletionTime deletionTime) {
            for (org.apache.cassandra.index.Index.Indexer indexer : indexers)
                indexer.partitionDelete(deletionTime);

        }

        public void onRangeTombstone(org.apache.cassandra.index.RangeTombstone tombstone) {
            for (org.apache.cassandra.index.Index.Indexer indexer : indexers)
                indexer.rangeTombstone(tombstone);

        }

        public void onInserted(org.apache.cassandra.index.Row row) {
            java.util.Arrays.stream(indexers).forEach(( h) -> h.insertRow(row));
        }

        public void onUpdated(org.apache.cassandra.index.Row existing, org.apache.cassandra.index.Row updated) {
            final org.apache.cassandra.index.Row.Builder toRemove = org.apache.cassandra.index.BTreeRow.sortedBuilder();
            toRemove.newRow(existing.clustering());
            toRemove.addPrimaryKeyLivenessInfo(existing.primaryKeyLivenessInfo());
            final org.apache.cassandra.index.Row.Builder toInsert = org.apache.cassandra.index.BTreeRow.sortedBuilder();
            toInsert.newRow(updated.clustering());
            toInsert.addPrimaryKeyLivenessInfo(updated.primaryKeyLivenessInfo());
            org.apache.cassandra.index.RowDiffListener diffListener = new org.apache.cassandra.index.RowDiffListener() {
                public void onPrimaryKeyLivenessInfo(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.index.LivenessInfo merged, org.apache.cassandra.index.LivenessInfo original) {
                }

                public void onDeletion(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.index.Row.Deletion merged, org.apache.cassandra.index.Row.Deletion original) {
                }

                public void onComplexDeletion(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.config.ColumnDefinition column, org.apache.cassandra.index.DeletionTime merged, org.apache.cassandra.index.DeletionTime original) {
                }

                public void onCell(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.index.Cell merged, org.apache.cassandra.index.Cell original) {
                    if ((merged != null) && (!(merged.equals(original))))
                        toInsert.addCell(merged);

                    if ((merged == null) || ((original != null) && (shouldCleanupOldValue(original, merged))))
                        toRemove.addCell(original);

                }
            };
            org.apache.cassandra.index.Rows.diff(diffListener, updated, existing);
            org.apache.cassandra.index.Row oldRow = toRemove.build();
            org.apache.cassandra.index.Row newRow = toInsert.build();
            for (org.apache.cassandra.index.Index.Indexer indexer : indexers)
                indexer.updateRow(oldRow, newRow);

        }

        public void commit() {
            for (org.apache.cassandra.index.Index.Indexer indexer : indexers)
                indexer.finish();

        }

        private boolean shouldCleanupOldValue(org.apache.cassandra.index.Cell oldCell, org.apache.cassandra.index.Cell newCell) {
            return (!(oldCell.value().equals(newCell.value()))) || ((oldCell.timestamp()) != (newCell.timestamp()));
        }
    }

    private static final class IndexGCTransaction implements org.apache.cassandra.index.CompactionTransaction {
        private final org.apache.cassandra.index.DecoratedKey key;

        private final int versions;

        private final int nowInSec;

        private final org.apache.cassandra.index.Index[] indexes;

        private org.apache.cassandra.index.Row[] rows;

        private IndexGCTransaction(org.apache.cassandra.index.DecoratedKey key, int versions, int nowInSec, org.apache.cassandra.index.Index... indexes) {
            for (org.apache.cassandra.index.Index index : indexes)
                assert index != null;

            this.key = key;
            this.versions = versions;
            this.indexes = indexes;
            this.nowInSec = nowInSec;
        }

        public void start() {
            if ((versions) > 0)
                rows = new org.apache.cassandra.index.Row[versions];

        }

        public void onRowMerge(org.apache.cassandra.index.Row merged, org.apache.cassandra.index.Row... versions) {
            final org.apache.cassandra.index.Row[] builders = new org.apache.cassandra.index.Row.Builder[versions.length];
            org.apache.cassandra.index.RowDiffListener diffListener = new org.apache.cassandra.index.RowDiffListener() {
                public void onPrimaryKeyLivenessInfo(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.index.LivenessInfo merged, org.apache.cassandra.index.LivenessInfo original) {
                }

                public void onDeletion(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.index.Row.Deletion merged, org.apache.cassandra.index.Row.Deletion original) {
                }

                public void onComplexDeletion(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.config.ColumnDefinition column, org.apache.cassandra.index.DeletionTime merged, org.apache.cassandra.index.DeletionTime original) {
                }

                public void onCell(int i, org.apache.cassandra.index.Clustering clustering, org.apache.cassandra.index.Cell merged, org.apache.cassandra.index.Cell original) {
                    if ((original != null) && (merged == null)) {
                        if ((builders[i]) == null) {
                            builders[i] = org.apache.cassandra.index.BTreeRow.sortedBuilder();
                            builders[i].newRow(clustering);
                        }
                        builders[i].addCell(original);
                    }
                }
            };
            org.apache.cassandra.index.Rows.diff(diffListener, merged, versions);
            for (int i = 0; i < (builders.length); i++)
                if ((builders[i]) != null)
                    rows[i] = builders[i].build();


        }

        public void commit() {
            if ((rows) == null)
                return;

            try (org.apache.cassandra.utils.concurrent.OpOrder.Group opGroup = Keyspace.writeOrder.start()) {
                for (org.apache.cassandra.index.Index index : indexes) {
                    org.apache.cassandra.index.Index.Indexer indexer = index.indexerFor(key, nowInSec, opGroup, Type.COMPACTION);
                    indexer.begin();
                    for (org.apache.cassandra.index.Row row : rows)
                        if (row != null)
                            indexer.removeRow(row);


                    indexer.finish();
                }
            }
        }
    }

    private static final class CleanupGCTransaction implements org.apache.cassandra.index.CleanupTransaction {
        private final org.apache.cassandra.index.DecoratedKey key;

        private final int nowInSec;

        private final org.apache.cassandra.index.Index[] indexes;

        private org.apache.cassandra.index.Row row;

        private org.apache.cassandra.index.DeletionTime partitionDelete;

        private CleanupGCTransaction(org.apache.cassandra.index.DecoratedKey key, int nowInSec, org.apache.cassandra.index.Index... indexes) {
            for (org.apache.cassandra.index.Index index : indexes)
                assert index != null;

            this.key = key;
            this.indexes = indexes;
            this.nowInSec = nowInSec;
        }

        public void start() {
        }

        public void onPartitionDeletion(org.apache.cassandra.index.DeletionTime deletionTime) {
            partitionDelete = deletionTime;
        }

        public void onRowDelete(org.apache.cassandra.index.Row row) {
            this.row = row;
        }

        public void commit() {
            if (((row) == null) && ((partitionDelete) == null))
                return;

            try (org.apache.cassandra.utils.concurrent.OpOrder.Group opGroup = Keyspace.writeOrder.start()) {
                for (org.apache.cassandra.index.Index index : indexes) {
                    org.apache.cassandra.index.Index.Indexer indexer = index.indexerFor(key, nowInSec, opGroup, Type.CLEANUP);
                    indexer.begin();
                    if ((partitionDelete) != null)
                        indexer.partitionDelete(partitionDelete);

                    if ((row) != null)
                        indexer.removeRow(row);

                    indexer.finish();
                }
            }
        }
    }

    private static void executeBlocking(java.util.concurrent.Callable<?> task) {
        if (null != task)
            org.apache.cassandra.utils.FBUtilities.waitOnFuture(org.apache.cassandra.index.SecondaryIndexManager.blockingExecutor.submit(task));

    }

    private static void executeAllBlocking(java.util.stream.Stream<org.apache.cassandra.index.Index> indexers, java.util.function.Function<org.apache.cassandra.index.Index, java.util.concurrent.Callable<?>> function) {
        java.util.List<java.util.concurrent.Future<?>> waitFor = new java.util.ArrayList<>();
        indexers.forEach(( indexer) -> {
            Callable<?> task = function.apply(indexer);
            if (null != task)
                waitFor.add(blockingExecutor.submit(task));

        });
        org.apache.cassandra.utils.FBUtilities.waitOnFutures(waitFor);
    }
}

