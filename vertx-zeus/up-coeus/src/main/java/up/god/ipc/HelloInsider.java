package up.god.ipc;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Uson;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class HelloInsider {

    @Ipc("RPC://SAY/HELLO")
    public Envelop sayHello(final Envelop envelop) {
        final JsonObject data = envelop.data();
        System.out.println(data);
        return Envelop.success(data);
    }

    @Ipc("RPC://SAY/FUTURE")
    public Future<JsonObject> sayFuture(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return Uson.create(data).toFuture();
    }
}
