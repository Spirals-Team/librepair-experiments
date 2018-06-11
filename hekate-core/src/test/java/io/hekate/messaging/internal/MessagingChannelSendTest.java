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

package io.hekate.messaging.internal;

import io.hekate.cluster.ClusterNodeId;
import io.hekate.codec.CodecException;
import io.hekate.core.internal.HekateTestNode;
import io.hekate.core.internal.util.ErrorUtils;
import io.hekate.messaging.MessageInterceptor;
import io.hekate.messaging.MessagingChannelClosedException;
import io.hekate.messaging.MessagingChannelConfig;
import io.hekate.messaging.MessagingException;
import io.hekate.messaging.MessagingFutureException;
import io.hekate.messaging.MessagingServiceFactory;
import io.hekate.messaging.loadbalance.EmptyTopologyException;
import io.hekate.messaging.loadbalance.LoadBalancerException;
import io.hekate.messaging.loadbalance.UnknownRouteException;
import io.hekate.messaging.unicast.SendFuture;
import io.hekate.network.NetworkFuture;
import io.hekate.test.HekateTestError;
import io.hekate.util.async.Waiting;
import java.io.NotSerializableException;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MessagingChannelSendTest extends MessagingServiceTestBase {
    public MessagingChannelSendTest(MessagingTestContext ctx) {
        super(ctx);
    }

    @Test
    public void testNoWait() throws Exception {
        List<TestChannel> channels = createAndJoinChannels(3);

        for (TestChannel from : channels) {
            for (TestChannel to : channels) {
                from.send(to.getNodeId(), "test-" + from.getNodeId());
            }
        }

        for (TestChannel to : channels) {
            for (TestChannel from : channels) {
                to.awaitForMessage("test-" + from.getNodeId());
            }
        }
    }

    @Test
    public void testCallback() throws Exception {
        List<TestChannel> channels = createAndJoinChannels(3);

        for (TestChannel from : channels) {
            for (TestChannel to : channels) {
                String msg = "test-" + from.getNodeId() + "-" + to.getNodeId();

                from.sendWithSyncCallback(to.getNodeId(), msg);
            }
        }

        for (TestChannel to : channels) {
            for (TestChannel from : channels) {
                String msg = "test-" + from.getNodeId() + "-" + to.getNodeId();

                to.awaitForMessage(msg);
            }
        }
    }

    @Test
    public void testFuture() throws Exception {
        List<TestChannel> channels = createAndJoinChannels(3);

        for (TestChannel from : channels) {
            for (TestChannel to : channels) {
                String msg1 = "test1-" + from.getNodeId();
                String msg2 = "test2-" + from.getNodeId();

                from.get().forNode(to.getNodeId()).send(msg1).get();
                from.get().forNode(to.getNodeId()).send(msg2).getUninterruptedly();
            }
        }

        for (TestChannel to : channels) {
            for (TestChannel from : channels) {
                to.awaitForMessage("test1-" + from.getNodeId());
                to.awaitForMessage("test2-" + from.getNodeId());
            }
        }
    }

    @Test(expected = EmptyTopologyException.class)
    public void testUnknownNodeCallback() throws Exception {
        TestChannel channel = createChannel().join();

        channel.sendWithSyncCallback(newNodeId(), "failed");
    }

    @Test
    public void testUnknownNodeFuture() throws Exception {
        TestChannel channel = createChannel().join();

        try {
            channel.send(newNodeId(), "failed").get();

            fail("Error was expected.");
        } catch (MessagingFutureException e) {
            assertTrue("" + e, e.isCausedBy(EmptyTopologyException.class));
        }
    }

    @Test
    public void testIdleSocketTimeout() throws Exception {
        repeat(3, j -> {
            int idlePoolTimeout = 20 * (j + 1);

            TestChannel sender = createChannel(c -> c.setIdleSocketTimeout(idlePoolTimeout)).join();

            TestChannel receiver = createChannel(c -> c.setIdleSocketTimeout(idlePoolTimeout)).join();

            awaitForChannelsTopology(sender, receiver);

            MessagingClient<String> client = sender.getImpl().clientOf(receiver.getNodeId());

            repeat(3, i -> {
                assertFalse(client.isConnected());

                sender.send(receiver.getNodeId(), "test-" + i).get();

                busyWait("disconnect idle", () -> !client.isConnected());

                assertFalse(client.isConnected());
            });

            receiver.awaitForMessage("test-2");
            assertEquals(3, receiver.getReceived().size());

            sender.leave();
            receiver.leave();
        });
    }

    @Test
    public void testReplyIsNotSupported() throws Throwable {
        TestChannel sender = createChannel().join();

        TestChannel receiver = createChannel(c -> c.setReceiver(msg -> {
            assertFalse(msg.mustReply());

            assertResponseUnsupported(msg);
        })).join();

        awaitForChannelsTopology(sender, receiver);

        repeat(5, i -> {
            sender.sendWithSyncCallback(receiver.getNodeId(), "request");

            receiver.checkReceiverError();
        });
    }

    @Test
    public void testNetworkDisconnect() throws Throwable {
        TestChannel sender = createChannel().join();
        TestChannel receiver = createChannel().join();

        awaitForChannelsTopology(sender, receiver);

        MessagingClient<String> client = sender.getImpl().clientOf(receiver.getNodeId());

        List<NetworkFuture<MessagingProtocol>> closeFuture = client.close();

        for (NetworkFuture<MessagingProtocol> future : closeFuture) {
            future.get();
        }

        repeat(5, i -> {
            try {
                sender.sendWithSyncCallback(receiver.getNodeId(), "request" + i);

                fail("Error was expected.");
            } catch (MessagingException e) {
                assertSame(ErrorUtils.stackTrace(e), ClosedChannelException.class, e.getCause().getClass());
            }
        });
    }

    @Test
    public void testChannelCloseDuringRouting() throws Exception {
        repeat(3, i -> {
            TestChannel sender = createChannel().join();
            TestChannel receiver = createChannel().join();

            awaitForChannelsTopology(sender, receiver);

            CountDownLatch routeLatch = new CountDownLatch(1);
            CountDownLatch closeLatch = new CountDownLatch(1);

            Future<SendFuture> future = runAsync(() -> sender.withLoadBalancer((msg, topology) -> {
                routeLatch.countDown();

                await(closeLatch);

                return receiver.getNodeId();
            }).send("test"));

            await(routeLatch);

            Waiting close = sender.getImpl().close();

            closeLatch.countDown();

            close.await();

            try {
                SendFuture sendFuture = get(future);

                get(sendFuture);

                fail("Error was expected.");
            } catch (MessagingFutureException e) {
                Throwable cause = e.getCause();

                assertTrue(getStacktrace(cause), cause instanceof MessagingChannelClosedException);
            }

            sender.leave();
            receiver.leave();
        });
    }

    @Test
    public void testTopologyChange() throws Throwable {
        TestChannel sender = createChannel().join();
        TestChannel receiver = createChannel().join();

        awaitForChannelsTopology(sender, receiver);

        repeat(5, i -> {
            say("Topology change on join.");

            CountDownLatch beforeJoinLatch = new CountDownLatch(1);
            CountDownLatch joinLatch = new CountDownLatch(1);
            AtomicInteger joinInvocations = new AtomicInteger();
            SendCallbackMock joinCallback = new SendCallbackMock();

            runAsync(() -> {
                sender.withLoadBalancer((msg, topology) -> {
                    beforeJoinLatch.countDown();

                    joinInvocations.incrementAndGet();

                    await(joinLatch);

                    return topology.youngest().id();
                }).send("join-request-" + i, joinCallback);

                return null;
            });

            await(beforeJoinLatch);

            TestChannel temporary = createChannel().join();

            awaitForChannelsTopology(sender, receiver, temporary);

            joinLatch.countDown();

            joinCallback.get();

            receiver.awaitForMessage("join-request-" + i);

            assertEquals(1, joinInvocations.get());

            say("Topology change on leave.");

            CountDownLatch beforeLeaveLatch = new CountDownLatch(1);
            CountDownLatch leaveLatch = new CountDownLatch(1);
            AtomicInteger leaveInvocations = new AtomicInteger();
            SendCallbackMock leaveCallback = new SendCallbackMock();

            runAsync(() -> {
                sender.withLoadBalancer((msg, topology) -> {
                    beforeLeaveLatch.countDown();

                    leaveInvocations.incrementAndGet();

                    await(leaveLatch);

                    return topology.youngest().id();
                }).send("leave-request-" + i, leaveCallback);

                return null;
            });

            await(beforeLeaveLatch);

            temporary.leave();

            awaitForChannelsTopology(sender, receiver);

            leaveLatch.countDown();

            leaveCallback.get();

            assertEquals(2, leaveInvocations.get());
        });
    }

    @Test
    public void testLoadBalanceFailure() throws Throwable {
        TestChannel sender = createChannel().join();
        TestChannel receiver = createChannel().join();

        awaitForChannelsTopology(sender, receiver);

        repeat(3, i -> {
            SendFuture future = sender.withLoadBalancer((msg, topology) -> {
                throw new LoadBalancerException(HekateTestError.MESSAGE);
            }).send("failed" + i);

            try {
                future.get();
            } catch (MessagingFutureException e) {
                assertTrue(e.isCausedBy(LoadBalancerException.class));
                assertEquals(HekateTestError.MESSAGE, e.getCause().getMessage());
            }
        });

        sender.send(receiver.getNodeId(), "success").get();
    }

    @Test
    public void testLoadBalanceReturnsNull() throws Throwable {
        TestChannel sender = createChannel().join();
        TestChannel receiver = createChannel().join();

        awaitForChannelsTopology(sender, receiver);

        repeat(3, i -> {
            SendFuture future = sender.withLoadBalancer((msg, topology) -> null).send("failed" + i);

            try {
                future.get();
            } catch (MessagingFutureException e) {
                assertTrue(e.isCausedBy(LoadBalancerException.class));
                assertEquals("Load balancer failed to select a target node.", e.getCause().getMessage());
            }
        });

        sender.send(receiver.getNodeId(), "success").get();
    }

    @Test
    public void testRouteToNonExistingNode() throws Throwable {
        TestChannel sender = createChannel().join();
        TestChannel receiver = createChannel().join();

        awaitForChannelsTopology(sender, receiver);

        ClusterNodeId invalidNodeId = newNodeId();

        repeat(3, i -> {
            SendFuture future = sender.withLoadBalancer((msg, topology) -> invalidNodeId).send("failed" + i);

            try {
                future.get();
            } catch (MessagingFutureException e) {
                assertTrue(e.isCausedBy(UnknownRouteException.class));
                assertEquals("Node is not within the channel topology [id=" + invalidNodeId + ']', e.getCause().getMessage());
            }
        });

        sender.send(receiver.getNodeId(), "success").get();
    }

    @Test
    public void testNoReceiver() throws Exception {
        TestChannel channel = createChannel(c ->
            c.withClusterFilter(n -> !n.isLocal())
        ).join();

        try {
            get(channel.send(channel.getNodeId(), "test"));
        } catch (MessagingFutureException e) {
            assertTrue(e.getCause().toString(), e.getCause() instanceof LoadBalancerException);
            assertEquals("No suitable receivers [channel=test-channel]", e.getCause().getMessage());
        }

        try {
            channel.sendWithSyncCallback(channel.getNodeId(), "test");
        } catch (LoadBalancerException e) {
            assertEquals("No suitable receivers [channel=test-channel]", e.getMessage());
        }
    }

    @Test
    public void testClosedChannel() throws Exception {
        TestChannel channel = createChannel();

        channel.join();

        get(channel.send(channel.getNodeId(), "test"));

        channel.leave();

        try {
            get(channel.send(channel.getNodeId(), "test"));
        } catch (MessagingFutureException e) {
            assertTrue(e.getCause().toString(), e.getCause() instanceof MessagingChannelClosedException);
            assertEquals("Channel closed [channel=test-channel]", e.getCause().getMessage());
        }

        try {
            channel.sendWithSyncCallback(channel.getNodeId(), "test");
        } catch (MessagingChannelClosedException e) {
            assertEquals("Channel closed [channel=test-channel]", e.getMessage());
        }
    }

    @Test
    public void testInterceptor() throws Exception {
        List<TestChannel> channels = createAndJoinChannels(3, c ->
            c.setInterceptor(new MessageInterceptor<String>() {
                @Override
                public String interceptOutbound(String msg, OutboundContext ctx) {
                    return msg + "-###";
                }

                @Override
                public String interceptInbound(String msg, InboundContext ctx) {
                    return msg + "-@@@";
                }

                @Override
                public String interceptReply(String msg, ReplyContext ctx) {
                    throw new UnsupportedOperationException("Unexpected reply.");
                }
            })
        );

        for (TestChannel from : channels) {
            for (TestChannel to : channels) {
                String msg1 = "test1-" + from.getNodeId();
                String msg2 = "test2-" + from.getNodeId();

                from.get().forNode(to.getNodeId()).send(msg1).get();
                from.get().forNode(to.getNodeId()).send(msg2).getUninterruptedly();
            }
        }

        for (TestChannel to : channels) {
            for (TestChannel from : channels) {
                to.awaitForMessage("test1-" + from.getNodeId() + "-###-@@@");
                to.awaitForMessage("test2-" + from.getNodeId() + "-###-@@@");
            }
        }
    }

    @Test
    public void testNonSerializableMessage() throws Exception {
        createNode(boot -> boot.withService(MessagingServiceFactory.class, f -> {
            f.withChannel(MessagingChannelConfig.of(Object.class)
                .withName("test")
                .withReceiver(msg -> {
                    // No-op.
                })
            );
        })).join();

        HekateTestNode sender = createNode(boot -> boot.withService(MessagingServiceFactory.class, f -> {
            f.withChannel(MessagingChannelConfig.of(Object.class)
                .withName("test")
            );
        })).join();

        repeat(5, i -> {
            MessagingFutureException err = expect(MessagingFutureException.class, () ->
                get(sender.messaging().channel("test").forRemotes().send(new Socket()))
            );

            assertSame(err.toString(), MessagingException.class, err.getCause().getClass());
            assertTrue(err.isCausedBy(CodecException.class));
            assertTrue(err.isCausedBy(NotSerializableException.class));
        });
    }
}
