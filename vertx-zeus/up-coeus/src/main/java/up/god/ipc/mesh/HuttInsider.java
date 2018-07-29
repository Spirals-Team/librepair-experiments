package up.god.ipc.mesh;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class HuttInsider {
    @Ipc(value = "RPC://IPC/NODE/HUTT",
            to = "RPC://IPC/NODE/HUTT1", name = "ipc-crius")
    public String next(final Envelop envelop) {
        final JsonObject data = envelop.data();
        data.put("coordinator1", "ipc-coeus");
        return data.encode();
    }
}
