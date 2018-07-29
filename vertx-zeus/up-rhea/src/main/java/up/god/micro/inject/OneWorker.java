package up.god.micro.inject;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;

import javax.inject.Inject;

@Queue
public class OneWorker {

    @Inject
    private transient OneStub stub;

    @Address("ZERO://INJECT/ONE")
    public Future<JsonObject> process(final JsonObject user) {
        final JsonObject processed = this.stub.getData(user);
        return Future.succeededFuture(processed);
    }
}
