/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.hystrix;

import com.alipay.sofa.rpc.context.RpcInternalContext;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import com.netflix.hystrix.HystrixCommand;

import java.lang.reflect.InvocationTargetException;

public class SofaHystrixCommand extends HystrixCommand<SofaResponse> {

    private RpcInternalContext rpcInternalContext;
    private RpcInvokeContext   rpcInvokeContext;
    private FilterInvoker      invoker;
    private SofaRequest        request;

    protected SofaHystrixCommand(FilterInvoker invoker, SofaRequest request) {
        super(SetterFactoryLoader.load(invoker.getConfig()).createSetter(invoker, request));
        this.rpcInternalContext = RpcInternalContext.peekContext();
        this.rpcInvokeContext = RpcInvokeContext.peekContext();
        this.invoker = invoker;
        this.request = request;
    }

    @Override
    protected SofaResponse run() throws Exception {
        RpcInternalContext.setContext(rpcInternalContext);
        RpcInvokeContext.setContext(rpcInvokeContext);

        SofaResponse sofaResponse = invoker.invoke(request);
        if (!sofaResponse.isError()) {
            return sofaResponse;
        }
        return getFallback(sofaResponse, null);
    }

    @Override
    protected SofaResponse getFallback() {
        return getFallback(null, getExecutionException());
    }

    private SofaResponse getFallback(SofaResponse response, Throwable t) {
        FallbackFactory fallbackFactory = FallbackFactoryLoader.load(invoker.getConfig());
        if (fallbackFactory == null) {
            return super.getFallback();
        }
        Object fallback = fallbackFactory.create(response, t);
        try {
            Object fallbackResult = request.getMethod().invoke(fallback, request.getMethodArgs());
            SofaResponse actualResponse = new SofaResponse();
            actualResponse.setAppResponse(fallbackResult);
            return actualResponse;
        } catch (IllegalAccessException e) {
            throw new SofaRpcRuntimeException("Hystrix fallback method invoke failed.", e);
        } catch (InvocationTargetException e) {
            throw new SofaRpcRuntimeException("Hystrix fallback method invoke failed.",
                e.getTargetException());
        }
    }

}
