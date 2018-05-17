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

package org.apache.flink.cep.utils;

import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.state.FoldingState;
import org.apache.flink.api.common.state.FoldingStateDescriptor;
import org.apache.flink.api.common.state.KeyedStateStore;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.cep.nfa.sharedbuffer.SharedBuffer;

import org.apache.flink.shaded.guava18.com.google.common.collect.Iterators;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestSharedBuffer {

	public static <T> SharedBuffer<T> createTestBuffer(TypeSerializer<T> typeSerializer) {
		return new SharedBuffer<>(new MockKeyedStateStore(), typeSerializer);
	}

	private static class MockKeyedStateStore implements KeyedStateStore {

		@Override
		public <T> ValueState<T> getState(ValueStateDescriptor<T> stateProperties) {
			return new ValueState<T>() {

				private T value;

				@Override
				public T value() throws IOException {
					return value;
				}

				@Override
				public void update(T value) throws IOException {
					this.value = value;
				}

				@Override
				public void clear() {
					this.value = null;
				}
			};
		}

		@Override
		public <T> ListState<T> getListState(ListStateDescriptor<T> stateProperties) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> ReducingState<T> getReducingState(ReducingStateDescriptor<T> stateProperties) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <IN, ACC, OUT> AggregatingState<IN, OUT> getAggregatingState(AggregatingStateDescriptor<IN, ACC, OUT> stateProperties) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T, ACC> FoldingState<T, ACC> getFoldingState(FoldingStateDescriptor<T, ACC> stateProperties) {
			throw new UnsupportedOperationException();
		}

		@Override
		public <UK, UV> MapState<UK, UV> getMapState(MapStateDescriptor<UK, UV> stateProperties) {
			return new MapState<UK, UV>() {

				private Map<UK, UV> values;

				private Map<UK, UV> getOrSetMap() {
					if (values == null) {
						this.values = new HashMap<>();
					}
					return values;
				}

				@Override
				public UV get(UK key) throws Exception {
					if (values == null) {
						return null;
					}

					return values.get(key);
				}

				@Override
				public void put(UK key, UV value) throws Exception {
					getOrSetMap().put(key, value);
				}

				@Override
				public void putAll(Map<UK, UV> map) throws Exception {
					getOrSetMap().putAll(map);
				}

				@Override
				public void remove(UK key) throws Exception {
					if (values == null) {
						return;
					}

					values.remove(key);
				}

				@Override
				public boolean contains(UK key) throws Exception {
					if (values == null) {
						return false;
					}

					return values.containsKey(key);
				}

				@Override
				public Iterable<Map.Entry<UK, UV>> entries() throws Exception {
					if (values == null) {
						return Collections.emptyList();
					}

					return values.entrySet();
				}

				@Override
				public Iterable<UK> keys() throws Exception {
					if (values == null) {
						return Collections.emptyList();
					}

					return values.keySet();
				}

				@Override
				public Iterable<UV> values() throws Exception {
					if (values == null) {
						return Collections.emptyList();
					}

					return values.values();
				}

				@Override
				public Iterator<Map.Entry<UK, UV>> iterator() throws Exception {
					if (values == null) {
						return Iterators.emptyIterator();
					}

					return values.entrySet().iterator();
				}

				@Override
				public void clear() {
					this.values = null;
				}
			};
		}
	}

	private TestSharedBuffer() {
	}
}
