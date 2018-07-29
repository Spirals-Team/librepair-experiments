package up.god.ipc;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class EnvelopInsider {

    @Ipc("RPC://IPC/NODE/ENVELOP1")
    public JsonObject sayEnvelop(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-hecate")
                .put("type", "envelop");
    }
}
