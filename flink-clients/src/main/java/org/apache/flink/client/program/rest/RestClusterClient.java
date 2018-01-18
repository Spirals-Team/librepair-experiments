/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.client.program.rest;

import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.JobSubmissionResult;
import org.apache.flink.api.common.accumulators.AccumulatorHelper;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.ProgramInvocationException;
import org.apache.flink.client.program.rest.retry.ExponentialWaitStrategy;
import org.apache.flink.client.program.rest.retry.WaitStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.blob.BlobClient;
import org.apache.flink.runtime.blob.PermanentBlobKey;
import org.apache.flink.runtime.client.JobStatusMessage;
import org.apache.flink.runtime.client.JobSubmissionException;
import org.apache.flink.runtime.clusterframework.messages.GetClusterStatusResponse;
import org.apache.flink.runtime.concurrent.FutureUtils;
import org.apache.flink.runtime.concurrent.ScheduledExecutorServiceAdapter;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobmaster.JobResult;
import org.apache.flink.runtime.leaderretrieval.LeaderRetrievalListener;
import org.apache.flink.runtime.leaderretrieval.LeaderRetrievalService;
import org.apache.flink.runtime.rest.RestClient;
import org.apache.flink.runtime.rest.messages.BlobServerPortHeaders;
import org.apache.flink.runtime.rest.messages.BlobServerPortResponseBody;
import org.apache.flink.runtime.rest.messages.EmptyMessageParameters;
import org.apache.flink.runtime.rest.messages.EmptyRequestBody;
import org.apache.flink.runtime.rest.messages.EmptyResponseBody;
import org.apache.flink.runtime.rest.messages.JobMessageParameters;
import org.apache.flink.runtime.rest.messages.JobTerminationHeaders;
import org.apache.flink.runtime.rest.messages.JobTerminationMessageParameters;
import org.apache.flink.runtime.rest.messages.JobsOverviewHeaders;
import org.apache.flink.runtime.rest.messages.MessageHeaders;
import org.apache.flink.runtime.rest.messages.MessageParameters;
import org.apache.flink.runtime.rest.messages.RequestBody;
import org.apache.flink.runtime.rest.messages.ResponseBody;
import org.apache.flink.runtime.rest.messages.TerminationModeQueryParameter;
import org.apache.flink.runtime.rest.messages.job.JobExecutionResultHeaders;
import org.apache.flink.runtime.rest.messages.job.JobSubmitHeaders;
import org.apache.flink.runtime.rest.messages.job.JobSubmitRequestBody;
import org.apache.flink.runtime.rest.messages.job.JobSubmitResponseBody;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointInfo;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointStatusHeaders;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointStatusMessageParameters;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerHeaders;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerId;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerMessageParameters;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerRequestBody;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerResponseBody;
import org.apache.flink.runtime.rest.messages.queue.AsynchronouslyCreatedResource;
import org.apache.flink.runtime.rest.messages.queue.QueueStatus;
import org.apache.flink.runtime.rest.util.RestClientException;
import org.apache.flink.runtime.util.ExecutorThreadFactory;
import org.apache.flink.util.ExceptionUtils;
import org.apache.flink.util.ExecutorUtils;
import org.apache.flink.util.FlinkException;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.SerializedThrowable;
import org.apache.flink.util.function.CheckedSupplier;

import org.apache.flink.shaded.netty4.io.netty.channel.ConnectTimeoutException;

import akka.actor.AddressFromURIString;

import javax.annotation.Nullable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import scala.Option;

import static org.apache.flink.util.Preconditions.checkArgument;

/**
 * A {@link ClusterClient} implementation that communicates via HTTP REST requests.
 */
public class RestClusterClient<T> extends ClusterClient<T> {

	private static final long AWAIT_LEADER_TIMEOUT = 10_000;

	private static final int MAX_RETRIES = 20;

	private static final Time RETRY_DELAY = Time.seconds(3);

	private final RestClusterClientConfiguration restClusterClientConfiguration;

	private final RestClient restClient;

	private final ExecutorService executorService = Executors.newFixedThreadPool(4, new ExecutorThreadFactory("Flink-RestClusterClient-IO"));

	private final WaitStrategy waitStrategy;

	private final T clusterId;

	private LeaderRetrievalService restServerRetrievalService;

	private LeaderRetrievalService dispatcherLeaderRetriever;

	private final LeaderHolder<URL> restServerLeaderHolder = new LeaderHolder<>("RestServer", AWAIT_LEADER_TIMEOUT);

	private final LeaderHolder<String> dispatcherLeaderHolder = new LeaderHolder<>("Dispatcher", AWAIT_LEADER_TIMEOUT);

	/** ExecutorService to run operations that can be retried on exceptions. */
	private ScheduledExecutorService retryExecutorService;

	public RestClusterClient(Configuration config, T clusterId) throws Exception {
		this(
			config,
			clusterId,
			new ExponentialWaitStrategy(10L, 2000L));
	}

	@VisibleForTesting
	RestClusterClient(Configuration configuration, T clusterId, WaitStrategy waitStrategy) throws Exception {
		super(configuration);
		this.restClusterClientConfiguration = RestClusterClientConfiguration.fromConfiguration(configuration);
		this.restClient = new RestClient(restClusterClientConfiguration.getRestClientConfiguration(), executorService);
		this.waitStrategy = Preconditions.checkNotNull(waitStrategy);
		this.clusterId = Preconditions.checkNotNull(clusterId);

		this.restServerRetrievalService = highAvailabilityServices.getRestServerLeaderRetriever();
		this.dispatcherLeaderRetriever = highAvailabilityServices.getDispatcherLeaderRetriever();
		this.retryExecutorService = Executors.newSingleThreadScheduledExecutor(new ExecutorThreadFactory("Flink-RestClusterClient-Retry"));
		startLeaderRetrievers();
	}

	private void startLeaderRetrievers() throws Exception {
		this.restServerRetrievalService.start(new RestServerLeaderRetrievalListener());
		this.dispatcherLeaderRetriever.start(new DispatcherLeaderRetrievalListener());
	}

	@Override
	public void shutdown() {
		try {
			// we only call this for legacy reasons to shutdown components that are started in the ClusterClient constructor
			super.shutdown();
		} catch (Exception e) {
			log.error("An error occurred during the client shutdown.", e);
		}

		ExecutorUtils.gracefulShutdown(RETRY_DELAY.getSize(), RETRY_DELAY.getUnit(), retryExecutorService);

		this.restClient.shutdown(Time.seconds(5));
		ExecutorUtils.gracefulShutdown(5, TimeUnit.SECONDS, this.executorService);

		try {
			restServerRetrievalService.stop();
		} catch (Exception e) {
			log.error("An error occurred during stopping the restServerRetrievalService", e);
		}

		try {
			dispatcherLeaderRetriever.stop();
		} catch (Exception e) {
			log.error("An error occurred during stopping the dispatcherLeaderRetriever", e);
		}
	}

	@Override
	protected JobSubmissionResult submitJob(JobGraph jobGraph, ClassLoader classLoader) throws ProgramInvocationException {
		log.info("Submitting job.");
		try {
			// we have to enable queued scheduling because slot will be allocated lazily
			jobGraph.setAllowQueuedScheduling(true);
			submitJob(jobGraph);
		} catch (JobSubmissionException e) {
			throw new ProgramInvocationException(e);
		}

		final JobResult jobExecutionResult;
		try {
			jobExecutionResult = waitForResource(
				() -> {
					final JobMessageParameters messageParameters = new JobMessageParameters();
					messageParameters.jobPathParameter.resolve(jobGraph.getJobID());
					return sendRetryableRequest(
						JobExecutionResultHeaders.getInstance(),
						messageParameters,
						EmptyRequestBody.getInstance(),
						isTimeoutException().or(isHttpStatusUnsuccessfulException()));
				});
		} catch (final Exception e) {
			throw new ProgramInvocationException(e);
		}

		if (jobExecutionResult.getSerializedThrowable().isPresent()) {
			final SerializedThrowable serializedThrowable = jobExecutionResult.getSerializedThrowable().get();
			final Throwable throwable = serializedThrowable.deserializeError(classLoader);
			throw new ProgramInvocationException(throwable);
		}

		try {
			// don't return just a JobSubmissionResult here, the signature is lying
			// The CliFrontend expects this to be a JobExecutionResult
			this.lastJobExecutionResult = new JobExecutionResult(
				jobExecutionResult.getJobId(),
				jobExecutionResult.getNetRuntime(),
				AccumulatorHelper.deserializeAccumulators(
					jobExecutionResult.getAccumulatorResults(),
					classLoader));
			return lastJobExecutionResult;
		} catch (IOException | ClassNotFoundException e) {
			throw new ProgramInvocationException(e);
		}
	}

	private void submitJob(JobGraph jobGraph) throws JobSubmissionException {
		log.info("Requesting blob server port.");
		int blobServerPort;
		try {
			CompletableFuture<BlobServerPortResponseBody> portFuture = sendRequest(
				BlobServerPortHeaders.getInstance());
			blobServerPort = portFuture.get(timeout.toMillis(), TimeUnit.MILLISECONDS).port;
		} catch (Exception e) {
			throw new JobSubmissionException(jobGraph.getJobID(), "Failed to retrieve blob server port.", e);
		}

		log.info("Uploading jar files.");
		try {
			InetSocketAddress address = new InetSocketAddress(dispatcherLeaderHolder.getLeaderAddress(), blobServerPort);
			List<PermanentBlobKey> keys = BlobClient.uploadJarFiles(address, this.flinkConfig, jobGraph.getJobID(), jobGraph.getUserJars());
			for (PermanentBlobKey key : keys) {
				jobGraph.addBlob(key);
			}
		} catch (Exception e) {
			throw new JobSubmissionException(jobGraph.getJobID(), "Failed to upload user jars to blob server.", e);
		}

		log.info("Submitting job graph.");
		try {
			CompletableFuture<JobSubmitResponseBody> responseFuture = sendRequest(
				JobSubmitHeaders.getInstance(),
				new JobSubmitRequestBody(jobGraph));
			responseFuture.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw new JobSubmissionException(jobGraph.getJobID(), "Failed to submit JobGraph.", e);
		}
	}

	@Override
	public void stop(JobID jobID) throws Exception {
		JobTerminationMessageParameters params = new JobTerminationMessageParameters();
		params.jobPathParameter.resolve(jobID);
		params.terminationModeQueryParameter.resolve(Collections.singletonList(TerminationModeQueryParameter.TerminationMode.STOP));
		CompletableFuture<EmptyResponseBody> responseFuture = sendRequest(
			JobTerminationHeaders.getInstance(),
			params
		);
		responseFuture.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public void cancel(JobID jobID) throws Exception {
		JobTerminationMessageParameters params = new JobTerminationMessageParameters();
		params.jobPathParameter.resolve(jobID);
		params.terminationModeQueryParameter.resolve(Collections.singletonList(TerminationModeQueryParameter.TerminationMode.CANCEL));
		CompletableFuture<EmptyResponseBody> responseFuture = sendRequest(
			JobTerminationHeaders.getInstance(),
			params
		);
		responseFuture.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String cancelWithSavepoint(JobID jobId, @Nullable String savepointDirectory) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public CompletableFuture<String> triggerSavepoint(
			final JobID jobId,
			final @Nullable String savepointDirectory) throws FlinkException {
		final SavepointTriggerHeaders savepointTriggerHeaders = SavepointTriggerHeaders.getInstance();
		final SavepointTriggerMessageParameters savepointTriggerMessageParameters =
			savepointTriggerHeaders.getUnresolvedMessageParameters();
		savepointTriggerMessageParameters.jobID.resolve(jobId);

		final CompletableFuture<SavepointTriggerResponseBody> responseFuture;

		try {
			responseFuture = sendRequest(
				savepointTriggerHeaders,
				savepointTriggerMessageParameters,
				new SavepointTriggerRequestBody(savepointDirectory));
		} catch (IOException e) {
			throw new FlinkException("Could not send trigger savepoint request to Flink cluster.", e);
		}

		return responseFuture.thenApply(savepointTriggerResponseBody -> {
			final SavepointTriggerId savepointTriggerId = savepointTriggerResponseBody.getSavepointTriggerId();
			final SavepointInfo savepointInfo;
			try {
				savepointInfo = waitForSavepointCompletion(jobId, savepointTriggerId);
			} catch (Exception e) {
				throw new CompletionException(e);
			}
			if (savepointInfo.getFailureCause() != null) {
				throw new CompletionException(savepointInfo.getFailureCause());
			}
			return savepointInfo.getLocation();
		});
	}

	private SavepointInfo waitForSavepointCompletion(
			final JobID jobId,
			final SavepointTriggerId savepointTriggerId) throws Exception {
		return waitForResource(() -> {
			final SavepointStatusHeaders savepointStatusHeaders = SavepointStatusHeaders.getInstance();
			final SavepointStatusMessageParameters savepointStatusMessageParameters =
				savepointStatusHeaders.getUnresolvedMessageParameters();
			savepointStatusMessageParameters.jobIdPathParameter.resolve(jobId);
			savepointStatusMessageParameters.savepointTriggerIdPathParameter.resolve(savepointTriggerId);
			return sendRetryableRequest(
				savepointStatusHeaders,
				savepointStatusMessageParameters,
				EmptyRequestBody.getInstance(),
				isTimeoutException());
		});
	}

	@Override
	public CompletableFuture<Collection<JobStatusMessage>> listJobs() throws Exception {
		return sendRequest(JobsOverviewHeaders.getInstance())
			.thenApply(
				(multipleJobsDetails) -> multipleJobsDetails
					.getJobs()
					.stream()
					.map(detail -> new JobStatusMessage(
						detail.getJobId(),
						detail.getJobName(),
						detail.getStatus(),
						detail.getStartTime()))
					.collect(Collectors.toList()));
	}

	@Override
	public T getClusterId() {
		return clusterId;
	}

	private <R, A extends AsynchronouslyCreatedResource<R>> R waitForResource(
			final Supplier<CompletableFuture<A>> resourceFutureSupplier)
				throws IOException, InterruptedException, ExecutionException, TimeoutException {
		A asynchronouslyCreatedResource;
		long attempt = 0;
		while (true) {
			final CompletableFuture<A> responseFuture = resourceFutureSupplier.get();
			asynchronouslyCreatedResource = responseFuture.get();

			if (asynchronouslyCreatedResource.queueStatus().getId() == QueueStatus.Id.COMPLETED) {
				break;
			}
			Thread.sleep(waitStrategy.sleepTime(attempt));
			attempt++;
		}
		return asynchronouslyCreatedResource.resource();
	}

	// ======================================
	// Legacy stuff we actually implement
	// ======================================

	@Override
	public boolean hasUserJarsInClassPath(List<URL> userJarFiles) {
		return false;
	}

	@Override
	public void waitForClusterToBeReady() {
		// no op
	}

	@Override
	public String getWebInterfaceURL() {
		try {
			return restServerLeaderHolder.getLeaderAddress().toString();
		} catch (LeaderNotAvailableException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public GetClusterStatusResponse getClusterStatus() {
		return null;
	}

	@Override
	public List<String> getNewMessages() {
		return Collections.emptyList();
	}

	@Override
	public int getMaxSlots() {
		return 0;
	}

	//-------------------------------------------------------------------------
	// RestClient Helper
	//-------------------------------------------------------------------------

	private <M extends MessageHeaders<EmptyRequestBody, P, U>, U extends MessageParameters, P extends ResponseBody> CompletableFuture<P>
			sendRequest(M messageHeaders, U messageParameters) throws IOException, LeaderNotAvailableException {
		return sendRequest(messageHeaders, messageParameters, EmptyRequestBody.getInstance());
	}

	private <M extends MessageHeaders<R, P, EmptyMessageParameters>, R extends RequestBody, P extends ResponseBody> CompletableFuture<P>
			sendRequest(M messageHeaders, R request) throws IOException, LeaderNotAvailableException {
		return sendRequest(messageHeaders, EmptyMessageParameters.getInstance(), request);
	}

	private <M extends MessageHeaders<EmptyRequestBody, P, EmptyMessageParameters>, P extends ResponseBody> CompletableFuture<P>
			sendRequest(M messageHeaders) throws IOException, LeaderNotAvailableException {
		return sendRequest(messageHeaders, EmptyMessageParameters.getInstance(), EmptyRequestBody.getInstance());
	}

	private <M extends MessageHeaders<R, P, U>, U extends MessageParameters, R extends RequestBody, P extends ResponseBody> CompletableFuture<P>
			sendRequest(M messageHeaders, U messageParameters, R request) throws IOException, LeaderNotAvailableException {
		final URL restServerBaseUrl = restServerLeaderHolder.getLeaderAddress();
		return restClient.sendRequest(restServerBaseUrl.getHost(), restServerBaseUrl.getPort(), messageHeaders, messageParameters, request);
	}

	private <M extends MessageHeaders<R, P, U>, U extends MessageParameters, R extends RequestBody, P extends ResponseBody> CompletableFuture<P>
			sendRetryableRequest(M messageHeaders, U messageParameters, R request, Predicate<Throwable> retryPredicate) {
		return retry(() -> {
			final URL restServerBaseUrl = restServerLeaderHolder.getLeaderAddress();
			return restClient.sendRequest(restServerBaseUrl.getHost(), restServerBaseUrl.getPort(), messageHeaders, messageParameters, request);
		}, retryPredicate);
	}

	private <C> CompletableFuture<C> retry(
			CheckedSupplier<CompletableFuture<C>> operation,
			Predicate<Throwable> retryPredicate) {
		return FutureUtils.retryWithDelay(
			CheckedSupplier.unchecked(operation),
			MAX_RETRIES,
			RETRY_DELAY,
			retryPredicate,
			new ScheduledExecutorServiceAdapter(retryExecutorService));
	}

	private static Predicate<Throwable> isTimeoutException() {
		return (throwable) ->
			ExceptionUtils.findThrowable(throwable, java.net.ConnectException.class).isPresent() ||
				ExceptionUtils.findThrowable(throwable, java.net.SocketTimeoutException.class).isPresent() ||
				ExceptionUtils.findThrowable(throwable, ConnectTimeoutException.class).isPresent() ||
				ExceptionUtils.findThrowable(throwable, IOException.class).isPresent();
	}

	private static Predicate<Throwable> isHttpStatusUnsuccessfulException() {
		return (throwable) -> ExceptionUtils.findThrowable(throwable, RestClientException.class)
				.map(restClientException -> {
					final int code = restClientException.getHttpResponseStatus().code();
					return code < 200 || code > 299;
				})
				.orElse(false);
	}

	private abstract class RestClusterClientLeaderRetrievalListener implements LeaderRetrievalListener {
		@Override
		public final void handleError(final Exception exception) {
			log.error("Exception in LeaderRetrievalListener", exception);
			shutdown();
		}
	}

	private class RestServerLeaderRetrievalListener extends RestClusterClientLeaderRetrievalListener {
		@Override
		public void notifyLeaderAddress(final String leaderAddress, final UUID leaderSessionID) {
			try {
				logAndSysout("New rest server address: " + leaderAddress);
				restServerLeaderHolder.setLeaderAddress(leaderAddress != null ? new URL(leaderAddress) : null);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException("Could not parse URL from " + leaderAddress, e);
			}
		}
	}

	private class DispatcherLeaderRetrievalListener extends RestClusterClientLeaderRetrievalListener {
		@Override
		public void notifyLeaderAddress(final String leaderAddress, final UUID leaderSessionID) {
			if (leaderAddress != null) {
				final Option<String> host = AddressFromURIString.parse(leaderAddress).host();
				checkArgument(host.isDefined(), "Could not parse host from %s", leaderAddress);
				dispatcherLeaderHolder.setLeaderAddress(host.get());
			} else {
				dispatcherLeaderHolder.setLeaderAddress(null);
			}
		}
	}

}
