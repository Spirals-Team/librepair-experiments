package io.vertx.zero.micro.config;

import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.up.log.Annal;
import io.vertx.zero.atom.Ruler;
import io.vertx.zero.eon.Info;
import io.vertx.zero.exception.ZeroException;
import io.vertx.zero.marshal.Visitor;
import io.vertx.zero.marshal.node.Node;
import io.vertx.zero.marshal.node.ZeroUniform;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

public class CircuitVisitor implements Visitor<CircuitBreakerOptions> {

    private static final Annal LOGGER = Annal.get(CircuitVisitor.class);
    private static final String CIRCUIT = "circuit";
    private final transient Node<JsonObject> node =
            Ut.singleton(ZeroUniform.class);

    @Override
    public CircuitBreakerOptions visit(final String... key)
            throws ZeroException {
        // 1. Must be the first line, fixed position.
        Ut.ensureEqualLength(this.getClass(), 0, (Object[]) key);
        // 2. Read data
        final JsonObject data = this.node.read();
        // 3. CircuitBreakerOptions building.
        final JsonObject config =
                Fn.getSemi(data.containsKey(CIRCUIT) &&
                                null != data.getValue(CIRCUIT), LOGGER,
                        () -> data.getJsonObject(CIRCUIT),
                        JsonObject::new);
        // 4. Verify the configuration data
        return this.visit(config);
    }

    private CircuitBreakerOptions visit(final JsonObject data)
            throws ZeroException {
        LOGGER.info(Info.INF_B_VERIFY, CIRCUIT, "Circuit", data.encode());
        Ruler.verify("circuit", data);
        return new CircuitBreakerOptions(data);
    }
}
