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

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wali.MinimalLockingWriteAheadLog;
import org.wali.SerDe;
import org.wali.SerDeFactory;
import org.wali.UpdateType;

public class HashMapSnapshot<T> implements WriteAheadSnapshot<T>, RecordLookup<T> {
    private static final Logger logger = LoggerFactory.getLogger(HashMapSnapshot.class);
    private static final int ENCODING_VERSION = 1;

    private final ConcurrentMap<Object, T> recordMap = new ConcurrentHashMap<>();
    private final SerDeFactory<T> serdeFactory;
    private final Set<String> swapLocations = Collections.synchronizedSet(new HashSet<>());
    private final File storageDirectory;

    public HashMapSnapshot(final File storageDirectory, final SerDeFactory<T> serdeFactory) {
        this.serdeFactory = serdeFactory;
        this.storageDirectory = storageDirectory;
    }


    @Override
    public SnapshotRecovery<T> recover() throws IOException {
        // TODO: Implement
        final long maxTransactionId = -1L;

        return new StandardSnapshotRecovery<>(Collections.unmodifiableMap(recordMap), Collections.unmodifiableSet(swapLocations), maxTransactionId);
    }

    @Override
    public void update(final Collection<T> records, final long transactionId) {
        for (final T record : records) {
            final Object recordId = serdeFactory.getRecordIdentifier(record);
            final UpdateType updateType = serdeFactory.getUpdateType(record);

            if (updateType == UpdateType.DELETE) {
                recordMap.remove(recordId);
            } else if (updateType == UpdateType.SWAP_OUT) {
                final String newLocation = serdeFactory.getLocation(record);
                if (newLocation == null) {
                    logger.error("Received Record (ID=" + recordId + ") with UpdateType of SWAP_OUT but "
                        + "no indicator of where the Record is to be Swapped Out to; these records may be "
                        + "lost when the repository is restored!");
                } else {
                    recordMap.remove(recordId);
                    this.swapLocations.add(newLocation);
                }
            } else if (updateType == UpdateType.SWAP_IN) {
                final String newLocation = serdeFactory.getLocation(record);
                if (newLocation == null) {
                    logger.error("Received Record (ID=" + recordId + ") with UpdateType of SWAP_IN but no "
                        + "indicator of where the Record is to be Swapped In from; these records may be duplicated "
                        + "when the repository is restored!");
                } else {
                    swapLocations.remove(newLocation);
                }
                recordMap.put(recordId, record);
            } else {
                recordMap.put(recordId, record);
            }
        }
    }

    @Override
    public T lookup(final Object recordId) {
        return recordMap.get(recordId);
    }


    @Override
    public SnapshotCapture<T> prepareSnapshot(final long maxTransactionId) {
        return new Snapshot(new HashMap<>(recordMap), new HashSet<>(swapLocations), maxTransactionId);
    }

    private int getVersion() {
        return ENCODING_VERSION;
    }

    private File getPartialFile() {
        return new File(storageDirectory, "snapshot.partial");
    }

    private File getSnapshotFile() {
        return new File(storageDirectory, "snapshot");
    }

    @Override
    public void writeSnapshot(final SnapshotCapture<T> snapshot) throws IOException {
        final SerDe<T> serde = serdeFactory.createSerDe(null);

        final File snapshotFile = getSnapshotFile();
        final File partialFile = getPartialFile();
        try (final OutputStream fileOut = new FileOutputStream(getPartialFile());
            final OutputStream bufferedOut = new BufferedOutputStream(fileOut);
            final DataOutputStream dataOut = new DataOutputStream(bufferedOut)) {

            dataOut.writeUTF(MinimalLockingWriteAheadLog.class.getName());
            dataOut.writeInt(getVersion());
            dataOut.writeUTF(serde.getClass().getName());
            dataOut.writeInt(serde.getVersion());
            dataOut.writeLong(snapshot.getMaxTransactionId());
            dataOut.writeInt(snapshot.getRecords().size());
            serde.writeHeader(dataOut);

            for (final T record : snapshot.getRecords().values()) {
                logger.trace("Checkpointing {}", record);
                serde.serializeRecord(record, dataOut);
            }

            dataOut.writeInt(snapshot.getSwapLocations().size());
            for (final String swapLocation : snapshot.getSwapLocations()) {
                dataOut.writeUTF(swapLocation);
            }
        }

        // If the snapshot file exists, delete it
        if (snapshotFile.exists()) {
            if (!snapshotFile.delete()) {
                logger.warn("Unable to delete existing Snapshot file " + snapshotFile);
            }
        }

        final boolean rename = partialFile.renameTo(snapshotFile);
        if (!rename) {
            throw new IOException("Failed to rename partial snapshot file " + partialFile + " to " + snapshotFile);
        }
    }


    public class Snapshot implements SnapshotCapture<T> {
        private final Map<Object, T> records;
        private final long maxTransactionId;
        private final Set<String> swapLocations;

        public Snapshot(final Map<Object, T> records, final Set<String> swapLocations, final long maxTransactionId) {
            this.records = records;
            this.swapLocations = swapLocations;
            this.maxTransactionId = maxTransactionId;
        }

        @Override
        public final Map<Object, T> getRecords() {
            return records;
        }

        @Override
        public long getMaxTransactionId() {
            return maxTransactionId;
        }

        @Override
        public Set<String> getSwapLocations() {
            return swapLocations;
        }
    }
}
