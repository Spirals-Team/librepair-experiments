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
package org.aesh.terminal.http.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.aesh.terminal.Connection;
import org.aesh.terminal.http.HttpTtyConnection;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TtyWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  private final ChannelGroup group;
  private final Consumer<Connection> handler;
  private ChannelHandlerContext context;
  private HttpTtyConnection conn;

  public TtyWebSocketFrameHandler(ChannelGroup group, Consumer<Connection> handler) {
    this.group = group;
    this.handler = handler;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    context = ctx;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
      ctx.pipeline().remove(HttpRequestHandler.class);
      group.add(ctx.channel());
      conn = new HttpTtyConnection() {
        @Override
        protected void write(byte[] buffer) {
          ByteBuf byteBuf = Unpooled.buffer();
          byteBuf.writeBytes(buffer);
          context.writeAndFlush(new TextWebSocketFrame(byteBuf));
        }

        public void schedule(Runnable task, long delay, TimeUnit unit) {
          context.executor().schedule(task, delay, unit);
        }

        public void execute(Runnable task) {
          context.executor().execute(task);
        }

        @Override
        public void close() {
          context.close();
        }
      };
      handler.accept(conn);
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    HttpTtyConnection tmp = conn;
    context = null;
    conn = null;
    if (tmp != null) {
      Consumer<Void> closeHandler = tmp.getCloseHandler();
      if (closeHandler != null) {
        closeHandler.accept(null);
      }
    }
  }

  public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    conn.writeToDecoder(msg.text());
  }
}
