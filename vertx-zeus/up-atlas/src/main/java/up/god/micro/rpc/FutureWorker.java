package up.god.micro.rpc;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class FutureWorker {
    @Address("ZERO://RPC/SECOND")
    public Future<JsonObject> sayHello(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        final JsonObject params = new JsonObject().put("name", name);
        return Ux.thenRpc("ipc-coeus", "RPC://SAY/FUTURE", params);
    }
}
