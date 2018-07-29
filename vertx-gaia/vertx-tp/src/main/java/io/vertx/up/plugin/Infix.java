package io.vertx.up.plugin;

import io.vertx.core.json.JsonObject;
import io.vertx.up.log.Annal;
import io.vertx.zero.atom.Ruler;
import io.vertx.zero.exception.ConfigKeyMissingException;
import io.vertx.zero.marshal.node.Node;
import io.vertx.zero.marshal.node.ZeroUniform;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.util.function.Function;

public interface Infix {

    static <R> R initTp(final String key,
                        final Function<JsonObject, R> executor,
                        final Class<?> clazz) {
        final Annal logger = Annal.get(clazz);
        final JsonObject options = init(logger, key, clazz);
        final JsonObject config = null == options.getJsonObject(key) ? new JsonObject() : options.getJsonObject(key);
        final JsonObject ready = config.containsKey("config") ? config.getJsonObject("config") : new JsonObject();
        return init(logger, key, ready, executor);
    }

    static <R> R init(final String key,
                      final Function<JsonObject, R> executor,
                      final Class<?> clazz) {
        final Annal logger = Annal.get(clazz);
        final JsonObject options = init(logger, key, clazz);
        final JsonObject config = null == options.getJsonObject(key) ? new JsonObject() : options.getJsonObject(key);
        return init(logger, key, config, executor);
    }

    static JsonObject init(
            final Annal logger,
            final String key,
            final Class<?> clazz) {
        final Node<JsonObject> node = Ut.instance(ZeroUniform.class);
        final JsonObject options = node.read();
        Fn.outUp(null == options || !options.containsKey(key)
                , logger, ConfigKeyMissingException.class,
                clazz, key);
        return options;
    }

    static <R> R init(
            final Annal logger,
            final String key,
            final JsonObject config,
            final Function<JsonObject, R> executor) {
        Fn.outUp(() -> Ruler.verify(key, config), logger);
        return executor.apply(config);
    }

    <T> T get();
}
