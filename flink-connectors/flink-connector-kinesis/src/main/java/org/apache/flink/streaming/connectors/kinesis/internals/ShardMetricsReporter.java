package org.apache.flink.streaming.connectors.kinesis.internals;

import org.apache.flink.annotation.Internal;

/**
 * A container for {@link ShardConsumer}s to report metric values.
 */
@Internal
public class ShardMetricsReporter {

	private volatile long millisBehindLatest = -1;

	public long getMillisBehindLatest() {
		return millisBehindLatest;
	}

	public void setMillisBehindLatest(long millisBehindLatest) {
		this.millisBehindLatest = millisBehindLatest;
	}

}
