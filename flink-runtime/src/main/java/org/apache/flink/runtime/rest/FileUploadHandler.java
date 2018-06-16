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

import org.apache.flink.runtime.rest.handler.FileUploads;
import org.apache.flink.runtime.rest.messages.job.JobSubmitRequestBody;
import org.apache.flink.runtime.rest.util.RestConstants;

import org.apache.flink.shaded.netty4.io.netty.buffer.ByteBuf;
import org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled;
import org.apache.flink.shaded.netty4.io.netty.channel.ChannelHandlerContext;
import org.apache.flink.shaded.netty4.io.netty.channel.ChannelInboundHandler;
import org.apache.flink.shaded.netty4.io.netty.channel.ChannelPipeline;
import org.apache.flink.shaded.netty4.io.netty.channel.SimpleChannelInboundHandler;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.DefaultFullHttpRequest;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.FullHttpRequest;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpContent;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpHeaders;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpMethod;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpObject;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpRequest;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.LastHttpContent;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.multipart.Attribute;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.multipart.DiskFileUpload;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.multipart.HttpDataFactory;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.apache.flink.shaded.netty4.io.netty.util.AttributeKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.apache.flink.util.Preconditions.checkState;

/**
 * Writes multipart/form-data to disk. Delegates all other requests to the next
 * {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
 */
public class FileUploadHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadHandler.class);

	public static final String HTTP_ATTRIBUTE_REQUEST = "request";
	public static final String HTTP_ATTRIBUTE_JARS = "jars";

	static final AttributeKey<Path> UPLOADED_FILE = AttributeKey.valueOf("UPLOADED_FILE");
	static final AttributeKey<FileUploads> UPLOADED_FILES = AttributeKey.valueOf("UPLOADED_FILES");
	public static final AttributeKey<JobSubmitRequestBody> SUBMITTED_JOB = AttributeKey.valueOf("SUBMITTED_JOB");

	private static final HttpDataFactory DATA_FACTORY = new DefaultHttpDataFactory(true);

	private final Path uploadDir;

	private HttpPostRequestDecoder currentHttpPostRequestDecoder;

	private HttpRequest currentHttpRequest;

	private List<Path> currentUploadedFiles;

	private Path currentUploadDir;

	public FileUploadHandler(final Path uploadDir) {
		super(false);
		DiskFileUpload.baseDirectory = uploadDir.normalize().toAbsolutePath().toString();
		this.uploadDir = requireNonNull(uploadDir);
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final HttpObject msg) throws Exception {
		if (msg instanceof HttpRequest) {
			final HttpRequest httpRequest = (HttpRequest) msg;
			LOG.trace("Received request. URL:{} Method:{}", httpRequest.getUri(), httpRequest.getMethod());
			if (httpRequest.getMethod().equals(HttpMethod.POST)) {
				if (HttpPostRequestDecoder.isMultipart(httpRequest)) {
					currentHttpPostRequestDecoder = new HttpPostRequestDecoder(DATA_FACTORY, httpRequest);
					currentHttpRequest = httpRequest;
					currentUploadedFiles = new ArrayList<>(4);
					currentUploadDir = Files.createDirectory(uploadDir.resolve(UUID.randomUUID().toString()));
				} else {
					ctx.fireChannelRead(msg);
				}
			} else {
				ctx.fireChannelRead(msg);
			}
		} else if (msg instanceof HttpContent && currentHttpPostRequestDecoder != null) {
			// make sure that we still have a upload dir in case that it got deleted in the meanwhile
			RestServerEndpoint.createUploadDir(uploadDir, LOG);

			final HttpContent httpContent = (HttpContent) msg;
			currentHttpPostRequestDecoder.offer(httpContent);

			while (httpContent != LastHttpContent.EMPTY_LAST_CONTENT && currentHttpPostRequestDecoder.hasNext()) {
				final InterfaceHttpData data = currentHttpPostRequestDecoder.next();
				if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
					final DiskFileUpload fileUpload = (DiskFileUpload) data;
					checkState(fileUpload.isCompleted());

					final Path dest = currentUploadDir.resolve(fileUpload.getFilename());
					fileUpload.renameTo(dest.toFile());
					currentUploadedFiles.add(dest);
				} else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
					final Attribute request = (Attribute) data;
					if (data.getName().equals(HTTP_ATTRIBUTE_REQUEST)) {
						final byte[] jsonRequest = request.get();
						ByteBuf payload = Unpooled.wrappedBuffer(jsonRequest);
						FullHttpRequest httpRequest = new DefaultFullHttpRequest(
							currentHttpRequest.getProtocolVersion(),
							currentHttpRequest.getMethod(),
							currentHttpRequest.getUri(),
							payload);
						httpRequest.headers()
							.add(HttpHeaders.Names.CONTENT_LENGTH, payload.capacity())
							.add(HttpHeaders.Names.CONTENT_TYPE, RestConstants.REST_CONTENT_TYPE)
							.set(HttpHeaders.Names.HOST, currentHttpRequest.headers().get(HttpHeaders.Names.HOST))
							.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
						currentHttpRequest = httpRequest;
					} else {
						LOG.warn("Received unknown attribute {}, will be ignored.", data.getName());
					}
				}
				data.release();
			}

			if (httpContent instanceof LastHttpContent) {
				ctx.channel().attr(UPLOADED_FILES).set(new FileUploads(Collections.singleton(currentUploadDir)));
				ctx.fireChannelRead(currentHttpRequest);
				ctx.fireChannelRead(LastHttpContent.EMPTY_LAST_CONTENT);
				reset();
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private void reset() {
		currentHttpPostRequestDecoder.destroy();
		currentHttpPostRequestDecoder = null;
		currentHttpRequest = null;
		currentUploadDir = null;
	}
}
