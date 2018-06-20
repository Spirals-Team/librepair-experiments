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

import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.runtime.state.internal.InternalKvState;

class MockInternalKvState<K, N, T> implements InternalKvState<K, N, T> {
	protected T value = null;

	@Override
	public TypeSerializer<K> getKeySerializer() {
		return null;
	}

	@Override
	public TypeSerializer<N> getNamespaceSerializer() {
		return null;
	}

	@Override
	public TypeSerializer<T> getValueSerializer() {
		return null;
	}

	@Override
	public void setCurrentNamespace(Object namespace) {

	}

	@Override
	public byte[] getSerializedValue(
		byte[] serializedKeyAndNamespace,
		TypeSerializer safeKeySerializer,
		TypeSerializer safeNamespaceSerializer,
		TypeSerializer safeValueSerializer) {
		return null;
	}

	@Override
	public void clear() {
		value = null;
	}

	public T getInternal() {
		return value;
	}

	@SuppressWarnings("WeakerAccess")
	public void updateInternal(T valueToStore) {
		value = valueToStore;
	}
}
