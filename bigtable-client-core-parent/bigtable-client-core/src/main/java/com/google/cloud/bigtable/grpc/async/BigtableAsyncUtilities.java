/*
 * Copyright 2015 Google Inc. All Rights Reserved.
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
package com.google.cloud.bigtable.grpc.async;


import com.google.cloud.bigtable.config.Logger;
import com.google.cloud.bigtable.grpc.async.BigtableAsyncRpc.RpcMetrics;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientCall.Listener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * Utilities for creating and executing async methods.
 *
 * @author sduskis
 * @version $Id: $Id
 */
public interface BigtableAsyncUtilities {

  /**
   * <p>createAsyncUnaryRpc.</p>
   *
   * @param method a {@link io.grpc.MethodDescriptor} object.
   * @param isRetryable a {@link com.google.common.base.Predicate} object.
   * @param <RequestT> a RequestT object.
   * @param <ResponseT> a ResponseT object.
   * @return a {@link com.google.cloud.bigtable.grpc.async.BigtableAsyncRpc} object.
   */
  <RequestT, ResponseT> BigtableAsyncRpc<RequestT, ResponseT> createAsyncRpc(
      MethodDescriptor<RequestT, ResponseT> method, Predicate<RequestT> isRetryable);

  public static class Default implements BigtableAsyncUtilities {
    private static final Logger LOG = new Logger(BigtableAsyncUtilities.class);
    private final Channel channel;

    public Default(Channel channel) {
      this.channel = channel;
    }

    @Override
    public <RequestT, ResponseT> BigtableAsyncRpc<RequestT, ResponseT> createAsyncRpc(
        final MethodDescriptor<RequestT, ResponseT> method, final Predicate<RequestT> isRetryable) {
      final BigtableAsyncRpc.RpcMetrics metrics = RpcMetrics.createRpcMetrics(method);
      return new BigtableAsyncRpc<RequestT, ResponseT>() {
        @Override
        public boolean isRetryable(RequestT request) {
          return isRetryable.apply(request);
        }

        @Override
        public MethodDescriptor<RequestT, ResponseT> getMethodDescriptor() {
          return method;
        }

        @Override
        public BigtableAsyncRpc.RpcMetrics getRpcMetrics() {
          return metrics;
        }

        @Override
        public ClientCall<RequestT, ResponseT> newCall(CallOptions callOptions) {
          return channel.newCall(method, callOptions);
        }

        @Override
        public void start(RequestT request, Listener<ResponseT> listener, Metadata metadata,
            ClientCall<RequestT, ResponseT> call) {
          call.start(listener, metadata);
          call.request(1);
          try {
            call.sendMessage(request);
          } catch (Throwable t) {
            LOG.error("Could not sendMessage()", t);
            call.cancel("Exception in sendMessage.", t);
            Throwables.throwIfUnchecked(t);
            throw new RuntimeException(t);
          }
          try {
            call.halfClose();
          } catch (Throwable t) {
            LOG.error("Could not halfClose()", t);
            call.cancel("Exception in halfClose.", t);
            Throwables.throwIfUnchecked(t);
            throw new RuntimeException(t);
          }
        }
      };
    }
  }
}
