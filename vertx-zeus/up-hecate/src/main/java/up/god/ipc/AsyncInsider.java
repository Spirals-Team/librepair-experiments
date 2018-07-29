package up.god.ipc;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class AsyncInsider {

    @Ipc("RPC://IPC/NODE/ASYNC1")
    public JsonObject sayAsync1(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-hecate")
                .put("type", "async");
    }
}
