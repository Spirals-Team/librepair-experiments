package io.descoped.client.http;

import java.net.URI;
import java.util.Optional;

public interface Response<T> {

    /**
     * Returns the status code for this response.
     *
     * @return the response code
     */
    int statusCode();

    /**
     * Returns the initial {@link Request} that initiated the exchange.
     *
     * @return the request
     */
    Request request();

    /**
     * Returns the received response headers.
     *
     * @return the response headers
     */
    Headers headers();

    /**
     * Returns the body. Depending on the type of {@code T}, the returned body may
     * represent the body after it was read (such as {@code byte[]}, or
     * {@code String}, or {@code Path}) or it may represent an object with
     * which the body is read, such as an {@link java.io.InputStream}.
     *
     * @return the body
     */
    Optional<T> body();

    /**
     * Returns the {@code URI} that the response was received from. This may be
     * different from the request {@code URI} if redirection occurred.
     *
     * @return the URI of the response
     */
    public abstract URI uri();

    boolean isError();

    Exception getError();

}

