/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package io.ballerina.messaging.broker.amqp.codec.handlers;

import io.ballerina.messaging.broker.amqp.AmqpConnectionManager;
import io.ballerina.messaging.broker.amqp.AmqpServerConfiguration;
import io.ballerina.messaging.broker.amqp.codec.AmqpChannel;
import io.ballerina.messaging.broker.amqp.codec.BlockingTask;
import io.ballerina.messaging.broker.amqp.codec.ConnectionException;
import io.ballerina.messaging.broker.amqp.codec.frames.AmqpBadMessage;
import io.ballerina.messaging.broker.amqp.codec.frames.ConnectionStart;
import io.ballerina.messaging.broker.amqp.codec.frames.GeneralFrame;
import io.ballerina.messaging.broker.amqp.codec.frames.ProtocolInitFrame;
import io.ballerina.messaging.broker.amqp.metrics.AmqpMetricManager;
import io.ballerina.messaging.broker.core.Broker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty handler for handling an AMQP connection.
 */
public class AmqpConnectionHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpConnectionHandler.class);
    private final Map<Integer, AmqpChannel> channels = new HashMap<>();
    private final AmqpServerConfiguration configuration;
    private Broker broker;
    private final AmqpMetricManager metricManager;
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private final int id;
    private String ip;
    private AmqpConnectionManager connectionManager;

    public AmqpConnectionHandler(AmqpServerConfiguration configuration,
            AmqpMetricManager metricManager, AmqpConnectionManager amqpConnectionManager) {
        this.id = idGenerator.incrementAndGet();
        this.configuration = configuration;
        this.metricManager = metricManager;
        this.connectionManager = amqpConnectionManager;
        metricManager.incrementConnectionCount();
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ip = ctx.channel().remoteAddress().toString();
        connectionManager.addConnectionHandler(this);
        ctx.channel().closeFuture().addListener(future -> ctx.fireChannelRead((BlockingTask) this::onConnectionClose));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ProtocolInitFrame) {
            handleProtocolInit(ctx, (ProtocolInitFrame) msg);
        } else if (msg instanceof GeneralFrame) {
            ((GeneralFrame) msg).handle(ctx, this);
        } else if (msg instanceof AmqpBadMessage) {
            LOGGER.warn("Bad message received", ((AmqpBadMessage) msg).getCause());
            // TODO need to send error back to client
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.warn("Exception while handling request", cause);
        ctx.close();
    }

    private void onConnectionClose() {
        closeAllChannels();
        metricManager.decrementConnectionCount();
        connectionManager.removeConnectionHandler(this);
    }

    private void handleProtocolInit(ChannelHandlerContext ctx, ProtocolInitFrame msg) {
        if (ProtocolInitFrame.V_091.equals(msg)) {
            ctx.writeAndFlush(ConnectionStart.DEFAULT_FRAME);
        } else {
            ctx.writeAndFlush(ProtocolInitFrame.V_091);
        }
    }

    public void createChannel(int channelId) throws ConnectionException {
        AmqpChannel channel = channels.get(channelId);
        if (channel != null) {
            throw new ConnectionException(ConnectionException.CHANNEL_ERROR,
                    "Channel ID " + channelId + " Already exists");
        }
        channels.put(channelId, new AmqpChannel(configuration, broker, channelId, metricManager));
        metricManager.incrementChannelCount();
    }

    /**
     * Returns the {@link AmqpChannel} for the specified channelId.
     *
     * @param channelId channel id
     * @return AmqpChannel
     */
    public AmqpChannel getChannel(int channelId) {
        return channels.get(channelId);
    }

    public void closeChannel(int channelId) {
        AmqpChannel channel = channels.remove(channelId);
        if (Objects.nonNull(channel)) {
            closeChannel(channel);
        }
    }

    private void closeChannel(AmqpChannel channel) {
        metricManager.decrementChannelCount();
        channel.close();
    }

    public void closeAllChannels() {
        for (AmqpChannel channel: channels.values()) {
            closeChannel(channel);
        }

        channels.clear();
    }

    /**
     * Returns the {@link Broker} for the amq connection.
     *
     * @return Broker
     */
    public Broker getBroker() {
        return broker;
    }

    /**
     * Attach relevant broker implementation to the amq connection.
     *
     * @param broker a broker instance
     */
    public void attachBroker(Broker broker) {
        this.broker = broker;
    }

    /**
     * Returns the @{@link AmqpServerConfiguration} for the amq connection.
     *
     * @return Configuration
     */
    public AmqpServerConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Returns the unique identifier representing the connection handler.
     *
     * @return identifier
     */
    public final int getId() {
        return id;
    }

    /**
     * Returns the remote address of the connection that is established.
     *
     * @return the remote address in the format of /ip:port
     */
    public String getIp() {
        return ip;
    }

    /**
     * Returns the AMQP channels that are created using the underlying connection.
     *
     * @return a list of {@link AmqpChannel}
     */
    public Map<Integer, AmqpChannel> getChannels() {
        return channels;
    }
}
