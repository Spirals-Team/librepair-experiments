package up.god.stream;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class NextInsider {
    @Ipc(value = "IPC://TWO/THIRD")
    public JsonObject next(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-cronus");
    }

}
