package org.corfudb.infrastructure;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AttributeKey;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

import org.corfudb.protocols.wireprotocol.CorfuMsg;
import org.corfudb.protocols.wireprotocol.CorfuMsgType;
import org.corfudb.protocols.wireprotocol.CorfuPayloadMsg;
import org.corfudb.protocols.wireprotocol.HandshakeMsg;
import org.corfudb.protocols.wireprotocol.HandshakeResponse;

/**
 * The ServerHandshakeHandler waits for the handshake message, validates and sends
 * a response to the client. This reply contains its node id and current version of Corfu.
 *
 * Created by amartinezman on 12/11/17.
 */
@Slf4j
public class ServerHandshakeHandler extends ChannelDuplexHandler {

    private final UUID nodeId;
    private final String corfuVersion;
    private final AtomicBoolean handshakeComplete;
    private final AtomicBoolean handshakeFailed;
    private final Queue<CorfuMsg> messages = new ArrayDeque();
    private static final  AttributeKey<UUID> clientIdAttrKey = AttributeKey.valueOf("ClientID");

    public ServerHandshakeHandler(UUID nodeId, String corfuVersion) {
        this.nodeId = nodeId;
        this.corfuVersion = corfuVersion;
        this.handshakeComplete = new AtomicBoolean(false);
        this.handshakeFailed = new AtomicBoolean(false);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object m) throws Exception {

        if (this.handshakeFailed.get()) {
            return;
        }

        if (this.handshakeComplete.get()) {
            // If handshake completed successfully, but still a message came through this handler,
            // send on to the next handler in order to avoid message loss.
            super.channelRead(ctx, m);
            return;
        }

        CorfuPayloadMsg<HandshakeMsg> handshake;

        try {
            handshake = (CorfuPayloadMsg<HandshakeMsg>) m;
            log.debug("channelRead: Handshake Message received. Removing readTimeoutHandler " +
                    "from pipeline.");
            ctx.pipeline().remove("readTimeoutHandler");
        } catch (ClassCastException e) {
            log.warn("channelRead: Non-handshake message received by handshake handler." +
                    " Send upstream only if handshake succeeded.");
            if (this.handshakeComplete.get()) {
                // Only send upstream if handshake is complete.
                super.channelRead(ctx, m);
            }
            return;
        }

        UUID clientId = handshake.getPayload().getClientId();
        UUID serverId = handshake.getPayload().getServerId();

        // Validate handshake
        if (!serverId.equals(this.nodeId)) {
            log.error("channelRead: Invalid handshake: this is " + this.nodeId +
                    " and client is trying to connect to " + serverId);
            this.fireHandshakeFailed(ctx);
            return;
        }

        // Store clientID as a channel attribute.
        ctx.channel().attr(clientIdAttrKey).set(clientId);
        log.info("channelRead: Handshake validated by Server.");
        log.debug("channelRead: Sending handshake response: Node Id: " + this.nodeId +
                " Corfu Version: " + this.corfuVersion);

        CorfuMsg handshakeResponse = CorfuMsgType.HANDSHAKE_RESPONSE
                .payloadMsg(new HandshakeResponse(this.nodeId, this.corfuVersion));
        ctx.writeAndFlush(handshakeResponse);

        // Flush messages in queue
        log.debug("channelRead: There are {" + this.messages.size() + "} messages in queue to " +
                "be flushed.");
        for (CorfuMsg message : this.messages) {
            ctx.writeAndFlush(message);
        }

        // Remove this handler from the pipeline; handshake is completed.
        log.info("channelRead: Removing handshake handler from pipeline.");
        ctx.pipeline().remove(this);
        this.fireHandshakeSucceeded();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive: Incoming connection established from: " +
                ctx.channel().remoteAddress() + " Start Read Timeout.");
        ctx.pipeline().addBefore(ctx.name(), "readTimeoutHandler",
                new ReadTimeoutHandler(10));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelInactive: Channel closed.");
        if (!this.handshakeComplete.get()) {
            this.fireHandshakeFailed(ctx);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error("exceptionCaught: Exception caught: " + cause.getMessage());

        if (cause instanceof ReadTimeoutException) {
            // Read timeout: no inbound traffic detected in a period of time.
            if (handshakeFailed.get()) {
                log.debug("exceptionCaught: Handshake timeout checker: already failed.");
                return;
            }

            if (!handshakeComplete.get()) {
                log.error("exceptionCaught: Handshake timeout checker: timed out. Close Connection.");
                handshakeFailed.set(true);
                ctx.channel().close();
            } else {
                log.debug("exceptionCaught: Handshake timeout " +
                        "checker: discarded (handshake OK)");
            }
        } else {
            super.exceptionCaught(ctx, cause);
        }

        if (ctx.channel().isActive()) {
            // Closing the channel will trigger handshake failure.
            ctx.channel().close();
        } else {
            // Channel did not open, fire handshake failure.
            this.fireHandshakeFailed(ctx);
        }
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
            throws Exception {
        if (this.handshakeFailed.get()) {
            // If handshake failed, discard messages.
            return;
        }

        if (this.handshakeComplete.get()) {
            log.debug("write: Handshake already completed, not appending corfu message to queue");
            super.write(ctx, msg, promise);
        } else {
            this.messages.offer((CorfuMsg) msg);
        }
    }


    private void fireHandshakeFailed(ChannelHandlerContext ctx) {
        this.handshakeComplete.set(true);
        this.handshakeFailed.set(true);
        ctx.channel().close();
    }

    private void fireHandshakeSucceeded() {
        this.handshakeComplete.set(true);
        this.handshakeFailed.set(false);
    }
}

