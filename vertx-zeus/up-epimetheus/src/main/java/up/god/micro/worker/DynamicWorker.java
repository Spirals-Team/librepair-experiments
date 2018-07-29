package up.god.micro.worker;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.annotations.Queue;

@Queue
public class DynamicWorker {

    @Address("ZERO://IPC/NODE/DYNAMIC1")
    @Ipc(to = "RPC://IPC/NODE/DYNAMIC1", name = "ipc-hecate")
    public JsonObject sayDynamic(final String name) {
        return new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus");
    }
}
