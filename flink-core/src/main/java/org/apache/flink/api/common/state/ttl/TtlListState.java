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

package org.apache.flink.api.common.state.ttl;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.util.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * This class wraps list state with TTL logic.
 *
 * @param <T> Type of the user entry value of state with TTL
 */
class TtlListState<T> extends AbstractTtlDecorator<ListState<TtlValue<T>>> implements ListState<T> {
	TtlListState(
		ListState<TtlValue<T>> originalState,
		TtlConfig config,
		TtlTimeProvider timeProvider) {
		super(originalState, config, timeProvider);
	}

	@Override
	public void update(List<T> values) throws Exception {
		Preconditions.checkNotNull(values, "List of values to add cannot be null.");
		original.update(withTs(values));
	}

	@Override
	public void addAll(List<T> values) throws Exception {
		Preconditions.checkNotNull(values, "List of values to add cannot be null.");
		original.addAll(withTs(values));
	}

	private List<TtlValue<T>> withTs(List<T> values) {
		return values.stream().map(this::wrapWithTs).collect(Collectors.toList());
	}

	@Override
	public Iterable<T> get() throws Exception {
		Iterable<TtlValue<T>> ttlValue = original.get();
		ttlValue = ttlValue == null ? Collections.emptyList() : ttlValue;
		if (updateTsOnRead) {
			List<TtlValue<T>> collected = new ArrayList<>();
			ttlValue.forEach(collected::add);
			ttlValue = collected;
			updateTs(collected);
		}
		final Iterable<TtlValue<T>> finalResult = ttlValue;
		return () -> new IteratorWithCleanup(finalResult.iterator());
	}

	private void updateTs(List<TtlValue<T>> unexpiredList) throws Exception {
		List<TtlValue<T>> unexpiredWithUpdatedTs = unexpiredList.stream()
			.filter(v -> !expired(v))
			.map(this::rewrapWithNewTs)
			.collect(Collectors.toList());
		if (!unexpiredWithUpdatedTs.isEmpty()) {
			original.update(unexpiredWithUpdatedTs);
		}
	}

	@Override
	public void add(T value) throws Exception {
		Preconditions.checkNotNull(value, "You cannot add null to a ListState.");
		original.add(wrapWithTs(value));
	}

	@Override
	public void clear() {
		original.clear();
	}

	private class IteratorWithCleanup implements Iterator<T> {
		private final Iterator<TtlValue<T>> originalIterator;
		private boolean anyUnexpired = false;

		private IteratorWithCleanup(Iterator<TtlValue<T>> ttlIterator) {
			this.originalIterator = ttlIterator;
		}

		@Override
		public boolean hasNext() {
			cleanupIfEmpty();
			return originalIterator.hasNext();
		}

		private void cleanupIfEmpty() {
			if (!anyUnexpired && !originalIterator.hasNext()) {
				original.clear();
			}
		}

		@Override
		public T next() {
			while (hasNext()) {
				TtlValue<T> ttlValue = originalIterator.next();
				if (ttlValue == null) {
					return null;
				} else if (!expired(ttlValue)) {
					anyUnexpired = true;
				} else if (!returnExpired) {
					continue;
				}
				return ttlValue.getUserValue();
			}
			throw new NoSuchElementException();
		}
	}
}
