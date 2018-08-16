/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.internal.netty;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.jooby.internal.ConnectionResetByPeer;
import org.jooby.spi.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AsciiString;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class NettyHandler extends SimpleChannelInboundHandler<Object> {

  private static AsciiString STREAM_ID = HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text();

  /** The logging system. */
  private final Logger log = LoggerFactory.getLogger(getClass());

  public static final AttributeKey<String> PATH = AttributeKey
      .newInstance(NettyHandler.class.getName());

  private HttpHandler handler;

  private String tmpdir;

  private int wsMaxMessageSize;

  private int bufferSize;

  public NettyHandler(final HttpHandler handler, final String tmpdir, final int bufferSize,
      final int wsBufferSize) {
    this.handler = handler;
    this.tmpdir = tmpdir;
    this.bufferSize = bufferSize;
    this.wsMaxMessageSize = wsBufferSize;
  }

  @Override
  public void channelRead0(final ChannelHandlerContext ctx, final Object msg) {
    if (msg instanceof HttpRequest) {
      ctx.channel().attr(NettyRequest.NEED_FLUSH).set(true);

      HttpRequest req = (HttpRequest) msg;
      ctx.channel().attr(PATH).set(req.method().name() + " " + req.uri());

      if (HttpUtil.is100ContinueExpected(req)) {
        ctx.write(new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.CONTINUE));
      }

      boolean keepAlive = HttpUtil.isKeepAlive(req);

      try {
        String streamId = req.headers().get(STREAM_ID);

        handler.handle(
            new NettyRequest(ctx, req, tmpdir, wsMaxMessageSize),
            new NettyResponse(ctx, bufferSize, keepAlive, streamId));

      } catch (Throwable ex) {
        exceptionCaught(ctx, ex);
      }
    } else if (msg instanceof WebSocketFrame) {
      Attribute<NettyWebSocket> ws = ctx.channel().attr(NettyWebSocket.KEY);
      ws.get().handle(msg);
    }
  }

  @Override
  public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
    Attribute<Boolean> attr = ctx.channel().attr(NettyRequest.NEED_FLUSH);
    boolean needFlush = (attr == null || attr.get() == Boolean.TRUE);
    if (needFlush) {
      ctx.flush();
    }
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
    try {
      if (ConnectionResetByPeer.test(cause)) {
        log.trace("execution of: " + ctx.channel().attr(PATH).get() + " resulted in error", cause);
      } else {
        Attribute<NettyWebSocket> ws = ctx.channel().attr(NettyWebSocket.KEY);
        if (ws != null && ws.get() != null) {
          ws.get().handle(cause);
        } else {
          log.debug("execution of: " + ctx.channel().attr(PATH).get() + " resulted in error",
              cause);
        }
      }
    } finally {
      ctx.close();
    }

  }

  @Override
  public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt)
      throws Exception {
    // Idle timeout
    if (evt instanceof IdleStateEvent) {
      log.debug("idle timeout: {}", ctx);
      ctx.close();
    } else if (evt instanceof HttpServerUpgradeHandler.UpgradeEvent) {
      // Write an HTTP/2 response to the upgrade request
      FullHttpRequest req = ((HttpServerUpgradeHandler.UpgradeEvent) evt).upgradeRequest();
      req.headers().set(STREAM_ID, req.headers().get(STREAM_ID, "1"));
      channelRead0(ctx, req);
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

}
