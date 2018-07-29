package io.vertx.up.web;

import io.vertx.core.json.JsonObject;
import io.vertx.up.log.Annal;
import io.vertx.zero.log.internal.Log4JAnnal;
import io.vertx.zero.marshal.options.Opts;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Critical Environment
 *
 * @author lang
 */
public final class ZeroAmbient {

    private static final String KEY = "inject";
    /**
     * Avoid dead lock, use internal Log only.
     **/
    private static final Annal LOGGER = new Log4JAnnal(ZeroAmbient.class);

    private static final ConcurrentMap<String, Class<?>> INJECTIONS;

    private static final Opts<JsonObject> OPTS = Opts.get();

    static {
        INJECTIONS = new ConcurrentHashMap<>();
        // 2. The injections must be configured in lime node.
        Fn.outUp(() -> {
            final JsonObject opt = OPTS.ingest(KEY);
            Ut.itJObject(opt, (item, field) -> {
                final String plugin = item.toString();
                LOGGER.info(Info.PLUGIN_LOAD, KEY, field, plugin);
                INJECTIONS.put(field, Ut.clazz(plugin));
            });
        }, LOGGER);
    }

    private ZeroAmbient() {
    }

    public static Class<?> getPlugin(final String key) {
        return INJECTIONS.get(key);
    }

    public static ConcurrentMap<String, Class<?>> getInjections() {
        return INJECTIONS;
    }

    /**
     * Inited by ZeroGrid static
     */
    static void init() {
    }
}
