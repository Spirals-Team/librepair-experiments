package org.apache.flink.streaming.connectors.kinesis.internals;

import org.apache.flink.annotation.Internal;

/**
 * A collection of consumer metric related constant names.
 *
 * <p>The names must not be changed, as that would break backwards compatibility for the consumer metrics.
 */
@Internal
public class KinesisConsumerMetricConstants {

	public static final String KINESIS_CONSUMER_METRICS_GROUP = "KinesisConsumer";

	public static final String STREAM_METRICS_GROUP = "stream";
	public static final String SHARD_METRICS_GROUP = "shardId";

	public static final String MILLIS_BEHIND_LATEST_GAUGE = "millisBehindLatest";
}
