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

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.RequestOverrideConfiguration;
import software.amazon.awssdk.core.SdkStandardLogger;
import software.amazon.awssdk.core.client.config.SdkAdvancedAsyncClientOption;
import software.amazon.awssdk.core.client.config.SdkClientOption;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.internal.Response;
import software.amazon.awssdk.core.internal.http.HttpClientDependencies;
import software.amazon.awssdk.core.internal.http.InterruptMonitor;
import software.amazon.awssdk.core.internal.http.RequestExecutionContext;
import software.amazon.awssdk.core.internal.http.async.SimpleRequestProvider;
import software.amazon.awssdk.core.internal.http.pipeline.RequestPipeline;
import software.amazon.awssdk.core.internal.http.timers.ApiCallTimeoutTracker;
import software.amazon.awssdk.core.internal.http.timers.AsyncTimeoutTask;
import software.amazon.awssdk.core.internal.http.timers.NoOpTimeoutTracker;
import software.amazon.awssdk.core.internal.http.timers.TimeoutTracker;
import software.amazon.awssdk.http.Abortable;
import software.amazon.awssdk.http.HttpStatusFamily;
import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.http.SdkHttpFullResponse;
import software.amazon.awssdk.http.SdkHttpMethod;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.http.SdkRequestContext;
import software.amazon.awssdk.http.async.AbortableRunnable;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.async.SdkHttpRequestProvider;
import software.amazon.awssdk.http.async.SdkHttpResponseHandler;
import software.amazon.awssdk.utils.Logger;

/**
 * Delegate to the HTTP implementation to make an HTTP request and receive the response.
 */
@SdkInternalApi
public final class MakeAsyncHttpRequestStage<OutputT>
    implements RequestPipeline<SdkHttpFullRequest, CompletableFuture<Response<OutputT>>> {

    private static final Logger log = Logger.loggerFor(MakeAsyncHttpRequestStage.class);

    private final SdkAsyncHttpClient sdkAsyncHttpClient;
    private final SdkHttpResponseHandler<OutputT> responseHandler;
    private final SdkHttpResponseHandler<? extends SdkException> errorResponseHandler;
    private final Executor futureCompletionExecutor;
    private final ScheduledExecutorService timeoutExecutor;
    private final Duration apiCallAttemptTimeout;

    public MakeAsyncHttpRequestStage(SdkHttpResponseHandler<OutputT> responseHandler,
                                     SdkHttpResponseHandler<? extends SdkException> errorResponseHandler,
                                     HttpClientDependencies dependencies) {
        this.responseHandler = responseHandler;
        this.errorResponseHandler = errorResponseHandler;
        this.futureCompletionExecutor =
                dependencies.clientConfiguration().option(SdkAdvancedAsyncClientOption.FUTURE_COMPLETION_EXECUTOR);
        this.sdkAsyncHttpClient = dependencies.clientConfiguration().option(SdkClientOption.ASYNC_HTTP_CLIENT);
        this.apiCallAttemptTimeout = dependencies.clientConfiguration().option(SdkClientOption.API_CALL_ATTEMPT_TIME_OUT);
        this.timeoutExecutor = dependencies.clientConfiguration().option(SdkClientOption.TIMEOUT_EXECUTOR_SERVICE);
    }

    /**
     * Returns the response from executing one httpClientSettings request; or null for retry.
     */
    public CompletableFuture<Response<OutputT>> execute(SdkHttpFullRequest request,
                                                        RequestExecutionContext context) throws Exception {
        InterruptMonitor.checkInterrupted();
        return executeHttpRequest(request, context);
    }

    private CompletableFuture<Response<OutputT>> executeHttpRequest(SdkHttpFullRequest request,
                                                                    RequestExecutionContext context) throws Exception {

        long timeout = apiCallAttemptTimeoutInMillis(context.requestConfig());
        Completable completable = new Completable(timeout);

        SdkHttpResponseHandler<Response<OutputT>> handler = new ResponseHandler(completable);

        SdkHttpRequestProvider requestProvider = context.requestProvider() == null
                ? new SimpleRequestProvider(request, context.executionAttributes())
                : context.requestProvider();
        // Set content length if it hasn't been set already.
        SdkHttpFullRequest requestWithContentLength = getRequestWithContentLength(request, requestProvider);

        AbortableRunnable abortableRunnable = sdkAsyncHttpClient.prepareRequest(requestWithContentLength, SdkRequestContext
                                                                                    .builder().build(),
                                                                                requestProvider,
                                                                                handler);

        // Set the abortable so that the abortable request can be aborted after timeout
        completable.abortable(abortableRunnable);

        if (context.apiCallTimeoutTracker() != null && context.apiCallTimeoutTracker().isEnabled()) {
            context.apiCallTimeoutTracker().abortable(abortableRunnable);
        }

        abortableRunnable.run();
        return completable.completableFuture;
    }

    private SdkHttpFullRequest getRequestWithContentLength(SdkHttpFullRequest request, SdkHttpRequestProvider requestProvider) {
        if (shouldSetContentLength(request, requestProvider)) {
            return request.toBuilder()
                          .putHeader("Content-Length", String.valueOf(requestProvider.contentLength()))
                          .build();
        }
        return request;
    }

    private boolean shouldSetContentLength(SdkHttpFullRequest request, SdkHttpRequestProvider requestProvider) {
        return requestProvider != null
               && !request.firstMatchingHeader("Content-Length").isPresent()
               // Can cause issues with signing if content length is present for these method
               && request.method() != SdkHttpMethod.GET
               && request.method() != SdkHttpMethod.HEAD;

    }

    /**
     * Detects whether the response succeeded or failed and delegates to appropriate response handler.
     */
    private class ResponseHandler implements SdkHttpResponseHandler<Response<OutputT>> {
        private final Completable completable;

        private volatile SdkHttpResponse response;
        private volatile boolean isSuccess = false;

        /**
         * @param completable   Future to notify when response has been handled.
         */
        private ResponseHandler(Completable completable) {
            this.completable = completable;
        }

        @Override
        public void headersReceived(SdkHttpResponse response) {
            if (HttpStatusFamily.of(response.statusCode()) == HttpStatusFamily.SUCCESSFUL) {
                isSuccess = true;
                responseHandler.headersReceived(response);
                SdkStandardLogger.REQUEST_LOGGER.debug(() -> "Received successful response: " + response.statusCode());
            } else {
                SdkStandardLogger.REQUEST_LOGGER.debug(() -> "Received error response: " + response.statusCode());
                errorResponseHandler.headersReceived(response);
            }
            this.response = response;
        }

        @Override
        public void onStream(Publisher<ByteBuffer> publisher) {
            if (isSuccess) {
                // TODO handle exception as non retryable
                responseHandler.onStream(publisher);
            } else {
                errorResponseHandler.onStream(publisher);
            }
        }

        @Override
        public void exceptionOccurred(Throwable throwable) {
            // Note that we don't notify the response handler here, we do that in AsyncRetryableStage where we
            // have more context of what's going on and can deliver exceptions more reliably.
            completable.completeExceptionally(throwable);
        }

        @Override
        public Response<OutputT> complete() {
            try {
                SdkHttpFullResponse httpFullResponse = (SdkHttpFullResponse) this.response;
                Response<OutputT> toReturn = handleResponse(httpFullResponse);
                completable.complete(toReturn);
                return toReturn;
            } catch (Exception e) {
                completable.completeExceptionally(e);
                throw e;
            }
        }

        private Response<OutputT> handleResponse(SdkHttpFullResponse httpResponse) {
            if (isSuccess) {
                OutputT response = responseHandler.complete();
                return Response.fromSuccess(response, httpResponse);
            } else {
                return Response.fromFailure(errorResponseHandler.complete(), httpResponse);
            }
        }

    }

    /**
     * An interface similar to {@link CompletableFuture} that may or may not dispatch completion of the future to an executor
     * service, depending on the client's configuration.
     */
    private class Completable {
        private final CompletableFuture<Response<OutputT>> completableFuture = new CompletableFuture<>();
        private AsyncTimeoutTask timeout;
        private TimeoutTracker timeoutTracker;

        Completable(long timeoutInMills) {
            if (isApiCallAttemptTimeoutEnabled(timeoutInMills)) {
                timeout = new AsyncTimeoutTask(completableFuture);

                ScheduledFuture<?> scheduledFuture = timeoutExecutor.schedule(timeout, timeoutInMills, TimeUnit.MILLISECONDS);
                timeoutTracker = new ApiCallTimeoutTracker(timeout, scheduledFuture);

                completableFuture.whenComplete((o, t) -> {
                    if (t != null) {
                        timeoutTracker.cancel();
                    }
                });
            } else {
                timeoutTracker = new NoOpTimeoutTracker();
            }
        }

        void abortable(Abortable abortable) {
            if (timeout != null) {
                timeout.abortable(abortable);
            }
        }

        void complete(Response<OutputT> result) {
            try {
                log.trace(() -> "completing the result");
                futureCompletionExecutor.execute(() -> completableFuture.complete(result));
            } catch (RejectedExecutionException e) {
                completableFuture.completeExceptionally(explainRejection(e));
            }
        }

        void completeExceptionally(Throwable exception) {
            try {
                log.trace(() -> "completeExceptionally");
                futureCompletionExecutor.execute(() -> completableFuture.completeExceptionally(exception));
            } catch (RejectedExecutionException e) {
                completableFuture.completeExceptionally(explainRejection(e));
            }
        }

        private RejectedExecutionException explainRejection(RejectedExecutionException e) {
            return new RejectedExecutionException("The SDK was unable to complete the async future to provide you with a " +
                                                  "response. This may be caused by too-few threads in the response executor, " +
                                                  "too-short of a queue or too long of an operation in your future completion " +
                                                  "chain. You can provide a larger executor service to the SDK via the " +
                                                  "client's async configuration setting or you can reduce the amount of work " +
                                                  "performed on the async execution thread.", e);
        }
    }

    private long apiCallAttemptTimeoutInMillis(RequestOverrideConfiguration requestConfig) {
        if (requestConfig.apiCallAttemptTimeout().isPresent()) {
            return requestConfig.apiCallTimeout().get().toMillis();
        }

        if (apiCallAttemptTimeout != null) {
            return apiCallAttemptTimeout.toMillis();
        }

        return 0;
    }

    private boolean isApiCallAttemptTimeoutEnabled(long apiCallAttemptTimeout) {
        return apiCallAttemptTimeout > 0;
    }
}
