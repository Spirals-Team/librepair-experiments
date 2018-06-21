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

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.runtime.state.internal.InternalAggregatingState;

import java.util.Collection;

/** Test suite for {@link TtlAggregatingState}. */
public class TtlAggregatingStateTest extends TtlStateTestBase<TtlAggregatingState<?, ?, Integer, Long, String>, Integer, String> {
	@Override
	TtlAggregatingState<?, ?, Integer, Long, String> createState() {
		TtlAggregateFunction<Integer, Long, String> ttlAggregateFunction =
			new TtlAggregateFunction<>(AGGREGATE, ttlConfig, timeProvider);
		return new TtlAggregatingState<>(
			new MockInternalTtlAggregatingState<>(ttlAggregateFunction),
			ttlConfig, timeProvider, null, ttlAggregateFunction);
	}

	@Override
	void initTestValues() {
		updater = v -> ttlState.add(v);
		getter = () -> ttlState.get();
		originalGetter = () -> ttlState.original.get();

		updateValue1 = 5;
		updateValue2 = 7;

		getValue1 = "8";
		getValue2 = "15";
	}

	private static class MockInternalTtlAggregatingState<K, N, IN, ACC, OUT>
		extends MockInternalKvState<K, N, ACC> implements InternalAggregatingState<K, N, IN, ACC, OUT> {
		private final AggregateFunction<IN, ACC, OUT> aggregateFunction;

		private MockInternalTtlAggregatingState(AggregateFunction<IN, ACC, OUT> aggregateFunction) {
			this.aggregateFunction = aggregateFunction;
		}

		@Override
		public void mergeNamespaces(N target, Collection<N> sources) {
			// noop
		}

		@Override
		public OUT get() {
			return aggregateFunction.getResult(getInternal());
		}

		@Override
		public void add(IN value) {
			ACC acc = getInternal();
			acc = acc == null ? aggregateFunction.createAccumulator() : acc;
			this.value = aggregateFunction.add(value, acc);
		}
	}

	private static final AggregateFunction<Integer, Long, String> AGGREGATE =
		new AggregateFunction<Integer, Long, String>() {
			@Override
			public Long createAccumulator() {
				return 3L;
			}

			@Override
			public Long add(Integer value, Long accumulator) {
				return accumulator + value;
			}

			@Override
			public String getResult(Long accumulator) {
				return accumulator.toString();
			}

			@Override
			public Long merge(Long a, Long b) {
				return a + b;
			}
		};
}
