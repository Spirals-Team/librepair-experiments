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

import org.apache.flink.api.common.state.ValueState;

import java.io.IOException;

public class TtlValueState<T>
	extends AbstractTtlDecorator<ValueState<TtlValue<T>>>
	implements ValueState<T> {
	TtlValueState(ValueState<TtlValue<T>> originalState,
				  TtlConfig config,
				  TtlTimeProvider timeProvider) {
		super(originalState, config, timeProvider);
	}

	@Override
	public T value() throws IOException {
		TtlValue<T> ttlValue = original.value();
		if (ttlValue == null) {
			return null;
		}
		T value = getUnexpried(ttlValue);
		if (value != null && updateTsOnRead) {
			original.update(rewrapWithNewTs(ttlValue));
		}
		return value;
	}

	@Override
	public void update(T value) throws IOException {
		original.update(wrapWithTs(value));
	}

	@Override
	public void clear() {
		original.clear();
	}
}
