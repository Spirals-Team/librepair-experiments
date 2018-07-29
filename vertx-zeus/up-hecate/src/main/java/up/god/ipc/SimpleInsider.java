package up.god.ipc;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class SimpleInsider {

    @Ipc("RPC://IPC/NODE/AGENT1")
    public JsonObject saySimple(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-hecate");
    }
}
