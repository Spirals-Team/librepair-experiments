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

import org.apache.flink.api.common.functions.AggregateFunction;

public class TtlAggregatingFunctionDecorator<IN, ACC, OUT>
	extends AbstractTtlDecorator<AggregateFunction<IN, ACC, OUT>>
	implements AggregateFunction<IN, TtlValue<ACC>, OUT> {

	// TODO: throw unsupported exception for TtlUpdateType.OnReadAndWrite
	TtlAggregatingFunctionDecorator(AggregateFunction<IN, ACC, OUT> aggFunction,
									TtlConfig config,
									TtlTimeProvider timeProvider) {
		super(aggFunction, config, timeProvider);
	}

	@Override
	public TtlValue<ACC> createAccumulator() {
		return new TtlValue<>(original.createAccumulator(), newExpirationTimestamp());
	}

	@Override
	public TtlValue<ACC> add(IN value, TtlValue<ACC> accumulator) {
		ACC userAcc = expired(accumulator) ? original.createAccumulator() : accumulator.getUserValue();
		return new TtlValue<>(original.add(value, userAcc), newExpirationTimestamp());
	}

	@Override
	public OUT getResult(TtlValue<ACC> accumulator) {
		return expired(accumulator) ? null : original.getResult(accumulator.getUserValue());
	}

	@Override
	public TtlValue<ACC> merge(TtlValue<ACC> a, TtlValue<ACC> b) {
		ACC userA = getUnexpried(a);
		ACC userB = getUnexpried(b);
		if (userA != null && userB != null) {
			return wrapWithTs(original.merge(userA, userB));
		} else if (userA != null) {
			return a;
		} else {
			return b;
		}
	}
}
