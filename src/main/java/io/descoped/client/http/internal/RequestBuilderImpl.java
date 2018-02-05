package io.descoped.client.http.internal;

import io.descoped.client.http.Headers;
import io.descoped.client.http.Request;
import io.descoped.client.http.RequestBodyProcessor;
import io.descoped.client.http.RequestBuilder;

import java.net.URI;
import java.time.Duration;

import static java.util.Objects.requireNonNull;

public class RequestBuilderImpl implements RequestBuilder {

    private final HeadersImpl headers;
    private URI uri;
    private String method;
    private RequestBodyProcessor body;
    private Duration duration;

    public RequestBuilderImpl(URI uri) {
        headers = new HeadersImpl();
        this.uri = uri;
    }

    public Headers getHeaders() {
        return headers;
    }

    public URI getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public RequestBodyProcessor getBody() {
        return body;
    }

    @Override
    public RequestBuilder uri(URI uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public RequestBuilder header(String name, String value) {
        headers.addHeader(name, value);
        return this;
    }

    @Override
    public RequestBuilder timeout(Duration duration) {
        requireNonNull(duration);
        if (duration.isNegative() || Duration.ZERO.equals(duration))
            throw new IllegalArgumentException("Invalid duration: " + duration);
        this.duration = duration;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public RequestBuilder GET() {
        return method("GET", null);
    }

    @Override
    public RequestBuilder POST(RequestBodyProcessor body) {
        return method("POST", body);
    }

    @Override
    public RequestBuilder DELETE(RequestBodyProcessor body) {
        return method("DELETE", body);
    }

    @Override
    public RequestBuilder PUT(RequestBodyProcessor body) {
        return method("PUT", body);
    }

    private RequestBuilder method(String method, RequestBodyProcessor body) {
        this.method = requireNonNull(method);
        this.body = body;
        return this;
    }

    @Override
    public Request build() {
        return new RequestImpl(this);
    }


}
