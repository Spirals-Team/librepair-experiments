/*
 * Copyright 2015 JBoss Inc
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
package io.apiman.test.policies;

import io.apiman.gateway.engine.IApiRequestExecutor;
import io.apiman.gateway.engine.IEngine;
import io.apiman.gateway.engine.IEngineResult;
import io.apiman.gateway.engine.async.IAsyncHandler;
import io.apiman.gateway.engine.async.IAsyncResult;
import io.apiman.gateway.engine.async.IAsyncResultHandler;
import io.apiman.gateway.engine.beans.ApiRequest;
import io.apiman.gateway.engine.beans.ApiResponse;
import io.apiman.gateway.engine.beans.PolicyFailure;
import io.apiman.gateway.engine.io.ByteBuffer;
import io.apiman.gateway.engine.io.IApimanBuffer;
import io.apiman.gateway.engine.io.ISignalWriteStream;
import io.apiman.gateway.engine.policy.IPolicyContext;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.runner.RunWith;

/**
 * Base class for all apiman policy tests.
 *
 * @author eric.wittmann@redhat.com
 */
@RunWith(PolicyTester.class)
public abstract class ApimanPolicyTest {

    public static PolicyTester tester;

    public PolicyTestResponse send(final PolicyTestRequest ptRequest) throws PolicyFailureError, Throwable {
        final Set<Throwable> errorHolder = new HashSet<>();
        final Set<PolicyFailure> failureHolder = new HashSet<>();
        final Set<ApiResponse> responseHolder = new HashSet<>();
        final StringBuilder responseBody = new StringBuilder();

        IEngine engine = tester.getEngine();
        ApiRequest srequest = tester.createApiRequest();
        srequest.setUrl("http://localhost:8080" + ptRequest.resource()); //$NON-NLS-1$
        srequest.setDestination(ptRequest.resource());
        srequest.setType(ptRequest.method().name());
        srequest.getHeaders().putAll(ptRequest.headers());
        srequest.getQueryParams().putAll(ptRequest.queryParams());

        IApiRequestExecutor executor = engine.executor(srequest, new IAsyncResultHandler<IEngineResult>() {
            @Override
            public void handle(IAsyncResult<IEngineResult> result) {
                if (result.isError()) {
                    errorHolder.add(result.getError());
                } else {
                    IEngineResult engineResult = result.getResult();
                    if (engineResult.isFailure()) {
                        failureHolder.add(engineResult.getPolicyFailure());
                    } else {
                        responseHolder.add(engineResult.getApiResponse());
                        engineResult.bodyHandler(new IAsyncHandler<IApimanBuffer>() {
                            @Override
                            public void handle(IApimanBuffer result) {
                                responseBody.append(new String(result.getBytes()));
                            }
                        });
                        engineResult.endHandler(new IAsyncHandler<Void>() {
                            @Override
                            public void handle(Void result) {
                            }
                        });
                    }
                }
            }
        });
        executor.streamHandler(new IAsyncHandler<ISignalWriteStream>() {
            @Override
            public void handle(ISignalWriteStream stream) {
                if (ptRequest.body() != null) {
                    ByteBuffer buffer = new ByteBuffer(ptRequest.body());
                    stream.write(buffer);
                }
                stream.end();
            }
        });

        // Push any context attributes into the Policy Context.
        IPolicyContext policyContext = getContext(executor);
        Map<String, Object> contextAttributes = ptRequest.contextAttributes();
        for (Entry<String, Object> entry : contextAttributes.entrySet()) {
            policyContext.setAttribute(entry.getKey(), entry.getValue());
        }

        // Execute the request.
        executor.execute();

        if (!errorHolder.isEmpty()) {
            throw errorHolder.iterator().next();
        }
        if (!failureHolder.isEmpty()) {
            throw new PolicyFailureError(failureHolder.iterator().next());
        }
        if (!responseHolder.isEmpty()) {
            ApiResponse response = responseHolder.iterator().next();
            return new PolicyTestResponse(response, responseBody.toString());
        }
        throw new Exception("No response found from request!"); //$NON-NLS-1$
    }

    /**
     * @param executor
     */
    private IPolicyContext getContext(IApiRequestExecutor executor) {
        try {
            Field field = executor.getClass().getDeclaredField("context"); //$NON-NLS-1$
            field.setAccessible(true);
            Object fieldValue = field.get(executor);
            return (IPolicyContext) fieldValue;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
