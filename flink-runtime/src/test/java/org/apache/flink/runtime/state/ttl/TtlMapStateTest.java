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

import org.apache.flink.api.java.tuple.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** Test suite for collection methods of {@link TtlMapState}. */
public class TtlMapStateTest extends
	TtlStateTestBase<TtlMapState<?, ?, Integer, String>, Map<Integer, String>, Set<Map.Entry<Integer, String>>> {

	@Override
	TtlMapState<?, ?, Integer, String> createState() {
		return new TtlMapState<>(new MockInternalMapState<>(), ttlConfig, timeProvider, null);
	}

	@Override
	void initTestValues() {
		updater = map -> ttlState.putAll(map);
		getter = () -> StreamSupport.stream(ttlState.entries().spliterator(), false).collect(Collectors.toSet());
		originalGetter = () -> ttlState.original.entries();

		emptyValue = Collections.emptySet();

		updateValue1 = mapOf(Tuple2.of(3, "3"), Tuple2.of(5, "5"), Tuple2.of(10, "10"));
		updateValue2 = mapOf(Tuple2.of(12, "12"), Tuple2.of(7, "7"));

		getValue1 = updateValue1.entrySet();
		getValue2 = updateValue2.entrySet();
	}

	@SafeVarargs
	private static <UK, UV> Map<UK, UV> mapOf(Tuple2<UK, UV> ... entries) {
		return Arrays.stream(entries).collect(Collectors.toMap(t -> t.f0, t -> t.f1));
	}
}
