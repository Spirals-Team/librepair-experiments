/*
 * Copyright (c) 2015 AsyncHttpClient Project. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.asynchttpclient.netty.request.body;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.asynchttpclient.netty.NettyResponseFuture;
import org.asynchttpclient.netty.util.ByteBufUtils;
import org.asynchttpclient.util.Base64;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.concurrent.EventExecutor;

public class NettyReactiveStreamsBody implements NettyBody {

	private static final Logger LOGGER = LoggerFactory.getLogger(NettyReactiveStreamsBody.class);
	private static final String NAME_IN_CHANNEL_PIPELINE = "request-body-streamer";

	private final Publisher<ByteBuf> publisher;

	private final long contentLength;

	public NettyReactiveStreamsBody(Publisher<ByteBuf> publisher, long contentLength) {
		this.publisher = publisher;
		this.contentLength = contentLength;
	}

	@Override
	public long getContentLength() {
		return contentLength;
	}

	@Override
	public void write(Channel channel, NettyResponseFuture<?> future) throws IOException {
		if (future.isStreamConsumed()) {
			LOGGER.warn("Stream has already been consumed and cannot be reset");
		} else {
			future.setStreamConsumed(true);
			NettySubscriber subscriber = new NettySubscriber(channel, future);
			channel.pipeline().addLast(NAME_IN_CHANNEL_PIPELINE, subscriber);
			publisher.subscribe(new SubscriberAdapter(subscriber));
		}
	}

	private static class SubscriberAdapter implements Subscriber<ByteBuf> {
		private final Subscriber<HttpContent> subscriber;

		public SubscriberAdapter(Subscriber<HttpContent> subscriber) {
			this.subscriber = subscriber;
		}

		@Override
		public void onSubscribe(Subscription s) {
			subscriber.onSubscribe(s);
		}

		@Override
		public void onNext(ByteBuf buffer) {
			HttpContent content = new DefaultHttpContent(buffer);
			subscriber.onNext(content);
		}

		@Override
		public void onError(Throwable t) {
			subscriber.onError(t);
		}

		@Override
		public void onComplete() {
			subscriber.onComplete();
		}
	}

	private static MessageDigest newMd5() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new InternalError(e);
		}
	}
	
	private static class NettySubscriber extends ChannelDuplexHandler implements Subscriber<HttpContent> {
		
	    static final long DEFAULT_LOW_WATERMARK = 4;
	    static final long DEFAULT_HIGH_WATERMARK = 16;

	    private final EventExecutor executor;
	    private final long demandLowWatermark;
	    private final long demandHighWatermark;

	    enum State {
	        NO_SUBSCRIPTION_OR_CONTEXT,
	        NO_SUBSCRIPTION,
	        NO_CONTEXT,
	        INACTIVE,
	        RUNNING,
	        CANCELLED,
	        COMPLETE
	    }

	    private final AtomicBoolean hasSubscription = new AtomicBoolean();

	    private volatile Subscription subscription;
	    private volatile ChannelHandlerContext ctx;

	    private State state = State.NO_SUBSCRIPTION_OR_CONTEXT;
	    private long outstandingDemand = 0;
	    private ChannelFuture lastWriteFuture;

	    @Override
	    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
	        verifyRegisteredWithRightExecutor(ctx);

	        switch (state) {
	            case NO_SUBSCRIPTION_OR_CONTEXT:
	                this.ctx = ctx;
	                // We were in no subscription or context, now we just don't have a subscription.
	                state = State.NO_SUBSCRIPTION;
	                break;
	            case NO_CONTEXT:
	                this.ctx = ctx;
	                // We were in no context, we're now fully initialised
	                maybeStart();
	                break;
	            case COMPLETE:
	                // We are complete, close
	                state = State.COMPLETE;
	                ctx.close();
	                break;
	            default:
	                throw new IllegalStateException("This handler must only be added to a pipeline once " + state);
	        }
	    }

	    @Override
	    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
	        verifyRegisteredWithRightExecutor(ctx);
	        ctx.fireChannelRegistered();
	    }

	    private void verifyRegisteredWithRightExecutor(ChannelHandlerContext ctx) {
	        if (ctx.channel().isRegistered() && !executor.inEventLoop()) {
	            throw new IllegalArgumentException("Channel handler MUST be registered with the same EventExecutor that it is created with.");
	        }
	    }

	    @Override
	    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
	        maybeRequestMore();
	        ctx.fireChannelWritabilityChanged();
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        if (state == State.INACTIVE) {
	            state = State.RUNNING;
	            maybeRequestMore();
	        }
	        ctx.fireChannelActive();
	    }

	    @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	        cancel();
	        ctx.fireChannelInactive();
	    }

	    @Override
	    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
	        cancel();
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        cancel();
	        ctx.fireExceptionCaught(cause);
	    }

	    private void cancel() {
	        switch (state) {
	            case NO_SUBSCRIPTION:
	                state = State.CANCELLED;
	                break;
	            case RUNNING:
	            case INACTIVE:
	                subscription.cancel();
	                state = State.CANCELLED;
	                break;
	        }
	    }

	    @Override
	    public void onSubscribe(final Subscription subscription) {
	        if (subscription == null) {
	            throw new NullPointerException("Null subscription");
	        } else if (!hasSubscription.compareAndSet(false, true)) {
	            subscription.cancel();
	        } else {
	            this.subscription = subscription;
	            executor.execute(new Runnable() {
	                @Override
	                public void run() {
	                    provideSubscription();
	                }
	            });
	        }
	    }

	    private void provideSubscription() {
	    		NettySubscriber.LOGGER.debug(">>>>Calling provideSubscription"); 
	        switch (state) {
	            case NO_SUBSCRIPTION_OR_CONTEXT:
	                state = State.NO_CONTEXT;
	                break;
	            case NO_SUBSCRIPTION:
	                maybeStart();
	                break;
	            case CANCELLED:
	                subscription.cancel();
	                break;
	        }
	    }

	    private void maybeStart() {
	        if (ctx.channel().isActive()) {
	            state = State.RUNNING;
	            maybeRequestMore();
	        } else {
	            state = State.INACTIVE;
	        }
	    }

	    public void onNext0(HttpContent t) {

	        // Publish straight to the context.
	        lastWriteFuture = ctx.writeAndFlush(t);
	        lastWriteFuture.addListener(new ChannelFutureListener() {
	            @Override
	            public void operationComplete(ChannelFuture future) throws Exception {

	                outstandingDemand--;
	                maybeRequestMore();
	            }
	        });
	    }

	    @Override
	    public void onError(final Throwable error) {
	        if (error == null) {
	            throw new NullPointerException("Null error published");
	        }
	        error(error);
	    }

	    @Override
	    public void onComplete() {
	        if (lastWriteFuture == null) {
	            complete();
	        } else {
	            lastWriteFuture.addListener(new ChannelFutureListener() {
	                @Override
	                public void operationComplete(ChannelFuture channelFuture) throws Exception {
	                    complete();
	                }
	            });
	        }
	    }

//	    private void doClose() {
//	        executor.execute(new Runnable() {
//	            @Override
//	            public void run() {
//	                switch (state) {
//	                    case NO_SUBSCRIPTION:
//	                    case INACTIVE:
//	                    case RUNNING:
//	                        ctx.close();
//	                        state = State.COMPLETE;
//	                        break;
//	                }
//	            }
//	        });
//	    }

	    private void maybeRequestMore() {
	        if (outstandingDemand <= demandLowWatermark && ctx.channel().isWritable()) {
	            long toRequest = demandHighWatermark - outstandingDemand;
	            	LOGGER.debug(">>>maybeRequestMore toRequest={}", toRequest);
	            outstandingDemand = demandHighWatermark;
	            subscription.request(toRequest);
	        }
	    }

	    // =============
		private static final Logger LOGGER = LoggerFactory.getLogger(NettySubscriber.class);
		private final Channel channel;
		private final NettyResponseFuture<?> future;
		private final AtomicInteger calls = new AtomicInteger();

		public NettySubscriber(Channel channel, NettyResponseFuture<?> future) {
	        this.executor = channel.eventLoop();
	        this.demandLowWatermark = DEFAULT_LOW_WATERMARK;
	        this.demandHighWatermark = DEFAULT_HIGH_WATERMARK;
			this.channel = channel;
			this.future = future;
		}

		private final MessageDigest md = newMd5();
		
		@Override
		public void onNext(HttpContent t) {
			int i = calls.incrementAndGet();
			if (i % 10 == 0) {
				new Exception("call " + i).printStackTrace();
			}
			if (t.content().isReadable()) {
				byte[] bytes = ByteBufUtils.byteBuf2Bytes(t.content().duplicate());
				md.update(bytes, 0, bytes.length);
			}
			
			onNext0(t);
		}

		protected void complete() {
			LOGGER.debug(">>>>>>>>>>>>complete with {} md5", Base64.encode(md.digest()));
			md.reset();
			channel.eventLoop().execute(() -> channel.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
					.addListener(future -> removeFromPipeline())); // FIXME why not remove immediately?
		}

		protected void error(Throwable error) {
			if (error == null)
				throw null;
			removeFromPipeline();
			future.abort(error);
		}

		private void removeFromPipeline() {
			try {
				channel.pipeline().remove(this);
				LOGGER.debug(String.format("Removed handler %s from pipeline.", NAME_IN_CHANNEL_PIPELINE));
			} catch (NoSuchElementException e) {
				LOGGER.debug(String.format("Failed to remove handler %s from pipeline.", NAME_IN_CHANNEL_PIPELINE), e);
			}
		}
	}
}
