package up.god.ipc;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

public class WorkerInsider {
    @Ipc("RPC://IPC/NODE/WORKER1")
    public JsonObject sayWorker1(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return data.put("terminator", "ipc-hecate")
                .put("worker", "worker1");
    }

    @Ipc("RPC://IPC/NODE/WORKER2")
    public Envelop sayWorker2(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return Envelop.success(data
                .put("terminator", "ipc-hecate")
                .put("worker", "worker2"));
    }

    @Ipc("RPC://IPC/NODE/WORKER3")
    public Future<JsonObject> sayWorker3(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return Future.succeededFuture(data
                .put("terminator", "ipc-hecate")
                .put("worker", "worker3"));
    }
}
