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

package org.apache.flink.runtime.state.internal;

import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.runtime.query.KvStateInfo;
import org.apache.flink.util.Preconditions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * An abstract base class to be subclassed by states that are expected to be queryable.
 * Its main task is to keep a "thread-local" copy of the different serializers (if needed).
 *
 * @param <K>	The type of key the state is associated to
 * @param <N>	The type of the namespace the state is associated to
 * @param <V>	The type of values kept internally in state
 */
public abstract class InternalQueryableKvState<K, N, V> implements InternalKvState<N> {

	private final KvStateInfo<K, N, V> stateInfo;
	private final boolean areSerializersStateless;

	private final ConcurrentMap<Thread, KvStateInfo<K, N, V>> serializerCache = new ConcurrentHashMap<>(4);

	public InternalQueryableKvState(
			final TypeSerializer<K> keySerializer,
			final TypeSerializer<N> namespaceSerializer,
			final TypeSerializer<V> valueSerializer
	) {
		this.stateInfo = new KvStateInfo<>(
				Preconditions.checkNotNull(keySerializer),
				Preconditions.checkNotNull(namespaceSerializer),
				Preconditions.checkNotNull(valueSerializer)
		);
		this.areSerializersStateless = stateInfo == stateInfo.duplicate();
	}

	public KvStateInfo<K, N, V> getInfoForCurrentThread() {
		return areSerializersStateless
				? stateInfo
				: serializerCache.computeIfAbsent(Thread.currentThread(), t -> stateInfo.duplicate());
	}

	/**
	 * Returns the serialized value for the given key and namespace.
	 *
	 * <p>If no value is associated with key and namespace, <code>null</code> is returned.
	 *
	 * <p><b>TO IMPLEMENTERS:</b> This method is called by multiple threads.
	 *
	 * @param serializedKeyAndNamespace Serialized key and namespace
	 * @return Serialized value or <code>null</code> if no value is associated with the key and namespace.
	 *
	 * @throws Exception Exceptions during serialization are forwarded
	 */
	public abstract byte[] getSerializedValue(byte[] serializedKeyAndNamespace) throws Exception;

	public void clearCache() {
		serializerCache.clear();
	}

	@VisibleForTesting
	public int getCacheSize() {
		return serializerCache.size();
	}
}
