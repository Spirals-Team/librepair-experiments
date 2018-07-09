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

package org.apache.flink.core.fs.local;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.RecoverableFsDataOutputStream;
import org.apache.flink.core.fs.ResumableWriter;
import org.apache.flink.core.io.SimpleVersionedSerializer;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Tests for the {@link LocalResumableWriter}.
 */
public class LocalFileSystemResumableWriterTest {

	private static final String testData1 = "THIS IS A TEST 1.";
	private static final String testData2 = "THIS IS A TEST 2.";
	private static final String testData3 = "THIS IS A TEST 3.";

	@ClassRule
	public static TemporaryFolder tempFolder = new TemporaryFolder();

	public ResumableWriter initilizeWriter() {
		try {
			return FileSystem.getLocalFileSystem().createRecoverableWriter();
		} catch (IOException e) {
			fail();
		}
		return null;
	}

	@Test
	public void testCloseWithNoData() throws IOException {
		final ResumableWriter writer = initilizeWriter();

		final File testDir = tempFolder.newFolder();

		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		final RecoverableFsDataOutputStream stream = writer.open(path);
		for (Map.Entry<File, String> fileContents : getFileContentByPath(testDir).entrySet()) {
			Assert.assertTrue(fileContents.getKey().getName().startsWith(".part-0.inprogress."));
			Assert.assertTrue(fileContents.getValue().isEmpty());
		}

		stream.closeForCommit().commit();

		for (Map.Entry<File, String> fileContents : getFileContentByPath(testDir).entrySet()) {
			Assert.assertEquals("part-0", fileContents.getKey().getName());
			Assert.assertTrue(fileContents.getValue().isEmpty());
		}
	}

	@Test
	public void testCommitAfterNormalClose() throws IOException {
		final ResumableWriter writer = initilizeWriter();

		final File testDir = tempFolder.newFolder();
		
		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		try (final RecoverableFsDataOutputStream stream = writer.open(path)) {
			stream.write(testData1.getBytes(Charset.forName("UTF-8")));
			stream.closeForCommit().commit();

			for (Map.Entry<File, String> fileContents : getFileContentByPath(testDir).entrySet()) {
				Assert.assertEquals("part-0", fileContents.getKey().getName());
				Assert.assertEquals(testData1, fileContents.getValue());
			}
		}
	}

	@Test
	public void testExceptionWritingAfterCloseForCommit() throws IOException {
		final File testDir = tempFolder.newFolder();

		final ResumableWriter writer = initilizeWriter();
		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		try (final RecoverableFsDataOutputStream stream = writer.open(path)) {
			stream.write(testData1.getBytes(Charset.forName("UTF-8")));

			stream.closeForCommit().getRecoverable();
			stream.write(testData2.getBytes(Charset.forName("UTF-8")));
			fail();
		} catch (IOException e) {
			Assert.assertEquals("Stream Closed", e.getMessage());
		}
	}

	// TESTS FOR RECOVERY

	@Test
	public void testResumeAfterPersist() throws IOException {
		final File testDir = tempFolder.newFolder();

		final ResumableWriter writer = initilizeWriter();
		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		ResumableWriter.ResumeRecoverable recoverable;
		try (final RecoverableFsDataOutputStream stream = writer.open(path)) {
			stream.write(testData1.getBytes(Charset.forName("UTF-8")));

			// get the valid offset
			recoverable = stream.persist();

			// and write some more data
			stream.write(testData2.getBytes(Charset.forName("UTF-8")));

			Map<File, String> files = getFileContentByPath(testDir);
			Assert.assertEquals(1L, files.size());

			for (Map.Entry<File, String> fileContents : files.entrySet()) {
				Assert.assertTrue(fileContents.getKey().getName().startsWith(".part-0.inprogress."));
				Assert.assertEquals(testData1 + testData2, fileContents.getValue());
			}
		}

		SimpleVersionedSerializer<ResumableWriter.ResumeRecoverable> serializer = writer.getResumeRecoverableSerializer();
		byte[] serializedRecoverable = serializer.serialize(recoverable);

		// get a new serializer from a new writer to make sure that no pre-initilized state leaks in.
		final ResumableWriter newWriter = initilizeWriter();
		final ResumableWriter.ResumeRecoverable recoveredRecoverable =
				newWriter.getResumeRecoverableSerializer()
						.deserialize(serializer.getVersion(), serializedRecoverable);

		try (final RecoverableFsDataOutputStream recoveredStream = newWriter.recover(recoveredRecoverable)) {

			// we expect the data to be truncated
			Map<File, String> files = getFileContentByPath(testDir);
			Assert.assertEquals(1L, files.size());

			for (Map.Entry<File, String> fileContents : files.entrySet()) {
				Assert.assertTrue(fileContents.getKey().getName().startsWith(".part-0.inprogress."));
				Assert.assertEquals(testData1, fileContents.getValue());
			}

			recoveredStream.write(testData3.getBytes(Charset.forName("UTF-8")));
			recoveredStream.closeForCommit().commit();

			files = getFileContentByPath(testDir);
			Assert.assertEquals(1L, files.size());

			for (Map.Entry<File, String> fileContents : files.entrySet()) {
				Assert.assertEquals("part-0", fileContents.getKey().getName());
				Assert.assertEquals(testData1 + testData3, fileContents.getValue());
			}
		}
	}

	@Test
	public void testResumeCommitRecoverable() throws IOException {
		final File testDir = tempFolder.newFolder();

		final ResumableWriter writer = initilizeWriter();
		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		final ResumableWriter.CommitRecoverable recoverable;
		try (final RecoverableFsDataOutputStream stream = writer.open(path)) {
			stream.write(testData1.getBytes(Charset.forName("UTF-8")));

			recoverable = stream.closeForCommit().getRecoverable();
		}

		final SimpleVersionedSerializer<ResumableWriter.CommitRecoverable> serializer = writer.getCommitRecoverableSerializer();
		byte[] serializedCommitable = serializer.serialize(recoverable);

		// get a new serializer from a new writer to make sure that no pre-initialized state leaks in.
		final ResumableWriter newWriter = initilizeWriter();
		final ResumableWriter.CommitRecoverable recoveredCommitable =
				newWriter.getResumeRecoverableSerializer()
						.deserialize(serializer.getVersion(), serializedCommitable);

		newWriter.recoverForCommit(recoveredCommitable).commit();

		Map<File, String> files = getFileContentByPath(testDir);
		Assert.assertEquals(1L, files.size());

		for (Map.Entry<File, String> fileContents : files.entrySet()) {
			Assert.assertEquals("part-0", fileContents.getKey().getName());
			Assert.assertEquals(testData1, fileContents.getValue());
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testResumeAfterCommit() throws IOException {
		final File testDir = tempFolder.newFolder();

		final ResumableWriter writer = initilizeWriter();
		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		ResumableWriter.ResumeRecoverable recoverable;
		try (final RecoverableFsDataOutputStream stream = writer.open(path)) {
			stream.write(testData1.getBytes(Charset.forName("UTF-8")));

			recoverable = stream.persist();
			stream.write(testData2.getBytes(Charset.forName("UTF-8")));

			Map<File, String> files = getFileContentByPath(testDir);
			Assert.assertEquals(1L, files.size());

			for (Map.Entry<File, String> fileContents : files.entrySet()) {
				Assert.assertTrue(fileContents.getKey().getName().startsWith(".part-0.inprogress."));
				Assert.assertEquals(testData1 + testData2, fileContents.getValue());
			}

			stream.closeForCommit().commit();
		}

		// this should throw an exception as the file is already committed
		writer.recover(recoverable);
		fail();
	}

	@Test
	public void testResumeWithWrongOffset() throws IOException {
		// this is a rather unrealistic scenario, but it is to trigger
		// truncation of the file and try to resume with missing data.

		final File testDir = tempFolder.newFolder();

		final ResumableWriter writer = initilizeWriter();
		final Path path = new Path(testDir.getPath() + File.separator + "part-0");

		final RecoverableFsDataOutputStream stream = writer.open(path);
		stream.write(testData1.getBytes(Charset.forName("UTF-8")));

		final ResumableWriter.ResumeRecoverable recoverable1 = stream.persist();
		stream.write(testData2.getBytes(Charset.forName("UTF-8")));

		final ResumableWriter.ResumeRecoverable recoverable2 = stream.persist();
		stream.write(testData3.getBytes(Charset.forName("UTF-8")));

		try (RecoverableFsDataOutputStream ignored = writer.recover(recoverable1)) {
			// this should work fine
		} catch (Exception e) {
			fail();
		}

		// this should throw an exception
		try (RecoverableFsDataOutputStream ignored = writer.recover(recoverable2)) {
			fail();
		} catch (IOException e) {
			Assert.assertTrue(e.getMessage().startsWith("Missing data in tmp file: .part-0.inprogress."));
		}
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
