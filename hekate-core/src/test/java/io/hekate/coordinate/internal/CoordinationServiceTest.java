/*
 * Copyright 2018 The Hekate Project
 *
 * The Hekate Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.hekate.coordinate.internal;

import io.hekate.HekateNodeParamTestBase;
import io.hekate.HekateTestContext;
import io.hekate.cluster.ClusterNode;
import io.hekate.coordinate.CoordinationContext;
import io.hekate.coordinate.CoordinationFuture;
import io.hekate.coordinate.CoordinationHandler;
import io.hekate.coordinate.CoordinationProcessConfig;
import io.hekate.coordinate.CoordinationRequest;
import io.hekate.coordinate.CoordinationServiceFactory;
import io.hekate.core.internal.HekateTestNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.Test;
import org.mockito.InOrder;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CoordinationServiceTest extends HekateNodeParamTestBase {
    private static class CoordinatedValueHandler implements CoordinationHandler {
        private volatile String lastValue;

        @Override
        public void prepare(CoordinationContext ctx) {
            if (lastValue == null) {
                lastValue = "0";
            }
        }

        @Override
        public void coordinate(CoordinationContext ctx) {
            ctx.broadcast("get", getResponses -> {
                Optional<Integer> maxOpt = getResponses.values().stream()
                    .map(String.class::cast)
                    .map(Integer::parseInt)
                    .max(Integer::compare);

                assertTrue(maxOpt.isPresent());

                int newMax = maxOpt.get() + 1;

                ctx.broadcast("put_" + newMax, putResponses -> {
                    // No-op.
                });
            });
        }

        @Override
        public void process(CoordinationRequest request, CoordinationContext ctx) {
            String command = request.get();

            if ("get".equals(command)) {
                request.reply(lastValue);
            } else if ("prepare".equals(command)) {
                request.reply("ok");
            } else {
                lastValue = command.substring("put_".length());

                request.reply("ok");

                ctx.complete();

                complete();
            }
        }

        @Override
        public void initialize() {
            lastValue = null;
        }

        @Override
        public void terminate() {
            lastValue = null;
        }

        public String getLastValue() {
            return lastValue;
        }

        protected void complete() {
            // No-op.
        }
    }

    public CoordinationServiceTest(HekateTestContext params) {
        super(params);
    }

    @Test
    public void testHandlerLifecycleWithCompletedProcess() throws Exception {
        CoordinationHandler handler = mock(CoordinationHandler.class);

        HekateTestNode node = createCoordinationNode(handler);

        repeat(3, i -> {
            doAnswer(invocation -> {
                ((CoordinationContext)invocation.getArguments()[0]).complete();

                return null;
            }).when(handler).coordinate(any());

            node.join();

            get(node.coordination().futureOf("test"));

            InOrder order = inOrder(handler);

            order.verify(handler).initialize();
            order.verify(handler).prepare(any());
            order.verify(handler).coordinate(any());

            verifyNoMoreInteractions(handler);

            node.leave();

            order.verify(handler).terminate();

            verifyNoMoreInteractions(handler);

            reset(handler);
        });
    }

    @Test
    public void testHandlerLifecycleWithNonCompletedProcess() throws Exception {
        CoordinationHandler handler = mock(CoordinationHandler.class);

        HekateTestNode node = createCoordinationNode(handler);

        repeat(3, i -> {
            CountDownLatch coordinateLatch = new CountDownLatch(1);

            doAnswer(invocation -> {
                coordinateLatch.countDown();

                return null;
            }).when(handler).coordinate(any());

            node.join();

            await(coordinateLatch);

            InOrder order = inOrder(handler);

            order.verify(handler).initialize();
            order.verify(handler).prepare(any());
            order.verify(handler).coordinate(any());

            verifyNoMoreInteractions(handler);

            node.leave();

            order.verify(handler).cancel(any());
            order.verify(handler).terminate();

            verifyNoMoreInteractions(handler);

            reset(handler);
        });
    }

    @Test
    public void testCoordinationFuture() throws Exception {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(1);

        CoordinationHandler handler = mock(CoordinationHandler.class);

        doAnswer(invocation -> {
            startLatch.countDown();

            endLatch.await(3, TimeUnit.SECONDS);

            ((CoordinationContext)invocation.getArguments()[0]).complete();

            return null;
        }).when(handler).coordinate(any());

        HekateTestNode node = createCoordinationNode(handler);

        node.join();

        CoordinationFuture future = node.coordination().futureOf("test");

        await(startLatch);

        assertFalse(future.isDone());

        endLatch.countDown();

        get(future);

        assertTrue(future.isDone());
        assertNotNull(future.getNow(null));
        assertSame(handler, future.getNow(null).handler());
    }

    @Test
    public void testCoordinationWithLeaveBeforeCompletion() throws Exception {
        CountDownLatch startLatch = new CountDownLatch(1);

        CoordinationHandler handler = mock(CoordinationHandler.class);

        doAnswer(invocation -> {
            startLatch.countDown();

            return null;
        }).when(handler).coordinate(any());

        HekateTestNode node = createCoordinationNode(handler);

        node.join();

        CoordinationFuture future = node.coordination().futureOf("test");

        await(startLatch);

        assertFalse(future.isDone());

        node.leave();

        expect(CancellationException.class, () -> get(future));

        assertTrue(future.isDone());
        assertTrue(future.isCancelled());
    }

    @Test
    public void testCoordinatedValue() throws Exception {
        repeat(5, i -> {
            int nodesCount = i + 1;

            List<HekateTestNode> nodes = new ArrayList<>(nodesCount);

            int val = 0;

            for (int j = 0; j < nodesCount; j++) {
                HekateTestNode node = createCoordinationNode(new CoordinatedValueHandler()).join();

                nodes.add(node);

                get(node.coordination().futureOf("test"));

                String expected = String.valueOf(++val);

                awaitForCoordinatedValue(expected, nodes);
            }

            for (Iterator<HekateTestNode> it = nodes.iterator(); it.hasNext(); ) {
                HekateTestNode node = it.next();

                it.remove();

                node.leave();

                String expected = String.valueOf(++val);

                if (it.hasNext()) {
                    awaitForCoordinatedValue(expected, nodes);
                }
            }
        });
    }

    @Test
    public void testCoordinatedValueWithError() throws Exception {
        repeat(5, i -> {
            int nodesCount = i + 1;

            List<HekateTestNode> nodes = new ArrayList<>(nodesCount);

            int val = 0;

            for (int j = 0; j < nodesCount; j++) {
                HekateTestNode node = createCoordinationNode(new CoordinatedValueHandler() {
                    @Override
                    public void process(CoordinationRequest request, CoordinationContext ctx) {
                        if (ThreadLocalRandom.current().nextInt(3) == 0) {
                            throw TEST_ERROR;
                        } else {
                            super.process(request, ctx);
                        }
                    }
                }).join();

                nodes.add(node);

                get(node.coordination().futureOf("test"));

                String expected = String.valueOf(++val);

                awaitForCoordinatedValue(expected, nodes);
            }

            for (Iterator<HekateTestNode> it = nodes.iterator(); it.hasNext(); ) {
                HekateTestNode node = it.next();

                it.remove();

                node.leave();

                String expected = String.valueOf(++val);

                if (it.hasNext()) {
                    awaitForCoordinatedValue(expected, nodes);
                }
            }
        });
    }

    @Test
    public void testCoordinatorLeave() throws Exception {
        repeat(30, i ->
            doTestCoordinatorLeave(coordinator ->
                coordinator.leaveAsync()
            )
        );
    }

    @Test
    public void testCoordinatorTerminate() throws Exception {
        this.disableNodeFailurePostCheck();

        repeat(3, i ->
            doTestCoordinatorLeave(coordinator ->
                coordinator.terminateAsync()
            )
        );
    }

    @Test
    public void testJoinDuringCoordination() throws Exception {
        CountDownLatch blockLatch = new CountDownLatch(1);
        CountDownLatch proceedLatch = new CountDownLatch(1);

        AtomicInteger aborts = new AtomicInteger();

        class BlockingHandler extends CoordinatedValueHandler {
            @Override
            public void coordinate(CoordinationContext ctx) {
                if (ctx.size() == 2 && blockLatch.getCount() > 0) {
                    blockLatch.countDown();

                    await(proceedLatch);
                }

                super.coordinate(ctx);
            }

            @Override
            public void cancel(CoordinationContext ctx) {
                super.cancel(ctx);

                aborts.incrementAndGet();
            }
        }

        BlockingHandler h1 = new BlockingHandler();
        BlockingHandler h2 = new BlockingHandler();
        BlockingHandler h3 = new BlockingHandler();

        HekateTestNode n1 = createCoordinationNode(h1);
        HekateTestNode n2 = createCoordinationNode(h2);
        HekateTestNode n3 = createCoordinationNode(h3);

        // Join and await for initial coordination on the first node.
        get(n1.join().coordination().futureOf("test"));

        n2.join(); // <-- Coordination should block.

        await(blockLatch);

        n3.join();

        awaitForTopology(n1, n2, n3);

        proceedLatch.countDown();

        // Expect 2 but not 3 since one coordination should be interrupted by a concurrent join.
        awaitForCoordinatedValue("2", Arrays.asList(n1, n2, n3));

        assertEquals(1, aborts.get());
    }

    private void doTestCoordinatorLeave(Function<HekateTestNode, Future<?>> stopAction) throws Exception {
        CountDownLatch coordinatorReady = new CountDownLatch(1);
        CountDownLatch allPrepared = new CountDownLatch(3);
        CountDownLatch proceed = new CountDownLatch(1);

        AtomicReference<ClusterNode> coordinatorRef = new AtomicReference<>();

        class BlockingHandler extends CoordinatedValueHandler {
            private final AtomicInteger stack = new AtomicInteger();

            @Override
            public void coordinate(CoordinationContext ctx) {
                if (ctx.size() == 3 && coordinatorReady.getCount() > 0) {
                    coordinatorRef.set(ctx.coordinator().node());

                    // Make sure that all members will be prepared by the time of coordinator termination.
                    ctx.broadcast("prepare", responses -> {
                        // No-op.
                    });

                    coordinatorReady.countDown();

                    await(proceed);
                } else {
                    super.coordinate(ctx);
                }
            }

            @Override
            public void prepare(CoordinationContext ctx) {
                super.prepare(ctx);

                say(ctx);

                if (ctx.size() == 3) {
                    // Notify on prepare.
                    allPrepared.countDown();
                }

                stack.incrementAndGet();
            }

            @Override
            public void cancel(CoordinationContext ctx) {
                super.cancel(ctx);

                stack.decrementAndGet();
            }

            public int stack() {
                return stack.get();
            }

            @Override
            protected void complete() {
                stack.decrementAndGet();
            }
        }

        BlockingHandler h1 = new BlockingHandler();
        BlockingHandler h2 = new BlockingHandler();
        BlockingHandler h3 = new BlockingHandler();

        HekateTestNode n1 = createCoordinationNode(h1);
        HekateTestNode n2 = createCoordinationNode(h2);
        HekateTestNode n3 = createCoordinationNode(h3);

        assertEquals("1", n1.join().coordination().futureOf("test").get().<BlockingHandler>handler().getLastValue());
        assertEquals("2", n2.join().coordination().futureOf("test").get().<BlockingHandler>handler().getLastValue());

        n3.join(); // <-- Coordination should block.

        // Await for all members to become prepared.
        await(allPrepared);

        // Await for coordinator to become ready.
        await(coordinatorReady);

        HekateTestNode coordinator = Stream.of(n1, n2, n3)
            .filter(n -> n.localNode().equals(coordinatorRef.get()))
            .findFirst()
            .orElseThrow(AssertionError::new);

        say("Terminating coordinator.");

        Future<?> termFuture = stopAction.apply(coordinator);

        proceed.countDown();

        get(termFuture);

        List<HekateTestNode> remaining = Stream.of(n1, n2, n3).filter(n -> !n.equals(coordinator)).collect(toList());

        say("Awaiting for coordination.");

        awaitForCoordinatedValue("3", remaining);

        say("Done awaiting for coordination.");

        busyWait("empty stack of " + n1.toString(), () -> h1.stack() == 0);
        busyWait("empty stack of " + n2.toString(), () -> h2.stack() == 0);
        busyWait("empty stack of " + n3.toString(), () -> h3.stack() == 0);

        n1.leave();
        n2.leave();
        n3.leave();
    }

    private void awaitForCoordinatedValue(String expected, List<HekateTestNode> nodes) throws Exception {
        busyWait("value coordination", () ->
            nodes.stream().allMatch(n -> {
                CoordinatedValueHandler handler = n.coordination().process("test").handler();

                return handler.getLastValue().equals(expected);
            })
        );
    }

    private HekateTestNode createCoordinationNode(CoordinationHandler handler) throws Exception {
        return createNode(c ->
            c.withService(new CoordinationServiceFactory()
                .withProcess(new CoordinationProcessConfig()
                    .withName("test")
                    .withHandler(handler)
                )
            )
        );
    }
}
