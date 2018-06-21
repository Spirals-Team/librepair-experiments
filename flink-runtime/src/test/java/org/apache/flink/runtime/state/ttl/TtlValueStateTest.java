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

import org.apache.flink.runtime.state.internal.InternalValueState;

/** Test suite for {@link TtlValueState}. */
public class TtlValueStateTest extends TtlStateTestBase<TtlValueState<?, ?, String>, String, String> {
	private static final String TEST_VAL1 = "test value1";
	private static final String TEST_VAL2 = "test value2";

	@Override
	TtlValueState<?, ?, String> createState() {
		return new TtlValueState<>(new MockInternalValueState<>(), ttlConfig, timeProvider, null);
	}

	@Override
	void initTestValues() {
		updater = v -> ttlState.update(v);
		getter = () -> ttlState.value();
		originalGetter = () -> ttlState.original.value();

		updateValue1 = TEST_VAL1;
		updateValue2 = TEST_VAL2;

		getValue1 = TEST_VAL1;
		getValue2 = TEST_VAL2;
	}

	private static class MockInternalValueState<K, N, T>
		extends MockInternalKvState<K, N, T> implements InternalValueState<K, N, T> {

		@Override
		public T value() {
			return value;
		}

		@Override
		public void update(T value) {
			this.value = value;
		}
	}
}
