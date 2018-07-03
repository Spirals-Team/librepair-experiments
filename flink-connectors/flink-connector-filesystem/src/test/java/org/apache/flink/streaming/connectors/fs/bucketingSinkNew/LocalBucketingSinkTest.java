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
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.apache.flink.util.TestLogger;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

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
			long inactivityInterval) throws Exception {

		BucketingSink<String> sink = new BucketingSink<String>(new Path(outDir.toURI()))
				.setBucketer(new Bucketer<String>() {
					private static final long serialVersionUID = 1L;

					@Override
					public Path getBucketPath(Clock clock, Path basePath, String element) {
						return new Path(basePath, element);
					}
				})
				.setWriter(new StringWriter<>())
				.setPartFileSize(2L)
				.setBucketCheckInterval(10L)
				.setRolloverInterval(inactivityInterval)
				.setInactivityInterval(inactivityInterval);

		return createTestSink(sink, totalParallelism, taskIdx);
	}

	private <T> OneInputStreamOperatorTestHarness<T, Object> createTestSink(
			BucketingSink<T> sink, int totalParallelism, int taskIdx) throws Exception {
		return new OneInputStreamOperatorTestHarness<>(new StreamSink<>(sink), 10, totalParallelism, taskIdx);
	}

	private OneInputStreamOperatorTestHarness<String, Object> createRescalingTestSinkWithRollover(
			File outDir,
			int totalParallelism,
			int taskIdx,
			long inactivityInterval,
			long rolloverInterval) throws Exception {

		BucketingSink<String> sink = new BucketingSink<String>(new Path(outDir.toURI())) // TODO: 7/2/18 add this
			.setBucketer(new Bucketer<String>() {
				private static final long serialVersionUID = 1L;

				@Override
				public Path getBucketPath(Clock clock, Path basePath, String element) {
					return new Path(basePath, element);
				}
			})
			.setWriter(new StringWriter<>())
			.setInactivityInterval(inactivityInterval)
			.setRolloverInterval(rolloverInterval);

		return createTestSink(sink, totalParallelism, taskIdx);
	}

	@Test
	public void testClosingWithoutInput() throws Exception {
		final File outDir = tempFolder.newFolder();

		OneInputStreamOperatorTestHarness<String, Object> testHarness =
				createRescalingTestSink(outDir, 1, 0, 100L);
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
				createRescalingTestSink(outDir, 1, 0, 100L);
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

	public static void checkLocalFs(File outDir, int pending, int completed) throws IOException {
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

	@Test
	public void testBucketStateTransitions() throws Exception {
		final File outDir = tempFolder.newFolder();

		OneInputStreamOperatorTestHarness<String, Object> testHarness =
				createRescalingTestSink(outDir, 1, 0, 100L);
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

//		testHarness.snapshot(0, 0);
//		checkLocalFs(outDir, 3, 0);

		testHarness.notifyOfCompletedCheckpoint(0L);
//		checkLocalFs(outDir, 1, 2);

		OperatorSubtaskState snapshot = testHarness.snapshot(1L, 0L);

//		testHarness.close();
//		checkLocalFs(outDir, 1, 2);

		testHarness = createRescalingTestSink(outDir, 1, 0, 100L);
		testHarness.setup();
		testHarness.initializeState(snapshot);
		testHarness.open();
		checkLocalFs(outDir,0, 3);

		snapshot = testHarness.snapshot(2, 0);

		testHarness.processElement(new StreamRecord<>("test4", 10));
		checkLocalFs(outDir, 1, 3);

		testHarness = createRescalingTestSink(outDir, 1, 0, 100);
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

//	@Test
//	public void testSameParallelismWithShufflingStates() throws Exception {
//		final File outDir = tempFolder.newFolder();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness1 = createRescalingTestSink(outDir, 2, 0, 100);
//		testHarness1.setup();
//		testHarness1.open();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness2 = createRescalingTestSink(outDir, 2, 1, 100);
//		testHarness2.setup();
//		testHarness2.open();
//
//		testHarness1.processElement(new StreamRecord<>("test1", 0L));
//		checkLocalFs(outDir, 1, 0, 0, 0);
//
//		testHarness2.processElement(new StreamRecord<>("test2", 0L));
//		checkLocalFs(outDir, 2, 0, 0, 0);
//
//		// intentionally we snapshot them in the reverse order so that the states are shuffled
//		OperatorSubtaskState mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
//			testHarness2.snapshot(0, 0),
//			testHarness1.snapshot(0, 0)
//		);
//
//		checkLocalFs(outDir, 2, 0, 0, 0);
//
//		// this will not be included in any checkpoint so it can be cleaned up (although we do not)
//		testHarness2.processElement(new StreamRecord<>("test3", 0L));
//		checkLocalFs(outDir, 3, 0, 0, 0);
//
//		testHarness1 = createRescalingTestSink(outDir, 2, 0, 100);
//		testHarness1.setup();
//		testHarness1.initializeState(mergedSnapshot);
//		testHarness1.open();
//
//		// the one in-progress will be the one assigned to the next instance,
//		// the other is the test3 which is just not cleaned up
//		checkLocalFs(outDir, 2, 0, 1, 1);
//
//		testHarness2 = createRescalingTestSink(outDir, 2, 1, 100);
//		testHarness2.setup();
//		testHarness2.initializeState(mergedSnapshot);
//		testHarness2.open();
//
//		checkLocalFs(outDir, 1, 0, 2, 2);
//
//		testHarness1.close();
//		testHarness2.close();
//
//		// the 1 in-progress can be discarded.
//		checkLocalFs(outDir, 1, 0, 2, 2);
//	}
//
//	@Test
//	public void testScalingDown() throws Exception {
//		final File outDir = tempFolder.newFolder();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness1 = createRescalingTestSink(outDir, 3, 0, 100);
//		testHarness1.setup();
//		testHarness1.open();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness2 = createRescalingTestSink(outDir, 3, 1, 100);
//		testHarness2.setup();
//		testHarness2.open();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness3 = createRescalingTestSink(outDir, 3, 2, 100);
//		testHarness3.setup();
//		testHarness3.open();
//
//		testHarness1.processElement(new StreamRecord<>("test1", 0L));
//		checkLocalFs(outDir, 1, 0, 0, 0);
//
//		testHarness2.processElement(new StreamRecord<>("test2", 0L));
//		checkLocalFs(outDir, 2, 0, 0, 0);
//
//		testHarness3.processElement(new StreamRecord<>("test3", 0L));
//		testHarness3.processElement(new StreamRecord<>("test4", 0L));
//		checkLocalFs(outDir, 4, 0, 0, 0);
//
//		// intentionally we snapshot them in the reverse order so that the states are shuffled
//		OperatorSubtaskState mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
//			testHarness3.snapshot(0, 0),
//			testHarness1.snapshot(0, 0),
//			testHarness2.snapshot(0, 0)
//		);
//
//		testHarness1 = createRescalingTestSink(outDir, 2, 0, 100);
//		testHarness1.setup();
//		testHarness1.initializeState(mergedSnapshot);
//		testHarness1.open();
//
//		checkLocalFs(outDir, 1, 0, 3, 3);
//
//		testHarness2 = createRescalingTestSink(outDir, 2, 1, 100);
//		testHarness2.setup();
//		testHarness2.initializeState(mergedSnapshot);
//		testHarness2.open();
//
//		checkLocalFs(outDir, 0, 0, 4, 4);
//	}
//
//	@Test
//	public void testScalingUp() throws Exception {
//		final File outDir = tempFolder.newFolder();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness1 = createRescalingTestSink(outDir, 2, 0, 100);
//		testHarness1.setup();
//		testHarness1.open();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness2 = createRescalingTestSink(outDir, 2, 0, 100);
//		testHarness2.setup();
//		testHarness2.open();
//
//		testHarness1.processElement(new StreamRecord<>("test1", 1L));
//		testHarness1.processElement(new StreamRecord<>("test2", 1L));
//
//		checkLocalFs(outDir, 2, 0, 0, 0);
//
//		testHarness2.processElement(new StreamRecord<>("test3", 1L));
//		testHarness2.processElement(new StreamRecord<>("test4", 1L));
//		testHarness2.processElement(new StreamRecord<>("test5", 1L));
//
//		checkLocalFs(outDir, 5, 0, 0, 0);
//
//		// intentionally we snapshot them in the reverse order so that the states are shuffled
//		OperatorSubtaskState mergedSnapshot = AbstractStreamOperatorTestHarness.repackageState(
//			testHarness2.snapshot(0, 0),
//			testHarness1.snapshot(0, 0)
//		);
//
//		testHarness1 = createRescalingTestSink(outDir, 3, 0, 100);
//		testHarness1.setup();
//		testHarness1.initializeState(mergedSnapshot);
//		testHarness1.open();
//
//		checkLocalFs(outDir, 2, 0, 3, 3);
//
//		testHarness2 = createRescalingTestSink(outDir, 3, 1, 100);
//		testHarness2.setup();
//		testHarness2.initializeState(mergedSnapshot);
//		testHarness2.open();
//
//		checkLocalFs(outDir, 0, 0, 5, 5);
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness3 = createRescalingTestSink(outDir, 3, 2, 100);
//		testHarness3.setup();
//		testHarness3.initializeState(mergedSnapshot);
//		testHarness3.open();
//
//		checkLocalFs(outDir, 0, 0, 5, 5);
//
//		testHarness1.processElement(new StreamRecord<>("test6", 0));
//		testHarness2.processElement(new StreamRecord<>("test6", 0));
//		testHarness3.processElement(new StreamRecord<>("test6", 0));
//
//		checkLocalFs(outDir, 3, 0, 5, 5);
//
//		testHarness1.snapshot(1, 0);
//		testHarness2.snapshot(1, 0);
//		testHarness3.snapshot(1, 0);
//
//		testHarness1.close();
//		testHarness2.close();
//		testHarness3.close();
//
//		checkLocalFs(outDir, 0, 3, 5, 5);
//	}
//
//	@Test
//	public void testRolloverInterval() throws Exception {
//		final File outDir = tempFolder.newFolder();
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness = createRescalingTestSinkWithRollover(outDir, 1, 0, 1000L, 100L);
//		testHarness.setup();
//		testHarness.open();
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.processElement(new StreamRecord<>("test1", 1L));
//		checkLocalFs(outDir, 1, 0, 0, 0);
//
//		// invoke rollover based on rollover interval
//		testHarness.setProcessingTime(101L);
//		testHarness.processElement(new StreamRecord<>("test1", 2L));
//		checkLocalFs(outDir, 1, 1, 0, 0);
//
//		testHarness.snapshot(0, 0);
//		testHarness.notifyOfCompletedCheckpoint(0);
//		checkLocalFs(outDir, 1, 0, 1, 0);
//
//		// move the in-progress file to pending
//		testHarness.setProcessingTime(3000L);
//		testHarness.snapshot(1, 1);
//		checkLocalFs(outDir, 0, 1, 1, 0);
//
//		// move the pending file to "committed"
//		testHarness.notifyOfCompletedCheckpoint(1);
//		testHarness.close();
//
//		checkLocalFs(outDir, 0, 0, 2, 0);
//	}
//todo
//	/**
//	 * This tests {@link StringWriter} with
//	 * non-bucketing output.
//	 */
//	@Test
//	public void testNonRollingStringWriter() throws Exception {
//		final String outPath = hdfsURI + "/string-non-rolling-out";
//
//		final int numElements = 20;
//
//		BucketingSink<String> sink = new BucketingSink<String>(new Path()) // TODO: 7/2/18 add this
//			.setBucketer(new BasePathBucketer<>());
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness = createTestSink(sink, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElements; i++) {
//			testHarness.processElement(new StreamRecord<>("message #" + Integer.toString(i)));
//		}
//
//		testHarness.close();
//
//		FSDataInputStream inStream = dfs.open(new Path(outPath + "/" + PART_PREFIX + "-0-0"));
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
//
//		for (int i = 0; i < numElements; i++) {
//			String line = br.readLine();
//			Assert.assertEquals("message #" + i, line);
//		}
//
//		inStream.close();
//	}
//
//	/**
//	 * This tests {@link SequenceFileWriter}
//	 * with non-rolling output and without compression.
//	 */
//	@Test
//	public void testNonRollingSequenceFileWithoutCompressionWriter() throws Exception {
//		final String outPath = hdfsURI + "/seq-no-comp-non-rolling-out";
//
//		final int numElements = 20;
//
//		BucketingSink<Tuple2<IntWritable, Text>> sink = new BucketingSink<Tuple2<IntWritable, Text>>(outPath)
//			.setWriter(new SequenceFileWriter<IntWritable, Text>())
//			.setBucketer(new BasePathBucketer<Tuple2<IntWritable, Text>>())
//			.setPartPrefix(PART_PREFIX)
//			.setPendingPrefix("")
//			.setPendingSuffix("");
//
//		sink.setInputType(TypeInformation.of(new TypeHint<Tuple2<IntWritable, Text>>(){}), new ExecutionConfig());
//
//		OneInputStreamOperatorTestHarness<Tuple2<IntWritable, Text>, Object> testHarness =
//			createTestSink(sink, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElements; i++) {
//			testHarness.processElement(new StreamRecord<>(Tuple2.of(
//				new IntWritable(i),
//				new Text("message #" + Integer.toString(i))
//			)));
//		}
//
//		testHarness.close();
//
//		FSDataInputStream inStream = dfs.open(new Path(outPath + "/" + PART_PREFIX + "-0-0"));
//
//		SequenceFile.Reader reader = new SequenceFile.Reader(inStream, 1000, 0, 100000, new Configuration());
//
//		IntWritable intWritable = new IntWritable();
//		Text txt = new Text();
//
//		for (int i = 0; i < numElements; i++) {
//			reader.next(intWritable, txt);
//			Assert.assertEquals(i, intWritable.get());
//			Assert.assertEquals("message #" + i, txt.toString());
//		}
//
//		reader.close();
//		inStream.close();
//	}
//
//	/**
//	 * This tests {@link AvroKeyValueSinkWriter}
//	 * with non-rolling output and with compression.
//	 */
//	@Test
//	public void testNonRollingAvroKeyValueWithCompressionWriter() throws Exception {
//		final String outPath = hdfsURI + "/avro-kv-no-comp-non-rolling-out";
//
//		final int numElements = 20;
//
//		Map<String, String> properties = new HashMap<>();
//		Schema keySchema = Schema.create(Schema.Type.INT);
//		Schema valueSchema = Schema.create(Schema.Type.STRING);
//		properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_KEY_SCHEMA, keySchema.toString());
//		properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_VALUE_SCHEMA, valueSchema.toString());
//		properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS, String.valueOf(true));
//		properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS_CODEC, DataFileConstants.SNAPPY_CODEC);
//
//		BucketingSink<Tuple2<Integer, String>> sink = new BucketingSink<Tuple2<Integer, String>>(outPath)
//			.setWriter(new AvroKeyValueSinkWriter<Integer, String>(properties))
//			.setBucketer(new BasePathBucketer<Tuple2<Integer, String>>())
//			.setPartPrefix(PART_PREFIX)
//			.setPendingPrefix("")
//			.setPendingSuffix("");
//
//		OneInputStreamOperatorTestHarness<Tuple2<Integer, String>, Object> testHarness =
//			createTestSink(sink, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElements; i++) {
//			testHarness.processElement(new StreamRecord<>(Tuple2.of(
//				i, "message #" + Integer.toString(i)
//			)));
//		}
//
//		testHarness.close();
//
//		GenericData.setStringType(valueSchema, GenericData.StringType.String);
//		Schema elementSchema = AvroKeyValueSinkWriter.AvroKeyValue.getSchema(keySchema, valueSchema);
//
//		FSDataInputStream inStream = dfs.open(new Path(outPath + "/" + PART_PREFIX + "-0-0"));
//
//		SpecificDatumReader<GenericRecord> elementReader = new SpecificDatumReader<>(elementSchema);
//		DataFileStream<GenericRecord> dataFileStream = new DataFileStream<>(inStream, elementReader);
//		for (int i = 0; i < numElements; i++) {
//			AvroKeyValueSinkWriter.AvroKeyValue<Integer, String> wrappedEntry =
//				new AvroKeyValueSinkWriter.AvroKeyValue<>(dataFileStream.next());
//			int key = wrappedEntry.getKey();
//			Assert.assertEquals(i, key);
//			String value = wrappedEntry.getValue();
//			Assert.assertEquals("message #" + i, value);
//		}
//
//		dataFileStream.close();
//		inStream.close();
//	}
//
//	/**
//	 * This uses {@link DateTimeBucketer} to
//	 * produce rolling files. We use {@link OneInputStreamOperatorTestHarness} to manually
//	 * advance processing time.
//	 */
//	@Test
//	public void testDateTimeRollingStringWriter() throws Exception {
//		final int numElements = 20;
//
//		final String outPath = hdfsURI + "/rolling-out";
//
//		BucketingSink<String> sink = new BucketingSink<String>(outPath)
//			.setBucketer(new DateTimeBucketer<String>("ss"))
//			.setPartPrefix(PART_PREFIX)
//			.setPendingPrefix("")
//			.setPendingSuffix("");
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness = createTestSink(sink, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElements; i++) {
//			// Every 5 elements, increase the clock time. We should end up with 5 elements per bucket.
//			if (i % 5 == 0) {
//				testHarness.setProcessingTime(i * 1000L);
//			}
//			testHarness.processElement(new StreamRecord<>("message #" + Integer.toString(i)));
//		}
//
//		testHarness.close();
//
//		RemoteIterator<LocatedFileStatus> files = dfs.listFiles(new Path(outPath), true);
//
//		// We should have 4 rolling files across 4 time intervals
//		int numFiles = 0;
//		while (files.hasNext()) {
//			LocatedFileStatus file = files.next();
//			numFiles++;
//			if (file.getPath().toString().contains("rolling-out/00")) {
//				FSDataInputStream inStream = dfs.open(file.getPath());
//
//				BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
//
//				for (int i = 0; i < 5; i++) {
//					String line = br.readLine();
//					Assert.assertEquals("message #" + i, line);
//				}
//
//				inStream.close();
//			} else if (file.getPath().toString().contains("rolling-out/05")) {
//				FSDataInputStream inStream = dfs.open(file.getPath());
//
//				BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
//
//				for (int i = 5; i < 10; i++) {
//					String line = br.readLine();
//					Assert.assertEquals("message #" + i, line);
//				}
//
//				inStream.close();
//			} else if (file.getPath().toString().contains("rolling-out/10")) {
//				FSDataInputStream inStream = dfs.open(file.getPath());
//
//				BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
//
//				for (int i = 10; i < 15; i++) {
//					String line = br.readLine();
//					Assert.assertEquals("message #" + i, line);
//				}
//
//				inStream.close();
//			} else if (file.getPath().toString().contains("rolling-out/15")) {
//				FSDataInputStream inStream = dfs.open(file.getPath());
//
//				BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
//
//				for (int i = 15; i < 20; i++) {
//					String line = br.readLine();
//					Assert.assertEquals("message #" + i, line);
//				}
//
//				inStream.close();
//			} else {
//				Assert.fail("File " + file + " does not match any expected roll pattern.");
//			}
//		}
//
//		Assert.assertEquals(4, numFiles);
//	}
//
//	/**
//	 * This uses a custom bucketing function which determines the bucket from the input.
//	 */
//	@Test
//	public void testCustomBucketing() throws Exception {
//		File dataDir = tempFolder.newFolder();
//
//		final int numIds = 4;
//		final int numElements = 20;
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness = createTestSink(dataDir, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElements; i++) {
//			testHarness.processElement(new StreamRecord<>(Integer.toString(i % numIds)));
//		}
//
//		testHarness.close();
//
//		// we should have 4 buckets, with 1 file each
//		int numFiles = 0;
//		for (File file: FileUtils.listFiles(dataDir, null, true)) {
//			if (file.getName().startsWith(PART_PREFIX)) {
//				numFiles++;
//			}
//		}
//
//		Assert.assertEquals(4, numFiles);
//	}
//
//	/**
//	 * This uses a custom bucketing function which determines the bucket from the input.
//	 * We use a simulated clock to reduce the number of buckets being written to over time.
//	 * This causes buckets to become 'inactive' and their file parts 'closed' by the sink.
//	 */
//	@Test
//	public void testCustomBucketingInactiveBucketCleanup() throws Exception {
//		File dataDir = tempFolder.newFolder();
//
//		final int step1NumIds = 4;
//		final int step2NumIds = 2;
//		final int numElementsPerStep = 20;
//
//		OneInputStreamOperatorTestHarness<String, Object> testHarness = createTestSink(dataDir, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElementsPerStep; i++) {
//			testHarness.processElement(new StreamRecord<>(Integer.toString(i % step1NumIds)));
//		}
//
//		testHarness.setProcessingTime(2 * 60 * 1000L);
//
//		for (int i = 0; i < numElementsPerStep; i++) {
//			testHarness.processElement(new StreamRecord<>(Integer.toString(i % step2NumIds)));
//		}
//
//		testHarness.setProcessingTime(6 * 60 * 1000L);
//
//		for (int i = 0; i < numElementsPerStep; i++) {
//			testHarness.processElement(new StreamRecord<>(Integer.toString(i % step2NumIds)));
//		}
//
//		// we should have 4 buckets, with 1 file each
//		// 2 of these buckets should have been finalised due to becoming inactive
//		int numFiles = 0;
//		int numInProgress = 0;
//		for (File file: FileUtils.listFiles(dataDir, null, true)) {
//			if (file.getAbsolutePath().endsWith("crc")) {
//				continue;
//			}
//			if (file.getPath().endsWith(IN_PROGRESS_SUFFIX)) {
//				numInProgress++;
//			}
//			numFiles++;
//		}
//
//		testHarness.close();
//
//		Assert.assertEquals(4, numFiles);
//		Assert.assertEquals(2, numInProgress);
//	}
//
//	/**
//	 * This tests user defined hdfs configuration.
//	 * @throws Exception
//	 */
//	@Test
//	public void testUserDefinedConfiguration() throws Exception {
//		final String outPath = hdfsURI + "/string-non-rolling-with-config";
//		final int numElements = 20;
//
//		Map<String, String> properties = new HashMap<>();
//		Schema keySchema = Schema.create(Schema.Type.INT);
//		Schema valueSchema = Schema.create(Schema.Type.STRING);
//		properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_KEY_SCHEMA, keySchema.toString());
//		properties.put(AvroKeyValueSinkWriter.CONF_OUTPUT_VALUE_SCHEMA, valueSchema.toString());
//		properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS, String.valueOf(true));
//		properties.put(AvroKeyValueSinkWriter.CONF_COMPRESS_CODEC, DataFileConstants.SNAPPY_CODEC);
//
//		Configuration conf = new Configuration();
//		conf.set("io.file.buffer.size", "40960");
//
//		BucketingSink<Tuple2<Integer, String>> sink = new BucketingSink<Tuple2<Integer, String>>(outPath)
//			.setFSConfig(conf)
//			.setWriter(new StreamWriterWithConfigCheck<Integer, String>(properties, "io.file.buffer.size", "40960"))
//			.setBucketer(new BasePathBucketer<Tuple2<Integer, String>>())
//			.setPartPrefix(PART_PREFIX)
//			.setPendingPrefix("")
//			.setPendingSuffix("");
//
//		OneInputStreamOperatorTestHarness<Tuple2<Integer, String>, Object> testHarness =
//			createTestSink(sink, 1, 0);
//
//		testHarness.setProcessingTime(0L);
//
//		testHarness.setup();
//		testHarness.open();
//
//		for (int i = 0; i < numElements; i++) {
//			testHarness.processElement(new StreamRecord<>(Tuple2.of(
//				i, "message #" + Integer.toString(i)
//			)));
//		}
//
//		testHarness.close();
//
//		GenericData.setStringType(valueSchema, GenericData.StringType.String);
//		Schema elementSchema = AvroKeyValueSinkWriter.AvroKeyValue.getSchema(keySchema, valueSchema);
//
//		FSDataInputStream inStream = dfs.open(new Path(outPath + "/" + PART_PREFIX + "-0-0"));
//
//		SpecificDatumReader<GenericRecord> elementReader = new SpecificDatumReader<>(elementSchema);
//		DataFileStream<GenericRecord> dataFileStream = new DataFileStream<>(inStream, elementReader);
//		for (int i = 0; i < numElements; i++) {
//			AvroKeyValueSinkWriter.AvroKeyValue<Integer, String> wrappedEntry =
//				new AvroKeyValueSinkWriter.AvroKeyValue<>(dataFileStream.next());
//			int key = wrappedEntry.getKey();
//			Assert.assertEquals(i, key);
//			String value = wrappedEntry.getValue();
//			Assert.assertEquals("message #" + i, value);
//		}
//
//		dataFileStream.close();
//		inStream.close();
//	}
//
//	private static class StreamWriterWithConfigCheck<K, V> extends AvroKeyValueSinkWriter<K, V> {
//		private Map<String, String> properties;
//		private String key;
//		private String expect;
//		public StreamWriterWithConfigCheck(Map<String, String> properties, String key, String expect) {
//			super(properties);
//			this.properties = properties;
//			this.key = key;
//			this.expect = expect;
//		}
//
//		@Override
//		public void open(FileSystem fs, Path path) throws IOException {
//			super.open(fs, path);
//			Assert.assertEquals(expect, fs.getConf().get(key));
//		}
//
//		@Override
//		public Writer<Tuple2<K, V>> duplicate() {
//			return new StreamWriterWithConfigCheck<>(properties, key, expect);
//		}
//	}

}
