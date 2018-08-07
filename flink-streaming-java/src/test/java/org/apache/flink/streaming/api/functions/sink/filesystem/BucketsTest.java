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
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.TestUtils.MockListState;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.OnCheckpointRollingPolicy;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.Map;

/**
 * Tests for {@link Buckets}.
 */
public class BucketsTest {

	@ClassRule
	public static final TemporaryFolder TEMP_FOLDER = new TemporaryFolder();

	@Test
	public void testSnapshotAndRestore() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final RollingPolicy<String, String> onCheckpointRP = OnCheckpointRollingPolicy.build();

		Buckets<String, String> buckets = new Buckets<>(
				path,
				new IdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				onCheckpointRP,
				0
		);

		final ListState<byte[]> bucketStateContainer = new MockListState<>();
		final ListState<Long> partCounterContainer = new MockListState<>();

		buckets.onElement("test1", new TestUtils.MockSinkContext(null, 1L, 2L));
		buckets.snapshotState(0L, bucketStateContainer, partCounterContainer);

		buckets.onElement("test2", new TestUtils.MockSinkContext(null, 1L, 2L));
		buckets.snapshotState(1L, bucketStateContainer, partCounterContainer);

		Buckets<String, String> restoredBuckets = new Buckets<>(
				path,
				new IdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				onCheckpointRP,
				0
		);
		restoredBuckets.initializeState(bucketStateContainer, partCounterContainer);

		final Map<String, Bucket<String, String>> activeBuckets = restoredBuckets.getActiveBuckets();
		for (Map.Entry<String, Bucket<String, String>> entry : activeBuckets.entrySet()) {
			System.out.println(entry.getKey());
			// TODO: 8/4/18 verify
		}
	}
// TODO: 8/4/18 test scale in and part counter here
	/**
	 * A simple {@link BucketAssigner} that accepts {@code String}'s
	 * and returns the element itself as the bucket id.
	 */
	private static class IdentityBucketAssigner implements BucketAssigner<String, String> {
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

	@Test
	public void testOnProcessingTime() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final OnProcessingTimePolicy<String, String> rollOnProcessingTimeCountingPolicy =
				new OnProcessingTimePolicy<>(2L);

		final Buckets<String, String> buckets = new Buckets<>(
				path,
				new IdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				rollOnProcessingTimeCountingPolicy,
				0
		);

		// it takes the current processing time of the context for the creation time, and for the last modification time.
		buckets.onElement("test", new TestUtils.MockSinkContext(1L, 2L ,3L));

		// now it should roll
		buckets.onProcessingTime(7L);
		Assert.assertEquals(1L, rollOnProcessingTimeCountingPolicy.getOnProcessingTimeRollCounter());

		final Map<String, Bucket<String, String>> activeBuckets = buckets.getActiveBuckets();
		Assert.assertEquals(1L, activeBuckets.size());

		final Map.Entry<String, Bucket<String, String>> bucketState =
				activeBuckets.entrySet().iterator().next();

		final String bucketId = bucketState.getKey();
		Assert.assertEquals("test", bucketId);

		final Bucket<String, String> bucket = bucketState.getValue();

		Assert.assertEquals(new Path(path, "test"), bucket.getBucketPath());
		Assert.assertEquals("test", bucket.getBucketId());

		Assert.assertNull(bucket.getInProgressPart());
		Assert.assertEquals(1L, bucket.getPendingPartsForCurrentCheckpoint().size());
		Assert.assertTrue(bucket.getPendingPartsPerCheckpoint().isEmpty());
	}

	@Test
	public void testBucketIsRemovedWhenNotActive() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final OnProcessingTimePolicy<String, String> rollOnProcessingTimeCountingPolicy =
				new OnProcessingTimePolicy<>(2L);

		final Buckets<String, String> buckets = new Buckets<>(
				path,
				new IdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				rollOnProcessingTimeCountingPolicy,
				0
		);

		// it takes the current processing time of the context for the creation time, and for the last modification time.
		buckets.onElement("test", new TestUtils.MockSinkContext(1L, 2L ,3L));

		// now it should roll
		buckets.onProcessingTime(7L);
		Assert.assertEquals(1L, rollOnProcessingTimeCountingPolicy.getOnProcessingTimeRollCounter());

		buckets.snapshotState(0L, new MockListState<>(), new MockListState<>());
		buckets.commitUpToCheckpoint(0L);

		Assert.assertTrue(buckets.getActiveBuckets().isEmpty());
	}

	@Test
	public void testPartCounterAfterBucketResurrection() throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();
		final Path path = new Path(outDir.toURI());

		final OnProcessingTimePolicy<String, String> rollOnProcessingTimeCountingPolicy =
				new OnProcessingTimePolicy<>(2L);

		final Buckets<String, String> buckets = new Buckets<>(
				path,
				new IdentityBucketAssigner(),
				new DefaultBucketFactoryImpl<>(),
				new RowWisePartWriter.Factory<>(new SimpleStringEncoder<>()),
				rollOnProcessingTimeCountingPolicy,
				0
		);

		// it takes the current processing time of the context for the creation time, and for the last modification time.
		buckets.onElement("test", new TestUtils.MockSinkContext(1L, 2L ,3L));
		Assert.assertEquals(1L, buckets.getActiveBuckets().get("test").getPartCounter());

		// now it should roll
		buckets.onProcessingTime(7L);
		Assert.assertEquals(1L, rollOnProcessingTimeCountingPolicy.getOnProcessingTimeRollCounter());
		Assert.assertEquals(1L, buckets.getActiveBuckets().get("test").getPartCounter());

		buckets.snapshotState(0L, new MockListState<>(), new MockListState<>());
		buckets.commitUpToCheckpoint(0L);

		Assert.assertTrue(buckets.getActiveBuckets().isEmpty());

		buckets.onElement("test", new TestUtils.MockSinkContext(2L, 3L ,4L));
		Assert.assertEquals(2L, buckets.getActiveBuckets().get("test").getPartCounter());
	}

	private static class OnProcessingTimePolicy<IN, BucketID> implements RollingPolicy<IN, BucketID> {

		private static final long serialVersionUID = 1L;

		private int onProcessingTimeRollCounter = 0;

		private final long rolloverInterval;

		OnProcessingTimePolicy(final long rolloverInterval) {
			this.rolloverInterval = rolloverInterval;
		}

		public int getOnProcessingTimeRollCounter() {
			return onProcessingTimeRollCounter;
		}

		@Override
		public boolean shouldRollOnCheckpoint(PartFileInfo<BucketID> partFileState) {
			return false;
		}

		@Override
		public boolean shouldRollOnEvent(PartFileInfo<BucketID> partFileState, IN element) {
			return false;
		}

		@Override
		public boolean shouldRollOnProcessingTime(PartFileInfo<BucketID> partFileState, long currentTime) {
			boolean result = currentTime - partFileState.getCreationTime() >= rolloverInterval;
			if (result) {
				onProcessingTimeRollCounter++;
			}
			return result;
		}
	}

	@Test
	public void testContextPassingNormalExecution() throws Exception {
		testCorrectTimestampPassingInContext(1L, 2L, 3L);
	}

	@Test
	public void testContextPassingNullTimestamp() throws Exception {
		testCorrectTimestampPassingInContext(null, 2L, 3L);
	}

	private void testCorrectTimestampPassingInContext(Long timestamp, long watermark, long processingTime) throws Exception {
		final File outDir = TEMP_FOLDER.newFolder();

		final Buckets<String, String> buckets = StreamingFileSink
				.<String>forRowFormat(new Path(outDir.toURI()), new SimpleStringEncoder<>())
				.withBucketAssigner(new VerifyingBucketAssigner(timestamp, watermark, processingTime))
				.createBuckets(2);

		buckets.onElement(
				"TEST",
				new TestUtils.MockSinkContext(
						timestamp,
						watermark,
						processingTime)
		);
	}

	private static class VerifyingBucketAssigner implements BucketAssigner<String, String> {

		private static final long serialVersionUID = 7729086510972377578L;

		private final Long expectedTimestamp;
		private final long expectedWatermark;
		private final long expectedProcessingTime;

		VerifyingBucketAssigner(
				final Long expectedTimestamp,
				final long expectedWatermark,
				final long expectedProcessingTime
		) {
			this.expectedTimestamp = expectedTimestamp;
			this.expectedWatermark = expectedWatermark;
			this.expectedProcessingTime = expectedProcessingTime;
		}

		@Override
		public String getBucketId(String element, BucketAssigner.Context context) {
			final Long elementTimestamp = context.timestamp();
			final long watermark = context.currentWatermark();
			final long processingTime = context.currentProcessingTime();

			Assert.assertEquals(expectedTimestamp, elementTimestamp);
			Assert.assertEquals(expectedProcessingTime, processingTime);
			Assert.assertEquals(expectedWatermark, watermark);

			return element;
		}

		@Override
		public SimpleVersionedSerializer<String> getSerializer() {
			return SimpleVersionedStringSerializer.INSTANCE;
		}
	}
}
