package io.descoped.client.http;

import java.net.URI;
import java.time.Duration;

public interface RequestBuilder {

    RequestBuilder uri(URI uri);

    RequestBuilder header(String name, String value);

    RequestBuilder timeout(Duration duration);

    RequestBuilder GET();

    RequestBuilder POST(RequestBodyProcessor body);

    RequestBuilder DELETE(RequestBodyProcessor body);

    RequestBuilder PUT(RequestBodyProcessor body);

    Request build();

}
