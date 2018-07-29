package io.vertx.zero.marshal.node;

import io.vertx.core.json.JsonObject;
import io.zero.epic.Ut;
import io.zero.quiz.ZeroBase;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

public class ZeroVertxTc extends ZeroBase {

    @Test
    public void testVertxRead() {
        final Node<JsonObject>
                node = Ut.singleton(ZeroVertx.class);
        final JsonObject data = node.read();
    }

    @Test
    public void testLimeRead() {
        final Node<ConcurrentMap<String, String>>
                node = Ut.singleton(ZeroLime.class);
        final ConcurrentMap<String, String> files = node.read();
    }

}
