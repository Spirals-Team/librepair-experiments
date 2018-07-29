package up.god.ipc.mesh;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class HuttInsider {
    @Ipc(value = "RPC://IPC/NODE/HUTT1",
            to = "RPC://IPC/NODE/HUTTS", name = "ipc-hecate")
    public JsonObject next(final Envelop envelop) {
        final String content = envelop.data();
        return new JsonObject(content)
                .put("coordinator2", "ipc-cronus");
    }

}
