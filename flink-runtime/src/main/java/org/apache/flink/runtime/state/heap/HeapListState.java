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

package org.apache.flink.runtime.state.heap;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.memory.DataOutputViewStreamWrapper;
import org.apache.flink.queryablestate.client.state.serialization.KvStateSerializer;
import org.apache.flink.runtime.state.internal.InternalListState;
import org.apache.flink.util.Preconditions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Heap-backed partitioned {@link org.apache.flink.api.common.state.ListState} that is snapshotted
 * into files.
 *
 * @param <K> The type of the key.
 * @param <N> The type of the namespace.
 * @param <V> The type of the value.
 */
public class HeapListState<K, N, V>
		extends AbstractHeapMergingState<K, N, V, Iterable<V>, List<V>, ListState<V>, ListStateDescriptor<V>>
		implements InternalListState<N, V> {

	/**
	 * Creates a new key/value state for the given hash map of key/value pairs.
	 *
	 * @param stateDesc The state identifier for the state. This contains name
	 *                           and can create a default state value.
	 * @param stateTable The state tab;e to use in this kev/value state. May contain initial state.
	 */
	public HeapListState(
			ListStateDescriptor<V> stateDesc,
			StateTable<K, N, List<V>> stateTable,
			TypeSerializer<K> keySerializer,
			TypeSerializer<N> namespaceSerializer) {
		super(stateDesc, stateTable, keySerializer, namespaceSerializer);
	}

	// ------------------------------------------------------------------------
	//  state access
	// ------------------------------------------------------------------------

	@Override
	public Iterable<V> get() {
		return stateTable.get(currentNamespace);
	}

	@Override
	public void add(V value) {
		Preconditions.checkNotNull(value, "You cannot add null to a ListState.");

		final N namespace = currentNamespace;

		final StateTable<K, N, List<V>> map = stateTable;
		List<V> list = map.get(namespace);

		if (list == null) {
			list = new ArrayList<>();
			map.put(namespace, list);
		}
		list.add(value);
	}

	@Override
	public byte[] getSerializedValue(byte[] serializedKeyAndNamespace) throws Exception {
		Preconditions.checkNotNull(serializedKeyAndNamespace, "Serialized key and namespace");

		final TypeSerializer<K> dupKeySerializer = keySerializer.duplicate();
		final TypeSerializer<N> dupNamespaceSerializer = namespaceSerializer.duplicate();

		Tuple2<K, N> keyAndNamespace = KvStateSerializer.deserializeKeyAndNamespace(
				serializedKeyAndNamespace, dupKeySerializer, dupNamespaceSerializer);

		List<V> result = stateTable.get(keyAndNamespace.f0, keyAndNamespace.f1);

		if (result == null) {
			return null;
		}

		final TypeSerializer<V> dupSerializer = stateDesc.getElementSerializer().duplicate();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputViewStreamWrapper view = new DataOutputViewStreamWrapper(baos);

		// write the same as RocksDB writes lists, with one ',' separator
		for (int i = 0; i < result.size(); i++) {
			dupSerializer.serialize(result.get(i), view);
			if (i < result.size() -1) {
				view.writeByte(',');
			}
		}
		view.flush();

		return baos.toByteArray();
	}

	// ------------------------------------------------------------------------
	//  state merging
	// ------------------------------------------------------------------------

	@Override
	protected List<V> mergeState(List<V> a, List<V> b) {
		a.addAll(b);
		return a;
	}

	@Override
	public void update(List<V> values) throws Exception {
		Preconditions.checkNotNull(values, "List of values to add cannot be null.");

		if (values.isEmpty()) {
			clear();
			return;
		}

		List<V> newStateList = new ArrayList<>();
		for (V v : values) {
			Preconditions.checkNotNull(v, "You cannot add null to a ListState.");
			newStateList.add(v);
		}

		stateTable.put(currentNamespace, newStateList);
	}

	@Override
	public void addAll(List<V> values) throws Exception {
		Preconditions.checkNotNull(values, "List of values to add cannot be null.");

		if (!values.isEmpty()) {
			stateTable.transform(currentNamespace, values, (previousState, value) -> {
				if (previousState == null) {
					previousState = new ArrayList<>();
				}
				for (V v : value) {
					Preconditions.checkNotNull(v, "You cannot add null to a ListState.");
					previousState.add(v);
				}
				return previousState;
			});
		}
	}
}
