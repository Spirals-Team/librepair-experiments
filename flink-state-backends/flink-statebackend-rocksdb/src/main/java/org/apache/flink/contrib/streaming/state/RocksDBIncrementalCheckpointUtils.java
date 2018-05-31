/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.contrib.streaming.state;

import org.apache.flink.runtime.state.KeyGroupRange;
import org.apache.flink.runtime.state.KeyedStateHandle;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import java.util.List;

/**
 * Utils for RocksDB Incremental Checkpoint.
 */
public class RocksDBIncrementalCheckpointUtils {

	/**
	 * The method to clip the db instance according to the target key group range using
	 * the {@link RocksDB#delete(ColumnFamilyHandle, byte[])}.
	 *
	 * @param db the RocksDB instance to be clipped.
	 * @param columnFamilyHandles the column families in the db instance.
	 * @param targetKeyGroupRange the target key group range.
	 * @param currentKeyGroupRange the key group range of the db instance.
	 * @param keyGroupPrefixBytes Number of bytes required to prefix the key groups.
	 */
	public static void clipDBWithKeyGroupRange(
		@Nonnull RocksDB db,
		@Nonnull List<ColumnFamilyHandle> columnFamilyHandles,
		@Nonnull KeyGroupRange targetKeyGroupRange,
		@Nonnull KeyGroupRange currentKeyGroupRange,
		@Nonnegative int keyGroupPrefixBytes) throws RocksDBException {

		final byte[] beginKeyGroupBytes = new byte[keyGroupPrefixBytes];
		final byte[] endKeyGroupBytes = new byte[keyGroupPrefixBytes];

		final byte[] largestEndKeyBytes = new byte[keyGroupPrefixBytes];
		for (int i = 0; i < keyGroupPrefixBytes; ++i) {
			largestEndKeyBytes[i] = (byte) (0xFF);
		}

		for (ColumnFamilyHandle columnFamilyHandle : columnFamilyHandles) {
			if (currentKeyGroupRange.getStartKeyGroup() < targetKeyGroupRange.getStartKeyGroup()) {
				RocksDBKeySerializationUtils.serializeKeyGroup(
					currentKeyGroupRange.getStartKeyGroup(), beginKeyGroupBytes);
				RocksDBKeySerializationUtils.serializeKeyGroup(
					targetKeyGroupRange.getStartKeyGroup(), endKeyGroupBytes);
				db.deleteRange(columnFamilyHandle, beginKeyGroupBytes, endKeyGroupBytes);
			}

			if (currentKeyGroupRange.getEndKeyGroup() > targetKeyGroupRange.getEndKeyGroup()) {
				RocksDBKeySerializationUtils.serializeKeyGroup(
					targetKeyGroupRange.getEndKeyGroup() + 1, beginKeyGroupBytes);

				db.deleteRange(columnFamilyHandle, beginKeyGroupBytes, largestEndKeyBytes);
			}
		}
	}

	/**
	 * The method to evaluate state handle's "score" regarding to the target range when
	 * choosing the best state handle to init the initial db for recovery.
	 */
	public static int evaluateHandleScoreInGroupRange(KeyGroupRange targetRange, KeyedStateHandle stateHandle) {
		// TODO a better way to evaluate the score is to also take the state size into count.
		return targetRange.getIntersection(stateHandle.getKeyGroupRange()).getNumberOfKeyGroups();
	}
}
