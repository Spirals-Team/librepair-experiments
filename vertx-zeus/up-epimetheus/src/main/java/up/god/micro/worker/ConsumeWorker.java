package up.god.micro.worker;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class ConsumeWorker {

    @Address("ZERO://IPC/NODE/WORKER1")
    @Ipc(to = "RPC://IPC/NODE/WORKER1", name = "ipc-hecate")
    public Future<JsonObject> worker1(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        return Future.succeededFuture(new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus"));
    }

    @Address("ZERO://IPC/NODE/WORKER2")
    @Ipc(to = "RPC://IPC/NODE/WORKER2", name = "ipc-hecate")
    public Future<JsonObject> worker2(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        return Future.succeededFuture(new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus"));
    }

    @Address("ZERO://IPC/NODE/WORKER3")
    @Ipc(to = "RPC://IPC/NODE/WORKER3", name = "ipc-hecate")
    public Future<JsonObject> worker3(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        return Future.succeededFuture(new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus"));
    }
}
