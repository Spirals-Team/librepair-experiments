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

import io.hekate.core.internal.util.ErrorUtils;
import io.hekate.messaging.Message;
import io.hekate.messaging.MessagingException;
import io.hekate.messaging.unicast.SendCallback;
import io.hekate.messaging.unicast.StreamFuture;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MessagingChannelStreamTest extends MessagingServiceTestBase {
    public MessagingChannelStreamTest(MessagingTestContext ctx) {
        super(ctx);
    }

    @Test
    public void testFuture() throws Throwable {
        TestChannel sender = createChannel().join();

        TestChannel receiver = createChannel(c -> c.setReceiver(msg -> {
            assertTrue(msg.mustReply());

            for (int i = 0; i < 3; i++) {
                msg.partialReply("response" + i);

                assertTrue(msg.mustReply());
            }

            msg.reply("final");

            assertResponded(msg);
        })).join();

        awaitForChannelsTopology(sender, receiver);

        repeat(5, i -> {
            StreamFuture<String> future = sender.get().forNode(receiver.getNodeId()).stream("request");

            List<String> expected = Arrays.asList("response0", "response1", "response2", "final");

            assertEquals(expected, get(future));

            receiver.checkReceiverError();
        });
    }

    @Test
    public void testCallback() throws Throwable {
        TestChannel sender = createChannel().join();

        TestChannel receiver = createChannel(c -> c.setReceiver(msg -> {
            assertTrue(msg.mustReply());

            for (int i = 0; i < 3; i++) {
                msg.partialReply("response" + i);

                assertTrue(msg.mustReply());
            }

            msg.reply("final");

            assertResponded(msg);
        })).join();

        awaitForChannelsTopology(sender, receiver);

        repeat(5, i -> {
            CompletableFuture<Throwable> errFuture = new CompletableFuture<>();

            List<String> senderMessages = Collections.synchronizedList(new ArrayList<>());

            sender.get().forNode(receiver.getNodeId()).stream("request", (err, reply) -> {
                if (err == null) {
                    try {
                        senderMessages.add(reply.get());

                        if (reply.get().equals("final")) {
                            assertFalse(reply.isPartial());

                            errFuture.complete(null);
                        } else {
                            assertTrue(reply.isPartial());
                        }
                    } catch (Throwable e) {
                        errFuture.complete(e);
                    }
                } else {
                    errFuture.complete(err);
                }
            });

            assertNull(get(errFuture));

            List<String> expectedMessages = Arrays.asList("response0", "response1", "response2", "final");

            assertEquals(expectedMessages, senderMessages);

            receiver.checkReceiverError();
        });
    }

    @Test
    public void testPartialReplyCallback() throws Throwable {
        AtomicReference<SendCallback> replyCallbackRef = new AtomicReference<>();
        AtomicReference<SendCallback> lastReplyCallbackRef = new AtomicReference<>();

        TestChannel sender = createChannel().join();

        TestChannel receiver = createChannel(c -> c.setReceiver(msg -> {
            assertTrue(msg.mustReply());
            assertTrue(msg.isStream());

            assertNotNull(replyCallbackRef.get());

            for (int i = 0; i < 3; i++) {
                msg.partialReply("response" + i, replyCallbackRef.get());

                assertTrue(msg.mustReply());
                assertTrue(msg.isStream());
            }

            msg.reply("final", lastReplyCallbackRef.get());

            assertResponded(msg);
        })).join();

        awaitForChannelsTopology(sender, receiver);

        repeat(5, i -> {
            CompletableFuture<Throwable> sendErrFuture = new CompletableFuture<>();
            CompletableFuture<Throwable> receiveErrFuture = new CompletableFuture<>();

            replyCallbackRef.set(err -> {
                if (err != null) {
                    receiveErrFuture.complete(err);
                }
            });

            lastReplyCallbackRef.set(receiveErrFuture::complete);

            List<String> senderMessages = Collections.synchronizedList(new ArrayList<>());

            sender.get().forNode(receiver.getNodeId()).stream("request", (err, reply) -> {
                if (err == null) {
                    try {
                        senderMessages.add(reply.get());

                        if (reply.get().equals("final")) {
                            assertFalse(reply.isPartial());

                            sendErrFuture.complete(null);
                        } else {
                            assertTrue(reply.isPartial());
                        }
                    } catch (AssertionError e) {
                        sendErrFuture.complete(e);
                        receiveErrFuture.complete(null);
                    }
                } else {
                    sendErrFuture.complete(err);
                    receiveErrFuture.complete(null);
                }
            });

            assertNull(get(receiveErrFuture));
            assertNull(get(sendErrFuture));

            List<String> expectedMessages = Arrays.asList("response0", "response1", "response2", "final");

            assertEquals(expectedMessages, senderMessages);

            receiver.checkReceiverError();
        });
    }

    @Test
    public void testNetworkDisconnectWhileReplying() throws Throwable {
        TestChannel sender = createChannel().join();

        Exchanger<Message<String>> messageExchanger = new Exchanger<>();

        TestChannel receiver = createChannel(c ->
            c.withReceiver(msg -> {
                try {
                    messageExchanger.exchange(msg);
                } catch (InterruptedException e) {
                    fail("Thread was unexpectedly interrupted.");
                }
            })
        ).join();

        awaitForChannelsTopology(sender, receiver);

        sender.get().forNode(receiver.getNodeId()).stream("test");

        Message<String> msg = messageExchanger.exchange(null, 3, TimeUnit.SECONDS);

        receiver.leave();

        Exchanger<Throwable> errExchanger = new Exchanger<>();

        for (int i = 0; i < 10; i++) {
            msg.partialReply("fail", err -> {
                try {
                    errExchanger.exchange(err);
                } catch (InterruptedException e) {
                    fail("Thread was unexpectedly interrupted .");
                }
            });

            Throwable partialErr = errExchanger.exchange(null, 3, TimeUnit.SECONDS);

            assertTrue(getStacktrace(partialErr), partialErr instanceof MessagingException);
            assertTrue(getStacktrace(partialErr), ErrorUtils.isCausedBy(ClosedChannelException.class, partialErr));
        }

        msg.reply("fail", err -> {
            try {
                errExchanger.exchange(err);
            } catch (InterruptedException e) {
                fail("Thread was unexpectedly interrupted .");
            }
        });

        Throwable err = errExchanger.exchange(null, 3, TimeUnit.SECONDS);

        assertTrue(getStacktrace(err), err instanceof MessagingException);
        assertTrue(getStacktrace(err), ErrorUtils.isCausedBy(ClosedChannelException.class, err));
    }
}
