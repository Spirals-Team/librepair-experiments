package io.vertx.zero.marshal.options;

import io.vertx.core.json.JsonObject;
import io.vertx.zero.exception.ZeroException;
import io.vertx.zero.exception.heart.EmptyStreamException;
import io.vertx.zero.exception.heart.LimeFileException;
import io.vertx.zero.marshal.node.Node;
import io.vertx.zero.marshal.node.ZeroTool;
import io.zero.epic.fn.Fn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Options for different configuration
 *
 * @param <T>
 */
public interface Opts<T> {

    /**
     * Read reference of Opts
     *
     * @return Opts contains json object.
     */
    static Opts<JsonObject> get() {
        return YamlOpts.create();
    }

    /**
     * Read data from files
     *
     * @return The config data
     * @throws ZeroException zero exception that prevent start up
     */
    T ingest(String node) throws ZeroException;
}

class YamlOpts implements Opts<JsonObject> {

    private static final ConcurrentMap<String, Node<JsonObject>>
            EXTENSIONS = new ConcurrentHashMap<>();
    private static Opts<JsonObject> INSTANCE;

    private YamlOpts() {
    }

    /**
     * Default package scope, manually implement singleton
     * instead of Ut.singleton.
     *
     * @return Opts reference contains JsonObject
     */
    public static Opts<JsonObject> create() {
        if (null == INSTANCE) {
            synchronized (YamlOpts.class) {
                if (null == INSTANCE) {
                    INSTANCE = new YamlOpts();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public JsonObject ingest(final String key) {
        final Node<JsonObject> node =
                Fn.pool(EXTENSIONS, key,
                        () -> Node.infix(key));
        final JsonObject data = new JsonObject();
        try {
            data.mergeIn(node.read());
        } catch (final EmptyStreamException ex) {
            if (data.isEmpty()) {
                throw new LimeFileException(ZeroTool.produce(key));
            }
        }
        return data;
    }
}
