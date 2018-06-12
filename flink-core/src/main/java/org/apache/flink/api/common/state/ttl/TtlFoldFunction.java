package org.apache.flink.api.common.state.ttl;

import org.apache.flink.api.common.functions.FoldFunction;

/**
 * This class wraps folding function with TTL logic.
 *
 * @param <T> Type of the values folded into the state
 * @param <ACC> Type of the value in the state
 *
 * @deprecated use {@link TtlAggregateFunction} instead
 */
@Deprecated
class TtlFoldFunction<T, ACC>
	extends AbstractTtlDecorator<FoldFunction<T, ACC>>
	implements FoldFunction<T, TtlValue<ACC>> {
	TtlFoldFunction(
		FoldFunction<T, ACC> original,
		TtlConfig config,
		TtlTimeProvider timeProvider) {
		super(original, config, timeProvider);
	}

	@Override
	public TtlValue<ACC> fold(TtlValue<ACC> accumulator, T value) throws Exception {
		return wrapWithTs(original.fold(accumulator.getUserValue(), value));
	}
}
