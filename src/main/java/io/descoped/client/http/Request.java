package io.descoped.client.http;

import io.descoped.client.http.internal.RequestBuilderImpl;

import java.net.URI;

public interface Request {

    static RequestBuilder builder(URI uri) {
        return new RequestBuilderImpl(uri);
    }

}
