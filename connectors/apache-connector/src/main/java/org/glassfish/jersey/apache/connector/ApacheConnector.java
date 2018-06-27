/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.apache.connector;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.message.internal.HeaderUtils;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.message.internal.Statuses;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.ChunkedOutputStream;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.TextUtils;
import org.apache.http.util.VersionInfo;

/**
 * A {@link Connector} that utilizes the Apache HTTP Client to send and receive
 * HTTP request and responses.
 * <p/>
 * The following properties are only supported at construction of this class:
 * <ul>
 * <li>{@link ApacheClientProperties#CONNECTION_MANAGER}</li>
 * <li>{@link ApacheClientProperties#REQUEST_CONFIG}</li>
 * <li>{@link ApacheClientProperties#CREDENTIALS_PROVIDER}</li>
 * <li>{@link ApacheClientProperties#DISABLE_COOKIES}</li>
 * <li>{@link ClientProperties#PROXY_URI}</li>
 * <li>{@link ClientProperties#PROXY_USERNAME}</li>
 * <li>{@link ClientProperties#PROXY_PASSWORD}</li>
 * <li>{@link ClientProperties#REQUEST_ENTITY_PROCESSING} - default value is {@link RequestEntityProcessing#CHUNKED}</li>
 * <li>{@link ApacheClientProperties#PREEMPTIVE_BASIC_AUTHENTICATION}</li>
 * <li>{@link ApacheClientProperties#RETRY_HANDLER}</li>
 * </ul>
 * <p>
 * This connector uses {@link RequestEntityProcessing#CHUNKED chunked encoding} as a default setting. This can
 * be overridden by the {@link ClientProperties#REQUEST_ENTITY_PROCESSING}. By default the
 * {@link ClientProperties#CHUNKED_ENCODING_SIZE} property is only supported by using default connection manager. If custom
 * connection manager needs to be used then chunked encoding size can be set by providing a custom
 * {@link org.apache.http.HttpClientConnection} (via custom {@link org.apache.http.impl.conn.ManagedHttpClientConnectionFactory})
 * and overriding {@code createOutputStream} method.
 * </p>
 * <p>
 * Using of authorization is dependent on the chunk encoding setting. If the entity
 * buffering is enabled, the entity is buffered and authorization can be performed
 * automatically in response to a 401 by sending the request again. When entity buffering
 * is disabled (chunked encoding is used) then the property
 * {@link org.glassfish.jersey.apache.connector.ApacheClientProperties#PREEMPTIVE_BASIC_AUTHENTICATION} must
 * be set to {@code true}.
 * </p>
 * <p>
 * If a {@link org.glassfish.jersey.client.ClientResponse} is obtained and an
 * entity is not read from the response then
 * {@link org.glassfish.jersey.client.ClientResponse#close()} MUST be called
 * after processing the response to release connection-based resources.
 * </p>
 * <p>
 * Client operations are thread safe, the HTTP connection may
 * be shared between different threads.
 * </p>
 * <p>
 * If a response entity is obtained that is an instance of {@link Closeable}
 * then the instance MUST be closed after processing the entity to release
 * connection-based resources.
 * </p>
 * <p>
 * The following methods are currently supported: HEAD, GET, POST, PUT, DELETE, OPTIONS, PATCH and TRACE.
 * </p>
 *
 * @author jorgeluisw@mac.com
 * @author Paul Sandoz
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 * @author Arul Dhesiaseelan (aruld at acm.org)
 * @see ApacheClientProperties#CONNECTION_MANAGER
 */
@SuppressWarnings("deprecation")
class ApacheConnector implements Connector {

    private static final Logger LOGGER = Logger.getLogger(ApacheConnector.class.getName());

    private static final VersionInfo vi;
    private static final String release;

    static {
        vi = VersionInfo.loadVersionInfo("org.apache.http.client", HttpClientBuilder.class.getClassLoader());
        release = (vi != null) ? vi.getRelease() : VersionInfo.UNAVAILABLE;
    }

    private final CloseableHttpClient client;
    private final CookieStore cookieStore;
    private final boolean preemptiveBasicAuth;
    private final RequestConfig requestConfig;

    /**
     * Create the new Apache HTTP Client connector.
     *
     * @param client JAX-RS client instance for which the connector is being created.
     * @param config client configuration.
     */
    ApacheConnector(final Client client, final Configuration config) {
        final Object connectionManager = config.getProperties().get(ApacheClientProperties.CONNECTION_MANAGER);
        if (connectionManager != null) {
            if (!(connectionManager instanceof HttpClientConnectionManager)) {
                LOGGER.log(
                        Level.WARNING,
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ApacheClientProperties.CONNECTION_MANAGER,
                                connectionManager.getClass().getName(),
                                HttpClientConnectionManager.class.getName())
                );
            }
        }

        Object reqConfig = config.getProperties().get(ApacheClientProperties.REQUEST_CONFIG);
        if (reqConfig != null) {
            if (!(reqConfig instanceof RequestConfig)) {
                LOGGER.log(
                        Level.WARNING,
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ApacheClientProperties.REQUEST_CONFIG,
                                reqConfig.getClass().getName(),
                                RequestConfig.class.getName())
                );
                reqConfig = null;
            }
        }

        final SSLContext sslContext = client.getSslContext();
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setConnectionManager(getConnectionManager(client, config, sslContext));
        clientBuilder.setConnectionManagerShared(
                PropertiesHelper.getValue(config.getProperties(), ApacheClientProperties.CONNECTION_MANAGER_SHARED, false, null));
        clientBuilder.setSslcontext(sslContext);

        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        final Object credentialsProvider = config.getProperty(ApacheClientProperties.CREDENTIALS_PROVIDER);
        if (credentialsProvider != null && (credentialsProvider instanceof CredentialsProvider)) {
            clientBuilder.setDefaultCredentialsProvider((CredentialsProvider) credentialsProvider);
        }

        final Object retryHandler = config.getProperties().get(ApacheClientProperties.RETRY_HANDLER);
        if (retryHandler != null && (retryHandler instanceof HttpRequestRetryHandler)) {
            clientBuilder.setRetryHandler((HttpRequestRetryHandler) retryHandler);
        }

        final Object proxyUri;
        proxyUri = config.getProperty(ClientProperties.PROXY_URI);
        if (proxyUri != null) {
            final URI u = getProxyUri(proxyUri);
            final HttpHost proxy = new HttpHost(u.getHost(), u.getPort(), u.getScheme());
            final String userName;
            userName = ClientProperties.getValue(config.getProperties(), ClientProperties.PROXY_USERNAME, String.class);
            if (userName != null) {
                final String password;
                password = ClientProperties.getValue(config.getProperties(), ClientProperties.PROXY_PASSWORD, String.class);

                if (password != null) {
                    final CredentialsProvider credsProvider = new BasicCredentialsProvider();
                    credsProvider.setCredentials(
                            new AuthScope(u.getHost(), u.getPort()),
                            new UsernamePasswordCredentials(userName, password)
                    );
                    clientBuilder.setDefaultCredentialsProvider(credsProvider);
                }
            }
            clientBuilder.setProxy(proxy);
        }

        final Boolean preemptiveBasicAuthProperty = (Boolean) config.getProperties()
                .get(ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION);
        this.preemptiveBasicAuth = (preemptiveBasicAuthProperty != null) ? preemptiveBasicAuthProperty : false;

        final boolean ignoreCookies = PropertiesHelper.isProperty(config.getProperties(), ApacheClientProperties.DISABLE_COOKIES);

        if (reqConfig != null) {
            final RequestConfig.Builder reqConfigBuilder = RequestConfig.copy((RequestConfig) reqConfig);
            if (ignoreCookies) {
                reqConfigBuilder.setCookieSpec(CookieSpecs.IGNORE_COOKIES);
            }
            requestConfig = reqConfigBuilder.build();
        } else {
            if (ignoreCookies) {
                requestConfigBuilder.setCookieSpec(CookieSpecs.IGNORE_COOKIES);
            }
            requestConfig = requestConfigBuilder.build();
        }

        if (requestConfig.getCookieSpec() == null || !requestConfig.getCookieSpec().equals(CookieSpecs.IGNORE_COOKIES)) {
            this.cookieStore = new BasicCookieStore();
            clientBuilder.setDefaultCookieStore(cookieStore);
        } else {
            this.cookieStore = null;
        }
        clientBuilder.setDefaultRequestConfig(requestConfig);
        this.client = clientBuilder.build();
    }

    private HttpClientConnectionManager getConnectionManager(final Client client,
                                                             final Configuration config,
                                                             final SSLContext sslContext) {
        final Object cmObject = config.getProperties().get(ApacheClientProperties.CONNECTION_MANAGER);

        // Connection manager from configuration.
        if (cmObject != null) {
            if (cmObject instanceof HttpClientConnectionManager) {
                return (HttpClientConnectionManager) cmObject;
            } else {
                LOGGER.log(
                        Level.WARNING,
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ApacheClientProperties.CONNECTION_MANAGER,
                                cmObject.getClass().getName(),
                                HttpClientConnectionManager.class.getName())
                );
            }
        }

        // Create custom connection manager.
        return createConnectionManager(
                client,
                config,
                sslContext,
                false);
    }

    private HttpClientConnectionManager createConnectionManager(
            final Client client,
            final Configuration config,
            final SSLContext sslContext,
            final boolean useSystemProperties) {

        final String[] supportedProtocols = useSystemProperties ? split(
                System.getProperty("https.protocols")) : null;
        final String[] supportedCipherSuites = useSystemProperties ? split(
                System.getProperty("https.cipherSuites")) : null;

        HostnameVerifier hostnameVerifier = client.getHostnameVerifier();

        final LayeredConnectionSocketFactory sslSocketFactory;
        if (sslContext != null) {
            sslSocketFactory = new SSLConnectionSocketFactory(
                    sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
        } else {
            if (useSystemProperties) {
                sslSocketFactory = new SSLConnectionSocketFactory(
                        (SSLSocketFactory) SSLSocketFactory.getDefault(),
                        supportedProtocols, supportedCipherSuites, hostnameVerifier);
            } else {
                sslSocketFactory = new SSLConnectionSocketFactory(
                        SSLContexts.createDefault(),
                        hostnameVerifier);
            }
        }

        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        final Integer chunkSize = ClientProperties.getValue(config.getProperties(),
                ClientProperties.CHUNKED_ENCODING_SIZE, ClientProperties.DEFAULT_CHUNK_SIZE, Integer.class);

        final PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(registry, new ConnectionFactory(chunkSize));

        if (useSystemProperties) {
            String s = System.getProperty("http.keepAlive", "true");
            if ("true".equalsIgnoreCase(s)) {
                s = System.getProperty("http.maxConnections", "5");
                final int max = Integer.parseInt(s);
                connectionManager.setDefaultMaxPerRoute(max);
                connectionManager.setMaxTotal(2 * max);
            }
        }

        return connectionManager;
    }

    private static String[] split(final String s) {
        if (TextUtils.isBlank(s)) {
            return null;
        }
        return s.split(" *, *");
    }

    /**
     * Get the {@link HttpClient}.
     *
     * @return the {@link HttpClient}.
     */
    @SuppressWarnings("UnusedDeclaration")
    public HttpClient getHttpClient() {
        return client;
    }

    /**
     * Get the {@link CookieStore}.
     *
     * @return the {@link CookieStore} instance or {@code null} when {@value ApacheClientProperties#DISABLE_COOKIES} set to
     * {@code true}.
     */
    public CookieStore getCookieStore() {
        return cookieStore;
    }

    private static URI getProxyUri(final Object proxy) {
        if (proxy instanceof URI) {
            return (URI) proxy;
        } else if (proxy instanceof String) {
            return URI.create((String) proxy);
        } else {
            throw new ProcessingException(LocalizationMessages.WRONG_PROXY_URI_TYPE(ClientProperties.PROXY_URI));
        }
    }

    @Override
    public ClientResponse apply(final ClientRequest clientRequest) throws ProcessingException {
        final HttpUriRequest request = getUriHttpRequest(clientRequest);
        final Map<String, String> clientHeadersSnapshot = writeOutBoundHeaders(clientRequest.getHeaders(), request);

        try {
            final CloseableHttpResponse response;
            final HttpClientContext context = HttpClientContext.create();
            if (preemptiveBasicAuth) {
                final AuthCache authCache = new BasicAuthCache();
                final BasicScheme basicScheme = new BasicScheme();
                authCache.put(getHost(request), basicScheme);
                context.setAuthCache(authCache);
            }

            // If a request-specific CredentialsProvider exists, use it instead of the default one
            CredentialsProvider credentialsProvider =
                    clientRequest.resolveProperty(ApacheClientProperties.CREDENTIALS_PROVIDER, CredentialsProvider.class);
            if (credentialsProvider != null) {
                context.setCredentialsProvider(credentialsProvider);
            }

            response = client.execute(getHost(request), request, context);
            HeaderUtils.checkHeaderChanges(clientHeadersSnapshot, clientRequest.getHeaders(), this.getClass().getName());

            final Response.StatusType status = response.getStatusLine().getReasonPhrase() == null
                    ? Statuses.from(response.getStatusLine().getStatusCode())
                    : Statuses.from(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());

            final ClientResponse responseContext = new ClientResponse(status, clientRequest);
            final List<URI> redirectLocations = context.getRedirectLocations();
            if (redirectLocations != null && !redirectLocations.isEmpty()) {
                responseContext.setResolvedRequestUri(redirectLocations.get(redirectLocations.size() - 1));
            }

            final Header[] respHeaders = response.getAllHeaders();
            final MultivaluedMap<String, String> headers = responseContext.getHeaders();
            for (final Header header : respHeaders) {
                final String headerName = header.getName();
                List<String> list = headers.get(headerName);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(header.getValue());
                headers.put(headerName, list);
            }

            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                if (headers.get(HttpHeaders.CONTENT_LENGTH) == null) {
                    headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(entity.getContentLength()));
                }

                final Header contentEncoding = entity.getContentEncoding();
                if (headers.get(HttpHeaders.CONTENT_ENCODING) == null && contentEncoding != null) {
                    headers.add(HttpHeaders.CONTENT_ENCODING, contentEncoding.getValue());
                }
            }

            try {
                responseContext.setEntityStream(new HttpClientResponseInputStream(getInputStream(response)));
            } catch (final IOException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return responseContext;
        } catch (final Exception e) {
            throw new ProcessingException(e);
        }
    }

    @Override
    public Future<?> apply(final ClientRequest request, final AsyncConnectorCallback callback) {
        try {
            ClientResponse response = apply(request);
            callback.response(response);
            return CompletableFuture.completedFuture(response);
        } catch (Throwable t) {
            callback.failure(t);
            CompletableFuture<Object> future = new CompletableFuture<>();
            future.completeExceptionally(t);
            return future;
        }
    }

    @Override
    public String getName() {
        return "Apache HttpClient " + release;
    }

    @Override
    public void close() {
        try {
            client.close();
        } catch (final IOException e) {
            throw new ProcessingException(LocalizationMessages.FAILED_TO_STOP_CLIENT(), e);
        }
    }

    private HttpHost getHost(final HttpUriRequest request) {
        return new HttpHost(request.getURI().getHost(), request.getURI().getPort(), request.getURI().getScheme());
    }

    private HttpUriRequest getUriHttpRequest(final ClientRequest clientRequest) {
        final RequestConfig.Builder requestConfigBuilder = RequestConfig.copy(requestConfig);

        final int connectTimeout = clientRequest.resolveProperty(ClientProperties.CONNECT_TIMEOUT, -1);
        final int socketTimeout = clientRequest.resolveProperty(ClientProperties.READ_TIMEOUT, -1);

        if (connectTimeout >= 0) {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
        }
        if (socketTimeout >= 0) {
            requestConfigBuilder.setSocketTimeout(socketTimeout);
        }

        final Boolean redirectsEnabled =
                clientRequest.resolveProperty(ClientProperties.FOLLOW_REDIRECTS, requestConfig.isRedirectsEnabled());
        requestConfigBuilder.setRedirectsEnabled(redirectsEnabled);

        final Boolean bufferingEnabled = clientRequest.resolveProperty(ClientProperties.REQUEST_ENTITY_PROCESSING,
                RequestEntityProcessing.class) == RequestEntityProcessing.BUFFERED;
        final HttpEntity entity = getHttpEntity(clientRequest, bufferingEnabled);

        return RequestBuilder
                .create(clientRequest.getMethod())
                .setUri(clientRequest.getUri())
                .setConfig(requestConfigBuilder.build())
                .setEntity(entity)
                .build();
    }

    private HttpEntity getHttpEntity(final ClientRequest clientRequest, final boolean bufferingEnabled) {
        final Object entity = clientRequest.getEntity();

        if (entity == null) {
            return null;
        }

        final AbstractHttpEntity httpEntity = new AbstractHttpEntity() {
            @Override
            public boolean isRepeatable() {
                return false;
            }

            @Override
            public long getContentLength() {
                return -1;
            }

            @Override
            public InputStream getContent() throws IOException, IllegalStateException {
                if (bufferingEnabled) {
                    final ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
                    writeTo(buffer);
                    return new ByteArrayInputStream(buffer.toByteArray());
                } else {
                    return null;
                }
            }

            @Override
            public void writeTo(final OutputStream outputStream) throws IOException {
                clientRequest.setStreamProvider(new OutboundMessageContext.StreamProvider() {
                    @Override
                    public OutputStream getOutputStream(final int contentLength) throws IOException {
                        return outputStream;
                    }
                });
                clientRequest.writeEntity();
            }

            @Override
            public boolean isStreaming() {
                return false;
            }
        };

        if (bufferingEnabled) {
            try {
                return new BufferedHttpEntity(httpEntity);
            } catch (final IOException e) {
                throw new ProcessingException(LocalizationMessages.ERROR_BUFFERING_ENTITY(), e);
            }
        } else {
            return httpEntity;
        }
    }

    private static Map<String, String> writeOutBoundHeaders(final MultivaluedMap<String, Object> headers,
                                                            final HttpUriRequest request) {
        final Map<String, String> stringHeaders = HeaderUtils.asStringHeadersSingleValue(headers);

        for (final Map.Entry<String, String> e : stringHeaders.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        return stringHeaders;
    }

    private static final class HttpClientResponseInputStream extends FilterInputStream {

        HttpClientResponseInputStream(final InputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override
        public void close() throws IOException {
            super.close();
        }
    }

    private static InputStream getInputStream(final CloseableHttpResponse response) throws IOException {

        final InputStream inputStream;

        if (response.getEntity() == null) {
            inputStream = new ByteArrayInputStream(new byte[0]);
        } else {
            final InputStream i = response.getEntity().getContent();
            if (i.markSupported()) {
                inputStream = i;
            } else {
                inputStream = new BufferedInputStream(i, ReaderWriter.BUFFER_SIZE);
            }
        }

        return new FilterInputStream(inputStream) {
            @Override
            public void close() throws IOException {
                response.close();
                super.close();
            }
        };
    }

    private static class ConnectionFactory extends ManagedHttpClientConnectionFactory {

        private static final AtomicLong COUNTER = new AtomicLong();

        private final int chunkSize;

        private ConnectionFactory(final int chunkSize) {
            this.chunkSize = chunkSize;
        }

        @Override
        public ManagedHttpClientConnection create(final HttpRoute route, final ConnectionConfig config) {
            final String id = "http-outgoing-" + Long.toString(COUNTER.getAndIncrement());

            return new HttpClientConnection(id, config.getBufferSize(), chunkSize);
        }
    }

    private static class HttpClientConnection extends DefaultManagedHttpClientConnection {

        private final int chunkSize;

        private HttpClientConnection(final String id, final int buffersize, final int chunkSize) {
            super(id, buffersize);

            this.chunkSize = chunkSize;
        }

        @Override
        protected OutputStream createOutputStream(final long len, final SessionOutputBuffer outbuffer) {
            if (len == ContentLengthStrategy.CHUNKED) {
                return new ChunkedOutputStream(chunkSize, outbuffer);
            }
            return super.createOutputStream(len, outbuffer);
        }
    }
}
