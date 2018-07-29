package up.god.ipc.mesh;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class HuttInsider {

    @Ipc(value = "RPC://IPC/NODE/HUTTS")
    public JsonObject sayHutt(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-hecate")
                .put("type", "async");
    }
}
