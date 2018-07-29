package io.vertx.up.plugin.shared;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.up.exception._501SharedDataModeException;
import io.vertx.up.log.Annal;
import io.zero.epic.container.KeyPair;
import io.zero.epic.fn.Fn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("all")
public class SharedClientImpl<K, V> implements SharedClient<K, V> {

    private static final Annal LOGGER = Annal.get(SharedClientImpl.class);

    private static final ConcurrentMap<String, SharedClient> CLIENTS =
            new ConcurrentHashMap<>();

    private final transient Vertx vertx;
    private transient LocalMap<K, V> syncMap;
    private transient AsyncMap<K, V> asyncMap;
    private transient boolean isAsync;

    SharedClientImpl(final Vertx vertx) {
        this.vertx = vertx;
    }

    SharedClient create(final JsonObject config, final String name) {
        return Fn.pool(CLIENTS, name, () -> {
            final boolean async = null != config && config.containsKey("async") ?
                    config.getBoolean("async") : Boolean.FALSE;
            if (async) {
                // Switch reference for async map to be sure it's initialized.
                this.createAsync(name, res -> this.asyncMap = res.result().fetchAsync());
            } else {
                this.createSync(name);
            }
            return this;
        });
    }

    @Override
    public AsyncMap<K, V> fetchAsync() {
        return this.asyncMap;
    }

    @Override
    public LocalMap<K, V> fetchSync() {
        return this.syncMap;
    }

    @Override
    public SharedClient<K, V> switchClient(final String name) {
        final SharedClient<K, V> client;
        if (this.isAsync) {
            // Switch reference for async map to be sure it's initialized.
            client = new SharedClientImpl<K, V>(this.vertx).create(new JsonObject().put("async", Boolean.TRUE), name);
        } else {
            client = new SharedClientImpl<K, V>(this.vertx).create(new JsonObject().put("async", Boolean.FALSE), name);
        }
        return client;
    }

    private SharedClient createSync(final String name) {
        final SharedData sd = this.vertx.sharedData();
        // Sync map created
        this.syncMap = sd.getLocalMap(name);
        LOGGER.info(Info.INFO_SYNC, String.valueOf(this.syncMap.hashCode()));
        this.isAsync = false;
        return this;
    }

    private SharedClient createAsync(final String name,
                                     final Handler<AsyncResult<SharedClient>> handler) {
        final SharedData sd = this.vertx.sharedData();
        // Async map created
        LOGGER.info(Info.INFO_ASYNC_START);
        sd.<K, V>getAsyncMap(name, res -> {
            if (res.succeeded()) {
                this.asyncMap = res.result();
                LOGGER.info(Info.INFO_ASYNC_END, String.valueOf(this.asyncMap.hashCode()));
                this.isAsync = true;
                handler.handle(Future.succeededFuture(this));
            }
        });
        return this;
    }

    @Override
    public KeyPair<K, V> put(final K key, final V value) {
        this.ensure(false);
        final V reference = this.syncMap.get(key);
        // Add & Replace
        Fn.safeSemi(null == reference, LOGGER,
                () -> this.syncMap.put(key, value),
                () -> this.syncMap.replace(key, value));
        return KeyPair.create(key, value);
    }

    @Override
    public KeyPair<K, V> put(final K key, final V value, final int seconds) {
        KeyPair<K, V> result = this.put(key, value);
        LOGGER.info(Info.INFO_TIMER_PUT, key, String.valueOf(seconds));
        this.vertx.setTimer(seconds * 1000, id -> {
            LOGGER.info(Info.INFO_TIMER_EXPIRE, key);
            this.remove(key);
        });
        return result;
    }

    @Override
    public SharedClient<K, V> put(final K key, final V value,
                                  final Handler<AsyncResult<KeyPair<K, V>>> handler) {
        this.ensure(true);
        this.asyncMap.get(key, res -> {
            if (res.succeeded()) {
                final V reference = res.result();
                Fn.safeSemi(null == reference, LOGGER,
                        () -> this.asyncMap.put(key, value, added -> {
                            if (added.succeeded()) {
                                handler.handle(Future.succeededFuture(KeyPair.create(key, value)));
                            }
                        }),
                        () -> this.asyncMap.replace(key, value, replaced -> {
                            if (replaced.succeeded()) {
                                handler.handle(Future.succeededFuture(KeyPair.create(key, value)));
                            }
                        }));
            }
        });
        return this;
    }

    @Override
    public SharedClient<K, V> put(final K key, final V value, final int seconds,
                                  final Handler<AsyncResult<KeyPair<K, V>>> handler) {
        final SharedClient<K, V> reference = this.put(key, value, handler);
        LOGGER.info(Info.INFO_TIMER_PUT, key, String.valueOf(seconds));
        this.vertx.setTimer(seconds * 1000, id -> this.remove(key, res -> LOGGER.info(Info.INFO_TIMER_EXPIRE, key)));
        return reference;
    }

    @Override
    public KeyPair<K, V> remove(final K key) {
        this.ensure(false);
        final V removed = this.syncMap.remove(key);
        return KeyPair.create(key, removed);
    }

    @Override
    public V get(final K key) {
        this.ensure(false);
        return this.syncMap.get(key);
    }

    @Override
    public V get(final K key, final boolean once) {
        final V value = this.get(key);
        if (once) {
            this.remove(key);
        }
        return value;
    }

    @Override
    public SharedClient<K, V> remove(final K key,
                                     final Handler<AsyncResult<KeyPair<K, V>>> handler) {
        this.ensure(true);
        this.asyncMap.remove(key, res -> {
            if (res.succeeded()) {
                final V reference = res.result();
                handler.handle(Future.succeededFuture(KeyPair.create(key, reference)));
            }
        });
        return this;
    }

    @Override
    public SharedClient<K, V> get(final K key,
                                  final Handler<AsyncResult<V>> handler) {
        this.ensure(true);
        this.asyncMap.get(key, handler);
        return this;
    }

    @Override
    public SharedClient<K, V> get(K key, boolean once,
                                  Handler<AsyncResult<V>> handler) {
        final SharedClient<K, V> reference = this.get(key, handler);
        if (once) {
            this.asyncMap.remove(key, handler);
        }
        return reference;
    }

    private void ensure(final boolean expected) {
        Fn.outWeb(this.isAsync != expected, LOGGER,
                _501SharedDataModeException.class, this.getClass(), this.isAsync);
    }
}
