/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.core.internal.http.pipeline.stages;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.amazon.awssdk.core.client.config.SdkClientOption.API_CALL_ATTEMPT_TIME_OUT;
import static software.amazon.awssdk.core.client.config.SdkClientOption.ASYNC_HTTP_CLIENT;
import static software.amazon.awssdk.core.client.config.SdkClientOption.ASYNC_RETRY_EXECUTOR_SERVICE;
import static software.amazon.awssdk.core.client.config.SdkClientOption.TIMEOUT_EXECUTOR_SERVICE;
import static software.amazon.awssdk.core.internal.util.AsyncResponseHandlerTestUtils.noOpResponseHandler;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import software.amazon.awssdk.core.http.ExecutionContext;
import software.amazon.awssdk.core.http.NoopTestRequest;
import software.amazon.awssdk.core.internal.client.config.SdkClientConfiguration;
import software.amazon.awssdk.core.internal.http.HttpClientDependencies;
import software.amazon.awssdk.core.internal.http.RequestExecutionContext;
import software.amazon.awssdk.core.internal.http.timers.ClientExecutionAndRequestTimerTestUtils;
import software.amazon.awssdk.core.internal.util.CapacityManager;
import software.amazon.awssdk.http.async.AbortableRunnable;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import utils.ValidSdkObjects;

@RunWith(MockitoJUnitRunner.class)
public class MakeAsyncHttpRequestStageTest {

    @Mock
    private SdkAsyncHttpClient sdkAsyncHttpClient;

    @Mock
    private ScheduledExecutorService timeoutExecutor;

    @Mock
    private AbortableRunnable abortableRunnable;

    private MakeAsyncHttpRequestStage stage;

    @Before
    public void setup() {
        when(sdkAsyncHttpClient.prepareRequest(any(), any(), any(), any())).thenReturn(abortableRunnable);
    }

    @Test
    public void apiCallAttemptTimeoutEnabled_shouldInvokeExecutor() throws Exception {
        stage = new MakeAsyncHttpRequestStage(noOpResponseHandler(), noOpResponseHandler(), clientDependencies(Duration.ofMillis(1000)));
        stage.execute(ValidSdkObjects.sdkHttpFullRequest().build(), requestContext());

        verify(timeoutExecutor, times(1)).schedule(any(Runnable.class), anyLong(), any(TimeUnit.class));
    }

    @Test
    public void apiCallAttemptTimeoutNotEnabled_shouldNotInvokeExecutor() throws Exception {
        stage = new MakeAsyncHttpRequestStage<Object>(noOpResponseHandler(), noOpResponseHandler(), clientDependencies(null));
        stage.execute(ValidSdkObjects.sdkHttpFullRequest().build(), requestContext());

        verify(timeoutExecutor, never()).schedule(any(Runnable.class), anyLong(), any(TimeUnit.class));
    }

    private HttpClientDependencies clientDependencies(Duration timeout) {
        SdkClientConfiguration configuration = SdkClientConfiguration.builder()
                                                                     .option(ASYNC_HTTP_CLIENT, sdkAsyncHttpClient)
                                                                     .option(TIMEOUT_EXECUTOR_SERVICE, timeoutExecutor)
                                                                     .option(ASYNC_RETRY_EXECUTOR_SERVICE, Executors.newScheduledThreadPool(1))
                                                                     .option(API_CALL_ATTEMPT_TIME_OUT, timeout)
                                                                     .build();


        return HttpClientDependencies.builder()
                                     .clientConfiguration(configuration)
                                     .capacityManager(new CapacityManager(2))
                                     .build();
    }

    private RequestExecutionContext requestContext() {
        ExecutionContext executionContext = ClientExecutionAndRequestTimerTestUtils.executionContext(ValidSdkObjects.sdkHttpFullRequest().build());
        return RequestExecutionContext.builder()
                                      .executionContext(executionContext)
                                      .originalRequest(NoopTestRequest.builder().build())
                                      .build();
    }
}
