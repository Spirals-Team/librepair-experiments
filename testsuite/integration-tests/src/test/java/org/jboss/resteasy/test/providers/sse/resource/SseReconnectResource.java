package org.jboss.resteasy.test.providers.sse.resource;

import org.hibernate.validator.constraints.br.CPF;
import org.jboss.logging.Logger;
import org.junit.Assert;

import javax.ejb.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.concurrent.TimeUnit;

@Singleton
@Path("/reconnect")
public class SseReconnectResource {

    private volatile boolean isServiceAvailable = false;
    private volatile boolean close = true;
    private volatile boolean unavailableRetryAfterCompleted = false;

    private volatile long startTime = 0L;
    private volatile long elapsedTime = 0L;

    private volatile SseEventSink eventSink;

    private final static Logger logger = Logger.getLogger(SseReconnectResource.class);

    @GET
    @Path("/defaultReconnectDelay")
    @Produces(MediaType.TEXT_PLAIN)
    public Response reconnectDelayNotSet(@Context Sse sse, @Context SseEventSink sseEventSink) {
        OutboundSseEvent event = sse.newEventBuilder().id("1").data("test").build();
        return Response.ok(event.getReconnectDelay()).build();
    }

    @GET
    @Path("/reconnectDelaySet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response reconnectDelaySet(@Context Sse sse, @Context SseEventSink sseEventSink) {
        OutboundSseEvent event = sse.newEventBuilder().id("1").data("test").reconnectDelay(1000L).build();
        return Response.ok(event.getReconnectDelay()).build();
    }

    @GET
    @Path("/unavailable")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void sendMessageUnavailableRetryAfter(@Context SseEventSink sink, @Context Sse sse)
    {
        unavailableRetryAfter(sink, sse);
    }

    @GET
    @Path("/default")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void sendMessageDefaultReconnect(@Context SseEventSink sink, @Context Sse sse)
    {
        defaultReconnect(sink, sse);
    }

    @GET
    @Path("/unavailableAndDefault")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void sendMessageUnavailableAndDefault(@Context SseEventSink sink, @Context Sse sse) {
        if (!unavailableRetryAfterCompleted) {
            unavailableRetryAfter(sink, sse);
        }
        defaultReconnect(sink, sse);
    }

    private void unavailableRetryAfter(SseEventSink sink, Sse sse) {
        this.eventSink = sink;
        if (!isServiceAvailable) {
            isServiceAvailable = true;
            startTime = System.currentTimeMillis();
            throw new WebApplicationException(Response.status(503).header(HttpHeaders.RETRY_AFTER, String.valueOf(2))
                    .build());
        } else {
            elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("unavailableRetryAfter - Elapsed: " + elapsedTime + "ms");
            Assert.assertTrue(elapsedTime >= 2000);
            this.eventSink.send(sse.newEvent("ServiceAvailable"));
            isServiceAvailable = false;
            unavailableRetryAfterCompleted = true;
        }
    }

    private void defaultReconnect(SseEventSink sink, Sse sse) {
        this.eventSink = sink;
        if (close) {
            this.eventSink.close();
            startTime = System.currentTimeMillis();
            close = false;
        } else {
            elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("defaultReconnect - Elapsed: " + elapsedTime + "ms");
            Assert.assertTrue(elapsedTime >= 500 && elapsedTime <= 1000);
            close = true;
            this.eventSink.send(sse.newEvent("ServiceAvailable"));
        }
    }
}
