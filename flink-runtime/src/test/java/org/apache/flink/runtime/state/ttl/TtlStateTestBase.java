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

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.state.State;
import org.apache.flink.api.common.state.TtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.checkpoint.CheckpointOptions;
import org.apache.flink.runtime.checkpoint.StateObjectCollection;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.runtime.operators.testutils.DummyEnvironment;
import org.apache.flink.runtime.state.AbstractKeyedStateBackend;
import org.apache.flink.runtime.state.CheckpointStorageLocation;
import org.apache.flink.runtime.state.KeyGroupRange;
import org.apache.flink.runtime.state.KeyedStateHandle;
import org.apache.flink.runtime.state.SnapshotResult;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.util.FlinkRuntimeException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RunnableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/** State TTL base test suite. */
@RunWith(Parameterized.class)
public abstract class TtlStateTestBase {
	private static final long TTL = 100;
	private static final boolean TEST_SNAPSHOT = false;

	private StateBackend stateBackend;
	private CheckpointStorageLocation checkpointStorageLocation;
	private AbstractKeyedStateBackend<String> keyedStateBackend;
	private MockTtlTimeProvider timeProvider;

	@Before
	public void setup() {
		stateBackend = createStateBackend();
		checkpointStorageLocation = createCheckpointStorageLocation();
	}

	protected abstract StateBackend createStateBackend();

	private CheckpointStorageLocation createCheckpointStorageLocation() {
		try {
			return stateBackend
				.createCheckpointStorage(new JobID())
				.initializeLocationForCheckpoint(2L);
		} catch (IOException e) {
			throw new RuntimeException("unexpected");
		}
	}

	@Parameterized.Parameter
	public TtlStateTestContextBase<?, ?, ?> ctx;

	@Parameterized.Parameters(name = "{0}")
	public static List<TtlStateTestContextBase<?, ?, ?>> testContexts() {
		return Arrays.asList(
			new TtlValueStateTestContext(),
			new TtlListStateTestContext(),
			new TtlMapStateAllEntriesTestContext(),
			new TtlMapStatePerElementTestContext(),
			new TtlAggregatingStateTestContext(),
			new TtlReducingStateTestContext(),
			new TtlFoldingStateTestContext());
	}

	@SuppressWarnings("unchecked")
	private <UV> TtlStateTestContextBase<?, UV, ?> ctx() {
		return (TtlStateTestContextBase<?, UV, ?>) ctx;
	}

	private void initTest() {
		initTest(TtlConfig.TtlUpdateType.OnCreateAndWrite, TtlConfig.TtlStateVisibility.NeverReturnExpired);
	}

	private void initTest(TtlConfig.TtlUpdateType updateType, TtlConfig.TtlStateVisibility visibility) {
		initTest(updateType, visibility, TTL);
	}

	private void initTest(TtlConfig.TtlUpdateType updateType, TtlConfig.TtlStateVisibility visibility, long ttl) {
		timeProvider = new MockTtlTimeProvider();
		ctx().ttlConfig = new TtlConfig(
			updateType,
			visibility,
			TtlConfig.TtlTimeCharacteristic.ProcessingTime,
			Time.milliseconds(ttl));
		createKeyedStateBackend();
		keyedStateBackend.setCurrentKey("defaultKey");
		ctx().ttlState = createState();
		ctx().ttlState.setCurrentNamespace("defaultNamespace");
		ctx().initTestValues();
	}

	private void createKeyedStateBackend() {
		Environment env = new DummyEnvironment();
		try {
			keyedStateBackend = stateBackend.createKeyedStateBackend(
				env, new JobID(), "test", StringSerializer.INSTANCE, 10,
				new KeyGroupRange(0, 9), env.getTaskKvStateRegistry(), timeProvider);
			keyedStateBackend.restore(null);
		} catch (Exception e) {
			throw new RuntimeException("unexpected");
		}
	}

	private void takeAndRestoreSnapshot() throws Exception {
		if (!TEST_SNAPSHOT) {
			return;
		}
		RunnableFuture<SnapshotResult<KeyedStateHandle>> snapshotRunnableFuture =
			keyedStateBackend.snapshot(682375462378L, 2,
				checkpointStorageLocation, CheckpointOptions.forCheckpointWithDefaultLocation());
		if (!snapshotRunnableFuture.isDone()) {
			snapshotRunnableFuture.run();
		}
		KeyedStateHandle snapshot = snapshotRunnableFuture.get().getJobManagerOwnedSnapshot();

		keyedStateBackend.close();
		keyedStateBackend.dispose();
		createKeyedStateBackend();
		keyedStateBackend.restore(new StateObjectCollection<>(Collections.singleton(snapshot)));
		keyedStateBackend.setCurrentKey("defaultKey");
		snapshot.discardState();
		ctx().ttlState = createState();
		ctx().ttlState.setCurrentNamespace("defaultNamespace");
	}

	private <US extends State, IS extends US> IS createState() {
		try {
			return keyedStateBackend.getOrCreateKeyedState(StringSerializer.INSTANCE, ctx().createStateDescriptor());
		} catch (Exception e) {
			throw new FlinkRuntimeException("Unexpected exception wrapping mock state", e);
		}
	}

	@Test
	public void testNonExistentValue() throws Exception {
		initTest();
		assertEquals("Non-existing state should be empty", ctx().emptyValue, ctx().getter.get());
	}

	@Test
	public void testExactExpirationOnWrite() throws Exception {
		initTest(TtlConfig.TtlUpdateType.OnCreateAndWrite, TtlConfig.TtlStateVisibility.NeverReturnExpired);

		takeAndRestoreSnapshot();

		timeProvider.time = 0;
		ctx().updater.accept(ctx().updateEmpty);

		takeAndRestoreSnapshot();

		timeProvider.time = 20;
		assertEquals("Unexpired state should be available", ctx().getUpdateEmpty, ctx().getter.get());

		takeAndRestoreSnapshot();

		timeProvider.time = 50;
		ctx().updater.accept(ctx().updateUnexpired);

		takeAndRestoreSnapshot();

		timeProvider.time = 120;
		assertEquals("Unexpired state should be available after update", ctx().getUnexpired, ctx().getter.get());

		takeAndRestoreSnapshot();

		timeProvider.time = 170;
		ctx().updater.accept(ctx().updateExpired);

		takeAndRestoreSnapshot();

		timeProvider.time = 220;
		assertEquals("Unexpired state should be available after update", ctx().getUpdateExpired, ctx().getter.get());

		takeAndRestoreSnapshot();

		timeProvider.time = 300;
		assertEquals("Expired state should be unavailable", ctx().emptyValue, ctx().getter.get());
		assertEquals("Original state should be cleared on access", ctx().emptyValue, ctx().originalGetter.get());
	}

	@Test
	public void testRelaxedExpirationOnWrite() throws Exception {
		initTest(TtlConfig.TtlUpdateType.OnCreateAndWrite, TtlConfig.TtlStateVisibility.ReturnExpiredIfNotCleanedUp);

		timeProvider.time = 0;
		ctx().updater.accept(ctx().updateEmpty);

		takeAndRestoreSnapshot();

		timeProvider.time = 120;
		assertEquals("Expired state should be available", ctx().getUpdateEmpty, ctx().getter.get());
		assertEquals("Original state should be cleared on access", ctx().emptyValue, ctx().originalGetter.get());
		assertEquals("Expired state should be cleared on access", ctx().emptyValue, ctx().getter.get());
	}

	@Test
	public void testExactExpirationOnRead() throws Exception {
		initTest(TtlConfig.TtlUpdateType.OnReadAndWrite, TtlConfig.TtlStateVisibility.NeverReturnExpired);

		timeProvider.time = 0;
		ctx().updater.accept(ctx().updateEmpty);

		takeAndRestoreSnapshot();

		timeProvider.time = 50;
		assertEquals("Unexpired state should be available", ctx().getUpdateEmpty, ctx().getter.get());

		takeAndRestoreSnapshot();

		timeProvider.time = 120;
		assertEquals("Unexpired state should be available after read", ctx().getUpdateEmpty, ctx().getter.get());

		takeAndRestoreSnapshot();

		timeProvider.time = 250;
		assertEquals("Expired state should be unavailable", ctx().emptyValue, ctx().getter.get());
		assertEquals("Original state should be cleared on access", ctx().emptyValue, ctx().originalGetter.get());
	}

	@Test
	public void testRelaxedExpirationOnRead() throws Exception {
		initTest(TtlConfig.TtlUpdateType.OnReadAndWrite, TtlConfig.TtlStateVisibility.ReturnExpiredIfNotCleanedUp);

		timeProvider.time = 0;
		ctx().updater.accept(ctx().updateEmpty);

		takeAndRestoreSnapshot();

		timeProvider.time = 50;
		assertEquals("Unexpired state should be available", ctx().getUpdateEmpty, ctx().getter.get());

		takeAndRestoreSnapshot();

		timeProvider.time = 170;
		assertEquals("Expired state should be available", ctx().getUpdateEmpty, ctx().getter.get());
		assertEquals("Expired state should be cleared on access", ctx().emptyValue, ctx().getter.get());
	}

	@Test
	public void testExpirationTimestampOverflow() throws Exception {
		initTest(TtlConfig.TtlUpdateType.OnCreateAndWrite, TtlConfig.TtlStateVisibility.NeverReturnExpired, Long.MAX_VALUE);

		timeProvider.time = 10;
		ctx().updater.accept(ctx().updateEmpty);

		takeAndRestoreSnapshot();

		timeProvider.time = 50;
		assertEquals("Unexpired state should be available", ctx().getUpdateEmpty, ctx().getter.get());
	}

	@SuppressWarnings("unchecked")
	private <UV> TtlMergingStateTestContext<?, UV, ?> mctx() {
		return (TtlMergingStateTestContext<?, UV, ?>) ctx;
	}

	@Test
	public void testMergeNamespaces() throws Exception {
		assumeTrue(ctx instanceof TtlMergingStateTestContext);

		initTest();

		timeProvider.time = 0;
		List<Tuple2<String, Object>> expiredUpdatesToMerge = mctx().generateExpiredUpdatesToMerge();
		mctx().applyStateUpdates(expiredUpdatesToMerge);

		takeAndRestoreSnapshot();

		timeProvider.time = 120;
		List<Tuple2<String, Object>> unexpiredUpdatesToMerge = mctx().generateUnexpiredUpdatesToMerge();
		mctx().applyStateUpdates(unexpiredUpdatesToMerge);

		takeAndRestoreSnapshot();

		timeProvider.time = 150;
		List<Tuple2<String, Object>> finalUpdatesToMerge = mctx().generateFinalUpdatesToMerge();
		mctx().applyStateUpdates(finalUpdatesToMerge);

		takeAndRestoreSnapshot();

		timeProvider.time = 230;
		mctx().ttlState.mergeNamespaces("targetNamespace", TtlMergingStateTestContext.NAMESPACES);
		mctx().ttlState.setCurrentNamespace("targetNamespace");
		assertEquals("Unexpected result of merge operation",
			mctx().getMergeResult(unexpiredUpdatesToMerge, finalUpdatesToMerge), mctx().getter.get());
	}
}
