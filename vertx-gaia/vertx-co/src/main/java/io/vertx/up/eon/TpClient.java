package io.vertx.up.eon;

import io.vertx.core.json.JsonObject;

/**
 * Uniform third part interface for client
 */
public interface TpClient<T> {

    T init(JsonObject params);
}
