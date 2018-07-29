package up.god.micro.mesh;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class HuttWorker {

    @Address("ZERO://IPC/NODE/HUTT")
    @Ipc(to = "RPC://IPC/NODE/HUTT", name = "ipc-coeus")
    public Future<JsonObject> sayHutt(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        return Future.succeededFuture(new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus"));
    }
}
