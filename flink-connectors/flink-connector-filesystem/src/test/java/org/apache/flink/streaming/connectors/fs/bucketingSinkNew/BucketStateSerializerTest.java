package org.apache.flink.streaming.connectors.fs.bucketingSinkNew;

import org.apache.flink.core.fs.FileStatus;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.fs.RecoverableFsDataOutputStream;
import org.apache.flink.core.fs.ResumableWriter;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.connectors.fs.bucketing_new.Bucket;
import org.apache.flink.streaming.connectors.fs.bucketing_new.BucketStateSerializer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 7/3/18 make it nicer. 
public class BucketStateSerializerTest {

	private static final String IN_PROGRESS_CONTENT = "writing";
	private static final String PENDING_CONTENT = "wrote";

	@ClassRule
	public static TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testSerializationEmpty() throws IOException {
		final File testFolder = tempFolder.newFolder();
		final FileSystem fs = FileSystem.get(testFolder.toURI());
		final ResumableWriter writer = fs.createRecoverableWriter();

		final Path testBucket = new Path(testFolder.getPath() + "/test.bucket/part-0-0");

		final ResumableWriter.ResumeRecoverable current = null;
		final Bucket.BucketState bucketState = new Bucket.BucketState(testBucket, current, new HashMap<>());

		final BucketStateSerializer serializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);
		byte[] bytes = serializer.serialize(bucketState);

		final BucketStateSerializer deSerializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);

		final Bucket.BucketState recoveredState =  deSerializer.deserialize(1, bytes);
		Assert.assertEquals(testBucket, recoveredState.getBucketPath());
		Assert.assertNull(recoveredState.getCurrentInProgress());
		Assert.assertTrue(recoveredState.getPendingPerCheckpoint().isEmpty());
	}

	@Test
	public void testSerializationOnlyInProgress() throws IOException {
		final File testFolder = tempFolder.newFolder();
		final FileSystem fs = FileSystem.get(testFolder.toURI());

		final Path testBucket = new Path(testFolder.getPath() + "/test.bucket/part-0-0");

		final ResumableWriter writer = fs.createRecoverableWriter();
		final RecoverableFsDataOutputStream stream = writer.open(testBucket);
		stream.write(IN_PROGRESS_CONTENT.getBytes(Charset.forName("UTF-8")));

		final ResumableWriter.ResumeRecoverable current = stream.persist();

		final Bucket.BucketState bucketState = new Bucket.BucketState(testBucket, current, new HashMap<>());

		final BucketStateSerializer serializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);
		byte[] bytes = serializer.serialize(bucketState);

		// to simulate that everything is over for file.
		stream.close();

		final BucketStateSerializer deSerializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);

		final Bucket.BucketState recoveredState =  deSerializer.deserialize(1, bytes);
		Assert.assertEquals(testBucket, recoveredState.getBucketPath());

		FileStatus[] statuses = fs.listStatus(testBucket.getParent());
		for (int i = 0; i < statuses.length; i++) {
			System.out.println(statuses[i].getPath());
		}
	}

	@Test
	public void testSerializationFull() throws IOException {
		final File testFolder = tempFolder.newFolder();
		final FileSystem fs = FileSystem.get(testFolder.toURI());
		final ResumableWriter writer = fs.createRecoverableWriter();

		final Path bucketPath = new Path(testFolder.getPath());

		final Map<Long, List<ResumableWriter.CommitRecoverable>> commitRecoverables = new HashMap<>();
		for (long i = 0L; i < 5L; i++) {
			final List<ResumableWriter.CommitRecoverable> recoverables = new ArrayList<>();
			for (int j = 0; j < 2; j++) {
				final Path part = new Path(bucketPath + "/part-" + i + "-" + j);

				final RecoverableFsDataOutputStream stream = writer.open(part);
				stream.write((PENDING_CONTENT + "-" + j).getBytes(Charset.forName("UTF-8")));
				recoverables.add(stream.closeForCommit().getRecoverable());
			}
			commitRecoverables.put(i, recoverables);
		}

		final Path testBucket = new Path(bucketPath + "/part-0-" + 6);
		final RecoverableFsDataOutputStream stream = writer.open(testBucket);
		stream.write(IN_PROGRESS_CONTENT.getBytes(Charset.forName("UTF-8")));

		final ResumableWriter.ResumeRecoverable current = stream.persist();

		final Bucket.BucketState bucketState = new Bucket.BucketState(bucketPath, current, commitRecoverables);
		final SimpleVersionedSerializer<Bucket.BucketState> serializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);
		stream.close();

		byte[] bytes = serializer.serialize(bucketState);

		final SimpleVersionedSerializer<Bucket.BucketState> deSerializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);

		final Bucket.BucketState recoveredState =  deSerializer.deserialize(1, bytes);

		Assert.assertEquals(bucketPath, recoveredState.getBucketPath());

		final Map<Long, List<ResumableWriter.CommitRecoverable>> recoveredRecoverables = recoveredState.getPendingPerCheckpoint();
		Assert.assertEquals(5, recoveredRecoverables.size());

		for (Map.Entry<Long, List<ResumableWriter.CommitRecoverable>> entry: recoveredRecoverables.entrySet()) {
			final long checkpointId = entry.getKey();
			final List<ResumableWriter.CommitRecoverable> recoverables = entry.getValue();

			System.out.println(checkpointId);
			for (ResumableWriter.CommitRecoverable recoverable: recoverables) {
				writer.recoverForCommit(recoverable).commit();
			}
		}

		FileStatus[] statuses = fs.listStatus(bucketPath);
		for (int i = 0; i < statuses.length; i++) {
			System.out.println(statuses[i].getPath());
		}
	}

	@Test
	public void testSerializationNullInProgress() throws IOException {
		final File testFolder = tempFolder.newFolder();
		final FileSystem fs = FileSystem.get(testFolder.toURI());
		final ResumableWriter writer = fs.createRecoverableWriter();

		final Path bucketPath = new Path(testFolder.getPath());

		final Map<Long, List<ResumableWriter.CommitRecoverable>> commitRecoverables = new HashMap<>();
		for (long i = 0L; i < 5L; i++) {
			final List<ResumableWriter.CommitRecoverable> recoverables = new ArrayList<>();
			for (int j = 0; j < 2; j++) {
				final Path part = new Path(bucketPath + "/part-" + i + "-" + j);

				final RecoverableFsDataOutputStream stream = writer.open(part);
				stream.write((PENDING_CONTENT + "-" + j).getBytes(Charset.forName("UTF-8")));
				recoverables.add(stream.closeForCommit().getRecoverable());
			}
			commitRecoverables.put(i, recoverables);
		}

		final ResumableWriter.ResumeRecoverable current = null;

		final Bucket.BucketState bucketState = new Bucket.BucketState(bucketPath, current, commitRecoverables);
		final SimpleVersionedSerializer<Bucket.BucketState> serializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);

		byte[] bytes = serializer.serialize(bucketState);

		final SimpleVersionedSerializer<Bucket.BucketState> deSerializer = new BucketStateSerializer(
				writer.getResumeRecoverableSerializer(),
				writer.getCommitRecoverableSerializer()
		);

		final Bucket.BucketState recoveredState =  deSerializer.deserialize(1, bytes);

		Assert.assertEquals(bucketPath, recoveredState.getBucketPath());
		Assert.assertNull(recoveredState.getCurrentInProgress());

		final Map<Long, List<ResumableWriter.CommitRecoverable>> recoveredRecoverables = recoveredState.getPendingPerCheckpoint();
		Assert.assertEquals(5, recoveredRecoverables.size());

		for (Map.Entry<Long, List<ResumableWriter.CommitRecoverable>> entry: recoveredRecoverables.entrySet()) {
			final long checkpointId = entry.getKey();
			final List<ResumableWriter.CommitRecoverable> recoverables = entry.getValue();

			System.out.println(checkpointId);
			for (ResumableWriter.CommitRecoverable recoverable: recoverables) {
				writer.recoverForCommit(recoverable).commit();
			}
		}

		FileStatus[] statuses = fs.listStatus(bucketPath);
		for (int i = 0; i < statuses.length; i++) {
			System.out.println(statuses[i].getPath());
		}
	}
}
