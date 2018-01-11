package org.jboss.resteasy.client.jaxrs.engines;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.DeferredContentProvider;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.util.Callback;
import org.jboss.resteasy.client.jaxrs.AsyncClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.internal.ClientInvocation;
import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;

public class JettyClientEngine implements AsyncClientHttpEngine {
    private static final InvocationCallback<ClientResponse> NOP = new InvocationCallback<ClientResponse>() {
        @Override
        public void completed(ClientResponse response) {
        }

        @Override
        public void failed(Throwable throwable) {
        }
    };

    private final HttpClient client;

    public JettyClientEngine(HttpClient client) {
        if (!client.isStarted()) {
            try {
                client.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.client = client;
    }

    @Override
    public SSLContext getSslContext() {
        return client.getSslContextFactory().getSslContext();
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientResponse invoke(ClientInvocation invocation) {
        Future<ClientResponse> future = submit(invocation, true, NOP, null);
        try {
            return future.get(client.getIdleTimeout(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            future.cancel(true);
            throw clientException(e, null);
        } catch (ExecutionException | TimeoutException e) {
            throw clientException(e.getCause(), null); // NOPMD
        }
    }

    /**
     * Implementation note: due to lack of asynchronous message decoders the request must either be buffered,
     * or it must have a {@code null} extractor and type parameter {@code <T>} must be {@link ClientResponse},
     * which will read the data through its stream.  It is not possible to use the synchronous JAX-RS message
     * decoding infrastructure without buffering or spinning up auxiliary threads (arguably more expensive than buffering).
     *
     * @see AsyncClientHttpEngine#submit(ClientInvocation, boolean, InvocationCallback, org.jboss.resteasy.client.jaxrs.AsyncClientHttpEngine.ResultExtractor)
     */
    @SuppressWarnings("resource")
    @Override
    public <T> Future<T> submit(ClientInvocation invocation, boolean buffered, InvocationCallback<T> callback, ResultExtractor<T> extractor) {
        final Request request = client.newRequest(invocation.getUri());
        final CompletableFuture<T> future = new RequestFuture<T>(request);

        request.method(invocation.getMethod());
        invocation.getHeaders().asMap().forEach((h, vs) -> vs.forEach(v -> request.header(h, v)));

        final DeferredContentProvider content;
        if (invocation.getEntity() != null) {
            content = new DeferredContentProvider();
            request.content(content);
        } else {
            content = null;
        }

        request.send(new Response.Listener.Adapter() {
            private ClientResponse cr;
            private JettyResponseStream stream = new JettyResponseStream();

            @Override
            public void onHeaders(Response response) {
                cr = new JettyClientResponse(invocation.getClientConfiguration(), stream, () -> future.cancel(true));
                cr.setProperties(invocation.getMutableProperties());
                cr.setStatus(response.getStatus());
                cr.setHeaders(extract(response.getHeaders()));
                if (!buffered) {
                    if (extractor != null) {
                        throw new IllegalStateException("TODO: ResultExtractor is synchronous and may not be used without buffering.");
                    }
                    future.complete((T) cr); // TODO: dangerous cast, see javadoc!
                }
            }

            @Override
            public void onContent(Response response, ByteBuffer content, Callback callback) {
                stream.offer(content, callback);
            }

            @Override
            public void onFailure(Response response, Throwable failure) {
                future.completeExceptionally(failure);
                callback.failed(failure);
            }

            @Override
            public void onSuccess(Response response) {
                if (buffered) {
                    try {
                        final T t = extractor.extractResult(cr);
                        future.complete(t);
                        callback.completed(t);
                    } catch (Exception e) {
                        onFailure(response, e);
                    }
                }
            }

            @Override
            public void onComplete(Result result) {
                if (content != null) {
                    content.close();
                }
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        });

        if (content != null) {
            try {
                invocation.writeRequestBody(new JettyContentStream(client.getByteBufferPool(), content));
            } catch (IOException e) {
                request.abort(e);
                future.completeExceptionally(e);
            }
            content.close();
        }
        return future;
    }

    @Override
    public void close() {
        try {
            client.stop();
        } catch (Exception e) {
            throw new RuntimeException("Unable to close JettyHttpEngine", e);
        }
    }

    MultivaluedMap<String, String> extract(HttpFields headers) {
        final MultivaluedMap<String, String> extracted = new MultivaluedHashMap<>();
        headers.forEach(h -> extracted.add(h.getName(), h.getValue()));
        return extracted;
    }

    private static RuntimeException clientException(Throwable ex, javax.ws.rs.core.Response clientResponse) {
        RuntimeException ret;
        if (ex == null) {
            ret = new ProcessingException(new NullPointerException()); // NOPMD
        }
        else if (ex instanceof WebApplicationException) {
            ret = (WebApplicationException) ex;
        }
        else if (ex instanceof ProcessingException) {
            ret = (ProcessingException) ex;
        }
        else if (clientResponse != null) {
            ret = new ResponseProcessingException(clientResponse, ex);
        }
        else {
            ret = new ProcessingException(ex);
        }
        return ret;
    }

    static class RequestFuture<T> extends CompletableFuture<T> {
        private final Request request;

        RequestFuture(Request request) {
            this.request = request;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            final boolean cancelled = super.cancel(mayInterruptIfRunning);
            if (mayInterruptIfRunning && cancelled) {
                request.abort(new CancellationException());
            }
            return cancelled;
        }
    }
}
