package up.god.ipc;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class DynamicInsider {

    @Ipc("RPC://IPC/NODE/DYNAMIC1")
    public JsonObject sayDyanmic(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-hecate")
                .put("type", "dynamic");
    }
}
