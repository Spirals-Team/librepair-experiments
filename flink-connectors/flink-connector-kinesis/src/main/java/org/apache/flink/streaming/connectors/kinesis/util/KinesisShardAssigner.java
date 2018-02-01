package org.apache.flink.streaming.connectors.kinesis.util;

import org.apache.flink.streaming.connectors.kinesis.model.StreamShardHandle;

import java.io.Serializable;

/**
 * Utility to map Kinesis shards to Flink subtask indices.
 */
public interface KinesisShardAssigner extends Serializable {
    /**
	 * Returns the index of the target subtask that a specific Kafka partition should be
	 * assigned to. For return values outside the subtask range, modulus operation will
	 * be applied automatically, hence it is also valid to just return a hash code.
	 *
	 * <p>The resulting distribution of shards has the following contract:
	 * <ul>
	 *     <li>1. Uniform distribution across subtasks</li>
	 *     <li>2. Deterministic, calls for a given shard always return same index.</li>
	 * </ul>
	 *
	 * <p>The above contract is crucial and cannot be broken. Consumer subtasks rely on this
	 * contract to filter out partitions that they should not subscribe to, guaranteeing
	 * that all partitions of a single topic will always be assigned to some subtask in a
	 * uniformly distributed manner.
	 *
	 * <p>Kinesis and the consumer support dynamic re-sharding and shard IDs, while sequential,
	 * cannot be assumed to be consecutive. There is no perfect generic default assignment function.
	 * Default subtask index assignment, which is based on hash code, may result in skew,
	 * with some subtasks having many shards assigned and others none.
	 *
	 * <p>It is recommended to monitor the shard distribution and adjust assignment appropriately.
	 * Custom implementation may optimize the hash function or use static overrides to limit skew.
     *
     * @param shard the shard to determine
     * @param numParallelSubtasks total number of subtasks
     * @return index or hash code
     */
	int assign(StreamShardHandle shard, int numParallelSubtasks);
}
