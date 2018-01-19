/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.wali;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wali.SerDeFactory;
import org.wali.SyncListener;
import org.wali.WriteAheadRepository;

/**
 * This implementation of WriteAheadRepository provides the ability to write all updates to the
 * repository sequentially by writing to a single journal file. Serialization of data into bytes
 * happens outside of any lock contention and is done so using recycled byte buffers. As such,
 * we occur minimal garbage collection and the theoretical throughput of this repository is equal
 * to the throughput of the underlying disk itself.
 */
public class SequentialAccessWriteAheadLog<T> implements WriteAheadRepository<T> {
    private static final int PARTITION_INDEX = 0;
    private static final Logger logger = LoggerFactory.getLogger(SequentialAccessWriteAheadLog.class);
    private static final Pattern JOURNAL_FILENAME_PATTERN = Pattern.compile("\\d+\\.journal");
    private static final int MAX_BUFFERS = 64;
    private static final int BUFFER_SIZE = 256 * 1024;

    private final File storageDirectory;
    private final File journalsDirectory;
    private final SerDeFactory<T> serdeFactory;
    private final SyncListener syncListener;

    private final ReadWriteLock journalRWLock = new ReentrantReadWriteLock();
    private final Lock journalReadLock = journalRWLock.readLock();
    private final Lock journalWriteLock = journalRWLock.writeLock();
    private final ObjectPool<ByteArrayDataOutputStream> streamPool = new BlockingQueuePool<>(MAX_BUFFERS,
        () -> new ByteArrayDataOutputStream(BUFFER_SIZE),
        stream -> stream.getByteArrayOutputStream().size() < BUFFER_SIZE);

    private final WriteAheadSnapshot<T> snapshot;
    private final RecordLookup<T> recordLookup;
    private SnapshotRecovery<T> snapshotRecovery;

    private volatile boolean recovered = false;
    private WriteAheadJournal<T> journal;
    private volatile long activeTransactionId = 0L;

    public SequentialAccessWriteAheadLog(final File storageDirectory, final SerDeFactory<T> serdeFactory) throws IOException {
        this(storageDirectory, serdeFactory, SyncListener.NOP_SYNC_LISTENER);
    }

    public SequentialAccessWriteAheadLog(final File storageDirectory, final SerDeFactory<T> serdeFactory, final SyncListener syncListener) throws IOException {
        if (!storageDirectory.exists() && !storageDirectory.mkdirs()) {
            throw new IOException("Directory " + storageDirectory + " does not exist and cannot be created");
        }
        if (!storageDirectory.isDirectory()) {
            throw new IOException("File " + storageDirectory + " is a regular file and not a directory");
        }

        final HashMapSnapshot<T> hashMapSnapshot = new HashMapSnapshot<>(storageDirectory, serdeFactory);
        this.snapshot = hashMapSnapshot;
        this.recordLookup = hashMapSnapshot;

        this.storageDirectory = storageDirectory;
        this.journalsDirectory = new File(storageDirectory, "journals");
        if (!journalsDirectory.exists() && !journalsDirectory.mkdirs()) {
            throw new IOException("Directory " + journalsDirectory + " does not exist and cannot be created");
        }

        recovered = false;

        this.serdeFactory = serdeFactory;
        this.syncListener = (syncListener == null) ? SyncListener.NOP_SYNC_LISTENER : syncListener;
    }

    @Override
    public int update(final Collection<T> records, final boolean forceSync) throws IOException {
        if (!recovered) {
            throw new IllegalStateException("Cannot update repository until record recovery has been performed");
        }

        journalReadLock.lock();
        try {
            final long transactionId = journal.update(records, recordLookup);

            if (forceSync) {
                journal.fsync();
                syncListener.onSync(PARTITION_INDEX);
            }

            snapshot.update(records, transactionId);
        } finally {
            journalReadLock.unlock();
        }

        return PARTITION_INDEX;
    }

    @Override
    public synchronized Collection<T> recoverRecords() throws IOException {
        if (recovered) {
            throw new IllegalStateException("Cannot recover records from repository because record recovery has already commenced");
        }

        final long recoverStart = System.nanoTime();
        recovered = true;
        snapshotRecovery = snapshot.recover();

        final Map<Object, T> recoveredRecords = snapshotRecovery.getRecords();

        final File[] journalFiles = journalsDirectory.listFiles(this::isJournalFile);
        if (journalFiles == null) {
            throw new IOException("Cannot access the list of files in directory " + journalsDirectory + "; please ensure that appropriate file permissions are set.");
        }

        final List<File> orderedJournalFiles = Arrays.asList(journalFiles);
        Collections.sort(orderedJournalFiles, new Comparator<File>() {
            @Override
            public int compare(final File o1, final File o2) {
                final long transactionId1 = getMinTransactionId(o1);
                final long transactionId2 = getMinTransactionId(o2);

                return Long.compare(transactionId1, transactionId2);
            }
        });

        final long snapshotTransactionId = snapshotRecovery.getMaxTransactionId();

        int totalUpdates = 0;
        int journalFilesRecovered = 0;
        int journalFilesSkipped = 0;
        long maxTransactionId = snapshotTransactionId;

        for (final File journalFile : orderedJournalFiles) {
            final long journalMinTransactionId = getMinTransactionId(journalFile);
            if (journalMinTransactionId < snapshotTransactionId) {
                logger.debug("Will not recover records from journal file {} because the minimum Transaction ID for that journal is {} and the Transaction ID recovered from Snapshot was {}",
                    journalFile, journalMinTransactionId, snapshotTransactionId);

                journalFilesSkipped++;
                continue;
            }

            logger.debug("Min Transaction ID for journal {} is {}, so will recover records from journal", journalFile, journalMinTransactionId);
            journalFilesRecovered++;

            try (final WriteAheadJournal<T> journal = new LengthDelimitedJournal<>(journalFile, serdeFactory, streamPool, 0L)) {
                // TODO: Think about how to recover from EOFException, others.
                final JournalRecovery journalRecovery = journal.recoverRecords(recoveredRecords);
                final int updates = journalRecovery.getUpdateCount();

                logger.debug("Recovered {} updates from journal {}", updates, journalFile);
                totalUpdates += updates;
                maxTransactionId = Math.max(maxTransactionId, journalRecovery.getMaxTransactionId());
            }
        }

        logger.debug("Recovered {} updates from {} journal files and skipped {} journal files because their data was already encapsulated in the snapshot",
            totalUpdates, journalFilesRecovered, journalFilesSkipped);
        this.activeTransactionId = maxTransactionId + 1;

        final long recoverNanos = System.nanoTime() - recoverStart;
        final long recoveryMillis = TimeUnit.MILLISECONDS.convert(recoverNanos, TimeUnit.NANOSECONDS);
        logger.info("Successfully recovered {} records in {} milliseconds. Now checkpointing to ensure that Write-Ahead Log is in a consistent state", recoveredRecords.size(), recoveryMillis);

        checkpoint();

        return recoveredRecords.values();
    }

    private long getMinTransactionId(final File journalFile) {
        final String filename = journalFile.getName();
        final String numeral = filename.substring(0, filename.indexOf("."));
        return Long.parseLong(numeral);
    }

    private boolean isJournalFile(final File file) {
        if (!file.isFile()) {
            return false;
        }

        final String filename = file.getName();
        return JOURNAL_FILENAME_PATTERN.matcher(filename).matches();
    }

    @Override
    public synchronized Set<String> getRecoveredSwapLocations() throws IOException {
        if (!recovered) {
            throw new IllegalStateException("Cannot retrieve the Recovered Swap Locations until record recovery has been performed");
        }

        return snapshotRecovery.getRecoveredSwapLocations();
    }

    @Override
    public int checkpoint() throws IOException {
        final SnapshotCapture<T> snapshotCapture;

        final long startNanos = System.nanoTime();
        final File[] existingJournals;
        journalWriteLock.lock();
        try {
            if (journal == null) {
                activeTransactionId = 0;
            } else {
                journal.close();

                final JournalSummary journalSummary = journal.getSummary();
                activeTransactionId = Math.max(activeTransactionId, journalSummary.getLastTransactionId() + 1);
            }

            syncListener.onGlobalSync();

            final File[] existingFiles = journalsDirectory.listFiles(this::isJournalFile);
            existingJournals = (existingFiles == null) ? new File[0] : existingFiles;

            snapshotCapture = snapshot.prepareSnapshot(activeTransactionId);
            journal = createJournal(activeTransactionId);
            journal.writeHeader();
        } finally {
            journalWriteLock.unlock();
        }

        final long stopTheWorldMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
        snapshot.writeSnapshot(snapshotCapture);

        for (final File existingJournal : existingJournals) {
            if (!existingJournal.delete() && existingJournal.exists()) {
                logger.warn("Unable to delete expired journal file " + existingJournal + "; this file should be deleted manually.");
            }
        }

        final long totalNanos = System.nanoTime() - startNanos;
        final long millis = TimeUnit.NANOSECONDS.toMillis(totalNanos);
        logger.info("Checkpointed Write-Ahead Log with {} Records and {} Swap Files in {} milliseconds (Stop-the-world time = {} milliseconds), max Transaction ID {}",
            new Object[] {snapshotCapture.getRecords().size(), snapshotCapture.getSwapLocations().size(), millis, stopTheWorldMillis, snapshotCapture.getMaxTransactionId()});

        return snapshotCapture.getRecords().size();
    }

    private WriteAheadJournal<T> createJournal(final long minTransactionId) throws FileNotFoundException {
        final File journalFile = new File(journalsDirectory, String.valueOf(minTransactionId) + ".journal");
        return new LengthDelimitedJournal<>(journalFile, serdeFactory, streamPool, minTransactionId);
    }

    @Override
    public void shutdown() throws IOException {

    }
}
