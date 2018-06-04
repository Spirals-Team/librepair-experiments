package org.apache.flink.api.common.state.ttl;

import org.apache.flink.api.common.state.State;

class AbstractTtlStateDecorator<STATE extends State> {
	final STATE originalState;
	final TtlConfig config;
	final TtlTimeProvider timeProvider;

	AbstractTtlStateDecorator(STATE originalState,
							  TtlConfig config,
							  TtlTimeProvider timeProvider) {
		this.originalState = originalState;
		this.config = config;
		this.timeProvider = timeProvider;
	}

	<T> TtlValue<T> wrapWithTs(T value) {
		return new TtlValue<>(value, newExpirationTimestamp());
	}

	long newExpirationTimestamp() {
		return timeProvider.currentTimestamp() + config.getTtl().toMilliseconds();
	}
}
