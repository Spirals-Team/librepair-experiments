package io.vertx.zero.marshal;

import io.vertx.zero.exception.ZeroException;

public interface Visitor<T> {
    /**
     * @return The generic type of config
     * @throws ZeroException The zero exception that prevent start up
     */
    T visit(String... keys) throws ZeroException;
}
