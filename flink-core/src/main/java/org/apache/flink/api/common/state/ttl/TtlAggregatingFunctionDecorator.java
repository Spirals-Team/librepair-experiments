package org.apache.flink.api.common.state.ttl;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.state.State;

public class TtlAggregatingFunctionDecorator<IN, ACC, OUT>
	extends AbstractTtlStateDecorator<State>
	implements AggregateFunction<IN, TtlValue<ACC>, OUT> {
	private final AggregateFunction<IN, ACC, OUT> aggFunction;

	// TODO: throw unsupported exception for TtlUpdateType.OnReadAndWrite
	TtlAggregatingFunctionDecorator(AggregateFunction<IN, ACC, OUT> aggFunction,
									TtlConfig config,
									TtlTimeProvider timeProvider) {
		super(null, config, timeProvider);
		this.aggFunction = aggFunction;
	}

	@Override
	public TtlValue<ACC> createAccumulator() {
		return new TtlValue<>(aggFunction.createAccumulator(), newExpirationTimestamp());
	}

	@Override
	public TtlValue<ACC> add(IN value, TtlValue<ACC> accumulator) {
		ACC userAcc = accumulator.expired(timeProvider) ? aggFunction.createAccumulator() : accumulator.getUserValue();
		return new TtlValue<>(aggFunction.add(value, userAcc), newExpirationTimestamp());
	}

	@Override
	public OUT getResult(TtlValue<ACC> accumulator) {
		return accumulator.expired(timeProvider) ? null : aggFunction.getResult(accumulator.getUserValue());
	}

	@Override
	public TtlValue<ACC> merge(TtlValue<ACC> a, TtlValue<ACC> b) {
		ACC userA = a.getUnexpried(timeProvider);
		ACC userB = b.getUnexpried(timeProvider);
		if (userA != null && userB != null) {
			return wrapWithTs(aggFunction.merge(userA, userB));
		} else if (userA != null) {
			return a;
		} else {
			return b;
		}
	}
}
