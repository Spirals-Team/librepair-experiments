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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.wali.DummyRecord;
import org.wali.DummyRecordSerde;
import org.wali.SerDeFactory;
import org.wali.SingletonSerDeFactory;
import org.wali.UpdateType;
import org.wali.WriteAheadRepository;

public class TestSequentialAccessWriteAheadLog {

    @Test
    public void testUpdatePerformance() throws IOException, InterruptedException {
        final Path path = Paths.get("target/sequential-access-repo");
        deleteRecursively(path.toFile());
        assertTrue(path.toFile().mkdirs());

        final DummyRecordSerde serde = new DummyRecordSerde();
        final SerDeFactory<DummyRecord> serdeFactory = new SingletonSerDeFactory<>(serde);

        final WriteAheadRepository<DummyRecord> repo = new SequentialAccessWriteAheadLog<>(path.toFile(), serdeFactory);
        final Collection<DummyRecord> initialRecs = repo.recoverRecords();
        assertTrue(initialRecs.isEmpty());

        final long updateCountPerThread = 1_000_000;
        final int numThreads = 4;

        final Thread[] threads = new Thread[numThreads];
        final int batchSize = 1;

        long previousBytes = 0L;

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < numThreads; i++) {
                final Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<DummyRecord> batch = new ArrayList<>();
                        for (int i = 0; i < updateCountPerThread / batchSize; i++) {
                            batch.clear();
                            for (int j = 0; j < batchSize; j++) {
                                final DummyRecord record = new DummyRecord(String.valueOf(i), UpdateType.CREATE);
                                batch.add(record);
                            }

                            try {
                                repo.update(batch, false);
                            } catch (Throwable t) {
                                t.printStackTrace();
                                Assert.fail(t.toString());
                            }
                        }
                    }
                });

                threads[i] = t;
            }

            final long start = System.nanoTime();
            for (final Thread t : threads) {
                t.start();
            }
            for (final Thread t : threads) {
                t.join();
            }

            long bytes = 0L;
            for (final File journalFile : path.resolve("journals").toFile().listFiles()) {
                bytes += journalFile.length();
            }

            bytes -= previousBytes;
            previousBytes = bytes;

            final long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            final long eventsPerSecond = (updateCountPerThread * numThreads * 1000) / millis;
            final String eps = NumberFormat.getInstance().format(eventsPerSecond);
            final long bytesPerSecond = bytes * 1000 / millis;
            final String bps = NumberFormat.getInstance().format(bytesPerSecond);

            if (j == 0) {
                System.out.println(millis + " ms to insert " + updateCountPerThread * numThreads + " updates using " + numThreads
                    + " threads, *as a warmup!*  " + eps + " events per second, " + bps + " bytes per second");
            } else {
                System.out.println(millis + " ms to insert " + updateCountPerThread * numThreads + " updates using " + numThreads
                    + " threads, " + eps + " events per second, " + bps + " bytes per second");
            }
        }
    }

    private void deleteRecursively(final File file) {
        final File[] children = file.listFiles();
        if (children != null) {
            for (final File child : children) {
                deleteRecursively(child);
            }
        }

        file.delete();
    }

}
