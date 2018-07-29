package up.god.micro.fix;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class EmployeeWorker {

    @Address("ZERO://FIX/ENVELOP")
    public Future<JsonObject> send(final Envelop envelop) {
        final JsonObject params = Ux.getJson(envelop);
        return Ux.thenRpc("ipc-coeus", "IPC://FIX/ENVELOP", params);
    }
}
