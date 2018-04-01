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

package io.hekate.rpc.internal;

import io.hekate.cluster.ClusterNode;
import io.hekate.messaging.MessagingChannel;
import io.hekate.messaging.MessagingFutureException;
import io.hekate.messaging.broadcast.AggregateFuture;
import io.hekate.messaging.broadcast.AggregateResult;
import io.hekate.rpc.RpcAggregate;
import io.hekate.rpc.RpcAggregateException;
import io.hekate.rpc.RpcInterfaceInfo;
import io.hekate.rpc.RpcMethodInfo;
import io.hekate.rpc.RpcService;
import io.hekate.rpc.internal.RpcProtocol.CallRequest;
import io.hekate.rpc.internal.RpcProtocol.ObjectResponse;
import io.hekate.util.format.ToString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.hekate.rpc.internal.RpcUtils.mergeToList;
import static io.hekate.rpc.internal.RpcUtils.mergeToMap;
import static io.hekate.rpc.internal.RpcUtils.mergeToSet;

class RpcAggregateMethodClient<T> extends RpcMethodClientBase<T> {
    private static final Logger log = LoggerFactory.getLogger(RpcService.class);

    private final Function<AggregateResult<RpcProtocol>, ?> converter;

    public RpcAggregateMethodClient(RpcInterfaceInfo<T> rpc, String tag, RpcMethodInfo method, MessagingChannel<RpcProtocol> channel) {
        super(rpc, tag, method, channel);

        assert method.aggregate().isPresent() : "Not an aggregate method [rpc=" + rpc + ", method=" + method + ']';

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Error handling.
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        RpcAggregate config = method.aggregate().get();

        Consumer<AggregateResult<RpcProtocol>> errorCheck;

        if (config.remoteErrors() == RpcAggregate.RemoteErrors.IGNORE) {
            errorCheck = null;
        } else {
            errorCheck = aggregate -> {
                if (!aggregate.isSuccess()) {
                    if (config.remoteErrors() == RpcAggregate.RemoteErrors.WARN) {
                        if (log.isWarnEnabled()) {
                            aggregate.errors().forEach((node, err) ->
                                log.warn("RPC aggregation failed [remote-node={}, rpc={}, method={}]", node, rpc, method, err)
                            );
                        }
                    } else {
                        String errMsg = "RPC aggregation failed [" + ToString.formatProperties(aggregate.request()) + ']';

                        Map<ClusterNode, Object> partialResults = new HashMap<>(aggregate.resultsByNode().size(), 1.0f);

                        aggregate.resultsByNode().forEach((node, response) -> {
                            if (response instanceof ObjectResponse) {
                                partialResults.put(node, ((ObjectResponse)response).object());
                            } else {
                                partialResults.put(node, null);
                            }
                        });

                        throw new RpcAggregateException(errMsg, aggregate.errors(), partialResults);
                    }
                }
            };
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Aggregation of results.
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (method.realReturnType().equals(Map.class)) {
            converter = aggregate -> {
                if (errorCheck != null) {
                    errorCheck.accept(aggregate);
                }

                Map<Object, Object> merged = new HashMap<>();

                aggregate.results().forEach(rpcResult ->
                    mergeToMap(rpcResult, merged)
                );

                return merged;
            };
        } else if (method.realReturnType().equals(Set.class)) {
            converter = aggregate -> {
                if (errorCheck != null) {
                    errorCheck.accept(aggregate);
                }

                Set<Object> merged = new HashSet<>();

                aggregate.results().forEach(rpcResult ->
                    mergeToSet(rpcResult, merged)
                );

                return merged;
            };
        } else {
            converter = aggregate -> {
                if (errorCheck != null) {
                    errorCheck.accept(aggregate);
                }

                List<Object> merged = new ArrayList<>();

                aggregate.results().forEach(rpcResult ->
                    mergeToList(rpcResult, merged)
                );

                return merged;
            };
        }
    }

    @Override
    protected Object doInvoke(MessagingChannel<RpcProtocol> channel, Object[] args) throws MessagingFutureException,
        InterruptedException, TimeoutException {
        CallRequest<T> request = new CallRequest<>(rpc(), tag(), method(), args);

        AggregateFuture<RpcProtocol> future = channel.aggregate(request);

        if (method().isAsync()) {
            return future.thenApply(converter);
        } else {
            AggregateResult<RpcProtocol> result;

            if (channel.timeout() > 0) {
                result = future.get(channel.timeout(), TimeUnit.MILLISECONDS);
            } else {
                result = future.get();
            }

            return converter.apply(result);
        }
    }
}
