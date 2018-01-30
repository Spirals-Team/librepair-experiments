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

package org.apache.flink.api.common.state;

import org.apache.flink.annotation.PublicEvolving;

import java.util.Map;

/**
 * A read-only type of state that gives read-only access to the state of a {@code BroadcastStream}
 * (see {@link ReadWriteBroadcastState}).
 *
 * @param <K> The key type of the elements in the {@link ReadOnlyBroadcastState}.
 * @param <V> The value type of the elements in the {@link ReadOnlyBroadcastState}.
 */
@PublicEvolving
public interface ReadOnlyBroadcastState<K, V> extends State {

	/**
	 * Returns the current value associated with the given key.
	 *
	 * @param key The key of the mapping
	 * @return The value of the mapping with the given key
	 *
	 * @throws Exception Thrown if the system cannot access the state.
	 */
	V get(K key) throws Exception;

	/**
	 * Returns whether there exists the given mapping.
	 *
	 * @param key The key of the mapping
	 * @return True if there exists a mapping whose key equals to the given key
	 *
	 * @throws Exception Thrown if the system cannot access the state.
	 */
	boolean contains(K key) throws Exception;

	/**
	 * Returns an immutable {@link Iterable} over the entries in the state.
	 */
	Iterable<Map.Entry<K, V>> immutableEntries() throws Exception;
}
