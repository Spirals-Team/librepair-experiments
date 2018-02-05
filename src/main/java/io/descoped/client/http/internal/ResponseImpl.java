package io.descoped.client.http.internal;

import io.descoped.client.http.Headers;
import io.descoped.client.http.Request;
import io.descoped.client.http.Response;
import io.descoped.client.http.internal.httpRequest.HttpRequestExchange;

import java.net.URI;
import java.util.Optional;

public class ResponseImpl<T> implements Response<T> {

    private final int responseCode;
    private final HttpRequestExchange<T> exchange;
    private final RequestImpl initialRequest;
    private final Headers headers;
    private final URI uri;
    private final Optional<T> body;
    private Exception error;

    public ResponseImpl(Request initialRequest,
                        int statusCode,
                        Headers responseHeaders,
                        Optional<T> body, HttpRequestExchange<T> exch) {
        this.responseCode = statusCode;
        this.exchange = exch;
        this.initialRequest = (RequestImpl) initialRequest;
        this.headers = responseHeaders;
        this.uri = this.initialRequest.getUri();
        this.body = body;
    }

    @Override
    public int statusCode() {
        return responseCode;
    }

    @Override
    public Request request() {
        return initialRequest;
    }

    @Override
    public Headers headers() {
        return headers;
    }

    @Override
    public Optional<T> body() {
        return body;
    }

    @Override
    public URI uri() {
        return uri;
    }

    @Override
    public boolean isError() {
        return error != null;
    }

    public void setError(Exception error) {
        this.error = error;
    }


    @Override
    public Exception getError() {
        return error;
    }

    public HttpRequestExchange<T> getExchange() {
        return exchange;
    }
}
