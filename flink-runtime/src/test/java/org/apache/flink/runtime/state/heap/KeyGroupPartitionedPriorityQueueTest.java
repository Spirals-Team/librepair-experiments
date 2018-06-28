package org.apache.flink.runtime.state.heap;

import org.apache.flink.runtime.state.InternalPriorityQueue;
import org.apache.flink.runtime.state.InternalPriorityQueueTestBase;

/**
 * Test for {@link KeyGroupPartitionedPriorityQueue}.
 */
public class KeyGroupPartitionedPriorityQueueTest extends InternalPriorityQueueTestBase {

	@Override
	protected InternalPriorityQueue<TestElement> newPriorityQueue(int initialCapacity) {
		return new KeyGroupPartitionedPriorityQueue<>(
			KEY_EXTRACTOR_FUNCTION,
			TEST_ELEMENT_COMPARATOR,
			newFactory(initialCapacity),
			KEY_GROUP_RANGE, KEY_GROUP_RANGE.getNumberOfKeyGroups());
	}

	protected KeyGroupPartitionedPriorityQueue.PartitionQueueSetFactory<
			TestElement, CachingInternalPriorityQueueSet<TestElement>> newFactory(int initialCapacity) {

		return (keyGroupId, elementComparator) -> {
			CachingInternalPriorityQueueSet.OrderedSetCache<TestElement> cache =
				new TreeOrderedSetCache<>(TEST_ELEMENT_COMPARATOR, 4);
			CachingInternalPriorityQueueSet.OrderedSetStore<TestElement> store =
				new TestOrderedStore<>(TEST_ELEMENT_COMPARATOR);
			return new CachingInternalPriorityQueueSet<>(cache, store);
		};
	}

	@Override
	protected boolean testSetSemantics() {
		return true;
	}
}
