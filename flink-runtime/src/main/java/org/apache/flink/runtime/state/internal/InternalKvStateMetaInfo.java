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

import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.util.Preconditions;

/**
 * Metadata about a {@link InternalKvState}. This includes the serializers for
 * the key, the namespace, and the values kept in the state.
 *
 * @param <K>	The type of key the state is associated to
 * @param <N>	The type of the namespace
 * @param <V>	The type of values kept internally in state
 */
public class InternalKvStateMetaInfo<K, N, V> {

	private final TypeSerializer<K> keySerializer;
	private final TypeSerializer<N> namespaceSerializer;
	private final TypeSerializer<V> stateValueSerializer;

	public InternalKvStateMetaInfo(
			final TypeSerializer<K> keySerializer,
			final TypeSerializer<N> namespaceSerializer,
			final TypeSerializer<V> stateValueSerializer
	) {
		this.keySerializer = Preconditions.checkNotNull(keySerializer);
		this.namespaceSerializer = Preconditions.checkNotNull(namespaceSerializer);
		this.stateValueSerializer = Preconditions.checkNotNull(stateValueSerializer);
	}

	/**
	 * @return The serializer for the key the state is associated to.
	 */
	public TypeSerializer<K> getKeySerializer() {
		return keySerializer;
	}

	/**
	 * @return The serializer for the namespace the state is associated to.
	 */
	public TypeSerializer<N> getNamespaceSerializer() {
		return namespaceSerializer;
	}

	/**
	 * @return The serializer for the values kept in the state.
	 */
	public TypeSerializer<V> getStateValueSerializer() {
		return stateValueSerializer;
	}
}
