package up.god.micro.async;

import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class VertxStyleWorker {


    @Address("ZERO://ASYNC/VERTX/HANDLER")
    public void sayMessage(final Message<Envelop> message) {
        final JsonObject data = Ux.getBody(message);
        message.reply(Envelop.success(data));
    }

    @Address("ZERO://ASYNC/VERTX/FUTURE")
    public Future<JsonObject> sayFuture(final Envelop envelop) {
        final JsonObject data = envelop.data();
        return Future.succeededFuture(data);
    }
}
