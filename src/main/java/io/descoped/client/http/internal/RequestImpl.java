package io.descoped.client.http.internal;

import io.descoped.client.http.Request;
import io.descoped.client.http.RequestBodyProcessor;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RequestImpl implements Request {

    private final String method;
    private final URI uri;
    private final boolean secure;
    private final RequestBodyProcessor requestProcessor;
    private final Duration duration;
    private final HeadersImpl systemHeaders;
    private final HeadersImpl userHeaders;
    private RequestBuilderImpl builder;

    public RequestImpl(RequestBuilderImpl builder) {
        this.builder = builder;

        this.method = builder.getMethod();
        this.systemHeaders = new HeadersImpl();
        this.userHeaders = (HeadersImpl) builder.getHeaders();
        this.uri = builder.getUri();
        this.secure = uri.getScheme().toLowerCase(Locale.US).equals("https");
        if (builder.getBody() == null) {
            this.requestProcessor = new RequestProcessors.EmptyProcessor();
        } else {
            this.requestProcessor = builder.getBody();
        }
        this.duration = builder.getDuration();
    }

    public String getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isSecure() {
        return secure;
    }

    public RequestBodyProcessor getRequestProcessor() {
        return requestProcessor;
    }

    public Duration getDuration() {
        return duration;
    }

    public Map<String,List<String>> headers() {
        userHeaders.addHeader("ove", "ranheim");
        return userHeaders.map();
    }

}
