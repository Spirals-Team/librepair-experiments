/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.tests.e2e.sse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.SseEventSource;

import javax.inject.Singleton;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Assert;
import org.junit.Test;

/**
 * JAX-RS {@link javax.ws.rs.sse.SseBroadcaster} test.
 *
 * @author Adam Lindenthal (adam.lindenthal at oracle.com)
 */
public class BroadcasterTest extends JerseyTest {

    static final CountDownLatch closeLatch = new CountDownLatch(4);
    static final CountDownLatch txLatch = new CountDownLatch(4);
    private static boolean isSingleton = false;

    @Path("sse")
    @Singleton
    public static class SseResource {
        private final Sse sse;
        private SseBroadcaster broadcaster;

        public SseResource(@Context final Sse sse) {
            this.sse = sse;
            broadcaster = sse.newBroadcaster();
        }

        @GET
        @Produces(MediaType.SERVER_SENT_EVENTS)
        @Path("events")
        public void getServerSentEvents(@Context final SseEventSink eventSink, @Context final Sse sse) {
            isSingleton = this.sse == sse;
            eventSink.send(sse.newEventBuilder().data("Event1").build());
            eventSink.send(sse.newEventBuilder().data("Event2").build());
            eventSink.send(sse.newEventBuilder().data("Event3").build());
            broadcaster.register(eventSink);
            broadcaster.onClose((subscriber) -> {
                if (subscriber == eventSink) {
                    closeLatch.countDown();
                }
            });
            txLatch.countDown();
        }

        @Path("push/{msg}")
        @GET
        public String pushMessage(@PathParam("msg") final String msg) {
            broadcaster.broadcast(sse.newEventBuilder().data(msg).build());
            txLatch.countDown();
            return "Broadcasting message: " + msg;
        }

        @Path("close")
        @GET
        public String close() {
            broadcaster.close();
            return "Closed.";
        }
    }

    @Override
    protected Application configure() {
        final ResourceConfig rc = new ResourceConfig(SseResource.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);
        return rc;
    }

    @Test
    public void test() throws InterruptedException {
        SseEventSource eventSourceA = SseEventSource.target(target().path("sse/events")).build();
        List<String> resultsA1 = new ArrayList<>();
        List<String> resultsA2 = new ArrayList<>();
        CountDownLatch a1Latch = new CountDownLatch(5);
        CountDownLatch a2Latch = new CountDownLatch(5);
        eventSourceA.register((event) -> {
            resultsA1.add(event.readData());
            a1Latch.countDown();
        });
        eventSourceA.register((event) -> {
            resultsA2.add(event.readData());
            a2Latch.countDown();
        });
        eventSourceA.open();

        target().path("sse/push/firstBroadcast").request().get(String.class);


        SseEventSource eventSourceB = SseEventSource.target(target().path("sse/events")).build();
        List<String> resultsB1 = new ArrayList<>();
        List<String> resultsB2 = new ArrayList<>();
        CountDownLatch b1Latch = new CountDownLatch(4);
        CountDownLatch b2Latch = new CountDownLatch(4);
        eventSourceB.register((event) -> {
            resultsB1.add(event.readData());
            b1Latch.countDown();
        });
        eventSourceB.register((event) -> {
            resultsB2.add(event.readData());
            b2Latch.countDown();
        });
        eventSourceB.open();

        target().path("sse/push/secondBroadcast").request().get(String.class);

        Assert.assertTrue("Waiting for resultsA1 to be complete failed.",
                a1Latch.await(3000, TimeUnit.MILLISECONDS));
        Assert.assertTrue("Waiting for resultsA2 to be complete failed.",
                a2Latch.await(3000, TimeUnit.MILLISECONDS));

        Assert.assertTrue("Waiting for resultsB1 to be complete failed.",
                b1Latch.await(3000, TimeUnit.MILLISECONDS));
        Assert.assertTrue("Waiting for resultsB2 to be complete failed.",
                b2Latch.await(3000, TimeUnit.MILLISECONDS));

        Assert.assertTrue(txLatch.await(5000, TimeUnit.MILLISECONDS));

        // Event1, Event2, Event3, firstBroadcast, secondBroadcast
        Assert.assertEquals("resultsA1 does not contain 5 elements.", 5, resultsA1.size());
        Assert.assertEquals("resultsA2 does not contain 5 elements.", 5, resultsA2.size());
        Assert.assertTrue("resultsA1 does not contain expected data",
                resultsA1.get(0).equals("Event1")
                        && resultsA1.get(1).equals("Event2")
                        && resultsA1.get(2).equals("Event3")
                        && resultsA1.get(3).equals("firstBroadcast")
                        && resultsA1.get(4).equals("secondBroadcast"));

        Assert.assertTrue("resultsA2 does not contain expected data",
                resultsA2.get(0).equals("Event1")
                        && resultsA2.get(1).equals("Event2")
                        && resultsA2.get(2).equals("Event3")
                        && resultsA2.get(3).equals("firstBroadcast")
                        && resultsA2.get(4).equals("secondBroadcast"));

        Assert.assertEquals("resultsB1 does not contain 4 elements.", 4, resultsB1.size());
        Assert.assertEquals("resultsB2 does not contain 4 elements.", 4, resultsB2.size());
        Assert.assertTrue("resultsB1 does not contain expected data",
                resultsB1.get(0).equals("Event1")
                        && resultsB1.get(1).equals("Event2")
                        && resultsB1.get(2).equals("Event3")
                        && resultsB1.get(3).equals("secondBroadcast"));

        Assert.assertTrue("resultsB2 does not contain expected data",
                resultsB2.get(0).equals("Event1")
                        && resultsB2.get(1).equals("Event2")
                        && resultsB2.get(2).equals("Event3")
                        && resultsB2.get(3).equals("secondBroadcast"));
        target().path("sse/close").request().get();
        Assert.assertTrue(closeLatch.await(3000, TimeUnit.MILLISECONDS));
        Assert.assertTrue("Sse instances injected into resource and constructor differ. Sse should have been injected"
                + "as a singleton", isSingleton);
    }
}
