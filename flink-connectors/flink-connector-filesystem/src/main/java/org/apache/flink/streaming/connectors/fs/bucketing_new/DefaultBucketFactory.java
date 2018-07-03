package org.apache.flink.streaming.connectors.fs.bucketing_new;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.connectors.fs.bucketing_new.writers.Writer;

import java.io.IOException;

public class DefaultBucketFactory<IN> implements BucketFactory<IN> {

	private static final long serialVersionUID = 3372881359208513357L;

	@Override
	public Bucket<IN> getNewBucket(
			FileSystem fileSystem,
			int subtaskIndex,
			Path bucketPath,
			long initialPartCounter,
			long maxPartSize,
			long rolloverTime,
			long inactivityTime,
			Writer<IN> writer) throws IOException {

		return new Bucket<>(
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
	public Bucket<IN> getRestoredBucket(
			FileSystem fileSystem,
			int subtaskIndex,
			Path bucketPath,
			long initialPartCounter,
			long maxPartSize,
			long rolloverTime,
			long inactivityTime,
			Writer<IN> writer,
			Bucket.BucketState bucketstate) throws IOException {

		return new Bucket<>(
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
}
