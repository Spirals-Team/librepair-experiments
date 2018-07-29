package up.god.micro.multi;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class MultiWorker {
    @Address("ZERO://RPC/MULTI")
    @SuppressWarnings("unchecked")
    public Future<JsonObject> sayHello(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        final JsonObject params = new JsonObject().put("name", name);
        return Ux.thenParallelJson(
                Ux.thenRpc("ipc-crius", "RPC://SAY/MULTI", params),
                Ux.thenRpc("ipc-coeus", "RPC://SAY/MULTI", params)
        ).compose(item -> {
            final JsonObject crius = item.getJsonObject("0");
            final JsonObject coeus = item.getJsonObject("1");
            return Future.succeededFuture(new JsonObject()
                    .put("name", crius.getValue("name"))
                    .put("first", crius.getValue("service"))
                    .put("second", coeus.getValue("service"))
            );
        });
    }
}
