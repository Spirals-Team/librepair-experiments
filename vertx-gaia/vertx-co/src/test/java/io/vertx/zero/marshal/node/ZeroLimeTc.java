package io.vertx.zero.marshal.node;

import io.vertx.core.json.JsonObject;
import io.zero.epic.Ut;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

public class ZeroLimeTc {

    private transient final Node<ConcurrentMap<String, String>> node
            = Ut.singleton(ZeroLime.class);

    @Test
    public void testGen() {
        final Node<JsonObject> dyanmic = Ut.singleton(ZeroUniform.class);
        final JsonObject data = dyanmic.read();
        System.out.println(data.encodePrettily());
    }
}
