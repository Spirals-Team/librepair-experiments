package io.vertx.up.aiki;

import io.vertx.core.Future;
import io.vertx.up.exception._500PoolInternalException;
import io.vertx.up.log.Annal;
import io.vertx.up.plugin.shared.MapInfix;
import io.vertx.up.plugin.shared.SharedClient;
import io.zero.epic.container.KeyPair;

/**
 * Shared Data for pool usage in utility X
 */
@SuppressWarnings("all")
public class UxPool {
    private static final Annal LOGGER = Annal.get(UxPool.class);
    private transient final String name;
    private transient final SharedClient client;

    UxPool() {
        this.name = MapInfix.getDefaultName();
        this.client = MapInfix.getClient();
    }

    UxPool(final String name) {
        this.name = name;
        this.client = MapInfix.getClient().switchClient(name);
    }

    // Put Operation
    public <K, V> Future<KeyPair<K, V>> put(final K key, final V value) {
        return Ux.<KeyPair<K, V>>thenGeneric(future -> this.client.put(key, value, res -> {
            LOGGER.debug(Info.POOL_PUT, key, value, this.name);
            Ux.thenGeneric(res, future, To.toError(_500PoolInternalException.class, this.getClass(), this.name, "put"));
        }));
    }

    public <K, V> Future<KeyPair<K, V>> put(final K key, final V value, int expiredSecs) {
        return Ux.<KeyPair<K, V>>thenGeneric(future -> this.client.<K, V>put(key, value, expiredSecs, res -> {
            LOGGER.debug(Info.POOL_PUT_TIMER, key, value, this.name, String.valueOf(expiredSecs));
            Ux.thenGeneric(res, future, To.toError(_500PoolInternalException.class, this.getClass(), this.name, "put"));
        }));
    }

    // Remove
    public <K, V> Future<KeyPair<K, V>> remove(final K key) {
        return Ux.<KeyPair<K, V>>thenGeneric(future -> this.client.<K, V>remove(key, res -> {
            LOGGER.debug(Info.POOL_REMOVE, key, this.name);
            Ux.thenGeneric(res, future, To.toError(_500PoolInternalException.class, this.getClass(), this.name, "remove"));
        }));
    }

    // Get
    public <K, V> Future<V> get(final K key) {
        return Ux.<V>thenGeneric(future -> this.client.get(key, res -> {
            LOGGER.debug(Info.POOL_GET, key, this.name, false);
            Ux.thenGeneric(res, future, To.toError(_500PoolInternalException.class, this.getClass(), this.name, "remove"));
        }));
    }

    public <K, V> Future<V> get(final K key, final boolean once) {
        return Ux.<V>thenGeneric(future -> this.client.get(key, once, res -> {
            LOGGER.debug(Info.POOL_GET, key, this.name, once);
            Ux.thenGeneric(res, future, To.toError(_500PoolInternalException.class, this.getClass(), this.name, "remove"));
        }));
    }
}
