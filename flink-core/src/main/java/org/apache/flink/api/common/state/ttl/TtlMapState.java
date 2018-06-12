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

import org.apache.flink.api.common.state.MapState;
import org.apache.flink.util.FlinkRuntimeException;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class wraps map state with TTL logic.
 *
 * @param <UK> Type of the user entry key of state with TTL
 * @param <UV> Type of the user entry value of state with TTL
 */
class TtlMapState<UK, UV>
	extends AbstractTtlDecorator<MapState<UK, TtlValue<UV>>>
	implements MapState<UK, UV> {
	TtlMapState(
		MapState<UK, TtlValue<UV>> original,
		TtlConfig config,
		TtlTimeProvider timeProvider) {
		super(original, config, timeProvider);
	}

	@Override
	public UV get(UK key) throws Exception {
		TtlValue<UV> ttlValue = original.get(key);
		if (ttlValue == null) {
			return null;
		} else if (expired(ttlValue)) {
			original.remove(key);
			if (!returnExpired) {
				return null;
			}
		} else if (updateTsOnRead) {
			original.put(key, rewrapWithNewTs(ttlValue));
		}
		return ttlValue.getUserValue();
	}

	@Override
	public void put(UK key, UV value) throws Exception {
		original.put(key, wrapWithTs(value));
	}

	@Override
	public void putAll(Map<UK, UV> map) throws Exception {
		if (map == null) {
			return;
		}
		Map<UK, TtlValue<UV>> ttlMap = map.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> wrapWithTs(e.getValue())));
		original.putAll(ttlMap);
	}

	@Override
	public void remove(UK key) throws Exception {
		original.remove(key);
	}

	@Override
	public boolean contains(UK key) throws Exception {
		return get(key) != null;
	}

	@Override
	public Iterable<Map.Entry<UK, UV>> entries() throws Exception {
		return entriesStream()::iterator;
	}

	private Stream<Map.Entry<UK, UV>> entriesStream() throws Exception {
		Iterable<Map.Entry<UK, TtlValue<UV>>> withTs = original.entries();
		withTs = withTs == null ? Collections.emptyList() : withTs;
		return StreamSupport
			.stream(withTs.spliterator(), false)
			.filter(this::unexpiredAndUpdateOrCleanup)
			.map(TtlMapState::dropTs);
	}

	private static <UK, UV> Map.Entry<UK, UV> dropTs(Map.Entry<UK, TtlValue<UV>> e) {
		return new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().getUserValue());
	}

	private boolean unexpiredAndUpdateOrCleanup(Map.Entry<UK, TtlValue<UV>> e) {
		boolean hasExpired = expired(e.getValue());
		try {
			if (hasExpired) {
				original.remove(e.getKey());
			} else if (updateTsOnRead) {
				original.put(e.getKey(), rewrapWithNewTs(e.getValue()));
			}
		} catch (Exception ex) {
			throw new FlinkRuntimeException(ex);
		}
		return !hasExpired || returnExpired;
	}

	@Override
	public Iterable<UK> keys() throws Exception {
		return entriesStream().map(Map.Entry::getKey)::iterator;
	}

	@Override
	public Iterable<UV> values() throws Exception {
		return entriesStream().map(Map.Entry::getValue)::iterator;
	}

	@Override
	public Iterator<Map.Entry<UK, UV>> iterator() throws Exception {
		return entriesStream().iterator();
	}

	@Override
	public void clear() {
		original.clear();
	}
}
