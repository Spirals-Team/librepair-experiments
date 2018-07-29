package up.god.micro.filter;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class FilterWorker {

    @Address("ZERO://JSR340/WORKER")
    public Future<JsonObject> work(final Envelop envelop) {
        final String key = envelop.context("key", String.class);
        final String key1 = envelop.context("key1", String.class);
        return Future.succeededFuture(new JsonObject().put("key", key).put("key1", key1));
    }
}
