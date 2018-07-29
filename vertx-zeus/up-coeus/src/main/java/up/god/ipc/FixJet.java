package up.god.ipc;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class FixJet {

    @Ipc("IPC://FIX/ENVELOP")
    public Future<Envelop> sayHello(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return Ux.thenOne(data, "").compose(item -> {
            System.out.println(item);
            return Future.succeededFuture(item);
        });
    }
}
