/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.terminal.http.server;

import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedBinaryMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import org.aesh.terminal.http.HttpTtyConnection;
import org.xnio.ChannelListener;
import org.xnio.Pooled;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
public class WebSocketTtyConnection extends HttpTtyConnection {

    private static Logger log = Logger.getLogger(WebSocketTtyConnection.class.getName());
    private WebSocketChannel webSocketChannel;
    private final ScheduledExecutorService executor;
    private Set<WebSocketChannel> readonlyChannels = new HashSet<>();

    @Override
    protected void write(byte[] buffer) {
        if (isOpen()) {
            sendBinary(buffer, webSocketChannel);
        }
        readonlyChannels.forEach((wsChannel) -> sendBinary(buffer, wsChannel));
    }

    private void sendBinary(byte[] buffer, WebSocketChannel webSocketChannel) {
        WebSockets.sendBinary(ByteBuffer.wrap(buffer), webSocketChannel, null);
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public void schedule(Runnable task, long delay, TimeUnit unit) {
        executor.schedule(task, delay, unit);
    }

    public WebSocketTtyConnection(WebSocketChannel webSocketChannel, ScheduledExecutorService executor) {
        this.webSocketChannel = webSocketChannel;
        this.executor = executor;

        registerWebSocketChannelListener(webSocketChannel);
        webSocketChannel.resumeReceives();
    }

    private void registerWebSocketChannelListener(WebSocketChannel webSocketChannel) {
        ChannelListener<WebSocketChannel> listener = new AbstractReceiveListener() {

            @Override
            protected void onFullBinaryMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                log.log(Level.FINE, "Server received full binary message");
                Pooled<ByteBuffer[]> pulledData = message.getData();
                try {
                    ByteBuffer[] resource = pulledData.getResource();
                    ByteBuffer byteBuffer = WebSockets.mergeBuffers(resource);
                    String msg = new String(byteBuffer.array());
                    log.log(Level.FINE, "Sending message to decoder: "+ msg);
                    writeToDecoder(msg);
                }
                finally {
                    pulledData.discard();
                }
            }
        };
        webSocketChannel.getReceiveSetter().set(listener);
    }

    public boolean isOpen() {
        return webSocketChannel != null && webSocketChannel.isOpen();
    }

    public void setWebSocketChannel(WebSocketChannel webSocketChannel) {
        this.webSocketChannel = webSocketChannel;
    }

    public void addReadonlyChannel(WebSocketChannel webSocketChannel) {
        readonlyChannels.add(webSocketChannel);
    }

    public void removeReadonlyChannel(WebSocketChannel webSocketChannel) {
        readonlyChannels.remove(webSocketChannel);
    }

    public void removeWebSocketChannel() {
        webSocketChannel = null;
    }

    @Override
    public void close() {
        Consumer<Void> closeHandler = getCloseHandler();
        if (closeHandler != null) {
            closeHandler.accept(null);
        }
    }
}
