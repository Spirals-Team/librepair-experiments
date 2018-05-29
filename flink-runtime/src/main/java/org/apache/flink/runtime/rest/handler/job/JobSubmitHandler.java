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

package org.apache.flink.runtime.rest.handler.job;

import org.apache.flink.api.common.cache.DistributedCache;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.blob.BlobClient;
import org.apache.flink.runtime.blob.PermanentBlobKey;
import org.apache.flink.runtime.dispatcher.DispatcherGateway;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.rest.handler.AbstractRestHandler;
import org.apache.flink.runtime.rest.handler.HandlerRequest;
import org.apache.flink.runtime.rest.handler.RestHandlerException;
import org.apache.flink.runtime.rest.messages.EmptyMessageParameters;
import org.apache.flink.runtime.rest.messages.job.JobSubmitHeaders;
import org.apache.flink.runtime.rest.messages.job.JobSubmitRequestBody;
import org.apache.flink.runtime.rest.messages.job.JobSubmitResponseBody;
import org.apache.flink.runtime.util.ScalaUtils;
import org.apache.flink.runtime.webmonitor.retriever.GatewayRetriever;

import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;

import akka.actor.AddressFromURIString;

import javax.annotation.Nonnull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * This handler can be used to submit jobs to a Flink cluster.
 */
public final class JobSubmitHandler extends AbstractRestHandler<DispatcherGateway, JobSubmitRequestBody, JobSubmitResponseBody, EmptyMessageParameters> {

	private final Configuration config;

	public JobSubmitHandler(
			CompletableFuture<String> localRestAddress,
			GatewayRetriever<? extends DispatcherGateway> leaderRetriever,
			Time timeout,
			Map<String, String> headers,
			Configuration config) {
		super(localRestAddress, leaderRetriever, timeout, headers, JobSubmitHeaders.getInstance());
		this.config = config;
	}

	@Override
	protected CompletableFuture<JobSubmitResponseBody> handleRequest(@Nonnull HandlerRequest<JobSubmitRequestBody, EmptyMessageParameters> request, @Nonnull DispatcherGateway gateway) throws RestHandlerException {
		final JobSubmitRequestBody requestBody = request.getRequestBody();
		JobGraph jobGraph;
		try (ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(requestBody.serializedJobGraph))) {
			jobGraph = (JobGraph) objectIn.readObject();
		} catch (Exception e) {
			throw new RestHandlerException(
				"Failed to deserialize JobGraph.",
				HttpResponseStatus.BAD_REQUEST,
				e);
		}

		Collection<Path> jars = requestBody.getJars();
		// the existing entries still reference client-local jars
		jobGraph.getUserJars().clear();
		for (Path jar : jars) {
			jobGraph.addJar(new org.apache.flink.core.fs.Path(jar.toUri()));
		}

		Map<String, Tuple2<String, DistributedCache.DistributedCacheEntry>> remappedArtifactEntries = jobGraph.getUserArtifacts().entrySet().stream()
			.collect(Collectors.toMap(
				entry -> new org.apache.flink.core.fs.Path(entry.getValue().filePath).getName(),
				entry -> Tuple2.of(entry.getKey(), entry.getValue())
			));
		Map<String, Path> uploadedArtifacts = requestBody.getArtifacts().stream()
			.collect(Collectors.toMap(
				artifact -> new org.apache.flink.core.fs.Path(artifact.toUri()).getName(),
				artifact -> artifact
			));

		// the existing entries may still reference client-local artifacts
		jobGraph.getUserArtifacts().clear();
		if (!remappedArtifactEntries.isEmpty()) {
			for (Map.Entry<String, Tuple2<String, DistributedCache.DistributedCacheEntry>> entry : remappedArtifactEntries.entrySet()) {
				String fileName = entry.getKey();
				String dcEntryName = entry.getValue().f0;
				DistributedCache.DistributedCacheEntry dcEntry = entry.getValue().f1;
				Path uploadedArtifact = uploadedArtifacts.get(fileName);
				if (uploadedArtifact != null) {
					jobGraph.addUserArtifact(dcEntryName, new DistributedCache.DistributedCacheEntry(
						uploadedArtifact.toUri().toString(), dcEntry.isExecutable, dcEntry.isZipped));
				} else {
				    throw new RestHandlerException("Could not map uploaded file to original entry.", HttpResponseStatus.BAD_REQUEST);
				}
			}
		}

		CompletableFuture<JobGraph> jobGraphFuture;

		CompletableFuture<Integer> blobServerPortFuture = gateway.getBlobServerPort(timeout);

		jobGraphFuture = blobServerPortFuture.thenApply(blobServerPort -> {
			final InetSocketAddress address = new InetSocketAddress(getDispatcherHost(gateway), blobServerPort);
			final List<PermanentBlobKey> keys;
			try {
				keys = BlobClient.uploadFiles(address, config, jobGraph.getJobID(), jobGraph.getUserJars());
				jobGraph.uploadUserArtifacts(address, config);
			} catch (IOException ioe) {
				log.error("Could not upload job jar files.", ioe);
				throw new CompletionException(new RestHandlerException("Could not upload job jar files.", HttpResponseStatus.INTERNAL_SERVER_ERROR));
			}

			for (PermanentBlobKey key : keys) {
				jobGraph.addUserJarBlobKey(key);
			}

			return jobGraph;
		});

		CompletableFuture<JobSubmitResponseBody> submissionFuture = jobGraphFuture
			.thenCompose(finalizedJobGraph -> gateway.submitJob(jobGraph, timeout))
			.thenApply(ack -> new JobSubmitResponseBody("/jobs/" + jobGraph.getJobID()));

		CompletableFuture<Void> submissionCleanupFuture = submissionFuture.thenRun(() -> {
			// TODO: cleanup artifacts
			// TODO: cleanup parent directories
			for (Path jarToDelete : jars) {
				try {
					Files.delete(jarToDelete);
				} catch (IOException e) {
					log.warn("Failed to delete jar {}.", jarToDelete, e);
				}
			}
		});

		return submissionFuture.thenCombine(submissionCleanupFuture, (responseBody, ignored) -> responseBody);
	}

	private static String getDispatcherHost(DispatcherGateway gateway) {
		String dispatcherAddress = gateway.getAddress();
		final Optional<String> host = ScalaUtils.toJava(AddressFromURIString.parse(dispatcherAddress).host());

		return host.orElseGet(() -> {
			// if the dispatcher address does not contain a host part, then assume it's running
			// on the same machine as the handler
			return "localhost";
		});
	}
}
