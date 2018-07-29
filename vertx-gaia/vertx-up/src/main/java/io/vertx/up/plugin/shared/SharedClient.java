package io.vertx.up.plugin.shared;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.LocalMap;
import io.zero.epic.container.KeyPair;

/**
 * Shared client for shared data in vert.x
 */
public interface SharedClient<K, V> {
    /**
     * Create local map from shared data
     */
    static <K, V> SharedClient createShared(final Vertx vertx, final JsonObject config, final String name) {
        return new SharedClientImpl<K, V>(vertx).create(config, name);
    }

    /**
     * Get reference of AsyncMap
     */
    AsyncMap<K, V> fetchAsync();

    /**
     * Get reference of LocalMap
     */
    LocalMap<K, V> fetchSync();

    SharedClient<K, V> switchClient(final String name);

    KeyPair<K, V> put(K key, V value);

    KeyPair<K, V> put(K key, V value, int expiredSecs);

    KeyPair<K, V> remove(K key);

    V get(K key);

    V get(K key, boolean once);

    @Fluent
    SharedClient<K, V> put(K key, V value, Handler<AsyncResult<KeyPair<K, V>>> handler);

    @Fluent
    SharedClient<K, V> put(K key, V value, int expiredSecs, Handler<AsyncResult<KeyPair<K, V>>> handler);

    @Fluent
    SharedClient<K, V> remove(K key, Handler<AsyncResult<KeyPair<K, V>>> handler);

    @Fluent
    SharedClient<K, V> get(K key, Handler<AsyncResult<V>> handler);

    @Fluent
    SharedClient<K, V> get(K key, boolean once, Handler<AsyncResult<V>> handler);
}
