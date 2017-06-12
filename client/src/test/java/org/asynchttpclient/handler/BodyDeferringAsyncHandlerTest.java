/*
 * Copyright (c) 2010-2012 Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.asynchttpclient.handler;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_OCTET_STREAM;
import static org.apache.commons.io.IOUtils.copy;
import static org.asynchttpclient.Dsl.*;
import static org.asynchttpclient.test.TestUtils.findFreePort;
import static org.testng.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.asynchttpclient.AbstractBasicTest;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Response;
import org.asynchttpclient.exception.RemotelyClosedException;
import org.asynchttpclient.handler.BodyDeferringAsyncHandler.BodyDeferringInputStream;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.testng.annotations.Test;

public class BodyDeferringAsyncHandlerTest extends AbstractBasicTest {

    protected static final int CONTENT_LENGTH_VALUE = 100000;

    public static class SlowAndBigHandler extends AbstractHandler {

        public void handle(String pathInContext, Request request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {

            httpResponse.setStatus(200);
            httpResponse.setContentLength(CONTENT_LENGTH_VALUE);
            httpResponse.setContentType(APPLICATION_OCTET_STREAM.toString());

            httpResponse.flushBuffer();

            final boolean wantFailure = httpRequest.getHeader("X-FAIL-TRANSFER") != null;
            final boolean wantSlow = httpRequest.getHeader("X-SLOW") != null;

            OutputStream os = httpResponse.getOutputStream();
            for (int i = 0; i < CONTENT_LENGTH_VALUE; i++) {
                os.write(i % 255);

                if (wantSlow) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        // nuku
                    }
                }

                if (wantFailure) {
                    if (i > CONTENT_LENGTH_VALUE / 2) {
                        // kaboom
                        // yes, response is committed, but Jetty does aborts and
                        // drops connection
                        httpResponse.sendError(500);
                        break;
                    }
                }
            }

            httpResponse.getOutputStream().flush();
            httpResponse.getOutputStream().close();
        }
    }

    // a /dev/null but counting how many bytes it ditched
    public static class CountingOutputStream extends OutputStream {
        private int byteCount = 0;

        @Override
        public void write(int b) throws IOException {
            // /dev/null
            byteCount++;
        }

        public int getByteCount() {
            return byteCount;
        }
    }

    public AbstractHandler configureHandler() throws Exception {
        return new SlowAndBigHandler();
    }

    public AsyncHttpClientConfig getAsyncHttpClientConfig() {
        // for this test brevity's sake, we are limiting to 1 retries
        return config().setMaxRequestRetry(0).setRequestTimeout(10000).build();
    }

    @Test(groups = "standalone")
    public void deferredSimple() throws IOException, ExecutionException, TimeoutException, InterruptedException {
        try (AsyncHttpClient client = asyncHttpClient(getAsyncHttpClientConfig())) {
            BoundRequestBuilder r = client.prepareGet("http://localhost:" + port1 + "/deferredSimple");

            CountingOutputStream cos = new CountingOutputStream();
            BodyDeferringAsyncHandler bdah = new BodyDeferringAsyncHandler(cos);
            Future<Response> f = r.execute(bdah);
            Response resp = bdah.getResponse();
            assertNotNull(resp);
            assertEquals(resp.getStatusCode(), HttpServletResponse.SC_OK);
            assertEquals(resp.getHeader(CONTENT_LENGTH), String.valueOf(CONTENT_LENGTH_VALUE));
            // we got headers only, it's probably not all yet here (we have BIG file
            // downloading)
            assertTrue(cos.getByteCount() <= CONTENT_LENGTH_VALUE);

            // now be polite and wait for body arrival too (otherwise we would be
            // dropping the "line" on server)
            f.get();
            // it all should be here now
            assertEquals(cos.getByteCount(), CONTENT_LENGTH_VALUE);
        }
    }

    @Test(groups = "standalone", expectedExceptions = RemotelyClosedException.class)
    public void deferredSimpleWithFailure() throws Throwable {
        try (AsyncHttpClient client = asyncHttpClient(getAsyncHttpClientConfig())) {
            BoundRequestBuilder r = client.prepareGet("http://localhost:" + port1 + "/deferredSimpleWithFailure").addHeader("X-FAIL-TRANSFER", Boolean.TRUE.toString());

            CountingOutputStream cos = new CountingOutputStream();
            BodyDeferringAsyncHandler bdah = new BodyDeferringAsyncHandler(cos);
            Future<Response> f = r.execute(bdah);
            Response resp = bdah.getResponse();
            assertNotNull(resp);
            assertEquals(resp.getStatusCode(), HttpServletResponse.SC_OK);
            assertEquals(resp.getHeader(CONTENT_LENGTH), String.valueOf(CONTENT_LENGTH_VALUE));
            // we got headers only, it's probably not all yet here (we have BIG file
            // downloading)
            assertTrue(cos.getByteCount() <= CONTENT_LENGTH_VALUE);

            // now be polite and wait for body arrival too (otherwise we would be
            // dropping the "line" on server)
            try {
                f.get();
            } catch (ExecutionException e) {
                // good
                // it's incomplete, there was an error
                assertNotEquals(cos.getByteCount(), CONTENT_LENGTH_VALUE);
                throw e.getCause();
            }
        }
    }

    @Test(groups = "standalone")
    public void deferredInputStreamTrick() throws IOException, ExecutionException, TimeoutException, InterruptedException {
        try (AsyncHttpClient client = asyncHttpClient(getAsyncHttpClientConfig())) {
            BoundRequestBuilder r = client.prepareGet("http://localhost:" + port1 + "/deferredInputStreamTrick");

            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);
            BodyDeferringAsyncHandler bdah = new BodyDeferringAsyncHandler(pos);

            Future<Response> f = r.execute(bdah);

            BodyDeferringInputStream is = new BodyDeferringInputStream(f, bdah, pis);

            Response resp = is.getAsapResponse();
            assertNotNull(resp);
            assertEquals(resp.getStatusCode(), HttpServletResponse.SC_OK);
            assertEquals(resp.getHeader(CONTENT_LENGTH), String.valueOf(CONTENT_LENGTH_VALUE));
            // "consume" the body, but our code needs input stream
            CountingOutputStream cos = new CountingOutputStream();
            try {
                copy(is, cos);
            } finally {
                is.close();
                cos.close();
            }

            // now we don't need to be polite, since consuming and closing
            // BodyDeferringInputStream does all.
            // it all should be here now
            assertEquals(cos.getByteCount(), CONTENT_LENGTH_VALUE);
        }
    }

    @Test(groups = "standalone", expectedExceptions = RemotelyClosedException.class)
    public void deferredInputStreamTrickWithFailure() throws Throwable {
        try (AsyncHttpClient client = asyncHttpClient(getAsyncHttpClientConfig())) {
            BoundRequestBuilder r = client.prepareGet("http://localhost:" + port1 + "/deferredInputStreamTrickWithFailure").addHeader("X-FAIL-TRANSFER", Boolean.TRUE.toString());
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);
            BodyDeferringAsyncHandler bdah = new BodyDeferringAsyncHandler(pos);

            Future<Response> f = r.execute(bdah);

            BodyDeferringInputStream is = new BodyDeferringInputStream(f, bdah, pis);

            Response resp = is.getAsapResponse();
            assertNotNull(resp);
            assertEquals(resp.getStatusCode(), HttpServletResponse.SC_OK);
            assertEquals(resp.getHeader(CONTENT_LENGTH), String.valueOf(CONTENT_LENGTH_VALUE));
            // "consume" the body, but our code needs input stream
            CountingOutputStream cos = new CountingOutputStream();
            try {
                try {
                    copy(is, cos);
                } finally {
                    is.close();
                    cos.close();
                }
            } catch (IOException e) {
                throw e.getCause();
            }
        }
    }

    @Test(groups = "standalone", expectedExceptions = IOException.class)
    public void testConnectionRefused() throws IOException, ExecutionException, TimeoutException, InterruptedException {
        int newPortWithoutAnyoneListening = findFreePort();
        try (AsyncHttpClient client = asyncHttpClient(getAsyncHttpClientConfig())) {
            BoundRequestBuilder r = client.prepareGet("http://localhost:" + newPortWithoutAnyoneListening + "/testConnectionRefused");

            CountingOutputStream cos = new CountingOutputStream();
            BodyDeferringAsyncHandler bdah = new BodyDeferringAsyncHandler(cos);
            r.execute(bdah);
            bdah.getResponse();
        }
    }
}
