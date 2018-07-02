package org.apache.flink.streaming.connectors.fs.bucketing_new.bucketers;

import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.connectors.fs.Clock;

import java.io.Serializable;

public interface Bucketer<T> extends Serializable {

	/**
	 * Returns the {@link Path} of a bucket file.
	 *
	 * @param basePath The base path containing all the buckets.
	 * @param element The current element being processed.
	 *
	 * @return The complete {@code Path} of the bucket which the provided element should fall in. This
	 * should include the {@code basePath} and also the {@code subtaskIndex} to avoid clashes with
	 * parallel sinks.
	 */
	Path getBucketPath(Clock clock, Path basePath, T element);
}
