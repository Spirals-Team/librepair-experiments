package up.god.micro.inject;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;

import javax.inject.Inject;

@Queue
public class SimpleWorker {

    @Inject
    private transient SimpleObject simple;

    @Address("ZERO://INJECT/SIMPLE")
    public Future<JsonObject> process(final JsonObject user) {
        final JsonObject processed = this.simple.getData(user);
        return Future.succeededFuture(processed);
    }
}
