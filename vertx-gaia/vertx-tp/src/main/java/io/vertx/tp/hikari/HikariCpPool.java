package io.vertx.tp.hikari;

import io.vertx.core.json.JsonObject;
import io.vertx.tp.hikari.config.HikariCpConfig;
import io.vertx.up.log.Annal;
import io.vertx.zero.atom.Ruler;
import io.vertx.zero.marshal.node.Node;
import io.vertx.zero.marshal.node.ZeroUniform;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.sql.Connection;

/**
 * Enabled hikari pool
 */
public class HikariCpPool {

    private static final Annal LOGGER = Annal.get(HikariCpPool.class);
    private static final Node<JsonObject> NODE = Ut.singleton(ZeroUniform.class);
    private static final String KEY = "pool";
    private static final String HIKARI_KEY = "hikari";
    private static HikariCpConfig CONFIG;

    static {
        final JsonObject config = NODE.read();
        Fn.safeSemi(null != config && config.containsKey(KEY), LOGGER, () -> {
            final JsonObject hitted = config.getJsonObject(KEY);
            Fn.safeSemi(null != hitted && hitted.containsKey(HIKARI_KEY), LOGGER, () -> {
                final JsonObject meta = hitted.getJsonObject(HIKARI_KEY);
                Fn.outUp(() -> Fn.shuntZero(() ->
                                Ruler.verify(HIKARI_KEY, meta), meta),
                        LOGGER);
                CONFIG = HikariCpConfig.create(meta);
            });
        });
    }

    public static Connection getConnection(final JsonObject config) {
        return Fn.getJvm(() -> HikariCpConfig.create(config).getPool().getConnection(), config);
    }

    public static Connection getConnection() {
        return Fn.getJvm(() -> CONFIG.getPool().getConnection(), CONFIG);
    }
}
