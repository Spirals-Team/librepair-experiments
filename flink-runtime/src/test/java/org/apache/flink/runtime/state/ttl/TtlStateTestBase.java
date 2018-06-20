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

import org.apache.flink.api.common.time.Time;
import org.apache.flink.util.function.SupplierWithException;
import org.apache.flink.util.function.ThrowingConsumer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

abstract class TtlStateTestBase<S, UV, GV> {
	private static final long TTL = 100;

	S ttlState;
	MockTimeProvider timeProvider;
	TtlConfig ttlConfig;

	ThrowingConsumer<UV, Exception> updater;
	SupplierWithException<GV, Exception> getter;
	SupplierWithException<?, Exception> originalGetter;

	UV updateValue1;
	UV updateValue2;

	GV getValue1;
	GV getValue2;

	GV emptyValue = null;

	private void initTest() {
		initTest(TtlUpdateType.OnCreateAndWrite, TtlStateVisibility.Exact);
	}

	private void initTest(TtlUpdateType updateType, TtlStateVisibility visibility) {
		timeProvider = new MockTimeProvider();
		ttlConfig = new TtlConfig(
			updateType,
			visibility,
			TtlTimeCharacteristic.ProcessingTime,
			Time.milliseconds(TTL));
		ttlState = createState();
		initTestValues();
	}

	abstract S createState();

	abstract void initTestValues();

	@Test
	public void testNonExistentValue() throws Exception {
		initTest();
		assertEquals("Non-existing state should be empty", emptyValue, getter.get());
	}

	@Test
	public void testExactExpirationOnWrite() throws Exception {
		initTest(TtlUpdateType.OnCreateAndWrite, TtlStateVisibility.Exact);

		timeProvider.time = 0;
		updater.accept(updateValue1);

		timeProvider.time = 20;
		assertEquals("Unexpired state should be available", getValue1, getter.get());

		timeProvider.time = 50;
		updater.accept(updateValue2);

		timeProvider.time = 120;
		assertEquals("Unexpired state should be available after update", getValue2, getter.get());

		timeProvider.time = 170;
		assertEquals("Expired state should be hidden", emptyValue, getter.get());
		assertEquals("Original state should be cleared on access", emptyValue, originalGetter.get());
	}

	@Test
	public void testRelaxedExpirationOnWrite() throws Exception {
		initTest(TtlUpdateType.OnCreateAndWrite, TtlStateVisibility.Relaxed);

		timeProvider.time = 0;
		updater.accept(updateValue1);

		timeProvider.time = 120;
		assertEquals("Expired state should be available", getValue1, getter.get());
		assertEquals("Expired state should be cleared on access", emptyValue, getter.get());
	}

	@Test
	public void testExactExpirationOnRead() throws Exception {
		initTest(TtlUpdateType.OnReadAndWrite, TtlStateVisibility.Exact);

		timeProvider.time = 0;
		updater.accept(updateValue1);

		timeProvider.time = 50;
		assertEquals("Unexpired state should be available", getValue1, getter.get());

		timeProvider.time = 120;
		assertEquals("Unexpired state should be available after read", getValue1, getter.get());

		timeProvider.time = 250;
		assertEquals("Expired state should be hidden", emptyValue, getter.get());
		assertEquals("Original state should be cleared on access", emptyValue, originalGetter.get());
	}

	@Test
	public void testRelaxedExpirationOnRead() throws Exception {
		initTest(TtlUpdateType.OnReadAndWrite, TtlStateVisibility.Relaxed);

		timeProvider.time = 0;
		updater.accept(updateValue1);

		timeProvider.time = 50;
		assertEquals("Unexpired state should be available", getValue1, getter.get());

		timeProvider.time = 170;
		assertEquals("Expired state should be available", getValue1, getter.get());
		assertEquals("Expired state should be cleared on access", emptyValue, getter.get());
	}
}
