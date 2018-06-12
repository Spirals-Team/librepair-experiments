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

import org.apache.flink.util.Preconditions;

/**
 * Base class for state TTL logic wrappers.
 *
 * @param <S>  Type of originally wrapped state object
 */
class AbstractTtlDecorator<S> {
	final S original;
	final TtlConfig config;
	final TtlTimeProvider timeProvider;
	final boolean updateTsOnRead;
	final boolean returnExpired;

	AbstractTtlDecorator(S original, TtlConfig config, TtlTimeProvider timeProvider) {
		Preconditions.checkNotNull(original);
		Preconditions.checkNotNull(config);
		Preconditions.checkNotNull(timeProvider);
		this.original = original;
		this.config = config;
		this.timeProvider = timeProvider;
		this.updateTsOnRead = config.getTtlUpdateType() == TtlUpdateType.OnReadAndWrite;
		this.returnExpired = config.getStateVisibility() == TtlStateVisibility.Relaxed;
	}

	<T> T getUnexpried(TtlValue<T> ttlValue) {
		return ttlValue == null || (expired(ttlValue) && !returnExpired) ? null : ttlValue.getUserValue();
	}

	<T> boolean expired(TtlValue<T> ttlValue) {
		return ttlValue != null && ttlValue.getExpirationTimestamp() <= timeProvider.currentTimestamp();
	}

	<T> TtlValue<T> wrapWithTs(T value) {
		return new TtlValue<>(value, newExpirationTimestamp());
	}

	<T> TtlValue<T> rewrapWithNewTs(TtlValue<T> value) {
		return new TtlValue<>(value.getUserValue(), newExpirationTimestamp());
	}

	long newExpirationTimestamp() {
		return timeProvider.currentTimestamp() + config.getTtl().toMilliseconds();
	}
}
