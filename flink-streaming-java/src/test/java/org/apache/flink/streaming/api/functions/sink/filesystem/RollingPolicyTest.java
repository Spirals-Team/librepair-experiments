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

package org.apache.flink.streaming.api.functions.sink.filesystem;

import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.OnCheckpointRollingPolicy;
import org.apache.flink.util.Preconditions;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * Tests for different {@link RollingPolicy rolling policies}.
 */
public class RollingPolicyTest {

	@ClassRule
	public static final TemporaryFolder TEMP_FOLDER = new TemporaryFolder();

	@Test
	public void testDefaultRollingPolicy() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final RollingPolicy<String, String> originalRollingPolicy =
				DefaultRollingPolicy
						.create()
						.withMaxPartSize(10L)
						.withInactivityInterval(4L)
						.withRolloverInterval(11L)
						.build();

		final RollCountingPolicyWrapper<String, String> rollingPolicy =
				new RollCountingPolicyWrapper<>(originalRollingPolicy);

		final Buckets<String, String> buckets = new Buckets<>(
				path,
				new StringIdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				rollingPolicy,
				0
		);

		rollingPolicy.verifyCallCounters(0L, 0L, 0L, 0L, 0L, 0L);

		// these two will fill up the first in-progress file and at the third it will roll ...
		buckets.onElement("test1", new TestUtils.MockSinkContext(1L, 1L, 1L));
		buckets.onElement("test1", new TestUtils.MockSinkContext(2L, 1L, 2L));
		rollingPolicy.verifyCallCounters(0L, 0L, 1L, 0L, 0L, 0L);

		buckets.onElement("test1", new TestUtils.MockSinkContext(3L, 1L, 3L));
		rollingPolicy.verifyCallCounters(0L, 0L, 2L, 1L, 0L, 0L);

		// still no time to roll
		buckets.onProcessingTime(5L);
		rollingPolicy.verifyCallCounters(0L, 0L, 2L, 1L, 1L, 0L);

		// roll due to inactivity
		buckets.onProcessingTime(7L);
		rollingPolicy.verifyCallCounters(0L, 0L, 2L, 1L, 2L, 1L);

		buckets.onElement("test1", new TestUtils.MockSinkContext(3L, 1L, 3L));

		// roll due to rollover interval
		buckets.onProcessingTime(20L);
		rollingPolicy.verifyCallCounters(0L, 0L, 2L, 1L, 3L, 2L);

		// we take a checkpoint but we should not roll.
		buckets.snapshotState(1L, new TestUtils.MockListState<>(), new TestUtils.MockListState<>());
		rollingPolicy.verifyCallCounters(0L, 0L, 2L, 1L, 3L, 2L);
	}

	@Test
	public void testRollOnCheckpointPolicy() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final RollCountingPolicyWrapper<String, String> rollingPolicy =
				new RollCountingPolicyWrapper<>(OnCheckpointRollingPolicy.build());

		final Buckets<String, String> buckets = new Buckets<>(
				path,
				new StringIdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				rollingPolicy,
				0
		);

		rollingPolicy.verifyCallCounters(0L, 0L, 0L, 0L, 0L, 0L);

		// the following 2 elements will close a part file because of size...
		buckets.onElement("test1", new TestUtils.MockSinkContext(1L, 1L, 2L));
		buckets.onElement("test1", new TestUtils.MockSinkContext(2L, 1L, 2L));
		buckets.onElement("test1", new TestUtils.MockSinkContext(3L, 1L, 3L));

		// ... we have a checkpoint so we roll ...
		buckets.snapshotState(1L, new TestUtils.MockListState<>(), new TestUtils.MockListState<>());
		rollingPolicy.verifyCallCounters(1L, 1L, 2L, 0L, 0L, 0L);

		// ... create a new in-progress file (before we had closed the last one so it was null)...
		buckets.onElement("test1", new TestUtils.MockSinkContext(5L, 1L, 5L));

		// ... we have a checkpoint so we roll ...
		buckets.snapshotState(2L, new TestUtils.MockListState<>(), new TestUtils.MockListState<>());
		rollingPolicy.verifyCallCounters(2L, 2L, 2L, 0L, 0L, 0L);

		buckets.close();
	}

	@Test
	public void testCustomRollingPolicy() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final RollCountingPolicyWrapper<String, String> rollingPolicy = new RollCountingPolicyWrapper<>(
				new RollingPolicy<String, String>() {

					private static final long serialVersionUID = 1L;

					@Override
					public boolean shouldRollOnCheckpoint(PartFileInfo<String> partFileState) {
						return true;
					}

					@Override
					public boolean shouldRollOnEvent(PartFileInfo<String> partFileState, String element) throws IOException {
						// this means that 2 elements will close the part file.
						return partFileState.getSize() > 9L;
					}

					@Override
					public boolean shouldRollOnProcessingTime(PartFileInfo<String> partFileState, long currentTime) {
						return currentTime - partFileState.getLastUpdateTime() >= 10L;
					}
				});

		final Buckets<String, String> buckets = new Buckets<>(
				path,
				new StringIdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				rollingPolicy,
				0
		);

		rollingPolicy.verifyCallCounters(0L, 0L, 0L, 0L, 0L, 0L);

		// the following 2 elements will close a part file because of size...
		buckets.onElement("test1", new TestUtils.MockSinkContext(1L, 1L, 2L));
		buckets.onElement("test1", new TestUtils.MockSinkContext(2L, 1L, 2L));

		// only one call because we have no open part file in the other incoming elements, so currentPartFile == null so we roll without checking the policy.
		rollingPolicy.verifyCallCounters(0L, 0L, 1L, 0L, 0L, 0L);

		// ... and this one will trigger the roll and open a new part file...
		buckets.onElement("test1", new TestUtils.MockSinkContext(2L, 1L, 2L));
		rollingPolicy.verifyCallCounters(0L, 0L, 2L, 1L, 0L, 0L);

		// ... we have a checkpoint so we roll ...
		buckets.snapshotState(1L, new TestUtils.MockListState<>(), new TestUtils.MockListState<>());
		rollingPolicy.verifyCallCounters(1L, 1L, 2L, 1L, 0L, 0L);

		// ... create a new in-progress file (before we had closed the last one so it was null)...
		buckets.onElement("test1", new TestUtils.MockSinkContext(2L, 1L, 5L));

		// ... last modification time is 5L, so now we DON'T roll but we check ...
		buckets.onProcessingTime(12L);
		rollingPolicy.verifyCallCounters(1L, 1L, 2L, 1L, 1L, 0L);

		// ... last modification time is 5L, so now we roll
		buckets.onProcessingTime(16L);
		rollingPolicy.verifyCallCounters(1L, 1L, 2L, 1L, 2L, 1L);

		buckets.close();
	}

	/**
	 * A simple {@link BucketAssigner} that accepts {@code String}'s
	 * and returns the element itself as the bucket id.
	 */
	private static class StringIdentityBucketAssigner implements BucketAssigner<String, String> {

		private static final long serialVersionUID = 1L;

		@Override
		public String getBucketId(String element, BucketAssigner.Context context) {
			return element;
		}

		@Override
		public SimpleVersionedSerializer<String> getSerializer() {
			return SimpleVersionedStringSerializer.INSTANCE;
		}
	}

	/**
	 * A wrapper of a {@link RollingPolicy} which counts how many times each method of the policy was called
	 * and in how many of them it decided to roll.
	 */
	private static class RollCountingPolicyWrapper<IN, BucketID> implements RollingPolicy<IN, BucketID> {

		private static final long serialVersionUID = 1L;

		private final RollingPolicy<IN, BucketID> originalPolicy;

		private long onCheckpointCallCounter = 0;
		private long onCheckpointRollCounter = 0;

		private long onEventCallCounter = 0;
		private long onEventRollCounter = 0;

		private long onProcessingTimeCallCounter = 0;
		private long onProcessingTimeRollCounter = 0;

		RollCountingPolicyWrapper(final RollingPolicy<IN, BucketID> policy) {
			this.originalPolicy = Preconditions.checkNotNull(policy);
		}

		@Override
		public boolean shouldRollOnCheckpoint(PartFileInfo<BucketID> partFileState) throws IOException {
			final boolean shouldRoll = originalPolicy.shouldRollOnCheckpoint(partFileState);
			this.onCheckpointCallCounter++;
			if (shouldRoll) {
				this.onCheckpointRollCounter++;
			}
			return shouldRoll;
		}

		@Override
		public boolean shouldRollOnEvent(PartFileInfo<BucketID> partFileState, IN element) throws IOException {
			final boolean shouldRoll = originalPolicy.shouldRollOnEvent(partFileState, element);
			this.onEventCallCounter++;
			if (shouldRoll) {
				this.onEventRollCounter++;
			}
			return shouldRoll;
		}

		@Override
		public boolean shouldRollOnProcessingTime(PartFileInfo<BucketID> partFileState, long currentTime) throws IOException {
			final boolean shouldRoll = originalPolicy.shouldRollOnProcessingTime(partFileState, currentTime);
			this.onProcessingTimeCallCounter++;
			if (shouldRoll) {
				this.onProcessingTimeRollCounter++;
			}
			return shouldRoll;
		}

		void verifyCallCounters(
				final long onCheckpointCalls,
				final long onCheckpointRolls,
				final long onEventCalls,
				final long onEventRolls,
				final long onProcessingTimeCalls,
				final long onProcessingTimeRolls
		) {
			Assert.assertEquals(onCheckpointCalls, onCheckpointCallCounter);
			Assert.assertEquals(onCheckpointRolls, onCheckpointRollCounter);
			Assert.assertEquals(onEventCalls, onEventCallCounter);
			Assert.assertEquals(onEventRolls, onEventRollCounter);
			Assert.assertEquals(onProcessingTimeCalls, onProcessingTimeCallCounter);
			Assert.assertEquals(onProcessingTimeRolls, onProcessingTimeRollCounter);
		}
	}
}
