package up.god.ipc;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class MultiInsider {
    @Ipc("RPC://SAY/MULTI")
    public Future<JsonObject> sayMulti(final Envelop envelop) {
        final JsonObject data = envelop.data();
        data.put("service", "up-crius");
        return Future.succeededFuture(data);
    }
}
