package org.apache.flink.streaming.api.functions.sink.filesystem;


import org.apache.flink.api.common.serialization.Writer;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.checkpoint.OperatorSubtaskState;
import org.apache.flink.streaming.api.operators.StreamSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketers.Bucketer;
import org.apache.flink.streaming.api.functions.sink.filesystem.writers.StringWriter;
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
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for the {@link BucketingSink}.
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

		return createRescalingTestSinkWithCustomBucketFactory(
				outDir, totalParallelism, taskIdx, inactivityInterval, partMaxSize, new DefaultBucketFactory<>());
	}

	private OneInputStreamOperatorTestHarness<String, Object> createRescalingTestSinkWithCustomBucketFactory(
			File outDir,
			int totalParallelism,
			int taskIdx,
			long inactivityInterval,
			long partMaxSize,
			BucketFactory<String> factory) throws Exception {

		BucketingSink<String> sink = new BucketingSink<>(new Path(outDir.toURI()), factory)
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
	public void testTruncateAfterRecoveryAndOverwrite() throws Exception {
		final File outDir = tempFolder.newFolder();
		OperatorSubtaskState snapshot;

		// we set the max bucket size to small so that we can know when it rolls
		try (OneInputStreamOperatorTestHarness<String, Object> testHarness = createRescalingTestSink(
				outDir, 1, 0, 100L, 10L)) {

			testHarness.setup();
			testHarness.open();

			// this creates a new bucket "test1" and part-0-0
			testHarness.processElement(new StreamRecord<>("test1", 1L));
			checkLocalFs(outDir, 1, 0);

			// we take a checkpoint so that we keep the in-progress file offset.
			snapshot = testHarness.snapshot(1L, 1L);

			Map<File, String> contents = getFileContentByPath(outDir);
			Assert.assertEquals(1L, contents.size());
			for (Map.Entry<File, String> fileContents : contents.entrySet()) {
				Assert.assertEquals(".part-0-0.inprogress", fileContents.getKey().getName());
				Assert.assertEquals("test1\n", fileContents.getValue());
			}

			// this will put more elements in the same part file
			testHarness.processElement(new StreamRecord<>("test1", 2L));

			contents = getFileContentByPath(outDir);
			Assert.assertEquals(1L, contents.size());
			for (Map.Entry<File, String> fileContents : contents.entrySet()) {
				Assert.assertEquals(".part-0-0.inprogress", fileContents.getKey().getName());
				Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
			}

			// and this will close the previous and create a new part file, part-0-1
			testHarness.processElement(new StreamRecord<>("test1", 2L));
			checkLocalFs(outDir, 2, 0);
		}

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness = createRescalingTestSink(
				outDir, 1, 0, 100L, 10L)) {

			testHarness.setup();
			testHarness.initializeState(snapshot);
			testHarness.open();

			// the in-progress is the not cleaned up one and the pending is truncated and finalized
			checkLocalFs(outDir, 1, 1);

			// now we go back to the first checkpoint so it should truncate part-0-0 and restart part-0-1
			int fileCounter = 0;
			for (Map.Entry<File, String> fileContents : getFileContentByPath(outDir).entrySet()) {
				if (fileContents.getKey().getName().equals("part-0-0")) {
					// truncated
					fileCounter++;
					Assert.assertEquals("test1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals(".part-0-1.inprogress")) {
					// ignored for now as we do not clean up. This will be overwritten.
					fileCounter++;
					Assert.assertEquals("test1\n", fileContents.getValue());
				}
			}
			Assert.assertEquals(2L, fileCounter);

			// these 2 will overwrite and the part-0-1
			testHarness.processElement(new StreamRecord<>("test1", 2L));
			testHarness.processElement(new StreamRecord<>("test1", 2L));
			checkLocalFs(outDir, 1, 1);

			// this will open part-0-2
			testHarness.processElement(new StreamRecord<>("test1", 2L));
			checkLocalFs(outDir, 2, 1);

			fileCounter = 0;
			for (Map.Entry<File, String> fileContents : getFileContentByPath(outDir).entrySet()) {
				if (fileContents.getKey().getName().equals("part-0-0")) {
					fileCounter++;
					Assert.assertEquals("test1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals(".part-0-1.inprogress")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals(".part-0-2.inprogress")) {
					fileCounter++;
					Assert.assertEquals("test1\n", fileContents.getValue());
				}
			}
			Assert.assertEquals(3L, fileCounter);
		}
	}

	@Test
	public void testCommitStagedFilesInCorrectOrder() throws Exception {
		final File outDir = tempFolder.newFolder();
		OperatorSubtaskState snapshot;

		// we set the max bucket size to small so that we can know when it rolls
		try (OneInputStreamOperatorTestHarness<String, Object> testHarness = createRescalingTestSink(
				outDir, 1, 0, 100L, 10L)) {

			testHarness.setup();
			testHarness.open();

			testHarness.setProcessingTime(0L);

			// these 2 create a new bucket "test1", with a .part-0-0.inprogress and also fill it
			testHarness.processElement(new StreamRecord<>("test1", 1L));
			testHarness.processElement(new StreamRecord<>("test1", 2L));
			checkLocalFs(outDir, 1, 0);

			// this will open .part-0-1.inprogress
			testHarness.processElement(new StreamRecord<>("test1", 3L));
			checkLocalFs(outDir, 2, 0);

			// we take a checkpoint so that we keep the in-progress file offset.
			testHarness.snapshot(1L, 1L);

			// this will close .part-0-1.inprogress
			testHarness.processElement(new StreamRecord<>("test1", 4L));

			// and open .part-0-2.inprogress
			testHarness.processElement(new StreamRecord<>("test1", 5L));
			testHarness.processElement(new StreamRecord<>("test1", 6L));
			checkLocalFs(outDir, 3, 0);                    // nothing committed yet

			testHarness.snapshot(2L, 2L);

			testHarness.processElement(new StreamRecord<>("test1", 7L));
			checkLocalFs(outDir, 4, 0);

			// this will close the part file (time)
			testHarness.setProcessingTime(101L);

			testHarness.snapshot(3L, 3L);

			testHarness.notifyOfCompletedCheckpoint(1L);							// the pending for checkpoint 1 are committed
			checkLocalFs(outDir, 3, 1);

			int fileCounter = 0;
			for (Map.Entry<File, String> fileContents : getFileContentByPath(outDir).entrySet()) {
				if (fileContents.getKey().getName().equals("part-0-0")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals(".part-0-1.inprogress")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals(".part-0-2.inprogress")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals(".part-0-3.inprogress")) {
					fileCounter++;
					Assert.assertEquals("test1\n", fileContents.getValue());
				}
			}
			Assert.assertEquals(4L, fileCounter);

			testHarness.notifyOfCompletedCheckpoint(3L);							// all the pending for checkpoint 2 and 3 are committed
			checkLocalFs(outDir, 0, 4);

			fileCounter = 0;
			for (Map.Entry<File, String> fileContents : getFileContentByPath(outDir).entrySet()) {
				if (fileContents.getKey().getName().equals("part-0-0")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals("part-0-1")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals("part-0-2")) {
					fileCounter++;
					Assert.assertEquals("test1\ntest1\n", fileContents.getValue());
				} else if (fileContents.getKey().getName().equals("part-0-3")) {
					fileCounter++;
					Assert.assertEquals("test1\n", fileContents.getValue());
				}
			}
			Assert.assertEquals(4L, fileCounter);
		}
	}

	@Test
	public void testInactivityPeriodWithLateNotify() throws Exception {
		final File outDir = tempFolder.newFolder();

		// we set a big bucket size so that it does not close by size, but by timers.
		try (OneInputStreamOperatorTestHarness<String, Object> testHarness = createRescalingTestSink(
				outDir, 1, 0, 100L, 124L)) {

			testHarness.setup();
			testHarness.open();

			testHarness.setProcessingTime(0L);

			testHarness.processElement(new StreamRecord<>("test1", 1L));
			testHarness.processElement(new StreamRecord<>("test2", 1L));
			checkLocalFs(outDir, 2, 0);

			int bucketCounter = 0;
			for (Map.Entry<File, String> fileContents : getFileContentByPath(outDir).entrySet()) {
				if (fileContents.getKey().getParentFile().getName().equals("test1")) {
					bucketCounter++;
				} else if (fileContents.getKey().getParentFile().getName().equals("test2")) {
					bucketCounter++;
				}
			}
			Assert.assertEquals(2L, bucketCounter);					// verifies that we have 2 buckets, "test1" and "test2"

			testHarness.setProcessingTime(101L);                                // put them in pending
			checkLocalFs(outDir, 2, 0);

			testHarness.snapshot(0L, 0L);                // put them in pending for 0
			checkLocalFs(outDir, 2, 0);

			// create another 2 buckets with 1 inprogress file each
			testHarness.processElement(new StreamRecord<>("test3", 1L));
			testHarness.processElement(new StreamRecord<>("test4", 1L));

			testHarness.setProcessingTime(202L);                                // put them in pending

			bucketCounter = 0;
			for (Map.Entry<File, String> fileContents : getFileContentByPath(outDir).entrySet()) {
				if (fileContents.getKey().getParentFile().getName().equals("test1")) {
					bucketCounter++;
				} else if (fileContents.getKey().getParentFile().getName().equals("test2")) {
					bucketCounter++;
				} else if (fileContents.getKey().getParentFile().getName().equals("test3")) {
					bucketCounter++;
				} else if (fileContents.getKey().getParentFile().getName().equals("test4")) {
					bucketCounter++;
				}
			}
			Assert.assertEquals(4L, bucketCounter);

			testHarness.snapshot(1L, 0L);                // put them in pending for 1
			checkLocalFs(outDir, 4, 0);

			testHarness.notifyOfCompletedCheckpoint(0L);            // put the pending for 0 to the "committed" state
			checkLocalFs(outDir, 2, 2);

			testHarness.notifyOfCompletedCheckpoint(1L);            // put the pending for 1 to the "committed" state
			checkLocalFs(outDir, 0, 4);
		}
	}

	@Test
	public void testClosingOnSnapshot() throws Exception {
		final File outDir = tempFolder.newFolder();

		OperatorSubtaskState snapshot;

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness = createRescalingTestSink(
				outDir, 1, 0, 100L, 2L)) {

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

			snapshot = testHarness.snapshot(1L, 0L);
		}

		checkLocalFs(outDir, 0, 3);

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 = createRescalingTestSink(
				outDir, 1, 0, 100L, 2L)) {
			testHarness1.setup();
			testHarness1.initializeState(snapshot);
			testHarness1.open();
			checkLocalFs(outDir, 0, 3);

			snapshot = testHarness1.snapshot(2L, 0L);

			testHarness1.processElement(new StreamRecord<>("test4", 10L));
			checkLocalFs(outDir, 1, 3);
		}

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness2 = createRescalingTestSink(
				outDir, 1, 0, 100L, 2L)) {
			testHarness2.setup();
			testHarness2.initializeState(snapshot);
			testHarness2.open();

			// the in-progress file remains as we do not clean up now
			checkLocalFs(outDir, 1, 3);
		}

		// at close it is not moved to final because it is not part
		// of the current task's state, it was just a not cleaned up leftover.
		checkLocalFs(outDir, 1, 3);
	}

	@Test
	public void testSameParallelismWithShufflingStates() throws Exception {
		final File outDir = tempFolder.newFolder();

		OperatorSubtaskState mergedSnapshot;

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
		final File outDir = tempFolder.newFolder();

		OperatorSubtaskState mergedSnapshot;

		final TestBucketFactory first = new TestBucketFactory();
		final TestBucketFactory second = new TestBucketFactory();

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 = createRescalingTestSinkWithCustomBucketFactory(
				outDir, 2, 0, 100L, 2L, first);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 = createRescalingTestSinkWithCustomBucketFactory(
					 outDir, 2, 1, 100L, 2L, second)
		) {
			testHarness1.setup();
			testHarness1.open();

			testHarness2.setup();
			testHarness2.open();

			// we only put elements in one task.
			testHarness1.processElement(new StreamRecord<>("test1", 0L));
			testHarness1.processElement(new StreamRecord<>("test1", 0L));
			testHarness1.processElement(new StreamRecord<>("test1", 0L));
			checkLocalFs(outDir, 3, 0);

			// intentionally we snapshot them in the reverse order so that the states are shuffled
			mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
					testHarness2.snapshot(0L, 0L),
					testHarness1.snapshot(0L, 0L)
			);
		}

		final TestBucketFactory firstRecovered = new TestBucketFactory();
		final TestBucketFactory secondRecovered = new TestBucketFactory();

		try (OneInputStreamOperatorTestHarness<String, Object> testHarness1 = createRescalingTestSinkWithCustomBucketFactory(
				outDir, 2, 0, 100L, 2L, firstRecovered);
			 OneInputStreamOperatorTestHarness<String, Object> testHarness2 = createRescalingTestSinkWithCustomBucketFactory(
					 outDir, 2, 1, 100L, 2L, secondRecovered)
		) {
			testHarness1.setup();
			testHarness1.initializeState(mergedSnapshot);
			testHarness1.open();

			// we have to send an element so that the factory updates its counter.
			testHarness1.processElement(new StreamRecord<>("test4", 0L));

			Assert.assertEquals(3L, firstRecovered.getInitialCounter());
			checkLocalFs(outDir, 1, 3);

			testHarness2.setup();
			testHarness2.initializeState(mergedSnapshot);
			testHarness2.open();

			// we have to send an element so that the factory updates its counter.
			testHarness2.processElement(new StreamRecord<>("test2", 0L));

			Assert.assertEquals(3L, secondRecovered.getInitialCounter());
			checkLocalFs(outDir, 2, 3);
		}
	}

	//////////////////////			Helper Methods			//////////////////////

	static class TestBucketFactory extends DefaultBucketFactory<String> {

		private static final long serialVersionUID = 2794824980604027930L;

		private long initialCounter = -1L;

		@Override
		public Bucket<String> getNewBucket(
				FileSystem fileSystem,
				int subtaskIndex,
				Path bucketPath,
				long initialPartCounter,
				long maxPartSize,
				long rolloverTime,
				long inactivityTime,
				Writer<String> writer) throws IOException {

			this.initialCounter = initialPartCounter;

			return super.getNewBucket(
					fileSystem,
					subtaskIndex,
					bucketPath,
					initialPartCounter,
					maxPartSize,
					rolloverTime,
					inactivityTime,
					writer);
		}

		@Override
		public Bucket<String> getRestoredBucket(
				FileSystem fileSystem,
				int subtaskIndex,
				Path bucketPath,
				long initialPartCounter,
				long maxPartSize,
				long rolloverTime,
				long inactivityTime,
				Writer<String> writer,
				Bucket.BucketState bucketstate) throws IOException {

			this.initialCounter = initialPartCounter;

			return super.getRestoredBucket(
					fileSystem,
					subtaskIndex,
					bucketPath,
					initialPartCounter,
					maxPartSize,
					rolloverTime,
					inactivityTime,
					writer,
					bucketstate);
		}

		public long getInitialCounter() {
			return initialCounter;
		}
	}

	private static void checkLocalFs(File outDir, int expectedInProgress, int expectedCompleted) {
		int inProgress = 0;
		int finished = 0;

		for (File file: FileUtils.listFiles(outDir, null, true)) {
			if (file.getAbsolutePath().endsWith("crc")) {
				continue;
			}

			if (file.toPath().getFileName().toString().startsWith(".")) {
				inProgress++;
			} else {
				finished++;
			}
		}

		Assert.assertEquals(expectedInProgress, inProgress);
		Assert.assertEquals(expectedCompleted, finished);
	}

	private Map<File, String> getFileContentByPath(File directory) throws IOException {
		Map<File, String> contents = new HashMap<>();

		final Collection<File> filesInBucket = FileUtils.listFiles(directory, null, true);
		for (File file : filesInBucket) {
			contents.put(file, FileUtils.readFileToString(file));
		}
		return contents;
	}
}
