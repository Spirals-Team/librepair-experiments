/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.gateway.engine.policy;

import io.apiman.gateway.engine.beans.ApiRequest;
import io.apiman.gateway.engine.beans.ApiResponse;
import io.apiman.gateway.engine.io.IReadWriteStream;

/**
 * Policies that wish to be applied to the data handling phase of apiman
 * must implement this interface.  Normal policies (which implement
 * {@link IPolicy}) are only given a crack at the {@link ApiRequest}
 * and {@link ApiResponse}.  If this interface is implemented, then
 * the policy also gets a crack at the request body stream and response
 * body stream.  This is useful for things like URL rewriting policies.
 *
 * @author eric.wittmann@redhat.com
 */
public interface IDataPolicy extends IPolicy {

    /**
     * This method should return a stream that will be used when piping the request data
     * from the client to the back-end API.
     * @param request the request
     * @param context the context
     * @param policyConfiguration the policy's configuration
     * @return Request handler to stream request data through the policy.
     */
    public IReadWriteStream<ApiRequest> getRequestDataHandler(ApiRequest request,
            IPolicyContext context, Object policyConfiguration);

    /**
     * This method should return a stream that will be used when piping the response data
     * from the back-end API to the client.
     * @param response the response
     * @param context the context
     * @param policyConfiguration the policy's configuration
     * @return Response handler to stream request data through the policy.
     */
    public IReadWriteStream<ApiResponse> getResponseDataHandler(ApiResponse response,
            IPolicyContext context, Object policyConfiguration);

}
