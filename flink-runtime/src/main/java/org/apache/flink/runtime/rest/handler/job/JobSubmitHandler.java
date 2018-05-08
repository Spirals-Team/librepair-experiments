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

import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.blob.BlobClient;
import org.apache.flink.runtime.blob.PermanentBlobKey;
import org.apache.flink.runtime.dispatcher.DispatcherGateway;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.rest.NotFoundException;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * This handler can be used to submit jobs to a Flink cluster.
 */
public final class JobSubmitHandler extends AbstractRestHandler<DispatcherGateway, JobSubmitRequestBody, JobSubmitResponseBody, EmptyMessageParameters> {

	private final Path jarDir;
	private final Configuration config;

	public JobSubmitHandler(
			CompletableFuture<String> localRestAddress,
			GatewayRetriever<? extends DispatcherGateway> leaderRetriever,
			Time timeout,
			Map<String, String> headers,
			Path jarDir,
			Configuration config) {
		super(localRestAddress, leaderRetriever, timeout, headers, JobSubmitHeaders.getInstance());
		this.jarDir = jarDir;
		this.config = config;
	}

	@Override
	protected CompletableFuture<JobSubmitResponseBody> handleRequest(@Nonnull HandlerRequest<JobSubmitRequestBody, EmptyMessageParameters> request, @Nonnull DispatcherGateway gateway) throws RestHandlerException {
		JobGraph jobGraph;
		try (ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(request.getRequestBody().serializedJobGraph))) {
			jobGraph = (JobGraph) objectIn.readObject();
		} catch (Exception e) {
			throw new RestHandlerException(
				"Failed to deserialize JobGraph.",
				HttpResponseStatus.BAD_REQUEST,
				e);
		}

		Collection<JobSubmitRequestBody.JarEntry> jarEntries = request.getRequestBody().jarFiles;
		Collection<String> jarsToDelete = new ArrayList<>(jarEntries.size());
		if (!jarEntries.isEmpty()) {
			for (JobSubmitRequestBody.JarEntry jarEntry : jarEntries) {
				Path jar = jarDir.resolve(jarEntry.jarId);
				if (!Files.exists(jar)) {
					throw new NotFoundException("Jar " + jarEntry.jarId + " does not exist.");
				}
				jobGraph.addJar(new org.apache.flink.core.fs.Path(jar.toUri()));
				if (jarEntry.deleteAfterSubmission) {
					jarsToDelete.add(jarEntry.jarId);
				}
			}
		}

		CompletableFuture<JobGraph> jobGraphFuture;
		// the client may have set the blob keys already
		if (jobGraph.getUserJarBlobKeys().isEmpty() || !jarEntries.isEmpty()) {
			CompletableFuture<Integer> blobServerPortFuture = gateway.getBlobServerPort(timeout);

			jobGraphFuture = blobServerPortFuture.thenApply(blobServerPort -> {
				final InetSocketAddress address = new InetSocketAddress(getDispatcherHost(gateway), blobServerPort);
				final List<PermanentBlobKey> keys;
				try {
					keys = BlobClient.uploadFiles(address, config, jobGraph.getJobID(), jobGraph.getUserJars());
				} catch (IOException ioe) {
					log.error("Could not upload job jar files.", ioe);
					throw new CompletionException(new RestHandlerException("Could not upload job jar files.", HttpResponseStatus.INTERNAL_SERVER_ERROR));
				}

				for (PermanentBlobKey key : keys) {
					jobGraph.addUserJarBlobKey(key);
				}

				return jobGraph;
			});
		} else {
			jobGraphFuture = CompletableFuture.completedFuture(jobGraph);
		}

		CompletableFuture<JobSubmitResponseBody> submissionFuture = jobGraphFuture
			.thenCompose(finalizedJobGraph -> gateway.submitJob(jobGraph, timeout))
			.thenApply(ack -> new JobSubmitResponseBody("/jobs/" + jobGraph.getJobID()));

		submissionFuture.thenRun(() -> {
			for (String jarToDelete : jarsToDelete) {
				Path jar = jarDir.resolve(jarToDelete);
				try {
					Files.delete(jar);
				} catch (IOException e) {
					log.warn("Failed to delete jar {}.", jar, e);
				}
			}
		});

		return submissionFuture;
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
