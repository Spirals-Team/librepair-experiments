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

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TtlMapStateDecorator<UK, UV>
	extends AbstractTtlDecorator<MapState<UK, TtlValue<UV>>>
	implements MapState<UK, UV> {
	TtlMapStateDecorator(MapState<UK, TtlValue<UV>> original,
						 TtlConfig config,
						 TtlTimeProvider timeProvider) {
		super(original, config, timeProvider);
	}

	@Override
	public UV get(UK key) throws Exception {
		TtlValue<UV> ttlValue = original.get(key);
		if (ttlValue == null) {
			return null;
		}
		UV value = getUnexpried(ttlValue);
		if (value != null && updateTsOnRead) {
			original.put(key, rewrapWithNewTs(ttlValue));
		}
		return value;
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
		if (withTs == null) {
			return Stream.empty();
		}
		Stream<Map.Entry<UK, TtlValue<UV>>> unexpired = StreamSupport
			.stream(withTs.spliterator(), false)
			.filter(e -> !expired(e.getValue()));
		if (updateTsOnRead) {
			Map<UK, TtlValue<UV>> withNewTs =
				unexpired.collect(Collectors.toMap(Map.Entry::getKey, e -> rewrapWithNewTs(e.getValue())));
			original.putAll(withNewTs);
			unexpired = withNewTs.entrySet().stream();
		}
		return unexpired.map(TtlMapStateDecorator::dropTs);
	}

	private static <UK, UV> Map.Entry<UK, UV> dropTs(Map.Entry<UK, TtlValue<UV>> e) {
		return new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().getUserValue());
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
