/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.contrib.streaming.state.benchmark;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.contrib.streaming.state.RocksDBWriteBatchWrapper;
import org.apache.flink.util.TestLogger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDB;
import org.rocksdb.WriteOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Test that validates that the performance of RocksDB is WriteBatch as expected.
 * More over, it also tested the write performance when we turn off/on WAL.
 */
public class RocksDBWriteBatchPerformanceTest extends TestLogger {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private static final String KEY_PREFIX = "key key key key key key";

	private static final String VALUE_PREFIX = "value value value value value";

	@Test
	public void benchMark() throws Exception {

		// put with disableWAL=true VS put with disableWAL=false
		log.info("--> put with disableWAL=true VS put with disableWAL=false <--");

		benchMarkHelper(1_000, true, WRITETYPE.PUT);
		benchMarkHelper(1_000, false, WRITETYPE.PUT);

		benchMarkHelper(10_000, true, WRITETYPE.PUT);
		benchMarkHelper(10_000, false, WRITETYPE.PUT);

		benchMarkHelper(100_000, true, WRITETYPE.PUT);
		benchMarkHelper(100_000, false, WRITETYPE.PUT);

		benchMarkHelper(1_000_000, true, WRITETYPE.PUT);
		benchMarkHelper(1_000_000, false, WRITETYPE.PUT);

		// put with disableWAL=true VS write batch with disableWAL=false
		log.info("--> put with disableWAL=true VS write batch with disableWAL=false <--");

		benchMarkHelper(1_000, true, WRITETYPE.PUT);
		benchMarkHelper(1_000, false, WRITETYPE.WRITE_BATCH);

		benchMarkHelper(10_000, true, WRITETYPE.PUT);
		benchMarkHelper(10_000, false, WRITETYPE.WRITE_BATCH);

		benchMarkHelper(100_000, true, WRITETYPE.PUT);
		benchMarkHelper(100_000, false, WRITETYPE.WRITE_BATCH);

		benchMarkHelper(1_000_000, true, WRITETYPE.PUT);
		benchMarkHelper(1_000_000, false, WRITETYPE.WRITE_BATCH);

		// write batch with disableWAL=true VS write batch disableWAL = true
		log.info("--> write batch with disableWAL=true VS write batch disableWAL = true <--");

		benchMarkHelper(1_000, true, WRITETYPE.WRITE_BATCH);
		benchMarkHelper(1_000, false, WRITETYPE.WRITE_BATCH);

		benchMarkHelper(10_000, true, WRITETYPE.WRITE_BATCH);
		benchMarkHelper(10_000, false, WRITETYPE.WRITE_BATCH);

		benchMarkHelper(100_000, true, WRITETYPE.WRITE_BATCH);
		benchMarkHelper(100_000, false, WRITETYPE.WRITE_BATCH);

		benchMarkHelper(1_000_000, true, WRITETYPE.WRITE_BATCH);
		benchMarkHelper(1_000_000, false, WRITETYPE.WRITE_BATCH);
	}

	private enum WRITETYPE {PUT, WRITE_BATCH}

	private void benchMarkHelper(int number, boolean disableWAL, WRITETYPE type) throws Exception {

		List<Tuple2<byte[], byte[]>> data = new ArrayList<>(number);
		for (int i = 0; i < number; ++i) {
			data.add(new Tuple2<>((KEY_PREFIX + i).getBytes(), (VALUE_PREFIX + i).getBytes()));
		}

		switch (type) {
			case PUT:
				try (RocksDB db = RocksDB.open(folder.newFolder().getAbsolutePath());
					WriteOptions options = new WriteOptions().setDisableWAL(disableWAL);
					ColumnFamilyHandle handle = db.createColumnFamily(new ColumnFamilyDescriptor("test".getBytes()))) {
					long t1 = System.currentTimeMillis();
					for (Tuple2<byte[], byte[]> item : data) {
						db.put(handle, options, item.f0, item.f1);
					}
					long t2 = System.currentTimeMillis();
					log.info("number:" + number + " put cost:" + (t2 - t1) + " ms");
				}
				break;
			case WRITE_BATCH:
				try (RocksDB db = RocksDB.open(folder.newFolder().getAbsolutePath());
					WriteOptions options = new WriteOptions().setDisableWAL(disableWAL);
					ColumnFamilyHandle handle = db.createColumnFamily(new ColumnFamilyDescriptor("test".getBytes()));
					RocksDBWriteBatchWrapper writeBatchWrapper = new RocksDBWriteBatchWrapper(db, options, 200)) {
					long t1 = System.currentTimeMillis();
					for (Tuple2<byte[], byte[]> item : data) {
						writeBatchWrapper.put(handle, item.f0, item.f1);
					}
					writeBatchWrapper.flush();
					long t2 = System.currentTimeMillis();
					log.info("number:" + number + " write batch cost:" + (t2 - t1) + " ms");
				}
				break;
			default:
				throw new RuntimeException("Unknown benchmark type:" + type);
		}
	}
}
