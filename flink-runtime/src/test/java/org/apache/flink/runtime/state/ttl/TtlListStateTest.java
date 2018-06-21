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

package org.apache.flink.runtime.state.ttl;

import org.apache.flink.runtime.state.internal.InternalListState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** Test suite for {@link TtlListState}. */
public class TtlListStateTest extends TtlStateTestBase<TtlListState<?, ?, Integer>, List<Integer>, Iterable<Integer>> {
	@Override
	TtlListState<?, ?, Integer> createState() {
		return new TtlListState<>(new MockInternalListState<>(), ttlConfig, timeProvider, null);
	}

	@Override
	void initTestValues() {
		updater = v -> ttlState.addAll(v);
		getter = () -> StreamSupport.stream(ttlState.get().spliterator(), false).collect(Collectors.toList());
		originalGetter = () -> ttlState.original.get();

		emptyValue = Collections.emptyList();

		updateValue1 = Arrays.asList(5, 7, 10);
		updateValue2 = Arrays.asList(8, 9, 11);

		getValue1 = updateValue1;
		getValue2 = updateValue2;
	}

	private static class MockInternalListState<K, N, T>
		extends MockInternalKvState<K, N, List<T>>
		implements InternalListState<K, N, T> {

		MockInternalListState() {
			value = new ArrayList<>();
		}

		@Override
		public void update(List<T> elements) {
			updateInternal(elements);
		}

		@Override
		public void addAll(List<T> elements) {
			value.addAll(elements);
		}

		@Override
		public void mergeNamespaces(N target, Collection<N> sources) {
			// noop
		}

		@Override
		public Iterable<T> get() {
			return getInternal();
		}

		@Override
		public void add(T element) {
			value.add(element);
		}

		@Override
		public void clear() {
			value.clear();
		}
	}
}
