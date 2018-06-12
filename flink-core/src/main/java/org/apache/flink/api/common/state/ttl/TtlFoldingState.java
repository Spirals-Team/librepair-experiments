package org.apache.flink.api.common.state.ttl;

import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.FoldingState;

/**
 * This class wraps folding state with TTL logic.
 *
 * @param <T> Type of the values folded into the state
 * @param <ACC> Type of the value in the state
 *
 * @deprecated use {@link AggregatingState} instead
 */
@Deprecated
class TtlFoldingState<T, ACC>
	extends AbstractTtlDecorator<FoldingState<T, TtlValue<ACC>>>
	implements FoldingState<T, ACC> {
	// TODO: throw unsupported exception for TtlUpdateType.OnReadAndWrite
	TtlFoldingState(
		FoldingState<T, TtlValue<ACC>> originalState,
		TtlConfig config,
		TtlTimeProvider timeProvider) {
		super(originalState, config, timeProvider);
	}

	@Override
	public ACC get() throws Exception {
		TtlValue<ACC> ttlValue = original.get();
		if (ttlValue == null) {
			return null;
		} else if (expired(ttlValue)) {
			original.clear();
			if (!returnExpired) {
				return null;
			}
		}
		return ttlValue.getUserValue();
	}

	@Override
	public void add(T value) throws Exception {
		original.add(value);
	}

	@Override
	public void clear() {
		original.clear();
	}
}
