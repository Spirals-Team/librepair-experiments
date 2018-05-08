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
import org.apache.flink.runtime.dispatcher.DispatcherGateway;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.messages.Acknowledge;
import org.apache.flink.runtime.rest.handler.HandlerRequest;
import org.apache.flink.runtime.rest.handler.RestHandlerException;
import org.apache.flink.runtime.rest.messages.EmptyMessageParameters;
import org.apache.flink.runtime.rest.messages.job.JobSubmitRequestBody;
import org.apache.flink.runtime.rpc.RpcUtils;
import org.apache.flink.runtime.webmonitor.retriever.GatewayRetriever;
import org.apache.flink.testutils.category.New;
import org.apache.flink.util.TestLogger;

import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link JobSubmitHandler}.
 */
@Category(New.class)
public class JobSubmitHandlerTest extends TestLogger {
	
	@ClassRule
	public static final TemporaryFolder TMP = new TemporaryFolder();

	@Test
	public void testSerializationFailureHandling() throws Exception {
		DispatcherGateway mockGateway = mock(DispatcherGateway.class);
		when(mockGateway.submitJob(any(JobGraph.class), any(Time.class))).thenReturn(CompletableFuture.completedFuture(Acknowledge.get()));
		GatewayRetriever<DispatcherGateway> mockGatewayRetriever = mock(GatewayRetriever.class);

		JobSubmitHandler handler = new JobSubmitHandler(
			CompletableFuture.completedFuture("http://localhost:1234"),
			mockGatewayRetriever,
			RpcUtils.INF_TIMEOUT,
			Collections.emptyMap(),
			TMP.newFolder().toPath(),
			new Configuration());

		JobSubmitRequestBody request = new JobSubmitRequestBody(new byte[0], Collections.emptyList());

		try {
			handler.handleRequest(new HandlerRequest<>(request, EmptyMessageParameters.getInstance()), mockGateway);
			Assert.fail();
		} catch (RestHandlerException rhe) {
			Assert.assertEquals(HttpResponseStatus.BAD_REQUEST, rhe.getHttpResponseStatus());
		}
	}

	@Test
	public void testSuccessfulJobSubmission() throws Exception {
		DispatcherGateway mockGateway = mock(DispatcherGateway.class);
		when(mockGateway.submitJob(any(JobGraph.class), any(Time.class))).thenReturn(CompletableFuture.completedFuture(Acknowledge.get()));
		GatewayRetriever<DispatcherGateway> mockGatewayRetriever = mock(GatewayRetriever.class);

		JobSubmitHandler handler = new JobSubmitHandler(
			CompletableFuture.completedFuture("http://localhost:1234"),
			mockGatewayRetriever,
			RpcUtils.INF_TIMEOUT,
			Collections.emptyMap(),
			TMP.newFolder().toPath(),
			new Configuration());

		JobGraph job = new JobGraph("testjob");
		JobSubmitRequestBody request = new JobSubmitRequestBody(job, Collections.emptyList());

		handler.handleRequest(new HandlerRequest<>(request, EmptyMessageParameters.getInstance()), mockGateway)
			.get();
	}

	@Test
	public void testUserJarHandling() throws Exception {
		DispatcherGateway mockGateway = mock(DispatcherGateway.class);
		when(mockGateway.submitJob(any(JobGraph.class), any(Time.class))).thenReturn(CompletableFuture.completedFuture(Acknowledge.get()));
		GatewayRetriever<DispatcherGateway> mockGatewayRetriever = mock(GatewayRetriever.class);

		Path jarDir = TMP.newFolder().toPath();
		Path jarToDelete = jarDir.resolve("jarToDelete");
		Path jarToRetain = jarDir.resolve("jarToRetain");
		Files.createFile(jarToDelete);
		Files.createFile(jarToRetain);

		JobSubmitHandler handler = new JobSubmitHandler(
			CompletableFuture.completedFuture("http://localhost:1234"),
			mockGatewayRetriever,
			RpcUtils.INF_TIMEOUT,
			Collections.emptyMap(),
			jarDir,
			new Configuration());

		JobGraph job = new JobGraph("testjob");
		JobSubmitRequestBody request = new JobSubmitRequestBody(job, Arrays.asList(
			new JobSubmitRequestBody.JarEntry(jarToDelete.getFileName().toString(), true),
			new JobSubmitRequestBody.JarEntry(jarToRetain.getFileName().toString(), false)));

		handler.handleRequest(new HandlerRequest<>(request, EmptyMessageParameters.getInstance()), mockGateway)
			.get();

		Assert.assertFalse("Jar was not deleted.", !Files.exists(jarToDelete));
		Assert.assertTrue("Jar was not retained.", Files.exists(jarToRetain));

		Assert.assertEquals("User-jars were not correctly registered with the jobgraph.", 2, job.getUserJars().size());
		Assert.assertEquals("Blob keys were not properly registered with the jobgraph.", 2, job.getUserJarBlobKeys().size());
	}
}
