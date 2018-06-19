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

package org.apache.flink.runtime.rest;

import org.apache.flink.runtime.rest.util.RestMapperUtils;
import org.apache.flink.util.Preconditions;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link FileUploadHandler}. Ensures that multipart http messages containing files and/or json are properly
 * handled.
 */
public class FileUploadHandlerTest extends MultipartUploadTestBase {

	private static final ObjectMapper OBJECT_MAPPER = RestMapperUtils.getStrictObjectMapper();

	private static Request buildRequest(String headerUrl, @Nullable TestRequestBody request, boolean includeFile) throws IOException {
		Preconditions.checkArgument(includeFile || (request != null), "You have to either include JSON or a file.");
		MultipartBody.Builder builder = new MultipartBody.Builder();

		if (includeFile) {
			okhttp3.RequestBody filePayload1 = okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"), file1);
			builder = builder.addFormDataPart("file1", file1.getName(), filePayload1);

			okhttp3.RequestBody filePayload2 = okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"), file2);
			builder = builder.addFormDataPart("file2", file2.getName(), filePayload2);
		}

		if (request != null) {
			StringWriter sw = new StringWriter();
			OBJECT_MAPPER.writeValue(sw, request);

			String jsonPayload = sw.toString();

			builder = builder.addFormDataPart(org.apache.flink.runtime.rest.FileUploadHandler.HTTP_ATTRIBUTE_REQUEST, jsonPayload);
		}

		MultipartBody multipartBody = builder
			.setType(MultipartBody.FORM)
			.build();

		return new Request.Builder()
			.url(serverAddress + headerUrl)
			.post(multipartBody)
			.build();
	}

	@Test
	public void testMixedMultipart() throws Exception {
		OkHttpClient client = new OkHttpClient();

		Request jsonRequest = buildRequest(mixedHandler.getMessageHeaders().getTargetRestEndpointURL(), new TestRequestBody(), false);
		try (Response response = client.newCall(jsonRequest).execute()) {
			// explicitly rejected by the test handler implementation
			assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.code());
		}

		Request fileRequest = buildRequest(mixedHandler.getMessageHeaders().getTargetRestEndpointURL(), null, true);
		try (Response response = client.newCall(fileRequest).execute()) {
			// expected JSON payload is missing
			assertEquals(HttpResponseStatus.BAD_REQUEST.code(), response.code());
		}

		TestRequestBody json = new TestRequestBody();
		Request mixedRequest = buildRequest(mixedHandler.getMessageHeaders().getTargetRestEndpointURL(), json, true);
		try (Response response = client.newCall(mixedRequest).execute()) {
			assertEquals(mixedHandler.getMessageHeaders().getResponseStatusCode().code(), response.code());
			assertEquals(json, mixedHandler.lastReceivedRequest);
		}
	}

	@Test
	public void testJsonMultipart() throws Exception {
		OkHttpClient client = new OkHttpClient();

		TestRequestBody json = new TestRequestBody();
		Request jsonRequest = buildRequest(jsonHandler.getMessageHeaders().getTargetRestEndpointURL(), json, false);
		try (Response response = client.newCall(jsonRequest).execute()) {
			assertEquals(jsonHandler.getMessageHeaders().getResponseStatusCode().code(), response.code());
			assertEquals(json, jsonHandler.lastReceivedRequest);
		}

		Request fileRequest = buildRequest(jsonHandler.getMessageHeaders().getTargetRestEndpointURL(), null, true);
		try (Response response = client.newCall(fileRequest).execute()) {
			// either because JSON payload is missing or FileUploads are outright forbidden
			assertEquals(HttpResponseStatus.BAD_REQUEST.code(), response.code());
		}

		Request mixedRequest = buildRequest(jsonHandler.getMessageHeaders().getTargetRestEndpointURL(), new TestRequestBody(), true);
		try (Response response = client.newCall(mixedRequest).execute()) {
			// FileUploads are outright forbidden
			assertEquals(HttpResponseStatus.BAD_REQUEST.code(), response.code());
		}
	}

	@Test
	public void testFileMultipart() throws Exception {
		OkHttpClient client = new OkHttpClient();

		Request jsonRequest = buildRequest(fileHandler.getMessageHeaders().getTargetRestEndpointURL(), new TestRequestBody(), false);
		try (Response response = client.newCall(jsonRequest).execute()) {
			// JSON payload did not match expected format
			assertEquals(HttpResponseStatus.BAD_REQUEST.code(), response.code());
		}

		Request fileRequest = buildRequest(fileHandler.getMessageHeaders().getTargetRestEndpointURL(), null, true);
		try (Response response = client.newCall(fileRequest).execute()) {
			assertEquals(fileHandler.getMessageHeaders().getResponseStatusCode().code(), response.code());
		}

		Request mixedRequest = buildRequest(fileHandler.getMessageHeaders().getTargetRestEndpointURL(), new TestRequestBody(), true);
		try (Response response = client.newCall(mixedRequest).execute()) {
			// JSON payload did not match expected format
			assertEquals(HttpResponseStatus.BAD_REQUEST.code(), response.code());
		}
	}
}
