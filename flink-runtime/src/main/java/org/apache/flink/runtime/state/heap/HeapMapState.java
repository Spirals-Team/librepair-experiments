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

import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.queryablestate.client.state.serialization.KvStateSerializer;
import org.apache.flink.runtime.state.HashMapSerializer;
import org.apache.flink.runtime.state.internal.InternalMapState;
import org.apache.flink.util.Preconditions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Heap-backed partitioned {@link MapState} that is snapshotted into files.
 *
 * @param <K> The type of the key.
 * @param <N> The type of the namespace.
 * @param <UK> The type of the keys in the state.
 * @param <UV> The type of the values in the state.
 */
public class HeapMapState<K, N, UK, UV>
		extends AbstractHeapState<K, N, HashMap<UK, UV>, MapState<UK, UV>, MapStateDescriptor<UK, UV>>
		implements InternalMapState<K, N, UK, UV, HashMap<UK, UV>> {

	private final TypeSerializer<UK> userKeySerializer;

	private final TypeSerializer<UV> userValueSerializer;

	/**
	 * Creates a new key/value state for the given hash map of key/value pairs.
	 *
	 * @param valueSerializer  The serializer for the state.
	 * @param stateTable The state tab;e to use in this kev/value state. May contain initial state.
	 */
	public HeapMapState(
			StateTable<K, N, HashMap<UK, UV>> stateTable,
			TypeSerializer<K> keySerializer,
			TypeSerializer<HashMap<UK, UV>> valueSerializer,
			TypeSerializer<N> namespaceSerializer,
			HashMap<UK, UV> defaultValue) {
		super(stateTable, keySerializer, valueSerializer, namespaceSerializer, defaultValue);

		Preconditions.checkState(valueSerializer instanceof HashMapSerializer, "Unexpected serializer type.");

		HashMapSerializer<UK, UV> castedMapSerializer = (HashMapSerializer<UK, UV>) valueSerializer;
		this.userKeySerializer = castedMapSerializer.getKeySerializer();
		this.userValueSerializer = castedMapSerializer.getValueSerializer();
	}

	@Override
	public TypeSerializer<K> getKeySerializer() {
		return keySerializer;
	}

	@Override
	public TypeSerializer<N> getNamespaceSerializer() {
		return namespaceSerializer;
	}

	@Override
	public TypeSerializer<HashMap<UK, UV>> getValueSerializer() {
		return new HashMapSerializer<>(
				userKeySerializer,
				userValueSerializer
		);
	}

	@Override
	public UV get(UK userKey) {

		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);

		if (userMap == null) {
			return null;
		}
		
		return userMap.get(userKey);
	}

	@Override
	public void put(UK userKey, UV userValue) {

		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		if (userMap == null) {
			userMap = new HashMap<>();
			stateTable.put(currentNamespace, userMap);
		}

		userMap.put(userKey, userValue);
	}

	@Override
	public void putAll(Map<UK, UV> value) {

		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);

		if (userMap == null) {
			userMap = new HashMap<>();
			stateTable.put(currentNamespace, userMap);
		}

		userMap.putAll(value);
	}

	@Override
	public void remove(UK userKey) {

		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		if (userMap == null) {
			return;
		}

		userMap.remove(userKey);

		if (userMap.isEmpty()) {
			clear();
		}
	}

	@Override
	public boolean contains(UK userKey) {
		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		return userMap != null && userMap.containsKey(userKey);
	}

	@Override
	public Iterable<Map.Entry<UK, UV>> entries() {
		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		return userMap == null ? null : userMap.entrySet();
	}
	
	@Override
	public Iterable<UK> keys() {
		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		return userMap == null ? null : userMap.keySet();
	}

	@Override
	public Iterable<UV> values() {
		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		return userMap == null ? null : userMap.values();
	}

	@Override
	public Iterator<Map.Entry<UK, UV>> iterator() {
		HashMap<UK, UV> userMap = stateTable.get(currentNamespace);
		return userMap == null ? null : userMap.entrySet().iterator();
	}

	@Override
	public byte[] getSerializedValue(
			final byte[] serializedKeyAndNamespace,
			final TypeSerializer<K> safeKeySerializer,
			final TypeSerializer<N> safeNamespaceSerializer,
			final TypeSerializer<HashMap<UK, UV>> safeValueSerializer) throws Exception {

		Preconditions.checkNotNull(serializedKeyAndNamespace);
		Preconditions.checkNotNull(safeKeySerializer);
		Preconditions.checkNotNull(safeNamespaceSerializer);
		Preconditions.checkNotNull(safeValueSerializer);

		Tuple2<K, N> keyAndNamespace = KvStateSerializer.deserializeKeyAndNamespace(
				serializedKeyAndNamespace, safeKeySerializer, safeNamespaceSerializer);

		Map<UK, UV> result = stateTable.get(keyAndNamespace.f0, keyAndNamespace.f1);

		if (result == null) {
			return null;
		}

		final HashMapSerializer<UK, UV> serializer = (HashMapSerializer<UK, UV>) safeValueSerializer;

		final TypeSerializer<UK> dupUserKeySerializer = serializer.getKeySerializer();
		final TypeSerializer<UV> dupUserValueSerializer = serializer.getValueSerializer();

		return KvStateSerializer.serializeMap(result.entrySet(), dupUserKeySerializer, dupUserValueSerializer);
	}
}
