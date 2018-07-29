package up.god.micro.worker;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.annotations.Queue;

@Queue
public class AsyncWorker {

    @Address("ZERO://IPC/NODE/ASYNC1")
    @Ipc(to = "RPC://IPC/NODE/ASYNC1", name = "ipc-hecate")
    public Future<JsonObject> sayAsync(final JsonObject params) {
        final String name = params.getString("0");
        return Future.succeededFuture(new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus"));
    }
}
