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

import io.hekate.cluster.ClusterNode;
import io.hekate.cluster.ClusterNodeId;
import io.hekate.cluster.ClusterTopology;
import io.hekate.cluster.UpdatableClusterView;
import io.hekate.failover.FailoverPolicy;
import io.hekate.messaging.MessagingChannel;
import io.hekate.messaging.MessagingChannelId;
import io.hekate.messaging.MessagingEndpoint;
import io.hekate.messaging.internal.MessagingProtocol.Connect;
import io.hekate.messaging.internal.MessagingProtocol.Notification;
import io.hekate.messaging.loadbalance.EmptyTopologyException;
import io.hekate.messaging.loadbalance.UnknownRouteException;
import io.hekate.network.NetworkClient;
import io.hekate.network.NetworkConnector;
import io.hekate.network.NetworkService;
import io.hekate.test.NetworkClientCallbackMock;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MessagingChannelTest extends MessagingServiceTestBase {
    public MessagingChannelTest(MessagingTestContext ctx) {
        super(ctx);
    }

    @Test
    public void testChannel() throws Exception {
        TestChannel testChannel = createChannel(c -> c.withMessagingTimeout(100500)).join();

        MessagingChannel<String> channel = testChannel.get();

        assertNotNull(channel.id());
        assertEquals(TEST_CHANNEL_NAME, channel.name());
        assertNotNull(channel.cluster());
        assertThat(channel.cluster().topology().nodes(), hasItem(testChannel.getNode().localNode()));
        assertEquals(nioThreads, channel.nioThreads());
        assertEquals(workerThreads, channel.workerThreads());
        assertNotNull(channel.executor());
        assertEquals(100500, channel.timeout());
    }

    @Test
    public void testIgnoreMessageToInvalidNode() throws Exception {
        TestChannel channel = createChannel().join();

        // Create a fake TCP client via node's TCP service.
        NetworkService net = channel.getNode().network();

        NetworkConnector<MessagingProtocol> fakeConnector = net.connector(TEST_CHANNEL_NAME);

        repeat(5, i -> {
            NetworkClient<MessagingProtocol> fakeClient = fakeConnector.newClient();

            // Try connect to a node by using an invalid node ID.
            ClusterNodeId invalidNodeId = newNodeId();

            InetSocketAddress socketAddress = channel.getNode().getSocketAddress();

            NetworkClientCallbackMock<MessagingProtocol> callback = new NetworkClientCallbackMock<>();

            MessagingChannelId sourceId = new MessagingChannelId();

            fakeClient.connect(socketAddress, new Connect(invalidNodeId, newNodeId(), sourceId), callback);

            fakeClient.send(new Notification<>(false, 0, "fail1"));
            fakeClient.send(new Notification<>(false, 0, "fail2"));
            fakeClient.send(new Notification<>(false, 0, "fail3"));

            // Check that client was disconnected and no messages were received by the server.
            callback.awaitForDisconnects(1);
            assertTrue(channel.getReceived().isEmpty());

            fakeClient.disconnect();
        });
    }

    @Test
    public void testMessagingEndpoint() throws Exception {
        TestChannel sender = createChannel().join();

        AtomicReference<AssertionError> errorRef = new AtomicReference<>();

        TestChannel receiver = createChannel(c -> c.setReceiver(msg -> {
            try {
                MessagingEndpoint<String> endpoint = msg.endpoint();

                assertEquals(sender.getNodeId(), endpoint.remoteNodeId());

                endpoint.setContext("test");
                assertEquals("test", endpoint.getContext());

                endpoint.setContext(null);
                assertNull(endpoint.getContext());

                assertTrue(endpoint.toString(), endpoint.toString().startsWith(MessagingEndpoint.class.getSimpleName()));
            } catch (AssertionError e) {
                errorRef.compareAndSet(null, e);
            }

            if (msg.mustReply()) {
                String response = msg.get() + "-reply";

                msg.reply(response);
            }
        })).join();

        awaitForChannelsTopology(sender, receiver);

        sender.get().forNode(receiver.getNodeId()).request("request").get();

        if (errorRef.get() != null) {
            throw errorRef.get();
        }
    }

    @Test
    public void testMessageState() throws Exception {
        Exchanger<Throwable> errRef = new Exchanger<>();

        createChannel(c -> c.setReceiver(msg -> {
            try {
                switch (msg.get()) {
                    case "send":
                    case "broadcast": {
                        assertFalse(msg.mustReply());
                        assertFalse(msg.isRequest());
                        assertFalse(msg.isStream());

                        assertResponseUnsupported(msg);

                        break;
                    }
                    case "request":
                    case "aggregate": {
                        assertTrue(msg.mustReply());
                        assertTrue(msg.isRequest());
                        assertFalse(msg.isStream());

                        msg.reply("ok");

                        assertFalse(msg.mustReply());
                        assertTrue(msg.isRequest());
                        assertFalse(msg.isStream());

                        assertResponded(msg);

                        break;
                    }
                    case "stream-request": {
                        assertTrue(msg.mustReply());
                        assertTrue(msg.isRequest());
                        assertTrue(msg.isStream());

                        for (int i = 0; i < 5; i++) {
                            msg.partialReply("ok");

                            assertTrue(msg.mustReply());
                            assertTrue(msg.isRequest());
                            assertTrue(msg.isStream());
                        }

                        msg.reply("ok");

                        assertFalse(msg.mustReply());
                        assertTrue(msg.isRequest());
                        assertTrue(msg.isStream());

                        assertResponded(msg);

                        break;
                    }
                    default: {
                        fail("Unexpected message: " + msg);
                    }
                }

                errRef.exchange(null);
            } catch (Throwable t) {
                try {
                    errRef.exchange(t);
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
        })).join();

        TestChannel sender = createChannel().join();

        sender.get().forRemotes().send("send");
        Optional.ofNullable(errRef.exchange(null)).ifPresent(e -> {
            throw new AssertionError(e);
        });

        sender.get().forRemotes().request("request");
        Optional.ofNullable(errRef.exchange(null)).ifPresent(e -> {
            throw new AssertionError(e);
        });

        sender.get().forRemotes().stream("stream-request");
        Optional.ofNullable(errRef.exchange(null)).ifPresent(e -> {
            throw new AssertionError(e);
        });

        sender.get().forRemotes().broadcast("broadcast");
        Optional.ofNullable(errRef.exchange(null)).ifPresent(e -> {
            throw new AssertionError(e);
        });

        sender.get().forRemotes().aggregate("aggregate");
        Optional.ofNullable(errRef.exchange(null)).ifPresent(e -> {
            throw new AssertionError(e);
        });
    }

    @Test
    public void testAffinity() throws Exception {
        MessagingChannel<String> channel = createChannel().join().get();

        assertNull(channel.affinity());
        assertNull(channel.forRemotes().affinity());

        assertEquals("affinity1", channel.withAffinity("affinity1").affinity());
        assertNull(channel.affinity());
        assertNull(channel.forRemotes().affinity());

        assertEquals("affinity2", channel.forRemotes().withAffinity("affinity2").affinity());
        assertNull(channel.affinity());
        assertNull(channel.forRemotes().affinity());

        assertEquals("affinity3", channel.forRemotes().withAffinity("affinity3").forRemotes().affinity());
        assertNull(channel.affinity());
        assertNull(channel.forRemotes().affinity());
    }

    @Test
    public void testFailover() throws Exception {
        MessagingChannel<String> channel = createChannel().join().get();

        assertNull(channel.failover());
        assertNull(channel.forRemotes().failover());

        FailoverPolicy p1 = context -> null;
        FailoverPolicy p2 = context -> null;
        FailoverPolicy p3 = context -> null;

        assertSame(p1, channel.withFailover(p1).failover());
        assertNull(channel.failover());
        assertNull(channel.forRemotes().failover());

        assertSame(p2, channel.forRemotes().withFailover(p2).failover());
        assertNull(channel.failover());
        assertNull(channel.forRemotes().failover());

        assertSame(p3, channel.forRemotes().withFailover(p3).forRemotes().failover());
        assertNull(channel.failover());
        assertNull(channel.forRemotes().failover());
    }

    @Test
    public void testPartitions() throws Exception {
        MessagingChannel<String> channel1 = createChannel(cfg -> cfg
            .withPartitions(64)
            .withBackupNodes(12)
        ).join().get();

        assertNotNull(channel1.partitions());
        assertEquals(64, channel1.partitions().partitions());
        assertEquals(12, channel1.partitions().backupNodes());

        MessagingChannel<String> channel2 = channel1.withPartitions(128, 6);

        assertNotSame(channel1, channel2);
        assertNotSame(channel1.partitions(), channel2.partitions());

        assertEquals(128, channel2.partitions().partitions());
        assertEquals(6, channel2.partitions().backupNodes());

        // Should return self if request partitions and backup nodes are the same with the current one.
        assertSame(channel2, channel2.withPartitions(128, 6));
    }

    @Test
    public void testTimeout() throws Exception {
        MessagingChannel<String> channel = createChannel().join().get();

        assertEquals(0, channel.timeout());

        assertEquals(1000, channel.withTimeout(1, TimeUnit.SECONDS).timeout());
        assertEquals(0, channel.timeout());

        assertEquals(2000, channel.forRemotes().withTimeout(2, TimeUnit.SECONDS).timeout());
        assertEquals(0, channel.timeout());

        assertEquals(3000, channel.forRemotes().withTimeout(3, TimeUnit.SECONDS).forRemotes().timeout());
        assertEquals(0, channel.timeout());
    }

    @Test
    public void testCustomClusterView() throws Exception {
        List<TestChannel> channels = createAndJoinChannels(3, c ->
            c.setReceiver(msg -> msg.reply(msg.get() + "-reply"))
        );

        MessagingChannel<String> originalSender = channels.get(0).get();

        UpdatableClusterView manualCluster = UpdatableClusterView.empty();

        MessagingChannel<String> sender = originalSender.withCluster(manualCluster);

        // Empty cluster with older topology version.
        // ----------------------------------------------------------------------
        assertEquals(0, sender.cluster().topology().version());
        assertTrue(sender.cluster().topology().isEmpty());
        assertEquals(0, sender.partitions().topology().version());
        assertTrue(sender.partitions().topology().isEmpty());

        channels.forEach(c ->
            expectCause(EmptyTopologyException.class, () ->
                get(sender.forNode(c.getNodeId()).request("test"))
            )
        );

        // Empty cluster with the same topology version.
        // ----------------------------------------------------------------------
        manualCluster.update(ClusterTopology.of(originalSender.cluster().topology().version(), emptySet()));

        assertEquals(originalSender.cluster().topology().version(), sender.cluster().topology().version());
        assertTrue(sender.cluster().topology().isEmpty());
        assertEquals(originalSender.cluster().topology().version(), sender.partitions().topology().version());
        assertTrue(sender.partitions().topology().isEmpty());

        channels.forEach(c ->
            expectCause(EmptyTopologyException.class, () ->
                get(sender.forNode(c.getNodeId()).request("test"))
            )
        );

        // Empty cluster with newer topology version.
        // ----------------------------------------------------------------------
        manualCluster.update(ClusterTopology.of(originalSender.cluster().topology().version() + 1, emptySet()));

        assertEquals(originalSender.cluster().topology().version() + 1, sender.cluster().topology().version());
        assertTrue(sender.cluster().topology().isEmpty());
        assertEquals(originalSender.cluster().topology().version() + 1, sender.partitions().topology().version());
        assertTrue(sender.partitions().topology().isEmpty());

        channels.forEach(c ->
            expectCause(EmptyTopologyException.class, () ->
                get(sender.forNode(c.getNodeId()).request("test"))
            )
        );

        // One node in the cluster.
        // ----------------------------------------------------------------------
        for (TestChannel c : channels) {
            manualCluster.update(ClusterTopology.of(manualCluster.topology().version() + 1, singleton(c.getNode().localNode())));

            assertEquals(1, sender.cluster().topology().size());
            assertTrue(sender.cluster().topology().contains(c.getNodeId()));
            assertEquals(1, sender.partitions().topology().size());
            assertTrue(sender.partitions().topology().contains(c.getNodeId()));

            get(sender.forNode(c.getNodeId()).request("test"));
        }

        // New node should not be visible.
        // ----------------------------------------------------------------------
        // Synchronize custom cluster view with live view
        MessagingChannel<String> sender2 = sender.withCluster(UpdatableClusterView.of(originalSender.cluster().topology()));

        assertEquals(3, sender2.cluster().topology().size());
        assertEquals(3, sender2.partitions().topology().size());

        // Join new node.
        channels.add(createChannel(c ->
            c.setReceiver(msg -> msg.reply(msg.get() + "-reply"))
        ).join());

        awaitForChannelsTopology(channels);

        // New node should not be visible.
        expectCause(EmptyTopologyException.class, () ->
            get(sender2.forNode(channels.get(channels.size() - 1).getNodeId()).request("test"))
        );

        // Old nodes should be visible.
        for (int i = 0; i < channels.size() - 1 /* <- Exclude newly added node */; i++) {
            get(sender2.forNode(channels.get(i).getNodeId()).request("test"));
        }

        // Removed node should be still visible.
        // ----------------------------------------------------------------------
        // Synchronize custom cluster view with live view
        MessagingChannel<String> sender3 = sender.withCluster(UpdatableClusterView.of(originalSender.cluster().topology()));

        assertEquals(4, sender3.cluster().topology().size());
        assertEquals(4, sender3.partitions().topology().size());

        TestChannel removed = channels.get(channels.size() - 1).leave();

        channels.remove(removed);

        awaitForChannelsTopology(channels);

        assertEquals(4, sender3.cluster().topology().size());

        expectCause(UnknownRouteException.class, () ->
            get(sender3.forNode(removed.getNodeId()).request("test"))
        );

        // Non-channel node should be filtered out.
        // ----------------------------------------------------------------------
        ClusterNode fakeNode = newNode();

        Set<ClusterNode> fakeNodes = new HashSet<>(originalSender.cluster().topology().nodes());
        fakeNodes.add(fakeNode);

        ClusterTopology fakeTopology = ClusterTopology.of(originalSender.cluster().topology().version(), fakeNodes);

        MessagingChannel<String> sender4 = sender.withCluster(UpdatableClusterView.of(fakeTopology));

        assertEquals(3, sender4.cluster().topology().size());
        assertFalse(sender4.cluster().topology().contains(fakeNode));
    }
}
