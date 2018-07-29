package up.god.micro.async;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;

@Queue
public class VertxAsyncWorker {

    @Address("ZERO://ASYNC/VERTX/FUTURE_T")
    public Future<JsonObject> sayFutureT(final JsonObject data) {
        final String string = data.encode();
        System.out.println(string);
        data.put("result", "Perfect");
        return Future.succeededFuture(data);
    }
}
