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

package org.apache.flink.contrib.streaming.state;

import org.apache.flink.util.Preconditions;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

import javax.annotation.Nonnull;

/**
 * A wrapper class to wrap WriteBatch.
 */
public class RocksDBWriteBatchWrapper implements AutoCloseable {

	private final static int MIN_CAPACITY = 100;
	private final static int MAX_CAPACITY = 10000;

	private final RocksDB db;

	private final WriteBatch batch;

	private final WriteOptions options;

	private final int capacity;

	private int currentSize;

	public RocksDBWriteBatchWrapper(@Nonnull RocksDB rocksDB,
									@Nonnull WriteOptions options,
									int capacity) {

		Preconditions.checkArgument(capacity >= MIN_CAPACITY && capacity <= MAX_CAPACITY,
			"capacity should at least greater than 100");

		this.db = rocksDB;
		this.options = options;
		this.capacity = capacity;
		this.batch = new WriteBatch(this.capacity);
		this.currentSize = 0;
	}

	public void put(ColumnFamilyHandle handle, byte[] key, byte[] value) throws RocksDBException {

		this.batch.put(handle, key, value);

		if (++currentSize == capacity) {
			flush();
		}
	}

	public void flush() throws RocksDBException {
		this.db.write(options, batch);
		batch.clear();
		currentSize = 0;
	}

	@Override
	public void close() throws RocksDBException {
		if (batch != null) {
			if (currentSize != 0) {
				flush();
			}
			batch.close();
		}
	}
}
