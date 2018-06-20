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

import org.apache.flink.util.Preconditions;
import org.apache.flink.util.function.SupplierWithException;
import org.apache.flink.util.function.ThrowingConsumer;
import org.apache.flink.util.function.ThrowingRunnable;

/**
 * Base class for TTL logic wrappers.
 *
 * @param <T> Type of originally wrapped object
 */
abstract class AbstractTtlDecorator<T> {
	final T original;
	final TtlConfig config;
	final TtlTimeProvider timeProvider;
	final boolean updateTsOnRead;
	final boolean returnExpired;

	AbstractTtlDecorator(
		T original,
		TtlConfig config,
		TtlTimeProvider timeProvider) {
		Preconditions.checkNotNull(original);
		Preconditions.checkNotNull(config);
		Preconditions.checkNotNull(timeProvider);
		Preconditions.checkArgument(config.getTtlUpdateType() != TtlUpdateType.Disabled,
			"State does not need to be wrapped with TTL if it is configured as disabled.");
		this.original = original;
		this.config = config;
		this.timeProvider = timeProvider;
		this.updateTsOnRead = config.getTtlUpdateType() == TtlUpdateType.OnReadAndWrite;
		this.returnExpired = config.getStateVisibility() == TtlStateVisibility.Relaxed;
	}

	<V> V getUnexpried(TtlValue<V> ttlValue) {
		return ttlValue == null || (expired(ttlValue) && !returnExpired) ? null : ttlValue.getUserValue();
	}

	<V> boolean expired(TtlValue<V> ttlValue) {
		return ttlValue != null && ttlValue.getExpirationTimestamp() <= timeProvider.currentTimestamp();
	}

	<V> TtlValue<V> wrapWithTs(V value) {
		return wrapWithTs(value, newExpirationTimestamp());
	}

	static <V> TtlValue<V> wrapWithTs(V value, long ts) {
		return value == null ? null : new TtlValue<>(value, ts);
	}

	<V> TtlValue<V> rewrapWithNewTs(TtlValue<V> ttlValue) {
		return wrapWithTs(ttlValue.getUserValue());
	}

	private long newExpirationTimestamp() {
		return timeProvider.currentTimestamp() + config.getTtl().toMilliseconds();
	}

	<SE extends Throwable, CE extends Throwable, CLE extends Throwable, V> V getWithTtlCheckAndUpdate(
		SupplierWithException<TtlValue<V>, SE> getter,
		ThrowingConsumer<TtlValue<V>, CE> updater,
		ThrowingRunnable<CLE> stateClear) throws SE, CE, CLE {
		TtlValue<V> ttlValue = getter.get();
		if (ttlValue == null) {
			return null;
		} else if (expired(ttlValue)) {
			stateClear.run();
			if (!returnExpired) {
				return null;
			}
		} else if (updateTsOnRead) {
			updater.accept(rewrapWithNewTs(ttlValue));
		}
		return ttlValue.getUserValue();
	}
}
