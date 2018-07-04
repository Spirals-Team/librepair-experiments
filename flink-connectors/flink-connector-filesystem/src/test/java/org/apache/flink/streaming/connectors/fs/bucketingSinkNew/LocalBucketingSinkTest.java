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

package org.apache.flink.streaming.connectors.fs.bucketingSinkNew;

import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.checkpoint.OperatorSubtaskState;
import org.apache.flink.streaming.api.operators.StreamSink;
import org.apache.flink.streaming.connectors.fs.Clock;
import org.apache.flink.streaming.connectors.fs.bucketing_new.BucketingSink;
import org.apache.flink.streaming.connectors.fs.bucketing_new.bucketers.Bucketer;
import org.apache.flink.streaming.connectors.fs.bucketing_new.writers.StringWriter;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.AbstractStreamOperatorTestHarness;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.apache.flink.util.TestLogger;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

/**
 * Tests for the {@link BucketingSink}.
 *
 * todo write test to verify that we do not keep trash in the state.
 * todo write test to verify that we start after recovery with the max previous part counter.
 */
public class LocalBucketingSinkTest extends TestLogger {

	@ClassRule
	public static TemporaryFolder tempFolder = new TemporaryFolder();

	private OneInputStreamOperatorTestHarness<String, Object> createRescalingTestSink(
			File outDir,
			int totalParallelism,
			int taskIdx,
			long inactivityInterval,
			long partMaxSize) throws Exception {

		BucketingSink<String> sink = new BucketingSink<String>(new Path(outDir.toURI()))
				.setBucketer(new Bucketer<String>() {

					private static final long serialVersionUID = -3086487303018372007L;

					@Override
					public Path getBucketPath(Clock clock, Path basePath, String element) {
						return new Path(basePath, element);
					}
				})
				.setWriter(new StringWriter<>())
				.setPartFileSize(partMaxSize)
				.setBucketCheckInterval(10L)
				.setRolloverInterval(inactivityInterval)
				.setInactivityInterval(inactivityInterval);

		return createTestSink(sink, totalParallelism, taskIdx);
	}

	private <T> OneInputStreamOperatorTestHarness<T, Object> createTestSink(
			BucketingSink<T> sink, int totalParallelism, int taskIdx) throws Exception {
		return new OneInputStreamOperatorTestHarness<>(new StreamSink<>(sink), 10, totalParallelism, taskIdx);
	}

	@Test
	public void testClosingWithoutInput() throws Exception {
		final File outDir = tempFolder.newFolder();

		OneInputStreamOperatorTestHarness<String, Object> testHarness =
				createRescalingTestSink(outDir, 1, 0, 100L, 124L);
		testHarness.setup();
		testHarness.open();

		// verify that we can close without ever having an input. An earlier version of the code
		// was throwing an NPE because we never initialized some internal state
		testHarness.close();
	}

	@Test
	public void testInactivityPeriodWithLateNotify() throws Exception {
		final File outDir = tempFolder.newFolder();

		OneInputStreamOperatorTestHarness<String, Object> testHarness =
				createRescalingTestSink(outDir, 1, 0, 100L, 124L);
		testHarness.setup();
		testHarness.open();

		testHarness.setProcessingTime(0L);

		testHarness.processElement(new StreamRecord<>("test1", 1L));
		testHarness.processElement(new StreamRecord<>("test2", 1L));
		checkLocalFs(outDir, 2, 0);

		testHarness.setProcessingTime(101L);								// put some in pending
		checkLocalFs(outDir, 2, 0);

		testHarness.snapshot(0L, 0L);				// put them in pending for 0
		checkLocalFs(outDir, 2, 0);

		testHarness.processElement(new StreamRecord<>("test3", 1L));
		testHarness.processElement(new StreamRecord<>("test4", 1L));

		testHarness.setProcessingTime(202L);								// put some in pending

		testHarness.snapshot(1L, 0L);				// put them in pending for 1
		checkLocalFs(outDir,4, 0);

		testHarness.notifyOfCompletedCheckpoint(0L);			// put the pending for 0 to the "committed" state
		checkLocalFs(outDir, 2, 2);

		testHarness.notifyOfCompletedCheckpoint(1L);			// put the pending for 1 to the "committed" state
		checkLocalFs(outDir, 0, 4);
	}

	@Test
	public void testClosingOnSnapshot() throws Exception {
		final File outDir = tempFolder.newFolder();

		OneInputStreamOperatorTestHarness<String, Object> testHarness =
				createRescalingTestSink(outDir, 1, 0, 100L, 2L);
		testHarness.setup();
		testHarness.open();

		testHarness.setProcessingTime(0L);

		testHarness.processElement(new StreamRecord<>("test1", 1L));
		testHarness.processElement(new StreamRecord<>("test2", 1L));
		checkLocalFs(outDir, 2, 0);

		// this is to check the inactivity threshold
		testHarness.setProcessingTime(101L);
		checkLocalFs(outDir, 2, 0);

		testHarness.processElement(new StreamRecord<>("test3", 1L));
		checkLocalFs(outDir, 3, 0);

		testHarness.snapshot(0L, 1L);
		checkLocalFs(outDir, 3, 0);

		testHarness.notifyOfCompletedCheckpoint(0L);
		checkLocalFs(outDir, 0, 3);

		OperatorSubtaskState snapshot = testHarness.snapshot(1L, 0L);

		testHarness.close();
		checkLocalFs(outDir, 0, 3);

		testHarness = createRescalingTestSink(outDir, 1, 0, 100L, 2L);
		testHarness.setup();
		testHarness.initializeState(snapshot);
		testHarness.open();
		checkLocalFs(outDir,0, 3);

		snapshot = testHarness.snapshot(2L, 0L);

		testHarness.processElement(new StreamRecord<>("test4", 10L));
		checkLocalFs(outDir, 1, 3);

		testHarness = createRescalingTestSink(outDir, 1, 0, 100L, 2L);
		testHarness.setup();
		testHarness.initializeState(snapshot);
		testHarness.open();

		// the in-progress file remains as we do not clean up now
		checkLocalFs(outDir, 1, 3);

		testHarness.close();

		// at close it is not moved to final because it is not part
		// of the current task's state, it was just a not cleaned up leftover.
		checkLocalFs(outDir, 1, 3);
	}

	@Test
	public void testSameParallelismWithShufflingStates() throws Exception {
		final File outDir = tempFolder.newFolder();

		OperatorSubtaskState mergedSnapshot = null;

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 =
					 createRescalingTestSink(outDir, 2, 0, 100L, 124L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 =
					 createRescalingTestSink(outDir, 2, 1, 100L, 124L)
		) {
			testHarness1.setup();
			testHarness1.open();

			testHarness2.setup();
			testHarness2.open();

			testHarness1.processElement(new StreamRecord<>("test1", 0L));
			checkLocalFs(outDir, 1, 0);

			testHarness2.processElement(new StreamRecord<>("test2", 0L));
			checkLocalFs(outDir, 2, 0);

			// intentionally we snapshot them in the reverse order so that the states are shuffled
			mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
					testHarness2.snapshot(0L, 0L),
					testHarness1.snapshot(0L, 0L)
			);
			checkLocalFs(outDir, 2, 0);

			// this will not be included in any checkpoint so it can be cleaned up (although we do not)
			testHarness2.processElement(new StreamRecord<>("test3", 0L));
			checkLocalFs(outDir, 3, 0);
		}

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 =
					 createRescalingTestSink(outDir, 2, 0, 100L, 124L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 =
					 createRescalingTestSink(outDir, 2, 1, 100L, 124L)
		) {
			testHarness1.setup();
			testHarness1.initializeState(mergedSnapshot);
			testHarness1.open();

			// the one in-progress will be the one assigned to the next instance,
			// the other is the test3 which is just not cleaned up
			checkLocalFs(outDir, 2, 1);

			testHarness2.setup();
			testHarness2.initializeState(mergedSnapshot);
			testHarness2.open();

			checkLocalFs(outDir, 1, 2);

			// the 1 in-progress can be discarded.
			checkLocalFs(outDir, 1, 2);
		}
	}

	@Test
	public void testScalingDown() throws Exception {
		final File outDir = tempFolder.newFolder();

		OperatorSubtaskState mergedSnapshot;

		// we set small file size so that the part file rolls.
		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 =
					 createRescalingTestSink(outDir, 3, 0, 100L, 2L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 =
					 createRescalingTestSink(outDir, 3, 1, 100L, 2L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness3 =
					 createRescalingTestSink(outDir, 3, 2, 100L, 2L)) {

			testHarness1.setup();
			testHarness1.open();

			testHarness2.setup();
			testHarness2.open();

			testHarness3.setup();
			testHarness3.open();

			testHarness1.processElement(new StreamRecord<>("test1", 0L));
			checkLocalFs(outDir, 1, 0);

			testHarness2.processElement(new StreamRecord<>("test2", 0L));
			checkLocalFs(outDir, 2, 0);

			testHarness1.snapshot(0L, 0L);

			testHarness3.processElement(new StreamRecord<>("test3", 0L));
			testHarness3.processElement(new StreamRecord<>("test4", 0L));
			checkLocalFs(outDir, 4, 0);

			// intentionally we snapshot them in the reverse order so that the states are shuffled
			mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
					testHarness3.snapshot(1L, 0L),
					testHarness1.snapshot(1L, 0L),
					testHarness2.snapshot(1L, 0L)
			);
		}

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 =
					 createRescalingTestSink(outDir, 2, 0, 100L, 2L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 =
					 createRescalingTestSink(outDir, 2, 1, 100L, 2L)
		) {

			testHarness1.setup();
			testHarness1.initializeState(mergedSnapshot);
			testHarness1.open();

			checkLocalFs(outDir, 2, 2);

			testHarness2.setup();
			testHarness2.initializeState(mergedSnapshot);
			testHarness2.open();

			checkLocalFs(outDir, 0, 4);
		}
	}

	@Test
	public void testScalingUp() throws Exception {
		final File outDir = tempFolder.newFolder();

		OperatorSubtaskState mergedSnapshot;

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 =
					 createRescalingTestSink(outDir, 2, 0, 100L, 2L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 =
					 createRescalingTestSink(outDir, 2, 1, 100L, 2L)) {

			testHarness1.setup();
			testHarness1.open();

			testHarness2.setup();
			testHarness2.open();

			testHarness1.processElement(new StreamRecord<>("test1", 1L));
			testHarness1.processElement(new StreamRecord<>("test2", 1L));

			checkLocalFs(outDir, 2, 0);

			testHarness2.processElement(new StreamRecord<>("test3", 1L));
			testHarness2.processElement(new StreamRecord<>("test4", 1L));
			testHarness2.processElement(new StreamRecord<>("test5", 1L));

			checkLocalFs(outDir, 5, 0);

			// intentionally we snapshot them in the reverse order so that the states are shuffled
			mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
					testHarness2.snapshot(0L, 0L),
					testHarness1.snapshot(0L, 0L)
			);
		}

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 =
					 createRescalingTestSink(outDir, 3, 0, 100L, 2L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 =
					 createRescalingTestSink(outDir, 3, 1, 100L, 2L);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness3 =
					 createRescalingTestSink(outDir, 3, 2, 100L, 2L)) {

			testHarness1.setup();
			testHarness1.initializeState(mergedSnapshot);
			testHarness1.open();

			checkLocalFs(outDir, 3, 2);

			testHarness2.setup();
			testHarness2.initializeState(mergedSnapshot);
			testHarness2.open();

			checkLocalFs(outDir, 1, 4);

			testHarness3.setup();
			testHarness3.initializeState(mergedSnapshot);
			testHarness3.open();

			checkLocalFs(outDir, 0, 5);

			// we have small batch size so it will roll
			testHarness1.processElement(new StreamRecord<>("test6", 0L));
			testHarness2.processElement(new StreamRecord<>("test6", 0L));
			testHarness3.processElement(new StreamRecord<>("test6", 0L));

			checkLocalFs(outDir, 3, 5);

			testHarness1.snapshot(1L, 0L);
			testHarness2.snapshot(1L, 0L);
			testHarness3.snapshot(1L, 0L);

			checkLocalFs(outDir, 3, 5);

			testHarness1.notifyOfCompletedCheckpoint(1L);
			testHarness2.notifyOfCompletedCheckpoint(1L);
			testHarness3.notifyOfCompletedCheckpoint(1L);

			checkLocalFs(outDir, 0, 8);
		}
	}

	@Test
	public void testMaxCounterUponRecovery() throws Exception {

	}

	public static void checkLocalFs(File outDir, int pending, int completed) {
		int pend = 0;
		int compl = 0;
		for (File file: FileUtils.listFiles(outDir, null, true)) {
			if (file.getAbsolutePath().endsWith("crc")) {
				continue;
			}

			// TODO: 7/3/18 all the prefix/suffix ... should become parameters
			String[] filePathParts = file.toPath().getFileName().toString().split("\\.");

			if (filePathParts.length > 2 && filePathParts[2].equals("inprogress")) {
				pend++;
			} else {
				compl++;
			}
		}

		Assert.assertEquals(pending, pend);
		Assert.assertEquals(completed, compl);
	}
}
