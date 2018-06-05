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

import org.apache.flink.api.common.functions.ReduceFunction;

public class TtlReducingFunctionDecorator<T>
	extends AbstractTtlDecorator<ReduceFunction<T>>
	implements ReduceFunction<TtlValue<T>> {
	TtlReducingFunctionDecorator(ReduceFunction<T> reduceFunction,
								 TtlConfig config,
								 TtlTimeProvider timeProvider) {
		super(reduceFunction, config, timeProvider);
	}

	@Override
	public TtlValue<T> reduce(TtlValue<T> value1, TtlValue<T> value2) throws Exception {
		return wrapWithTs(original.reduce(value1.getUserValue(), value2.getUserValue()));
	}
}
