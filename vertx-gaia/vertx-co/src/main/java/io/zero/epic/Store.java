package io.zero.epic;

import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.json.JsonObject;
import io.zero.epic.fn.Fn;

/**
 * Connect to vertx config to getNull options
 * From filename to ConfigStoreOptions
 */
final class Store {

    /**
     * Return yaml
     */

    private Store() {
    }

    /**
     * Return json
     *
     * @param filename
     * @return
     */
    static ConfigStoreOptions getJson(final String filename) {
        return Fn.getJvm(() -> {
            final JsonObject data = IO.getJObject(filename);
            return Fn.getJvm(() ->
                            Fn.pool(Storage.STORE, filename,
                                    () -> new ConfigStoreOptions()
                                            .setType(StoreType.JSON.key())
                                            .setConfig(data))
                    , data);
        }, filename);
    }

    /**
     * Return yaml
     *
     * @param filename
     * @return
     */
    static ConfigStoreOptions getYaml(final String filename) {
        return getFile(filename, StoreFormat.YAML);
    }

    /**
     * Return properties
     *
     * @param filename
     * @return
     */
    static ConfigStoreOptions getProp(final String filename) {
        return getFile(filename, StoreFormat.PROP);
    }

    private static ConfigStoreOptions getFile(final String filename,
                                              final StoreFormat format) {
        return Fn.getJvm(() -> {
            final JsonObject config = new JsonObject()
                    .put(StoreConfig.PATH.key(), IO.getPath(filename));
            return Fn.pool(Storage.STORE, filename,
                    () -> new ConfigStoreOptions()
                            .setType(StoreType.FILE.key())
                            .setFormat(format.key())
                            .setConfig(config));
        }, filename, format);
    }
}
