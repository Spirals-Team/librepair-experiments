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

package org.apache.flink.runtime.query;

import org.apache.flink.annotation.Internal;
import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.runtime.state.internal.InternalKvState;
import org.apache.flink.util.Preconditions;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * An entry holding the {@link InternalKvState} along with its {@link KvStateInfo}.
 */
@Internal
public class KvStateEntry<K, N, V> {

	private final InternalKvState<K, N, V> state;
	private final KvStateInfo<K, N, V> stateInfo;

	private final Map<Thread, KvStateInfo<K, N, V>> serializerCache;

	public KvStateEntry(final InternalKvState<K, N, V> state) {

		this.state = Preconditions.checkNotNull(state);
		this.stateInfo = new KvStateInfo<>(
				state.getKeySerializer(),
				state.getNamespaceSerializer(),
				state.getValueSerializer()
		);

		this.serializerCache =
				stateInfo.duplicate() == stateInfo
						? null							// if the serializers are stateless, we do not need a cache
						: Collections.synchronizedMap(new WeakHashMap<>(4));
	}

	public InternalKvState<K, N, V> getState() {
		return state;
	}

	public KvStateInfo<K, N, V> getInfoForCurrentThread() {
		return serializerCache == null
				? stateInfo
				: serializerCache.computeIfAbsent(Thread.currentThread(), t -> stateInfo.duplicate());
	}

	public void clear() {
		serializerCache.clear();
	}

	@VisibleForTesting
	public int getCacheSize() {
		return serializerCache.size();
	}
}
