package io.descoped.client.http;

import java.util.Optional;

public interface ResponseBodyProcessor<T> {

    /**
     * Returns the body object
     *
     * @return a body object
     */
    Optional<T> getBody();

}
