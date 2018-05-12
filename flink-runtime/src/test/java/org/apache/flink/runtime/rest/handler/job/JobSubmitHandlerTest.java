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

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.blob.BlobClient;
import org.apache.flink.runtime.blob.PermanentBlobKey;
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

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link JobSubmitHandler}.
 */
@Category(New.class)
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate
@PrepareForTest(BlobClient.class)
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

		JobSubmitRequestBody request = new JobSubmitRequestBody(new byte[0], Collections.emptyList(), Collections.emptyList());

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
		JobSubmitRequestBody request = new JobSubmitRequestBody(job, Collections.emptyList(), Collections.emptyList());

		handler.handleRequest(new HandlerRequest<>(request, EmptyMessageParameters.getInstance()), mockGateway)
			.get();
	}

	@Test
	public void testUserJarHandling() throws Exception {
		PowerMockito.mockStatic(BlobClient.class);
		when(BlobClient.uploadFiles(any(InetSocketAddress.class), any(Configuration.class), any(JobID.class), argThat(new ListSizeVerifier())))
			.thenReturn(Arrays.asList(new PermanentBlobKey()));
		DispatcherGateway mockGateway = mock(DispatcherGateway.class);
		when(mockGateway.submitJob(argThat(new JobGraphVerifier()), any(Time.class))).thenReturn(CompletableFuture.completedFuture(Acknowledge.get()));
		when(mockGateway.getBlobServerPort(any(Time.class))).thenReturn(CompletableFuture.completedFuture(1234));
		when(mockGateway.getAddress()).thenReturn("akka://dispatcher@localhost:1234");
		GatewayRetriever<DispatcherGateway> mockGatewayRetriever = mock(GatewayRetriever.class);

		Path jarDir = TMP.newFolder().toPath();
		Path jarToDelete = jarDir.resolve("jarToDelete");
		Files.createFile(jarToDelete);

		JobSubmitHandler handler = new JobSubmitHandler(
			CompletableFuture.completedFuture("http://localhost:1234"),
			mockGatewayRetriever,
			RpcUtils.INF_TIMEOUT,
			Collections.emptyMap(),
			jarDir,
			new Configuration());

		JobGraph job = new JobGraph("testjob");
		JobSubmitRequestBody request = new JobSubmitRequestBody(
			job, Collections.singletonList(jarToDelete.getFileName().toString()),
			Collections.emptyList());

		handler.handleRequest(new HandlerRequest<>(request, EmptyMessageParameters.getInstance()), mockGateway)
			.get();

		Assert.assertFalse("Jar was not deleted.", Files.exists(jarToDelete));
	}

	private static class ListSizeVerifier extends TypeSafeMatcher<List<org.apache.flink.core.fs.Path>> {
		@Override
		protected boolean matchesSafely(List<org.apache.flink.core.fs.Path> paths) {
			Assert.assertEquals("Incorrect number of jars were passed to the blob client.", 1, paths.size());
			return true;
		}

		@Override
		public void describeTo(Description description) {
		}
	}

	private static class JobGraphVerifier extends TypeSafeMatcher<JobGraph> {
		@Override
		protected boolean matchesSafely(JobGraph job) {
			Assert.assertEquals("User-jars were not correctly registered with the job graph.", 1, job.getUserJars().size());
			Assert.assertEquals("Blob keys were not properly registered with the job graph.", 1, job.getUserJarBlobKeys().size());
			return true;
		}

		@Override
		public void describeTo(Description description) {
		}
	}
}
